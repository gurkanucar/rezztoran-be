package com.rezztoran.rezztoranbe.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantTable extends BaseEntity {

  private Boolean isAvailable;

  private TimeOfDay availableDayTimeZone;

  private Integer capacity;

  @ElementCollection
  @CollectionTable(name = "busy_dates", joinColumns = @JoinColumn(name = "id"))
  private List<LocalDate> busyDates;

  @ManyToOne
  @JoinColumn(name = "restaurant_id")
  private Restaurant restaurant;

  @ElementCollection
  @MapKeyColumn(name = "name")
  @Column(name = "value")
  @CollectionTable(name = "table_attributes", joinColumns = @JoinColumn(name = "table_id"))
  private Map<String, String> tableAttributes = new HashMap<>();
}
