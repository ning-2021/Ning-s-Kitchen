package org.example.enums;
// RecipeCols enum implements the TableCols interface,
// ensuring that each column enum provides the methods to get the column name and index
public enum RecipeCols implements TableCols {
    // each constant in the RecipeCols enum represents a column in the recipes table
    ID("id", 0), // ID represents the "id" column with an index of 0
    TITLE("title", 1),
    DESCRIPTION("description", 2),
    INSTRUCTIONS("instructions", 3),
    RATING("rating", 4),
    IMAGE("image", 5),
    DURATION("duration", 6),
    CREATED_AT("created_at", 7);

    private final String columnName;
    private final int columnIndex;

    // private constructor that takes the column name and index
    // as parameters and assigns them to the respective fields
    RecipeCols(String columnName, int columnIndex) {
        this.columnName = columnName;
        this.columnIndex = columnIndex;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public int getColumnIndex() {
        return columnIndex;
    }
}