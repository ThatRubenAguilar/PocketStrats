package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

import com.google.common.collect.Lists;
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
        this.locationId = c.getInt(c.getColumnIndex(Columns.LocationIdColumn));
        this.locationDescription = c.getString(c.getColumnIndex(Columns.LocationDescriptionColumn));
        this.locationTypeId = MapLocationType.FromInt(c.getInt(c.getColumnIndex(Columns.LocationTypeIdColumn)));
        this.locationImageName = c.getString(c.getColumnIndex(Columns.LocationImageNameColumn));
        this.segmentId = c.getInt(c.getColumnIndex(Columns.SegmentIdColumn));
        this.mapId = c.getInt(c.getColumnIndex(Columns.MapIdColumn));
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

    public static final MapLocationColumns Columns = new MapLocationColumns();

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


