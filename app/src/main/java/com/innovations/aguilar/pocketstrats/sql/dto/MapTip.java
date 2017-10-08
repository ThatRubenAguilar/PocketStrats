package com.innovations.aguilar.pocketstrats.sql.dto;

import android.database.Cursor;

import com.innovations.aguilar.pocketstrats.sql.query.DTOFromCursorFactory;


/**
 * Created by Ruben on 9/12/2017.
 */
public class MapTip extends MapTipDescription implements MapTipDTO {
    public static DTOFromCursorFactory<MapTipDTO> Factory =
            new DTOFromCursorFactory<MapTipDTO>() {
                @Override
                public MapTipDTO Create(Cursor c) { return new MapTip(c); }
            };

    private final int mapSubjectId;
    private final int mapTipId;
    private final int orderPrecedence;
    private final Integer parentMapTipId;

    public MapTip(int mapTipId, int mapSubjectId, int orderPrecedence,
                  Integer parentMapTipId,
                  int mapTipDescriptionId, int mapTipDescriptionHash, String mapTipDescription) {
        super(mapTipDescriptionId, mapTipDescriptionHash, mapTipDescription);
        this.mapSubjectId = mapSubjectId;
        this.mapTipId = mapTipId;
        this.orderPrecedence = orderPrecedence;
        this.parentMapTipId = parentMapTipId;
    }

    public MapTip(Cursor c) {
        super(c);
        this.mapSubjectId = c.getInt(c.getColumnIndex(Columns.MapSubjectIdColumn));
        this.mapTipId = c.getInt(c.getColumnIndex(Columns.MapTipIdColumn));
        this.orderPrecedence = c.getInt(c.getColumnIndex(Columns.OrderPrecedenceColumn));
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


    public static final MapTipColumns Columns = new MapTipColumns();

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MapTip{");
        sb.append("mapSubjectId=").append(mapSubjectId);
        sb.append(", mapTipId=").append(mapTipId);
        sb.append(", orderPrecedence=").append(orderPrecedence);
        sb.append(", parentMapTipId=").append(parentMapTipId);
        sb.append(", MapTipDescription=").append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
