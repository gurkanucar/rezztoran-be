package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.exception.CategoryAlreadyExistsException;
import com.rezztoran.rezztoranbe.exception.CategoryNotFoundException;
import com.rezztoran.rezztoranbe.model.Category;
import com.rezztoran.rezztoranbe.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryByID(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("category not found!"));
    }

    public Category create(Category category) {
        if (categoryRepository.findByCategoryName(category.getCategoryName()).isPresent()) {
            throw new CategoryAlreadyExistsException("category already exists!");
        }
        return categoryRepository.save(category);
    }


    public Category update(Category category) {
        var existing = getCategoryByID(category.getId());
        if (categoryRepository.findByCategoryName(category.getCategoryName()).isPresent()) {
            throw new CategoryAlreadyExistsException("category already exists!");
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
