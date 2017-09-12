package com.innovations.aguilar.pocketstrats.sql.dto;

/**
 * Created by Ruben on 7/8/2017.
 */

public interface MapTypeSpawnTimeDTO {
    int getStatisticId();
    double getMinSpawnTime();
    double getMaxSpawnTime();
    MapType getMapTypeId();
    double getOvertimeSpawnTime();
}
