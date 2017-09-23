package com.innovations.aguilar.pocketstrats.sql.dto;

import com.google.common.collect.Lists;

public class MapTipColumns extends DTOColumnInfo {
    public static final String MapSubjectIdColumn = "MapSubjectId";
    public static final String MapTipIdColumn = "MapTipId";
    public static final String OrderPrecedenceColumn = "OrderPrecedence";
    public static final String MapTipDescriptionColumn = "MapTipDescription";
    public static final String ParentMapTipIdColumn = "ParentMapTipId";

    public MapTipColumns() {
        super("MapTips", Lists.newArrayList(MapSubjectIdColumn, MapTipIdColumn,
                OrderPrecedenceColumn, MapTipDescriptionColumn, ParentMapTipIdColumn));
    }
}
