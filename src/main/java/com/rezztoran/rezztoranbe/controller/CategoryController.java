package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.CategoryDTO;
import com.rezztoran.rezztoranbe.model.Category;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type Category controller. */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

  private final CategoryService categoryService;
  private final ModelMapper mapper;

  /**
   * Instantiates a new Category controller.
   *
   * @param categoryService the category service
   * @param mapper the mapper
   */
  public CategoryController(CategoryService categoryService, ModelMapper mapper) {
    this.categoryService = categoryService;
    this.mapper = mapper;
  }

  /**
   * Gets all categories.
   *
   * @param pageable the pageable
   * @return the all categories
   */
  @GetMapping
  public ResponseEntity<ApiResponse<Object>> getAllCategories(
      @PageableDefault(size = 20) Pageable pageable) {
    return ApiResponse.builder().pageableData(categoryService.getAllCategories(pageable)).build();
  }

  /**
   * Gets category by id.
   *
   * @param id the id
   * @return the category by id
   */
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> getCategoryByID(@PathVariable Long id) {
    return ApiResponse.builder()
        .data(mapper.map(categoryService.getCategoryByID(id), CategoryDTO.class))
        .build();
  }

  /**
   * Create category response entity.
   *
   * @param category the category
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<ApiResponse<Object>> createCategory(@RequestBody Category category) {
    return ApiResponse.builder()
        .data(mapper.map(categoryService.create(category), CategoryDTO.class))
        .build();
  }

  /**
   * Update category response entity.
   *
   * @param category the category
   * @return the response entity
   */
  @PutMapping
  public ResponseEntity<ApiResponse<Object>> updateCategory(@RequestBody Category category) {
    return ApiResponse.builder()
        .data(mapper.map(categoryService.update(category), CategoryDTO.class))
        .build();
  }

  /**
   * Delete category response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteCategory(@PathVariable Long id) {
    categoryService.delete(id);
    return ApiResponse.builder().build();
  }
}
