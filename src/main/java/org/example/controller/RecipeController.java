package org.example.controller;

import org.example.model.Recipe;
import org.example.service.RecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class RecipeController {
    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes/{id}")
    public Recipe fetchRecipeById(@PathVariable Long id) {
        return recipeService.findRecipeById(id);
    }

    @GetMapping("/recipes/ids")
    public List<Long> fetchRecipeIdByTagId(@RequestParam("tag_id") Long tagId) {
        return recipeService.findRecipeIdByTagId(tagId);
    }

    @GetMapping("/recipes")
    public List<Recipe> fetchAllRecipes() {
        return recipeService.findAllRecipes();
    }

    @Value("${TAG_IDS}")
    private String tagIds;
    @GetMapping("/today-recipes")
    public List<Recipe> fetchTodayRecipes() {
        List<String> tagIdList = Arrays.asList(tagIds.split(","));
        return recipeService.findTodayRecipes(tagIdList);
    }

    @GetMapping("/tags-selected")
    public List<Recipe> getQueryParameters(@RequestParam("selected") String selectedTags) throws SQLException {
        System.out.println("selectedTags: " + selectedTags);
        String[] tagIdsArrayStr = selectedTags.split("\\|");
        List<Integer> tagIdsArrayNum = new ArrayList<>();
        for (String tagIdStr: tagIdsArrayStr) {
            tagIdsArrayNum.add(Integer.parseInt(tagIdStr));
        }
        System.out.println("tag IDs: " + tagIdsArrayNum);

        return recipeService.findRecipesBySelectedTags(tagIdsArrayNum);
    }

    @PostMapping
    public int postRecipe(@RequestBody Recipe recipe) {
        return recipeService.addRecipe(recipe);
    }

    @PutMapping("/recipes/{id}")
    public int alterRecipe(@PathVariable("id") Long id, @RequestBody Recipe recipe) {
        return recipeService.modifyRecipe(id, recipe);
    }

    @DeleteMapping("/recipes/{id}")
    public int dropRecipe(@PathVariable Long id) {
        return recipeService.removeRecipe(id);
    }
}
