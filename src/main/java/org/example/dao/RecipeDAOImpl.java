package org.example.dao;

import org.example.TestConnect;
import org.example.model.Recipe;
import org.example.model.RecipeMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RecipeDAOImpl implements RecipeDAO {
    private static final String sqlPath = "src/main/resources/sqlFiles/get_intersected_recipe_ids.sql";
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

    private void executeSqlFile() {
        try (Connection conn = TestConnect.dataSource().getConnection();
             Statement stmt = conn.createStatement()) {
            String sqlString = new String(Files.readAllBytes(Paths.get(sqlPath)), Charset.forName("UTF-8"));
            stmt.execute(sqlString);
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Failed to create SQL function", e);
        }
    }

    public List<Recipe> getRecipesBySelectedTags(List<Integer> tagIds) throws SQLException {
        List<Recipe> recipeList = new ArrayList<>();
        String sql = "SELECT get_intersected_recipe_ids(?) AS result";
        try (Connection conn = TestConnect.dataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            executeSqlFile();
            Array sqlArray = conn.createArrayOf("INTEGER", tagIds.toArray());
            pstmt.setArray(1, sqlArray);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long recipeId = rs.getLong("recipe_id");
                recipeList.add(getRecipeById(recipeId));
            }
            return recipeList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

// https://www.digitalocean.com/community/tutorials/spring-jdbctemplate-example