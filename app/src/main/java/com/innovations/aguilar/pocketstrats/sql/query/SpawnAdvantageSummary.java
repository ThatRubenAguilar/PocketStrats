package com.innovations.aguilar.pocketstrats.sql.query;

import com.innovations.aguilar.pocketstrats.sql.dto.MapSpawnStatisticDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTypeSpawnTimeDTO;
import com.google.common.base.Preconditions;

import java.util.Collections;
import java.util.List;

public class SpawnAdvantageSummary {

    private final MapSpawnStatisticDTO minSpawnStatistic;
    private final MapTypeSpawnTimeDTO spawnTime;

    public SpawnAdvantageSummary(List<MapSpawnStatisticDTO> statistics, MapTypeSpawnTimeDTO spawnTime) {
        CheckAllSameSide(statistics);
        Preconditions.checkNotNull(spawnTime, "'spawnTime' cannot be null.");
        this.minSpawnStatistic = Collections.min(statistics, MapSpawnStatisticDTO.RunDurationComparer);
        this.spawnTime = spawnTime;
    }

    void CheckAllSameSide(List<MapSpawnStatisticDTO> statistics) {
        Preconditions.checkNotNull(statistics, "'statistics' cannot be null.");
        if (!statistics.isEmpty()) {
            MapSpawnStatisticDTO firstStatistic = statistics.get(0);
            for (MapSpawnStatisticDTO spawnStatistic :
                    statistics) {
                Preconditions.checkArgument(spawnStatistic.getSpawnSideId() == firstStatistic.getSpawnSideId(),
                        "All spawn sides must match for summary, expected '%s', found '%s'",
                        firstStatistic.getSpawnSideId(), spawnStatistic.getSpawnSideId());
            }
        }
    }

    public MapSpawnStatisticDTO getMinSpawnStatistic() {
        return minSpawnStatistic;
    }

    public double getTotalSpawnPenalty(SpawnSummaryQuery query) {
        switch (query) {
            case MIN:
                return minSpawnStatistic.getRunDuration() + spawnTime.getMinSpawnTime();
            case MAX:
                return minSpawnStatistic.getRunDuration() + spawnTime.getMaxSpawnTime();
            case MIN_OVERTIME:
                return minSpawnStatistic.getRunDuration() + spawnTime.getMinSpawnTime() + spawnTime.getOvertimeSpawnTime();
            case MAX_OVERTIME:
                return minSpawnStatistic.getRunDuration() + spawnTime.getMinSpawnTime() + spawnTime.getOvertimeSpawnTime();
        }
        throw new IllegalArgumentException(String.format("SummaryQuery '%s' does not exist.",
                query != null ? query.toString() : "null"));
    }

}
