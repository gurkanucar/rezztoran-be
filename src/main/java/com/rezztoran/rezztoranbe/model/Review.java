package com.rezztoran.rezztoranbe.model;

import com.rezztoran.rezztoranbe.service.ReviewCountAndRatingListener;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** The type Review. */
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(ReviewCountAndRatingListener.class)
public class Review extends BaseEntity {

  private String content;
  private int star;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "restaurant_id", nullable = false)
  private Restaurant restaurant;
}
