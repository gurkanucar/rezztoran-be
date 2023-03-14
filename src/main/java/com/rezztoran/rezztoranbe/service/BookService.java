package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.request.BookRequestModel;
import com.rezztoran.rezztoranbe.model.Booking;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface BookService {

  Booking getBookById(Long id);

  Booking createBook(BookRequestModel bookRequestModel);

  Booking updateBook(BookRequestModel bookRequestModel);

  List<Booking> getBooks(LocalDate bookingDate, Long restaurantId);

  Map<LocalTime, Boolean> getAvailableTimeSlotsMap(LocalDate date, Long restaurantId);

  void deleteBook(Long id);

  List<Booking> getBooksByUser(Long id);

  List<Booking> getBooksByUserAndDate(Long id, LocalDate date);
}
