package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.request.RestaurantTableRequestModel;
import com.rezztoran.rezztoranbe.model.RestaurantTable;
import java.time.LocalDate;
import java.util.List;

public interface RestaurantTableService {

  RestaurantTable getTableById(Long tableId);

  List<RestaurantTable> getTablesByRestaurant(Long restaurantId);

  List<RestaurantTable> getTablesByRestaurantAndDate(Long restaurantId, LocalDate date);

  RestaurantTable createTable(RestaurantTableRequestModel requestModel);

  RestaurantTable updateTable(RestaurantTableRequestModel requestModel);

  void deleteTableById(Long id);
}
