package com.rezztoran.rezztoranbe.service.spesifications;

import com.rezztoran.rezztoranbe.model.Restaurant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

/**
 * The type Restaurant specifications.
 */
public class RestaurantSpecifications {
  /**
   * Search specification.
   *
   * @param value the value
   * @return the specification
   */
public static Specification<Restaurant> search(String value) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.like(root.get("title"), "%" + value + "%");
  }

  /**
   * Search and sort by fields specification.
   *
   * @param city the city
   * @param restaurantName the restaurant name
   * @param district the district
   * @param sortBy the sort by
   * @param sortDirection the sort direction
   * @return the specification
   */
public static Specification<Restaurant> searchAndSortByFields(
      String city, String restaurantName, String district, String sortBy, String sortDirection) {
    return (root, query, builder) -> {
      if (sortBy != null) {

        boolean descending = sortDirection.equalsIgnoreCase("desc");
        List<Order> orders = new ArrayList<>();
        if (descending) {
          orders.add(builder.desc(root.get(sortBy)));
        } else {
          orders.add(builder.asc(root.get(sortBy)));
        }
        query.orderBy(orders);
      }

      Predicate predicate = builder.conjunction();

      if (city != null) {
        predicate =
            builder.and(
                predicate,
                builder.like(builder.lower(root.get("city")), "%" + city.toLowerCase() + "%"));
      }

      if (restaurantName != null) {
        predicate =
            builder.and(
                predicate,
                builder.like(
                    builder.lower(root.get("restaurantName")),
                    "%" + restaurantName.toLowerCase() + "%"));
      }

      if (district != null) {
        predicate = builder.and(predicate, builder.equal(root.get("district"), district));
      }

      return predicate;
    };
  }

  //    public static Specification<Restaurant> searchByFields(
  //        String city, String restaurantName, String district) {
  //      return (root, query, builder) -> {
  //        List<Order> orders = new ArrayList<>();
  //        orders.add(builder.desc(root.get("starCount")));
  //        query.orderBy(orders);
  //        return builder.and(
  //            builder.like(builder.lower(root.get("city")), "%" + city.toLowerCase() + "%"),
  //            builder.like(
  //                builder.lower(root.get("restaurantName")),
  //                "%" + restaurantName.toLowerCase() + "%"),
  //            builder.equal(root.get("district"), district));
  //      };
  //    }
}
