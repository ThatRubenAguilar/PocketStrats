package com.innovations.aguilar.pocketstrats.sql.dto;

import com.google.common.collect.Lists;

public class MapLocationColumns extends DTOColumnInfo {

    public static final String LocationIdColumn = "LocationId";
    public static final String LocationDescriptionColumn = "LocationDescription";
    public static final String LocationTypeIdColumn = "LocationTypeId";
    public static final String LocationImageNameColumn = "LocationImageName";
    public static final String SegmentIdColumn = "SegmentId";
    public static final String MapIdColumn = "MapId";
    public MapLocationColumns() {
        super("MapLocations", Lists.newArrayList(LocationIdColumn, LocationDescriptionColumn,
                LocationTypeIdColumn, LocationImageNameColumn, SegmentIdColumn, MapIdColumn));
    }
}
