package com.rezztoran.rezztoranbe.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookRequestModel {

  @NotBlank private Long userId;

  @NotBlank private Long restaurantId;

  @NotBlank
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate reservationDate;

  @NotBlank
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private LocalTime reservationTime;

  private String note;
}
