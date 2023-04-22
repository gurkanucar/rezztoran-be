package com.rezztoran.rezztoranbe.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Coupon extends BaseEntity{


  @Column(nullable = false, unique = true)
  private String code;

  @Column(nullable = false)
  private Integer capacity;

  @Column(nullable = false)
  private Integer usage;

  @Column(nullable = false)
  private LocalDate expiryDate;

}