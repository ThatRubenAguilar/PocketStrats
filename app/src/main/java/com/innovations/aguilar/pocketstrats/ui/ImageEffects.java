package com.innovations.aguilar.pocketstrats.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.innovations.aguilar.pocketstrats.R;

public class ImageEffects {
    public static Drawable tint(Context context, Drawable drawable, @ColorRes int id) {
        // TODO: Unfuck all other areas that use DrawableCompact wrapping without needing to tint.
        int highlightColor = ContextCompat.getColor(context, id);
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, highlightColor);
        DrawableCompat.setTintMode(wrappedDrawable, PorterDuff.Mode.SCREEN);
        return DrawableCompat.unwrap(wrappedDrawable);
    }

    public static Drawable removeTint(Drawable drawable) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintMode(wrappedDrawable, null);
        return DrawableCompat.unwrap(wrappedDrawable);
    }

    public static LayerDrawable layerDrawables(Context context, @DrawableRes int ... resourceIds) {
        Drawable[] layerList = new Drawable[resourceIds.length];

        for (int layer = 0; layer < layerList.length; layer++) {
            layerList[layer] = ContextCompat.getDrawable(context, resourceIds[layer]);
        }
        return new LayerDrawable(layerList);
    }
    public static LayerDrawable layerDrawables(Context context, Drawable ... drawables) {
        return new LayerDrawable(drawables);
    }
}
