package com.innovations.aguilar.pocketstrats.sql.dto;

import com.innovations.aguilar.pocketstrats.exceptions.IntEnumCastException;

public enum SpawnSide {
    None(SpawnSideIds.NONE),
    Attack(SpawnSideIds.ATTACK),
    Defend(SpawnSideIds.DEFEND);

    public final int spawnSideId;

    SpawnSide(int spawnSideId) {
        this.spawnSideId = spawnSideId;
    }


    public static SpawnSide FromInt(int id){
        switch(id) {
            case SpawnSideIds.NONE:
                return None;
            case SpawnSideIds.ATTACK:
                return Attack;
            case SpawnSideIds.DEFEND:
                return Defend;
        }
        throw new IntEnumCastException(id, SpawnSide.class);
    }


    class SpawnSideIds {
        public static final int NONE = 0;
        public static final int ATTACK = 1;
        public static final int DEFEND = 2;
    }
}
