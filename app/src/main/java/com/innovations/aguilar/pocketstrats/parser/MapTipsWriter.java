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
import com.innovations.aguilar.pocketstrats.sql.dto.MapType;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTypeTip;
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
        try (MapTipsNodeParser nodeParser = new MapTipsNodeParser(mapTipsReader)) {
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

    private void writeSubjects(List<SubjectNode> subjects) throws SQLDataException {
        for (SubjectNode subjNode :
                subjects) {
            Integer mapId = null;
            if (mapShortNames.containsKey(subjNode.INode.MapName))
                mapId = new Integer(mapShortNames.get(subjNode.INode.MapName).getMapId());
            Integer segmentId = null;
            if (mapId != null && subjNode.INode.SegmentName != null) {
                String segmentNameKey = String.format("%s_%s", mapId.intValue(), subjNode.INode.SegmentName);
                if (mapIdAndSegmentNames.containsKey(segmentNameKey))
                    segmentId = mapIdAndSegmentNames.get(segmentNameKey).getSegmentId();
            }
            MapSubject subj = new MapSubject(subjNode.Precedence, subjNode.Message,
                    mapId, subjNode.INode.Side, subjNode.INode.Side.toString(), segmentId);
            // Strategy -> MapSubject
            int subjectId = (int)writer.WriteMapSubject(subj);
            if (subjectId < 0) {
                throw new SQLDataException(String.format("Failed to write DTO '%s'", subj));
            }
            writeSections(subjectId, subjNode.SectionNodes);

        }
    }

    private void writeSections(int subjectId, List<SectionNode> sections) throws SQLDataException {
        for (SectionNode sectNode :
                sections) {
            // MapName => Strategy => Section -> MapSpecificTip w/o parent tip
            if (mapShortNames.containsKey(sectNode.INode.MapName)) {
                MapSpecificTip sect = new MapSpecificTip(0, subjectId, sectNode.Precedence,
                        sectNode.Message, null, 0);
                int sectionId = (int)writer.WriteMapSpecificTip(sect);
                if (sectionId < 0) {
                    throw new SQLDataException(String.format("Failed to write DTO '%s'", sect));
                }
                writeTips(subjectId, sectNode, sectionId, sectNode.TipNodes);
                writePicks(subjectId, sectionId, sectNode.PickNodes);
            }
            // Category => Subject => Section -> MapTypeTip w/o parent tip
            else if (sectNode.INode.MapTypes != null && !sectNode.INode.MapTypes.isEmpty()) {
                for (MapType mapType :
                        sectNode.INode.MapTypes) {
                    MapTypeTip sect = new MapTypeTip(0, subjectId, sectNode.Precedence,
                            sectNode.Message, null, 0, mapType);
                    int sectionId = (int)writer.WriteMapTypeTip(sect);
                    if (sectionId < 0) {
                        throw new SQLDataException(String.format("Failed to write DTO '%s'", sect));
                    }
                    writeTips(subjectId, sectNode, sectionId, sectNode.TipNodes);
                    writePicks(subjectId, sectionId, sectNode.PickNodes);
                }
            }
            else
            {
                log.get().warn(String.format("Unwritten section '%s'", sectNode));
            }
        }
    }

    private void writeTips(int subjectId, SectionNode sectNode, int parentTipId, List<TipNode> tipNodes) throws SQLDataException {
        for (TipNode tipNode :
                tipNodes) {
            // MapName => Strategy => Section => Tip -> MapSpecificTip w/ parent tip
            if (mapShortNames.containsKey(sectNode.INode.MapName)) {
                MapSpecificTip tip = new MapSpecificTip(0, subjectId, tipNode.Precedence,
                        tipNode.Message, parentTipId, 0);
                int tipId = (int) writer.WriteMapSpecificTip(tip);
                if (tipId < 0) {
                    throw new SQLDataException(String.format("Failed to write DTO '%s'", tip));
                }
            }
            // Category => Subject => Section => Tip -> MapTypeTip w/ parent tip
            else if (sectNode.INode.MapTypes != null && !sectNode.INode.MapTypes.isEmpty()) {
                for (MapType mapType :
                        sectNode.INode.MapTypes) {
                    MapTypeTip tip = new MapTypeTip(0, subjectId, tipNode.Precedence,
                            tipNode.Message, parentTipId, 0, mapType);
                    int tipId = (int)writer.WriteMapTypeTip(tip);
                    if (tipId < 0) {
                        throw new SQLDataException(String.format("Failed to write DTO '%s'", tip));
                    }
                }
            } else {
                log.get().warn(String.format("Unwritten tip '%s'", tipNode));
            }
        }
    }

    private void writePicks(int subjectId, int parentTipId, List<PickNode> pickNodes) throws SQLDataException {
        for (PickNode pickNode :
                pickNodes) {
            if (heroLatinNames.containsKey(pickNode.Message)) {
                HeroDataDTO hero = heroLatinNames.get(pickNode.Message);
                // Pick => Tip => MapHeroPickTip w/ parent tip
                for (TipNode tipNode :
                        pickNode.TipNodes) {
                    MapHeroPickTip tip = new MapHeroPickTip(0, subjectId, tipNode.Precedence,
                            tipNode.Message, parentTipId, 0, hero.getHeroId());
                    int tipId = (int) writer.WriteMapHeroPickTip(tip);
                    if (tipId < 0) {
                        throw new SQLDataException(String.format("Failed to write DTO '%s'", tip));
                    }
                }
            }
            else {
                log.get().warn(String.format("Unwritten tip '%s'", pickNode));
            }
        }
    }

    @Override
    public void close() throws Exception {
        if (accessor != null)
            accessor.close();
        if (writer != null)
            writer.close();
    }
}
