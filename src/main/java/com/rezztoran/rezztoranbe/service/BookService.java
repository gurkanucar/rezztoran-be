package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.BookDTO;
import com.rezztoran.rezztoranbe.dto.request.BookRequestModel;
import com.rezztoran.rezztoranbe.model.Booking;
import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.model.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/** The interface Book service. */
public interface BookService {

  /**
   * Gets book by id.
   *
   * @param id the id
   * @return the book by id
   */
  BookDTO getBookById(Long id);

  /**
   * Create book book dto.
   *
   * @param bookRequestModel the book request model
   * @return the book dto
   */
  BookDTO createBook(BookRequestModel bookRequestModel);

  /**
   * Update book book dto.
   *
   * @param bookRequestModel the book request model
   * @return the book dto
   */
  BookDTO updateBook(BookRequestModel bookRequestModel);

  void setBookReminderMailStatus(Long id, boolean status);

  /**
   * Gets books.
   *
   * @param bookingDate the booking date
   * @param restaurantId the restaurant id
   * @return the books
   */
  List<BookDTO> getBooks(LocalDate bookingDate, Long restaurantId);

  /**
   * Gets available time slots map.
   *
   * @param date the date
   * @param restaurantId the restaurant id
   * @return the available time slots map
   */
  Map<LocalTime, Boolean> getAvailableTimeSlotsMap(LocalDate date, Long restaurantId);

  /**
   * Delete book.
   *
   * @param id the id
   */
  void deleteBook(Long id);

  /**
   * Gets books by user.
   *
   * @param id the id
   * @return the books by user
   */
  List<BookDTO> getBooksByUser(Long id);

  /**
   * Gets books by user and date.
   *
   * @param id the id
   * @param date the date
   * @return the books by user and date
   */
  List<BookDTO> getBooksByUserAndDate(Long id, LocalDate date);

  List<Booking> findBookingsWithReminderCondition();

  void sendBookReminderEvent(Booking booking);
}
