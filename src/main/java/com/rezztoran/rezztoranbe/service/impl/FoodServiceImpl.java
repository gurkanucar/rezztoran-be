package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.dto.FoodDTO;
import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.Food;
import com.rezztoran.rezztoranbe.repository.FoodRepository;
import com.rezztoran.rezztoranbe.service.CategoryService;
import com.rezztoran.rezztoranbe.service.FoodService;
import com.rezztoran.rezztoranbe.service.RestaurantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/** The type Food service. */
@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

  private final FoodRepository foodRepository;
  private final RestaurantService restaurantService;
  private final CategoryService categoryService;
  private final ExceptionUtil exceptionUtil;

  @Override
  public Food createFood(Food food) {
    var restaurant = restaurantService.getById(food.getRestaurant().getId());
    var category = categoryService.getCategoryByID(food.getCategory().getId());

    var existing =
        foodRepository.findByFoodNameAndRestaurant_IdAndCategory_Id(
            food.getFoodName(), restaurant.getId(), category.getId());

    if (existing.isPresent()) {
      throw exceptionUtil.buildException(Ex.ALREADY_EXISTS_EXCEPTION);
    }

    var savedFood = foodRepository.save(food);
    savedFood.setRestaurant(restaurant);
    savedFood.setCategory(category);
    return savedFood;
  }

  @Override
  public void createFoodList(List<Food> foods) {
    foods.forEach(this::createFood);
  }

  @Override
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

  @Override
  public Food getFoodByID(Long id) {
    return foodRepository
        .findById(id)
        .orElseThrow(() -> exceptionUtil.buildException(Ex.FOOD_NOT_FOUND_EXCEPTION));
  }

  @Override
  public void deleteFoodByID(Long id) {
    foodRepository.delete(getFoodByID(id));
  }

  @Override
  public Page<FoodDTO> getFoodByRestaurantID(Long id, Pageable pageable) {

    return foodRepository
        .findAllByRestaurant_Id(id, pageable)
        .map(
            x -> {
              x.setRestaurant(null);
              return FoodDTO.toDTO(x);
            });
  }
}
