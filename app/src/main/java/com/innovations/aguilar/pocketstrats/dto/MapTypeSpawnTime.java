package com.innovations.aguilar.pocketstrats.dto;

import android.database.Cursor;

public class MapTypeSpawnTime implements MapTypeSpawnTimeDTO {
    private final int statisticId;
    private final double minSpawnTime;
    private final double maxSpawnTime;
    private final MapType mapTypeId;
    private final double overtimeSpawnTime;

    public MapTypeSpawnTime(int statisticId, double minSpawnTime, double maxSpawnTime, MapType mapTypeId, double overtimeSpawnTime) {
        this.statisticId = statisticId;
        this.minSpawnTime = minSpawnTime;
        this.maxSpawnTime = maxSpawnTime;
        this.mapTypeId = mapTypeId;
        this.overtimeSpawnTime = overtimeSpawnTime;
    }


    public MapTypeSpawnTime(Cursor c) {
        this.statisticId = c.getInt(c.getColumnIndex(StatisticIdColumn));
        this.minSpawnTime = c.getDouble(c.getColumnIndex(MinSpawnTimeColumn));
        this.maxSpawnTime = c.getDouble(c.getColumnIndex(MaxSpawnTimeColumn));
        int mapTypeIdRaw = c.getInt(c.getColumnIndex(MapTypeIdColumn));
        this.mapTypeId = MapType.FromInt(mapTypeIdRaw);
        this.overtimeSpawnTime = c.getDouble(c.getColumnIndex(OvertimeSpawnTimeColumn));
    }

    @Override
    public int getStatisticId() {
        return statisticId;
    }

    @Override
    public double getMinSpawnTime() {
        return minSpawnTime;
    }

    @Override
    public double getMaxSpawnTime() {
        return maxSpawnTime;
    }

    @Override
    public MapType getMapTypeId() {
        return mapTypeId;
    }

    @Override
    public double getOvertimeSpawnTime() {
        return overtimeSpawnTime;
    }

    public static final String StatisticIdColumn = "StatisticId";
    public static final String MinSpawnTimeColumn = "MinSpawnTime";
    public static final String MaxSpawnTimeColumn = "MaxSpawnTime";
    public static final String MapTypeIdColumn = "MapTypeId";
    public static final String OvertimeSpawnTimeColumn = "OvertimeSpawnTime";

    public static final String TableName = "MapTypeSpawnTimes";

    public static final String[] ColumnNames = {
            String.format("%s.%s", TableName, StatisticIdColumn),
            String.format("%s.%s", TableName, MinSpawnTimeColumn),
            String.format("%s.%s", TableName, MaxSpawnTimeColumn),
            String.format("%s.%s", TableName, MapTypeIdColumn),
            String.format("%s.%s", TableName, OvertimeSpawnTimeColumn)
    };


}
