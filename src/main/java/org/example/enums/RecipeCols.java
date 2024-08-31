package org.example.enums;

import java.sql.Types;

// RecipeCols enum implements the TableCols interface,
// ensuring that each column enum provides the methods to get the column name and index
public enum RecipeCols implements TableCols {
    // each constant in the RecipeCols enum represents a column in the recipes table
    ID("id", 0, Types.INTEGER), // ID represents the "id" column with an index of 0
    TITLE("title", 1, Types.VARCHAR),
    DESCRIPTION("description", 2, Types.VARCHAR),
    INSTRUCTIONS("instructions", 3, Types.OTHER),
    RATING("rating", 4, Types.NUMERIC),
    IMAGE("image", 5, Types.LONGNVARCHAR),
    DURATION("duration", 6, Types.INTEGER),
    CREATED_AT("created_at", 7, Types.TIMESTAMP);

    private final String columnName;
    private final int columnIndex;
    private final int columnType;

    // private constructor that takes the column name and index
    // as parameters and assigns them to the respective fields
    RecipeCols(String columnName, int columnIndex, int columnType) {
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.columnType = columnType;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public int getColumnIndex() {
        return columnIndex;
    }

    @Override
    public int getColumnType() { return columnType; }
}