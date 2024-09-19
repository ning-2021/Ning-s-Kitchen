package org.example.service;

import org.example.dao.RecipeDAOImpl;
import org.example.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeDAOImpl recipeDAOImpl;

    public RecipeService(RecipeDAOImpl recipeDAOImpl){
        this.recipeDAOImpl = recipeDAOImpl;
    }

    public Recipe findRecipeById(Long id) {
        return recipeDAOImpl.getRecipeById(id);
    }

    public List<Recipe> findAllRecipes() {
        return recipeDAOImpl.getAllRecipes();
    }

    public int removeRecipe(Long id) {
        return recipeDAOImpl.deleteRecipe(id);
    }

    public int modifyRecipe(Long id, Recipe recipe) {
        return recipeDAOImpl.updateRecipe(id, recipe);
    }

    public int addRecipe(Recipe recipe) { return recipeDAOImpl.createRecipe(recipe); }

}
