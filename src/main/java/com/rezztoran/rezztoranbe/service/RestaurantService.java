package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.RestaurantDTO;
import com.rezztoran.rezztoranbe.model.Restaurant;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface RestaurantService {

  Page<RestaurantDTO> getRestaurants(
      String searchTerm,
      String sortField,
      Sort.Direction sortDirection,
      String city,
      String restaurantName,
      String district,
      List<String> categories,
      LocalDate localDate,
      Pageable pageable);
  /**
   * Gets restaurants randomly.
   *
   * @param count the count
   * @return the restaurants randomly
   */
  List<RestaurantDTO> getRestaurantsRandomly(int count);
  /**
   * Create restaurant.
   *
   * @param restaurant the restaurant
   * @return the restaurant
   */
  Restaurant create(Restaurant restaurant);
  /**
   * Create.
   *
   * @param restaurants the restaurants
   */
  void create(List<Restaurant> restaurants);
  /**
   * Update restaurant.
   *
   * @param restaurant the restaurant
   * @return the restaurant
   */
  Restaurant update(Restaurant restaurant);
  /**
   * Update owner restaurant.
   *
   * @param restaurant the restaurant
   * @return the restaurant
   */
  Restaurant updateOwner(Restaurant restaurant);

  /**
   * Does restaurant exist by name boolean.
   *
   * @param restaurant the restaurant
   * @return the boolean
   */
  boolean doesRestaurantExistByName(Restaurant restaurant);
  /**
   * Does restaurant exist by user boolean.
   *
   * @param restaurant the restaurant
   * @return the boolean
   */
  boolean doesRestaurantExistByUser(Restaurant restaurant);
  /**
   * Gets by id.
   *
   * @param id the id
   * @return the by id
   */
  Restaurant getById(Long id);

  /**
   * Gets by id dto.
   *
   * @param id the id
   * @return the by id dto
   */
  RestaurantDTO getByIdDto(Long id);

  /**
   * Delete.
   *
   * @param id the id
   */
  void delete(Long id);
}
