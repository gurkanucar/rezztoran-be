package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.dto.request.BookRequestModel;
import com.rezztoran.rezztoranbe.enums.BookingStatus;
import com.rezztoran.rezztoranbe.model.Booking;
import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.repository.BookRepository;
import com.rezztoran.rezztoranbe.service.BookService;
import com.rezztoran.rezztoranbe.service.RestaurantService;
import com.rezztoran.rezztoranbe.service.UserService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
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

  @Override
  public Booking createBook(BookRequestModel bookRequestModel) {
    var user = userService.findUserByID(bookRequestModel.getUserId());
    var restaurant = restaurantService.getById(bookRequestModel.getRestaurantId());
    var availableTimes =
        getAvailableTimeSlotsMap(bookRequestModel.getReservationDate(), restaurant.getId());
    if (Boolean.TRUE.equals(availableTimes.get(bookRequestModel.getReservationTime()))) {
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

    return bookRepository.save(book);
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
    var bookings = getBooks(date, restaurantId);

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

  public boolean isBeforeOrEquals(LocalTime dateTime1, LocalTime dateTime2) {
    return !dateTime1.isAfter(dateTime2);
  }
}
