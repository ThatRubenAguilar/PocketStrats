package com.innovations.aguilar.pocketstrats.sql.dto;

import com.google.common.collect.Lists;

public class MapSubjectColumns extends DTOColumnInfo {

    public static final String MapSubjectIdColumn = "MapSubjectId";
    public static final String MapIdColumn = "MapId";
    public static final String MapSubjectPrecedenceColumn = "MapSubjectPrecedence";
    public static final String MapTipDescriptionIdColumn = "MapTipDescriptionId";
    public static final String SpawnSideIdColumn = "SpawnSideId";
    public static final String SpawnSideDescriptionColumn = "SpawnSideDescription";
    public static final String SegmentIdColumn = "SegmentId";

    public MapSubjectColumns() {
        super("MapSubjects", Lists.newArrayList(MapSubjectIdColumn, MapIdColumn,
                MapSubjectPrecedenceColumn, MapTipDescriptionIdColumn, SpawnSideIdColumn,
                SpawnSideDescriptionColumn, SegmentIdColumn));
    }
}
