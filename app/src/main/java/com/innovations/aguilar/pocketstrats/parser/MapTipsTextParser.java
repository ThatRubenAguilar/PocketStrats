package com.innovations.aguilar.pocketstrats.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by Ruben on 9/4/2017.
 */

public class MapTipsTextParser implements Closeable {
    BufferedReader reader;
    TextNode currentNode;
    public MapTipsTextParser(Reader stream) {
        reader = new BufferedReader(stream);
    }

    static final Set<String> TokenWords = Sets.newHashSet(
            Tokens.Tip,
            Tokens.Pick,
            Tokens.Section,
            Tokens.Strategy,
            Tokens.Subject,
            Tokens.Side,
            Tokens.Tags,
            Tokens.Map,
            Tokens.Type,
            Tokens.Category
    );

    static final String Seperator = "\\|";
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
                if (tokens.length <= 1) foundTokens = false;
                else {
                    String nodeType = tokens[0].trim().toLowerCase();
                    foundTokens = TokenWords.contains(nodeType);
                    if (foundTokens) {
                        List<String> nodeContents = Lists.newArrayList();
                        for (int i = 1; i < tokens.length; i++)
                            nodeContents.add(tokens[i].trim());
                        currentNode = new TextNode(nodeType, Collections.unmodifiableList(nodeContents));
                    }
                }
            }
        } while(!foundTokens);

        return true;
    }

    public TextNode getCurrentNode() {
        return currentNode;
    }

    public List<TextNode> allNodes() throws IOException {
        ArrayList<TextNode> allNodes = Lists.newArrayList();
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


