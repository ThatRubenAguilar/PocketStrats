package com.innovations.aguilar.pocketstrats.sql.dto;

import java.util.Objects;

public class MapSubjectAssociation implements MapSubjectAssociationDTO {

    private final int mapSubjectId;
    private final int associatedMapSubjectId;

    public MapSubjectAssociation(int mapSubjectId, int associatedMapSubjectId) {
        this.mapSubjectId = mapSubjectId;
        this.associatedMapSubjectId = associatedMapSubjectId;
    }

    public static final MapSubjectAssocationColumns Columns = new MapSubjectAssocationColumns();

    @Override
    public int getMapSubjectId() {
        return mapSubjectId;
    }

    @Override
    public int getAssociatedMapSubjectId() {
        return associatedMapSubjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapSubjectAssociation that = (MapSubjectAssociation) o;
        return mapSubjectId == that.mapSubjectId &&
                associatedMapSubjectId == that.associatedMapSubjectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mapSubjectId, associatedMapSubjectId);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MapSubjectAssociation{");
        sb.append("mapSubjectId=").append(mapSubjectId);
        sb.append(", associatedMapSubjectId=").append(associatedMapSubjectId);
        sb.append('}');
        return sb.toString();
    }
}

