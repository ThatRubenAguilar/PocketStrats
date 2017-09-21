package com.innovations.aguilar.pocketstrats.parser;

import com.google.common.collect.Lists;
import com.innovations.aguilar.pocketstrats.sql.dto.MapType;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;

import java.util.List;
import java.util.Objects;

public class InfoNode {
    public List<MapType> MapTypes;
    public List<SpawnSide> Sides;
    public String MapName;
    public String SegmentName;

    public InfoNode() {
        MapTypes = Lists.newArrayList();
        Sides = Lists.newArrayList();
        MapName = null;
        SegmentName = null;
    }

    public InfoNode(List<MapType> mapTypes, List<SpawnSide> sides, String mapName, String segmentName) {
        MapTypes = Lists.newArrayList(mapTypes);
        Sides = Lists.newArrayList(sides);
        MapName = mapName;
        SegmentName = segmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoNode infoNode = (InfoNode) o;
        return Objects.equals(MapTypes, infoNode.MapTypes) &&
                Objects.equals(Sides, infoNode.Sides) &&
                Objects.equals(MapName, infoNode.MapName) &&
                Objects.equals(SegmentName, infoNode.SegmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MapTypes, Sides, MapName, SegmentName);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("InfoNode{");
        sb.append("MapTypes=").append(MapTypes);
        sb.append(", Sides=").append(Sides);
        sb.append(", MapName='").append(MapName).append('\'');
        sb.append(", SegmentName='").append(SegmentName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public InfoNode Copy() {
        return new InfoNode(MapTypes, Sides, MapName, SegmentName);
    }
}
