package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.Food;
import com.rezztoran.rezztoranbe.repository.FoodRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** The type Food service. */
@Service
@RequiredArgsConstructor
public class FoodService {

  private final FoodRepository foodRepository;
  private final RestaurantService restaurantService;
  private final CategoryService categoryService;
  private final ExceptionUtil exceptionUtil;

  /**
   * Create food food.
   *
   * @param food the food
   * @return the food
   */
  public Food createFood(Food food) {
    var restaurant = restaurantService.getById(food.getRestaurant().getId());
    var category = categoryService.getCategoryByID(food.getCategory().getId());
//    foodRepository
//        .findByFoodName(food.getFoodName())
//        .ifPresentOrElse(
//            (item) -> {
//              if (item.getCategory().getId().equals(category.getId()) && item.get) {
//                throw exceptionUtil.buildException(Ex.ALREADY_EXISTS_EXCEPTION);
//              }
//            },
//            () -> {});
    var savedFood = foodRepository.save(food);
    savedFood.setRestaurant(restaurant);
    savedFood.setCategory(category);
    return savedFood;
  }

  public void createFoodList(List<Food> foods){
    foods.forEach(this::createFood);
  }




  /**
   * Update food food.
   *
   * @param food the food
   * @return the food
   */
  public Food updateFood(Food food) {
    var existingFood = getFoodByID(food.getId());

    var restaurant = restaurantService.getById(food.getRestaurant().getId());
    var category = categoryService.getCategoryByID(food.getCategory().getId());

    existingFood.setRestaurant(restaurant);
    existingFood.setCategory(category);
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

  /**
   * Gets food by restaurant id.
   *
   * @param id the id
   * @return the food by restaurant id
   */
  public List<Food> getFoodByRestaurantID(Long id) {
    return foodRepository.findAllByRestaurant_Id(id);
  }
}
