package com.rezztoran.rezztoranbe.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Favorite restaurant.
 */
@Data
@Entity
@Table(name = "favorite_restaurants")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteRestaurant extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "restaurant_id", nullable = false)
  private Restaurant restaurant;
}
