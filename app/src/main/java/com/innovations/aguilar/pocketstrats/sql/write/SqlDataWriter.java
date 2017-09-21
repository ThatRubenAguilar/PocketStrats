package com.innovations.aguilar.pocketstrats.sql.write;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.innovations.aguilar.pocketstrats.sql.dto.MapHeroPickTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapHeroPickTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpecificTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpecificTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubject;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTypeTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTypeTipDTO;

/**
 * Created by Ruben on 9/18/2017.
 */
public class SqlDataWriter implements AutoCloseable {
    final SQLiteDatabase writeableDb;

    public SqlDataWriter(SQLiteDatabase writeableDb) {
        this.writeableDb = writeableDb;
    }

    public int WriteMapSubject(MapSubjectDTO mapSubject) {
        writeableDb.beginTransaction();
        try {
            ContentValues mapSubjectContent = new ContentValues();
            mapSubjectContent.put(MapSubject.MapIdColumn, mapSubject.getMapId());
            mapSubjectContent.put(MapSubject.MapSubjectPrecedenceColumn, mapSubject.getMapSubjectPrecedence());
            mapSubjectContent.put(MapSubject.MapSubjectDescriptionColumn, mapSubject.getMapSubjectDescription());
            mapSubjectContent.put(MapSubject.SegmentIdColumn, mapSubject.getSegmentId());
            mapSubjectContent.put(MapSubject.SpawnSideDescriptionColumn, mapSubject.getSpawnSideId().toString());
            mapSubjectContent.put(MapSubject.SpawnSideIdColumn, mapSubject.getSpawnSideId().spawnSideId);
            int mapSubjectId = (int)writeableDb.insert(MapSubject.TableName, null, mapSubjectContent);
            if (mapSubjectId < 0)
                return -1;
            writeableDb.setTransactionSuccessful();
            return mapSubjectId;
        }
        finally {
            writeableDb.endTransaction();
        }
    }

    public int WriteMapSpecificTip(MapSpecificTipDTO mapSpecificTip) {
        writeableDb.beginTransaction();
        try {
            int mapTipId = mapSpecificTip.getMapTipId();
            if (mapTipId <= 0) {
                mapTipId = WriteMapTip(mapSpecificTip);
                if (mapTipId < 0)
                    return -1;
            }

            ContentValues mapSpecificTipContent = new ContentValues();
            mapSpecificTipContent.put(MapSpecificTip.MapTipIdColumn, mapTipId);
            int mapSpecificTipId = (int)writeableDb.insert(MapSpecificTip.TableName, null, mapSpecificTipContent);
            if (mapSpecificTipId < 0)
                return -1;
            writeableDb.setTransactionSuccessful();
            return mapSpecificTipId;
        }
        finally {
            writeableDb.endTransaction();
        }
    }
    public int WriteMapTypeTip(MapTypeTipDTO mapTypeTip) {
        writeableDb.beginTransaction();
        try {
            int mapTipId = mapTypeTip.getMapTipId();
            if (mapTipId <= 0) {
                mapTipId = WriteMapTip(mapTypeTip);
                if (mapTipId < 0)
                    return -1;
            }

            ContentValues mapTypeTipContent = new ContentValues();
            mapTypeTipContent.put(MapTypeTip.MapTipIdColumn, mapTipId);
            mapTypeTipContent.put(MapTypeTip.MapTypeIdColumn, mapTypeTip.getMapTypeId().typeId);
            int mapTypeTipId = (int)writeableDb.insert(MapTypeTip.TableName, null, mapTypeTipContent);
            if (mapTypeTipId < 0)
                return -1;
            writeableDb.setTransactionSuccessful();
            return mapTypeTipId;
        }
        finally {
            writeableDb.endTransaction();
        }
    }
    public int WriteMapHeroPickTip(MapHeroPickTipDTO mapHeroPickTip) {
        writeableDb.beginTransaction();
        try {

            int mapTipId = mapHeroPickTip.getMapTipId();
            if (mapTipId <= 0) {
                mapTipId = WriteMapTip(mapHeroPickTip);
                if (mapTipId < 0)
                    return -1;
            }

            ContentValues mapTypeTipContent = new ContentValues();
            mapTypeTipContent.put(MapHeroPickTip.MapTipIdColumn, mapTipId);
            mapTypeTipContent.put(MapHeroPickTip.HeroIdColumn, mapHeroPickTip.getHeroId());
            int mapHeroPickTipId = (int)writeableDb.insert(MapHeroPickTip.TableName, null, mapTypeTipContent);
            if (mapHeroPickTipId < 0)
                return -1;
            writeableDb.setTransactionSuccessful();
            return mapHeroPickTipId;
        }
        finally {
            writeableDb.endTransaction();
        }
    }

    public int WriteMapTip(MapTipDTO mapTip) {
        ContentValues mapTipContent = new ContentValues();
        if (mapTip.getMapSubjectId() <= 0)
            mapTipContent.putNull(MapTip.MapSubjectIdColumn);
        else
            mapTipContent.put(MapTip.MapSubjectIdColumn, mapTip.getMapSubjectId());

        mapTipContent.put(MapTip.MapTipDescriptionColumn, mapTip.getMapTipDescription());
        mapTipContent.put(MapTip.ParentMapTipIdColumn, mapTip.getParentMapTipId());
        mapTipContent.put(MapTip.OrderPrecedenceColumn, mapTip.getOrderPrecedence());
        return (int)writeableDb.insert(MapTip.TableName, null, mapTipContent);
    }

    @Override
    public void close() throws Exception {
        if (writeableDb != null) writeableDb.close();
    }
}
