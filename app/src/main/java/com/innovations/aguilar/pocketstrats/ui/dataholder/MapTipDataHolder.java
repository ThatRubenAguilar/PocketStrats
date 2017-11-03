package com.innovations.aguilar.pocketstrats.ui.dataholder;

import com.innovations.aguilar.pocketstrats.sql.dto.MapTipDTO;

/**
 * Created by Ruben on 10/19/2017.
 */
public class MapTipDataHolder {
    public MapTipDTO getMapTip() {
        return mapTip;
    }

    private MapTipDTO mapTip;

    public String getMessage() {
        return mapTip.getMapTipDescription();
    }


    public MapTipDataHolder(MapTipDTO mapTip) {

        this.mapTip = mapTip;
    }
}
