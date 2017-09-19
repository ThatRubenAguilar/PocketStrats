package com.innovations.aguilar.pocketstrats.parser;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import com.innovations.aguilar.pocketstrats.logging.LoggerSupplier;
import com.innovations.aguilar.pocketstrats.sql.dto.HeroDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubject;
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
            MapSubject subj = new MapSubject(0, 0, subjNode.Precedence, subjNode.Message,
                    null, mapId, subjNode.INode.Side, subjNode.INode.Side.toString(), null);
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

            }
            // Category => Subject => Section -> MapTypeTip w/o parent tip
            else if (sectNode.INode.MapTypes != null && !sectNode.INode.MapTypes.isEmpty()) {

            }
        }
    }

    private void writeTip(int subjectId, SectionNode sectNode, int parentTipId) throws SQLDataException {
        // MapName => Strategy => Section => Tip -> MapSpecificTip w/ parent tip
        if (mapShortNames.containsKey(sectNode.INode.MapName)) {

        }
        // Category => Subject => Section => Tip -> MapTypeTip w/ parent tip
        else if (sectNode.INode.MapTypes != null && !sectNode.INode.MapTypes.isEmpty()) {

        }
    }

    private void writePicks(int subjectId) throws SQLDataException {
        // Pick => Tip => MapHeroPickTip w/o parent tip
    }

    @Override
    public void close() throws Exception {
        if (accessor != null)
            accessor.close();
        if (writer != null)
            writer.close();
    }
}
