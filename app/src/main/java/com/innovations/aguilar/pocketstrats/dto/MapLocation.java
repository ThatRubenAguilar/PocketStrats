package com.innovations.aguilar.pocketstrats.dto;

import android.database.Cursor;

public class MapLocation implements MapLocationDTO {
    private final int locationId;
    private final String locationDescription;
    private final String locationImageName;
    private final int segmentId;
    private final int mapId;

    public MapLocation(int locationId, String locationDescription, String locationImageName, int segmentId, int mapId) {
        this.locationId = locationId;
        this.locationDescription = locationDescription;
        this.locationImageName = locationImageName;
        this.segmentId = segmentId;
        this.mapId = mapId;
    }

    public MapLocation(Cursor c) {
        this.locationId = c.getInt(c.getColumnIndex(LocationIdColumn));
        this.locationDescription = c.getString(c.getColumnIndex(LocationDescriptionColumn));
        this.locationImageName = c.getString(c.getColumnIndex(LocationImageNameColumn));
        this.segmentId = c.getInt(c.getColumnIndex(SegmentIdColumn));
        this.mapId = c.getInt(c.getColumnIndex(MapIdColumn));
    }

    @Override
    public int getLocationId() {
        return locationId;
    }

    @Override
    public String getLocationDescription() {
        return locationDescription;
    }

    @Override
    public String getLocationImageName() {
        return locationImageName;
    }

    @Override
    public int getSegmentId() {
        return segmentId;
    }

    @Override
    public int getMapId() {
        return mapId;
    }


    public static final String LocationIdColumn = "LocationId";
    public static final String LocationDescriptionColumn = "LocationDescription";
    public static final String LocationImageNameColumn = "LocationImageName";
    public static final String SegmentIdColumn = "SegmentId";
    public static final String MapIdColumn = "MapId";

    public static final String TableName = "MapLocations";

    public static final String[] ColumnNames = {
            String.format("%s.%s", TableName, LocationIdColumn),
            String.format("%s.%s", TableName, LocationDescriptionColumn),
            String.format("%s.%s", TableName, LocationImageNameColumn),
            String.format("%s.%s", TableName, SegmentIdColumn),
            String.format("%s.%s", TableName, MapIdColumn)
    };



}
