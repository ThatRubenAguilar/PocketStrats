package com.innovations.aguilar.pocketstrats.sql.query;

import android.database.Cursor;
import android.database.sqlite.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.innovations.aguilar.pocketstrats.sql.dto.HeroData;
import com.innovations.aguilar.pocketstrats.sql.dto.HeroDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapData;
import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapLocation;
import com.innovations.aguilar.pocketstrats.sql.dto.MapLocationDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSegment;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSegmentDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpawnStatistic;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpawnStatisticDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpecificTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpecificTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubject;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapType;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTypeSpawnTime;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTypeSpawnTimeDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;

public class SqlDataAccessor implements AutoCloseable {
    private SQLiteDatabase readableDb;

    public SqlDataAccessor(SQLiteDatabase readableDb) {
        this.readableDb = readableDb;
    }

    // TODO: Add MapTypeTips, MapTips, MapSegmentTips, MapLocationTips to database and accessors

    <T> List<T> MakeListFromCursor(Cursor c, DTOFromCursorFactory<T> objectFactory) {
        List<T> list = new ArrayList<>();
        while (c.moveToNext())
        {
            list.add(objectFactory.Create(c));
        }
        return Collections.unmodifiableList(list);
    }

    public Cursor GetAllHerosCursor() {
        return readableDb.query(HeroData.TableName, HeroData.ColumnNames, null, null, null, null, null);
    }

    public List<HeroDataDTO> GetAllHeros() {
        try (Cursor c = GetAllHerosCursor()) {
            return MakeListFromCursor(c, HeroData.Factory);
        }
    }

    public Cursor GetMapSubjectsCursorByMap(Integer mapId, SpawnSide side) {
        String whereClause = String.format("%s = ? AND %s = ?",
                MapData.MapIdColumn, MapSubject.SpawnSideIdColumn);
        String mapIdStr = "NULL";
        if (mapId != null)
            mapIdStr = mapId.toString();
        String[] whereArgs = {
                mapIdStr,
                Integer.toString(side.spawnSideId) };
        return readableDb.query(MapSubject.TableName, MapSubject.ColumnNames, whereClause, whereArgs, null, null, null);
    }
    public List<MapSubjectDTO> GetMapSubjectsByMap(Integer mapId, SpawnSide side) {
        try (Cursor c = GetMapSubjectsCursorByMap(mapId, side)) {
            return MakeListFromCursor(c, MapSubject.Factory);
        }
    }

    public Cursor GetAllMapsCursor() {
        return readableDb.query(MapData.TableName, MapData.ColumnNames, null, null, null, null, null);
    }

    public List<MapDataDTO> GetAllMaps() {
        try (Cursor c = GetAllMapsCursor()) {
            return MakeListFromCursor(c, MapData.Factory);
        }
    }

    public Cursor GetMapsCursorById(int mapId) {
        String whereClause = String.format("%s = ?", MapData.MapIdColumn);
        String[] whereArgs = { Integer.toString(mapId) };
        return readableDb.query(MapData.TableName, MapData.ColumnNames, whereClause, whereArgs, null, null, null);
    }
    public MapDataDTO GetMapById(int mapId) {
        try (Cursor c = GetMapsCursorById(mapId)) {
            return MakeListFromCursor(c, MapData.Factory).get(0);
        }
    }
    public Cursor GetMapsCursorByType(MapType mapType) {
        String whereClause = String.format("%s = ?", MapData.MapTypeIdColumn);
        String[] whereArgs = { Integer.toString(mapType.typeId) };
        return readableDb.query(MapData.TableName, MapData.ColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapDataDTO> GetMapsByType(MapType mapType) {
        try (Cursor c = GetMapsCursorByType(mapType)) {
            return MakeListFromCursor(c, MapData.Factory);
        }
    }


    public Cursor GetAllMapSegmentsCursor() {
        return readableDb.query(MapSegment.TableName, MapSegment.ColumnNames, null, null, null, null, null);
    }
    public List<MapSegmentDTO> GetAllMapSegments() {
        try (Cursor c = GetAllMapSegmentsCursor()) {
            return MakeListFromCursor(c, MapSegment.Factory);
        }
    }

    public Cursor GetMapSegmentsCursorByMap(int mapId) {
        String whereClause = String.format("%s = ?", MapSegment.MapIdColumn);
        String[] whereArgs = { Integer.toString(mapId) };
        return readableDb.query(MapSegment.TableName, MapSegment.ColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapSegmentDTO> GetMapSegmentsByMap(int mapId) {
        try (Cursor c = GetMapSegmentsCursorByMap(mapId)) {
            return MakeListFromCursor(c, MapSegment.Factory);
        }
    }


    public Cursor GetMapLocationsCursorByMap(int mapId) {
        String whereClause = String.format("%s = ?", MapLocation.MapIdColumn);
        String[] whereArgs = { Integer.toString(mapId) };
        return readableDb.query(MapLocation.TableName, MapLocation.ColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapLocationDTO> GetMapLocationsByMap(int mapId) {
        try (Cursor c = GetMapLocationsCursorByMap(mapId)) {
            return MakeListFromCursor(c, MapLocation.Factory);
        }
    }

    public Cursor GetMapLocationsCursorBySegment(int segmentId) {
        String whereClause = String.format("%s = ?", MapLocation.SegmentIdColumn);
        String[] whereArgs = { Integer.toString(segmentId) };
        return readableDb.query(MapLocation.TableName, MapLocation.ColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapLocationDTO> GetMapLocationsBySegment(int segmentId) {
        try (Cursor c = GetMapLocationsCursorBySegment(segmentId)) {
            return MakeListFromCursor(c, MapLocation.Factory);
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

        return readableDb.query(tableName, MapSpawnStatistic.ColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapSpawnStatisticDTO> GetMapSpawnStatsBySegment(int segmentId) {
        try (Cursor c = GetMapSpawnStatsCursorBySegment(segmentId)) {
            return MakeListFromCursor(c, MapSpawnStatistic.Factory);
        }
    }


    public Cursor GetMapSpawnStatsCursorByLocation(int locationId) {
        String whereClause = String.format("%s = ?", MapSpawnStatistic.LocationIdColumn);
        String[] whereArgs = { Integer.toString(locationId) };
        return readableDb.query(MapSpawnStatistic.TableName, MapSpawnStatistic.ColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapSpawnStatisticDTO> GetMapSpawnStatsByLocation(int locationId) {
        try (Cursor c = GetMapSpawnStatsCursorByLocation(locationId)) {
            return MakeListFromCursor(c, MapSpawnStatistic.Factory);
        }
    }


    public Cursor GetAllMapTypeSpawnTimesCursor() {
        return readableDb.query(MapTypeSpawnTime.TableName, MapTypeSpawnTime.ColumnNames, null, null, null, null, null);
    }

    public List<MapTypeSpawnTimeDTO> GetAllMapTypeSpawnTimes() {
        try (Cursor c = GetAllMapTypeSpawnTimesCursor()) {
            return MakeListFromCursor(c, MapTypeSpawnTime.Factory);
        }
    }


    public Cursor GetMapTypeSpawnTimesCursorByType(MapType mapType) {
        String whereClause = String.format("%s = ?", MapTypeSpawnTime.MapTypeIdColumn);
        String[] whereArgs = { Integer.toString(mapType.typeId) };
        return readableDb.query(MapTypeSpawnTime.TableName, MapTypeSpawnTime.ColumnNames, whereClause, whereArgs, null, null, null);
    }

    public MapTypeSpawnTimeDTO GetMapTypeSpawnTimeByType(MapType mapType) {
        try (Cursor c = GetMapTypeSpawnTimesCursorByType(mapType)) {
            return MakeListFromCursor(c, MapTypeSpawnTime.Factory).get(0);
        }
    }


    public Cursor GetMapSpecificTipsCursorByMap(int mapId, SpawnSide side) {
        String whereClause = String.format("%s = ? AND %s = ?", MapSubject.MapIdColumn, MapSubject.SpawnSideIdColumn);
        String[] whereArgs = {
                Integer.toString(mapId),
                Integer.toString(side.spawnSideId)
        };
        String tableName = String.format("%s INNER JOIN %s ON %s.%s = %s.%s %s INNER JOIN %s ON %s.%s = %s.%s",
                // JOIN 1
                MapSubject.TableName, MapTip.TableName,
                MapSubject.TableName, MapSubject.MapSubjectIdColumn,
                MapTip.TableName, MapTip.MapSubjectIdColumn,
                // JOIN 2
                MapTip.TableName, MapSpecificTip.TableName,
                MapTip.TableName, MapTip.MapTipIdColumn,
                MapSpecificTip.TableName, MapSpecificTip.MapTipIdColumn
        );

        return readableDb.query(tableName, MapSpecificTip.ColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapSpecificTipDTO> GetMapSpecificTipsByMap(int mapId, SpawnSide side) {
        try (Cursor c = GetMapSpecificTipsCursorByMap(mapId, side)) {
            return MakeListFromCursor(c, MapSpecificTip.Factory);
        }
    }

    @Override
    public void close() {
        if (readableDb != null) readableDb.close();
    }

    // TODO: Common query cache mechanisms
    // NOTE: Either cache everything we need or nothing and keep db open, as the db itself is cached while open.
    // TODO: Perf test in memory vs db
}
