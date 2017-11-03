package com.innovations.aguilar.pocketstrats.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

public class ImageEffects {
    public static Drawable tint(Context context, Drawable drawable, @ColorRes int id) {
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
}
