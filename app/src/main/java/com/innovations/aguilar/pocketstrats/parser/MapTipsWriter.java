package com.innovations.aguilar.pocketstrats.parser;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import com.innovations.aguilar.pocketstrats.logging.LoggerSupplier;
import com.innovations.aguilar.pocketstrats.sql.dto.HeroDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapHeroPickTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSegmentDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpecificTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubject;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapType;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTypeTip;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;
import com.innovations.aguilar.pocketstrats.sql.query.SqlDataAccessor;
import com.innovations.aguilar.pocketstrats.sql.write.SqlDataWriter;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLDataException;
import java.util.List;
import java.util.Map;

public class MapTipsWriter implements AutoCloseable {
    protected Supplier<Logger> log = Suppliers.memoize(new LoggerSupplier(this.getClass()));
    SqlDataWriter writer;
    SqlDataAccessor accessor;

    Map<String, MapDataDTO> mapShortNames = Maps.newHashMap();
    Map<String, MapSegmentDTO> mapIdAndSegmentNames = Maps.newHashMap();
    Map<String, HeroDataDTO> heroLatinNames = Maps.newHashMap();

    public MapTipsWriter(SqlDataWriter writer, SqlDataAccessor accessor) {
        this.writer = writer;
        this.accessor = accessor;
        for (MapDataDTO map :
                accessor.GetAllMaps()) {
            mapShortNames.put(map.getMapNameShort(), map);
        }

        for (HeroDataDTO hero:
                accessor.GetAllHeros()) {
            heroLatinNames.put(hero.getHeroNameLatin(), hero);
        }
        for (MapSegmentDTO segment:
                accessor.GetAllMapSegments()) {
            mapIdAndSegmentNames.put(String.format("%s_%s", segment.getMapId(), segment.getSegmentName()), segment);
        }
    }


    public boolean WriteTips(Reader mapTipsReader) {
        try (MapTipsTextParser nodeParser = new MapTipsTextParser(mapTipsReader)) {
            TipsDocument tDoc = new TipsDocument(nodeParser.allNodes());
            writeSubjects(tDoc.getSubjects());
            return true;
        }
        catch (IOException iex) {
            log.get().error(iex.toString());
        }
        catch (SQLDataException sdex) {
            log.get().error(sdex.toString());
        }
        return false;
    }

    Integer getKnownMapIdOrNull(String mapName) {
        if (mapShortNames.containsKey(mapName))
            return new Integer(mapShortNames.get(mapName).getMapId());
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

    // Subjects can be duplicated for sides
    // MapTips should not be duplicated more than per subject
    // MapTypeTips can be duplicated per maptype and per subject
    private void writeSubjects(List<SubjectNode> subjects) throws SQLDataException {
        for (SubjectNode subjNode :
                subjects) {
            Integer mapId = getKnownMapIdOrNull(subjNode.INode.MapName);
            Integer segmentId = getKnownSegmentIdOrNull(subjNode.INode.SegmentName, mapId);

            if (subjNode.INode.Sides != null && !subjNode.INode.Sides.isEmpty()) {
                for (SpawnSide spawnSide :
                        subjNode.INode.Sides) {
                    int subjectId = writeSubjectOrThrow(subjNode, mapId, spawnSide, segmentId);

                    if (mapShortNames.containsKey(subjNode.INode.MapName))
                        writeSectionsAsSpecificTips(subjectId, subjNode.SectionNodes);
                    else if (subjNode.INode.MapTypes != null && !subjNode.INode.MapTypes.isEmpty())
                        writeSectionsAsTypeTips(subjectId, subjNode.SectionNodes);
                    else
                    {
                        log.get().warn(String.format("Invalid subject's info node '%s'", subjNode.INode.toString()));
                    }
                }
            }
        }
    }

    private void writeSectionsAsSpecificTips(int subjectId, List<SectionNode> sections) throws SQLDataException {
        for (SectionNode sectNode :
                sections) {
            int sectionId = writeSpecificTipOrThrow(subjectId, sectNode, null);
            writeTipsAsSpecificTips(subjectId, sectionId, sectNode.TipNodes);
            writePicks(subjectId, sectionId, sectNode.PickNodes);

        }
    }


    private void writeSectionsAsTypeTips(int subjectId, List<SectionNode> sections) throws SQLDataException {
        for (SectionNode sectNode :
                sections) {
            int tipId = writeTipOrThrow(subjectId, sectNode, null);
            for (MapType mapType :
                    sectNode.INode.MapTypes) {
                writeTypeTipOrThrow(subjectId, sectNode, mapType, null, tipId);
            }
            writeTipsAsTypeTips(subjectId, tipId, sectNode.TipNodes);
            writePicks(subjectId, tipId, sectNode.PickNodes);
        }
    }

    private void writeTipsAsSpecificTips(int subjectId, int parentTipId, List<TipNode> tipNodes) throws SQLDataException {
        for (TipNode tipNode :
                tipNodes) {
            writeSpecificTipOrThrow(subjectId, tipNode, new Integer(parentTipId));
        }
    }
    private void writeTipsAsTypeTips(int subjectId, int parentTipId, List<TipNode> tipNodes) throws SQLDataException {
        for (TipNode tipNode :
                tipNodes) {
            int tipId = writeTipOrThrow(subjectId, tipNode, new Integer(parentTipId));
            for (MapType mapType :
                    tipNode.INode.MapTypes) {
                writeTypeTipOrThrow(subjectId, tipNode, mapType, new Integer(parentTipId), tipId);
            }
        }
    }


    private void writePicks(int subjectId, int parentTipId, List<PickNode> pickNodes) throws SQLDataException {
        for (PickNode pickNode :
                pickNodes) {
            if (heroLatinNames.containsKey(pickNode.Message)) {
                HeroDataDTO hero = heroLatinNames.get(pickNode.Message);
                for (TipNode tipNode :
                        pickNode.TipNodes) {
                    writeHeroPickTipOrThrow(subjectId, tipNode, hero.getHeroId(), parentTipId);
                }
            }
            else {
                log.get().warn(String.format("Unwritten tip '%s'", pickNode));
            }
        }
    }


    int writeSubjectOrThrow(SubjectNode subjNode, Integer mapId, SpawnSide spawnSide, Integer segmentId) throws SQLDataException {
        MapSubject subj = new MapSubject(0, subjNode.Precedence, subjNode.Message,
                mapId, spawnSide, spawnSide.toString(), segmentId);
        // Strategy -> MapSubject
        int subjectId = writer.WriteMapSubject(subj);
        if (subjectId < 0) {
            throw new SQLDataException(String.format("Failed to write DTO '%s'", subj));
        }
        return subjectId;
    }
    int writeSpecificTipOrThrow(int subjectId, TipNode tipNode, Integer parentTipId) throws SQLDataException {
        return writeSpecificTipOrThrow(subjectId, tipNode, parentTipId, 0);
    }
    int writeSpecificTipOrThrow(int subjectId, TipNode tipNode, Integer parentTipId, int mapTipId) throws SQLDataException {
        // MapName => Strategy => Section -> MapSpecificTip w/o parent tip
        // MapName => Strategy => Section => Tip -> MapSpecificTip w/ parent tip
        MapSpecificTip sect = new MapSpecificTip(mapTipId, subjectId, tipNode.Precedence,
                tipNode.Message, parentTipId, 0);
        int specificTipId = writer.WriteMapSpecificTip(sect);
        if (specificTipId < 0) {
            throw new SQLDataException(String.format("Failed to write DTO '%s'", sect));
        }
        return specificTipId;
    }


    int writeTypeTipOrThrow(int subjectId, TipNode tipNode, MapType mapType, Integer parentTipId) throws SQLDataException {
        return writeTypeTipOrThrow(subjectId, tipNode, mapType, parentTipId, 0);
    }
    int writeTypeTipOrThrow(int subjectId, TipNode tipNode, MapType mapType, Integer parentTipId, int mapTipId) throws SQLDataException {
        // Category => Subject => Section -> MapTypeTip w/o parent tip
        // Category => Subject => Section => Tip -> MapTypeTip w/ parent tip
        MapTypeTip sect = new MapTypeTip(mapTipId, subjectId, tipNode.Precedence,
                tipNode.Message, parentTipId, 0, mapType);
        int typeTipId = writer.WriteMapTypeTip(sect);
        if (typeTipId < 0) {
            throw new SQLDataException(String.format("Failed to write DTO '%s'", sect));
        }
        return typeTipId;
    }

    int writeHeroPickTipOrThrow(int subjectId, TipNode tipNode, int heroId, Integer parentTipId) throws SQLDataException {
        return writeHeroPickTipOrThrow(subjectId, tipNode, heroId, parentTipId, 0);
    }
    int writeHeroPickTipOrThrow(int subjectId, TipNode tipNode, int heroId, Integer parentTipId, int mapTipId) throws SQLDataException {
        // Pick => Tip => MapHeroPickTip w/ parent tip
        MapHeroPickTip tip = new MapHeroPickTip(mapTipId, subjectId, tipNode.Precedence,
                tipNode.Message, parentTipId, 0, heroId);
        int heroPickTipId =  writer.WriteMapHeroPickTip(tip);
        if (heroPickTipId < 0) {
            throw new SQLDataException(String.format("Failed to write DTO '%s'", tip));
        }
        return heroPickTipId;
    }
    int writeTipOrThrow(int subjectId, TipNode tipNode, Integer parentTipId) throws SQLDataException {

        MapTip tip = new MapTip(0, subjectId, tipNode.Precedence,
                tipNode.Message, parentTipId);
        int tipId =  writer.WriteMapTip(tip);
        if (tipId < 0) {
            throw new SQLDataException(String.format("Failed to write DTO '%s'", tip));
        }
        return tipId;
    }


    @Override
    public void close() throws Exception {
        if (accessor != null)
            accessor.close();
        if (writer != null)
            writer.close();
    }
}
