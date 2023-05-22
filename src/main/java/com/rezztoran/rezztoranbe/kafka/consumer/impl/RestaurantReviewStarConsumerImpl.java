package com.rezztoran.rezztoranbe.kafka.consumer.impl;

import com.rezztoran.rezztoranbe.dto.ReviewDTO;
import com.rezztoran.rezztoranbe.kafka.consumer.RestaurantReviewStarConsumer;
import com.rezztoran.rezztoranbe.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/** The type Password reset mail consumer. */
@Service
@Slf4j
public class RestaurantReviewStarConsumerImpl implements RestaurantReviewStarConsumer {

  private final RestaurantService restaurantService;

  public RestaurantReviewStarConsumerImpl(RestaurantService restaurantService) {
    this.restaurantService = restaurantService;
  }

  @KafkaListener(
      topics = "${spring.kafka.topics.review-update}",
      containerFactory = "reviewKafkaListenerContainerFactory",
      groupId = "group-id")
  @Override
  public void consume(ReviewDTO reviewDTO) {
    restaurantService.updateReviewCountAndStar(reviewDTO);
  }
}
