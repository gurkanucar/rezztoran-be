package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

  List<Restaurant> findAllByCity(String city);

  List<Restaurant> findAllByCityAndDistrictOrderByCreatedDateTime(String city, String distrcit);

  @Query(nativeQuery = true, value = "SELECT * from RESTAURANT where restaurant_name= :name")
  List<Restaurant> findRestaurantByMenuNames(String name);

  Optional<Restaurant> findRestaurantByRestaurantName(String name);

  Optional<Restaurant> findRestaurantByUser(User user);
}
