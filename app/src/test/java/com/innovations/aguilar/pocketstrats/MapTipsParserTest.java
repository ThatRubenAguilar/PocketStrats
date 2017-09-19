package com.innovations.aguilar.pocketstrats;

import com.google.common.collect.Lists;
import com.innovations.aguilar.pocketstrats.parser.InfoNode;
import com.innovations.aguilar.pocketstrats.parser.MapTipsNodeParser;
import com.innovations.aguilar.pocketstrats.parser.ParsedNode;
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
                "Subject|Subj1\n" +
                " Tip|TryThis \n" +
                "";
        List<ParsedNode> expected = Lists.newArrayList(
                new ParsedNode(Tokens.Category.toLowerCase(), Lists.newArrayList("Assault", "Hybrid_Assault_Escort")),
                new ParsedNode(Tokens.Subject.toLowerCase(), Lists.newArrayList("Subj1")),
                new ParsedNode(Tokens.Tip.toLowerCase(), Lists.newArrayList("TryThis"))
        );
        Reader textReader = new InputStreamReader(new ByteArrayInputStream(text.getBytes()));
        MapTipsNodeParser parser = new MapTipsNodeParser(textReader);
        List<ParsedNode> allNodes = parser.allNodes();
        assertEquals(expected.size(), allNodes.size());
        for (int i = 0; i < expected.size(); i++) {
            ParsedNode expectedNode = expected.get(i);
            ParsedNode parsedNode = allNodes.get(i);
            assertEquals(expectedNode.toString(), parsedNode.toString());
        }
    }

    @Test
    public void TipsDocument_Should_Parse_Document() throws Exception {
        String text = "Category|Assault|Hybrid_Assault_Escort\n" +
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
        MapTipsNodeParser parser = new MapTipsNodeParser(textReader);
        List<ParsedNode> allNodes = parser.allNodes();
        TipsDocument tDoc = new TipsDocument(allNodes);
        assertEquals(expected, tDoc.getSubjects());
    }
}