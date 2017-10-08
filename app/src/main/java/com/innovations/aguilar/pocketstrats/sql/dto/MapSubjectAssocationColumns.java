package com.innovations.aguilar.pocketstrats.sql.dto;

import com.google.common.collect.Lists;

public class MapSubjectAssocationColumns extends DTOColumnInfo {

    public static final String MapSubjectIdColumn = "MapSubjectId";
    public static final String AssociatedMapSubjectIdColumn = "AssociatedMapSubjectId";

    public MapSubjectAssocationColumns() {
        super("MapSubjectAssociations", Lists.newArrayList(MapSubjectIdColumn, AssociatedMapSubjectIdColumn));
    }
}
