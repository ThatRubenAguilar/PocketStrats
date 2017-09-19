package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

import com.innovations.aguilar.pocketstrats.sql.query.DTOFromCursorFactory;


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
        this.segmentId = c.getInt(c.getColumnIndex(SegmentIdColumn));
        this.mapId = c.getInt(c.getColumnIndex(MapIdColumn));
        this.segmentName = c.getString(c.getColumnIndex(SegmentNameColumn));
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

    public static final String SegmentIdColumn = "SegmentId";
    public static final String MapIdColumn = "MapId";
    public static final String SegmentNameColumn = "SegmentName";

    public static final String TableName = "MapSegments";

    public static final String[] ColumnNames = {
            String.format("%s.%s", TableName, SegmentIdColumn),
            String.format("%s.%s", TableName, MapIdColumn),
            String.format("%s.%s", TableName, SegmentNameColumn)
    };

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
