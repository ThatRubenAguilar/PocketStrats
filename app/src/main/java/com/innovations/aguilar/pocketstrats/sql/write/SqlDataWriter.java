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

    public long WriteMapSubject(MapSubjectDTO mapSubject) {
        writeableDb.beginTransaction();
        try {
            long mapTipId = WriteMapTip(mapSubject);
            if (mapTipId < 0)
                return -1;

            ContentValues mapSubjectContent = new ContentValues();
            mapSubjectContent.put(MapSubject.MapIdColumn, mapSubject.getMapId());
            mapSubjectContent.put(MapSubject.MapTipIdColumn, mapTipId);
            mapSubjectContent.put(MapSubject.SegmentIdColumn, mapSubject.getSegmentId());
            mapSubjectContent.put(MapSubject.SpawnSideDescriptionColumn, mapSubject.getSpawnSideId().toString());
            mapSubjectContent.put(MapSubject.SpawnSideIdColumn, mapSubject.getSpawnSideId().spawnSideId);
            long mapSubjectId = writeableDb.insert(MapSubject.TableName, null, mapSubjectContent);
            if (mapSubjectId < 0)
                return -1;
            // TODO: Do we need backlink from MapTips to MapSubjects?
//            ContentValues mapTipUpdate = new ContentValues();
//            mapTipContent.put(MapTip.MapSubjectIdColumn, mapSubjectId);
//            writeableDb.update(MapTip.TableName, mapTipUpdate, );
            writeableDb.setTransactionSuccessful();
            return mapSubjectId;
        }
        finally {
            writeableDb.endTransaction();
        }
    }

    public long WriteMapSpecificTip(MapSpecificTipDTO mapSpecificTip) {
        writeableDb.beginTransaction();
        try {
            long mapTipId = WriteMapTip(mapSpecificTip);
            if (mapTipId < 0)
                return -1;

            ContentValues mapSpecificTipContent = new ContentValues();
            mapSpecificTipContent.put(MapSpecificTip.MapTipIdColumn, mapTipId);
            long mapSpecificTipId = writeableDb.insert(MapSpecificTip.TableName, null, mapSpecificTipContent);
            if (mapSpecificTipId < 0)
                return -1;
            writeableDb.setTransactionSuccessful();
            return mapSpecificTipId;
        }
        finally {
            writeableDb.endTransaction();
        }
    }
    public long WriteMapTypeTip(MapTypeTipDTO mapTypeTip) {
        writeableDb.beginTransaction();
        try {
            long mapTipId = WriteMapTip(mapTypeTip);
            if (mapTipId < 0)
                return -1;

            ContentValues mapTypeTipContent = new ContentValues();
            mapTypeTipContent.put(MapTypeTip.MapTipIdColumn, mapTipId);
            mapTypeTipContent.put(MapTypeTip.MapTypeIdColumn, mapTypeTip.getMapTypeId().typeId);
            long mapTypeTipId = writeableDb.insert(MapTypeTip.TableName, null, mapTypeTipContent);
            if (mapTypeTipId < 0)
                return -1;
            writeableDb.setTransactionSuccessful();
            return mapTypeTipId;
        }
        finally {
            writeableDb.endTransaction();
        }
    }
    public long WriteMapHeroPickTip(MapHeroPickTipDTO mapHeroPickTip) {
        writeableDb.beginTransaction();
        try {
            long mapTipId = WriteMapTip(mapHeroPickTip);
            if (mapTipId < 0)
                return -1;

            ContentValues mapTypeTipContent = new ContentValues();
            mapTypeTipContent.put(MapHeroPickTip.MapTipIdColumn, mapTipId);
            mapTypeTipContent.put(MapHeroPickTip.HeroIdColumn, mapHeroPickTip.getHeroId());
            long mapHeroPickTipId = writeableDb.insert(MapHeroPickTip.TableName, null, mapTypeTipContent);
            if (mapHeroPickTipId < 0)
                return -1;
            writeableDb.setTransactionSuccessful();
            return mapHeroPickTipId;
        }
        finally {
            writeableDb.endTransaction();
        }
    }

    private long WriteMapTip(MapTipDTO mapTip) {
        ContentValues mapTipContent = new ContentValues();
        if (mapTip.getMapSubjectId() <= 0)
            mapTipContent.putNull(MapTip.MapSubjectIdColumn);
        else
            mapTipContent.put(MapTip.MapSubjectIdColumn, mapTip.getMapSubjectId());

        mapTipContent.put(MapTip.MapTipDescriptionColumn, mapTip.getMapTipDescription());
        mapTipContent.put(MapTip.ParentMapTipIdColumn, mapTip.getParentMapTipId());
        mapTipContent.put(MapTip.OrderPrecedenceColumn, mapTip.getOrderPrecedence());
        return writeableDb.insert(MapTip.TableName, null, mapTipContent);
    }

    @Override
    public void close() throws Exception {
        if (writeableDb != null) writeableDb.close();
    }
}
