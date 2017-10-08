package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

import com.google.common.collect.Lists;
import com.innovations.aguilar.pocketstrats.sql.query.DTOFromCursorFactory;

import java.util.List;

public class MapHeroPickTip extends MapTip implements MapHeroPickTipDTO {
    public static DTOFromCursorFactory<MapHeroPickTipDTO> Factory =
            new DTOFromCursorFactory<MapHeroPickTipDTO>() {
                @Override
                public MapHeroPickTipDTO Create(Cursor c) { return new MapHeroPickTip(c); }
            };


    private final int mapPickTipId;
    private final int heroId;

    public MapHeroPickTip(int mapTipId, int mapSubjectId, int orderPrecedence,
                          Integer parentMapTipId, int mapPickTipId, int heroId,
                          int mapTipDescriptionId, int mapTipDescriptionHash, String mapTipDescription) {
        super(mapTipId, mapSubjectId, orderPrecedence, parentMapTipId,
                mapTipDescriptionId, mapTipDescriptionHash, mapTipDescription);
        this.mapPickTipId = mapPickTipId;
        this.heroId = heroId;
    }

    public MapHeroPickTip(Cursor c) {
        super(c);
        this.mapPickTipId = c.getInt(c.getColumnIndex(Columns.MapPickTipIdColumn));
        this.heroId = c.getInt(c.getColumnIndex(Columns.HeroIdColumn));
    }

    @Override
    public int getMapPickTipId() {
        return mapPickTipId;
    }
    @Override
    public int getHeroId() {
        return heroId;
    }

    public static final MapHeroPickTipColumns Columns = new MapHeroPickTipColumns();


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MapHeroPickTip{");
        sb.append("mapPickTipId=").append(mapPickTipId);
        sb.append(", heroId=").append(heroId);
        sb.append(", MapTip=").append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
