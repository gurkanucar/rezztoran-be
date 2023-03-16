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

  List<Review> findAllByUser_Id(Long userId);

  List<Review> findAllByRestaurant_Id(Long restaurantId);


  @Query("SELECT e FROM Review e WHERE e.restaurant.id IN :restaurantIds")
  List<Review> findAllByRestaurantIds(@Param("restaurantIds") List<Long> restaurantIds);
}
