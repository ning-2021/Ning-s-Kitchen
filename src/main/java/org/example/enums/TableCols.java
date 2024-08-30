package org.example.enums;
// It defines methods to get column names and indices,
// ensuring a consistent contract for all table column enums.
// It also ensures like RecipeCols and the other enums
// can be used interchangeably in the DataLoader class
public interface TableCols {
    String getColumnName();
    int getColumnIndex();
}