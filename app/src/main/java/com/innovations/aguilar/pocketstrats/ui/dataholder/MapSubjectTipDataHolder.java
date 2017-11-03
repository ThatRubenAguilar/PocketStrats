package com.innovations.aguilar.pocketstrats.ui.dataholder;

import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectDTO;

import java.util.List;

/**
 * Created by Ruben on 10/19/2017.
 */
public class MapSubjectTipDataHolder {
    public MapSubjectDTO getMapSubject() {
        return mapSubject;
    }

    public List<MapSectionTipDataHolder> getMapSectionTips() {
        return mapSectionTips;
    }

    // We can load in ctor with parcelable
    private MapSubjectDTO mapSubject;
    private List<MapSectionTipDataHolder> mapSectionTips;


    public MapSubjectTipDataHolder(MapSubjectDTO mapSubject, List<MapSectionTipDataHolder> mapSectionTips) {
        this.mapSubject = mapSubject;
        this.mapSectionTips = mapSectionTips;
    }

}
