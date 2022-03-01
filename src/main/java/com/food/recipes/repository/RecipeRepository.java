package com.food.recipes.repository;

import com.food.recipes.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByCategoryId(Long recipeId);

    Recipe findByNameAndUserId(String name, Long id);

    Recipe findByNameAndUserIdAndIdIsNot(String name, Long id, Long recipeId);
}
