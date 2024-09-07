package org.example.enums;

import java.sql.Types;

public enum TagCols implements BaseColumnEnum {
    ID("id", 0, Types.INTEGER),
    NAME("name", 1, Types.VARCHAR),
    TYPE_ID("type_id", 2, Types.INTEGER);

    private final ColumnInfo columnInfo;

    TagCols(String columnName, int columnIndex, int columnType) {
        this.columnInfo = new ColumnInfo(columnName, columnIndex, columnType);
    }
    @Override public ColumnInfo getColumnInfo() {
        return columnInfo;
    }
}
