package org.example.controller;

import org.example.model.Recipe;
import org.example.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping
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
        System.out.println("Haha " + recipeService.findRecipeIdByTagId(tagId));
        return recipeService.findRecipeIdByTagId(tagId);
    }

    @GetMapping("/recipes")
    public List<Recipe> fetchAllRecipes() {
        return recipeService.findAllRecipes();
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
