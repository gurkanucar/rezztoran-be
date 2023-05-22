package com.rezztoran.rezztoranbe.kafka.producer;

import com.rezztoran.rezztoranbe.dto.ReviewDTO;

/** The interface Password reset mail producer. */
public interface RestaurantStarReviewCountProducer {

  void updateValues(ReviewDTO restaurantReviewStarDTO);
}
