package com.rezztoran.rezztoranbe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private RestaurantDTO restaurant;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private UserDTO user;

  private String note;

  private Integer personCount;
}
