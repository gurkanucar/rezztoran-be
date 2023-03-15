package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.FavoriteRestaurant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** The interface Favorite restaurant repository. */
@Repository
public interface FavoriteRestaurantRepository extends JpaRepository<FavoriteRestaurant, Long> {

  /**
   * Find by restaurant id and user id optional.
   *
   * @param restaurantId the restaurant id
   * @param userId the user id
   * @return the optional
   */
  Optional<FavoriteRestaurant> findByRestaurant_IdAndUser_Id(Long restaurantId, Long userId);

  /**
   * Find all by user id list.
   *
   * @param userId the user id
   * @return the list
   */
  List<FavoriteRestaurant> findAllByUser_Id(Long userId);
}
