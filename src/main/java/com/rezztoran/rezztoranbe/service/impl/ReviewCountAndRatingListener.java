package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.dto.ReviewDTO;
import com.rezztoran.rezztoranbe.kafka.producer.RestaurantStarReviewCountProducer;
import com.rezztoran.rezztoranbe.model.Review;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReviewCountAndRatingListener { // implements ApplicationContextAware {

  private final RestaurantStarReviewCountProducer producer;

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

  @PostPersist
  @PostUpdate
  // @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handleReviewSaveOrUpdate(Review review) {
    calculateAverageStarAndReviewCount(review, false);
  }

  @PostRemove
  // @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handleReviewDelete(Review review) {
    calculateAverageStarAndReviewCount(review, true);
  }

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
