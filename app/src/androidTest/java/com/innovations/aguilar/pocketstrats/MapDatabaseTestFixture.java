package com.innovations.aguilar.pocketstrats;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.InstrumentationRegistry;

import com.innovations.aguilar.pocketstrats.sql.query.MapDatabaseOpenHelper;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.IOException;

public class MapDatabaseTestFixture extends LoggingTestFixture {
    protected static SQLiteOpenHelper openHelper;
    protected SQLiteDatabase mapDatabase;

    @Before
    public void setupDatabaseBeforeTest(){

        log.get().debug("Before Begin {}", MapDatabaseTestFixture.class.toString());
        mapDatabase = openHelper.getReadableDatabase();
        log.get().debug("Before End {}", MapDatabaseTestFixture.class.toString());
    }

    @After
    public void closeDatabaseAfterTest() {
        log.get().debug("After Begin {}", MapDatabaseTestFixture.class.toString());
        if (mapDatabase != null) {
            mapDatabase.close();
            mapDatabase = null;
        }
        log.get().debug("After End {}", MapDatabaseTestFixture.class.toString());
    }

    @BeforeClass
    public static void setupDatabaseHelperBeforeAllTest()
            throws IOException {
        staticLog.get().debug("Before All Begin {}", MapDatabaseTestFixture.class.toString());
        Context targetContext = InstrumentationRegistry.getTargetContext();
        openHelper = new MapDatabaseOpenHelper(targetContext);
        staticLog.get().debug("Before All End {}", MapDatabaseTestFixture.class.toString());
    }

    @AfterClass
    public static void closeDatabaseHelperAfterAllTest() {
        staticLog.get().debug("After All Begin {}", MapDatabaseTestFixture.class.toString());
        if (openHelper != null) {
            openHelper.close();
            openHelper = null;
        }
        staticLog.get().debug("After All End {}", MapDatabaseTestFixture.class.toString());
    }

}
