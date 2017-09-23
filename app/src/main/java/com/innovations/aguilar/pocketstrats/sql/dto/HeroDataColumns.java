package com.innovations.aguilar.pocketstrats.sql.dto;

import com.google.common.collect.Lists;

public class HeroDataColumns extends DTOColumnInfo {

    public static final String HeroIdColumn = "HeroId";
    public static final String HeroNameColumn = "HeroName";
    public static final String HeroNameLatinColumn = "HeroNameLatin";
    public static final String HeroIconNameColumn = "HeroIconName";

    public HeroDataColumns() {
        super("Heros",
                Lists.newArrayList(HeroIdColumn, HeroNameColumn,
                        HeroNameLatinColumn, HeroIconNameColumn));
    }
}
