package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.repository.RestaurantRepository;
import com.rezztoran.rezztoranbe.service.impl.UserServiceImpl;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

  private final RestaurantRepository restaurantRepository;
  private final UserServiceImpl userService;
  private final ExceptionUtil exceptionUtil;

  public List<Restaurant> getRestaurants() {
    var restaurants = restaurantRepository.findAll();
    restaurants.forEach(x -> x.setMenu(null));
    return restaurants;
  }

  public Restaurant create(Restaurant restaurant) {
    if (doesRestaurantExistByName(restaurant)) {
      throw exceptionUtil.buildException(Ex.RESTAURANT_ALREADY_EXISTS_EXCEPTION);
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

  @Transactional
  public Restaurant update(Restaurant restaurant) {
    var existing = getById(restaurant.getId());
    existing.setCity(restaurant.getCity());
    existing.setDistrict(restaurant.getDistrict());
    existing.setLongitude(restaurant.getLongitude());
    existing.setLatitude(restaurant.getLatitude());
    existing.setRestaurantImage(restaurant.getRestaurantImage());
    existing.setRestaurantImageList(restaurant.getRestaurantImageList());
    existing.setDetailedAddress(restaurant.getDetailedAddress());
    existing.setMenu(restaurant.getMenu());
    existing.setPhone(restaurant.getPhone());
    existing.setBookingAvailable(restaurant.getBookingAvailable());
    existing.setOpeningTime(restaurant.getOpeningTime());
    existing.setClosingTime(restaurant.getClosingTime());
    existing.setIntervalMinutes(restaurant.getIntervalMinutes());
    existing.setBusyDates(restaurant.getBusyDates());
    existing.setRestaurantAttributes(restaurant.getRestaurantAttributes());
    return restaurantRepository.save(existing);
  }

  public Restaurant updateOwner(Restaurant restaurant) {
    var existing = getById(restaurant.getId());
    if (doesRestaurantExistByUser(restaurant)) {
      throw exceptionUtil.buildException(Ex.USER_ALREADY_OWNER_OF_A_RESTAURANT_EXCEPTION);
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
        .orElseThrow(() -> exceptionUtil.buildException(Ex.RESTAURANT_NOT_FOUND_EXCEPTION));
  }

  public void delete(Long id) {
    var restaurant = getById(id);
    restaurantRepository.delete(restaurant);
  }
}
