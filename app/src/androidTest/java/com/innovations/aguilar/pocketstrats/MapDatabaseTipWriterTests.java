package com.innovations.aguilar.pocketstrats;

import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.innovations.aguilar.pocketstrats.parser.MapTipsNodeParser;
import com.innovations.aguilar.pocketstrats.parser.MapTipsWriter;
import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.sql.query.SqlDataAccessor;
import com.innovations.aguilar.pocketstrats.sql.write.SqlDataWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MapDatabaseTipWriterTests extends MapDatabaseTestFixture {

    SqlDataAccessor accessor = null;
    SqlDataWriter writer = null;
    @Before
    public void beforeTest() {
        accessor = new SqlDataAccessor(readableMapDb);
        writer = new SqlDataWriter(writeableMapDb);
    }

    @After
    public void afterTest() {
        accessor = null;
        writer = null;
    }


    @Test
    public void Writer_Should_Write_Subjects() throws Exception {
        MapTipsWriter tipsWriter = new MapTipsWriter(writer,accessor);

        String scriptAssetPath = "test_docs/ParseableTips1.txt";
        AssetManager assets = InstrumentationRegistry.getContext().getAssets();

        try (InputStream scriptTextStream = assets.open(scriptAssetPath)) {
            tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8));
        }

    }
    @Test
    public void Writer_Should_Write_Real_Tips() throws Exception {
        MapTipsWriter tipsWriter = new MapTipsWriter(writer,accessor);

        AssetManager assets = InstrumentationRegistry.getTargetContext().getAssets();

        try (InputStream scriptTextStream = assets.open("tips_docs/map_type_tips.txt")) {
            tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8));
        }
        try (InputStream scriptTextStream = assets.open("tips_docs/assault_maps_tips.txt")) {
            tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8));
        }
        try (InputStream scriptTextStream = assets.open("tips_docs/control_maps_tips.txt")) {
            tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8));
        }
        try (InputStream scriptTextStream = assets.open("tips_docs/escort_maps_tips.txt")) {
            tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8));
        }
        try (InputStream scriptTextStream = assets.open("tips_docs/hybrid_assault_escort_maps_tips.txt")) {
            tipsWriter.WriteTips(new InputStreamReader(scriptTextStream, Charsets.UTF_8));
        }

    }
}
