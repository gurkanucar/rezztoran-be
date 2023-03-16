package com.rezztoran.rezztoranbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {

  private Long id;
  private Long restaurantId;
  private String restaurantName;
  private String username;
  private Long userId;
  private String content;
  private int star;
}
