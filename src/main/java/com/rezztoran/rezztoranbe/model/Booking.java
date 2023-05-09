package com.rezztoran.rezztoranbe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rezztoran.rezztoranbe.enums.BookingStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** The type Booking. */
@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Booking extends BaseEntity {

  @Enumerated(EnumType.STRING)
  private BookingStatus bookingStatus;

  private LocalDate reservationDate;
  private LocalTime reservationTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "restaurant_id")
  private Restaurant restaurant;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  private String note;

  private Integer personCount;
}
