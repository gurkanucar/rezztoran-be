package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.Menu;
import com.rezztoran.rezztoranbe.repository.MenuRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** The type Menu service. */
@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {

  private final MenuRepository menuRepository;
  private final RestaurantService restaurantService;
  private final ExceptionUtil exceptionUtil;

  /**
   * Create menu.
   *
   * @param menu the menu
   * @return the menu
   */
  public Menu create(Menu menu) {
    var restaurant = restaurantService.getById(menu.getRestaurant().getId());
    menu.setRestaurant(restaurant);
    menu.setMenuCode(UUID.randomUUID().toString());
    return menuRepository.save(menu);
  }

  /**
   * Update menu.
   *
   * @param menu the menu
   * @return the menu
   */
  public Menu update(Menu menu) {
    var restaurant = restaurantService.getById(menu.getRestaurant().getId());
    var existing = getMenuById(menu.getId());
    existing.setRestaurant(restaurant);
    return menuRepository.save(existing);
  }

  /**
   * Delete.
   *
   * @param id the id
   */
  public void delete(Long id) {
    var existing = getMenuById(id);
    menuRepository.delete(existing);
  }

  /**
   * Gets menu by id.
   *
   * @param id the id
   * @return the menu by id
   */
  public Menu getMenuById(Long id) {
    return menuRepository
        .findById(id)
        .orElseThrow(() -> exceptionUtil.buildException(Ex.NOT_FOUND_EXCEPTION));
  }
}
