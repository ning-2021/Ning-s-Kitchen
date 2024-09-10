package org.example.enums;

import java.sql.Types;

public enum RecipeIngredientCols implements BaseColumnEnum {
    RECIPE_ID("recipe_id", 0, Types.INTEGER),
    INGREDIENT_ID("ingredient_id", 1, Types.INTEGER);

    private final ColumnInfo columnInfo;

    RecipeIngredientCols(String columnName, int columnIndex, int columnType) {
        this.columnInfo = new ColumnInfo(columnName, columnIndex, columnType);
    }
    @Override public ColumnInfo getColumnInfo() {
        return columnInfo;
    }
}