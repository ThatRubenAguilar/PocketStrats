package com.innovations.aguilar.pocketstrats.sql.dto;

public interface MapSubjectDTO extends MapTipDTO {
    int getMapId();
    Integer getSegmentId();
    SpawnSide getSpawnSideId();
    String getSpawnSideDescription();
}
