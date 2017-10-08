package com.innovations.aguilar.pocketstrats.parser;

import android.util.Log;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.innovations.aguilar.pocketstrats.logging.LoggerSupplier;
import com.innovations.aguilar.pocketstrats.sql.dto.HeroDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapHeroPickTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapHeroPickTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSegmentDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpecificTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpecificTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubject;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectAssociation;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectAssociationDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTipDescriptionDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapType;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTypeTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTypeTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;
import com.innovations.aguilar.pocketstrats.sql.query.SqlDataAccessor;
import com.innovations.aguilar.pocketstrats.sql.write.SqlDataWriter;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLDataException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MapTipsWriter implements AutoCloseable {
    protected Supplier<Logger> log = Suppliers.memoize(new LoggerSupplier(this.getClass()));
    SqlDataWriter writer;
    SqlDataAccessor accessor;

    Map<String, MapDataDTO> mapShortNamesMap = Maps.newHashMap();
    Map<MapType, List<MapDataDTO>> mapTypesMap = Maps.newHashMap();
    Map<String, MapSegmentDTO> mapIdAndSegmentNames = Maps.newHashMap();
    Map<String, HeroDataDTO> heroLatinNames = Maps.newHashMap();

    public MapTipsWriter(SqlDataWriter writer, SqlDataAccessor accessor) {
        this.writer = writer;
        this.accessor = accessor;
        for (MapDataDTO map :
                accessor.mapAccessor().GetAllMaps()) {
            mapShortNamesMap.put(map.getMapNameShort(), map);

            if (!mapTypesMap.containsKey(map.getMapTypeId()))
                mapTypesMap.put(map.getMapTypeId(), Lists.<MapDataDTO>newArrayList());
            mapTypesMap.get(map.getMapTypeId()).add(map);
        }

        for (HeroDataDTO hero:
                accessor.GetAllHeros()) {
            heroLatinNames.put(hero.getHeroNameLatin(), hero);
        }
        for (MapSegmentDTO segment:
                accessor.mapAccessor().GetAllMapSegments()) {
            mapIdAndSegmentNames.put(String.format("%s_%s", segment.getMapId(), segment.getSegmentName()), segment);
        }
    }


    public TipsDocument WriteTips(Reader mapTipsReader) {
        return WriteTips(mapTipsReader, null);
    }
    public TipsDocument WriteTips(Reader mapTipsReader, TipsDocument continueDoc) {
        try (MapTipsTextParser nodeParser = new MapTipsTextParser(mapTipsReader)) {
            TipsDocument tDoc = new TipsDocument(nodeParser.allNodes(), continueDoc);
            writeSubjects(tDoc.getSubjects());
            return tDoc;
        }
        catch (IOException iex) {
            log.get().error(iex.toString());
        }
        catch (SQLDataException sdex) {
            log.get().error(sdex.toString());
        }
        return null;
    }

    public boolean WriteSubjectAssociations() {
        try {
            writeSubjectAssociations();
            return true;
        }
        catch (SQLDataException sdex) {
            log.get().error(sdex.toString());
        }
        return false;
    }

    List<MapDataDTO> getKnownMaps(InfoNode infoNode) {
        if (mapShortNamesMap.containsKey(infoNode.MapName))
            return Lists.newArrayList(mapShortNamesMap.get(infoNode.MapName));
        else if (infoNode.MapTypes != null && infoNode.MapTypes.size() > 0) {
            List<MapDataDTO> maps = Lists.newArrayList();
            for (MapType mapType :
                    infoNode.MapTypes) {
                maps.addAll(mapTypesMap.get(mapType));
            }
            return maps;
        }
        return null;
    }

    Integer getKnownSegmentIdOrNull(String segmentName, Integer mapId) {
        if (mapId != null && segmentName != null) {
            String segmentNameKey = String.format("%s_%s", mapId.intValue(), segmentName);
            if (mapIdAndSegmentNames.containsKey(segmentNameKey))
                return mapIdAndSegmentNames.get(segmentNameKey).getSegmentId();
        }
        return null;
    }

    private interface Matcher<T> {
        boolean match(T one, T two);
    }

    private void writeSubjectAssociations() throws SQLDataException {
        List<MapSubjectDTO> subjects = accessor.mapSubjectAccessor().GetAllMapSubjects();

        Map<Integer, List<MapSubjectDTO>> mapDataMap = Maps.newTreeMap();

        // Subject association is on a per map basis
        for (MapSubjectDTO subject :
             subjects) {

            Integer mapIdKey = subject.getMapId();

            if (!mapDataMap.containsKey(mapIdKey))
                mapDataMap.put(mapIdKey, Lists.<MapSubjectDTO>newArrayList());

            List<MapSubjectDTO> subjectList = mapDataMap.get(mapIdKey);
            subjectList.add(subject);
        }

        Set<MapSubjectAssociationDTO> subjectAssociations = Sets.newHashSet();

        for (Map.Entry<Integer, List<MapSubjectDTO>> mapDataEntry :
             mapDataMap.entrySet()) {
            List<MapSubjectDTO> subjectList = mapDataEntry.getValue();

            Matcher<MapSubjectDTO> subjectMatcher = new Matcher<MapSubjectDTO>() {
                    @Override
                    public boolean match(MapSubjectDTO one, MapSubjectDTO two) {
                        return mapSubjectsAssociated(one, two);
                    }
                };


            for (MapSubjectDTO masterSubject :
                    subjectList) {
                for (MapSubjectDTO possibleAssociatedSubject :
                        subjectList) {
                    if (subjectMatcher.match(masterSubject, possibleAssociatedSubject)) {
                        subjectAssociations.add(new MapSubjectAssociation(
                                masterSubject.getMapSubjectId(), possibleAssociatedSubject.getMapSubjectId()));
                    }
                }
            }

        }

        for (MapSubjectAssociationDTO associatedSubject :
                subjectAssociations) {
            writeSubjectAssociationOrThrow(associatedSubject);
        }
    }

    private void writeSubjectAssociationOrThrow(MapSubjectAssociationDTO associationDTO) throws SQLDataException {
        int associationId = writer.WriteMapSubjectAssociation(associationDTO);
        if (associationId < 0) {
            throw new SQLDataException(String.format("Failed to write DTO '%s'", associationDTO));
        }
    }

    private boolean mapTypeSubjectsAssociated(MapSubjectDTO one, MapSubjectDTO two) {
        return mapSubjectsAssociated(one, two) &&
                one.getMapTipDescription().equals(two.getMapTipDescription());
    }

    private boolean mapSubjectsAssociated(MapSubjectDTO one, MapSubjectDTO two) {
        return Objects.equals(one.getMapId(), two.getMapId()) &&
                one.getMapSubjectPrecedence() == two.getMapSubjectPrecedence() &&
                one.getSpawnSideId() != two.getSpawnSideId();
    }

    // Subjects can be duplicated for sides per map
    // MapTips should not be duplicated more than per subject
    // MapTypeTips should be 1:1 with MapTips
    // MapSpecificTips should be 1:1 with MapTips
    private void writeSubjects(List<SubjectNode> subjects) throws SQLDataException {
        for (SubjectNode subjNode :
                subjects) {
            List<MapDataDTO> maps = getKnownMaps(subjNode.INode);
            if (maps == null) {
                Log.e(this.getClass().toString(), String.format("Null maps for subject '%s'", subjNode));
            }

            for (MapDataDTO map :
                    maps) {
                Integer segmentId = getKnownSegmentIdOrNull(subjNode.INode.SegmentName, map.getMapId());

                if (subjNode.INode.Sides != null && !subjNode.INode.Sides.isEmpty()) {
                    for (SpawnSide spawnSide :
                            subjNode.INode.Sides) {
                        MapSubjectDTO subject = writeSubjectOrThrow(subjNode, map, spawnSide, segmentId);

                        if (mapShortNamesMap.containsKey(subjNode.INode.MapName))
                            writeSectionsAsSpecificTips(subject, map, subjNode.SectionNodes);
                        else if (subjNode.INode.MapTypes != null && !subjNode.INode.MapTypes.isEmpty())
                            writeSectionsAsTypeTips(subject, map, subjNode.SectionNodes);
                        else {
                            log.get().warn(String.format("Invalid subject's info node '%s'", subjNode.INode.toString()));
                        }
                    }
                }
            }
        }
    }

    private void writeSectionsAsSpecificTips(MapSubjectDTO subject,MapDataDTO map,
                                             List<SectionNode> sections) throws SQLDataException {
        for (SectionNode sectNode :
                sections) {
            if (inMapTypeCategory(map, sectNode.INode)) {
                MapTipDTO parentTip = writeSpecificTipOrThrow(subject, sectNode, null);
                writeTipsAsSpecificTips(subject, parentTip, sectNode.TipNodes);
                writePicks(subject, parentTip, sectNode.PickNodes);
            }

        }
    }


    private void writeSectionsAsTypeTips(MapSubjectDTO subject,MapDataDTO map,
                                         List<SectionNode> sections) throws SQLDataException {
        for (SectionNode sectNode :
                sections) {
            if (inMapTypeCategory(map, sectNode.INode)) {
                MapTipDTO mapTip = writeTypeTipOrThrow(subject, sectNode, map.getMapTypeId(), null);
                writeTipsAsTypeTips(subject, mapTip, map.getMapTypeId(), sectNode.TipNodes);
                writePicks(subject, mapTip, sectNode.PickNodes);
            }

        }
    }

    private void writeTipsAsSpecificTips(MapSubjectDTO subject, MapTipDTO parentTip, List<TipNode> tipNodes) throws SQLDataException {
        for (TipNode tipNode :
                tipNodes) {
            writeSpecificTipOrThrow(subject, tipNode, parentTip.getMapTipId());
        }
    }
    private void writeTipsAsTypeTips(MapSubjectDTO subject, MapTipDTO parentTip, MapType mapType, List<TipNode> tipNodes) throws SQLDataException {
        for (TipNode tipNode :
                tipNodes) {
            writeTypeTipOrThrow(subject, tipNode, mapType, parentTip.getMapTipId());

        }
    }


    private void writePicks(MapSubjectDTO subject, MapTipDTO parentTip, List<PickNode> pickNodes) throws SQLDataException {
        for (PickNode pickNode :
                pickNodes) {
            if (heroLatinNames.containsKey(pickNode.Message)) {
                HeroDataDTO hero = heroLatinNames.get(pickNode.Message);
                for (TipNode tipNode :
                        pickNode.TipNodes) {
                    writeHeroPickTipOrThrow(subject, tipNode, hero.getHeroId(), parentTip.getMapTipId());
                }
            }
            else {
                log.get().warn(String.format("Unwritten tip '%s'", pickNode));
            }
        }
    }


    MapSubjectDTO writeSubjectOrThrow(SubjectNode subjNode, MapDataDTO map, SpawnSide spawnSide, Integer segmentId) throws SQLDataException {
        int mapTipDescriptionId = getExistingMapTipDescriptionIdOrZero(subjNode);

        MapSubject subj = new MapSubject(0, subjNode.Precedence,
                map.getMapId(), spawnSide, spawnSide.toString(), segmentId,
                mapTipDescriptionId, subjNode.Message.hashCode(), subjNode.Message);
        // Strategy -> MapSubject
        int subjectId = writer.WriteMapSubject(subj);
        if (subjectId < 0) {
            throw new SQLDataException(String.format("Failed to write DTO '%s'", subj));
        }
        return accessor.mapSubjectAccessor().GetMapSubjectById(subjectId);
    }
    MapSpecificTipDTO writeSpecificTipOrThrow(MapSubjectDTO subject, TipNode tipNode, Integer parentTipId) throws SQLDataException {
        int mapTipDescriptionId = getExistingMapTipDescriptionIdOrZero(tipNode);
        // MapName => Strategy => Section -> MapSpecificTip w/o parent tip
        // MapName => Strategy => Section => Tip -> MapSpecificTip w/ parent tip
        MapSpecificTip sect = new MapSpecificTip(0, subject.getMapSubjectId(), tipNode.Precedence,
                parentTipId, 0,
                mapTipDescriptionId, tipNode.Message.hashCode(), tipNode.Message);
        int specificTipId = writer.WriteMapSpecificTip(sect);
        if (specificTipId < 0) {
            throw new SQLDataException(String.format("Failed to write DTO '%s'", sect));
        }
        return accessor.mapTipAccessor().GetMapSpecificTipById(specificTipId);
    }


    MapTypeTipDTO writeTypeTipOrThrow(MapSubjectDTO subject, TipNode tipNode, MapType mapType,
                                      Integer parentTipId) throws SQLDataException {
        int mapTipDescriptionId = getExistingMapTipDescriptionIdOrZero(tipNode);
        // Category => Subject => Section -> MapTypeTip w/o parent tip
        // Category => Subject => Section => Tip -> MapTypeTip w/ parent tip
        MapTypeTip sect = new MapTypeTip(0, subject.getMapSubjectId(), tipNode.Precedence,
                parentTipId, 0, mapType,
                mapTipDescriptionId, tipNode.Message.hashCode(), tipNode.Message);
        int typeTipId = writer.WriteMapTypeTip(sect);
        if (typeTipId < 0) {
            throw new SQLDataException(String.format("Failed to write DTO '%s'", sect));
        }
        return accessor.mapTipAccessor().GetMapTypeTipById(typeTipId);
    }

    MapHeroPickTipDTO writeHeroPickTipOrThrow(MapSubjectDTO subject, TipNode tipNode, int heroId,
                                              Integer parentTipId) throws SQLDataException {
        int mapTipDescriptionId = getExistingMapTipDescriptionIdOrZero(tipNode);
        // Pick => Tip => MapHeroPickTip w/ parent tip
        MapHeroPickTip tip = new MapHeroPickTip(0, subject.getMapSubjectId(), tipNode.Precedence,
                parentTipId, 0, heroId,
                mapTipDescriptionId, tipNode.Message.hashCode(), tipNode.Message);
        int heroPickTipId =  writer.WriteMapHeroPickTip(tip);
        if (heroPickTipId < 0) {
            throw new SQLDataException(String.format("Failed to write DTO '%s'", tip));
        }
        return accessor.mapTipAccessor().GetMapHeroPickTipById(heroPickTipId);
    }
    MapTipDTO writeTipOrThrow(MapSubjectDTO subject, TipNode tipNode, Integer parentTipId) throws SQLDataException {
        int mapTipDescriptionId = getExistingMapTipDescriptionIdOrZero(tipNode);

        MapTip tip = new MapTip(0, subject.getMapSubjectId(), tipNode.Precedence, parentTipId,
                mapTipDescriptionId, tipNode.Message.hashCode(), tipNode.Message);
        int tipId =  writer.WriteMapTip(tip);
        if (tipId < 0) {
            throw new SQLDataException(String.format("Failed to write DTO '%s'", tip));
        }
        return accessor.mapTipAccessor().GetMapTipById(tipId);
    }

    int getExistingMapTipDescriptionIdOrZero(TipNode tipNode) {
        List<MapTipDescriptionDTO> tipDescs = accessor.mapTipAccessor().GetMapTipDescriptionsByHash(tipNode.Message.hashCode());
        if (tipDescs.size() == 0)
            return 0;

        for (MapTipDescriptionDTO tipDesc :
                tipDescs) {
            if (tipDesc.getMapTipDescription().equals(tipNode.Message))
                return tipDesc.getMapTipDescriptionId();
        }
        return 0;
    }

    boolean inMapTypeCategory(MapDataDTO map, InfoNode iNode) {
        return (iNode.MapTypes == null || iNode.MapTypes.isEmpty()) ||
                iNode.MapTypes.contains(map.getMapTypeId());
    }

    @Override
    public void close() throws Exception {
        if (accessor != null)
            accessor.close();
        if (writer != null)
            writer.close();
    }
}
