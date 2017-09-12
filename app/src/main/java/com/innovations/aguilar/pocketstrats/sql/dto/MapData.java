package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;


public class MapData implements MapDataDTO {
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
        this.mapId = c.getInt(c.getColumnIndex(MapIdColumn));
        this.mapName = c.getString(c.getColumnIndex(MapNameColumn));
        this.mapNameShort = c.getString(c.getColumnIndex(MapNameShortColumn));
        this.mapFileCompatName = c.getString(c.getColumnIndex(MapFileCompatNameColumn));
        int mapTypeIdRaw = c.getInt(c.getColumnIndex(MapTypeIdColumn));
        this.mapTypeId = MapType.FromInt(mapTypeIdRaw);
        this.mapType = c.getString(c.getColumnIndex(MapTypeColumn));
        this.mapTypeShort = c.getString(c.getColumnIndex(MapTypeShortColumn));
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

    public static final String MapIdColumn = "MapId";
    public static final String MapNameColumn = "MapName";
    public static final String MapNameShortColumn = "MapNameShort";
    public static final String MapFileCompatNameColumn = "MapFileCompatName";
    public static final String MapTypeIdColumn = "MapTypeId";
    public static final String MapTypeColumn = "MapType";
    public static final String MapTypeShortColumn = "MapTypeShort";

    public static final String TableName = "Maps";

    public static final String[] ColumnNames = {
            String.format("%s.%s", TableName, MapIdColumn),
            String.format("%s.%s", TableName, MapNameColumn),
            String.format("%s.%s", TableName, MapNameShortColumn),
            String.format("%s.%s", TableName, MapFileCompatNameColumn),
            String.format("%s.%s", TableName, MapTypeIdColumn),
            String.format("%s.%s", TableName, MapTypeColumn),
            String.format("%s.%s", TableName, MapTypeShortColumn)
    };


}
