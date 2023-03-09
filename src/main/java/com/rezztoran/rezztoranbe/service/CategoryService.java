package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.Category;
import com.rezztoran.rezztoranbe.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final ExceptionUtil exceptionUtil;

  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  public Category getCategoryByID(Long id) {
    return categoryRepository
        .findById(id)
        .orElseThrow(() -> exceptionUtil.buildException(Ex.CATEGORY_NOT_FOUND_EXCEPTION));
  }

  public Category create(Category category) {
    if (categoryRepository.findByCategoryName(category.getCategoryName()).isPresent()) {
      throw exceptionUtil.buildException(Ex.CATEGORY_ALREADY_EXISTS_EXCEPTION);
    }
    return categoryRepository.save(category);
  }

  public Category update(Category category) {
    var existing = getCategoryByID(category.getId());
    if (categoryRepository.findByCategoryName(category.getCategoryName()).isPresent()) {
      throw exceptionUtil.buildException(Ex.CATEGORY_ALREADY_EXISTS_EXCEPTION);
    }
    existing.setCategoryName(category.getCategoryName());
    existing.setCategoryImage(category.getCategoryImage());
    return categoryRepository.save(category);
  }

  public void delete(Long id) {
    var existing = getCategoryByID(id);
    categoryRepository.delete(existing);
  }
}
