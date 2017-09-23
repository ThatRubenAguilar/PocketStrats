package com.innovations.aguilar.pocketstrats.sql.dto;

import com.google.common.collect.Lists;

public class MapTypeSpawnTimeColumns extends DTOColumnInfo {

    public static final String StatisticIdColumn = "StatisticId";
    public static final String MinSpawnTimeColumn = "MinSpawnTime";
    public static final String MaxSpawnTimeColumn = "MaxSpawnTime";
    public static final String MapTypeIdColumn = "MapTypeId";
    public static final String OvertimeSpawnTimeColumn = "OvertimeSpawnTime";
    public MapTypeSpawnTimeColumns() {
        super("MapTypeSpawnTimes", Lists.newArrayList(StatisticIdColumn,
                MinSpawnTimeColumn, MaxSpawnTimeColumn, MapTypeIdColumn, OvertimeSpawnTimeColumn));
    }
}
