package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.dto.request.RestaurantTableRequestModel;
import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.RestaurantTable;
import com.rezztoran.rezztoranbe.repository.RestaurantTableRepository;
import com.rezztoran.rezztoranbe.service.RestaurantService;
import com.rezztoran.rezztoranbe.service.RestaurantTableService;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantTableServiceImpl implements RestaurantTableService {

  private final RestaurantTableRepository tableRepository;
  private final RestaurantService restaurantService;
  private final ExceptionUtil exceptionUtil;

  @Override
  public RestaurantTable getTableById(Long tableId) {
    return tableRepository
        .findById(tableId)
        .orElseThrow(() -> exceptionUtil.buildException(Ex.NOT_FOUND_EXCEPTION));
  }

  @Override
  public List<RestaurantTable> getTablesByRestaurant(Long restaurantId) {
    return tableRepository.findAllByRestaurant_Id(restaurantId);
  }

  @Override
  public List<RestaurantTable> getAvailableTablesByRestaurantAndDate(Long restaurantId, Date date) {
    return tableRepository.findAvailableTablesByDateAndRestaurant(restaurantId, date);
  }

  @Override
  public RestaurantTable createTable(RestaurantTableRequestModel requestModel) {
    RestaurantTable restaurantTable =
        RestaurantTable.builder()
            .restaurant(restaurantService.getById(requestModel.getRestaurantId()))
            .tableAttributes(requestModel.getTableAttributes())
            .capacity(requestModel.getCapacity())
            .availableDayTimeZone(requestModel.getAvailableDayTimeZone())
            .busyDates(requestModel.getBusyDates())
            .isAvailable(true)
            .build();
    return tableRepository.save(restaurantTable);
  }

  @Override
  public RestaurantTable updateTable(RestaurantTableRequestModel requestModel) {
    RestaurantTable existing = getTableById(requestModel.getId());
    existing.setRestaurant(restaurantService.getById(requestModel.getRestaurantId()));
    existing.setTableAttributes(requestModel.getTableAttributes());
    existing.setCapacity(requestModel.getCapacity());
    existing.setAvailableDayTimeZone(requestModel.getAvailableDayTimeZone());
    existing.setBusyDates(requestModel.getBusyDates());
    existing.setIsAvailable(requestModel.getIsAvailable());
    return tableRepository.save(existing);
  }

  @Override
  public void deleteTableById(Long id) {
    RestaurantTable existing = getTableById(id);
    tableRepository.delete(existing);
  }
}
