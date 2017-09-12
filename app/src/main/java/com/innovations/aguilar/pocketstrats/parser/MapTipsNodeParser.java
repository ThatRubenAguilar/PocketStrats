package com.innovations.aguilar.pocketstrats.parser;

import android.database.sqlite.SQLiteDatabase;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.innovations.aguilar.pocketstrats.logging.LoggerSupplier;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;

import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ruben on 9/4/2017.
 */

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
            "subject",
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

    @Override
    public String toString() {
        return String.format("{'%s':'%s'}", nodeType, nodeContents);
    }
}

public class MapLocationNodeParser {
    static final Pattern braces = Pattern.compile("\\[[^\\]]*\\]");
    public static List<ParsedNode> ParseLocations(String contents) {
        contents = contents.trim();
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
    protected Supplier<Logger> log = Suppliers.memoize(new LoggerSupplier(this.getClass()));

    private Reader mapTipsReader;

    ParsedNode mapNode;
    ParsedNode sectionNode;
    ParsedNode typeNode;
    ParsedNode categoryNode;
    ParsedNode tipNode;
    ParsedNode pickNode;
    ParsedNode sideNode;
    ParsedNode strategyNode;
    ParsedNode subjectNode;
    ParsedNode tagNode;

    Stack<Integer> precedenceStack = new Stack<>();

    public MapTipsWriter(Reader mapTipsReader) {
        this.mapTipsReader = mapTipsReader;
    }

    // Persist state: Type, Map, Section, Side, Category, Strategy=Subject
    // Clear state: Pick => next !Tip
    // No state: Tip
    // Location associations with tips
    public void WriteTips(SQLiteDatabase sqlDb) {
        try (MapTipsNodeParser nodeParser = new MapTipsNodeParser(mapTipsReader)) {

            // Pick => Tip => MapHeroPickTip w/o parent tip
            // Strategy -> MapSubject
            // Strategy => Tip -> MapSpecificTip w/ parent tip
            // Strategy => Section -> MapSpecificTip w/o parent tip
            // Category => Subject -> MapTypeTip

            // Strategy/Subject => Section/Pick => Tip -> Precedence Stack Push
            // Tip => Section/Pick => Strategy/Subject -> Precedence Stack Pop
            while (nodeParser.nextNode()) {
                ParsedNode currNode = nodeParser.getCurrentNode();
                switch(currNode.nodeType) {
                    case "tip":
                        break;
                    case "pick":
                        break;
                    case "map":
                        break;
                    case "tip":
                        break;
                    case "tip":
                        break;
                    case "tip":
                        break;
                    case "tip":
                        break;
                    case "tip":
                        break;
                    case "tip":
                        break;
                    case "tip":
                        break;
                    case "tip":
                        break;
                    case "tip":
                        break;
                    case "tip":
                        break;
                    default:
                        log.get().warn("Node type not found for Node %s", currNode);
                        break;
                }
            }
        }
        catch (IOException iex) {

        }
    }
}


