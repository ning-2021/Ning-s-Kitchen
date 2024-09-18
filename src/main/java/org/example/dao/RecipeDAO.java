package org.example.dao;

import org.example.model.Recipe;
import java.util.List;
public interface RecipeDAO {
    Recipe getRecipeById(Long id);
    List<Recipe> getAllRecipes(Recipe recipe);
    int deleteRecipe(Long id);
    int updateRecipe(Long id);
    int createRecipe(Long id);
}
