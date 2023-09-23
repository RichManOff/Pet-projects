package com.example.provence.service;

import com.example.provence.model.Category;
import com.example.provence.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryService {
    public final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        } else {
            // Handle not found case (e.g., throw an exception or return null)
            throw new IllegalStateException("Menu category with ID " + id + " not found");
        }
    }

    public void createMenuCategory(Category category) {
        categoryRepository.save(category);
    }

    public void deleteMenuCategory(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            categoryRepository.delete(categoryOptional.get());
        } else {
            throw new IllegalStateException("Order not found with ID: " + id);
        }
    }

}
