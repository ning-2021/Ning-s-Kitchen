package org.example.enums;

public interface BaseColumnEnum extends TableCols{
    ColumnInfo getColumnInfo();

    @Override
    default String getColumnName() {
        return getColumnInfo().getColumnName();
    }
    @Override
    default int getColumnIndex() {
        return getColumnInfo().getColumnIndex();
    }

    @Override
    default int getColumnType() {
        return getColumnInfo().getColumnType();
    }
}
