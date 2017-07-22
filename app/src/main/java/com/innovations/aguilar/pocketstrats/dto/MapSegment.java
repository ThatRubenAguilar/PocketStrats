package com.innovations.aguilar.pocketstrats.dto;

import android.database.Cursor;

public class MapSegment implements MapSegmentDTO {
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


}
