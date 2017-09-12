package com.innovations.aguilar.pocketstrats.sql.dto;

import com.google.common.base.Preconditions;

import java.util.Comparator;

public interface MapSpawnStatisticDTO {
    double Threshhold = 0.0001;
    Comparator<MapSpawnStatisticDTO> RunDurationComparer = new Comparator<MapSpawnStatisticDTO>() {
        @Override
        public int compare(MapSpawnStatisticDTO one, MapSpawnStatisticDTO two) {
            Preconditions.checkNotNull(one, "'one' cannot be null.");
            Preconditions.checkNotNull(two, "'two' cannot be null.");

            if (Math.abs(one.getRunDuration()-two.getRunDuration()) < Threshhold)
                return 0;
            else if (one.getRunDuration() < two.getRunDuration())
                return -1;
            else
                return 1;
        }
    };

    int getStatisticId();
    int getLocationId();
    SpawnSide getSpawnSideId();
    String getSpawnSideDescription();
    String getRunDescription();
    double getRunDuration();
}
