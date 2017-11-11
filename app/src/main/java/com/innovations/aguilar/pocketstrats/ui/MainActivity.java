package com.innovations.aguilar.pocketstrats.ui;

import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.parser.MapTipsWriter;
import com.innovations.aguilar.pocketstrats.parser.TipsDocument;
import com.innovations.aguilar.pocketstrats.sql.query.MapDatabaseOpenHelper;
import com.innovations.aguilar.pocketstrats.sql.query.SqlDataAccessor;
import com.innovations.aguilar.pocketstrats.sql.write.SqlDataWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    protected static Logger log = LoggerFactory.getLogger(MainActivity.class);

    Container container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            setupDatabase();
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.main_pane_container);
            container = (Container) findViewById(R.id.layout_main_container);
            log.debug("onCreate()");
        }
    }

    public Container getContainer() {
        return container;
    }

    @Override
    public void onBackPressed() {
        boolean handled = container.onBackPressed();
        if (!handled)
            finish();
    }

    public static Supplier<Container> generateContainerRef(final View v) {
        return Suppliers.memoize(new Supplier<Container>() {
            @Override
            public Container get() {
                Container containerRef = (Container)((MainActivity)v.getContext()).findViewById(R.id.layout_main_container);
                Preconditions.checkNotNull(containerRef,
                        "containerRef is null, ensure container is not referenced before onCreate finishes (e.g. onFinishInflate).");
                return containerRef;
            }
        });
    }

    private void setupDatabase() {
        MapDatabaseOpenHelper openHelper = MapDatabaseOpenHelper.getHelper(this);
        // TODO: Out to thread and decouple
        openHelper.getWritableDatabase();
    }
}
