package com.innovations.aguilar.pocketstrats.dto;

import com.google.common.base.Preconditions;

import java.util.Comparator;

public interface MapLocationDTO {
    Comparator<MapLocationDTO> LocationIdComparer = new Comparator<MapLocationDTO>() {
        @Override
        public int compare(MapLocationDTO one, MapLocationDTO two) {
            Preconditions.checkNotNull(one, "'one' cannot be null.");
            Preconditions.checkNotNull(two, "'two' cannot be null.");

            return Integer.compare(one.getLocationId(), two.getLocationId());
        }
    };

    int getLocationId();
    String getLocationDescription();
    String getLocationImageName();
    int getSegmentId();
    int getMapId();
}
