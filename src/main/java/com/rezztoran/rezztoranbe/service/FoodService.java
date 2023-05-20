package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.FoodDTO;
import com.rezztoran.rezztoranbe.model.Food;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** The interface Food service. */
public interface FoodService {

  /**
   * Create food food.
   *
   * @param food the food
   * @return the food
   */
  Food createFood(Food food);

  /**
   * Create food list.
   *
   * @param foods the foods
   */
  void createFoodList(List<Food> foods);
  /**
   * Update food food.
   *
   * @param food the food
   * @return the food
   */
  Food updateFood(Food food);

  /**
   * Gets food by id.
   *
   * @param id the id
   * @return the food by id
   */
  Food getFoodByID(Long id);
  /**
   * Delete food by id.
   *
   * @param id the id
   */
  void deleteFoodByID(Long id);
  /**
   * Gets food by restaurant id.
   *
   * @param id the id
   * @param pageable the pageable
   * @return the food by restaurant id
   */
  Page<FoodDTO> getFoodByRestaurantID(Long id, Pageable pageable);
}
