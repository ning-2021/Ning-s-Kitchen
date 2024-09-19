package org.example.dao;

import org.example.model.Recipe;
import org.example.model.RecipeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

public class RecipeDAOImpl implements RecipeDAO {
    JdbcTemplate jdbc;

    @Autowired
    public RecipeDAOImpl(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
    }

    public Recipe getRecipeById(Long id) {
        String findRecipeSql = "SELECT * FROM recipes WHERE id = ?";
        return jdbc.queryForObject(findRecipeSql, new RecipeMapper(), id);
    }

    public List<Recipe> getAllRecipes() {
        String getAllRecipesSql = "SELECT * FROM recipes";
        return jdbc.query(getAllRecipesSql, new RecipeMapper());
    }

    public int deleteRecipe(Long id) {
        String deleteRecipeSql = "DELETE FROM recipes WHERE id = ?";
        return jdbc.update(deleteRecipeSql, id);
    }

    public int updateRecipe(Long id, Recipe recipe) {
        String updateRecipeSql = "UPDATE recipes SET title = ?, description = ?, instructions = ?, rating = ?, image = ?, duration = ?, created_at = ? WHERE id = ?";
        return jdbc.update(updateRecipeSql, recipe.getTitle(), recipe.getDescription(), recipe.getInstructions(), recipe.getRating(), recipe.getImage(), recipe.getDuration(), recipe.getCreated_at(), id);
    }

    public int createRecipe(Recipe recipe) {
        String insertRecipeSql = "INSERT INTO recipes(title, description, instructions, rating, image, duration, created_at) VALUES (?,?,?,?,?,?,?,?)";
        return jdbc.update(insertRecipeSql, recipe.getTitle(), recipe.getDescription(), recipe.getInstructions(), recipe.getRating(), recipe.getImage(), recipe.getDuration(), recipe.getCreated_at());
    }
}

// https://www.digitalocean.com/community/tutorials/spring-jdbctemplate-example
