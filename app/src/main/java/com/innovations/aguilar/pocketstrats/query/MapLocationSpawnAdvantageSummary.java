package com.innovations.aguilar.pocketstrats.query;

import com.innovations.aguilar.pocketstrats.dto.MapLocationDTO;
import com.innovations.aguilar.pocketstrats.dto.MapSpawnStatistic;
import com.innovations.aguilar.pocketstrats.dto.MapSpawnStatisticDTO;
import com.innovations.aguilar.pocketstrats.dto.MapTypeSpawnTimeDTO;
import com.innovations.aguilar.pocketstrats.dto.SpawnSide;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class MapLocationSpawnAdvantageSummary {
    private final MapLocationDTO location;
    private final List<MapSpawnStatisticDTO> statistics;
    private MapTypeSpawnTimeDTO spawnTime;
    private final SpawnAdvantageSummary attackSummary;
    private final SpawnAdvantageSummary defendSummary;

    public MapLocationSpawnAdvantageSummary(MapLocationDTO location, List<MapSpawnStatisticDTO> statistics, MapTypeSpawnTimeDTO spawnTime) {
        this.location = location;
        this.statistics = statistics;
        this.spawnTime = spawnTime;

        SortedMap<SpawnSide, List<MapSpawnStatisticDTO>> spawnSideMappedStats = MapSpawnStatistic.SplitBySpawnSide(statistics);
        List<MapSpawnStatisticDTO> attackStatsList;
        if (spawnSideMappedStats.containsKey(SpawnSide.Attack))
            attackStatsList = spawnSideMappedStats.get(SpawnSide.Attack);
        else
            attackStatsList = new ArrayList<>();
        this.attackSummary = new SpawnAdvantageSummary(attackStatsList, spawnTime);

        List<MapSpawnStatisticDTO> defendStatsList;
        if (spawnSideMappedStats.containsKey(SpawnSide.Defend))
            defendStatsList = spawnSideMappedStats.get(SpawnSide.Defend);
        else
            defendStatsList = new ArrayList<>();
        this.defendSummary = new SpawnAdvantageSummary(defendStatsList, spawnTime);
    }


    public double getSpawnAdvantage(SpawnSummaryQuery query, SpawnSide spawnSide) {
        double attackSpawnPenalty = attackSummary.getTotalSpawnPenalty(query);
        double defendSpawnPenalty = defendSummary.getTotalSpawnPenalty(query);
        switch (spawnSide) {
            case Attack:
                // Attack spawns X% faster/slower than Defend
                return (defendSpawnPenalty / attackSpawnPenalty) - 1.0;
            case Defend:
                // Defend spawns X% faster/slower than Attack
                return (attackSpawnPenalty / defendSpawnPenalty) - 1.0;

        }

        throw new IllegalArgumentException(String.format("SpawnSide '%s' does not exist.",
                spawnSide != null ? spawnSide.toString() : "null"));
    }

    public SpawnAdvantageSummary getSpawnAdvantageSummary(SpawnSide spawnSide) {
        switch (spawnSide) {
            case Attack:
                return attackSummary;
            case Defend:
                return defendSummary;
        }

        throw new IllegalArgumentException(String.format("SpawnSide '%s' does not exist.",
                spawnSide != null ? spawnSide.toString() : "null"));
    }
}
