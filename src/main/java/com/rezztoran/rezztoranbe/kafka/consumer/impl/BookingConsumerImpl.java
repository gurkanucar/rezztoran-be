package com.rezztoran.rezztoranbe.kafka.consumer.impl;

import com.rezztoran.rezztoranbe.dto.BookDTO;
import com.rezztoran.rezztoranbe.dto.request.MailModel;
import com.rezztoran.rezztoranbe.kafka.consumer.BookingConsumer;
import com.rezztoran.rezztoranbe.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/** The type Booking consumer. */
@Service
@Slf4j
public class BookingConsumerImpl implements BookingConsumer {

  private final MailService mailService;

  /**
   * Instantiates a new Booking consumer.
   *
   * @param mailService the mail service
   */
  public BookingConsumerImpl(MailService mailService) {
    this.mailService = mailService;
  }

  @KafkaListener(
      topics = "${spring.kafka.topics.book-created}",
      containerFactory = "bookingKafkaListenerContainerFactory",
      groupId = "group-id")
  @Override
  public void consumeBookingCreated(BookDTO booking) {
    mailService.sendBookCreatedMail(
        MailModel.builder().subject("Book Created").to(booking.getUser().getMail()).build(),
        booking);
  }

  @KafkaListener(
      topics = "${spring.kafka.topics.book-reminder}",
      containerFactory = "bookingKafkaListenerContainerFactory",
      groupId = "group-id")
  @Override
  public void consumeBookingReminder(BookDTO booking) {
    mailService.sendBookReminderMail(
        MailModel.builder().subject("Book Reminder").to(booking.getUser().getMail()).build(),
        booking);
  }

   @KafkaListener(
      topics = "${spring.kafka.topics.book-cancelled-restaurant}",
      containerFactory = "bookingKafkaListenerContainerFactory",
      groupId = "group-id")
  @Override
  public void consumeBookingCancelledByRestaurant(BookDTO booking) {
     mailService.sendBookCancelledByRestaurantMail(
         MailModel.builder().subject("Book Cancelled :(").to(booking.getUser().getMail()).build(),
         booking);
  }
}
