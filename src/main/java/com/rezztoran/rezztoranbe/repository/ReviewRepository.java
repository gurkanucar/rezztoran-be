package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** The interface Review repository. */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  /**
   * Find all by user id list.
   *
   * @param userId the user id
   * @return the list
   */
  List<Review> findAllByUser_Id(Long userId);

  /**
   * Find all by restaurant id list.
   *
   * @param restaurantId the restaurant id
   * @return the list
   */
  List<Review> findAllByRestaurant_Id(Long restaurantId);

  /**
   * Find all by restaurant ids list.
   *
   * @param restaurantIds the restaurant ids
   * @return the list
   */
  @Query("SELECT e FROM Review e WHERE e.restaurant.id IN :restaurantIds")
  List<Review> findAllByRestaurantIds(@Param("restaurantIds") List<Long> restaurantIds);

  /**
   * Exists by user id and restaurant id boolean.
   *
   * @param userId the user id
   * @param restaurantId the restaurant id
   * @return the boolean
   */
  Boolean existsByUser_IdAndRestaurant_Id(Long userId, Long restaurantId);
}
