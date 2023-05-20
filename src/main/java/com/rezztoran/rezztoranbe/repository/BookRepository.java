package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.enums.BookingStatus;
import com.rezztoran.rezztoranbe.model.Booking;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** The interface Book repository. */
@Repository
public interface BookRepository extends JpaRepository<Booking, Long> {

  /**
   * Gets today bookings.
   *
   * @param today the today
   * @param hours the hours
   * @return the today bookings
   */
  @Query(
      value =
          "SELECT * FROM booking "
              + "WHERE reservation_date <= :today "
              + "AND reservation_time >= :hours "
              + "AND reminder_mail_sent = false",
      nativeQuery = true)
  List<Booking> getTodayBookings(LocalDate today, LocalTime hours);

  /**
   * Find all by user id and reservation date list.
   *
   * @param id the id
   * @param localDate the local date
   * @return the list
   */
  List<Booking> findAllByUser_IdAndReservationDate(Long id, LocalDate localDate);

  /**
   * Find all by user id list.
   *
   * @param id the id
   * @return the list
   */
  List<Booking> findAllByUser_Id(Long id);

  /**
   * Find all by restaurant id and reservation date and booking status list.
   *
   * @param id the id
   * @param localDate the local date
   * @param bookingStatus the booking status
   * @return the list
   */
  List<Booking> findAllByRestaurant_IdAndReservationDateAndBookingStatus(
      Long id, LocalDate localDate, BookingStatus bookingStatus);

  /**
   * Find all by restaurant id and reservation date and booking status not list.
   *
   * @param id the id
   * @param localDate the local date
   * @param bookingStatus the booking status
   * @return the list
   */
  List<Booking> findAllByRestaurant_IdAndReservationDateAndBookingStatusNot(
      Long id, LocalDate localDate, BookingStatus bookingStatus);

  /**
   * Find all by restaurant id and reservation date list.
   *
   * @param id the id
   * @param localDate the local date
   * @return the list
   */
  List<Booking> findAllByRestaurant_IdAndReservationDate(Long id, LocalDate localDate);
}
