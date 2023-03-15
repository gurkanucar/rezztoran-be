package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.Food;
import com.rezztoran.rezztoranbe.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The type Food service.
 */
@Service
@RequiredArgsConstructor
public class FoodService {

  private final FoodRepository foodRepository;
  private final MenuService menuService;
  private final ExceptionUtil exceptionUtil;

  /**
   * Create food food.
   *
   * @param food the food
   * @return the food
   */
public Food createFood(Food food) {
    var menu = menuService.getMenuById(food.getMenu().getId());
    food.setMenu(menu);
    return foodRepository.save(food);
  }

  /**
   * Update food food.
   *
   * @param food the food
   * @return the food
   */
public Food updateFood(Food food) {
    var menu = menuService.getMenuById(food.getMenu().getId());
    var existingFood = getFoodByID(food.getId());
    existingFood.setMenu(menu);
    existingFood.setCategories(food.getCategories());
    existingFood.setMainCategory(food.getMainCategory());
    existingFood.setPrice(food.getPrice());
    existingFood.setCal(food.getCal());
    existingFood.setFoodName(food.getFoodName());
    existingFood.setFoodImage(food.getFoodImage());
    return foodRepository.save(food);
  }

  /**
   * Gets food by id.
   *
   * @param id the id
   * @return the food by id
   */
public Food getFoodByID(Long id) {
    return foodRepository
        .findById(id)
        .orElseThrow(() -> exceptionUtil.buildException(Ex.FOOD_NOT_FOUND_EXCEPTION));
  }

  /**
   * Delete food by id.
   *
   * @param id the id
   */
public void deleteFoodByID(Long id) {
    foodRepository.delete(getFoodByID(id));
  }
}
