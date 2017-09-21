package com.innovations.aguilar.pocketstrats;

import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.base.Strings;
import android.support.test.espresso.core.deps.guava.collect.Lists;
import android.support.test.runner.AndroidJUnit4;

import com.google.common.base.Charsets;
import com.innovations.aguilar.pocketstrats.parser.InfoNode;
import com.innovations.aguilar.pocketstrats.parser.MapTipsTextParser;
import com.innovations.aguilar.pocketstrats.parser.MapTipsWriter;
import com.innovations.aguilar.pocketstrats.parser.PickNode;
import com.innovations.aguilar.pocketstrats.parser.SectionNode;
import com.innovations.aguilar.pocketstrats.parser.SubjectNode;
import com.innovations.aguilar.pocketstrats.parser.TipNode;
import com.innovations.aguilar.pocketstrats.parser.TipsDocument;
import com.innovations.aguilar.pocketstrats.sql.dto.MapType;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;
import com.innovations.aguilar.pocketstrats.sql.query.SqlDataAccessor;
import com.innovations.aguilar.pocketstrats.sql.write.SqlDataWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MapDatabaseTipWriterTests extends MapDatabaseTestFixture {

    SqlDataAccessor accessor = null;
    SqlDataWriter writer = null;
    @Before
    public void beforeTest() {
        accessor = new SqlDataAccessor(readableMapDb);
        writer = new SqlDataWriter(writeableMapDb);
    }

    @After
    public void afterTest() {
        accessor = null;
        writer = null;
    }


    @Test
    public void Tips_Documents_Should_Parse_Category_Subjects() throws Exception {

        List<MapType> AllTypes = Lists.newArrayList(MapType.Assault, MapType.Control, MapType.Escort, MapType.Hybrid_Assault_Escort);
        List<MapType> AssaultType = Lists.newArrayList(MapType.Assault);
        List<SpawnSide> AllSides = Lists.newArrayList(SpawnSide.Attack,SpawnSide.Defend);
        List<SpawnSide> AttackSide = Lists.newArrayList(SpawnSide.Attack);


        List<SubjectNode> expected = Lists.newArrayList(
                generateCategorySubjectNode("AllTypesAllSides", 1, AllTypes, AllSides),
                generateCategorySubjectNode("AllTypes", 2, AllTypes, AttackSide),
                generateCategorySubjectNode("AssaultTypeAllSides", 3, AssaultType, AllSides),
                generateCategorySubjectNode("AssaultType", 4, AssaultType, AttackSide)
        );

        String scriptAssetPath = "test_docs/ParseableTips1.txt";
        AssetManager assets = InstrumentationRegistry.getContext().getAssets();

        try (InputStream scriptTextStream = assets.open(scriptAssetPath)) {
            MapTipsTextParser parser = new MapTipsTextParser(new InputStreamReader(scriptTextStream, Charsets.UTF_8));
            TipsDocument tDoc = new TipsDocument(parser.allNodes());
            List<SubjectNode> subjectNodes = tDoc.getSubjects();

            assertEquals(expected, subjectNodes);
        }
    }
    @Test
    public void Tips_Documents_Should_Parse_Strategy_Subjects() throws Exception {

        String mapName = "Anubis";
        String segmentName1 = "First";
        String segmentName2 = "Last";
        List<SpawnSide> DefendSide = Lists.newArrayList(SpawnSide.Defend);
        List<SpawnSide> AttackSide = Lists.newArrayList(SpawnSide.Attack);


        List<SubjectNode> expected = Lists.newArrayList(
                generateStrategySubjectNode("AnubisAttack1", 1, mapName, segmentName1, AttackSide ),
                generateStrategySubjectNode("AnubisAttack2", 2, mapName, segmentName1, AttackSide),
                generateStrategySubjectNode("AnubisDefend1", 3, mapName, segmentName2, DefendSide),
                generateStrategySubjectNode("AnubisDefend2", 4, mapName, segmentName2, DefendSide)
        );

        String scriptAssetPath = "test_docs/ParseableTips2.txt";
        AssetManager assets = InstrumentationRegistry.getContext().getAssets();

        try (InputStream scriptTextStream = assets.open(scriptAssetPath)) {
            MapTipsTextParser parser = new MapTipsTextParser(new InputStreamReader(scriptTextStream, Charsets.UTF_8));
            TipsDocument tDoc = new TipsDocument(parser.allNodes());
            List<SubjectNode> subjectNodes = tDoc.getSubjects();

            assertEquals(expected, subjectNodes);
        }
    }

    SubjectNode generateCategorySubjectNode(String subjectPrefix, int subjectPrecedence, List<MapType> mapTypes, List<SpawnSide> sides) {
        InfoNode info = new InfoNode();
        info.MapTypes = mapTypes;
        info.Sides = sides;

        List<TipNode> tips1 = Lists.newArrayList(
          new TipNode(String.format("%s Tip1", subjectPrefix), 1, info),
          new TipNode(String.format("%s Tip2", subjectPrefix), 2, info)
        );
        List<TipNode> tips2 = Lists.newArrayList(
          new TipNode(String.format("%s Tip3", subjectPrefix), 1, info)
        );

        List<SectionNode> sections =
                Lists.newArrayList(
                        new SectionNode(String.format("%s Sect1", subjectPrefix), 1, info, tips1, Lists.<PickNode>newArrayList()),
                        new SectionNode(String.format("%s Sect2", subjectPrefix), 2, info, tips2, Lists.<PickNode>newArrayList())
                        );
        SubjectNode subj = new SubjectNode(subjectPrefix,  subjectPrecedence, info, sections);
        return subj;
    }
    SubjectNode generateStrategySubjectNode(String subjectPrefix, int subjectPrecedence, String mapName, String segmentName, List<SpawnSide> sides) {
        InfoNode info = new InfoNode();
        info.MapName = mapName;
        info.SegmentName = segmentName;
        info.Sides = sides;

        List<TipNode> tips1 = Lists.newArrayList(
          new TipNode(String.format("%s Tip1", subjectPrefix), 1, info)
        );
        List<TipNode> pickTips1 = Lists.newArrayList(
          new TipNode(String.format("%s Pick1", subjectPrefix), 1, info)
        );
        List<PickNode> picks1 = Lists.newArrayList(
            new PickNode("Lucio",1, info, pickTips1)
        );

        List<SectionNode> sections =
                Lists.newArrayList(
                        new SectionNode(String.format("%s Sect1", subjectPrefix), 1, info, tips1, Lists.<PickNode>newArrayList()),
                        new SectionNode(String.format("%s Picks", subjectPrefix), 2, info, Lists.<TipNode>newArrayList(), picks1)
                        );
        SubjectNode subj = new SubjectNode(subjectPrefix,  subjectPrecedence, info, sections);
        return subj;
    }
    @Test @Ignore
    public void Writer_Should_Write_Category_Subjects() throws Exception {
        MapTipsWriter tipsWriter = new MapTipsWriter(writer,accessor);

        String scriptAssetPath = "test_docs/ParseableTips1.txt";
        AssetManager assets = InstrumentationRegistry.getContext().getAssets();

        try (InputStream scriptTextStream = assets.open(scriptAssetPath)) {
            tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8));
        }

    }
    @Test @Ignore
    public void Writer_Should_Write_Strategy_Subjects() throws Exception {
        MapTipsWriter tipsWriter = new MapTipsWriter(writer,accessor);

        String scriptAssetPath = "test_docs/ParseableTips2.txt";
        AssetManager assets = InstrumentationRegistry.getContext().getAssets();

        try (InputStream scriptTextStream = assets.open(scriptAssetPath)) {
            tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8));
        }

    }
    @Test @Ignore
    public void Writer_Should_Write_Real_Tips() throws Exception {
        MapTipsWriter tipsWriter = new MapTipsWriter(writer,accessor);

        AssetManager assets = InstrumentationRegistry.getTargetContext().getAssets();

        try (InputStream scriptTextStream = assets.open("tips_docs/map_type_tips.txt")) {
            tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8));
        }
        try (InputStream scriptTextStream = assets.open("tips_docs/assault_maps_tips.txt")) {
            tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8));
        }
        try (InputStream scriptTextStream = assets.open("tips_docs/control_maps_tips.txt")) {
            tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8));
        }
        try (InputStream scriptTextStream = assets.open("tips_docs/escort_maps_tips.txt")) {
            tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8));
        }
        try (InputStream scriptTextStream = assets.open("tips_docs/hybrid_assault_escort_maps_tips.txt")) {
            tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8));
        }

    }
}
