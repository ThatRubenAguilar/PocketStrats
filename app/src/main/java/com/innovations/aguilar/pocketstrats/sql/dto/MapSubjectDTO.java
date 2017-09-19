package com.innovations.aguilar.pocketstrats.sql.dto;

public interface MapSubjectDTO extends MapTipDTO {
    Integer getMapId();
    Integer getSegmentId();
    SpawnSide getSpawnSideId();
    String getSpawnSideDescription();
}
