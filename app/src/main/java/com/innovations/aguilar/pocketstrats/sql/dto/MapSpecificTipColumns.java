package com.innovations.aguilar.pocketstrats.sql.dto;

import com.google.common.collect.Lists;

public class MapSpecificTipColumns extends DTOColumnInfo {
    public static final String MapTipIdColumn = "MapTipId";
    public static final String MapSpecificTipIdColumn = "MapSpecificTipId";

    public MapSpecificTipColumns() {
        super("MapSpecificTips", Lists.newArrayList(MapTipIdColumn, MapSpecificTipIdColumn));
    }
}
