package org.example.enums;

import java.sql.Types;

public enum RecipeTagCols implements BaseColumnEnum {
    RECIPE_ID("recipe_id", 0, Types.INTEGER),
    TAG_ID("tag_id", 1, Types.INTEGER);

    private final ColumnInfo columnInfo;

    RecipeTagCols(String columnName, int columnIndex, int columnType) {
        this.columnInfo = new ColumnInfo(columnName, columnIndex, columnType);
    }
    @Override public ColumnInfo getColumnInfo() {
        return columnInfo;
    }
}
