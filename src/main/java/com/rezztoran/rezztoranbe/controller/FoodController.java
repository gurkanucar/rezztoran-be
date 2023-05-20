package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.FoodDTO;
import com.rezztoran.rezztoranbe.model.Food;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.FoodService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type Food controller. */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/food")
public class FoodController {

  private final FoodService foodService;

  /**
   * Gets food by id.
   *
   * @param id the id
   * @return the food by id
   */
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> getFoodByID(@PathVariable Long id) {
    return ApiResponse.builder().data(FoodDTO.toDTO(foodService.getFoodByID(id))).build();
  }

  /**
   * Gets food by restaurant id.
   *
   * @param id the id
   * @return the food by restaurant id
   */
  @GetMapping("/restaurant/{id}")
  public ResponseEntity<ApiResponse<Object>> getFoodByRestaurantID(@PathVariable Long id) {
    var data =
        foodService.getFoodByRestaurantID(id).stream()
            .map(
                x -> {
                  x.setRestaurant(null);
                  return FoodDTO.toDTO(x);
                })
            .collect(Collectors.toList());

    return ApiResponse.builder().data(data).build();
  }

  /**
   * Create food response entity.
   *
   * @param food the food
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<ApiResponse<Object>> createFood(@RequestBody Food food) {
    return ApiResponse.builder().data(FoodDTO.toDTO(foodService.createFood(food))).build();
  }

  /**
   * Update food response entity.
   *
   * @param food the food
   * @return the response entity
   */
  @PutMapping
  public ResponseEntity<ApiResponse<Object>> updateFood(@RequestBody Food food) {
    return ApiResponse.builder().data(FoodDTO.toDTO(foodService.updateFood(food))).build();
  }

  /**
   * Delete food by id response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteFoodByID(@PathVariable Long id) {
    foodService.deleteFoodByID(id);
    return ApiResponse.builder().build();
  }
}
