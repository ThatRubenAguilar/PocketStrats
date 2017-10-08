package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

import com.innovations.aguilar.pocketstrats.sql.query.DTOFromCursorFactory;

public class MapTipDescription implements MapTipDescriptionDTO {
    public static DTOFromCursorFactory<MapTipDescriptionDTO> Factory =
        new DTOFromCursorFactory<MapTipDescriptionDTO>() {
            @Override
            public MapTipDescriptionDTO Create(Cursor c) { return new MapTipDescription(c); }
        };

    private final int mapTipDescriptionId;
    private final int mapTipDescriptionHash;
    private final String mapTipDescription;

    public MapTipDescription(int mapTipDescriptionId, int mapTipDescriptionHash, String mapTipDescription) {
        this.mapTipDescriptionId = mapTipDescriptionId;
        this.mapTipDescriptionHash = mapTipDescriptionHash;
        this.mapTipDescription = mapTipDescription;
    }


    public MapTipDescription(Cursor c) {
        this.mapTipDescriptionId= c.getInt(c.getColumnIndex(Columns.MapTipDescriptionIdColumn));
        this.mapTipDescriptionHash= c.getInt(c.getColumnIndex(Columns.MapTipDescriptionHashColumn));
        this.mapTipDescription= c.getString(c.getColumnIndex(Columns.MapTipDescriptionColumn));
    }


    public static final MapTipDescriptionColumns Columns = new MapTipDescriptionColumns();

    @Override
    public int getMapTipDescriptionId() {
        return mapTipDescriptionId;
    }

    @Override
    public int getMapTipDescriptionHash() {
        return mapTipDescriptionHash;
    }

    @Override
    public String getMapTipDescription() {
        return mapTipDescription;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MapTipDescription{");
        sb.append("mapTipDescriptionId=").append(mapTipDescriptionId);
        sb.append(", mapTipDescriptionHash=").append(mapTipDescriptionHash);
        sb.append(", mapTipDescription='").append(mapTipDescription).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
