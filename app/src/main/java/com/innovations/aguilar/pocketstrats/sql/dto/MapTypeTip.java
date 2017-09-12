package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

/**
 * Created by Ruben on 9/12/2017.
 */
public class MapTypeTip extends MapTip implements MapTypeTipDTO {
    private final int mapTypeTipId;
    private final MapType mapType;

    public MapTypeTip(int mapTipId, int mapSubjectId, int orderPrecedence,
                      String mapTipDescription, Integer parentMapTipId,
                      int mapTypeTipId, MapType mapType) {
        super(mapTipId, mapSubjectId, orderPrecedence, mapTipDescription, parentMapTipId);
        this.mapTypeTipId = mapTypeTipId;
        this.mapType = mapType;
    }

    public MapTypeTip(Cursor c) {
        super(c);
        this.mapTypeTipId = c.getInt(c.getColumnIndex(MapTypeTipIdColumn));
        int mapTypeIdRaw = c.getInt(c.getColumnIndex(MapTypeIdColumn));
        this.mapType = MapType.FromInt(mapTypeIdRaw);
    }

    @Override
    public int getMapTypeTipId() {
        return mapTypeTipId;
    }
    @Override
    public MapType getMapTypeId() {
        return mapType;
    }


    public static final String MapTypeTipIdColumn = "MapTypeTipId";
    public static final String MapTipIdColumn = "MapTipId";
    public static final String MapTypeIdColumn = "MapTypeId";

    public static final String TableName = "MapTypeTips";

    public static final String[] ColumnNames = {
            String.format("%s.%s", TableName, MapTypeTipIdColumn),
            String.format("%s.%s", TableName, MapTypeIdColumn),
            String.format("%s.%s", TableName, MapTipIdColumn)
    };


}
