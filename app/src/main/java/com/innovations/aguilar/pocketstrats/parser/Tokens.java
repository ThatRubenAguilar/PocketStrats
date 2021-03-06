package com.innovations.aguilar.pocketstrats.parser;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by Ruben on 9/13/2017.
 */
public class Tokens {
    public static final String Tip = "tip";
    public static final String Pick = "pick";
    public static final String Section = "section";
    public static final String Strategy = "strategy";
    public static final String Subject = "subject";
    public static final String MapSegment = "segment";
    public static final String Side = "side";
    public static final String Tags = "tags";
    public static final String Map = "map";
    public static final String Type = "type";
    public static final String Category = "category";

    public static final Set<String> AllTokens = Sets.newHashSet(
            Tip, Pick, Section,
            Strategy, Subject, Side,
            Tags, Map, Type,
            Category, MapSegment
    );
}
