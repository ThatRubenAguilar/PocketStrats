package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

import com.google.common.base.Preconditions;
import com.innovations.aguilar.pocketstrats.sql.query.DTOFromCursorFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class MapSpawnStatistic implements MapSpawnStatisticDTO {
    public static DTOFromCursorFactory<MapSpawnStatisticDTO> Factory =
            new DTOFromCursorFactory<MapSpawnStatisticDTO>() {
                @Override
                public MapSpawnStatisticDTO Create(Cursor c) { return new MapSpawnStatistic(c); }
            };

    private final int statisticId;
    private final int locationId;
    private final SpawnSide spawnSideId;
    private final String spawnSideDescription;
    private final String runDescription;
    private final double runDuration;

    public MapSpawnStatistic(int statisticId, int locationId, SpawnSide spawnSideId, String spawnSideDescription, String runDescription, double runDuration) {
        this.statisticId = statisticId;
        this.locationId = locationId;
        this.spawnSideId = spawnSideId;
        this.spawnSideDescription = spawnSideDescription;
        this.runDescription = runDescription;
        this.runDuration = runDuration;
    }

    public MapSpawnStatistic(Cursor c) {
        this.statisticId = c.getInt(c.getColumnIndex(StatisticIdColumn));
        this.locationId = c.getInt(c.getColumnIndex(LocationIdColumn));
        int spawnSideIdRaw = c.getInt(c.getColumnIndex(SpawnSideIdColumn));
        this.spawnSideId = SpawnSide.FromInt(spawnSideIdRaw);
        this.spawnSideDescription = c.getString(c.getColumnIndex(SpawnSideDescriptionColumn));
        this.runDescription = c.getString(c.getColumnIndex(RunDescriptionColumn));
        this.runDuration = c.getDouble(c.getColumnIndex(RunDurationColumn));
    }

    @Override
    public int getStatisticId() {
        return statisticId;
    }

    @Override
    public int getLocationId() {
        return locationId;
    }

    @Override
    public SpawnSide getSpawnSideId() {
        return spawnSideId;
    }

    @Override
    public String getSpawnSideDescription() {
        return spawnSideDescription;
    }

    @Override
    public String getRunDescription() {
        return runDescription;
    }

    @Override
    public double getRunDuration() {
        return runDuration;
    }

    public static final String StatisticIdColumn = "StatisticId";
    public static final String LocationIdColumn = "LocationId";
    public static final String SpawnSideIdColumn = "SpawnSideId";
    public static final String SpawnSideDescriptionColumn = "SpawnSideDescription";
    public static final String RunDescriptionColumn = "RunDescription";
    public static final String RunDurationColumn = "RunDuration";

    public static final String TableName = "MapSpawnStatistics";

    public static final String[] ColumnNames = {
            String.format("%s.%s", TableName, StatisticIdColumn),
            String.format("%s.%s", TableName, LocationIdColumn),
            String.format("%s.%s", TableName, SpawnSideIdColumn),
            String.format("%s.%s", TableName, SpawnSideDescriptionColumn),
            String.format("%s.%s", TableName, RunDescriptionColumn),
            String.format("%s.%s", TableName, RunDurationColumn),
    };


    public static SortedMap<SpawnSide, List<MapSpawnStatisticDTO>> SplitBySpawnSide(List<MapSpawnStatisticDTO> statistics) {
        Preconditions.checkNotNull(statistics, "'statistics' cannot be null.");
        SortedMap<SpawnSide, List<MapSpawnStatisticDTO>> spawnDataMap = new TreeMap<>();
        List<MapSpawnStatisticDTO> currentList;
        for (MapSpawnStatisticDTO spawnStatistic :
                statistics) {
            if (!spawnDataMap.containsKey(spawnStatistic.getSpawnSideId())) {
                currentList = new ArrayList<>();
                spawnDataMap.put(spawnStatistic.getSpawnSideId(), currentList);
            }
            else {
                currentList = spawnDataMap.get(spawnStatistic.getSpawnSideId());
            }
            currentList.add(spawnStatistic);
        }
        return Collections.unmodifiableSortedMap(spawnDataMap);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MapSpawnStatistic{");
        sb.append("statisticId=").append(statisticId);
        sb.append(", locationId=").append(locationId);
        sb.append(", spawnSideId=").append(spawnSideId);
        sb.append(", spawnSideDescription='").append(spawnSideDescription).append('\'');
        sb.append(", runDescription='").append(runDescription).append('\'');
        sb.append(", runDuration=").append(runDuration);
        sb.append('}');
        return sb.toString();
    }
}
