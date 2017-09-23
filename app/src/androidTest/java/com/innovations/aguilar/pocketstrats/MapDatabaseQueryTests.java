package com.innovations.aguilar.pocketstrats;

import android.support.test.espresso.core.deps.guava.collect.ImmutableMap;
import android.support.test.runner.AndroidJUnit4;

import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapLocationDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSegmentDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpawnStatisticDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpecificTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapType;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;
import com.innovations.aguilar.pocketstrats.sql.query.SqlDataAccessor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class MapDatabaseQueryTests extends MapDatabaseTestFixture {
    static final Map<MapType, Integer> MapNumberByType = ImmutableMap.of(
            MapType.Assault, 4,
            MapType.Control, 4,
            MapType.Escort, 3,
            MapType.Hybrid_Assault_Escort, 4
    );
    static final Map<MapType, Integer> MapSegmentsByType = ImmutableMap.of(
            MapType.Assault, 2,
            MapType.Control, 3,
            MapType.Escort, 3,
            MapType.Hybrid_Assault_Escort, 3
    );
    static final int ExpectedMaps = MapNumberByType.get(MapType.Assault) +
            MapNumberByType.get(MapType.Control) +
            MapNumberByType.get(MapType.Escort) +
            MapNumberByType.get(MapType.Hybrid_Assault_Escort);
    static final int ExpectedMapSegments = (MapNumberByType.get(MapType.Assault) * MapSegmentsByType.get(MapType.Assault)) +
            (MapNumberByType.get(MapType.Control) * MapSegmentsByType.get(MapType.Control)) +
            (MapNumberByType.get(MapType.Escort) * MapSegmentsByType.get(MapType.Escort)) +
            (MapNumberByType.get(MapType.Hybrid_Assault_Escort) * MapSegmentsByType.get(MapType.Hybrid_Assault_Escort));
    static final int ExpectedMinimumMapLocations = (int)(ExpectedMapSegments * 1.5);
    static final int ExpectedMinimumMapStatistics = (int)(ExpectedMapSegments * 2.5);

    SqlDataAccessor accessor = null;
    @Before
    public void beforeTest() {
        accessor = new SqlDataAccessor(readableMapDb);
    }

    @After
    public void afterTest() {
        accessor = null;
    }

    @Test
    public void Accessor_Should_Read_All_Maps() throws Exception {
        List<MapDataDTO> maps = accessor.GetAllMaps();

        for (MapDataDTO map :
                maps) {
            log.get().debug(map.getMapName());
        }
        assertEquals(ExpectedMaps, maps.size());
    }
    @Test
    public void Accessor_Should_Read_Map_By_Id() throws Exception {
        int expectedMapId = 1;
        String expectedShortName = "Anubis";
        MapDataDTO map = accessor.GetMapById(expectedMapId);

        assertEquals(expectedMapId, map.getMapId());
        assertEquals(expectedShortName, map.getMapNameShort());
    }
    @Test
    public void Accessor_Should_Read_Maps_By_Type() throws Exception {
        int expectedMaps = MapNumberByType.get(MapType.Assault);
        List<MapDataDTO> maps = accessor.GetMapsByType(MapType.Assault);

        assertEquals(expectedMaps, maps.size());
    }

    @Test
    public void Accessor_Should_Read_All_Map_Segments() throws Exception {
        List<MapSegmentDTO> mapSegments = accessor.GetAllMapSegments();

        for (MapSegmentDTO segment :
                mapSegments) {
            log.get().debug(segment.getSegmentName());
        }
        assertEquals(ExpectedMapSegments, mapSegments.size());
    }
    @Test
    public void Accessor_Should_Read_Map_Segments_By_Map() throws Exception {
        int expectedMapId = 1;
        int expectedMapSegments = 2;
        List<MapSegmentDTO> mapSegments = accessor.GetMapSegmentsByMap(expectedMapId);

        assertEquals(expectedMapSegments, mapSegments.size());
    }
    @Test
    public void Accessor_Should_Read_Map_Locations_By_Map() throws Exception {
        List<MapDataDTO> maps = accessor.GetAllMaps();

        int LocationsCount = 0;
        for (MapDataDTO map :
                maps) {
            List<MapLocationDTO> mapLocations = accessor.GetMapLocationsByMap(map.getMapId());
            LocationsCount += mapLocations.size();
        }

        assertTrue(ExpectedMinimumMapLocations < LocationsCount);
    }
    @Test
    public void Accessor_Should_Read_Map_Locations_By_Segment() throws Exception {
        List<MapSegmentDTO> mapSegments = accessor.GetAllMapSegments();

        int LocationsCount = 0;
        for (MapSegmentDTO segment :
                mapSegments) {
            List<MapLocationDTO> mapLocations = accessor.GetMapLocationsBySegment(segment.getSegmentId());
            LocationsCount += mapLocations.size();
        }

        assertTrue(ExpectedMinimumMapLocations < LocationsCount);
    }
    @Test
    public void Accessor_Should_Read_Map_Spawn_Statistics_By_Map_Segment() throws Exception {
        List<MapSegmentDTO> mapSegments = accessor.GetAllMapSegments();

        int StatisticsCount = 0;
        for (MapSegmentDTO segment :
                mapSegments) {
            List<MapSpawnStatisticDTO> mapSpawnStats = accessor.GetMapSpawnStatsBySegment(segment.getSegmentId());
            StatisticsCount += mapSpawnStats.size();
        }

        assertTrue(ExpectedMinimumMapStatistics < StatisticsCount);
    }
    @Test
    public void Accessor_Should_Read_Map_Spawn_Statistics_By_Map_Location() throws Exception {
        List<MapSegmentDTO> mapSegments = accessor.GetAllMapSegments();

        int StatisticsCount = 0;
        for (MapSegmentDTO segment :
                mapSegments) {
            List<MapLocationDTO> mapLocations = accessor.GetMapLocationsBySegment(segment.getSegmentId());
            for (MapLocationDTO location :
                    mapLocations) {
                List<MapSpawnStatisticDTO> mapSpawnStats = accessor.GetMapSpawnStatsByLocation(location.getLocationId());
                StatisticsCount += mapSpawnStats.size();
            }
        }

        assertTrue(ExpectedMinimumMapStatistics < StatisticsCount);
    }


    @Test
    public void Accessor_Should_Read_Map_Subjects_By_Map() throws Exception {
        List<MapSubjectDTO> mapSubjects = accessor.GetMapSubjectsByMap(1 /*Anubis*/, SpawnSide.Attack);
        final int ExpectedMinimumSubjects = 4; // Forward, Point, Forward, Point

        int SubjectCount = mapSubjects.size();

        assertTrue(ExpectedMinimumSubjects < SubjectCount);
    }

    @Test
    public void Accessor_Should_Read_Map_Specific_Tips() throws Exception {
        List<MapSpecificTipDTO> mapSpecificTips = accessor.GetMapSpecificTipsByMap(1, SpawnSide.Attack);
        final int ExpectedMinimumTips = 1;

        int SubjectCount = mapSpecificTips.size();

        assertTrue(ExpectedMinimumTips < SubjectCount);
    }

    // TODO: Test and query beyond map specific tips
}
