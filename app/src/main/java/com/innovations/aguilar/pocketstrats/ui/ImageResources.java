package com.innovations.aguilar.pocketstrats.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.innovations.aguilar.pocketstrats.sql.dto.HeroDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;

/**
 * Created by Ruben on 10/10/2017.
 */
public class ImageResources {

    public static Drawable getMapBanner(Context context, MapDataDTO map) {
        String drawableName = String.format("ic_%s_list_item", map.getMapFileCompatName());
        return getDrawableResource(context, drawableName);
    }

    public static Drawable getHeroIcon(Context context, HeroDataDTO hero) {
        return getDrawableResource(context, hero.getHeroIconName().substring(0, hero.getHeroIconName().length()-".png".length()/*TODO: Modify this in the data set iself*/));
    }

    public static Drawable getDrawableResource(Context context, String drawableName) {
        int itemResourceId = context.getResources()
                .getIdentifier(drawableName, "drawable", context.getPackageName());
        return ContextCompat.getDrawable(context, itemResourceId);
    }
}

