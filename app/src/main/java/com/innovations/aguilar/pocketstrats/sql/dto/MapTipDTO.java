package com.innovations.aguilar.pocketstrats.sql.dto;

public interface MapTipDTO extends MapTipDescriptionDTO {
    int getMapTipId();
    Integer getParentMapTipId();
    int getOrderPrecedence();
    int getMapSubjectId();
}
