package com.innovations.aguilar.pocketstrats.ui.view.configurator;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.ui.CustomTypeFaces;
import com.innovations.aguilar.pocketstrats.ui.ImageResources;
import com.innovations.aguilar.pocketstrats.ui.view.BannerBackButton;

/**
 * Created by Ruben on 11/9/2017.
 */
public class BannerConfigurator {
    public static void configureForMap(BannerBackButton banner, MapDataDTO map) {
        Context context = banner.getContext();
        banner.setBannerTypeface(CustomTypeFaces.BigNoodleTitlingOblique(context.getAssets()));
        banner.setBannerText(map.getMapName());

        Drawable mapBanner = ImageResources.getMapBanner(context, map);
        banner.setBannerBackground(mapBanner);
    }
}
