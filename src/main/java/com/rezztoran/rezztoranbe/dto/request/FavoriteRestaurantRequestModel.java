package com.rezztoran.rezztoranbe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Favorite restaurant request model.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteRestaurantRequestModel {

  private Long id;
  private Long userId;
  private Long restaurantId;
}
