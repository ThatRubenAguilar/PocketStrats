package com.innovations.aguilar.pocketstrats.sql.dto;


public interface MapDataDTO {
    int getMapId();
    String getMapName();
    String getMapNameShort();
    String getMapFileCompatName();
    MapType getMapTypeId();
    String getMapType();
    String getMapTypeShort();
}
