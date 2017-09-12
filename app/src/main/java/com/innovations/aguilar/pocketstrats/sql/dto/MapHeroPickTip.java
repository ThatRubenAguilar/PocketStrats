package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

/**
 * Created by Ruben on 9/11/2017.
 */


public class MapHeroPickTip extends MapTip implements MapHeroPickTipDTO {

    private final int mapPickTipId;
    private final int heroId;

    public MapHeroPickTip(int mapTipId, int mapSubjectId, int orderPrecedence,
                          String mapTipDescription, Integer parentMapTipId,
                            int mapPickTipId, int heroId) {
        super(mapTipId, mapSubjectId, orderPrecedence, mapTipDescription, parentMapTipId);
        this.mapPickTipId = mapPickTipId;
        this.heroId = heroId;
    }

    public MapHeroPickTip(Cursor c) {
        super(c);
        this.mapPickTipId = c.getInt(c.getColumnIndex(MapPickTipIdColumn));
        this.heroId = c.getInt(c.getColumnIndex(HeroIdColumn));
    }

    @Override
    public int getMapPickTipId() {
        return mapPickTipId;
    }
    @Override
    public int getHeroId() {
        return heroId;
    }


    public static final String MapTipIdColumn = "MapTipId";
    public static final String MapPickTipIdColumn = "MapPickTipId";
    public static final String HeroIdColumn = "HeroId";

    public static final String TableName = "MapHeroPickTips";

    public static final String[] ColumnNames = {
            String.format("%s.%s", TableName, MapTipIdColumn),
            String.format("%s.%s", TableName, MapPickTipIdColumn),
            String.format("%s.%s", TableName, HeroIdColumn)
    };


}
