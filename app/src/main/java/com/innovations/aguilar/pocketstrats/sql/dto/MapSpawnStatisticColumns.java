package com.innovations.aguilar.pocketstrats.sql.dto;

import com.google.common.collect.Lists;

public class MapSpawnStatisticColumns extends DTOColumnInfo {

    public static final String StatisticIdColumn = "StatisticId";
    public static final String LocationIdColumn = "LocationId";
    public static final String SpawnSideIdColumn = "SpawnSideId";
    public static final String SpawnSideDescriptionColumn = "SpawnSideDescription";
    public static final String RunDescriptionColumn = "RunDescription";
    public static final String RunDurationColumn = "RunDuration";

    public MapSpawnStatisticColumns() {
        super("MapSpawnStatistics", Lists.newArrayList(StatisticIdColumn, LocationIdColumn,
                SpawnSideIdColumn, SpawnSideDescriptionColumn, RunDescriptionColumn, RunDurationColumn));
    }
}
