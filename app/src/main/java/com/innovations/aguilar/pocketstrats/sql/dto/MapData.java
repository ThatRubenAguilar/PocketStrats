package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

import com.google.common.collect.Lists;
import com.innovations.aguilar.pocketstrats.sql.query.DTOFromCursorFactory;


public class MapData implements MapDataDTO {
    public static DTOFromCursorFactory<MapDataDTO> Factory =
        new DTOFromCursorFactory<MapDataDTO>() {
            @Override
            public MapDataDTO Create(Cursor c) { return new MapData(c); }
        };

    private final int mapId;
    private final String mapName;
    private final String mapNameShort;
    private final String mapFileCompatName;
    private final MapType mapTypeId;
    private final String mapType;
    private final String mapTypeShort;

    public MapData(int mapId, String mapName, String mapNameShort, String mapFileCompatName,
                   MapType mapTypeId, String mapType, String mapTypeShort) {
        this.mapId = mapId;
        this.mapName = mapName;
        this.mapNameShort = mapNameShort;
        this.mapFileCompatName = mapFileCompatName;
        this.mapTypeId = mapTypeId;
        this.mapType = mapType;
        this.mapTypeShort = mapTypeShort;
    }

    public MapData(Cursor c) {
        this.mapId = c.getInt(c.getColumnIndex(Columns.MapIdColumn));
        this.mapName = c.getString(c.getColumnIndex(Columns.MapNameColumn));
        this.mapNameShort = c.getString(c.getColumnIndex(Columns.MapNameShortColumn));
        this.mapFileCompatName = c.getString(c.getColumnIndex(Columns.MapFileCompatNameColumn));
        int mapTypeIdRaw = c.getInt(c.getColumnIndex(Columns.MapTypeIdColumn));
        this.mapTypeId = MapType.FromInt(mapTypeIdRaw);
        this.mapType = c.getString(c.getColumnIndex(Columns.MapTypeColumn));
        this.mapTypeShort = c.getString(c.getColumnIndex(Columns.MapTypeShortColumn));
    }

    @Override
    public int getMapId() {
        return mapId;
    }

    @Override
    public String getMapName() {
        return mapName;
    }

    @Override
    public String getMapNameShort() {
        return mapNameShort;
    }

    @Override
    public String getMapFileCompatName() { return mapFileCompatName; }

    @Override
    public MapType getMapTypeId() {
        return mapTypeId;
    }

    @Override
    public String getMapType() {
        return mapType;
    }

    @Override
    public String getMapTypeShort() {
        return mapTypeShort;
    }

    public static final MapDataColumns Columns = new MapDataColumns();


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MapData{");
        sb.append("mapId=").append(mapId);
        sb.append(", mapName='").append(mapName).append('\'');
        sb.append(", mapNameShort='").append(mapNameShort).append('\'');
        sb.append(", mapFileCompatName='").append(mapFileCompatName).append('\'');
        sb.append(", mapTypeId=").append(mapTypeId);
        sb.append(", mapType='").append(mapType).append('\'');
        sb.append(", mapTypeShort='").append(mapTypeShort).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
