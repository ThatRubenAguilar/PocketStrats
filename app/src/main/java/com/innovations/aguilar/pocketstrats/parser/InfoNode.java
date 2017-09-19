package com.innovations.aguilar.pocketstrats.parser;

import com.google.common.collect.Lists;
import com.innovations.aguilar.pocketstrats.sql.dto.MapType;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;

import java.util.List;
import java.util.Objects;

public class InfoNode {
    public List<MapType> MapTypes;
    public SpawnSide Side;
    public String MapName;

    public InfoNode() {
        MapTypes = Lists.newArrayList();
        Side = SpawnSide.Attack;
        MapName = null;
    }

    public InfoNode(List<MapType> mapTypes, SpawnSide side, String mapName) {
        MapTypes = Lists.newArrayList(mapTypes);
        Side = side;
        MapName = mapName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoNode infoNode = (InfoNode) o;
        return Objects.equals(MapTypes, infoNode.MapTypes) &&
                Side == infoNode.Side &&
                Objects.equals(MapName, infoNode.MapName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MapTypes, Side, MapName);
    }

    public InfoNode Copy() {
        return new InfoNode(MapTypes, Side, MapName);
    }
}
