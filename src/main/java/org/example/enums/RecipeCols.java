package org.example.enums;

import java.sql.Types;

public enum RecipeCols implements BaseColumnEnum {
    // each constant in the RecipeCols enum represents a column in the recipes table
    ID("id", 0, Types.INTEGER), // ID represents the "id" column with an index of 0
    TITLE("title", 1, Types.VARCHAR),
    DESCRIPTION("description", 2, Types.VARCHAR),
    INSTRUCTIONS("instructions", 3, Types.OTHER),
    RATING("rating", 4, Types.NUMERIC),
    IMAGE("image", 5, Types.LONGNVARCHAR),
    DURATION("duration", 6, Types.INTEGER),
    CREATED_AT("created_at", 7, Types.TIMESTAMP);
    private final ColumnInfo columnInfo;

    RecipeCols(String columnName, int columnIndex, int columnType) {
        this.columnInfo = new ColumnInfo(columnName, columnIndex, columnType);
    }

    @Override
    public ColumnInfo getColumnInfo() {
        return columnInfo;
    }

}