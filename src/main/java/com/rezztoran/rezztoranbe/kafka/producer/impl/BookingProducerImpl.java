package com.rezztoran.rezztoranbe.kafka.producer.impl;

import com.rezztoran.rezztoranbe.dto.BookDTO;
import com.rezztoran.rezztoranbe.kafka.producer.BookingProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/** The type Booking producer. */
@Service
@Slf4j
public class BookingProducerImpl implements BookingProducer {
  @Qualifier("BookingKafkaProducerFactory")
  private final KafkaTemplate<String, BookDTO> kafkaTemplate;

  /** The Topic name created. */
  @Value("${spring.kafka.topics.book-created}")
  String topicNameCreated;

  /** The Topic name reminder. */
  @Value("${spring.kafka.topics.book-reminder}")
  String topicNameReminder;

  /** The Topic name cancelled by restaurant. */
  @Value("${spring.kafka.topics.book-cancelled-restaurant}")
  String topicNameCancelledByRestaurant;

  /**
   * Instantiates a new Booking producer.
   *
   * @param kafkaTemplate the kafka template
   */
  public BookingProducerImpl(KafkaTemplate<String, BookDTO> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void sendBookingCreatedMail(BookDTO booking) {
    kafkaTemplate.send(topicNameCreated, booking);
  }

  @Override
  public void sendBookingReminderMail(BookDTO booking) {
    kafkaTemplate.send(topicNameReminder, booking);
  }

  @Override
  public void sendBookCancelledByRestaurantMail(BookDTO booking) {
    kafkaTemplate.send(topicNameCancelledByRestaurant, booking);
  }
}
