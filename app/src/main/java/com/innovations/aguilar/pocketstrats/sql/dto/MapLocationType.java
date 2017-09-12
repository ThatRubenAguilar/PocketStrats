package com.innovations.aguilar.pocketstrats.sql.dto;

import com.innovations.aguilar.pocketstrats.exceptions.IntEnumCastException;

public enum MapLocationType {
    None(MapLocationTypeIds.NONE),
    Choke(MapLocationTypeIds.CHOKE),
    Streets(MapLocationTypeIds.STREETS),
    Corner(MapLocationTypeIds.CORNER),
    HighGround(MapLocationTypeIds.HIGH_GROUND),
    PointA(MapLocationTypeIds.POINT_A),
    PointB(MapLocationTypeIds.POINT_B),
    Checkpoint(MapLocationTypeIds.CHECKPOINT),
    LastCheckpoint(MapLocationTypeIds.LAST_CHECKPOINT);

    public final int typeId;

    MapLocationType(int typeId) {
        this.typeId = typeId;
    }

    public static MapLocationType FromInt(int id){
        switch(id) {
            case MapLocationTypeIds.NONE:
                return None;
            case MapLocationTypeIds.CHOKE:
                return Choke;
            case MapLocationTypeIds.STREETS:
                return Streets;
            case MapLocationTypeIds.CORNER:
                return Corner;
            case MapLocationTypeIds.HIGH_GROUND:
                return HighGround;
            case MapLocationTypeIds.POINT_A:
                return PointA;
            case MapLocationTypeIds.POINT_B:
                return PointB;
            case MapLocationTypeIds.CHECKPOINT:
                return Checkpoint;
            case MapLocationTypeIds.LAST_CHECKPOINT:
                return LastCheckpoint;
        }
        throw new IntEnumCastException(id, MapLocationType.class);
    }


    class MapLocationTypeIds {
        public static final int NONE = 0;
        public static final int CHOKE = 1;
        public static final int STREETS = 2;
        public static final int CORNER = 3;
        public static final int HIGH_GROUND = 4;
        public static final int POINT_A = 5;
        public static final int POINT_B = 6;
        public static final int CHECKPOINT = 7;
        public static final int LAST_CHECKPOINT = 8;
    }
}
