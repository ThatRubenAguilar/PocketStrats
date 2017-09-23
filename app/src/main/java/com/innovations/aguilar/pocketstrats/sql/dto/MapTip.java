package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

import com.google.common.collect.Lists;
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
        this.mapSubjectId = c.getInt(c.getColumnIndex(Columns.MapSubjectIdColumn));
        this.mapTipId = c.getInt(c.getColumnIndex(Columns.MapTipIdColumn));
        this.orderPrecedence = c.getInt(c.getColumnIndex(Columns.OrderPrecedenceColumn));;
        this.mapTipDescription = c.getString(c.getColumnIndex(Columns.MapTipDescriptionColumn));
        if (!c.isNull(c.getColumnIndex(Columns.ParentMapTipIdColumn)))
            this.parentMapTipId = c.getInt(c.getColumnIndex(Columns.ParentMapTipIdColumn));
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


    public static final MapTipColumns Columns = new MapTipColumns();

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
