package com.rezztoran.rezztoranbe.service.kafka.consumer;

import com.rezztoran.rezztoranbe.dto.BookDTO;

public interface BookingConsumer {
  void consumeBookingCreated(BookDTO booking);
}
