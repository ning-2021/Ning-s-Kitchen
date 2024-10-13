package org.example.dao;

import org.example.model.Recipe;
import org.example.model.RecipeMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class RecipeDAOImpl implements RecipeDAO {
    JdbcTemplate jdbc;

    public RecipeDAOImpl(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
    }

    public Recipe getRecipeById(Long id) {
        String getRecipeSql = "SELECT * FROM recipes WHERE id = ?";
        return jdbc.queryForObject(getRecipeSql, new RecipeMapper(), id);
    }

    public List<Long> getRecipeIdByTagId(Long tag_id) {
        String getRecipeIdByTagIdSql = "SELECT recipe_id FROM recipes_tags WHERE tag_id = ?";
        return jdbc.queryForList(getRecipeIdByTagIdSql, Long.class, tag_id);
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
        String updateRecipeSql = "UPDATE recipes SET title = ?, description = ?, instructions = ?::jsonb, rating = ?, image = ?, duration = ? WHERE id = ?";
        return jdbc.update(updateRecipeSql, recipe.getTitle(), recipe.getDescription(), recipe.getInstructions(), recipe.getRating(), recipe.getImage(), recipe.getDuration(), id);
    }

    public int createRecipe(Recipe recipe) {
        String insertRecipeSql = "INSERT INTO recipes(title, description, instructions, rating, image, duration) VALUES (?,?,?::jsonb,?,?,?)";
        return jdbc.update(insertRecipeSql, recipe.getTitle(), recipe.getDescription(), recipe.getInstructions(), recipe.getRating(), recipe.getImage(), recipe.getDuration());
    }
}

// https://www.digitalocean.com/community/tutorials/spring-jdbctemplate-example
