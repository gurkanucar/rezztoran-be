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
import org.springframework.data.domain.Page;
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

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
@Slf4j
public class RestaurantController {

  private final RestaurantService restaurantService;
  private final BookService bookService;
  private final ModelMapper mapper;

  @GetMapping
  public Page<Restaurant> search(
      @RequestParam(required = false) String city,
      @RequestParam(required = false) String restaurantName,
      @RequestParam(required = false) String district,
      @RequestParam(required = false, defaultValue = "desc") String sortDirection,
      @RequestParam(defaultValue = "restaurantName") String sortBy,
      @PageableDefault(size = 20) Pageable pageable) {
    Specification<Restaurant> specification =
        RestaurantSpecifications.searchAndSortByFields(city, restaurantName, district, sortBy,sortDirection);
    return restaurantService.getRestaurants(specification, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<RestaurantDTO> getById(@PathVariable Long id) {
    var response = mapper.map(restaurantService.getById(id), RestaurantDTO.class);
    Optional.ofNullable(response.getMenu()).ifPresent((x) -> x.setRestaurant(null));
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<RestaurantDTO> create(@RequestBody Restaurant restaurant) {
    var response = mapper.map(restaurantService.create(restaurant), RestaurantDTO.class);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/insert-list")
  public ResponseEntity<List<RestaurantDTO>> create(@RequestBody List<Restaurant> restaurants) {
    return ResponseEntity.ok().build();
  }

  @PutMapping
  public ResponseEntity<RestaurantDTO> update(@RequestBody Restaurant restaurant) {
    var response = mapper.map(restaurantService.update(restaurant), RestaurantDTO.class);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/update-owner")
  public ResponseEntity<RestaurantDTO> updateOwner(@RequestBody Restaurant restaurant) {
    var response = mapper.map(restaurantService.updateOwner(restaurant), RestaurantDTO.class);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    restaurantService.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{id}/book")
  public ResponseEntity<ApiResponse<Object>> getBooksByRestaurantIdAndDate(
      @PathVariable Long id,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate) {
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{id}/book/slots")
  public ResponseEntity<ApiResponse<Object>> getTablesByRestaurantIdAndDate(
      @PathVariable Long id,
      @RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate) {
    return ApiResponse.builder().data(bookService.getAvailableTimeSlotsMap(localDate, id)).build();
  }
}
