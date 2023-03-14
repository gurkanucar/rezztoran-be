package com.rezztoran.rezztoranbe.service.kafka.producer.impl;

import com.rezztoran.rezztoranbe.dto.BookDTO;
import com.rezztoran.rezztoranbe.service.kafka.producer.BookingProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookingProducerImpl implements BookingProducer {
  @Qualifier("BookingKafkaProducerFactory")
  private final KafkaTemplate<String, BookDTO> kafkaTemplate;
  @Value("${spring.kafka.topics.book-created}")
  String topicName;

  public BookingProducerImpl(KafkaTemplate<String, BookDTO> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void sendBookingCreatedMail(BookDTO booking) {
    kafkaTemplate.send(topicName, booking);
  }
}
