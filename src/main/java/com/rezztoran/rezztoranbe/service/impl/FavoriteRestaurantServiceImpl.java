package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.dto.request.FavoriteRestaurantRequestModel;
import com.rezztoran.rezztoranbe.model.FavoriteRestaurant;
import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.repository.FavoriteRestaurantRepository;
import com.rezztoran.rezztoranbe.service.FavoriteRestaurantService;
import com.rezztoran.rezztoranbe.service.RestaurantService;
import com.rezztoran.rezztoranbe.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FavoriteRestaurantServiceImpl implements FavoriteRestaurantService {

  private final FavoriteRestaurantRepository favoriteRestaurantRepository;
  private final UserService userService;
  private final RestaurantService restaurantService;

  @Override
  public void addToFavorite(FavoriteRestaurantRequestModel requestModel) {
    var existing =
        favoriteRestaurantRepository.findByRestaurant_IdAndUser_Id(
            requestModel.getRestaurantId(), requestModel.getUserId());
    var user = userService.findUserByID(requestModel.getUserId());
    var restaurant = restaurantService.getById(requestModel.getRestaurantId());
    if (existing.isEmpty()) {
      favoriteRestaurantRepository.save(
          FavoriteRestaurant.builder().user(user).restaurant(restaurant).build());
    }
  }

  @Override
  public void removeFromFavorite(FavoriteRestaurantRequestModel requestModel) {
    var existing =
        favoriteRestaurantRepository.findByRestaurant_IdAndUser_Id(
            requestModel.getRestaurantId(), requestModel.getUserId());
    existing.ifPresent(
        favoriteRestaurant -> favoriteRestaurantRepository.deleteById(favoriteRestaurant.getId()));
  }

  @Override
  public List<Restaurant> getFavoriteRestaurantsByUser(Long userId) {
    var result = favoriteRestaurantRepository.findAllByUser_Id(userId);
    return result.stream().map(FavoriteRestaurant::getRestaurant).collect(Collectors.toList());
  }
}
