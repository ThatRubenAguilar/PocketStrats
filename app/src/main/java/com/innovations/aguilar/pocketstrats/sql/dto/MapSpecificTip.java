package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;


public class MapSpecificTip extends MapTip implements MapSpecificTipDTO {
    private final int mapSpecificTipId;

    public MapSpecificTip(int mapTipId, int mapSubjectId, int orderPrecedence,
                          String mapTipDescription, Integer parentMapTipId,
                          int mapSpecificTipId) {
        super(mapTipId, mapSubjectId, orderPrecedence, mapTipDescription, parentMapTipId);
        this.mapSpecificTipId = mapSpecificTipId;
    }

    public MapSpecificTip(Cursor c) {
        super(c);
        this.mapSpecificTipId = c.getInt(c.getColumnIndex(MapSpecificTipIdColumn));
    }

    @Override
    public int getMapSpecificTipId() {
        return mapSpecificTipId;
    }


    public static final String MapTipIdColumn = "MapTipId";
    public static final String MapSpecificTipIdColumn = "MapSpecificTipId";

    public static final String TableName = "MapSpecificTips";

    public static final String[] ColumnNames = {
            String.format("%s.%s", TableName, MapTipIdColumn),
            String.format("%s.%s", TableName, MapSpecificTipIdColumn)
    };


}
