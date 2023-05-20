package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.Food;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** The interface Food repository. */
@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

  Optional<Food> findByFoodName(String foodName);
  List<Food> findAllByRestaurant_Id(Long restaurantId);

}
