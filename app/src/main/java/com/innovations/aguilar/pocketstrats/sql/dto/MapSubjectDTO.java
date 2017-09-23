package com.innovations.aguilar.pocketstrats.sql.dto;

public interface MapSubjectDTO {
    Integer getMapId();
    Integer getSegmentId();
    SpawnSide getSpawnSideId();
    String getSpawnSideDescription();
    String getMapSubjectDescription();
    int getMapSubjectPrecedence();
    int getMapSubjectId();
}
