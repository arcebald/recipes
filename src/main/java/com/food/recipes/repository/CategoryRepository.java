package com.food.recipes.repository;

import com.food.recipes.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String categoryName);
    List<Category> findByUserId(Long userId);
    Category findByUserIdAndName(Long userId, String name);

    Category findByIdAndUserId(Long categoryId, Long id);
}
