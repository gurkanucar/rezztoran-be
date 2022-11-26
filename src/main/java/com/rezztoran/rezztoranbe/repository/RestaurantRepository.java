package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {


    List<Restaurant> findAllByCity(String city);

    List<Restaurant> findAllByCityAndDistrictOrderByCreatedDateTime(String city, String distrcit);

    @Query(nativeQuery = true, value = "SELECT * from RESTAURANT where restaurant_name= :name")
    List<Restaurant> findRestaurantByMenuNames(String name);

}
