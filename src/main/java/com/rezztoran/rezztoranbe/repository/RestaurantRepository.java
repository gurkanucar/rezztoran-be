package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.model.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** The interface Restaurant repository. */
@Repository
public interface RestaurantRepository
    extends JpaRepository<Restaurant, Long>, JpaSpecificationExecutor<Restaurant> {

  Page<Restaurant> findAll(@NonNull Pageable pageable);

  /**
   * Find all randomly list.
   *
   * @param count the count
   * @return the list
   */
  @Query(value = "SELECT * FROM restaurant ORDER BY RAND() LIMIT :elementCount", nativeQuery = true)
  List<Restaurant> findAllRandomly(@Param("elementCount") int count);

  Page<Restaurant> findAll(Specification<Restaurant> spec, Pageable pageable);

  /**
   * Find all by city list.
   *
   * @param city the city
   * @return the list
   */
  List<Restaurant> findAllByCity(String city);

  /**
   * Find all by city and district order by created date time list.
   *
   * @param city the city
   * @param distrcit the distrcit
   * @return the list
   */
  List<Restaurant> findAllByCityAndDistrictOrderByCreatedDateTime(String city, String distrcit);

  /**
   * Find restaurant by menu names list.
   *
   * @param name the name
   * @return the list
   */
  @Query(nativeQuery = true, value = "SELECT * from RESTAURANT where restaurant_name= :name")
  List<Restaurant> findRestaurantByMenuNames(String name);

  /**
   * Find restaurant by restaurant name optional.
   *
   * @param name the name
   * @return the optional
   */
  Optional<Restaurant> findRestaurantByRestaurantName(String name);

  /**
   * Find restaurant by user optional.
   *
   * @param user the user
   * @return the optional
   */
  Optional<Restaurant> findRestaurantByUser(User user);

  List<Restaurant> findAll();

  /**
   * Find all map map.
   *
   * @return the map
   */
  default Map<Object, Object> findAllMap() {
    return findAll().stream()
        .collect(Collectors.toMap(Restaurant::getRestaurantName, Restaurant::getCity));
  }
}
