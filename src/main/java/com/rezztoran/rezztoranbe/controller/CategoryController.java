package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.dto.CategoryDTO;
import com.rezztoran.rezztoranbe.model.Category;
import com.rezztoran.rezztoranbe.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper mapper;

    public CategoryController(CategoryService categoryService,
                              ModelMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        var categoryDTOs = categoryService.getAllCategories()
                .stream().map(x -> mapper.map(x, CategoryDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryByID(@PathVariable Long id){
        return ResponseEntity.ok(mapper.map(categoryService.getCategoryByID(id), CategoryDTO.class));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(mapper.map(categoryService.create(category), CategoryDTO.class));
    }

}
