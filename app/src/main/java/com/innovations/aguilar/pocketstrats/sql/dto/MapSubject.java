package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

import com.google.common.collect.Lists;
import com.innovations.aguilar.pocketstrats.sql.query.DTOFromCursorFactory;

public class MapSubject implements MapSubjectDTO {
    public static DTOFromCursorFactory<MapSubjectDTO> Factory =
            new DTOFromCursorFactory<MapSubjectDTO>() {
                @Override
                public MapSubjectDTO Create(Cursor c) { return new MapSubject(c); }
            };

    private final int mapSubjectId;
    private final int mapSubjectPrecedence;
    private final String mapSubjectDescription;
    private final Integer mapId;
    private final SpawnSide spawnSide;
    private final String spawnSideDescription;
    private final Integer segmentId;

    public MapSubject(int mapSubjectId, int mapSubjectPrecedence, String mapSubjectDescription,
            Integer mapId, SpawnSide spawnSide, String spawnSideDescription, Integer segmentId) {
        this.mapSubjectId = mapSubjectId;
        this.mapSubjectPrecedence = mapSubjectPrecedence;
        this.mapSubjectDescription = mapSubjectDescription;
        this.mapId = mapId;
        this.spawnSide = spawnSide;
        this.spawnSideDescription = spawnSideDescription;
        this.segmentId = segmentId;
    }

    public MapSubject(Cursor c) {
        this.mapSubjectId= c.getInt(c.getColumnIndex(Columns.MapSubjectIdColumn));
        this.spawnSideDescription= c.getString(c.getColumnIndex(Columns.SpawnSideDescriptionColumn));
        this.mapSubjectDescription= c.getString(c.getColumnIndex(Columns.MapSubjectDescriptionColumn));
        this.mapSubjectPrecedence= c.getInt(c.getColumnIndex(Columns.MapSubjectPrecedenceColumn));
        int spawnSideIdRaw = c.getInt(c.getColumnIndex(Columns.SpawnSideIdColumn));
        this.spawnSide = SpawnSide.FromInt(spawnSideIdRaw);
        if (!c.isNull(c.getColumnIndex(Columns.MapIdColumn)))
            this.mapId = c.getInt(c.getColumnIndex(Columns.MapIdColumn));
        else
            this.mapId = null;
        if (!c.isNull(c.getColumnIndex(Columns.SegmentIdColumn)))
            this.segmentId = c.getInt(c.getColumnIndex(Columns.SegmentIdColumn));
        else
            this.segmentId = null;
    }

    @Override
    public int getMapSubjectId() {
        return mapSubjectId;
    }
    @Override
    public int getMapSubjectPrecedence() {
        return mapSubjectPrecedence;
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
    @Override
    public String getMapSubjectDescription() {
        return mapSubjectDescription;
    }


    public static final MapSubjectColumns Columns = new MapSubjectColumns();

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MapSubject{");
        sb.append("mapSubjectPrecedence=").append(mapSubjectPrecedence);
        sb.append(", mapSubjectDescription='").append(mapSubjectDescription).append('\'');
        sb.append(", mapId=").append(mapId);
        sb.append(", spawnSide=").append(spawnSide);
        sb.append(", spawnSideDescription='").append(spawnSideDescription).append('\'');
        sb.append(", segmentId=").append(segmentId);
        sb.append('}');
        return sb.toString();
    }
}


