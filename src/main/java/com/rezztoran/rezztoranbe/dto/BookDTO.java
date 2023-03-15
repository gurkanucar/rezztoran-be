package com.rezztoran.rezztoranbe.dto;

import com.rezztoran.rezztoranbe.enums.BookingStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Book dto. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

  private Long id;
  private BookingStatus bookingStatus;

  private LocalDate reservationDate;
  private LocalTime reservationTime;

  private RestaurantDTO restaurant;

  private UserDTO user;

  private String note;
}
