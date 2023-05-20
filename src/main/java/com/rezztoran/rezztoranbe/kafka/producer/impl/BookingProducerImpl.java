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

  /** The Topic name. */
  @Value("${spring.kafka.topics.book-created}")
  String topicNameCreated;

  @Value("${spring.kafka.topics.book-reminder}")
  String topicNameReminder;

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
}
