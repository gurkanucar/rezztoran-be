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

  void sendBookingReminderMail(BookDTO booking);
}
