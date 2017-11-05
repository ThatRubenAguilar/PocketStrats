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
    protected SQLiteDatabase readableMapDb;
    protected SQLiteDatabase writeableMapDb;

    @Before
    public void setupDatabaseBeforeTest(){

        log.debug("Before Begin {}", MapDatabaseTestFixture.class.toString());
        readableMapDb = openHelper.getReadableDatabase();
        writeableMapDb = openHelper.getWritableDatabase();
        log.debug("Before End {}", MapDatabaseTestFixture.class.toString());
    }

    @After
    public void closeDatabaseAfterTest() {
        log.debug("After Begin {}", MapDatabaseTestFixture.class.toString());
        if (readableMapDb != null) {
            readableMapDb.close();
            readableMapDb = null;
        }
        if (writeableMapDb != null) {
            writeableMapDb.close();
            writeableMapDb = null;
        }
        log.debug("After End {}", MapDatabaseTestFixture.class.toString());
    }

    @BeforeClass
    public static void setupDatabaseHelperBeforeAllTest()
            throws IOException {
        staticLog.debug("Before All Begin {}", MapDatabaseTestFixture.class.toString());
        Context targetContext = InstrumentationRegistry.getTargetContext();
        openHelper = new MapDatabaseOpenHelper(targetContext);
        staticLog.debug("Before All End {}", MapDatabaseTestFixture.class.toString());
    }

    @AfterClass
    public static void closeDatabaseHelperAfterAllTest() {
        staticLog.debug("After All Begin {}", MapDatabaseTestFixture.class.toString());
        if (openHelper != null) {
            openHelper.close();
            openHelper = null;
        }
        staticLog.debug("After All End {}", MapDatabaseTestFixture.class.toString());
    }

}
