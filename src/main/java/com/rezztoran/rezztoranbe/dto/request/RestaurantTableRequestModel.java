package com.rezztoran.rezztoranbe.dto.request;

import com.rezztoran.rezztoranbe.enums.TimeOfDay;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class RestaurantTableRequestModel {

  private Long id;

  private Boolean isAvailable;

  private TimeOfDay availableDayTimeZone;

  private Integer capacity;

  private List<Date> busyDates;

  private Long restaurantId;

  private Map<String, String> tableAttributes;
}
