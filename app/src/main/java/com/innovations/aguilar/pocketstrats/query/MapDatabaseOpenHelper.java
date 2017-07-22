package com.innovations.aguilar.pocketstrats.query;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MapDatabaseOpenHelper extends SQLiteOpenHelper {

    public static final int MapDatabaseVersion = 1;
    public static final String MapDatabaseName = "MapDatabase";
    public MapDatabaseOpenHelper(Context context) {
        super(context, MapDatabaseName, null, MapDatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
