package com.innovations.aguilar.pocketstrats.sql.query;

import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapLocationDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpawnStatisticDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTypeSpawnTimeDTO;

import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class MapSpawnStatisticsSummaryGenerator {
    private SqlDataAccessor accessor;

    public MapSpawnStatisticsSummaryGenerator(SqlDataAccessor accessor) {
        this.accessor = accessor;
    }

    public SortedMap<MapLocationDTO, MapLocationSpawnAdvantageSummary> GenerateLocationSpawnAdvantageSummaries(MapDataDTO map) {
        MapTypeSpawnTimeDTO spawnTime = accessor.GetMapTypeSpawnTimeByType(map.getMapTypeId());

        List<MapLocationDTO> mapLocations = accessor.mapAccessor().GetMapLocationsByMap(map.getMapId());
        SortedMap<MapLocationDTO, MapLocationSpawnAdvantageSummary> locationSpawnStatsSummaryMap =
                new TreeMap<>(MapLocationDTO.LocationIdComparer);
        for (MapLocationDTO location :
                mapLocations) {
            List<MapSpawnStatisticDTO> locationStatistics = accessor.GetMapSpawnStatsByLocation(location.getLocationId());

            locationSpawnStatsSummaryMap.put(location,
                    new MapLocationSpawnAdvantageSummary(location, locationStatistics, spawnTime));
        }

        return Collections.unmodifiableSortedMap(locationSpawnStatsSummaryMap);
    }
}
