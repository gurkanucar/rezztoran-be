package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.ReviewDTO;
import com.rezztoran.rezztoranbe.kafka.producer.RestaurantStarReviewCountProducer;
import com.rezztoran.rezztoranbe.model.Review;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** The type Review count and rating listener. */
@Service
@Slf4j
public class ReviewCountAndRatingListener {

  private final RestaurantStarReviewCountProducer producer;

  /**
   * Instantiates a new Review count and rating listener.
   *
   * @param producer the producer
   */
  public ReviewCountAndRatingListener(RestaurantStarReviewCountProducer producer) {
    this.producer = producer;
  }

  /**
   * Calculate average star and review count.
   *
   * @param review the review
   */
  @PostPersist
  @PostUpdate
  @PostRemove
  public void calculateAverageStarAndReviewCount(Review review) {
    producer.updateValues(
        ReviewDTO.builder()
            .id(review.getId())
            .star(review.getStar())
            .restaurantId(review.getRestaurant().getId())
            .build());
  }
}
