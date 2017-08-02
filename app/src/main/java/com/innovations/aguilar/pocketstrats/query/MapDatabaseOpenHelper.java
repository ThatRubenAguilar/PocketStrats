package com.innovations.aguilar.pocketstrats.query;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.common.base.Charsets;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.io.CharStreams;
import com.innovations.aguilar.pocketstrats.dto.MapData;
import com.innovations.aguilar.pocketstrats.logging.LoggerSupplier;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapDatabaseOpenHelper extends SQLiteOpenHelper {
    static final Supplier<Logger> log = Suppliers.memoize(new LoggerSupplier(MapDatabaseOpenHelper.class));
    public static final int MapDatabaseVersion = 2;
    public static final String MapDatabaseName = "MapDatabase";
    private Context context;

    public MapDatabaseOpenHelper(Context context) {
        super(context, MapDatabaseName, null, MapDatabaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            CreateMapDatabase(sqLiteDatabase, context.getAssets());
        }
        catch (IOException iex) {
            log.get().error("Exception onCreate MapDatabase: %s", iex.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onCreate(sqLiteDatabase);
    }


    void CreateMapDatabase(SQLiteDatabase writableDatabase, AssetManager assets)
            throws IOException {
        ReadAndExecuteQueries(writableDatabase, assets,
                "dbscript/PocketStrats_sqlite_create_maps.sql");
        ReadAndExecuteQueries(writableDatabase, assets,
                "dbscript/PocketStrats_sqlite_create_map_segments.sql");
        ReadAndExecuteQueries(writableDatabase, assets,
                "dbscript/PocketStrats_sqlite_create_map_locations.sql");
        ReadAndExecuteQueries(writableDatabase, assets,
                "dbscript/PocketStrats_sqlite_create_map_spawn_statistics.sql");
        ReadAndExecuteQueries(writableDatabase, assets,
                "dbscript/PocketStrats_sqlite_create_map_type_spawntimes.sql");

    }

    void ReadAndExecuteQueries(SQLiteDatabase writableDatabase, AssetManager assets, String scriptAssetPath)
            throws IOException {
        try (InputStream scriptTextStream = assets.open(scriptAssetPath)) {
            String scriptText = CharStreams.toString(new InputStreamReader(
                    scriptTextStream, Charsets.UTF_8));
            String[] queries = scriptText.split("(?m);$");
            for (String query : queries) {
                FilterOrExecuteQuery(writableDatabase, query);
            }
        }
    }

    static final Pattern AllWhitespaceRegex = Pattern.compile("\\s*");
    void FilterOrExecuteQuery(SQLiteDatabase writableDatabase, String query) {
        Matcher matches = AllWhitespaceRegex.matcher(query);
        if (matches.matches() && matches.group().contentEquals(query)){
            log.get().debug("Filtered Empty SQL '{}'", query);
        }
        else {
            log.get().debug("Running SQL '{}'", query);
            writableDatabase.execSQL(query);
        }
    }

}
