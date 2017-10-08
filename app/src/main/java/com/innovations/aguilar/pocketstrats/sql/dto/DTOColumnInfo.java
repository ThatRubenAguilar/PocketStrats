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

    public String[] joinAndQualifyColumns(DTOColumnInfo otherColumnInfo, DTOColumnInfo ... moreOtherColumnInfo) {
        Set<String> joinedColumns = Sets.union(ColumnNames, otherColumnInfo.ColumnNames);
        if (moreOtherColumnInfo != null) {
            for (DTOColumnInfo columnInfo :
                    moreOtherColumnInfo) {
                joinedColumns = Sets.union(joinedColumns, columnInfo.ColumnNames);
            }
        }
        String[] qualifiedColumns = new String[joinedColumns.size()];
        int i = 0;
        for (String joinedColumn :
                joinedColumns) {
            if (ColumnNames.contains(joinedColumn))
                qualifiedColumns[i] = qualifyColumn(joinedColumn);
            else if (otherColumnInfo.ColumnNames.contains(joinedColumn))
                qualifiedColumns[i] = otherColumnInfo.qualifyColumn(joinedColumn);
            else if (moreOtherColumnInfo != null) {
                for (DTOColumnInfo columnInfo :
                        moreOtherColumnInfo) {
                    if (columnInfo.ColumnNames.contains(joinedColumn)) {
                        qualifiedColumns[i] = columnInfo.qualifyColumn(joinedColumn);
                        break;
                    }
                }
            }

            i++;
        }
        return qualifiedColumns;
    }

    public String innerJoin(DTOColumnInfo right, String leftUnqualifiedColumn, String rightUnqualifiedColumn) {
        return innerJoin(this, right, leftUnqualifiedColumn, rightUnqualifiedColumn);
    }
    public String leftJoin(DTOColumnInfo right, String leftUnqualifiedColumn, String rightUnqualifiedColumn) {
        return leftJoin(this, right, leftUnqualifiedColumn, rightUnqualifiedColumn);
    }

    public static String innerJoin(DTOColumnInfo left, DTOColumnInfo right,
                                   String leftUnqualifiedColumn, String rightUnqualifiedColumn) {
        return joinFormat(left.TableName, "INNER JOIN", right.TableName,
                left.qualifyColumn(leftUnqualifiedColumn), right.qualifyColumn(rightUnqualifiedColumn));
    }
    public static String innerJoin(String previousJoin, DTOColumnInfo left, DTOColumnInfo right,
                                   String leftUnqualifiedColumn, String rightUnqualifiedColumn) {
        String leftSide = previousJoin != null ? previousJoin : left.TableName;
        return joinFormat(leftSide, "INNER JOIN", right.TableName,
                left.qualifyColumn(leftUnqualifiedColumn), right.qualifyColumn(rightUnqualifiedColumn));
    }
    public static String leftJoin(DTOColumnInfo left, DTOColumnInfo right,
                                   String leftUnqualifiedColumn, String rightUnqualifiedColumn) {
        return joinFormat(left.TableName, "LEFT JOIN", right.TableName,
                left.qualifyColumn(leftUnqualifiedColumn), right.qualifyColumn(rightUnqualifiedColumn));
    }
    public static String leftJoin(String previousJoin, DTOColumnInfo left, DTOColumnInfo right,
                                   String leftUnqualifiedColumn, String rightUnqualifiedColumn) {
        String leftSide = previousJoin != null ? previousJoin : left.TableName;
        return joinFormat(leftSide, "LEFT JOIN", right.TableName,
                left.qualifyColumn(leftUnqualifiedColumn), right.qualifyColumn(rightUnqualifiedColumn));
    }

    private static String joinFormat(String left, String join, String right, String leftQualifiedColumn, String rightQualifiedColumn) {
        return String.format("%s %s %s ON %s = %s", left, join, right, leftQualifiedColumn, rightQualifiedColumn);
    }
}
