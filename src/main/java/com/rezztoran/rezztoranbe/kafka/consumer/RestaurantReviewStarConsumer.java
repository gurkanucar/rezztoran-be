package com.rezztoran.rezztoranbe.kafka.consumer;

import com.rezztoran.rezztoranbe.dto.ReviewDTO;

/** The interface Password reset mail consumer. */
public interface RestaurantReviewStarConsumer {
  /**
   * Consume password reset mail.
   *
   * @param passwordResetMail the password reset mail
   */
  void consume(ReviewDTO reviewDTO);
}
