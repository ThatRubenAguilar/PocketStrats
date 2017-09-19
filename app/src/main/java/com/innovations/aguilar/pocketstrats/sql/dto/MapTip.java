package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

import com.innovations.aguilar.pocketstrats.sql.query.DTOFromCursorFactory;

/**
 * Created by Ruben on 9/12/2017.
 */
public class MapTip implements MapTipDTO {
    public static DTOFromCursorFactory<MapTipDTO> Factory =
            new DTOFromCursorFactory<MapTipDTO>() {
                @Override
                public MapTipDTO Create(Cursor c) { return new MapTip(c); }
            };

    private final int mapSubjectId;
    private final int mapTipId;
    private final int orderPrecedence;
    private final String mapTipDescription;
    private final Integer parentMapTipId;

    public MapTip(int mapTipId, int mapSubjectId, int orderPrecedence,
                  String mapTipDescription, Integer parentMapTipId) {
        this.mapSubjectId = mapSubjectId;
        this.mapTipId = mapTipId;
        this.orderPrecedence = orderPrecedence;
        this.mapTipDescription = mapTipDescription;
        this.parentMapTipId = parentMapTipId;
    }

    public MapTip(Cursor c) {
        this.mapSubjectId = c.getInt(c.getColumnIndex(MapSubjectIdColumn));
        this.mapTipId = c.getInt(c.getColumnIndex(MapTipIdColumn));
        this.orderPrecedence = c.getInt(c.getColumnIndex(OrderPrecedenceColumn));;
        this.mapTipDescription = c.getString(c.getColumnIndex(MapTipDescriptionColumn));
        if (!c.isNull(c.getColumnIndex(ParentMapTipIdColumn)))
            this.parentMapTipId = c.getInt(c.getColumnIndex(ParentMapTipIdColumn));
        else
            this.parentMapTipId = null;
    }

    @Override
    public int getMapSubjectId() {
        return mapSubjectId;
    }
    @Override
    public int getMapTipId() {
        return mapTipId;
    }
    @Override
    public int getOrderPrecedence() {
        return orderPrecedence;
    }
    @Override
    public Integer getParentMapTipId() {
        return parentMapTipId;
    }
    @Override
    public String getMapTipDescription() {
        return mapTipDescription;
    }


    public static final String MapSubjectIdColumn = "MapSubjectId";
    public static final String MapTipIdColumn = "MapTipId";
    public static final String OrderPrecedenceColumn = "OrderPrecedence";
    public static final String MapTipDescriptionColumn = "MapTipDescription";
    public static final String ParentMapTipIdColumn = "ParentMapTipId";

    public static final String TableName = "MapTips";

    public static final String[] ColumnNames = {
            String.format("%s.%s", TableName, MapSubjectIdColumn),
            String.format("%s.%s", TableName, MapTipIdColumn),
            String.format("%s.%s", TableName, OrderPrecedenceColumn),
            String.format("%s.%s", TableName, MapTipDescriptionColumn),
            String.format("%s.%s", TableName, ParentMapTipIdColumn)
    };

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MapTip{");
        sb.append("mapSubjectId=").append(mapSubjectId);
        sb.append(", mapTipId=").append(mapTipId);
        sb.append(", orderPrecedence=").append(orderPrecedence);
        sb.append(", mapTipDescription='").append(mapTipDescription).append('\'');
        sb.append(", parentMapTipId=").append(parentMapTipId);
        sb.append('}');
        return sb.toString();
    }
}
