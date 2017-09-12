package com.innovations.aguilar.pocketstrats.sql.dto;

public interface MapTipDTO {
    int getMapTipId();
    Integer getParentMapTipId();
    String getMapTipDescription();
    int getOrderPrecedence();
    int getMapSubjectId();
}
