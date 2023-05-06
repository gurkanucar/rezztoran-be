package com.rezztoran.rezztoranbe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rezztoran.rezztoranbe.dto.MenuDTO;
import com.rezztoran.rezztoranbe.exception.BusinessException;
import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.Menu;
import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.repository.MenuRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

  @Mock private MenuRepository menuRepository;

  @Mock private RestaurantService restaurantService;

  @Mock private ExceptionUtil exceptionUtil;

  @InjectMocks private MenuService menuService;

  @Test
  void testCreateMenu() {
    // Given
    Menu menu = new Menu();
    Restaurant restaurant = new Restaurant();
    restaurant.setId(1L);
    menu.setRestaurant(restaurant);
    when(restaurantService.getById(restaurant.getId())).thenReturn(restaurant);
    when(menuRepository.save(menu)).thenReturn(menu);

    // When
    MenuDTO createdMenu = menuService.create(menu);

    // Then
    assertEquals(menu, createdMenu);
    verify(menuRepository).save(menu);
  }

  @Test
  void testUpdateMenu() {
    // Given
    Long id = 1L;
    Menu existingMenu = new Menu();
    existingMenu.setId(id);
    Restaurant existingRestaurant = new Restaurant();
    existingRestaurant.setId(1L);
    existingMenu.setRestaurant(existingRestaurant);

    Restaurant updatedRestaurant = new Restaurant();
    updatedRestaurant.setId(2L);
    Menu updatedMenu = new Menu();
    updatedMenu.setId(id);
    updatedMenu.setRestaurant(updatedRestaurant);

    when(menuRepository.findById(id)).thenReturn(Optional.of(existingMenu));
    when(restaurantService.getById(updatedRestaurant.getId())).thenReturn(updatedRestaurant);
    when(menuRepository.save(existingMenu)).thenReturn(existingMenu);

    // When
    MenuDTO result = menuService.update(updatedMenu);

    // Then
    assertEquals(existingMenu, result);
    assertEquals(updatedRestaurant, result.getRestaurant());
    verify(menuRepository).save(existingMenu);
  }

  @Test
  void testDeleteMenu() {
    // Given
    Long idToDelete = 1L;
    Menu existingMenu = new Menu();
    existingMenu.setId(idToDelete);
    when(menuRepository.findById(idToDelete)).thenReturn(Optional.of(existingMenu));

    // When
    menuService.delete(idToDelete);

    // Then
    verify(menuRepository).delete(existingMenu);
  }

  @Test
  void testGetMenuById() {
    // Given
    Long menuId = 1L;
    Menu existingMenu = new Menu();
    existingMenu.setId(menuId);

    when(menuRepository.findById(menuId)).thenReturn(Optional.of(existingMenu));

    // When
    Menu result = menuService.getMenuById(menuId);

    // Then
    assertEquals(existingMenu, result);
  }

  @Test
  void testGetMenuByIdNotFound() {
    // arrange
    Long id = 1L;
    when(menuRepository.findById(id)).thenReturn(Optional.empty());
    when(exceptionUtil.buildException(Ex.NOT_FOUND_EXCEPTION))
        .thenReturn(new BusinessException("Menu not found", HttpStatus.NOT_FOUND));

    // act and assert
    BusinessException exception =
        assertThrows(
            BusinessException.class,
            () -> {
              menuService.getMenuById(id);
            });
    assertEquals("Menu not found", exception.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
  }
}
