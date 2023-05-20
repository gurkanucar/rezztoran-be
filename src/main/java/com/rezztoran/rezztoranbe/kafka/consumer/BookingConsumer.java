package com.rezztoran.rezztoranbe.kafka.consumer;

import com.rezztoran.rezztoranbe.dto.BookDTO;

/** The interface Booking consumer. */
public interface BookingConsumer {
  /**
   * Consume booking created.
   *
   * @param booking the booking
   */
  void consumeBookingCreated(BookDTO booking);
  /**
   * Consume booking reminder.
   *
   * @param booking the booking
   */
  void consumeBookingReminder(BookDTO booking);
}
