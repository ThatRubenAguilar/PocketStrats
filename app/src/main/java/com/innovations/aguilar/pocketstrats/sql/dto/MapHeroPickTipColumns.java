package com.innovations.aguilar.pocketstrats.sql.dto;

import com.google.common.collect.Lists;

/**
 * Created by Ruben on 9/11/2017.
 */


public class MapHeroPickTipColumns extends DTOColumnInfo {

    public static final String MapTipIdColumn = "MapTipId";
    public static final String MapPickTipIdColumn = "MapPickTipId";
    public static final String HeroIdColumn = "HeroId";

    public MapHeroPickTipColumns() {
        super("MapHeroPickTips", Lists.newArrayList(MapTipIdColumn, MapPickTipIdColumn, HeroIdColumn));
    }
}
