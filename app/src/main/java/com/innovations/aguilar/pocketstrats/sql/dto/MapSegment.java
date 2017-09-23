package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

import com.google.common.collect.Lists;
import com.innovations.aguilar.pocketstrats.sql.query.DTOFromCursorFactory;

import java.util.List;


public class MapSegment implements MapSegmentDTO {
    public static DTOFromCursorFactory<MapSegmentDTO> Factory =
            new DTOFromCursorFactory<MapSegmentDTO>() {
                @Override
                public MapSegmentDTO Create(Cursor c) { return new MapSegment(c); }
            };

    private final int segmentId;
    private final int mapId;
    private final String segmentName;

    public MapSegment(int segmentId, int mapId, String segmentName) {
        this.segmentId = segmentId;
        this.mapId = mapId;
        this.segmentName = segmentName;
    }

    public MapSegment(Cursor c) {
        this.segmentId = c.getInt(c.getColumnIndex(Columns.SegmentIdColumn));
        this.mapId = c.getInt(c.getColumnIndex(Columns.MapIdColumn));
        this.segmentName = c.getString(c.getColumnIndex(Columns.SegmentNameColumn));
    }


    @Override
    public int getSegmentId() {
        return segmentId;
    }

    @Override
    public int getMapId() {
        return mapId;
    }

    @Override
    public String getSegmentName() {
        return segmentName;
    }

    public static final MapSegmentColumns Columns = new MapSegmentColumns();

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MapSegment{");
        sb.append("segmentId=").append(segmentId);
        sb.append(", mapId=").append(mapId);
        sb.append(", segmentName='").append(segmentName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

