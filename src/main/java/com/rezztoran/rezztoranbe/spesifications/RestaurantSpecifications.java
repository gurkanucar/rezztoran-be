package com.rezztoran.rezztoranbe.spesifications;

import com.rezztoran.rezztoranbe.model.Category;
import com.rezztoran.rezztoranbe.model.Food;
import com.rezztoran.rezztoranbe.model.Restaurant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/** The type Restaurant specifications. */
public class RestaurantSpecifications {

  /**
   * Search by city district or name specification.
   *
   * @param searchTerm the search term
   * @param city the city
   * @param restaurantName the restaurant name
   * @param district the district
   * @param availabilityDate the availability date
   * @param foodCategories the food categories
   * @return the specification
   */
  public static Specification<Restaurant> searchByCityDistrictOrName(
      String searchTerm,
      String city,
      String restaurantName,
      String district,
      LocalDate availabilityDate,
      List<String> foodCategories) {

    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      // filter deleted
      predicates.add(criteriaBuilder.isFalse(root.get("deleted")));

      if (StringUtils.isNotBlank(searchTerm)) {
        String likeTerm = "%" + searchTerm.toLowerCase() + "%";
        predicates.add(
            criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), likeTerm),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("district")), likeTerm),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("restaurantName")), likeTerm)));
      } else {
        if (StringUtils.isNotBlank(city)) {
          predicates.add(
              criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("city")), "%" + city.toLowerCase() + "%"));
        }
        if (StringUtils.isNotBlank(district)) {
          predicates.add(
              criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("district")), "%" + district.toLowerCase() + "%"));
        }
        if (StringUtils.isNotBlank(restaurantName)) {
          predicates.add(
              criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("restaurantName")),
                  "%" + restaurantName.toLowerCase() + "%"));
        }
      }

      if (availabilityDate != null) {
        Path<List<LocalDate>> datesPath = root.get("busyDates");
        predicates.add(criteriaBuilder.not(criteriaBuilder.isMember(availabilityDate, datesPath)));
      }

      if (foodCategories != null && !foodCategories.isEmpty()) {
        Join<Restaurant, Food> foodJoin = root.join("foods", JoinType.INNER);
        Join<Food, Category> categoryJoin = foodJoin.join("category", JoinType.INNER);

        Predicate categoryPredicate = categoryJoin.get("id").in(foodCategories);
        predicates.add(categoryPredicate);
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

  /**
   * Sort by selected fields specification.
   *
   * @param sortField the sort field
   * @param sortDirection the sort direction
   * @return the specification
   */
  public static Specification<Restaurant> sortBySelectedFields(
      String sortField, Sort.Direction sortDirection) {
    return (root, query, criteriaBuilder) -> {
      if (sortField.equalsIgnoreCase("restaurantName")) {
        query.orderBy(
            sortDirection == Sort.Direction.DESC
                ? criteriaBuilder.desc(root.get("restaurantName"))
                : criteriaBuilder.asc(root.get("restaurantName")));
      } else if (sortField.equalsIgnoreCase("reviewsCount")) {
        query.orderBy(
            sortDirection == Sort.Direction.DESC
                ? criteriaBuilder.desc(root.get("reviewsCount"))
                : criteriaBuilder.asc(root.get("reviewsCount")));
      } else if (sortField.equalsIgnoreCase("averageReviewStar")) {
        query.orderBy(
            sortDirection == Sort.Direction.DESC
                ? criteriaBuilder.desc(root.get("starCount"))
                : criteriaBuilder.asc(root.get("starCount")));
      }
      return query.getRestriction();
    };
  }
}
