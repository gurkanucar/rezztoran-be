package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.request.FavoriteRestaurantRequestModel;
import com.rezztoran.rezztoranbe.model.FavoriteRestaurant;
import com.rezztoran.rezztoranbe.model.Restaurant;
import java.util.List;
import java.util.Optional;

/** The interface Favorite restaurant service. */
public interface FavoriteRestaurantService {

  /**
   * Add to favorite.
   *
   * @param requestModel the request model
   */
  void addToFavorite(FavoriteRestaurantRequestModel requestModel);

  /**
   * Remove from favorite.
   *
   * @param requestModel the request model
   */
  void removeFromFavorite(FavoriteRestaurantRequestModel requestModel);

  /**
   * Gets favorite restaurants by user.
   *
   * @param userId the user id
   * @return the favorite restaurants by user
   */
  List<Restaurant> getFavoriteRestaurantsByUser(Long userId);

  /**
   * Gets favorite restaurant by user and restaurant id.
   *
   * @param userId the user id
   * @param restaurantId the restaurant id
   * @return the favorite restaurant by user and restaurant id
   */
  Optional<FavoriteRestaurant> getFavoriteRestaurantByUserAndRestaurantId(
      Long userId, Long restaurantId);

  //  List<Restaurant> getMostFavoriteRestaurants();
}
