package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.RestaurantTable;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {

  List<RestaurantTable> findAllByRestaurant_Id(Long restaurantId);

  @Query(
      "SELECT t FROM RestaurantTable t "
          + "WHERE t.restaurant.id = :restaurantId "
          + "AND :date NOT MEMBER OF t.busyDates "
          + "AND t.id NOT IN (SELECT r.restaurantTable.id FROM Reservation r "
          + "WHERE DATE(r.reservationTime) = :date) AND t.isAvailable = true")
  List<RestaurantTable> findAvailableTablesByDateAndRestaurant(
      @Param("restaurantId") Long restaurantId, @Param("date") Date date);
}
