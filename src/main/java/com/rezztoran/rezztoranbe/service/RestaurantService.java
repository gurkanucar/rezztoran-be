package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.exception.BusinessException.Exception;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.repository.RestaurantRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

  private final RestaurantRepository restaurantRepository;
  private final UserService userService;
  private final ExceptionUtil exceptionUtil;

  public List<Restaurant> getRestaurants() {
    var restaurants = restaurantRepository.findAll();
    restaurants.forEach(x -> x.setMenu(null));
    return restaurants;
  }

  public Restaurant create(Restaurant restaurant) {
    if (doesRestaurantExistByName(restaurant)) {
      throw exceptionUtil.buildException(Exception.RESTAURANT_ALREADY_EXISTS_EXCEPTION);
    }
    return restaurantRepository.save(restaurant);
  }

  public List<Restaurant> create(List<Restaurant> restaurants) {
    restaurants.forEach(
        x -> {
          if (!doesRestaurantExistByName(x)) {
            create(x);
          }
        });
    return getRestaurants();
  }

  public Restaurant update(Restaurant restaurant) {
    var existing = getById(restaurant.getId());
    existing.setCity(restaurant.getCity());
    existing.setDistrict(restaurant.getDistrict());
    existing.setLongitude(restaurant.getLongitude());
    existing.setLatitude(restaurant.getLatitude());
    existing.setRestaurantImageList(restaurant.getRestaurantImageList());
    existing.setDetailedAddress(restaurant.getDetailedAddress());
    existing.setMenu(restaurant.getMenu());
    existing.setPhone(restaurant.getPhone());
    return restaurantRepository.save(existing);
  }

  public Restaurant updateOwner(Restaurant restaurant) {
    var existing = getById(restaurant.getId());
    if (doesRestaurantExistByUser(restaurant)) {
      throw exceptionUtil.buildException(Exception.USER_ALREADY_OWNER_OF_A_RESTAURANT_EXCEPTION);
    }
    var user = userService.findUserByID(restaurant.getUser().getId());
    existing.setUser(user);
    return restaurantRepository.save(existing);
  }

  public boolean doesRestaurantExistByName(Restaurant restaurant) {
    return restaurantRepository
        .findRestaurantByRestaurantName(restaurant.getRestaurantName())
        .isPresent();
  }

  public boolean doesRestaurantExistByUser(Restaurant restaurant) {
    return restaurantRepository.findRestaurantByUser(restaurant.getUser()).isPresent();
  }

  public Restaurant getById(Long id) {
    return restaurantRepository
        .findById(id)
        .orElseThrow(() -> exceptionUtil.buildException(Exception.RESTAURANT_NOT_FOUND_EXCEPTION));
  }

  public void delete(Long id) {
    var restaurant = getById(id);
    restaurantRepository.delete(restaurant);
  }
}
