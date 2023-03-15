package com.rezztoran.rezztoranbe.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rezztoran.rezztoranbe.enums.BookingStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/** The type Book request model. */
@Data
public class BookRequestModel {

  private Long id;

  @NotBlank private Long userId;

  @NotBlank private Long restaurantId;

  @NotBlank
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate reservationDate;

  @NotBlank
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private LocalTime reservationTime;

  private String note;

  private BookingStatus bookingStatus;
}
