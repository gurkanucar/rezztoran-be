package com.rezztoran.rezztoranbe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.rezztoran.rezztoranbe.exception.BusinessException;
import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.Food;
import com.rezztoran.rezztoranbe.repository.FoodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {
  @Mock private FoodRepository foodRepository;

  @Mock private MenuService menuService;

  @Mock private ExceptionUtil exceptionUtil;

  private FoodService foodService;

  @BeforeEach
  public void setUp() {
    foodService = new FoodService(foodRepository, menuService, exceptionUtil);
  }

  @Test
  void testCreateFood() {
    Menu menu = new Menu();
    menu.setId(1L);
    Food food = new Food();
    food.setFoodName("Kebab");
    food.setMenu(menu);
    when(menuService.getMenuById(1L)).thenReturn(menu);
    when(foodRepository.save(any(Food.class))).thenReturn(food);
    Food createdFood = foodService.createFood(food);
    assertEquals(food.getFoodName(), createdFood.getFoodName());
    assertEquals(food.getMenu().getId(), createdFood.getMenu().getId());
  }

  @Test
  void testUpdateFood() {
    Menu menu = new Menu();
    menu.setId(1L);
    Food existingFood = new Food();
    existingFood.setId(1L);
    existingFood.setFoodName("Pizza");
    existingFood.setMenu(menu);
    existingFood.setPrice(12.50);
    existingFood.setCal(600.0);
    when(menuService.getMenuById(1L)).thenReturn(menu);
    when(foodRepository.findById(1L)).thenReturn(java.util.Optional.of(existingFood));
    when(foodRepository.save(any(Food.class))).thenReturn(existingFood);
    Food updatedFood = foodService.updateFood(existingFood);
    assertEquals(existingFood.getId(), updatedFood.getId());
    assertEquals(existingFood.getFoodName(), updatedFood.getFoodName());
    assertEquals(existingFood.getMenu().getId(), updatedFood.getMenu().getId());
    assertEquals(existingFood.getPrice(), updatedFood.getPrice());
    assertEquals(existingFood.getCal(), updatedFood.getCal());
  }

  @Test
  void testGetFoodByID() {
    Food food = new Food();
    food.setId(1L);
    when(foodRepository.findById(1L)).thenReturn(java.util.Optional.of(food));
    Food foundFood = foodService.getFoodByID(1L);
    assertEquals(food.getId(), foundFood.getId());
  }

  @Test
  void testGetFoodByIDNotFound() {
    Long nonExistingId = 99L;
    when(foodRepository.findById(nonExistingId)).thenReturn(java.util.Optional.empty());
    BusinessException expectedException =
        new BusinessException("Food not found for id " + nonExistingId, HttpStatus.NOT_FOUND);

    when(exceptionUtil.buildException(Ex.FOOD_NOT_FOUND_EXCEPTION)).thenReturn(expectedException);

    BusinessException actualException =
        assertThrows(BusinessException.class, () -> foodService.getFoodByID(nonExistingId));

    assertEquals(expectedException.getStatus(), actualException.getStatus());
    assertEquals(expectedException.getMessage(), actualException.getMessage());
  }

  @Test
  void testDeleteFoodByID() {
    Food food = new Food();
    food.setId(1L);
    when(foodRepository.findById(1L)).thenReturn(java.util.Optional.of(food));
    foodService.deleteFoodByID(1L);
    Mockito.verify(foodRepository, Mockito.times(1)).delete(food);
  }
}
