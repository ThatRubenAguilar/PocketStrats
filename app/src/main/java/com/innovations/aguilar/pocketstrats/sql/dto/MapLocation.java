package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

import com.innovations.aguilar.pocketstrats.sql.query.DTOFromCursorFactory;

public class MapLocation implements MapLocationDTO {
    public static DTOFromCursorFactory<MapLocationDTO> Factory =
            new DTOFromCursorFactory<MapLocationDTO>() {
                @Override
                public MapLocationDTO Create(Cursor c) { return new MapLocation(c); }
            };

    private final int locationId;
    private final String locationDescription;
    private final MapLocationType locationTypeId;
    private final String locationImageName;
    private final int segmentId;
    private final int mapId;

    public MapLocation(int locationId, String locationDescription, MapLocationType locationTypeId,
                       String locationImageName, int segmentId, int mapId) {
        this.locationId = locationId;
        this.locationDescription = locationDescription;
        this.locationTypeId = locationTypeId;
        this.locationImageName = locationImageName;
        this.segmentId = segmentId;
        this.mapId = mapId;
    }

    public MapLocation(Cursor c) {
        this.locationId = c.getInt(c.getColumnIndex(LocationIdColumn));
        this.locationDescription = c.getString(c.getColumnIndex(LocationDescriptionColumn));
        this.locationTypeId = MapLocationType.FromInt(c.getInt(c.getColumnIndex(LocationTypeIdColumn)));
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
    public MapLocationType getLocationTypeId() { return locationTypeId; }

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
    public static final String LocationTypeIdColumn = "LocationTypeId";
    public static final String LocationImageNameColumn = "LocationImageName";
    public static final String SegmentIdColumn = "SegmentId";
    public static final String MapIdColumn = "MapId";

    public static final String TableName = "MapLocations";

    public static final String[] ColumnNames = {
            String.format("%s.%s", TableName, LocationIdColumn),
            String.format("%s.%s", TableName, LocationDescriptionColumn),
            String.format("%s.%s", TableName, LocationTypeIdColumn),
            String.format("%s.%s", TableName, LocationImageNameColumn),
            String.format("%s.%s", TableName, SegmentIdColumn),
            String.format("%s.%s", TableName, MapIdColumn)
    };


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MapLocation{");
        sb.append("locationId=").append(locationId);
        sb.append(", locationDescription='").append(locationDescription).append('\'');
        sb.append(", locationTypeId=").append(locationTypeId);
        sb.append(", locationImageName='").append(locationImageName).append('\'');
        sb.append(", segmentId=").append(segmentId);
        sb.append(", mapId=").append(mapId);
        sb.append('}');
        return sb.toString();
    }
}
