package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

import com.google.common.base.Objects;
import com.innovations.aguilar.pocketstrats.sql.query.DTOFromCursorFactory;

public class MapSubject extends MapTip implements MapSubjectDTO {
    public static DTOFromCursorFactory<MapSubjectDTO> Factory =
            new DTOFromCursorFactory<MapSubjectDTO>() {
                @Override
                public MapSubjectDTO Create(Cursor c) { return new MapSubject(c); }
            };

    private final Integer mapId;
    private final SpawnSide spawnSide;
    private final String spawnSideDescription;
    private final Integer segmentId;

    public MapSubject(int mapTipId, int mapSubjectId, int orderPrecedence,
                      String mapTipDescription, Integer parentMapTipId,
            Integer mapId, SpawnSide spawnSide, String spawnSideDescription,Integer segmentId) {
        super(mapTipId, mapSubjectId, orderPrecedence, mapTipDescription, parentMapTipId);
        this.mapId = mapId;
        this.spawnSide = spawnSide;
        this.spawnSideDescription = spawnSideDescription;
        this.segmentId = segmentId;
    }

    public MapSubject(Cursor c) {
        super(c);
        this.spawnSideDescription= c.getString(c.getColumnIndex(SpawnSideDescriptionColumn));
        int spawnSideIdRaw = c.getInt(c.getColumnIndex(SpawnSideIdColumn));
        this.spawnSide = SpawnSide.FromInt(spawnSideIdRaw);
        if (!c.isNull(c.getColumnIndex(MapIdColumn)))
            this.mapId = c.getInt(c.getColumnIndex(MapIdColumn));
        else
            this.mapId = null;
        if (!c.isNull(c.getColumnIndex(SegmentIdColumn)))
            this.segmentId = c.getInt(c.getColumnIndex(SegmentIdColumn));
        else
            this.segmentId = null;
    }

    @Override
    public Integer getMapId() {
        return mapId;
    }
    @Override
    public Integer getSegmentId() {
        return segmentId;
    }
    @Override
    public SpawnSide getSpawnSideId() {
        return spawnSide;
    }
    @Override
    public String getSpawnSideDescription() {
        return spawnSideDescription;
    }


    public static final String MapSubjectIdColumn = "MapSubjectId";
    public static final String MapIdColumn = "MapId";
    public static final String MapTipIdColumn = "MapTipId";
    public static final String SpawnSideIdColumn = "SpawnSideId";
    public static final String SpawnSideDescriptionColumn = "SpawnSideDescription";
    public static final String SegmentIdColumn = "SegmentId";

    public static final String TableName = "MapSubjects";

    public static final String[] ColumnNames = {
            String.format("%s.%s", TableName, MapSubjectIdColumn),
            String.format("%s.%s", TableName, MapIdColumn),
            String.format("%s.%s", TableName, SpawnSideIdColumn),
            String.format("%s.%s", TableName, SpawnSideDescriptionColumn),
            String.format("%s.%s", TableName, MapTipIdColumn),
            String.format("%s.%s", TableName, SegmentIdColumn)
    };

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MapSubject{");
        sb.append("mapId=").append(mapId);
        sb.append(", spawnSide=").append(spawnSide);
        sb.append(", spawnSideDescription='").append(spawnSideDescription).append('\'');
        sb.append(", segmentId=").append(segmentId);
        sb.append(", MapTip=").append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
