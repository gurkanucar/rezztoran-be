package com.rezztoran.rezztoranbe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequestModel {

  private Long id;
  private Long restaurantId;
  private Long userId;
  private String content;
  private int star;
}
