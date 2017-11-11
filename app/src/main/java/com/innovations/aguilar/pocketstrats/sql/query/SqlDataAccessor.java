package com.innovations.aguilar.pocketstrats.sql.query;

import android.database.Cursor;
import android.database.sqlite.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.innovations.aguilar.pocketstrats.sql.dto.HeroData;
import com.innovations.aguilar.pocketstrats.sql.dto.HeroDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapData;
import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapHeroPickTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapHeroPickTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapLocation;
import com.innovations.aguilar.pocketstrats.sql.dto.MapLocationDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSegment;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSegmentDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpawnStatistic;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpawnStatisticDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpecificTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpecificTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubject;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectAssociation;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTipDescription;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTipDescriptionDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapType;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTypeSpawnTime;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTypeSpawnTimeDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTypeTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTypeTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;

public class SqlDataAccessor implements AutoCloseable {
    private SQLiteDatabase readableDb;

    public MapSubjectDataAccessor mapSubjectAccessor() {
        return subjectAccessor;
    }

    private MapSubjectDataAccessor subjectAccessor = new MapSubjectDataAccessor();

    public MapDataAccessor mapAccessor() {
        return mapAccessor;
    }

    private MapDataAccessor mapAccessor = new MapDataAccessor();

    public MapTipDataAccessor mapTipAccessor() {
        return mapTipAccessor;
    }

    private MapTipDataAccessor mapTipAccessor = new MapTipDataAccessor();

    public SqlDataAccessor(SQLiteDatabase readableDb) {
        this.readableDb = readableDb;
    }


    public class MapSubjectDataAccessor {

        private String mapSubjectJoin() {
           return MapSubject.Columns.innerJoin(MapTipDescription.Columns,
                   MapSubject.Columns.MapTipDescriptionIdColumn, MapTipDescription.Columns.MapTipDescriptionIdColumn);
        }

        public Cursor GetAllMapSubjectsCursor() {

            String tableName = mapSubjectJoin();
            String[] columns = MapSubject.Columns.joinAndQualifyColumns(MapTipDescription.Columns);

            return readableDb.query(tableName, columns, null, null,
                    null, null, null);
        }
        public List<MapSubjectDTO> GetAllMapSubjects() {
            try (Cursor c = GetAllMapSubjectsCursor()) {
                return MakeListFromCursor(c, MapSubject.Factory);
            }
        }

        public Cursor GetMapSubjectsCursorByMapOrSide(int mapId, SpawnSide side) {
            String whereClause = String.format("%s = ? AND %s = ?",
                    MapSubject.Columns.qualifyColumn(MapSubject.Columns.MapIdColumn),
                    MapSubject.Columns.qualifyColumn(MapSubject.Columns.SpawnSideIdColumn));

            String[] whereArgs = {
                    Integer.toString(mapId),
                    Integer.toString(side.spawnSideId) };


            String tableName = mapSubjectJoin();

            String[] columns = MapSubject.Columns.joinAndQualifyColumns(MapTipDescription.Columns);
            String groupBy = MapSubject.Columns.qualifyColumn(MapSubject.Columns.MapSubjectIdColumn);
            String order = String.format("%s ASC",
                    MapSubject.Columns.qualifyColumn(MapSubject.Columns.MapSubjectPrecedenceColumn));

            return readableDb.query(tableName, columns, whereClause, whereArgs,
                    groupBy, null, order);
        }
        public List<MapSubjectDTO> GetMapSubjectsByMapOrSide(int mapId, SpawnSide side) {
            try (Cursor c = GetMapSubjectsCursorByMapOrSide(mapId, side)) {
                return MakeListFromCursor(c, MapSubject.Factory);
            }
        }
        public Cursor GetMapSubjectsCursorByAssociation(int associatedMapSubjectId) {
            String whereClause = String.format("%s = ?",
                    MapSubjectAssociation.Columns.qualifyColumn(MapSubjectAssociation.Columns.MapSubjectIdColumn));
            String[] whereArgs = {
                    Integer.toString(associatedMapSubjectId)
            };
            String tableName = MapSubject.Columns.innerJoin(mapSubjectJoin(),
                    MapSubject.Columns, MapSubjectAssociation.Columns,
                    MapSubject.Columns.MapSubjectIdColumn, MapSubjectAssociation.Columns.AssociatedMapSubjectIdColumn);

            String[] columns = MapSubject.Columns.joinAndQualifyColumns(MapTipDescription.Columns);

            String order = String.format("%s ASC",
                    MapSubject.Columns.qualifyColumn(MapSubject.Columns.MapSubjectPrecedenceColumn));

            return readableDb.query(tableName, columns, whereClause, whereArgs,
                    null, null, order);
        }
        public List<MapSubjectDTO> GetMapSubjectsByAssociation(int associatedMapSubjectId) {
            try (Cursor c = GetMapSubjectsCursorByAssociation(associatedMapSubjectId)) {
                return MakeListFromCursor(c, MapSubject.Factory);
            }
        }
        public Cursor GetMapSubjectCursorById(int mapSubjectId) {
            String whereClause = String.format("%s = ?",
                    MapSubject.Columns.qualifyColumn(MapSubject.Columns.MapSubjectIdColumn));
            String[] whereArgs = {
                    Integer.toString(mapSubjectId)
            };
            String tableName = mapSubjectJoin();

            String[] columns = MapSubject.Columns.joinAndQualifyColumns(MapTipDescription.Columns);

            return readableDb.query(tableName, columns, whereClause, whereArgs,
                    null, null, null);
        }
        public MapSubjectDTO GetMapSubjectById(int mapSubjectId) {
            try (Cursor c = GetMapSubjectCursorById(mapSubjectId)) {
                return MakeListFromCursor(c, MapSubject.Factory).get(0);
            }
        }
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



    public class MapDataAccessor {

        public Cursor GetAllMapsCursor() {
            return readableDb.query(MapData.Columns.TableName, MapData.Columns.QualifiedColumnNames,
                    null, null, null, null, null);
        }

        public List<MapDataDTO> GetAllMaps() {
            try (Cursor c = GetAllMapsCursor()) {
                return MakeListFromCursor(c, MapData.Factory);
            }
        }

        public Cursor GetMapsCursorById(int mapId) {
            String whereClause = String.format("%s = ?", MapData.Columns.MapIdColumn);
            String[] whereArgs = { Integer.toString(mapId) };
            return readableDb.query(MapData.Columns.TableName, MapData.Columns.QualifiedColumnNames,
                    whereClause, whereArgs, null, null, null);
        }
        public MapDataDTO GetMapById(int mapId) {
            try (Cursor c = GetMapsCursorById(mapId)) {
                return MakeListFromCursor(c, MapData.Factory).get(0);
            }
        }
        public Cursor GetMapsCursorByType(MapType mapType) {
            String whereClause = String.format("%s = ?", MapData.Columns.MapTypeIdColumn);
            String[] whereArgs = { Integer.toString(mapType.typeId) };
            return readableDb.query(MapData.Columns.TableName, MapData.Columns.QualifiedColumnNames,
                    whereClause, whereArgs, null, null, null);
        }

        public List<MapDataDTO> GetMapsByType(MapType mapType) {
            try (Cursor c = GetMapsCursorByType(mapType)) {
                return MakeListFromCursor(c, MapData.Factory);
            }
        }


        public Cursor GetAllMapSegmentsCursor() {
            return readableDb.query(MapSegment.Columns.TableName, MapSegment.Columns.QualifiedColumnNames,
                    null, null, null, null, null);
        }
        public List<MapSegmentDTO> GetAllMapSegments() {
            try (Cursor c = GetAllMapSegmentsCursor()) {
                return MakeListFromCursor(c, MapSegment.Factory);
            }
        }

        public Cursor GetMapSegmentsCursorByMap(int mapId) {
            String whereClause = String.format("%s = ?", MapSegment.Columns.MapIdColumn);
            String[] whereArgs = { Integer.toString(mapId) };
            return readableDb.query(MapSegment.Columns.TableName, MapSegment.Columns.QualifiedColumnNames,
                    whereClause, whereArgs, null, null, null);
        }

        public List<MapSegmentDTO> GetMapSegmentsByMap(int mapId) {
            try (Cursor c = GetMapSegmentsCursorByMap(mapId)) {
                return MakeListFromCursor(c, MapSegment.Factory);
            }
        }


        public Cursor GetMapLocationsCursorByMap(int mapId) {
            String whereClause = String.format("%s = ?", MapLocation.Columns.MapIdColumn);
            String[] whereArgs = { Integer.toString(mapId) };
            return readableDb.query(MapLocation.Columns.TableName, MapLocation.Columns.QualifiedColumnNames,
                    whereClause, whereArgs, null, null, null);
        }

        public List<MapLocationDTO> GetMapLocationsByMap(int mapId) {
            try (Cursor c = GetMapLocationsCursorByMap(mapId)) {
                return MakeListFromCursor(c, MapLocation.Factory);
            }
        }

        public Cursor GetMapLocationsCursorBySegment(int segmentId) {
            String whereClause = String.format("%s = ?", MapLocation.Columns.SegmentIdColumn);
            String[] whereArgs = { Integer.toString(segmentId) };
            return readableDb.query(MapLocation.Columns.TableName, MapLocation.Columns.QualifiedColumnNames,
                    whereClause, whereArgs, null, null, null);
        }

        public List<MapLocationDTO> GetMapLocationsBySegment(int segmentId) {
            try (Cursor c = GetMapLocationsCursorBySegment(segmentId)) {
                return MakeListFromCursor(c, MapLocation.Factory);
            }
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

    public class MapTipDataAccessor {


        private String mapSpecificTipJoin() {
            String join1 = MapSpecificTip.Columns.innerJoin(MapTip.Columns,
                    MapSpecificTip.Columns.MapTipIdColumn, MapTip.Columns.MapTipIdColumn);
            return MapTip.Columns.innerJoin(join1, MapTip.Columns, MapTipDescription.Columns,
                    MapTip.Columns.MapTipDescriptionIdColumn, MapTipDescription.Columns.MapTipDescriptionIdColumn);
        }

        private String mapTypeTipJoin() {
            String join1 = MapTypeTip.Columns.innerJoin(MapTip.Columns,
                    MapTypeTip.Columns.MapTipIdColumn, MapTip.Columns.MapTipIdColumn);
            return MapTip.Columns.innerJoin(join1, MapTip.Columns, MapTipDescription.Columns,
                    MapTip.Columns.MapTipDescriptionIdColumn, MapTipDescription.Columns.MapTipDescriptionIdColumn);
        }
        private String mapHeroPickTipJoin() {
            String join1 = MapHeroPickTip.Columns.innerJoin(MapTip.Columns,
                    MapHeroPickTip.Columns.MapTipIdColumn, MapTip.Columns.MapTipIdColumn);
            return MapTip.Columns.innerJoin(join1, MapTip.Columns, MapTipDescription.Columns,
                    MapTip.Columns.MapTipDescriptionIdColumn, MapTipDescription.Columns.MapTipDescriptionIdColumn);
        }
        private String mapTipJoin() {
            return MapTip.Columns.innerJoin(MapTipDescription.Columns,
                    MapTip.Columns.MapTipDescriptionIdColumn, MapTipDescription.Columns.MapTipDescriptionIdColumn);
        }


        public Cursor GetMapSpecificTipCursorById(int mapSpecificTipId) {
            String whereClause = String.format("%s = ?",
                    MapSpecificTip.Columns.qualifyColumn(MapSpecificTip.Columns.MapSpecificTipIdColumn));
            String[] whereArgs = {
                    Integer.toString(mapSpecificTipId)
            };
            String tableName = mapSpecificTipJoin();

            String[] columns = MapSpecificTip.Columns.joinAndQualifyColumns(
                    MapTip.Columns, MapTipDescription.Columns);

            return readableDb.query(tableName, columns, whereClause, whereArgs,
                    null, null, null);
        }
        public MapSpecificTipDTO GetMapSpecificTipById(int mapSpecificTipId) {
            try (Cursor c = GetMapSpecificTipCursorById(mapSpecificTipId)) {
                return MakeListFromCursor(c, MapSpecificTip.Factory).get(0);
            }
        }
        public Cursor GetMapTypeTipCursorById(int mapTypeTipId) {
            String whereClause = String.format("%s = ?",
                    MapTypeTip.Columns.qualifyColumn(MapTypeTip.Columns.MapTypeTipIdColumn));
            String[] whereArgs = {
                    Integer.toString(mapTypeTipId)
            };

            String tableName = mapTypeTipJoin();

            String[] columns = MapTypeTip.Columns.joinAndQualifyColumns(
                    MapTip.Columns, MapTipDescription.Columns);

            return readableDb.query(tableName, columns, whereClause, whereArgs,
                    null, null, null);
        }
        public MapTypeTipDTO GetMapTypeTipById(int mapTypeTipId) {
            try (Cursor c = GetMapTypeTipCursorById(mapTypeTipId)) {
                return MakeListFromCursor(c, MapTypeTip.Factory).get(0);
            }
        }
        public Cursor GetMapHeroPickTipCursorById(int mapHeroPickTipId) {
            String whereClause = String.format("%s = ?",
                    MapHeroPickTip.Columns.qualifyColumn(MapHeroPickTip.Columns.MapPickTipIdColumn));
            String[] whereArgs = {
                    Integer.toString(mapHeroPickTipId)
            };
            String tableName = mapHeroPickTipJoin();

            String[] columns = MapHeroPickTip.Columns.joinAndQualifyColumns(
                    MapTip.Columns, MapTipDescription.Columns);

            return readableDb.query(tableName, columns, whereClause, whereArgs,
                    null, null, null);
        }
        public MapHeroPickTipDTO GetMapHeroPickTipById(int mapHeroPickTipId) {
            try (Cursor c = GetMapHeroPickTipCursorById(mapHeroPickTipId)) {
                return MakeListFromCursor(c, MapHeroPickTip.Factory).get(0);
            }
        }
        public Cursor GetMapHeroPickTipCursorBySubject(int mapSubjectId) {
            String whereClause = String.format("%s = ?",
                    MapTip.Columns.qualifyColumn(MapTip.Columns.MapSubjectIdColumn));
            String[] whereArgs = {
                    Integer.toString(mapSubjectId)
            };
            String tableName = mapHeroPickTipJoin();

            String order = String.format("%s ASC, %s ASC",
                    MapTip.Columns.qualifyColumn(MapTip.Columns.OrderPrecedenceColumn),
                    MapHeroPickTip.Columns.qualifyColumn(MapHeroPickTip.Columns.HeroIdColumn)
                    );

            String[] columns = MapHeroPickTip.Columns.joinAndQualifyColumns(
                    MapTip.Columns, MapTipDescription.Columns);

            return readableDb.query(tableName, columns, whereClause, whereArgs,
                    null, null, order);
        }
        public List<MapHeroPickTipDTO> GetMapHeroPickTipBySubject(int mapSubjectId) {
            try (Cursor c = GetMapHeroPickTipCursorBySubject(mapSubjectId)) {
                return MakeListFromCursor(c, MapHeroPickTip.Factory);
            }
        }

        public Cursor GetMapTipDescriptionCursorByHash(int mapTipDescriptionHash) {
            String whereClause = String.format("%s = ?", MapTipDescription.Columns.MapTipDescriptionHashColumn);
            String[] whereArgs = { Integer.toString(mapTipDescriptionHash) };
            return readableDb.query(MapTipDescription.Columns.TableName, MapTipDescription.Columns.QualifiedColumnNames,
                    whereClause, whereArgs, null, null, null);
        }

        public List<MapTipDescriptionDTO> GetMapTipDescriptionsByHash(int mapTipDescriptionHash) {
            try (Cursor c = GetMapTipDescriptionCursorByHash(mapTipDescriptionHash)) {
                return MakeListFromCursor(c, MapTipDescription.Factory);
            }
        }
        public Cursor GetMapTipCursorById(int mapTipId) {
            String whereClause = String.format("%s = ?",
                    MapTip.Columns.qualifyColumn(MapTip.Columns.MapTipIdColumn));
            String[] whereArgs = { Integer.toString(mapTipId) };

            String tableName = mapTipJoin();

            String[] columns = MapTip.Columns.joinAndQualifyColumns(MapTipDescription.Columns);

            return readableDb.query(tableName, columns,
                    whereClause, whereArgs, null, null, null);
        }

        public MapTipDTO GetMapTipById(int mapTipId) {
            try (Cursor c = GetMapTipCursorById(mapTipId)) {
                return MakeListFromCursor(c, MapTip.Factory).get(0);
            }
        }



        public Cursor GetMapSpecificTipsCursorByMap(int mapId, SpawnSide side) {
            String whereClause = String.format("%s = ? AND %s = ?", MapSubject.Columns.MapIdColumn,
                    MapSubject.Columns.SpawnSideIdColumn);
            String[] whereArgs = {
                    Integer.toString(mapId),
                    Integer.toString(side.spawnSideId)
            };

            String tableName = MapSubject.Columns.innerJoin(mapSpecificTipJoin(),
                    MapTip.Columns, MapSubject.Columns,
                    MapTip.Columns.MapSubjectIdColumn, MapSubject.Columns.MapSubjectIdColumn);

            String order = String.format("%s ASC, %s ASC",
                    MapSubject.Columns.qualifyColumn(MapSubject.Columns.MapSubjectIdColumn),
                    MapTip.Columns.qualifyColumn(MapTip.Columns.OrderPrecedenceColumn));

            String[] columns = MapSpecificTip.Columns.joinAndQualifyColumns(MapTip.Columns, MapTipDescription.Columns);

            return readableDb.query(tableName, columns, whereClause, whereArgs,
                    null, null, order);
        }


        public List<MapSpecificTipDTO> GetMapSpecificTipsByMap(int mapId, SpawnSide side) {
            try (Cursor c = GetMapSpecificTipsCursorByMap(mapId, side)) {
                return MakeListFromCursor(c, MapSpecificTip.Factory);
            }
        }

        public Cursor GetMapSpecificTipsCursorByMapSubject(int mapSubjectId) {
            String whereClause = String.format("%s = ?", MapTip.Columns.MapSubjectIdColumn);
            String[] whereArgs = {
                    Integer.toString(mapSubjectId)
            };
            String tableName = mapSpecificTipJoin();

            String order = String.format("%s ASC",
                    MapTip.Columns.qualifyColumn(MapTip.Columns.OrderPrecedenceColumn));

            String[] columns = MapSpecificTip.Columns.joinAndQualifyColumns(MapTip.Columns, MapTipDescription.Columns);

            return readableDb.query(tableName, columns, whereClause, whereArgs,
                    null, null, order);
        }

        public List<MapSpecificTipDTO> GetMapSpecificTipsByMapSubject(int mapSubjectId) {
            try (Cursor c = GetMapSpecificTipsCursorByMapSubject(mapSubjectId)) {
                return MakeListFromCursor(c, MapSpecificTip.Factory);
            }
        }


        public Cursor GetMapTypeTipsCursorByMapType(MapType typeId, SpawnSide side) {
            String whereClause = String.format("%s = ? AND %s = ?", MapTypeTip.Columns.MapTypeIdColumn,
                    MapSubject.Columns.SpawnSideIdColumn);
            String[] whereArgs = {
                    Integer.toString(typeId.typeId),
                    Integer.toString(side.spawnSideId)
            };


            String tableName = MapSubject.Columns.innerJoin(mapTypeTipJoin(),
                    MapTip.Columns, MapSubject.Columns,
                    MapTip.Columns.MapSubjectIdColumn, MapSubject.Columns.MapSubjectIdColumn);

            String order = String.format("%s ASC, %s ASC",
                    MapSubject.Columns.qualifyColumn(MapSubject.Columns.MapSubjectIdColumn),
                    MapTip.Columns.qualifyColumn(MapTip.Columns.OrderPrecedenceColumn));

            String[] columns = MapTypeTip.Columns.joinAndQualifyColumns(MapTip.Columns, MapTipDescription.Columns);

            return readableDb.query(tableName, columns, whereClause, whereArgs,
                    null, null, order);
        }


        public List<MapTypeTipDTO> GetMapTypeTipsByMapType(MapType typeId, SpawnSide side) {
            try (Cursor c = GetMapTypeTipsCursorByMapType(typeId, side)) {
                return MakeListFromCursor(c, MapTypeTip.Factory);
            }
        }
        public Cursor GetMapTypeTipsCursorByMapSubject(int mapSubjectId) {
            String whereClause = String.format("%s = ?",
                    MapTip.Columns.qualifyColumn(MapTip.Columns.MapSubjectIdColumn));
            String[] whereArgs = {
                    Integer.toString(mapSubjectId)
            };
            String tableName = mapTypeTipJoin();

            String order = String.format("%s ASC, %s ASC",
                    MapTip.Columns.qualifyColumn(MapTip.Columns.MapSubjectIdColumn),
                    MapTip.Columns.qualifyColumn(MapTip.Columns.OrderPrecedenceColumn));

            String[] columns = MapTypeTip.Columns.joinAndQualifyColumns(MapTip.Columns, MapTipDescription.Columns);

            return readableDb.query(tableName, columns, whereClause, whereArgs,
                    null, null, order);
        }


        public List<MapTypeTipDTO> GetMapTypeTipsByMapSubject(int mapSubjectId) {
            try (Cursor c = GetMapTypeTipsCursorByMapSubject(mapSubjectId)) {
                return MakeListFromCursor(c, MapTypeTip.Factory);
            }
        }

    }

    @Override
    public void close() {
        // See SQLITE NOTE
//        if (readableDb != null) readableDb.close();
    }

    // TODO: Common query cache mechanisms
    // NOTE: Either cache everything we need or nothing and keep db open, as the db itself is cached while open.
    // TODO: Perf test in memory vs db
}
