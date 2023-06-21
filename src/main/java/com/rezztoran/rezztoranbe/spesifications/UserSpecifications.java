package com.rezztoran.rezztoranbe.spesifications;

import com.rezztoran.rezztoranbe.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/** The type User specifications. */
public class UserSpecifications {

  /**
   * Search by username name surname or email specification.
   *
   * @param searchTerm the search term
   * @param userId the user id
   * @return the specification
   */
  public static Specification<User> searchByUsernameNameSurnameOrEmail(
      String searchTerm, Long userId) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (StringUtils.isNotBlank(searchTerm)) {
        String likeTerm = "%" + searchTerm.toLowerCase() + "%";
        predicates.add(
            criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), likeTerm),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), likeTerm),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("surname")), likeTerm),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("mail")), likeTerm)));
      }

      if (userId != null) {
        predicates.add(criteriaBuilder.notEqual(root.get("id"), userId));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

  /**
   * Sort by field specification.
   *
   * @param sortField the sort field
   * @param sortDirection the sort direction
   * @return the specification
   */
  public static Specification<User> sortByField(String sortField, Sort.Direction sortDirection) {
    return (root, query, criteriaBuilder) -> {
      if (sortField.equals("name")) {
        if (sortDirection.isAscending()) {
          query.orderBy(criteriaBuilder.asc(root.get("name")));
        } else {
          query.orderBy(criteriaBuilder.desc(root.get("name")));
        }
      } else if (sortField.equals("username")) {
        if (sortDirection.isAscending()) {
          query.orderBy(criteriaBuilder.asc(root.get("username")));
        } else {
          query.orderBy(criteriaBuilder.desc(root.get("username")));
        }
      } else if (sortField.equals("surname")) {
        if (sortDirection.isAscending()) {
          query.orderBy(criteriaBuilder.asc(root.get("surname")));
        } else {
          query.orderBy(criteriaBuilder.desc(root.get("surname")));
        }
      } else if (sortField.equals("mail")) {
        if (sortDirection.isAscending()) {
          query.orderBy(criteriaBuilder.asc(root.get("mail")));
        } else {
          query.orderBy(criteriaBuilder.desc(root.get("mail")));
        }
      }

      return query.getRestriction();
    };
  }
}
