package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.dto.ReviewDTO;
import com.rezztoran.rezztoranbe.kafka.producer.RestaurantStarReviewCountProducer;
import com.rezztoran.rezztoranbe.model.Review;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** The type Review count and rating listener. */
@Slf4j
@Component
public class ReviewCountAndRatingListener { // implements ApplicationContextAware {

  private final RestaurantStarReviewCountProducer producer;

  /**
   * Instantiates a new Review count and rating listener.
   *
   * @param producer the producer
   */
  public ReviewCountAndRatingListener(RestaurantStarReviewCountProducer producer) {
    this.producer = producer;
  }

  //
  //  private static ApplicationContext context;
  //
  //  @Override
  //  public void setApplicationContext(ApplicationContext applicationContext) {
  //    context = applicationContext;
  //  }

  /**
   * Handle review save or update.
   *
   * @param review the review
   */
  @PostPersist
  @PostUpdate
  // @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handleReviewSaveOrUpdate(Review review) {
    calculateAverageStarAndReviewCount(review, false);
  }

  /**
   * Handle review delete.
   *
   * @param review the review
   */
  @PostRemove
  // @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handleReviewDelete(Review review) {
    calculateAverageStarAndReviewCount(review, true);
  }

  /**
   * Calculate average star and review count.
   *
   * @param review the review
   * @param isDeleted the is deleted
   */
  public void calculateAverageStarAndReviewCount(Review review, boolean isDeleted) {
    // RestaurantService restaurantService = context.getBean(RestaurantService.class);
    producer.updateValues(
        ReviewDTO.builder()
            .id(review.getId())
            .star(review.getStar())
            .restaurantId(review.getRestaurant().getId())
            .deleted(isDeleted)
            .build());
  }
}
