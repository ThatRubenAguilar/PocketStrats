package com.innovations.aguilar.pocketstrats.parser;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapLocationNodeParser {
    static final Pattern braces = Pattern.compile("\\[[^\\]]*\\]");
    public static List<TextNode> ParseLocations(String contents) {
        contents = contents.trim();
        Matcher matches = braces.matcher(contents);
        ArrayList<TextNode> allNodes = Lists.newArrayList();
        int segmentStartIndex = 0;
        while (matches.find()) {
            int matchStartIndex = matches.start();
            int matchEndIndex = matches.end();

            if (segmentStartIndex < matchStartIndex) {
                allNodes.add(new TextNode("segment", contents.substring(segmentStartIndex, matchStartIndex)));
                allNodes.add(new TextNode("location", contents.substring(matchStartIndex+1, matchEndIndex-1)));
                segmentStartIndex = matchEndIndex+1;
            }
            else {
                allNodes.add(new TextNode("location", contents.substring(matchStartIndex+1, matchEndIndex-1)));
                segmentStartIndex = matchEndIndex+1;
            }
        }
        if (segmentStartIndex < contents.length()) {
            allNodes.add(new TextNode("segment", contents.substring(segmentStartIndex)));
        }
        return Collections.unmodifiableList(allNodes);
    }
}
