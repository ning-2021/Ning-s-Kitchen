package org.example.model;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class RecipeMapper implements RowMapper<Recipe> {
    public Recipe mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Recipe recipe = new Recipe();
        recipe.setId(resultSet.getLong("id"));
        recipe.setTitle(resultSet.getString("title"));
        recipe.setDescription(resultSet.getString("description"));
        recipe.setInstructions(resultSet.getString("instructions"));
        recipe.setRating(resultSet.getDouble("rating"));
        recipe.setImage(resultSet.getString("image"));
        recipe.setDuration(resultSet.getInt("duration"));
        recipe.setCreated_at(resultSet.getString("created_at"));
        return recipe;
    }
}

