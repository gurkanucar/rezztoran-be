package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.CategoryDTO;
import com.rezztoran.rezztoranbe.model.Category;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

  Page<CategoryDTO> getAllCategories(Pageable pageable);

  /**
   * Gets category by id.
   *
   * @param id the id
   * @return the category by id
   */
  Category getCategoryByID(Long id);

  /**
   * Create category.
   *
   * @param category the category
   * @return the category
   */
  Category create(Category category);

  /**
   * Create category.
   *
   * @param categories the category list
   * @return the categories
   */
  List<Category> createCategoryList(List<Category> categories);

  /**
   * Update category.
   *
   * @param category the category
   * @return the category
   */
  Category update(Category category);
  /**
   * Delete.
   *
   * @param id the id
   */
  void delete(Long id);
}
