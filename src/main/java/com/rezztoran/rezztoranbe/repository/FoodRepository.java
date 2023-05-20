package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.Food;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** The interface Food repository. */
@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

  /**
   * Find by food name optional.
   *
   * @param foodName the food name
   * @return the optional
   */
  Optional<Food> findByFoodName(String foodName);

  /**
   * Find all by restaurant id page.
   *
   * @param restaurantId the restaurant id
   * @param pageable the pageable
   * @return the page
   */
  Page<Food> findAllByRestaurant_Id(Long restaurantId, Pageable pageable);
}
