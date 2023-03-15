package com.rezztoran.rezztoranbe.service.kafka.consumer;

import com.rezztoran.rezztoranbe.dto.BookDTO;

/**
 * The interface Booking consumer.
 */
public interface BookingConsumer {
  /**
   * Consume booking created.
   *
   * @param booking the booking
   */
void consumeBookingCreated(BookDTO booking);
}
