package com.innovations.aguilar.pocketstrats.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.innovations.aguilar.pocketstrats.dto.SpawnSide;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ruben on 9/4/2017.
 */

// Persist state: Type, Map, Section, Side, Strategy=Category
// Clear state: Pick => next !Tip
// No state: Tip
// Location associations with tips
public class MapTipsNodeParser implements Closeable {
    BufferedReader reader;
    ParsedNode currentNode;
    public MapTipsNodeParser(Reader stream) {
        reader = new BufferedReader(stream);
    }

    static final Set<String> TokenWords = Sets.newHashSet(
            "tip",
            "pick",
            "section",
            "strategy",
            "side",
            "tags",
            "map",
            "type",
            "category"
    );

    static final String Seperator = "|";
    boolean nextNode() throws IOException {
        boolean foundTokens;
        String line;
        do {
            line = reader.readLine();
            if (line == null) {
                currentNode = null;
                return false;
            }
            else {
                String[] tokens = line.split(Seperator);
                if (tokens.length > 1) foundTokens = false;
                else {
                    String nodeType = tokens[0].trim().toLowerCase();
                    foundTokens = TokenWords.contains(nodeType);
                    if (foundTokens) {
                        List<String> nodeContents = Lists.newArrayList();
                        for (int i = 1; i < tokens.length; i++)
                            nodeContents.add(tokens[i].trim());
                        currentNode = new ParsedNode(nodeType, Collections.unmodifiableList(nodeContents));
                    }
                }
            }
        } while(!foundTokens);

        return true;
    }

    public ParsedNode getCurrentNode() {
        return currentNode;
    }

    public List<ParsedNode> allNodes() throws IOException {
        ArrayList<ParsedNode> allNodes = Lists.newArrayList();
        while (nextNode()) {
            allNodes.add(getCurrentNode());
        }
        return Collections.unmodifiableList(allNodes);
    }

    public void close() throws IOException {
        if (reader != null)
            reader.close();
    }
}

public class ParsedNode {
    String nodeType;
    List<String> nodeContents;

    public ParsedNode(String nodeType, List<String> nodeContents) {
        this.nodeType = nodeType;
        this.nodeContents = nodeContents;
    }

    public ParsedNode(String nodeType, String nodeContents) {
        this.nodeType = nodeType;
        this.nodeContents = Lists.newArrayList(nodeContents);
    }
}

public class MapLocationNodeParser {
    static final Pattern braces = Pattern.compile("\\[[^\\]]*\\]");
    public static List<ParsedNode> ParseLocations(ParsedNode node) {
        String contents = node.nodeContents.get(0).trim();
        Matcher matches = braces.matcher(contents);
        ArrayList<ParsedNode> allNodes = Lists.newArrayList();
        int segmentStartIndex = 0;
        while (matches.find()) {
            int matchStartIndex = matches.start();
            int matchEndIndex = matches.end();
            
            if (segmentStartIndex < matchStartIndex) {
                allNodes.add(new ParsedNode("segment", contents.substring(segmentStartIndex, matchStartIndex)));
                allNodes.add(new ParsedNode("location", contents.substring(matchStartIndex+1, matchEndIndex-1)));
                segmentStartIndex = matchEndIndex+1;
            } 
            else {
                allNodes.add(new ParsedNode("location", contents.substring(matchStartIndex+1, matchEndIndex-1)));
                segmentStartIndex = matchEndIndex+1;
            }
        }
        if (segmentStartIndex < contents.length()) {
            allNodes.add(new ParsedNode("segment", contents.substring(segmentStartIndex)));
        }
        return Collections.unmodifiableList(allNodes);
    }
}

public class MapTipsWriter {
    private Reader mapTipsReader;

    public MapTipsWriter(Reader mapTipsReader) {
        this.mapTipsReader = mapTipsReader;
    }

    public void WriteTips() {
        try (MapTipsNodeParser nodeParser = new MapTipsNodeParser(mapTipsReader)) {

        }
        catch (IOException iex) {

        }
    }

    class MapTip {
        String Tip;
    }

    class MapSection {
        SpawnSide side;
        String Subject; // Category or Strategy
        String Section;
    }

    class MapInfo {
        String Name;
        SpawnSide side;
        List<String> tags;

    }
}


