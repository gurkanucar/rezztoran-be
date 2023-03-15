package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.FavoriteRestaurant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRestaurantRepository extends JpaRepository<FavoriteRestaurant, Long> {

  Optional<FavoriteRestaurant> findByRestaurant_IdAndUser_Id(Long restaurantId, Long userId);

  List<FavoriteRestaurant> findAllByUser_Id(Long userId);
}
