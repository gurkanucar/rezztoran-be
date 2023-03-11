package com.rezztoran.rezztoranbe.dto;

import com.rezztoran.rezztoranbe.model.TimeOfDay;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class RestaurantTableDTO {

  private Long id;

  private Boolean isAvailable;

  private TimeOfDay availableDayTimeZone;

  private Integer capacity;

  private List<LocalDate> busyDates;

  private Long restaurantId;

  private Map<String, String> tableAttributes;
}
