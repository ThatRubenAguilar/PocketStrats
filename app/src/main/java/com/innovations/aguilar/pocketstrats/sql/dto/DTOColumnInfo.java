package com.innovations.aguilar.pocketstrats.sql.dto;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public abstract class DTOColumnInfo {

    public final String TableName;

    public final Set<String> ColumnNames;
    public final String[] QualifiedColumnNames;

    protected DTOColumnInfo(String tableName, List<String> columnNames) {
        TableName = tableName;

        QualifiedColumnNames = new String[columnNames.size()];
        int i = 0;
        for (String columnName :
                columnNames) {
            QualifiedColumnNames[i] = qualifyColumn(columnName);
            i++;
        }

        ColumnNames = Sets.newLinkedHashSet(columnNames);
    }

    public String qualifyColumn(String columnName) {
        return String.format("%s.%s", TableName, columnName);
    }

    public String[] joinAndQualifyColumns(DTOColumnInfo otherColumnInfo) {
        Set<String> joinedColumns = Sets.union(ColumnNames, otherColumnInfo.ColumnNames);
        String[] qualifiedColumns = new String[joinedColumns.size()];
        int i = 0;
        for (String joinedColumn :
                joinedColumns) {
            if (ColumnNames.contains(joinedColumn))
                qualifiedColumns[i] = qualifyColumn(joinedColumn);
            else
                qualifiedColumns[i] = otherColumnInfo.qualifyColumn(joinedColumn);
            i++;
        }
        return qualifiedColumns;
    }
}
