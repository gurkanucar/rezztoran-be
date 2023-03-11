package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.RestaurantTableDTO;
import com.rezztoran.rezztoranbe.dto.request.RestaurantTableRequestModel;
import com.rezztoran.rezztoranbe.model.RestaurantTable;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.RestaurantTableService;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
  private final ModelMapper modelMapper;

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> getTableById(@PathVariable Long id) {
    var restaurantTable = tableService.getTableById(id);
    RestaurantTableDTO restaurantTableDTO = convertDto(restaurantTable);
    return ApiResponse.builder().data(restaurantTableDTO).build();
  }

  @GetMapping("/restaurant/{id}")
  public ResponseEntity<ApiResponse<Object>> getTablesByRestaurantIdAndDate(
      @PathVariable Long id,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date localDate) {
    if (localDate != null) {
      List<RestaurantTable> restaurantTables =
          tableService.getAvailableTablesByRestaurantAndDate(id, localDate);
      return convertDto(restaurantTables);
    }
    List<RestaurantTable> restaurantTables = tableService.getTablesByRestaurant(id);
    return convertDto(restaurantTables);
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Object>> createTable(
      @RequestBody RestaurantTableRequestModel requestModel) {
    var restaurantTable = tableService.createTable(requestModel);
    RestaurantTableDTO restaurantTableDTO = convertDto(restaurantTable);
    return ApiResponse.builder().data(restaurantTableDTO).build();
  }

  @PutMapping
  public ResponseEntity<ApiResponse<Object>> updateTableById(
      @RequestBody RestaurantTableRequestModel requestModel) {
    var restaurantTable = tableService.updateTable(requestModel);
    RestaurantTableDTO restaurantTableDTO = convertDto(restaurantTable);
    return ApiResponse.builder().data(restaurantTableDTO).build();
  }

  private RestaurantTableDTO convertDto(RestaurantTable restaurantTable) {
    RestaurantTableDTO restaurantTableDTO =
        modelMapper.map(restaurantTable, RestaurantTableDTO.class);
    restaurantTableDTO.setRestaurantId(restaurantTable.getRestaurant().getId());
    return restaurantTableDTO;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteTableById(@PathVariable Long id) {
    tableService.deleteTableById(id);
    return ApiResponse.builder().build();
  }

  private ResponseEntity<ApiResponse<Object>> convertDto(List<RestaurantTable> restaurantTables) {
    var restaurantTableDtos =
        restaurantTables.stream().map(this::convertDto).collect(Collectors.toList());
    return ApiResponse.builder().data(restaurantTableDtos).build();
  }
}
