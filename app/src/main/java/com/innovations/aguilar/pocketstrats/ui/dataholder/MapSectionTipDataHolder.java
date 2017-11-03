package com.innovations.aguilar.pocketstrats.ui.dataholder;

import com.innovations.aguilar.pocketstrats.sql.dto.MapTipDTO;

import java.util.List;

/**
 * Created by Ruben on 10/19/2017.
 */
public class MapSectionTipDataHolder {
    public MapTipDTO getSectionTip() {
        return mapTip;
    }

    private MapTipDTO mapTip;

    public String getMessage() {
        return mapTip.getMapTipDescription();
    }

    public List<MapTipDataHolder> getMapTips() {
        return mapTips;
    }

    public List<MapHeroPickTipDataHolder> getMapHeroPickTips() {
        return mapHeroPickTips;
    }

    private List<MapTipDataHolder> mapTips;
    private List<MapHeroPickTipDataHolder> mapHeroPickTips;

    public MapSectionTipDataHolder(MapTipDTO mapTip, List<MapTipDataHolder> mapTips, List<MapHeroPickTipDataHolder> mapHeroPickTips) {
        this.mapTip = mapTip;
        this.mapTips = mapTips;
        this.mapHeroPickTips = mapHeroPickTips;
    }
}
