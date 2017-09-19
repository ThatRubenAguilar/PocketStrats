package com.innovations.aguilar.pocketstrats;

import android.support.test.runner.AndroidJUnit4;

import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapLocationDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;
import com.innovations.aguilar.pocketstrats.sql.query.SqlDataAccessor;
import com.innovations.aguilar.pocketstrats.sql.query.MapLocationSpawnAdvantageSummary;
import com.innovations.aguilar.pocketstrats.sql.query.MapSpawnStatisticsSummaryGenerator;
import com.innovations.aguilar.pocketstrats.sql.query.SpawnSummaryQuery;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.SortedMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MapSummaryStatisticsQueryTests extends MapDatabaseTestFixture {


    SqlDataAccessor accessor = null;
    MapSpawnStatisticsSummaryGenerator spawnStatGenerator = null;
    @Before
    public void beforeTest() {
        accessor = new SqlDataAccessor(readableMapDb);
        spawnStatGenerator = new MapSpawnStatisticsSummaryGenerator(accessor);
    }

    @After
    public void afterTest() {
        accessor = null;
        spawnStatGenerator = null;
    }

    @Test
    public void Spawn_Advantage_Summary_Statistics_Should_Be_Calculated_For_Map() throws Exception {
        MapDataDTO anubis = accessor.GetMapById(1);
        SortedMap<MapLocationDTO, MapLocationSpawnAdvantageSummary> anubisLocationStats =
                spawnStatGenerator.GenerateLocationSpawnAdvantageSummaries(anubis);

        List<MapLocationDTO> anubisLocations = accessor.GetMapLocationsByMap(anubis.getMapId());
        MapLocationDTO firstForwardChoke = anubisLocations.get(0);

        MapLocationSpawnAdvantageSummary forwardChokeSummary = anubisLocationStats.get(firstForwardChoke);

        double minTotalAttackSpawnPenalty = forwardChokeSummary
                .getSpawnAdvantageSummary(SpawnSide.Attack).getTotalSpawnPenalty(SpawnSummaryQuery.MIN);
        double expectedMinTotalAttackSpawnPenalty = 23.6; // Pulled from original data sheet
        Assert.assertTrue(Math.abs(minTotalAttackSpawnPenalty - expectedMinTotalAttackSpawnPenalty) < 0.1);

        double minAttackSpawnAdvantage = forwardChokeSummary.getSpawnAdvantage(SpawnSummaryQuery.MIN, SpawnSide.Attack);
        double expectedMinAttackSpawnAdvantage = .5551; // Pulled from original data sheet
        Assert.assertTrue(Math.abs(minAttackSpawnAdvantage - expectedMinAttackSpawnAdvantage) < 0.0001);

    }
}
