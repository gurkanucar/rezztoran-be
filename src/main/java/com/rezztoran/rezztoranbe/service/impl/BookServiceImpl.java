package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.dto.request.BookRequestModel;
import com.rezztoran.rezztoranbe.dto.request.MailModel;
import com.rezztoran.rezztoranbe.enums.BookingStatus;
import com.rezztoran.rezztoranbe.model.Booking;
import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.repository.BookRepository;
import com.rezztoran.rezztoranbe.service.BookService;
import com.rezztoran.rezztoranbe.service.MailService;
import com.rezztoran.rezztoranbe.service.RestaurantService;
import com.rezztoran.rezztoranbe.service.UserService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;
  private final RestaurantService restaurantService;
  private final UserService userService;
  private final MailService mailService;

  @Override
  public Booking createBook(BookRequestModel bookRequestModel) {
    var user = userService.findUserByID(bookRequestModel.getUserId());
    var restaurant = restaurantService.getById(bookRequestModel.getRestaurantId());
    if (!isAvailable(bookRequestModel, restaurant)) {
      throw new RuntimeException("could not book!");
    }
    var book =
        Booking.builder()
            .bookingStatus(BookingStatus.PENDING)
            .reservationDate(bookRequestModel.getReservationDate())
            .reservationTime(bookRequestModel.getReservationTime())
            .user(user)
            .restaurant(restaurant)
            .note(bookRequestModel.getNote())
            .build();

    var result = bookRepository.save(book);
    mailService.sendBookCreatedMail(
        MailModel.builder().subject("Password Reset").to(user.getMail()).build(), book);
    return result;
  }

  private boolean isAvailable(BookRequestModel bookRequestModel, Restaurant restaurant) {
    var availableTimes =
        getAvailableTimeSlotsMap(bookRequestModel.getReservationDate(), restaurant.getId());
    var result = availableTimes.get(bookRequestModel.getReservationTime());
    return !Objects.isNull(result) && Boolean.FALSE.equals(result);
  }

  @Override
  public Booking updateBook(BookRequestModel bookRequestModel) {
    var restaurant = restaurantService.getById(bookRequestModel.getRestaurantId());
    Booking existing =
        bookRepository
            .findById(bookRequestModel.getId())
            .orElseThrow(() -> new RuntimeException("book not found!"));

    if (!isSameDateTime(existing, bookRequestModel) && !isAvailable(bookRequestModel, restaurant)) {
      throw new RuntimeException("could not book!");
    }

    existing.setBookingStatus(bookRequestModel.getBookingStatus());
    existing.setReservationDate(bookRequestModel.getReservationDate());
    existing.setReservationTime(bookRequestModel.getReservationTime());
    existing.setNote(bookRequestModel.getNote());

    return bookRepository.save(existing);
  }

  @Override
  public List<Booking> getBooks(LocalDate bookingDate, Long restaurantId) {
    return bookRepository.findAllByRestaurant_IdAndReservationDate(restaurantId, bookingDate);
  }

  @Override
  public Map<LocalTime, Boolean> getAvailableTimeSlotsMap(LocalDate date, Long restaurantId) {
    // List<LocalTime> availableSlots = new ArrayList<>();
    Map<LocalTime, Boolean> availableSlots = new TreeMap<>();
    Restaurant restaurant = restaurantService.getById(restaurantId);
    int interval = restaurant.getIntervalMinutes() != 0 ? restaurant.getIntervalMinutes() : 30;
    var bookings =
        getBooksByDateAndRestaurantIdAndStatusIs(date, restaurantId, BookingStatus.PENDING);

    // Get the start and end time for the day
    LocalTime startDateTime = restaurant.getOpeningTime();
    LocalTime endDateTime = restaurant.getClosingTime();

    // Initialize the current time slot to the start time
    LocalTime currentSlot = startDateTime;

    while (isBeforeOrEquals(currentSlot, endDateTime)) {
      LocalTime tempSlot = currentSlot; // create a temporary variable

      if (bookings.stream().noneMatch(x -> tempSlot.equals(x.getReservationTime()))) {
        availableSlots.put(tempSlot, false); // add the temp variable to the list
      } else {
        availableSlots.put(tempSlot, true);
      }
      currentSlot = currentSlot.plusMinutes(interval);
    }

    return availableSlots;
  }

  // TODO check access
  @Override
  public void deleteBook(Long id) {
    Booking existing =
        bookRepository.findById(id).orElseThrow(() -> new RuntimeException("book not found!"));
    existing.setBookingStatus(BookingStatus.CANCELLED);
    bookRepository.save(existing);
  }

  @Override
  public List<Booking> getBooksByUser(Long id) {
    return bookRepository.findAllByUser_Id(id);
  }

  @Override
  public List<Booking> getBooksByUserAndDate(Long id, LocalDate date) {
    return bookRepository.findAllByUser_IdAndReservationDate(id, date);
  }

  public boolean isBeforeOrEquals(LocalTime dateTime1, LocalTime dateTime2) {
    return !dateTime1.isAfter(dateTime2);
  }

  public boolean isSameDateTime(Booking existing, BookRequestModel bookRequestModel) {
    return existing.getReservationDate().equals(bookRequestModel.getReservationDate())
        && existing.getReservationTime().equals(bookRequestModel.getReservationTime());
  }

  public List<Booking> getBooksByDateAndRestaurantIdAndStatusIs(
      LocalDate bookingDate, Long restaurantId, BookingStatus bookingStatus) {
    return bookRepository.findAllByRestaurant_IdAndReservationDateAndBookingStatus(
        restaurantId, bookingDate, bookingStatus);
  }

  public List<Booking> getBooksByDateAndRestaurantIdAndStatusIsNot(
      LocalDate bookingDate, Long restaurantId, BookingStatus bookingStatus) {
    return bookRepository.findAllByRestaurant_IdAndReservationDateAndBookingStatusNot(
        restaurantId, bookingDate, bookingStatus);
  }
}
