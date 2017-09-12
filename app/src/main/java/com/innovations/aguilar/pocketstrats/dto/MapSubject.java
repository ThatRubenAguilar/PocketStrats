package com.innovations.aguilar.pocketstrats.dto;

import android.database.Cursor;

public class MapSubject implements MapSubjectDTO {
    private final int subjectId;
    private final int mapId;
    private final SpawnSide spawnSide;
    private final String spawnSideDescription;
    private final String subjectDescription;
    private final Integer segmentId;

    public MapSubject(int subjectId, int mapId, SpawnSide spawnSide,
                      String spawnSideDescription, String subjectDescription, Integer segmentId) {
        this.subjectId = subjectId;
        this.mapId = mapId;
        this.spawnSide = spawnSide;
        this.spawnSideDescription = spawnSideDescription;
        this.subjectDescription = subjectDescription;
        this.segmentId = segmentId;
    }

    public MapSubject(Cursor c) {
        this.subjectId = c.getInt(c.getColumnIndex(SubjectIdColumn));
        this.mapId = c.getInt(c.getColumnIndex(MapIdColumn));
        this.subjectDescription = c.getString(c.getColumnIndex(SubjectDescriptionColumn));
        this.spawnSideDescription= c.getString(c.getColumnIndex(SpawnSideDescriptionColumn));
        int spawnSideIdRaw = c.getInt(c.getColumnIndex(SpawnSideIdColumn));
        this.spawnSide = SpawnSide.FromInt(spawnSideIdRaw);
        if (!c.isNull(c.getColumnIndex(SegmentIdColumn)))
            this.segmentId = c.getInt(c.getColumnIndex(SegmentIdColumn));
        else
            this.segmentId = null;
    }

    @Override
    public int getSubjectId() {
        return subjectId;
    }
    @Override
    public int getMapId() {
        return mapId;
    }
    @Override
    public Integer getSegmentId() {
        return segmentId;
    }
    @Override
    public SpawnSide getSpawnSideId() {
        return spawnSide;
    }
    @Override
    public String getSpawnSideDescription() {
        return spawnSideDescription;
    }
    @Override
    public String getSubjectDescription() {
        return subjectDescription;
    }


    public static final String SubjectIdColumn = "SubjectId";
    public static final String MapIdColumn = "MapId";
    public static final String SpawnSideIdColumn = "SpawnSideId";
    public static final String SpawnSideDescriptionColumn = "SpawnSideDescription";
    public static final String SubjectDescriptionColumn = "SubjectDescription";
    public static final String SegmentIdColumn = "SegmentId";

    public static final String TableName = "MapSubjects";

    public static final String[] ColumnNames = {
            String.format("%s.%s", TableName, SubjectIdColumn),
            String.format("%s.%s", TableName, MapIdColumn),
            String.format("%s.%s", TableName, SpawnSideIdColumn),
            String.format("%s.%s", TableName, SpawnSideDescriptionColumn),
            String.format("%s.%s", TableName, SubjectDescriptionColumn),
            String.format("%s.%s", TableName, SegmentIdColumn)
    };


}
