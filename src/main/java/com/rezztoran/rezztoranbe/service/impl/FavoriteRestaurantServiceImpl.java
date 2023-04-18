package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.dto.RestaurantDTO;
import com.rezztoran.rezztoranbe.dto.request.FavoriteRestaurantRequestModel;
import com.rezztoran.rezztoranbe.model.FavoriteRestaurant;
import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.repository.FavoriteRestaurantRepository;
import com.rezztoran.rezztoranbe.service.FavoriteRestaurantService;
import com.rezztoran.rezztoranbe.service.RestaurantService;
import com.rezztoran.rezztoranbe.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** The type Favorite restaurant service. */
@Service
@Slf4j
public class FavoriteRestaurantServiceImpl implements FavoriteRestaurantService {

  private final FavoriteRestaurantRepository favoriteRestaurantRepository;
  private final UserService userService;
  private final RestaurantService restaurantService;

  /**
   * Instantiates a new Favorite restaurant service.
   *
   * @param favoriteRestaurantRepository the favorite restaurant repository
   * @param userService the user service
   * @param restaurantService the restaurant service
   */
  public FavoriteRestaurantServiceImpl(
      FavoriteRestaurantRepository favoriteRestaurantRepository,
      UserService userService,
      RestaurantService restaurantService) {
    this.favoriteRestaurantRepository = favoriteRestaurantRepository;
    this.userService = userService;
    this.restaurantService = restaurantService;
  }

  private static RestaurantDTO convertToRestaurantDTO(FavoriteRestaurant x) {
    return RestaurantDTO.builder()
        .id(x.getRestaurant().getId())
        .restaurantName(x.getRestaurant().getRestaurantName())
        .restaurantImage(x.getRestaurant().getRestaurantImage())
        .restaurantImageList(x.getRestaurant().getRestaurantImageList())
        .city(x.getRestaurant().getCity())
        .district(x.getRestaurant().getDistrict())
        .detailedAddress(x.getRestaurant().getDetailedAddress())
        .latitude(x.getRestaurant().getLatitude())
        .longitude(x.getRestaurant().getLongitude())
        .openingTime(x.getRestaurant().getOpeningTime())
        .closingTime(x.getRestaurant().getClosingTime())
        .phone(x.getRestaurant().getPhone())
        .build();
  }

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

  @Override
  public List<RestaurantDTO> getFavoriteRestaurantsDTOByUser(Long userId) {
    var result = favoriteRestaurantRepository.findAllByUser_Id(userId);
    return result.stream()
        .map(FavoriteRestaurantServiceImpl::convertToRestaurantDTO)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<FavoriteRestaurant> getFavoriteRestaurantByUserAndRestaurantId(
      Long userId, Long restaurantId) {
    return favoriteRestaurantRepository.findByRestaurant_IdAndUser_Id(restaurantId, userId);
  }
}
