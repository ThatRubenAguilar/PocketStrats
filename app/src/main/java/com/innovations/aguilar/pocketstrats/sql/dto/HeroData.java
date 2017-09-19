package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

import com.innovations.aguilar.pocketstrats.sql.query.DTOFromCursorFactory;

/**
 * Created by Ruben on 9/11/2017.
 */
public class HeroData implements HeroDataDTO {
    public static DTOFromCursorFactory<HeroDataDTO> Factory =
            new DTOFromCursorFactory<HeroDataDTO>() {
        @Override
        public HeroDataDTO Create(Cursor c) { return new HeroData(c); }
    };

    private final int heroId;
    private final String heroName;
    private final String heroNameLatin;
    private final String heroIconName;

    public HeroData(int heroId, String heroName, String heroNameLatin, String heroIconName) {
        this.heroId = heroId;
        this.heroName = heroName;
        this.heroNameLatin = heroNameLatin;
        this.heroIconName = heroIconName;
    }

    public HeroData(Cursor c) {
        this.heroId = c.getInt(c.getColumnIndex(HeroIdColumn));
        this.heroName = c.getString(c.getColumnIndex(HeroNameColumn));
        this.heroNameLatin = c.getString(c.getColumnIndex(HeroNameLatinColumn));
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
    public String getHeroNameLatin() {
        return heroNameLatin;
    }

    @Override
    public String getHeroIconName() {
        return heroIconName;
    }

    public static final String HeroIdColumn = "HeroId";
    public static final String HeroNameColumn = "HeroName";
    public static final String HeroNameLatinColumn = "HeroNameLatin";
    public static final String HeroIconNameColumn = "HeroIconName";

    public static final String TableName = "Heros";

    public static final String[] ColumnNames = {
            String.format("%s.%s", TableName, HeroIdColumn),
            String.format("%s.%s", TableName, HeroNameColumn),
            String.format("%s.%s", TableName, HeroNameLatinColumn),
            String.format("%s.%s", TableName, HeroIconNameColumn)
    };

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HeroData{");
        sb.append("heroId=").append(heroId);
        sb.append(", heroName='").append(heroName).append('\'');
        sb.append(", heroNameLatin='").append(heroNameLatin).append('\'');
        sb.append(", heroIconName='").append(heroIconName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
