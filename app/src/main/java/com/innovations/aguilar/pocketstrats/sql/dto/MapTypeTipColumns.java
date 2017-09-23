package com.innovations.aguilar.pocketstrats.sql.dto;

import com.google.common.collect.Lists;

public class MapTypeTipColumns extends DTOColumnInfo {

    public static final String MapTypeTipIdColumn = "MapTypeTipId";
    public static final String MapTipIdColumn = "MapTipId";
    public static final String MapTypeIdColumn = "MapTypeId";

    public MapTypeTipColumns() {
        super("MapTypeTips", Lists.newArrayList(MapTypeTipIdColumn, MapTipIdColumn,
                MapTypeIdColumn));
    }
}
