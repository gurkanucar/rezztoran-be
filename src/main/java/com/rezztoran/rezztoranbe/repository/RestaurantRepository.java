package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.model.User;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository
    extends JpaRepository<Restaurant, Long>, JpaSpecificationExecutor<Restaurant> {

  Page<Restaurant> findAll(@NonNull Pageable pageable);

  Page<Restaurant> findAll(Specification<Restaurant> spec, Pageable pageable);

  List<Restaurant> findAllByCity(String city);

  List<Restaurant> findAllByCityAndDistrictOrderByCreatedDateTime(String city, String distrcit);

  @Query(nativeQuery = true, value = "SELECT * from RESTAURANT where restaurant_name= :name")
  List<Restaurant> findRestaurantByMenuNames(String name);

  Optional<Restaurant> findRestaurantByRestaurantName(String name);

  Optional<Restaurant> findRestaurantByUser(User user);
}
