package com.rezztoran.rezztoranbe.service.kafka.producer;

import com.rezztoran.rezztoranbe.dto.BookDTO;

public interface BookingProducer {

  void sendBookingCreatedMail(BookDTO booking);
}
