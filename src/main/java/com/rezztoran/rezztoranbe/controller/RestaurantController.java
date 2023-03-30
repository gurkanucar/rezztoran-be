package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.RestaurantDTO;
import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.BookService;
import com.rezztoran.rezztoranbe.service.RestaurantService;
import com.rezztoran.rezztoranbe.service.spesifications.RestaurantSpecifications;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** The type Restaurant controller. */
@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
@Slf4j
public class RestaurantController {

  private final RestaurantService restaurantService;
  private final BookService bookService;
  private final ModelMapper mapper;

  /**
   * Search response entity.
   *
   * @param city the city
   * @param restaurantName the restaurant name
   * @param district the district
   * @param sortDirection the sort direction
   * @param sortBy the sort by
   * @param pageable the pageable
   * @return the response entity
   */
  @GetMapping
  public ResponseEntity<ApiResponse<Object>> search(
      @RequestParam(required = false) String city,
      @RequestParam(required = false) String restaurantName,
      @RequestParam(required = false) String district,
      @RequestParam(required = false, defaultValue = "desc") String sortDirection,
      @RequestParam(defaultValue = "restaurantName") String sortBy,
      @PageableDefault(size = 20) Pageable pageable) {
    Specification<Restaurant> specification =
        RestaurantSpecifications.searchAndSortByFields(
            city, restaurantName, district, sortBy, sortDirection);
    var response = restaurantService.getRestaurants(specification, pageable);
    return ApiResponse.builder().data(response.getContent()).build();
  }

  /**
   * Gets by id.
   *
   * @param id the id
   * @return the by id
   */
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> getById(@PathVariable Long id) {
    var response = restaurantService.getByIdDto(id);
    Optional.ofNullable(response.getMenu()).ifPresent(x -> x.setRestaurant(null));
    return ApiResponse.builder().data(response).build();
  }

  /**
   * Create response entity.
   *
   * @param restaurant the restaurant
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<ApiResponse<Object>> create(@RequestBody Restaurant restaurant) {
    var response = mapper.map(restaurantService.create(restaurant), RestaurantDTO.class);

    return ApiResponse.builder().data(response).build();
  }

  /**
   * Create response entity.
   *
   * @param restaurants the restaurants
   * @return the response entity
   */
  @PostMapping("/insert-list")
  public ResponseEntity<ApiResponse<Object>> create(@RequestBody List<Restaurant> restaurants) {
    restaurantService.create(restaurants);
    return ApiResponse.builder().build();
  }

  /**
   * Update response entity.
   *
   * @param restaurant the restaurant
   * @return the response entity
   */
  @PutMapping
  public ResponseEntity<ApiResponse<Object>> update(@RequestBody Restaurant restaurant) {
    var response = mapper.map(restaurantService.update(restaurant), RestaurantDTO.class);
    return ApiResponse.builder().data(response).build();
  }

  /**
   * Update owner response entity.
   *
   * @param restaurant the restaurant
   * @return the response entity
   */
  @PutMapping("/update-owner")
  public ResponseEntity<ApiResponse<Object>> updateOwner(@RequestBody Restaurant restaurant) {
    var response = mapper.map(restaurantService.updateOwner(restaurant), RestaurantDTO.class);
    return ApiResponse.builder().data(response).build();
  }

  /**
   * Delete response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
    restaurantService.delete(id);
    return ApiResponse.builder().build();
  }

  /**
   * Gets books by restaurant id and date.
   *
   * @param id the id
   * @param localDate the local date
   * @return the books by restaurant id and date
   */
  @GetMapping("/{id}/book")
  public ResponseEntity<ApiResponse<Object>> getBooksByRestaurantIdAndDate(
      @PathVariable Long id,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate) {
    var response = bookService.getBooks(localDate, id);
    return ApiResponse.builder().data(response).build();
  }

  /**
   * Gets tables by restaurant id and date.
   *
   * @param id the id
   * @param localDate the local date
   * @return the tables by restaurant id and date
   */
  @GetMapping("/{id}/book/slots")
  public ResponseEntity<ApiResponse<Object>> getTablesByRestaurantIdAndDate(
      @PathVariable Long id,
      @RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate) {
    return ApiResponse.builder().data(bookService.getAvailableTimeSlotsMap(localDate, id)).build();
  }
}
