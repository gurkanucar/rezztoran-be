package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.CategoryDTO;
import com.rezztoran.rezztoranbe.model.Category;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.CategoryService;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

  private final CategoryService categoryService;
  private final ModelMapper mapper;

  public CategoryController(CategoryService categoryService, ModelMapper mapper) {
    this.categoryService = categoryService;
    this.mapper = mapper;
  }

  @GetMapping
  public ResponseEntity<ApiResponse<Object>> getAllCategories() {
    var categoryDTOs =
        categoryService.getAllCategories().stream()
            .map(x -> mapper.map(x, CategoryDTO.class))
            .collect(Collectors.toList());
    return ApiResponse.builder().data(categoryDTOs).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> getCategoryByID(@PathVariable Long id) {
    return ApiResponse.builder()
        .data(mapper.map(categoryService.getCategoryByID(id), CategoryDTO.class))
        .build();
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Object>> createCategory(@RequestBody Category category) {
    return ApiResponse.builder()
        .data(mapper.map(categoryService.create(category), CategoryDTO.class))
        .build();
  }

  @PutMapping
  public ResponseEntity<ApiResponse<Object>> updateCategory(@RequestBody Category category) {
    return ApiResponse.builder()
        .data(mapper.map(categoryService.update(category), CategoryDTO.class))
        .build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteCategory(@PathVariable Long id) {
    categoryService.delete(id);
    return ApiResponse.builder().build();
  }
}
