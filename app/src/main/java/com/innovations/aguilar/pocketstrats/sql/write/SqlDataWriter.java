package com.innovations.aguilar.pocketstrats.sql.write;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.innovations.aguilar.pocketstrats.sql.dto.MapHeroPickTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapHeroPickTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpecificTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpecificTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubject;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectAssociation;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectAssociationDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTip;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTipDescription;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTipDescriptionDTO;
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

    public int WriteMapSubjectAssociation(MapSubjectAssociationDTO mapSubjectAssociation) {
        ContentValues mapSubjectAssocContent = new ContentValues();
        mapSubjectAssocContent.put(MapSubjectAssociation.Columns.MapSubjectIdColumn,
                mapSubjectAssociation.getMapSubjectId());
        mapSubjectAssocContent.put(MapSubjectAssociation.Columns.AssociatedMapSubjectIdColumn,
                mapSubjectAssociation.getAssociatedMapSubjectId());
        return (int)writeableDb.insert(MapSubjectAssociation.Columns.TableName, null, mapSubjectAssocContent);
    }

    public int WriteMapSubject(MapSubjectDTO mapSubject) {
        writeableDb.beginTransaction();
        try {
            ContentValues mapSubjectContent = new ContentValues();

            int mapTipDescriptionId = mapSubject.getMapTipDescriptionId();
            if (mapTipDescriptionId <= 0) {
                mapTipDescriptionId = WriteMapTipDescription(mapSubject);
                if (mapTipDescriptionId < 0)
                    return -1;
            }

            mapSubjectContent.put(MapSubject.Columns.MapIdColumn, mapSubject.getMapId());
            mapSubjectContent.put(MapSubject.Columns.MapSubjectPrecedenceColumn, mapSubject.getMapSubjectPrecedence());
            mapSubjectContent.put(MapSubject.Columns.MapTipDescriptionIdColumn, mapTipDescriptionId);
            mapSubjectContent.put(MapSubject.Columns.SegmentIdColumn, mapSubject.getSegmentId());
            mapSubjectContent.put(MapSubject.Columns.SpawnSideDescriptionColumn, mapSubject.getSpawnSideId().toString());
            mapSubjectContent.put(MapSubject.Columns.SpawnSideIdColumn, mapSubject.getSpawnSideId().spawnSideId);
            int mapSubjectId = (int)writeableDb.insert(MapSubject.Columns.TableName, null, mapSubjectContent);
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
                mapTipId = writeMapTipInternal(mapSpecificTip);
                if (mapTipId < 0)
                    return -1;
            }

            ContentValues mapSpecificTipContent = new ContentValues();
            mapSpecificTipContent.put(MapSpecificTip.Columns.MapTipIdColumn, mapTipId);
            int mapSpecificTipId = (int)writeableDb.insert(MapSpecificTip.Columns.TableName, null, mapSpecificTipContent);
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
                mapTipId = writeMapTipInternal(mapTypeTip);
                if (mapTipId < 0)
                    return -1;
            }

            ContentValues mapTypeTipContent = new ContentValues();
            mapTypeTipContent.put(MapTypeTip.Columns.MapTipIdColumn, mapTipId);
            mapTypeTipContent.put(MapTypeTip.Columns.MapTypeIdColumn, mapTypeTip.getMapTypeId().typeId);
            int mapTypeTipId = (int)writeableDb.insert(MapTypeTip.Columns.TableName, null, mapTypeTipContent);
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
                mapTipId = writeMapTipInternal(mapHeroPickTip);
                if (mapTipId < 0)
                    return -1;
            }

            ContentValues mapTypeTipContent = new ContentValues();
            mapTypeTipContent.put(MapHeroPickTip.Columns.MapTipIdColumn, mapTipId);
            mapTypeTipContent.put(MapHeroPickTip.Columns.HeroIdColumn, mapHeroPickTip.getHeroId());
            int mapHeroPickTipId = (int)writeableDb.insert(MapHeroPickTip.Columns.TableName, null, mapTypeTipContent);
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
        writeableDb.beginTransaction();
        try {
            int mapTipId = writeMapTipInternal(mapTip);
            writeableDb.setTransactionSuccessful();
            return mapTipId;
        }
        finally {
            writeableDb.endTransaction();
        }
    }

    private int writeMapTipInternal(MapTipDTO mapTip) {
        ContentValues mapTipContent = new ContentValues();
        if (mapTip.getMapSubjectId() <= 0)
            mapTipContent.putNull(MapTip.Columns.MapSubjectIdColumn);
        else
            mapTipContent.put(MapTip.Columns.MapSubjectIdColumn, mapTip.getMapSubjectId());

        int mapTipDescriptionId = mapTip.getMapTipDescriptionId();
        if (mapTipDescriptionId <= 0) {
            mapTipDescriptionId = WriteMapTipDescription(mapTip);
            if (mapTipDescriptionId < 0)
                return -1;
        }
        mapTipContent.put(MapTip.Columns.MapTipDescriptionIdColumn, mapTipDescriptionId);
        mapTipContent.put(MapTip.Columns.ParentMapTipIdColumn, mapTip.getParentMapTipId());
        mapTipContent.put(MapTip.Columns.OrderPrecedenceColumn, mapTip.getOrderPrecedence());
        return (int)writeableDb.insert(MapTip.Columns.TableName, null, mapTipContent);
    }

    public int WriteMapTipDescription(MapTipDescriptionDTO mapTipDescription) {
        ContentValues mapTipContent = new ContentValues();
        mapTipContent.put(MapTipDescription.Columns.MapTipDescriptionHashColumn, mapTipDescription.getMapTipDescriptionHash());
        mapTipContent.put(MapTipDescription.Columns.MapTipDescriptionColumn, mapTipDescription.getMapTipDescription());
        return (int)writeableDb.insert(MapTipDescription.Columns.TableName, null, mapTipContent);
    }

    @Override
    public void close() {
        // See SQLITE NOTE
//        if (writeableDb != null) writeableDb.close();
    }
}
