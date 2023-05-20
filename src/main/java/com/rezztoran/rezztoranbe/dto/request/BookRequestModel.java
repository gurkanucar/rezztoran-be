package com.rezztoran.rezztoranbe.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rezztoran.rezztoranbe.enums.BookingStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

/** The type Book request model. */
@Data
public class BookRequestModel {

  private Long id;

  @NotNull private Long userId;

  @NotNull private Long restaurantId;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate reservationDate;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private LocalTime reservationTime;

  private String note = "";
  @NotNull private String phone;

  private BookingStatus bookingStatus;

  @Min(1)
  @Max(50)
  @NotNull
  private Integer personCount;
}
