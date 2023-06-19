package com.rezztoran.rezztoranbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Analytics dto. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDto {
  private long restaurantCount;
  private long userCount;
  private long reviewCount;
  private long foodCount;
  private long bookingCount;
}
