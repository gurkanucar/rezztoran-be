package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.RestaurantTable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {

  List<RestaurantTable> findAllByRestaurant(Long restaurantId);
}
