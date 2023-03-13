package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.request.BookRequestModel;
import com.rezztoran.rezztoranbe.model.Booking;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookService {

  Booking createBook(BookRequestModel bookRequestModel);

  List<Booking> getBooks(LocalDate bookingDate, Long restaurantId);

  List<LocalTime> getAvailableTimeSlots(LocalDate date, Long restaurantId);
}
