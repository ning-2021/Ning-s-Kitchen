package org.example;
import com.opencsv.exceptions.CsvValidationException;
import org.example.enums.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import com.opencsv.CSVReader;
import org.postgresql.util.PGobject;


// use the TableCols interface to handle different enums dynamically
public class DataLoader {

    // dynamically build the SQL INSERT statement using column names and indices provided by TableCols interface
    public static void loadCsvToTable(String csvFilePath, String tableName, TableCols[] columns) {
        try (Connection conn = TestConnect.getConnection();
             CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {

            // build the SQL statement dynamically based on the column mapping
            // example: INSERT INTO recipes (id, title, description, instructions, rating, image, duration, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            StringBuilder insertBuilder = new StringBuilder("INSERT INTO ");
            insertBuilder.append(tableName).append("(");
            for (TableCols column: columns) {
                insertBuilder.append(column.getColumnName()).append(", ");
            }
            insertBuilder.delete(insertBuilder.length() - 2, insertBuilder.length());
            insertBuilder.append(") VALUES (");
            for (TableCols column: columns) {
                insertBuilder.append("?, ");
            }
            insertBuilder.delete(insertBuilder.length() - 2, insertBuilder.length());
            insertBuilder.append(")");
            String sqlInsert = insertBuilder.toString();

            // read rows from the csv file and insert into the table
            try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
                String[] nextLine = reader.readNext();
                while ((nextLine = reader.readNext()) != null) {
                    for (TableCols column: columns) {
                        int colIndex = column.getColumnIndex();
                        int colType = column.getColumnType();
                        int paramIndex = colIndex + 1;
                        if (nextLine[colIndex] != null && !nextLine[colIndex].isEmpty()) {
                            System.out.println(nextLine[colIndex]);
                            switch (colType) {
                                case Types.VARCHAR:
                                case Types.LONGNVARCHAR:
                                    pstmt.setString(paramIndex, nextLine[colIndex]);
                                    break;
                                case Types.INTEGER:
                                    pstmt.setInt(paramIndex, Integer.parseInt(nextLine[colIndex]));
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
                    System.out.println();
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
        loadCsvToTable("src/main/resources/data/recipes.csv", "recipes", RecipeCols.values());
    }
}
