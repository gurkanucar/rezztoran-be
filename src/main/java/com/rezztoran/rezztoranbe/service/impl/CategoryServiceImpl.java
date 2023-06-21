package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.dto.CategoryDTO;
import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.Category;
import com.rezztoran.rezztoranbe.repository.CategoryRepository;
import com.rezztoran.rezztoranbe.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/** The type Category service. */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final ExceptionUtil exceptionUtil;
  private final ModelMapper mapper;

  @Override
  public Page<CategoryDTO> getAllCategories(Pageable pageable) {
    return categoryRepository.findAll(pageable).map(x -> mapper.map(x, CategoryDTO.class));
  }

  @Override
  public Category getCategoryByID(Long id) {
    return categoryRepository
        .findById(id)
        .orElseThrow(() -> exceptionUtil.buildException(Ex.CATEGORY_NOT_FOUND_EXCEPTION));
  }

  @Override
  public Category create(Category category) {
    if (categoryRepository.findByCategoryName(category.getCategoryName()).isPresent()) {
      throw exceptionUtil.buildException(Ex.CATEGORY_ALREADY_EXISTS_EXCEPTION);
    }
    return categoryRepository.save(category);
  }

  @Override
  public List<Category> createCategoryList(List<Category> categories) {
    categories.forEach(
        x -> {
          if (categoryRepository.findByCategoryName(x.getCategoryName()).isEmpty()) {
            categoryRepository.save(x);
          }
        });
    return categoryRepository.findAll();
  }

  @Override
  public Category update(Category category) {
    var existing = getCategoryByID(category.getId());
    var found = categoryRepository.findByCategoryName(category.getCategoryName());
    if (found.isPresent() && !found.get().getId().equals(existing.getId())) {
      throw exceptionUtil.buildException(Ex.CATEGORY_ALREADY_EXISTS_EXCEPTION);
    }
    existing.setCategoryName(category.getCategoryName());
    existing.setCategoryImage(category.getCategoryImage());
    return categoryRepository.save(category);
  }

  @Override
  public void delete(Long id) {
    var existing = getCategoryByID(id);
    categoryRepository.delete(existing);
  }
}
