package com.ecommerce.service;

import com.ecommerce.entity.Category;
import com.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for category operations. Provides CRUD functionality and
 * search capabilities for the monolithic e-commerce application.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getActiveCategories() {
        return categoryRepository.findByIsActiveTrue();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    public Category createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new RuntimeException("Category with this name already exists");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        category.setImageUrl(categoryDetails.getImageUrl());
        category.setParentCategory(categoryDetails.getParentCategory());
        category.setActive(categoryDetails.isActive());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> getRootCategories() {
        return categoryRepository.findRootCategories();
    }

    public List<Category> getSubCategories(Long parentId) {
        return categoryRepository.findByParentCategoryId(parentId);
    }

    public List<Category> searchCategories(String keyword) {
        return categoryRepository.searchByKeyword(keyword);
    }

    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }
}
