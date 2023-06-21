package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.CategoryDTO;
import com.rezztoran.rezztoranbe.model.Category;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** The interface Category service. */
public interface CategoryService {

  /**
   * Gets all categories.
   *
   * @param pageable the pageable
   * @return the all categories
   */
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
   * Create category list list.
   *
   * @param categories the categories
   * @return the list
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
