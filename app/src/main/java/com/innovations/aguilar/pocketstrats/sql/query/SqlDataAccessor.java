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


    <T> List<T> MakeListFromCursor(Cursor c, DTOFromCursorFactory<T> objectFactory) {
        List<T> list = new ArrayList<>();
        while (c.moveToNext())
        {
            list.add(objectFactory.Create(c));
        }
        return Collections.unmodifiableList(list);
    }

    public Cursor GetAllHerosCursor() {
        return readableDb.query(HeroData.Columns.TableName, HeroData.Columns.QualifiedColumnNames, null, null, null, null, null);
    }

    public List<HeroDataDTO> GetAllHeros() {
        try (Cursor c = GetAllHerosCursor()) {
            return MakeListFromCursor(c, HeroData.Factory);
        }
    }

    public Cursor GetMapSubjectsCursorByMap(Integer mapId, SpawnSide side) {
        String whereClause = String.format("%s = ? AND %s = ?",
                MapData.Columns.MapIdColumn, MapSubject.Columns.SpawnSideIdColumn);
        String mapIdStr = "NULL";
        if (mapId != null)
            mapIdStr = mapId.toString();
        String[] whereArgs = {
                mapIdStr,
                Integer.toString(side.spawnSideId) };
        String order = String.format("%s ASC", MapSubject.Columns.MapSubjectPrecedenceColumn);
        return readableDb.query(MapSubject.Columns.TableName, MapSubject.Columns.QualifiedColumnNames, whereClause, whereArgs,
                null, null, order);
    }
    public List<MapSubjectDTO> GetMapSubjectsByMap(Integer mapId, SpawnSide side) {
        try (Cursor c = GetMapSubjectsCursorByMap(mapId, side)) {
            return MakeListFromCursor(c, MapSubject.Factory);
        }
    }

    public Cursor GetAllMapsCursor() {
        return readableDb.query(MapData.Columns.TableName, MapData.Columns.QualifiedColumnNames, null, null, null, null, null);
    }

    public List<MapDataDTO> GetAllMaps() {
        try (Cursor c = GetAllMapsCursor()) {
            return MakeListFromCursor(c, MapData.Factory);
        }
    }

    public Cursor GetMapsCursorById(int mapId) {
        String whereClause = String.format("%s = ?", MapData.Columns.MapIdColumn);
        String[] whereArgs = { Integer.toString(mapId) };
        return readableDb.query(MapData.Columns.TableName, MapData.Columns.QualifiedColumnNames, whereClause, whereArgs, null, null, null);
    }
    public MapDataDTO GetMapById(int mapId) {
        try (Cursor c = GetMapsCursorById(mapId)) {
            return MakeListFromCursor(c, MapData.Factory).get(0);
        }
    }
    public Cursor GetMapsCursorByType(MapType mapType) {
        String whereClause = String.format("%s = ?", MapData.Columns.MapTypeIdColumn);
        String[] whereArgs = { Integer.toString(mapType.typeId) };
        return readableDb.query(MapData.Columns.TableName, MapData.Columns.QualifiedColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapDataDTO> GetMapsByType(MapType mapType) {
        try (Cursor c = GetMapsCursorByType(mapType)) {
            return MakeListFromCursor(c, MapData.Factory);
        }
    }


    public Cursor GetAllMapSegmentsCursor() {
        return readableDb.query(MapSegment.Columns.TableName, MapSegment.Columns.QualifiedColumnNames, null, null, null, null, null);
    }
    public List<MapSegmentDTO> GetAllMapSegments() {
        try (Cursor c = GetAllMapSegmentsCursor()) {
            return MakeListFromCursor(c, MapSegment.Factory);
        }
    }

    public Cursor GetMapSegmentsCursorByMap(int mapId) {
        String whereClause = String.format("%s = ?", MapSegment.Columns.MapIdColumn);
        String[] whereArgs = { Integer.toString(mapId) };
        return readableDb.query(MapSegment.Columns.TableName, MapSegment.Columns.QualifiedColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapSegmentDTO> GetMapSegmentsByMap(int mapId) {
        try (Cursor c = GetMapSegmentsCursorByMap(mapId)) {
            return MakeListFromCursor(c, MapSegment.Factory);
        }
    }


    public Cursor GetMapLocationsCursorByMap(int mapId) {
        String whereClause = String.format("%s = ?", MapLocation.Columns.MapIdColumn);
        String[] whereArgs = { Integer.toString(mapId) };
        return readableDb.query(MapLocation.Columns.TableName, MapLocation.Columns.QualifiedColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapLocationDTO> GetMapLocationsByMap(int mapId) {
        try (Cursor c = GetMapLocationsCursorByMap(mapId)) {
            return MakeListFromCursor(c, MapLocation.Factory);
        }
    }

    public Cursor GetMapLocationsCursorBySegment(int segmentId) {
        String whereClause = String.format("%s = ?", MapLocation.Columns.SegmentIdColumn);
        String[] whereArgs = { Integer.toString(segmentId) };
        return readableDb.query(MapLocation.Columns.TableName, MapLocation.Columns.QualifiedColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapLocationDTO> GetMapLocationsBySegment(int segmentId) {
        try (Cursor c = GetMapLocationsCursorBySegment(segmentId)) {
            return MakeListFromCursor(c, MapLocation.Factory);
        }
    }

    public Cursor GetMapSpawnStatsCursorBySegment(int segmentId) {
        String whereClause = String.format("%s = ?", MapLocation.Columns.SegmentIdColumn);
        String[] whereArgs = { Integer.toString(segmentId) };
        String tableName = String.format("%s INNER JOIN %s ON %s.%s = %s.%s",
                MapSpawnStatistic.Columns.TableName, MapLocation.Columns.TableName,
                MapSpawnStatistic.Columns.TableName, MapSpawnStatistic.Columns.LocationIdColumn,
                MapLocation.Columns.TableName, MapLocation.Columns.LocationIdColumn
                );

        return readableDb.query(tableName, MapSpawnStatistic.Columns.QualifiedColumnNames, whereClause, whereArgs, null, null, null);
    }

    public List<MapSpawnStatisticDTO> GetMapSpawnStatsBySegment(int segmentId) {
        try (Cursor c = GetMapSpawnStatsCursorBySegment(segmentId)) {
            return MakeListFromCursor(c, MapSpawnStatistic.Factory);
        }
    }


    public Cursor GetMapSpawnStatsCursorByLocation(int locationId) {
        String whereClause = String.format("%s = ?", MapSpawnStatistic.Columns.LocationIdColumn);
        String[] whereArgs = { Integer.toString(locationId) };
        return readableDb.query(MapSpawnStatistic.Columns.TableName, MapSpawnStatistic.Columns.QualifiedColumnNames,
                whereClause, whereArgs, null, null, null);
    }

    public List<MapSpawnStatisticDTO> GetMapSpawnStatsByLocation(int locationId) {
        try (Cursor c = GetMapSpawnStatsCursorByLocation(locationId)) {
            return MakeListFromCursor(c, MapSpawnStatistic.Factory);
        }
    }


    public Cursor GetAllMapTypeSpawnTimesCursor() {
        return readableDb.query(MapTypeSpawnTime.Columns.TableName, MapTypeSpawnTime.Columns.QualifiedColumnNames,
                null, null, null, null, null);
    }

    public List<MapTypeSpawnTimeDTO> GetAllMapTypeSpawnTimes() {
        try (Cursor c = GetAllMapTypeSpawnTimesCursor()) {
            return MakeListFromCursor(c, MapTypeSpawnTime.Factory);
        }
    }


    public Cursor GetMapTypeSpawnTimesCursorByType(MapType mapType) {
        String whereClause = String.format("%s = ?", MapTypeSpawnTime.Columns.MapTypeIdColumn);
        String[] whereArgs = { Integer.toString(mapType.typeId) };
        return readableDb.query(MapTypeSpawnTime.Columns.TableName, MapTypeSpawnTime.Columns.QualifiedColumnNames,
                whereClause, whereArgs, null, null, null);
    }

    public MapTypeSpawnTimeDTO GetMapTypeSpawnTimeByType(MapType mapType) {
        try (Cursor c = GetMapTypeSpawnTimesCursorByType(mapType)) {
            return MakeListFromCursor(c, MapTypeSpawnTime.Factory).get(0);
        }
    }


    public Cursor GetMapSpecificTipsCursorByMap(int mapId, SpawnSide side) {
        String whereClause = String.format("%s = ? AND %s = ?", MapSubject.Columns.MapIdColumn,
                MapSubject.Columns.SpawnSideIdColumn);
        String[] whereArgs = {
                Integer.toString(mapId),
                Integer.toString(side.spawnSideId)
        };
        String tableName = String.format("%s INNER JOIN %s ON %s.%s = %s.%s %s INNER JOIN %s ON %s.%s = %s.%s",
                // JOIN 1
                MapSubject.Columns.TableName, MapTip.Columns.TableName,
                MapSubject.Columns.TableName, MapSubject.Columns.MapSubjectIdColumn,
                MapTip.Columns.TableName, MapTip.Columns.MapSubjectIdColumn,
                // JOIN 2
                MapTip.Columns.TableName, MapSpecificTip.Columns.TableName,
                MapTip.Columns.TableName, MapTip.Columns.MapTipIdColumn,
                MapSpecificTip.Columns.TableName, MapSpecificTip.Columns.MapTipIdColumn
        );

        String order = String.format("%s ASC, %s ASC", MapSubject.Columns.MapSubjectIdColumn, MapTip.Columns.OrderPrecedenceColumn);

        String[] columns = new String[] {
                MapSpecificTip.Columns.MapSpecificTipIdColumn,
                MapSpecificTip.Columns.MapTipIdColumn
        };

        return readableDb.query(tableName, MapSpecificTip.Columns.QualifiedColumnNames, whereClause, whereArgs,
                null, null, order);
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
