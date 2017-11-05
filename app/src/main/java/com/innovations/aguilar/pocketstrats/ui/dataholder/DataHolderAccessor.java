package com.innovations.aguilar.pocketstrats.ui.dataholder;

import android.content.Context;
import android.util.Log;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.innovations.aguilar.pocketstrats.sql.dto.HeroDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapHeroPickTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpecificTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTypeTipDTO;
import com.innovations.aguilar.pocketstrats.sql.query.OwnedSqlDataAccessor;
import com.innovations.aguilar.pocketstrats.sql.query.SqlDataAccessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by Ruben on 10/19/2017.
 */
public class DataHolderAccessor {
    protected static Logger log = LoggerFactory.getLogger(DataHolderAccessor.class);

    private final OwnedSqlDataAccessor ownedAccessor;

    public DataHolderAccessor(Context context) {
        this.ownedAccessor = new OwnedSqlDataAccessor(context);
    }

    public DataHolderAccessor(SqlDataAccessor accessor) {
        this.ownedAccessor = new OwnedSqlDataAccessor(accessor);
    }

    public MapSubjectTipDataHolder queryDataHolderFromSubject(MapSubjectDTO subject) {
        List<MapSpecificTipDTO> specificTips;
        List<MapTypeTipDTO> typeTips;
        List<MapHeroPickTipDTO> heroTips;
        List<HeroDataDTO> heroData;
        try (SqlDataAccessor accessor = ownedAccessor.getAccessor()) {
            specificTips = accessor.mapTipAccessor().GetMapSpecificTipsByMapSubject(subject.getMapSubjectId());
            typeTips = accessor.mapTipAccessor().GetMapTypeTipsByMapSubject(subject.getMapSubjectId());
            heroTips = accessor.mapTipAccessor().GetMapHeroPickTipBySubject(subject.getMapSubjectId());
            heroData = accessor.GetAllHeros();
        }

        Map<Integer, HeroDataDTO> heroMap = Maps.newHashMap();
        for (HeroDataDTO heroDatum :
                heroData) {
            Integer key = heroDatum.getHeroId();
            if (!heroMap.containsKey(key))
                heroMap.put(key, heroDatum);
        }

        List<MapSectionTipDataHolder> sections = createSections(specificTips, typeTips, heroTips, heroMap);
        return new MapSubjectTipDataHolder(subject, sections);
    }

    private List<MapSectionTipDataHolder> createSections(List<MapSpecificTipDTO> specificTips,
                                                         List<MapTypeTipDTO> typeTips,
                                                         List<MapHeroPickTipDTO> heroTips,
                                                         Map<Integer, HeroDataDTO> heroMap) {

        Map<Integer, MapTipDTO> tipIdToSectionMap = Maps.newHashMap();
        Map<MapTipDTO, List<MapTipDTO>> sectionToTipsMap = Maps.newTreeMap(new Comparator<MapTipDTO>() {
            @Override
            public int compare(MapTipDTO t1, MapTipDTO t2) {
                return Integer.compare(t1.getOrderPrecedence(), t2.getOrderPrecedence());
            }
        });

        Map<MapTipDTO, List<MapHeroPickTipDTO>> sectionToHeroPicksMap = Maps.newTreeMap(new Comparator<MapTipDTO>() {
            @Override
            public int compare(MapTipDTO t1, MapTipDTO t2) {
                return Integer.compare(t1.getOrderPrecedence(), t2.getOrderPrecedence());
            }
        });

        // Keys
        populateTipMapKeysForSections(specificTips, tipIdToSectionMap, sectionToTipsMap);
        populateTipMapKeysForSections(typeTips, tipIdToSectionMap, sectionToTipsMap);
        copyKeys(sectionToTipsMap, sectionToHeroPicksMap);
        // Values
        populateTipMapValuesForTips(specificTips, tipIdToSectionMap, sectionToTipsMap);
        populateTipMapValuesForTips(typeTips, tipIdToSectionMap, sectionToTipsMap);
        populateTipMapValuesForTips(heroTips, tipIdToSectionMap, sectionToHeroPicksMap);

        List<MapSectionTipDataHolder> sections = Lists.newArrayList();
        for (Map.Entry<MapTipDTO, List<MapTipDTO>> sectionToTipEntry :
                sectionToTipsMap.entrySet()) {
            MapTipDTO section = sectionToTipEntry.getKey();
            List<MapTipDTO> tips = sectionToTipEntry.getValue();
            List<MapTipDataHolder> tipDataHolders = Lists.newArrayList();
            for (MapTipDTO tip :
                    tips) {
                tipDataHolders.add(new MapTipDataHolder(tip));
            }

            List<MapHeroPickTipDataHolder> heroPickTips = Lists.<MapHeroPickTipDataHolder>newArrayList();
            if (sectionToHeroPicksMap.containsKey(section)) {
                List<MapHeroPickTipDTO> heroPicks = sectionToHeroPicksMap.get(section);
                for (MapHeroPickTipDTO heroPick :
                        heroPicks) {
                    heroPickTips.add(new MapHeroPickTipDataHolder(heroMap.get(heroPick.getHeroId()), heroPick));
                }
            }
            sections.add(new MapSectionTipDataHolder(section,
                    tipDataHolders, heroPickTips));
        }

        return sections;
    }

    private <TInTipType extends TOutTipType, TOutTipType extends MapTipDTO> void populateTipMapValuesForTips(List<TInTipType> mapTips,
                                                                            Map<Integer, MapTipDTO> tipIdToSectionMap,
                                                                            Map<MapTipDTO, List<TOutTipType>> sectionToTipsMap) {
        for (TInTipType mapTip :
                mapTips) {
            if (!isSection(mapTip)) {
                if (!tipIdToSectionMap.containsKey(mapTip.getParentMapTipId()))
                    log.warn("Child tip without parent in query '{}'", mapTip);
                else {
                    MapTipDTO parent = tipIdToSectionMap.get(mapTip.getParentMapTipId());
                    List<TOutTipType> tips = sectionToTipsMap.get(parent);
                    tips.add(mapTip);
                }
            }
        }
    }

    private <TInTipType extends MapTipDTO> void populateTipMapKeysForSections(List<TInTipType> mapTips,
                                                                              Map<Integer, MapTipDTO> tipIdToSectionMap,
                                                                              Map<MapTipDTO, List<MapTipDTO>> sectionToTipsMap) {
        for (MapTipDTO mapTip :
                mapTips) {
            if (isSection(mapTip)) {
                if (!sectionToTipsMap.containsKey(mapTip))
                    sectionToTipsMap.put(mapTip, Lists.<MapTipDTO>newArrayList());
                if (!tipIdToSectionMap.containsKey(mapTip.getMapTipId()))
                    tipIdToSectionMap.put(new Integer(mapTip.getMapTipId()), mapTip);
            }
        }
    }

    private <TInTipType extends MapTipDTO, TOutTipType extends MapTipDTO> void copyKeys(Map<MapTipDTO, List<TInTipType>> source,
                                                                                        Map<MapTipDTO, List<TOutTipType>> dest) {
        for (Map.Entry<MapTipDTO, List<TInTipType>> entry :
                source.entrySet()) {
            if (!dest.containsKey(entry.getKey()))
                dest.put(entry.getKey(), Lists.<TOutTipType>newArrayList());
        }
    }


    private static boolean isSection(MapTipDTO tip){
        return tip.getParentMapTipId() == null;
    }

}
