package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

import com.innovations.aguilar.pocketstrats.sql.query.DTOFromCursorFactory;


public class MapSpecificTip extends MapTip implements MapSpecificTipDTO {
    public static DTOFromCursorFactory<MapSpecificTipDTO> Factory =
            new DTOFromCursorFactory<MapSpecificTipDTO>() {
                @Override
                public MapSpecificTipDTO Create(Cursor c) { return new MapSpecificTip(c); }
            };

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MapSpecificTip{");
        sb.append("mapSpecificTipId=").append(mapSpecificTipId);
        sb.append(", MapTip=").append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
