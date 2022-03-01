package com.food.recipes.service;

import com.food.recipes.exceptions.InformationExistException;
import com.food.recipes.exceptions.InformationNotFoundException;
import com.food.recipes.model.Category;
import com.food.recipes.model.Recipe;
import com.food.recipes.repository.CategoryRepository;
import com.food.recipes.repository.RecipeRepository;
import com.food.recipes.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }


    private RecipeRepository recipeRepository;

    @Autowired
    public void setRecipeRepository(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }



    public List<Category> getAllCategories(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getUser().getId());
        List<Category> category = categoryRepository.findByUserId(userDetails.getUser().getId());

        if(category.isEmpty()){
            throw new InformationNotFoundException("no category found for user id" + userDetails.getUser().getId() + " not found");

        }else{
            return category;
        }
    }

    public Category createCategory(Category categoryObject){

        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Category category = categoryRepository.findByUserIdAndName(userDetails.getUser().getId(),categoryObject.getName());

        if(category != null){
            throw new InformationExistException("category with name " + category.getName() + " already exists");
        } else{
            categoryObject.setUser(userDetails.getUser());
            return categoryRepository.save(categoryObject);
        }
    }

    public Category getCategory(Long categoryId) {
        System.out.println("service calling getCategory ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Category category = categoryRepository.findByIdAndUserId(categoryId, userDetails.getUser().getId());
        if (category == null) {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        } else {
            return category;
        }
    }

    public Category updateCategory(Long categoryId, Category categoryObject){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Category category = categoryRepository.findByIdAndUserId(categoryId, userDetails.getUser().getId());
        if (category == null) {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        } else {
            category.setDescription(categoryObject.getDescription());
            category.setName(categoryObject.getName());
            category.setUser(userDetails.getUser());
            return categoryRepository.save(category);
        }

    }

    public String deleteCategory(Long categoryId) {
        System.out.println("service calling deleteCategory ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Category category = categoryRepository.findByIdAndUserId(categoryId, userDetails.getUser().getId());
        if (category == null) {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        } else {
            categoryRepository.deleteById(categoryId);
            return "category with id " + categoryId + " has been successfully deleted";
        }
    }

    public Recipe createCategoryRecipe(Long categoryId, Recipe recipeObject) {
        System.out.println("service calling createCategoryRecipe ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Category category = categoryRepository.findByIdAndUserId(categoryId, userDetails.getUser().getId());
        if (category == null) {
            throw new InformationNotFoundException(
                    "category with id " + categoryId + " not belongs to this user or category does not exist");
        }
        Recipe recipe = recipeRepository.findByNameAndUserId(recipeObject.getName(), userDetails.getUser().getId());
        if (recipe != null) {
            throw new InformationExistException("recipe with name " + recipe.getName() + " already exists");
        }
        recipeObject.setUser(userDetails.getUser());
        recipeObject.setCategory(category);
        return recipeRepository.save(recipeObject);
    }

    public List<Recipe> getCategoryRecipes(Long categoryId) {
        System.out.println("service calling getCategoryRecipes ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Category category = categoryRepository.findByIdAndUserId(categoryId, userDetails.getUser().getId());
        if (category == null) {
            throw new InformationNotFoundException("category with id " + categoryId + " " +
                    "not belongs to this user or category does not exist");
        }
        return category.getRecipeList();
    }

    public Recipe getCategoryRecipe(Long categoryId, Long recipeId) {
        System.out.println("service calling getCategoryRecipe ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Category category = categoryRepository.findByIdAndUserId(categoryId, userDetails.getUser().getId());
        if (category == null) {
            throw new InformationNotFoundException("category with id " + categoryId +
                    " not belongs to this user or category does not exist");
        }
        Optional<Recipe> recipe = recipeRepository.findByCategoryId(
                categoryId).stream().filter(p -> p.getId().equals(recipeId)).findFirst();
        if (!recipe.isPresent()) {
            throw new InformationNotFoundException("recipe with id " + recipeId +
                    " not belongs to this user or recipe does not exist");
        }
        return recipe.get();
    }

    public Recipe updateCategoryRecipe(Long categoryId, Long recipeId, Recipe recipeObject) {
        System.out.println("service calling updateCategoryRecipe ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Category category = categoryRepository.findByIdAndUserId(categoryId, userDetails.getUser().getId());
        if (category == null) {
            throw new InformationNotFoundException("category with id " + categoryId +
                    " not belongs to this user or category does not exist");
        }
        Optional<Recipe> recipe = recipeRepository.findByCategoryId(
                categoryId).stream().filter(p -> p.getId().equals(recipeId)).findFirst();
        if (!recipe.isPresent()) {
            throw new InformationNotFoundException("recipe with id " + recipeId +
                    " not belongs to this user or recipe does not exist");
        }
        Recipe oldRecipe = recipeRepository.findByNameAndUserIdAndIdIsNot(
                recipeObject.getName(), userDetails.getUser().getId(), recipeId);
        if (oldRecipe != null) {
            throw new InformationExistException("recipe with name " + oldRecipe.getName() + " already exists");
        }
        recipe.get().setName(recipeObject.getName());
        recipe.get().setIngredients(recipeObject.getIngredients());
        recipe.get().setSteps(recipeObject.getSteps());
        recipe.get().setTime(recipeObject.getTime());
        recipe.get().setPortions(recipeObject.getPortions());
        return recipeRepository.save(recipe.get());
    }


    public void deleteCategoryRecipe(Long categoryId, Long recipeId) {
        System.out.println("service calling deleteCategoryRecipe ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Category category = categoryRepository.findByIdAndUserId(categoryId, userDetails.getUser().getId());
        if (category == null) {
            throw new InformationNotFoundException("category with id " + categoryId +
                    " not belongs to this user or category does not exist");
        }
        Optional<Recipe> recipe = recipeRepository.findByCategoryId(
                categoryId).stream().filter(p -> p.getId().equals(recipeId)).findFirst();
        if (!recipe.isPresent()) {
            throw new InformationNotFoundException("recipe with id " + recipeId +
                    " not belongs to this user or recipe does not exist");
        }
        recipeRepository.deleteById(recipe.get().getId());
    }


}