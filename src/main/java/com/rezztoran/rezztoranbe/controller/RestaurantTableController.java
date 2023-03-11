package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.RestaurantTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurant-table")
@RequiredArgsConstructor
public class RestaurantTableController {

  private final RestaurantTableService tableService;

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> getTableById(@PathVariable Long id) {
    return ApiResponse.builder().build();
  }

  @GetMapping("/restaurant/{id}")
  public ResponseEntity<ApiResponse<Object>> getTablesByRestaurantId(@PathVariable Long id) {
    return ApiResponse.builder().build();
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Object>> createTable() {
    return ApiResponse.builder().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> updateTableById(@PathVariable Long id) {
    return ApiResponse.builder().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteTableById(@PathVariable Long id) {
    return ApiResponse.builder().build();
  }
}
