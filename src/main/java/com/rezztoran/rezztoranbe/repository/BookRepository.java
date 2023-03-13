package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.enums.BookingStatus;
import com.rezztoran.rezztoranbe.model.Booking;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Booking, Long> {

  List<Booking> findAllByUser_IdAndReservationDate(Long id, LocalDate localDate);
  List<Booking> findAllByUser_Id(Long id);

  List<Booking> findAllByRestaurant_IdAndReservationDateAndBookingStatus(
      Long id, LocalDate localDate, BookingStatus bookingStatus);

  List<Booking> findAllByRestaurant_IdAndReservationDateAndBookingStatusNot(
      Long id, LocalDate localDate, BookingStatus bookingStatus);

  List<Booking> findAllByRestaurant_IdAndReservationDate(Long id, LocalDate localDate);
}
