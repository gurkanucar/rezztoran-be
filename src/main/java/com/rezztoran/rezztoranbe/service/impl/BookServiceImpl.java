package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.dto.BookDTO;
import com.rezztoran.rezztoranbe.dto.RestaurantDTO;
import com.rezztoran.rezztoranbe.dto.UserDTO;
import com.rezztoran.rezztoranbe.dto.request.BookRequestModel;
import com.rezztoran.rezztoranbe.enums.BookingStatus;
import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.kafka.producer.BookingProducer;
import com.rezztoran.rezztoranbe.model.Booking;
import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.model.User;
import com.rezztoran.rezztoranbe.repository.BookRepository;
import com.rezztoran.rezztoranbe.service.BookService;
import com.rezztoran.rezztoranbe.service.RestaurantService;
import com.rezztoran.rezztoranbe.service.UserService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** The type Book service. */
@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;
  private final RestaurantService restaurantService;
  private final UserService userService;
  private final ExceptionUtil exceptionUtil;
  private final BookingProducer bookingProducer;

  @Override
  public Booking getBookById(Long id) {
    return bookRepository
        .findById(id)
        .orElseThrow(() -> exceptionUtil.buildException(Ex.BOOK_NOT_FOUND_EXCEPTION));
  }

  @Override
  public Booking createBook(BookRequestModel bookRequestModel) {
    var user = userService.findUserByID(bookRequestModel.getUserId());
    var restaurant = restaurantService.getById(bookRequestModel.getRestaurantId());
    if (!isAvailable(bookRequestModel, restaurant)) {
      throw exceptionUtil.buildException(Ex.AVAILABLE_BOOK_NOT_FOUND_EXCEPTION);
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

    sendBookCreatedEvent(user, restaurant, result);
    return result;
  }

  private boolean isAvailable(BookRequestModel bookRequestModel, Restaurant restaurant) {
    var availableTimes =
        getAvailableTimeSlotsMap(bookRequestModel.getReservationDate(), restaurant.getId());
    var result = availableTimes.get(bookRequestModel.getReservationTime());
    return !Objects.isNull(result)
        && Boolean.FALSE.equals(result)
        && restaurant.getBookingAvailable();
  }

  @Override
  public Booking updateBook(BookRequestModel bookRequestModel) {
    var restaurant = restaurantService.getById(bookRequestModel.getRestaurantId());
    Booking existing =
        bookRepository
            .findById(bookRequestModel.getId())
            .orElseThrow(() -> exceptionUtil.buildException(Ex.BOOK_NOT_FOUND_EXCEPTION));

    if (!isSameDateTime(existing, bookRequestModel) && !isAvailable(bookRequestModel, restaurant)) {
      throw exceptionUtil.buildException(Ex.COULD_NOT_BOOK_EXCEPTION);
    }

    existing.setBookingStatus(bookRequestModel.getBookingStatus());
    existing.setReservationDate(bookRequestModel.getReservationDate());
    existing.setReservationTime(bookRequestModel.getReservationTime());
    existing.setNote(bookRequestModel.getNote());

    return bookRepository.save(existing);
  }

  @Override
  public List<BookDTO> getBooks(LocalDate bookingDate, Long restaurantId) {
    return bookRepository
        .findAllByRestaurant_IdAndReservationDate(restaurantId, bookingDate)
        .stream()
        .map(x -> convertToBookDTO(x, true, false))
        .collect(Collectors.toList());
  }

  private static BookDTO convertToBookDTO(
      Booking x, boolean restaurantLeaveEmpty, boolean userLeaveEmpty) {
    return BookDTO.builder()
        .id(x.getId())
        .bookingStatus(x.getBookingStatus())
        .reservationDate(x.getReservationDate())
        .reservationTime(x.getReservationTime())
        .restaurant(
            !restaurantLeaveEmpty
                ? RestaurantDTO.builder()
                    .id(x.getRestaurant().getId())
                    .restaurantName(x.getRestaurant().getRestaurantName())
                    .restaurantImage(x.getRestaurant().getRestaurantImage())
                    .restaurantImageList(x.getRestaurant().getRestaurantImageList())
                    .city(x.getRestaurant().getCity())
                    .district(x.getRestaurant().getDistrict())
                    .detailedAddress(x.getRestaurant().getDetailedAddress())
                    .latitude(x.getRestaurant().getLatitude())
                    .longitude(x.getRestaurant().getLongitude())
                    .openingTime(x.getRestaurant().getOpeningTime())
                    .closingTime(x.getRestaurant().getClosingTime())
                    .phone(x.getRestaurant().getPhone())
                    .build()
                : null)
        .user(
            !userLeaveEmpty
                ? UserDTO.builder()
                    .id(x.getUser().getId())
                    .username(x.getUser().getUsername())
                    .name(x.getUser().getName())
                    .surname(x.getUser().getSurname())
                    .mail(x.getUser().getMail())
                    .build()
                : null)
        .note(x.getNote())
        .build();
  }

  @Override
  public Map<LocalTime, Boolean> getAvailableTimeSlotsMap(LocalDate date, Long restaurantId) {
    Map<LocalTime, Boolean> availableSlots = new TreeMap<>();
    Restaurant restaurant = restaurantService.getById(restaurantId);

    var busyDates = restaurant.getBusyDates();
    if (busyDates.contains(date) || Boolean.TRUE.equals(!restaurant.getBookingAvailable())) {
      throw exceptionUtil.buildException(Ex.AVAILABLE_BOOK_NOT_FOUND_EXCEPTION);
    }

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
        bookRepository
            .findById(id)
            .orElseThrow(() -> exceptionUtil.buildException(Ex.BOOK_NOT_FOUND_EXCEPTION));
    existing.setBookingStatus(BookingStatus.CANCELLED);
    bookRepository.save(existing);
  }

  @Override
  public List<BookDTO> getBooksByUser(Long id) {
    return bookRepository.findAllByUser_Id(id).stream()
        .map(x -> convertToBookDTO(x, false, true))
        .collect(Collectors.toList());
  }

  @Override
  public List<BookDTO> getBooksByUserAndDate(Long id, LocalDate date) {
    return bookRepository.findAllByUser_IdAndReservationDate(id, date).stream()
        .map(x -> convertToBookDTO(x, false, true))
        .collect(Collectors.toList());
  }

  /**
   * Is before or equals boolean.
   *
   * @param dateTime1 the date time 1
   * @param dateTime2 the date time 2
   * @return the boolean
   */
  public boolean isBeforeOrEquals(LocalTime dateTime1, LocalTime dateTime2) {
    return !dateTime1.isAfter(dateTime2);
  }

  /**
   * Is same date time boolean.
   *
   * @param existing the existing
   * @param bookRequestModel the book request model
   * @return the boolean
   */
  public boolean isSameDateTime(Booking existing, BookRequestModel bookRequestModel) {
    return existing.getReservationDate().equals(bookRequestModel.getReservationDate())
        && existing.getReservationTime().equals(bookRequestModel.getReservationTime());
  }

  /**
   * Gets books by date and restaurant id and status is.
   *
   * @param bookingDate the booking date
   * @param restaurantId the restaurant id
   * @param bookingStatus the booking status
   * @return the books by date and restaurant id and status is
   */
  public List<Booking> getBooksByDateAndRestaurantIdAndStatusIs(
      LocalDate bookingDate, Long restaurantId, BookingStatus bookingStatus) {
    return bookRepository.findAllByRestaurant_IdAndReservationDateAndBookingStatus(
        restaurantId, bookingDate, bookingStatus);
  }

  /**
   * Gets books by date and restaurant id and status is not.
   *
   * @param bookingDate the booking date
   * @param restaurantId the restaurant id
   * @param bookingStatus the booking status
   * @return the books by date and restaurant id and status is not
   */
  public List<Booking> getBooksByDateAndRestaurantIdAndStatusIsNot(
      LocalDate bookingDate, Long restaurantId, BookingStatus bookingStatus) {
    return bookRepository.findAllByRestaurant_IdAndReservationDateAndBookingStatusNot(
        restaurantId, bookingDate, bookingStatus);
  }

  private void sendBookCreatedEvent(User user, Restaurant restaurant, Booking result) {
    var restaurantDto =
        RestaurantDTO.builder()
            .id(restaurant.getId())
            .restaurantName(restaurant.getRestaurantName())
            .city(restaurant.getCity())
            .latitude(restaurant.getLatitude())
            .longitude(restaurant.getLongitude())
            .build();

    var userDto =
        UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .mail(user.getMail())
            .name(user.getName())
            .surname(user.getSurname())
            .build();

    var bookDto =
        BookDTO.builder()
            .id(result.getId())
            .bookingStatus(result.getBookingStatus())
            .reservationDate(result.getReservationDate())
            .reservationTime(result.getReservationTime())
            .user(userDto)
            .restaurant(restaurantDto)
            .note(result.getNote())
            .build();

    bookingProducer.sendBookingCreatedMail(bookDto);
  }
}
