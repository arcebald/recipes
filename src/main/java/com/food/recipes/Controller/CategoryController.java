package com.food.recipes.Controller;

import com.food.recipes.exceptions.InformationExistException;
import com.food.recipes.exceptions.InformationNotFoundException;
import com.food.recipes.model.Category;
import com.food.recipes.model.Recipe;
import com.food.recipes.repository.CategoryRepository;
import com.food.recipes.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class CategoryController {


    private CategoryService categoryService;

    @Autowired
    public void setCategoryRepository(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping(path = "/hello-world/")
    public String getHelloWorld() {
        return "Hello World";
    }

    @GetMapping("/categories/")
    public List<Category> getAllCategories() {
        System.out.println("calling getAllCategories");
        return categoryService.getAllCategories();
    }

    @PostMapping("/categories/")
    public Category createCategory(@RequestBody Category categoryObject) {
        return categoryService.createCategory(categoryObject);
    }

    @GetMapping("/categories/{categoryId}/")
    public Category getCategory(@PathVariable Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @PutMapping("/categories/{categoryId}/")
    public Category updateCategory(@PathVariable(value = "categoryId") Long categoryId, @RequestBody Category categoryObject) {
        return categoryService.updateCategory(categoryId, categoryObject);
    }

    @DeleteMapping("/categories/{categoryId}/")
    public String deleteCategory(@PathVariable(value = "categoryId") Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
    @PostMapping("/categories/{categoryId}/recipes/")
    public Recipe createCategoryRecipe(@PathVariable(value = "categoryId") Long categoryId, @RequestBody Recipe recipeObject){
        System.out.println("calling createCategoryRecipe===>");
        return categoryService.createCategoryRecipe(categoryId, recipeObject);
    }
    @GetMapping("/categories/{categoryId}/recipes/")
    public List<Recipe> getCategoryRecipes(@PathVariable(value = "categoryId") Long categoryId){
        return categoryService.getCategoryRecipes(categoryId);
    }
    @GetMapping("/categories/{categoryId}/recipes/{recipeId}/")
    public Recipe getCategoryRecipe(@PathVariable(value = "categoryId") Long categoryId, @PathVariable(value = "recipeId") Long recipeId){
        return categoryService.getCategoryRecipe(categoryId, recipeId);
    }

    @PutMapping("/categories/{categoryId}/recipes/{recipeId}")
    public Recipe updateCategoryRecipe(@PathVariable(value = "categoryId") Long categoryId,
                                       @PathVariable(value = "recipeId") Long recipeId,
                                       @RequestBody Recipe recipeObject) {
        return categoryService.updateCategoryRecipe(categoryId, recipeId, recipeObject);
    }

    @DeleteMapping("/categories/{categoryId}/recipes/{recipeId}/")
    public void deleteCategoryRecipe(@PathVariable(value = "categoryId") Long categoryId, @PathVariable(value = "recipeId") Long recipeId){
        categoryService.deleteCategoryRecipe(categoryId, recipeId);
    }

}
