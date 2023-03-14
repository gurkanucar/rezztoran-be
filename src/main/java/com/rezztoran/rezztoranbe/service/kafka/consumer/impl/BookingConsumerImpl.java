package com.rezztoran.rezztoranbe.service.kafka.consumer.impl;

import com.rezztoran.rezztoranbe.dto.BookDTO;
import com.rezztoran.rezztoranbe.dto.request.MailModel;
import com.rezztoran.rezztoranbe.service.BookService;
import com.rezztoran.rezztoranbe.service.MailService;
import com.rezztoran.rezztoranbe.service.kafka.consumer.BookingConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookingConsumerImpl implements BookingConsumer {

  private final MailService mailService;
  private final BookService bookService;

  public BookingConsumerImpl(MailService mailService, BookService bookService) {
    this.mailService = mailService;
    this.bookService = bookService;
  }

  @KafkaListener(
      topics = "${spring.kafka.topics.book-created}",
      containerFactory = "bookingKafkaListenerContainerFactory",
      groupId = "group-id")
  @Override
  public void consumeBookingCreated(BookDTO booking) {
    mailService.sendBookCreatedMail(
        MailModel.builder().subject("Password Reset").to(booking.getUser().getMail()).build(),
        booking);
  }
}
