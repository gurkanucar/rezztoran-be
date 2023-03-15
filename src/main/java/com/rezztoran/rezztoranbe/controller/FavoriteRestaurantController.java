package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.request.FavoriteRestaurantRequestModel;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.FavoriteRestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteRestaurantController {

  private final FavoriteRestaurantService favoriteRestaurantService;

  public FavoriteRestaurantController(FavoriteRestaurantService favoriteRestaurantService) {
    this.favoriteRestaurantService = favoriteRestaurantService;
  }

  @PostMapping
  ResponseEntity<ApiResponse<Object>> addFavoriteRestaurant(
      @RequestBody FavoriteRestaurantRequestModel requestModel) {
    favoriteRestaurantService.addToFavorite(requestModel);
    return ApiResponse.builder().build();
  }

  @DeleteMapping
  ResponseEntity<ApiResponse<Object>> removeFavoriteRestaurant(
      @RequestBody FavoriteRestaurantRequestModel requestModel) {
    favoriteRestaurantService.removeFromFavorite(requestModel);
    return ApiResponse.builder().build();
  }

  @GetMapping("/user/{id}")
  ResponseEntity<ApiResponse<Object>> getFavoriteRestaurantsByUser(@PathVariable Long id) {
    return ApiResponse.builder()
        .data(favoriteRestaurantService.getFavoriteRestaurantsByUser(id))
        .build();
  }
}
