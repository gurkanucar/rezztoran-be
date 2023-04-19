package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.CategoryDTO;
import com.rezztoran.rezztoranbe.dto.RestaurantDTO;
import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.Category;
import com.rezztoran.rezztoranbe.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/** The type Category service. */
@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final ExceptionUtil exceptionUtil;
  private final ModelMapper mapper;

  /**
   * Gets all categories.
   *
   * @return the all categories
   */
  public Page<CategoryDTO> getAllCategories(Pageable pageable) {
    return categoryRepository
        .findAll( pageable)
        .map(x -> mapper.map(x, CategoryDTO.class));
  }

  /**
   * Gets category by id.
   *
   * @param id the id
   * @return the category by id
   */
  public Category getCategoryByID(Long id) {
    return categoryRepository
        .findById(id)
        .orElseThrow(() -> exceptionUtil.buildException(Ex.CATEGORY_NOT_FOUND_EXCEPTION));
  }

  /**
   * Create category.
   *
   * @param category the category
   * @return the category
   */
  public Category create(Category category) {
    if (categoryRepository.findByCategoryName(category.getCategoryName()).isPresent()) {
      throw exceptionUtil.buildException(Ex.CATEGORY_ALREADY_EXISTS_EXCEPTION);
    }
    return categoryRepository.save(category);
  }

  /**
   * Update category.
   *
   * @param category the category
   * @return the category
   */
  public Category update(Category category) {
    var existing = getCategoryByID(category.getId());
    if (categoryRepository.findByCategoryName(category.getCategoryName()).isPresent()) {
      throw exceptionUtil.buildException(Ex.CATEGORY_ALREADY_EXISTS_EXCEPTION);
    }
    existing.setCategoryName(category.getCategoryName());
    existing.setCategoryImage(category.getCategoryImage());
    return categoryRepository.save(category);
  }

  /**
   * Delete.
   *
   * @param id the id
   */
  public void delete(Long id) {
    var existing = getCategoryByID(id);
    categoryRepository.delete(existing);
  }
}
