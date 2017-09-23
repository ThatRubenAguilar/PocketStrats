package com.innovations.aguilar.pocketstrats.sql.dto;

import com.google.common.collect.Lists;

public class MapDataColumns extends DTOColumnInfo {
    public static final String MapIdColumn = "MapId";
    public static final String MapNameColumn = "MapName";
    public static final String MapNameShortColumn = "MapNameShort";
    public static final String MapFileCompatNameColumn = "MapFileCompatName";
    public static final String MapTypeIdColumn = "MapTypeId";
    public static final String MapTypeColumn = "MapType";
    public static final String MapTypeShortColumn = "MapTypeShort";

    public MapDataColumns() {
        super("Maps", Lists.newArrayList(MapIdColumn, MapNameColumn,
                MapNameShortColumn, MapFileCompatNameColumn, MapTypeIdColumn, MapTypeColumn,
                MapTypeShortColumn));
    }
}
