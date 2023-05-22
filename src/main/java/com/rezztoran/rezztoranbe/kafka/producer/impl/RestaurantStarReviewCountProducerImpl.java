package com.rezztoran.rezztoranbe.kafka.producer.impl;

import com.rezztoran.rezztoranbe.dto.ReviewDTO;
import com.rezztoran.rezztoranbe.kafka.producer.RestaurantStarReviewCountProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/** The type Restaurant star review count producer. */
@Service
@Slf4j
public class RestaurantStarReviewCountProducerImpl implements RestaurantStarReviewCountProducer {
  @Qualifier("ReviewFactory")
  private final KafkaTemplate<String, ReviewDTO> kafkaTemplate;

  /** The Topic name. */
  @Value("${spring.kafka.topics.review-update}")
  String topicName;

  /**
   * Instantiates a new Restaurant star review count producer.
   *
   * @param kafkaTemplate the kafka template
   */
  public RestaurantStarReviewCountProducerImpl(KafkaTemplate<String, ReviewDTO> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void updateValues(ReviewDTO reviewDTO) {
    kafkaTemplate.send(topicName, reviewDTO);
  }
}
