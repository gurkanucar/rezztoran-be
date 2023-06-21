package com.rezztoran.rezztoranbe.kafka.producer;

import com.rezztoran.rezztoranbe.dto.BookDTO;

/** The interface Booking producer. */
public interface BookingProducer {

  /**
   * Send booking created mail.
   *
   * @param booking the booking
   */
  void sendBookingCreatedMail(BookDTO booking);

  /**
   * Send booking reminder mail.
   *
   * @param booking the booking
   */
  void sendBookingReminderMail(BookDTO booking);

  /**
   * Send book cancelled by restaurant mail.
   *
   * @param booking the booking
   */
  void sendBookCancelledByRestaurantMail(BookDTO booking);
}
