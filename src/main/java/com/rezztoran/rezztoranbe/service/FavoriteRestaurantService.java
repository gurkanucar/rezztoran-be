package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.request.FavoriteRestaurantRequestModel;
import com.rezztoran.rezztoranbe.model.FavoriteRestaurant;
import com.rezztoran.rezztoranbe.model.Restaurant;
import java.util.List;
import java.util.Optional;

public interface FavoriteRestaurantService {

  void addToFavorite(FavoriteRestaurantRequestModel requestModel);

  void removeFromFavorite(FavoriteRestaurantRequestModel requestModel);

  List<Restaurant> getFavoriteRestaurantsByUser(Long userId);

  Optional<FavoriteRestaurant> getFavoriteRestaurantByUserAndRestaurantId(
      Long userId, Long restaurantId);

  //  List<Restaurant> getMostFavoriteRestaurants();
}
