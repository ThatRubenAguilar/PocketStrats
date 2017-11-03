package com.innovations.aguilar.pocketstrats.ui.dataholder;

import com.innovations.aguilar.pocketstrats.sql.dto.HeroDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapHeroPickTipDTO;

/**
 * Created by Ruben on 10/19/2017.
 */
public class MapHeroPickTipDataHolder {
    private HeroDataDTO heroData;


    private MapHeroPickTipDTO mapTip;

    public MapHeroPickTipDataHolder(HeroDataDTO heroData, MapHeroPickTipDTO mapTip) {
        this.heroData = heroData;
        this.mapTip = mapTip;
    }

    public String getMessage() {
        return mapTip.getMapTipDescription();
    }

    public HeroDataDTO getHeroData() {
        return heroData;
    }

    public MapHeroPickTipDTO getMapHeroPickTip() {
        return mapTip;
    }
}
