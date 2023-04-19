package com.rezztoran.rezztoranbe.repository;

import com.rezztoran.rezztoranbe.model.Category;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** The interface Category repository. */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  /**
   * Find by category name optional.
   *
   * @param name the name
   * @return the optional
   */
  Optional<Category> findByCategoryName(String name);

  Page<Category> findAll(Pageable pageable);
}
