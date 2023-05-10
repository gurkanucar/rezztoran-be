package com.rezztoran.rezztoranbe.service.spesifications;

import com.rezztoran.rezztoranbe.model.Restaurant;
import com.rezztoran.rezztoranbe.model.Review;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/** The type Restaurant specifications. */
public class RestaurantSpecifications {

  public static Specification<Restaurant> searchByCityDistrictOrName(
      String searchTerm, String city, String restaurantName, String district) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

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

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

  public static Specification<Restaurant> sortBySelectedFields(
      String sortField, Sort.Direction sortDirection) {
    return (root, query, criteriaBuilder) -> {
      Join<Restaurant, Review> reviewJoin = root.join("reviews", JoinType.LEFT);
      query.groupBy(root.get("id"));

      if (sortField.equals("restaurantName")) {
        if (sortDirection.isAscending()) {
          query.orderBy(criteriaBuilder.asc(root.get("restaurantName")));
        } else {
          query.orderBy(criteriaBuilder.desc(root.get("restaurantName")));
        }
      } else if (sortField.equals("reviewsCount")) {
        if (sortDirection.isAscending()) {
          query.orderBy(criteriaBuilder.asc(criteriaBuilder.count(reviewJoin)));
        } else {
          query.orderBy(criteriaBuilder.desc(criteriaBuilder.count(reviewJoin)));
        }
      } else if (sortField.equals("averageReviewStar")) {
        if (sortDirection.isAscending()) {
          query.orderBy(criteriaBuilder.asc(criteriaBuilder.avg(reviewJoin.get("star"))));
        } else {
          query.orderBy(criteriaBuilder.desc(criteriaBuilder.avg(reviewJoin.get("star"))));
        }
      }

      return query.getRestriction();
    };
  }
}
