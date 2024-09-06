package org.example.enums;

public interface BaseColumnEnum {
    ColumnInfo getColumnInfo();

    default String getColumnName() {
        return getColumnInfo().columnName();
    }

    default int getColumnIndex() {
        return getColumnInfo().columnIndex();
    }


    default int getColumnType() {
        return getColumnInfo().columnType();
    }
}
