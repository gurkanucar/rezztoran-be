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
import javax.persistence.MapKeyColumn;
import lombok.Data;

@Data
@Entity
public class Table extends BaseEntity {

  private Boolean isAvailable;

  private TimeOfDay availableDayTimeZone;

  private Integer capacity;

  @ElementCollection
  @CollectionTable(name = "busy_dates", joinColumns = @JoinColumn(name = "id"))
  private List<LocalDate> busy_dates;

  @ElementCollection
  @MapKeyColumn(name = "name")
  @Column(name = "value")
  @CollectionTable(name = "table_attributes", joinColumns = @JoinColumn(name = "table_id"))
  private Map<String, String> tableAttributes = new HashMap<>();
}
