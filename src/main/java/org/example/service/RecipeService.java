package org.example.service;

import org.example.dao.RecipeDAOImpl;
import org.example.model.Recipe;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

@Service
public class RecipeService {
    private final RecipeDAOImpl recipeDAOImpl;
    private List<Recipe> todayRecipes;
    private LocalDateTime lastUpdate;

    public RecipeService(RecipeDAOImpl recipeDAOImpl){
        this.recipeDAOImpl = recipeDAOImpl;
    }

    public Recipe findRecipeById(Long id) {
        return recipeDAOImpl.getRecipeById(id);
    }

    public List<Long> findRecipeIdByTagId(Long tag_id) { return recipeDAOImpl.getRecipeIdByTagId(tag_id); }

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

    private List<Recipe> updateRecipesOfToday(LocalDateTime now, List<String> tagIdList) {
        todayRecipes = selectRandomRecipesByTags(tagIdList);
        lastUpdate = now;
        return todayRecipes;
    }

    private List<Recipe> selectRandomRecipesByTags(List<String> tagIdList) {
        List<Recipe> selectedRecipes = new ArrayList<>();

        for (String tagId : tagIdList) {
            // get all recipe IDs for this tag
            List<Long> recipeIds = findRecipeIdByTagId(Long.parseLong(tagId));
            if (!recipeIds.isEmpty()) {
                // select random recipe ID
                int randomIndex = new Random().nextInt(recipeIds.size());
                Long randomRecipeId = recipeIds.get(randomIndex);
                // get the full recipe and add to list
                Recipe recipe = findRecipeById(randomRecipeId);
                selectedRecipes.add(recipe);
            }
        }
        return selectedRecipes;
    }


    /**
     *
     [case 1]. If this is the first time loading:
               then we need to initialize lastUpdate and recipes
     [case 2]. If current time is before 6AM today, there will be two cases:
          (2a) if lastUpdate was before yesterday's 6AM, then we need to update recipes with yesterday's recipes, update the lastUpdate by current time
          (2b) if lastUpdate was after yesterday's 6AM (as it's already before today's 6AM), then there will be no update
     [case 3]. If current time is after or exactly 6AM today, there will be two cases:
          (3a) if lastUpdate was before 6AM today, then we need to update recipes with today's recipes and update lastUpdate as current time
          (3b) if lastUpdate was after or exactly 6AM today, then there will be no update
     *
     */

    public List<Recipe> findTodayRecipes(List<String> tagIdList) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sixAmToday = now.withHour(6).withMinute(0).withSecond(0).withNano(0);

        // case 1: first time loading
        if (todayRecipes == null || lastUpdate == null) {
            // no matter it's before today's 6AM or not,
            // we need to update the last update time to the current time and
            // set todayRecipes
            lastUpdate = now; // set last update time to the current time
            return updateRecipesOfToday(now, tagIdList);
        }

        // case 2: if current time is before today's 6AM
        if (now.isBefore(sixAmToday)) {
            // show yesterday's recipes
            if (lastUpdate.isBefore(sixAmToday.minusDays(1))) {
                // last visit was more than a day ago, generate yesterday's recipes by using current timestamp as it's still before today's 6AM
                lastUpdate = now;
                return updateRecipesOfToday(now, tagIdList);
            }
        }

        // case 3: if current time is exactly or after 6AM today
        if (now.isAfter(sixAmToday) || now.equals(sixAmToday)) {
            // if last update was before 6AM today, generate new recipes
            if (lastUpdate.isBefore(sixAmToday)) {
                lastUpdate = now;
                return updateRecipesOfToday(now, tagIdList);
            }
        }

        return todayRecipes;
    }

    public List<Recipe> findRecipesBySelectedTags(List<Integer> tagIdsArrayNum) throws SQLException {
        return recipeDAOImpl.getRecipesBySelectedTags(tagIdsArrayNum);
    }
}
