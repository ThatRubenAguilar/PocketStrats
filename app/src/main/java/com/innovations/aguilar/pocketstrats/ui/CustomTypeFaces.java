package com.innovations.aguilar.pocketstrats.ui;

import android.content.res.AssetManager;
import android.graphics.Typeface;

public class CustomTypeFaces {
    public static Typeface BigNoodleTitlingOblique(AssetManager assets) {
        return Typeface.createFromAsset(assets,  "fonts/big-noodle-titling-oblique.ttf");
    }
    public static Typeface DroidSans(AssetManager assets) {
        return DroidSans(assets, false);
    }
    public static Typeface DroidSans(AssetManager assets, boolean bold) {
        if (bold)
            return Typeface.createFromAsset(assets,  "fonts/DroidSans-Bold.ttf");
        else
            return Typeface.createFromAsset(assets,  "fonts/DroidSans.ttf");
    }

    public static Typeface Futura(AssetManager assets) {
        return Typeface.createFromAsset(assets,  "fonts/Futura Light font.ttf");
    }
}
