package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

public class MapTip implements MapTipDTO {
    private final int subjectId;
    private final int tipId;
    private final String tipDescription;
    private final Integer parentTipId;

    public MapTip(int tipId, int subjectId,
                  String tipDescription, Integer parentTipId) {
        this.subjectId = subjectId;
        this.tipId = tipId;
        this.tipDescription = tipDescription;
        this.parentTipId = parentTipId;
    }

    public MapTip(Cursor c) {
        this.subjectId = c.getInt(c.getColumnIndex(SubjectIdColumn));
        this.tipId = c.getInt(c.getColumnIndex(TipIdColumn));
        this.tipDescription = c.getString(c.getColumnIndex(TipDescriptionColumn));
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
    public int getTipId() {
        return tipId;
    }
    @Override
    public Integer getParentTipId() {
        return parentTipId;
    }
    @Override
    public String getTipDescription() {
        return tipDescription;
    }


    public static final String SubjectIdColumn = "SubjectId";
    public static final String TipIdColumn = "TipId";
    public static final String TipDescriptionColumn = "TipDescription";
    public static final String ParentTipIdColumn = "ParentTipId";

    public static final String TableName = "MapTips";

    public static final String[] ColumnNames = {
            String.format("%s.%s", TableName, SubjectIdColumn),
            String.format("%s.%s", TableName, TipIdColumn),
            String.format("%s.%s", TableName, TipDescriptionColumn),
            String.format("%s.%s", TableName, ParentTipIdColumn)
    };


}
