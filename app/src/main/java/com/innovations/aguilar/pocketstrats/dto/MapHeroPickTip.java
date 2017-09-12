package com.innovations.aguilar.pocketstrats.dto;

import android.database.Cursor;

/**
 * Created by Ruben on 9/11/2017.
 */


public class MapHeroPickTip implements MapHeroPickTipDTO {
    private final int subjectId;
    private final int pickTipId;
    private final int heroId;
    private final String pickTipDescription;
    private final Integer parentTipId;

    public MapHeroPickTip(int pickTipId, int subjectId, int heroId,
                          String pickTipDescription, Integer parentTipId) {
        this.subjectId = subjectId;
        this.pickTipId = pickTipId;
        this.heroId = heroId;
        this.pickTipDescription = pickTipDescription;
        this.parentTipId = parentTipId;
    }

    public MapHeroPickTip(Cursor c) {
        this.subjectId = c.getInt(c.getColumnIndex(SubjectIdColumn));
        this.pickTipId = c.getInt(c.getColumnIndex(PickTipIdColumn));
        this.heroId = c.getInt(c.getColumnIndex(HeroIdColumn));
        this.pickTipDescription = c.getString(c.getColumnIndex(PickTipDescriptionColumn));
        if (!c.isNull(c.getColumnIndex(ParentTipIdColumn)))
            this.parentTipId = c.getInt(c.getColumnIndex(ParentTipIdColumn));
        else
            this.parentTipId = null;
    }

    @Override
    public int getSubjectId() {
        return subjectId;
    }
    @Override
    public int getPickTipId() {
        return pickTipId;
    }
    @Override
    public int getHeroId() {
        return heroId;
    }
    @Override
    public Integer getParentTipId() {
        return parentTipId;
    }
    @Override
    public String getPickTipDescription() {
        return pickTipDescription;
    }


    public static final String SubjectIdColumn = "SubjectId";
    public static final String PickTipIdColumn = "PickTipId";
    public static final String HeroIdColumn = "HeroId";
    public static final String PickTipDescriptionColumn = "PickTipDescription";
    public static final String ParentTipIdColumn = "ParentTipId";

    public static final String TableName = "MapTips";

    public static final String[] ColumnNames = {
            String.format("%s.%s", TableName, SubjectIdColumn),
            String.format("%s.%s", TableName, PickTipIdColumn),
            String.format("%s.%s", TableName, HeroIdColumn),
            String.format("%s.%s", TableName, PickTipDescriptionColumn),
            String.format("%s.%s", TableName, ParentTipIdColumn)
    };


}
