package org.example.enums;

public class ColumnInfo {
    private final String columnName;
    private final int columnIndex;
    private final int columnType;

    public ColumnInfo(String columnName, int columnIndex, int columnType) {
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.columnType = columnType;
    }

    public String getColumnName() {
        return columnName;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getColumnType() {
        return columnType;
    }
}
