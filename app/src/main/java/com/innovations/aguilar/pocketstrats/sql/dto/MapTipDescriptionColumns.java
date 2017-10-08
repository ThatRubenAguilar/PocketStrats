package com.innovations.aguilar.pocketstrats.sql.dto;

import com.google.common.collect.Lists;

public class MapTipDescriptionColumns extends DTOColumnInfo {
    public static final String MapTipDescriptionIdColumn = "MapTipDescriptionId";
    public static final String MapTipDescriptionHashColumn = "MapTipDescriptionHash";
    public static final String MapTipDescriptionColumn = "MapTipDescription";

    public MapTipDescriptionColumns() {
        super("MapTipDescriptions", Lists.newArrayList(MapTipDescriptionIdColumn, MapTipDescriptionHashColumn,
                MapTipDescriptionColumn));
    }
}
