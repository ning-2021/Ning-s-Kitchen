package org.example.enums;

import java.sql.Types;

public enum CategoryCols implements BaseColumnEnum {
    ID("id", 0, Types.INTEGER),
    NAME("name", 1, Types.VARCHAR);
    private final ColumnInfo columnInfo;

    CategoryCols(String columnName, int columnIndex, int columnType) {
        this.columnInfo = new ColumnInfo(columnName, columnIndex, columnType);
    }
    @Override
    public ColumnInfo getColumnInfo() {
        return columnInfo;
    }
}
