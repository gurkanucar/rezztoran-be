package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.request.FavoriteRestaurantRequestModel;
import com.rezztoran.rezztoranbe.model.Restaurant;
import java.util.List;

public interface FavoriteRestaurantService {

  void addToFavorite(FavoriteRestaurantRequestModel requestModel);

  void removeFromFavorite(FavoriteRestaurantRequestModel requestModel);

  List<Restaurant> getFavoriteRestaurantsByUser(Long userId);

  //  List<Restaurant> getMostFavoriteRestaurants();
}
