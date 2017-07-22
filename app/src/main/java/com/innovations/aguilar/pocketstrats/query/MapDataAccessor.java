package com.innovations.aguilar.pocketstrats.query;

import android.database.Cursor;
import android.database.sqlite.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.innovations.aguilar.pocketstrats.dto.MapData;
import com.innovations.aguilar.pocketstrats.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.dto.MapLocation;
import com.innovations.aguilar.pocketstrats.dto.MapLocationDTO;
import com.innovations.aguilar.pocketstrats.dto.MapSegment;
import com.innovations.aguilar.pocketstrats.dto.MapSegmentDTO;
import com.innovations.aguilar.pocketstrats.dto.MapSpawnStatistic;
import com.innovations.aguilar.pocketstrats.dto.MapSpawnStatisticDTO;
import com.innovations.aguilar.pocketstrats.dto.MapType;
import com.innovations.aguilar.pocketstrats.dto.MapTypeSpawnTime;
import com.innovations.aguilar.pocketstrats.dto.MapTypeSpawnTimeDTO;

public class MapDataAccessor {
    private SQLiteDatabase db;

    public MapDataAccessor(SQLiteDatabase db) {

        this.db = db;
    }

    <T> List<T> MakeListFromCursor(Cursor c, DTOFromCursorFactory<T> objectFactory) {
        List<T> list = new ArrayList<>();
        while (c.moveToNext())
        {
            list.add(objectFactory.Create(c));
        }
        return Collections.unmodifiableList(list);
    }

    public Cursor GetAllMapsCursor() {
        return db.query(MapData.TableName, MapData.ColumnNames, null, null, null, null, null);
    }
    public List<MapDataDTO> GetAllMaps() {
        try (Cursor c = GetAllMapsCursor()) {
            return MakeListFromCursor(c, MapDTOFactory.Instance);
        }
    }

    public Cursor GetMapsCursorById(int mapId) {
        String whereClause = String.format("%s = ?", MapData.MapIdColumn);
        String[] whereArgs = { Integer.toString(mapId) };
        return db.query(MapData.TableName, MapData.ColumnNames, whereClause, whereArgs, null, null, null);
    }
    public MapDataDTO GetMapById(int mapId) {
        try (Cursor c = GetMapsCursorById(mapId)) {
            return MakeListFromCursor(c, MapDTOFactory.Instance).get(0);
        }
    }
    public Cursor GetMapsCursorByType(MapType mapType) {
        String whereClause = String.format("%s = ?", MapData.MapTypeIdColumn);
        String[] whereArgs = { Integer.toString(mapType.typeId) };
        return db.query(MapData.TableName, MapData.ColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapDataDTO> GetMapsByType(MapType mapType) {
        try (Cursor c = GetMapsCursorByType(mapType)) {
            return MakeListFromCursor(c, MapDTOFactory.Instance);
        }
    }


    public Cursor GetAllMapSegmentsCursor() {
        return db.query(MapSegment.TableName, MapSegment.ColumnNames, null, null, null, null, null);
    }
    public List<MapSegmentDTO> GetAllMapSegments() {
        try (Cursor c = GetAllMapSegmentsCursor()) {
            return MakeListFromCursor(c, MapSegmentDTOFactory.Instance);
        }
    }

    public Cursor GetMapSegmentsCursorByMap(int mapId) {
        String whereClause = String.format("%s = ?", MapSegment.MapIdColumn);
        String[] whereArgs = { Integer.toString(mapId) };
        return db.query(MapSegment.TableName, MapSegment.ColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapSegmentDTO> GetMapSegmentsByMap(int mapId) {
        try (Cursor c = GetMapSegmentsCursorByMap(mapId)) {
            return MakeListFromCursor(c, MapSegmentDTOFactory.Instance);
        }
    }


    public Cursor GetMapLocationsCursorByMap(int mapId) {
        String whereClause = String.format("%s = ?", MapLocation.MapIdColumn);
        String[] whereArgs = { Integer.toString(mapId) };
        return db.query(MapLocation.TableName, MapLocation.ColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapLocationDTO> GetMapLocationsByMap(int mapId) {
        try (Cursor c = GetMapLocationsCursorByMap(mapId)) {
            return MakeListFromCursor(c, MapLocationDTOFactory.Instance);
        }
    }

    public Cursor GetMapLocationsCursorBySegment(int segmentId) {
        String whereClause = String.format("%s = ?", MapLocation.SegmentIdColumn);
        String[] whereArgs = { Integer.toString(segmentId) };
        return db.query(MapLocation.TableName, MapLocation.ColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapLocationDTO> GetMapLocationsBySegment(int segmentId) {
        try (Cursor c = GetMapLocationsCursorBySegment(segmentId)) {
            return MakeListFromCursor(c, MapLocationDTOFactory.Instance);
        }
    }

    public Cursor GetMapSpawnStatsCursorBySegment(int segmentId) {
        String whereClause = String.format("%s = ?", MapLocation.SegmentIdColumn);
        String[] whereArgs = { Integer.toString(segmentId) };
        String tableName = String.format("%s INNER JOIN %s ON %s.%s = %s.%s",
                MapSpawnStatistic.TableName, MapLocation.TableName,
                MapSpawnStatistic.TableName, MapSpawnStatistic.LocationIdColumn,
                MapLocation.TableName, MapLocation.LocationIdColumn
                );

        return db.query(tableName, MapSpawnStatistic.ColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapSpawnStatisticDTO> GetMapSpawnStatsBySegment(int segmentId) {
        try (Cursor c = GetMapSpawnStatsCursorBySegment(segmentId)) {
            return MakeListFromCursor(c, MapSpawnStatisticDTOFactory.Instance);
        }
    }


    public Cursor GetMapSpawnStatsCursorByLocation(int locationId) {
        String whereClause = String.format("%s = ?", MapSpawnStatistic.LocationIdColumn);
        String[] whereArgs = { Integer.toString(locationId) };
        return db.query(MapSpawnStatistic.TableName, MapSpawnStatistic.ColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapSpawnStatisticDTO> GetMapSpawnStatsByLocation(int locationId) {
        try (Cursor c = GetMapSpawnStatsCursorByLocation(locationId)) {
            return MakeListFromCursor(c, MapSpawnStatisticDTOFactory.Instance);
        }
    }


    public Cursor GetAllMapTypeSpawnTimesCursor() {
        return db.query(MapTypeSpawnTime.TableName, MapTypeSpawnTime.ColumnNames, null, null, null, null, null);
    }

    public List<MapTypeSpawnTimeDTO> GetAllMapTypeSpawnTimes() {
        try (Cursor c = GetAllMapTypeSpawnTimesCursor()) {
            return MakeListFromCursor(c, MapTypeSpawnTimeDTOFactory.Instance);
        }
    }


    public Cursor GetMapTypeSpawnTimesCursorByType(MapType mapType) {
        String whereClause = String.format("%s = ?", MapTypeSpawnTime.MapTypeIdColumn);
        String[] whereArgs = { Integer.toString(mapType.typeId) };
        return db.query(MapTypeSpawnTime.TableName, MapTypeSpawnTime.ColumnNames, whereClause, whereArgs, null, null, null);
    }

    public MapTypeSpawnTimeDTO GetMapTypeSpawnTimeByType(MapType mapType) {
        try (Cursor c = GetMapTypeSpawnTimesCursorByType(mapType)) {
            return MakeListFromCursor(c, MapTypeSpawnTimeDTOFactory.Instance).get(0);
        }
    }

    // TODO: Common query cache mechanisms
    // NOTE: Either cache everything we need or nothing and keep db open, as the db itself is cached while open.
    // TODO: Perf test in memory vs db

    interface DTOFromCursorFactory<T> {
        T Create(Cursor c);
    }
    static class MapDTOFactory implements DTOFromCursorFactory<MapDataDTO> {
        static final MapDTOFactory Instance = new MapDTOFactory();
        @Override
        public MapDataDTO Create(Cursor c) {
            return new MapData(c);
        }
    }
    static class MapSegmentDTOFactory implements DTOFromCursorFactory<MapSegmentDTO> {
        static final MapSegmentDTOFactory Instance = new MapSegmentDTOFactory();
        @Override
        public MapSegmentDTO Create(Cursor c) {
            return new MapSegment(c);
        }
    }
    static class MapLocationDTOFactory implements DTOFromCursorFactory<MapLocationDTO> {
        static final MapLocationDTOFactory Instance = new MapLocationDTOFactory();
        @Override
        public MapLocationDTO Create(Cursor c) {
            return new MapLocation(c);
        }
    }
    static class MapSpawnStatisticDTOFactory implements DTOFromCursorFactory<MapSpawnStatisticDTO> {
        static final MapSpawnStatisticDTOFactory Instance = new MapSpawnStatisticDTOFactory();
        @Override
        public MapSpawnStatisticDTO Create(Cursor c) {
            return new MapSpawnStatistic(c);
        }
    }
    static class MapTypeSpawnTimeDTOFactory implements DTOFromCursorFactory<MapTypeSpawnTimeDTO> {
        static final MapTypeSpawnTimeDTOFactory Instance = new MapTypeSpawnTimeDTOFactory();
        @Override
        public MapTypeSpawnTimeDTO Create(Cursor c) {
            return new MapTypeSpawnTime(c);
        }
    }
}
