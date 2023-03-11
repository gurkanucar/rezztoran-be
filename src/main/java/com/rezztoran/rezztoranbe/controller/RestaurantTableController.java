package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.request.RestaurantTableRequestModel;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.RestaurantTableService;
import java.util.Date;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/restaurant-table")
@RequiredArgsConstructor
public class RestaurantTableController {

  private final RestaurantTableService tableService;

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> getTableById(@PathVariable Long id) {
    return ApiResponse.builder().data(tableService.getTableById(id)).build();
  }

  @GetMapping("/restaurant/{id}")
  public ResponseEntity<ApiResponse<Object>> getTablesByRestaurantIdAndDate(
      @PathVariable Long id,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date localDate) {
    if (localDate != null) {
      return ApiResponse.builder()
          .data(tableService.getAvailableTablesByRestaurantAndDate(id, localDate))
          .build();
    }
    return ApiResponse.builder().data(tableService.getTablesByRestaurant(id)).build();
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Object>> createTable(
      @RequestBody RestaurantTableRequestModel requestModel) {
    return ApiResponse.builder().data(tableService.createTable(requestModel)).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> updateTableById(
      @RequestBody RestaurantTableRequestModel requestModel) {
    return ApiResponse.builder().data(tableService.createTable(requestModel)).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteTableById(@PathVariable Long id) {
    tableService.deleteTableById(id);
    return ApiResponse.builder().build();
  }
}
