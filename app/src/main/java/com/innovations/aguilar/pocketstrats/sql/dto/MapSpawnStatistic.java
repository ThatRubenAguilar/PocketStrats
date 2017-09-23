package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
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
        this.statisticId = c.getInt(c.getColumnIndex(Columns.StatisticIdColumn));
        this.locationId = c.getInt(c.getColumnIndex(Columns.LocationIdColumn));
        int spawnSideIdRaw = c.getInt(c.getColumnIndex(Columns.SpawnSideIdColumn));
        this.spawnSideId = SpawnSide.FromInt(spawnSideIdRaw);
        this.spawnSideDescription = c.getString(c.getColumnIndex(Columns.SpawnSideDescriptionColumn));
        this.runDescription = c.getString(c.getColumnIndex(Columns.RunDescriptionColumn));
        this.runDuration = c.getDouble(c.getColumnIndex(Columns.RunDurationColumn));
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


    public static final MapSpawnStatisticColumns Columns = new MapSpawnStatisticColumns();


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

