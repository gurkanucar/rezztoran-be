package com.rezztoran.rezztoranbe.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** The type Food. */
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Food extends BaseEntity {

  private String foodName;
  private String foodImage;

  @OneToOne private Category category;

  private Double price;
  private Double cal;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "restaurant_id", nullable = false)
  private Restaurant restaurant;
}
