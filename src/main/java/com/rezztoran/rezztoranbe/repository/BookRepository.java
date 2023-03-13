package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.Booking;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Booking, Long> {

  List<Booking> findAllByRestaurant_IdAndReservationDate(Long id, LocalDate localDate);
}
