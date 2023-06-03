package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.dto.ReviewDTO;
import com.rezztoran.rezztoranbe.model.Review;
import com.rezztoran.rezztoranbe.service.RestaurantService;
import javax.persistence.EntityManager;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class ReviewCountAndRatingListener implements ApplicationContextAware {

  private static ApplicationContext context;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    context = applicationContext;
  }

  @PostPersist
  @PostUpdate
  @PostRemove
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void calculateAverageStarAndReviewCount(Review review) {

    EntityManager entityManager = context.getBean(EntityManager.class);
    boolean deleted = !entityManager.contains(review);

    RestaurantService restaurantService = context.getBean(RestaurantService.class);
    restaurantService.updateReviewCountAndStar(
        ReviewDTO.builder()
            .id(review.getId())
            .star(review.getStar())
            .restaurantId(review.getRestaurant().getId())
            .deleted(deleted)
            .build());
  }
}
