package com.innovations.aguilar.pocketstrats.sql.dto;

public interface MapSubjectDTO extends MapTipDescriptionDTO {
    int getMapId();
    Integer getSegmentId();
    SpawnSide getSpawnSideId();
    String getSpawnSideDescription();
    int getMapSubjectPrecedence();
    int getMapSubjectId();
}
