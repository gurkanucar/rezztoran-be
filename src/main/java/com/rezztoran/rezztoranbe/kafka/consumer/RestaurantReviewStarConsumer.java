package com.rezztoran.rezztoranbe.kafka.consumer;

import com.rezztoran.rezztoranbe.dto.ReviewDTO;

/** The interface Restaurant review star consumer. */
public interface RestaurantReviewStarConsumer {
  /**
   * Consume.
   *
   * @param reviewDTO the review dto
   */
  void consume(ReviewDTO reviewDTO);
}
