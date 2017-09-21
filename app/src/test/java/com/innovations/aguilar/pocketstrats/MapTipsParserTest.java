package com.innovations.aguilar.pocketstrats;

import com.google.common.collect.Lists;
import com.innovations.aguilar.pocketstrats.parser.InfoNode;
import com.innovations.aguilar.pocketstrats.parser.MapTipsTextParser;
import com.innovations.aguilar.pocketstrats.parser.TextNode;
import com.innovations.aguilar.pocketstrats.parser.PickNode;
import com.innovations.aguilar.pocketstrats.parser.SectionNode;
import com.innovations.aguilar.pocketstrats.parser.SubjectNode;
import com.innovations.aguilar.pocketstrats.parser.TipNode;
import com.innovations.aguilar.pocketstrats.parser.TipsDocument;
import com.innovations.aguilar.pocketstrats.parser.Tokens;
import com.innovations.aguilar.pocketstrats.sql.dto.MapType;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MapTipsParserTest {
    @Test
    public void NodeParser_Should_Parse_Nodes() throws Exception {
        String text = "Category|Assault|Hybrid_Assault_Escort\n" +
                "Side|Attack|Defend\n" +
                "Subject|Subj1\n" +
                " Tip|TryThis \n" +
                "";
        List<TextNode> expected = Lists.newArrayList(
                new TextNode(Tokens.Category.toLowerCase(), Lists.newArrayList("Assault", "Hybrid_Assault_Escort")),
                new TextNode(Tokens.Side.toLowerCase(), Lists.newArrayList("Attack", "Defend")),
                new TextNode(Tokens.Subject.toLowerCase(), Lists.newArrayList("Subj1")),
                new TextNode(Tokens.Tip.toLowerCase(), Lists.newArrayList("TryThis"))
        );
        Reader textReader = new InputStreamReader(new ByteArrayInputStream(text.getBytes()));
        MapTipsTextParser parser = new MapTipsTextParser(textReader);
        List<TextNode> allNodes = parser.allNodes();
        assertEquals(expected.size(), allNodes.size());
        for (int i = 0; i < expected.size(); i++) {
            TextNode expectedNode = expected.get(i);
            TextNode textNode = allNodes.get(i);
            assertEquals(expectedNode.toString(), textNode.toString());
        }
    }

    @Test
    public void TipsDocument_Should_Parse_Document() throws Exception {
        String text = "Category|Assault|Hybrid_Assault_Escort\n" +
                "Side|Attack|Defend\n" +
                "Subject|Subj1\n" +
                "Section|Sect1\n" +
                " Tip|TryThis \n" +
                " Tip|TryThis2 \n" +
                " Section|Sect2 \n" +
                " Tip|TryThis3 \n" +
                "";
        InfoNode iNode = new InfoNode();
        iNode.MapTypes = Lists.newArrayList(MapType.Assault, MapType.Hybrid_Assault_Escort);
        List<TipNode> tips = Lists.newArrayList(
                new TipNode("TryThis", 1, iNode),
                new TipNode("TryThis2", 2, iNode)
        );
        List<TipNode> tips2 = Lists.newArrayList(
                new TipNode("TryThis3", 1, iNode)
        );
        List<SectionNode> sections = Lists.newArrayList(
                new SectionNode("Sect1", 1, iNode, tips , Lists.<PickNode>newArrayList()),
                new SectionNode("Sect2", 2, iNode, tips2 , Lists.<PickNode>newArrayList())
        );
        SubjectNode subjNode = new SubjectNode("Subj1", 1, iNode, sections);
        List<SubjectNode> expected = Lists.newArrayList(subjNode);

        Reader textReader = new InputStreamReader(new ByteArrayInputStream(text.getBytes()));
        MapTipsTextParser parser = new MapTipsTextParser(textReader);
        List<TextNode> allNodes = parser.allNodes();
        TipsDocument tDoc = new TipsDocument(allNodes);
        assertEquals(expected, tDoc.getSubjects());
    }
}