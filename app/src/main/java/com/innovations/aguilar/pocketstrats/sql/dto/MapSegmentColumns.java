package com.innovations.aguilar.pocketstrats.sql.dto;

import com.google.common.collect.Lists;

public class MapSegmentColumns extends DTOColumnInfo {

    public static final String SegmentIdColumn = "SegmentId";
    public static final String MapIdColumn = "MapId";
    public static final String SegmentNameColumn = "SegmentName";

    public MapSegmentColumns() {
        super("MapSegments", Lists.newArrayList(SegmentIdColumn, MapIdColumn,
                SegmentNameColumn));
    }
}
