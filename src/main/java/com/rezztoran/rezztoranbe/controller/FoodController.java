package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.FoodDTO;
import com.rezztoran.rezztoranbe.model.Food;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.FoodService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/food")
public class FoodController {

  private final FoodService foodService;

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> getFoodByID(@PathVariable Long id) {
    return ApiResponse.builder().data(FoodDTO.toDTO(foodService.getFoodByID(id))).build();
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Object>> createFood(@RequestBody Food food) {
    return ApiResponse.builder().data(FoodDTO.toDTO(foodService.createFood(food))).build();
  }

  @PutMapping
  public ResponseEntity<ApiResponse<Object>> updateFood(@RequestBody Food food) {
    return ApiResponse.builder().data(FoodDTO.toDTO(foodService.updateFood(food))).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteFoodByID(@PathVariable Long id) {
    foodService.deleteFoodByID(id);
    return ApiResponse.builder().build();
  }
}
