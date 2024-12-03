package org.example;
import com.opencsv.exceptions.CsvValidationException;
import org.example.enums.*;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;
import org.postgresql.util.PGobject;
import io.github.cdimascio.dotenv.Dotenv;

// use the TableCols interface to handle different enums dynamically
public class DataLoader {
    static Dotenv dotenv = Dotenv.load();
    static Map<String, String> pKeys = new HashMap<>();

    public static void primaryKeys() {
        pKeys.put("categories", "id");
        pKeys.put("ingredients", "id");
        pKeys.put("recipes", "id");
        pKeys.put("tags", "id");
        pKeys.put("types", "id");
        pKeys.put("recipes_ingredients", "recipe_id, ingredient_id");
        pKeys.put("recipes_tags", "recipe_id, tag_id");
    }

    // check if a table already being created
    private static boolean checkTableExistence(String tableName) {
        try (Connection conn = TestConnect.dataSource().getConnection()) {
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet rs = dbm.getTables(null, null, tableName, null);
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // execute schema.sql file
    public static void executeSqlSchema(String sqlFilePath) {
        String allTableString = dotenv.get("ALL_TABLES");
        String[] tableNames = allTableString.split(",");
        boolean allExistence = true;
        for (String tableName: tableNames) {
            if (!checkTableExistence(tableName)) {
                allExistence = false;
            }
        }
        if (!allExistence) {
            try (Connection conn = TestConnect.dataSource().getConnection();
                 Statement stmt = conn.createStatement()) {
                // read the .sql file into a string
                String sqlString = new String(Files.readAllBytes(Paths.get(sqlFilePath)));
                // execute all SQL statements inside .sql file
                stmt.execute(sqlString);
                System.out.println("Schema created successfully!");
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("All tables already exist, skip the execution.");
        }
    }

    // dynamically build the SQL INSERT statement using column names and indices provided by TableCols interface
    public static void loadCsvToTable(String csvFilePath, String tableName, BaseColumnEnum[] columns) {

        try (Connection conn = TestConnect.dataSource().getConnection();
             CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            // build the SQL statement dynamically based on the column mapping
            // example: INSERT INTO recipes (id, title, description, instructions, rating, image, duration, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (id) DO NOTHING
            StringBuilder insertBuilder = new StringBuilder("INSERT INTO ");
            insertBuilder.append(tableName).append("(");
            for (BaseColumnEnum column: columns) {
                insertBuilder.append(column.getColumnName()).append(", ");
            }
            insertBuilder.delete(insertBuilder.length() - 2, insertBuilder.length());
            insertBuilder.append(") VALUES (");
            for (BaseColumnEnum column: columns) {
                insertBuilder.append("?, ");
            }
            insertBuilder.delete(insertBuilder.length() - 2, insertBuilder.length());
            insertBuilder.append(")");
            insertBuilder.append(" ON CONFLICT (");
            insertBuilder.append(pKeys.get(tableName));
            insertBuilder.append(") DO NOTHING");
            String sqlInsert = insertBuilder.toString();

            // read rows from the csv file and insert into the table
            try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
                String[] nextLine = reader.readNext();
                while ((nextLine = reader.readNext()) != null) {
                    for (BaseColumnEnum column: columns) {
                        int colIndex = column.getColumnIndex();
                        int colType = column.getColumnType();
                        int paramIndex = colIndex + 1;
                        if (nextLine[colIndex] != null && !nextLine[colIndex].isEmpty()) {
                            switch (colType) {
                                case Types.VARCHAR:
                                case Types.LONGNVARCHAR:
                                    pstmt.setString(paramIndex, nextLine[colIndex]);
                                    break;
                                case Types.INTEGER:
                                    pstmt.setInt(paramIndex, Integer.parseInt(nextLine[colIndex]));
                                    break;
                                case Types.NUMERIC:
                                    pstmt.setDouble(paramIndex, Double.parseDouble(nextLine[colIndex]));
                                    break;
                                case Types.TIMESTAMP:
                                    pstmt.setTimestamp(paramIndex, Timestamp.valueOf(nextLine[colIndex]));
                                    break;
                                case Types.OTHER:
                                    PGobject jsonObject = new PGobject();
                                    jsonObject.setType("jsonb");
                                    jsonObject.setValue(nextLine[colIndex]);
                                    pstmt.setObject(paramIndex, jsonObject);
                                    break;
                                default:
                                    pstmt.setObject(paramIndex, nextLine[colIndex]);
                            }
                        } else {
                            pstmt.setNull(paramIndex, colType);
                        }
                    }
                    // https://stackoverflow.com/questions/6860691/using-jdbc-preparedstatement-in-a-batch
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            } catch (CsvValidationException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        executeSqlSchema("src/main/resources/sqlFiles/schema.sql");
        primaryKeys();
        loadCsvToTable("src/main/resources/data/recipes.csv", "recipes", RecipeCols.values());
        loadCsvToTable("src/main/resources/data/categories.csv", "categories", CategoryCols.values());
        loadCsvToTable("src/main/resources/data/types.csv", "types", TypeCols.values());
        loadCsvToTable("src/main/resources/data/tags.csv", "tags", TagCols.values());
        loadCsvToTable("src/main/resources/data/ingredients.csv", "ingredients", IngredientCols.values());
        loadCsvToTable("src/main/resources/data/recipes_ingredients.csv", "recipes_ingredients", RecipeIngredientCols.values());
        loadCsvToTable("src/main/resources/data/recipes_tags.csv", "recipes_tags", RecipeTagCols.values());
    }
}
