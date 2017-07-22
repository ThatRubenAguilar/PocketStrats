package com.innovations.aguilar.pocketstrats.dto;

import com.innovations.aguilar.pocketstrats.exceptions.IntEnumCastException;

public enum MapType {
    None(MapTypeIds.NONE),
    Assault(MapTypeIds.ASSAULT),
    Control(MapTypeIds.CONTROL),
    Escort(MapTypeIds.ESCORT),
    Hybrid_Assault_Escort(MapTypeIds.HYBRID_ASSAULT_ESCORT);

    public final int typeId;

    MapType(int typeId) {
        this.typeId = typeId;
    }

    public static MapType FromInt(int id){
        switch(id) {
            case MapTypeIds.NONE:
                return None;
            case MapTypeIds.ASSAULT:
                return Assault;
            case MapTypeIds.CONTROL:
                return Control;
            case MapTypeIds.ESCORT:
                return Escort;
            case MapTypeIds.HYBRID_ASSAULT_ESCORT:
                return Hybrid_Assault_Escort;
        }
        throw new IntEnumCastException(id, MapType.class);
    }


    class MapTypeIds {
        public static final int NONE = 0;
        public static final int ASSAULT = 1;
        public static final int CONTROL = 2;
        public static final int ESCORT = 3;
        public static final int HYBRID_ASSAULT_ESCORT = 4;
    }
}
