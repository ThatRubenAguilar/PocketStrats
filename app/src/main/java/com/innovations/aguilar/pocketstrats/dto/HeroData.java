package com.innovations.aguilar.pocketstrats.dto;

import android.database.Cursor;

/**
 * Created by Ruben on 9/11/2017.
 */
public class HeroData implements HeroDataDTO {
    private final int heroId;
    private final String heroName;
    private final String heroIconName;

    public HeroData(int heroId, String heroName, String heroIconName) {
        this.heroId = heroId;
        this.heroName = heroName;
        this.heroIconName = heroIconName;
    }

    public HeroData(Cursor c) {
        this.heroId = c.getInt(c.getColumnIndex(HeroIdColumn));
        this.heroName = c.getString(c.getColumnIndex(HeroNameColumn));
        this.heroIconName = c.getString(c.getColumnIndex(HeroIconNameColumn));
    }


    @Override
    public int getHeroId() {
        return heroId;
    }

    @Override
    public String getHeroName() {
        return heroName;
    }

    @Override
    public String getHeroIconName() {
        return heroIconName;
    }

    public static final String HeroIdColumn = "HeroId";
    public static final String HeroNameColumn = "HeroName";
    public static final String HeroIconNameColumn = "HeroIconName";

    public static final String TableName = "Heros";

    public static final String[] ColumnNames = {
            String.format("%s.%s", TableName, HeroIdColumn),
            String.format("%s.%s", TableName, HeroNameColumn),
            String.format("%s.%s", TableName, HeroIconNameColumn)
    };


}
