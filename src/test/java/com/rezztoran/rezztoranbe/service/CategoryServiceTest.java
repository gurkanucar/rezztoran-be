package com.rezztoran.rezztoranbe.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rezztoran.rezztoranbe.exception.BusinessException;
import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.Category;
import com.rezztoran.rezztoranbe.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

  @Mock private CategoryRepository categoryRepository;

  @Mock private ExceptionUtil exceptionUtil;

  @InjectMocks private CategoryService categoryService;

  @Test
  void testGetAllCategories() {
    List<Category> categories = List.of(new Category(), new Category());
    when(categoryRepository.findAll()).thenReturn(categories);
    assertEquals(categories, categoryService.getAllCategories());
  }

  @Test
  void testGetCategoryById() {
    Long id = 1L;
    Category category = new Category();
    when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
    assertEquals(category, categoryService.getCategoryByID(id));
  }

  @Test
  void testGetCategoryByIdNotFound() {
    Long nonExistingId = 99L;
    when(categoryRepository.findById(nonExistingId)).thenReturn(Optional.empty());
    when(exceptionUtil.buildException(Ex.CATEGORY_NOT_FOUND_EXCEPTION))
        .thenReturn(new BusinessException("Category not found", HttpStatus.NOT_FOUND));
    assertThrows(
        BusinessException.class,
        () -> {
          categoryService.getCategoryByID(nonExistingId);
        },
        "Category not found for id " + nonExistingId);
  }

  @Test
  void testCreateCategory() {
    Category category = new Category();
    category.setCategoryName("Test Category");
    when(categoryRepository.findByCategoryName(category.getCategoryName()))
        .thenReturn(Optional.empty());
    when(categoryRepository.save(category)).thenReturn(category);
    assertEquals(category, categoryService.create(category));
  }

  @Test
  void testCreateCategoryAlreadyExists() {
    Category category = new Category();
    category.setCategoryName("Test Category");
    when(categoryRepository.findByCategoryName(category.getCategoryName()))
        .thenReturn(Optional.of(category));
    when(exceptionUtil.buildException(Ex.CATEGORY_ALREADY_EXISTS_EXCEPTION))
        .thenReturn(new BusinessException("Category already exists", HttpStatus.CONFLICT));
    assertThrows(
        BusinessException.class,
        () -> {
          categoryService.create(category);
        },
        "Category already exists for name " + category.getCategoryName());
  }

  @Test
  void testUpdateCategory() {
    Long id = 1L;
    Category existing = new Category();
    existing.setId(id);
    existing.setCategoryName("Existing Category");
    existing.setCategoryImage("existing.jpg");
    Category updated = new Category();
    updated.setId(id);
    updated.setCategoryName("Updated Category");
    updated.setCategoryImage("updated.jpg");

    when(categoryRepository.findById(id)).thenReturn(Optional.of(existing));
    when(categoryRepository.findByCategoryName(updated.getCategoryName()))
        .thenReturn(Optional.empty());
    when(categoryRepository.save(existing)).thenReturn(existing);

    assertAll(
        "category update",
        () -> assertEquals(existing, categoryService.update(updated)),
        () -> assertEquals(updated.getCategoryName(), existing.getCategoryName()),
        () -> assertEquals(updated.getCategoryImage(), existing.getCategoryImage()));
  }

  @Test
  void testUpdateCategoryAlreadyExists() {
    Long id = 1L;
    Category existing = new Category();
    existing.setId(id);
    existing.setCategoryName("Existing Category");
    existing.setCategoryImage("existing.jpg");
    Category updated = new Category();
    updated.setId(id);
    updated.setCategoryName("Updated Category");
    updated.setCategoryImage("updated.jpg");
    when(categoryRepository.findById(id)).thenReturn(Optional.of(existing));
    when(categoryRepository.findByCategoryName(updated.getCategoryName()))
        .thenReturn(Optional.of(existing));
    when(exceptionUtil.buildException(Ex.CATEGORY_ALREADY_EXISTS_EXCEPTION))
        .thenReturn(new BusinessException("Category already exists", HttpStatus.CONFLICT));
    assertThrows(
        BusinessException.class,
        () -> {
          categoryService.update(updated);
        },
        "Category already exists");
    verify(categoryRepository, never()).save(any(Category.class));
  }
}
