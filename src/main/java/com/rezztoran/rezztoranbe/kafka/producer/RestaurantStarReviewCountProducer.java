package com.rezztoran.rezztoranbe.kafka.producer;

import com.rezztoran.rezztoranbe.dto.ReviewDTO;

/** The interface Restaurant star review count producer. */
public interface RestaurantStarReviewCountProducer {

  /**
   * Update values.
   *
   * @param restaurantReviewStarDTO the restaurant review star dto
   */
  void updateValues(ReviewDTO restaurantReviewStarDTO);
}
