package com.innovations.aguilar.pocketstrats.sql.query;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.innovations.aguilar.pocketstrats.parser.MapTipsWriter;
import com.innovations.aguilar.pocketstrats.parser.TipsDocument;
import com.innovations.aguilar.pocketstrats.sql.write.SqlDataWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapDatabaseOpenHelper extends SQLiteOpenHelper {
    static final Logger log = LoggerFactory.getLogger(MapDatabaseOpenHelper.class);
    public static final int MapDatabaseVersion = 34;
    public static final String MapDatabaseName = "MapDatabase";
    private Context context;

    private static MapDatabaseOpenHelper instance;

    // SQLITE NOTE: Going with singleton help as described here: http://touchlabblog.tumblr.com/post/24474750219/single-sqlite-connection
    // Should SQLite ever become non-file, all close resources are merely commented out.
    public static synchronized MapDatabaseOpenHelper getHelper(Context context)
    {
        if (instance == null)
            instance = new MapDatabaseOpenHelper(context);

        return instance;
    }

    // TODO: decouple all getDatabase calls and add loading widget with transition callback.
    public MapDatabaseOpenHelper(Context context) {
        super(context, MapDatabaseName, null, MapDatabaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            CreateMapDatabase(sqLiteDatabase, context.getAssets());
            CreateMapTipsDatabase(sqLiteDatabase, context.getAssets());
        }
        catch (IOException iex) {
            log.error("Exception onCreate MapDatabase: {}", iex);
            return;
        }
        try {
            // Don't close db in onCreate
            SqlDataAccessor accessor = new SqlDataAccessor(sqLiteDatabase);
            SqlDataWriter writer = new SqlDataWriter(sqLiteDatabase);

            MapTipsWriter tipsWriter = new MapTipsWriter(writer, accessor);

            AssetManager assets = context.getAssets();

            TipsDocument currentDoc;
            try (InputStream scriptTextStream = assets.open("tips_docs/map_type_tips.txt")) {
                currentDoc = tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8));
            }
            try (InputStream scriptTextStream = assets.open("tips_docs/assault_maps_tips.txt")) {
                currentDoc = tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8),
                        currentDoc);
            }
            try (InputStream scriptTextStream = assets.open("tips_docs/control_maps_tips.txt")) {
                currentDoc = tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8),
                        currentDoc);
            }
            try (InputStream scriptTextStream = assets.open("tips_docs/escort_maps_tips.txt")) {
                currentDoc = tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8),
                        currentDoc);
            }
            try (InputStream scriptTextStream = assets.open("tips_docs/hybrid_assault_escort_maps_tips.txt")) {
                currentDoc = tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8),
                        currentDoc);
            }

            tipsWriter.WriteSubjectAssociations();
        }
        catch (IOException iex) {
            log.error("Exception writing tips to MapDatabase: {}", iex);
            return;
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
    void CreateMapTipsDatabase(SQLiteDatabase writableDatabase, AssetManager assets)
            throws IOException {
        ReadAndExecuteQueries(writableDatabase, assets,
                "dbscript/PocketStrats_sqlite_create_heros.sql");
        ReadAndExecuteQueries(writableDatabase, assets,
                "dbscript/PocketStrats_sqlite_create_map_subjects.sql");
        ReadAndExecuteQueries(writableDatabase, assets,
                "dbscript/PocketStrats_sqlite_create_map_subject_associations.sql");
        ReadAndExecuteQueries(writableDatabase, assets,
                "dbscript/PocketStrats_sqlite_create_map_tips.sql");
        ReadAndExecuteQueries(writableDatabase, assets,
                "dbscript/PocketStrats_sqlite_create_map_tip_descriptions.sql");
        ReadAndExecuteQueries(writableDatabase, assets,
                "dbscript/PocketStrats_sqlite_create_map_specific_tips.sql");
        ReadAndExecuteQueries(writableDatabase, assets,
                "dbscript/PocketStrats_sqlite_create_map_hero_pick_tips.sql");
        ReadAndExecuteQueries(writableDatabase, assets,
                "dbscript/PocketStrats_sqlite_create_map_type_tips.sql");

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
            log.debug("Filtered Empty SQL '{}'", query);
        }
        else {
            log.debug("Running SQL '{}'", query);
            writableDatabase.execSQL(query);
        }
    }

}
