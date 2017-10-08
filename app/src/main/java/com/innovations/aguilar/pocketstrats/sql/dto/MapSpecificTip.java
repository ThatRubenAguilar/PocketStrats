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
                          Integer parentMapTipId, int mapSpecificTipId,
                          int mapTipDescriptionId, int mapTipDescriptionHash, String mapTipDescription) {
        super(mapTipId, mapSubjectId, orderPrecedence, parentMapTipId,
                mapTipDescriptionId, mapTipDescriptionHash, mapTipDescription);
        this.mapSpecificTipId = mapSpecificTipId;
    }

    public MapSpecificTip(Cursor c) {
        super(c);
        this.mapSpecificTipId = c.getInt(c.getColumnIndex(Columns.MapSpecificTipIdColumn));
    }

    @Override
    public int getMapSpecificTipId() {
        return mapSpecificTipId;
    }

    public static final MapSpecificTipColumns Columns = new MapSpecificTipColumns();

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MapSpecificTip{");
        sb.append("mapSpecificTipId=").append(mapSpecificTipId);
        sb.append(", MapTip=").append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
