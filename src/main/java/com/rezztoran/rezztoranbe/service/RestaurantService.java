package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.BookDTO;
import com.rezztoran.rezztoranbe.dto.RestaurantDTO;
import com.rezztoran.rezztoranbe.dto.ReviewDTO;
import com.rezztoran.rezztoranbe.model.Restaurant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/** The interface Restaurant service. */
public interface RestaurantService {

  /**
   * Gets restaurants.
   *
   * @param searchTerm the search term
   * @param sortField the sort field
   * @param sortDirection the sort direction
   * @param city the city
   * @param restaurantName the restaurant name
   * @param district the district
   * @param categories the categories
   * @param localDate the local date
   * @param pageable the pageable
   * @return the restaurants
   */
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

  /**
   * Generate qr code for restaurant byte [ ].
   *
   * @param id the id
   * @return the byte [ ]
   */
  byte[] generateQrCodeForRestaurant(Long id);

  /**
   * Update review count and star.
   *
   * @param reviewDTO the review dto
   */
  void updateReviewCountAndStar(ReviewDTO reviewDTO);

  /**
   * Gets books of restaurant.
   *
   * @param localDate the local date
   * @param id the id
   * @return the books of restaurant
   */
  List<BookDTO> getBooksOfRestaurant(LocalDate localDate, Long id);

  /**
   * Gets time slots list.
   *
   * @param localDate the local date
   * @param id the id
   * @return the time slots list
   */
  List<Map.Entry<LocalTime, Boolean>> getTimeSlotsList(LocalDate localDate, Long id);

  /**
   * Get total RESTAURANT count.
   *
   */
  long getCount();
}
