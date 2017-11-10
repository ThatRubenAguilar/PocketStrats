package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.ui.ImageEffects;

import java.lang.reflect.Type;

public class BannerBackButton extends FrameLayout {

    protected TextView bannerText;
    protected ImageView bannerBack;

    public BannerBackButton(@NonNull Context context) {
        super(context);
        inflateLayout();
    }

    public BannerBackButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateLayout();
    }

    public BannerBackButton(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout();
    }

    private void inflateLayout() {
        inflate(getContext(), R.layout.banner_back_button_layout, this);
        bannerText = (TextView)findViewById(R.id.banner_text);
        bannerBack = (ImageView) findViewById(R.id.banner_back);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        OnTouchListener tintBackgroundOnTouch = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Drawable tintedDrawable = ImageEffects.tint(getContext(), bannerText.getBackground(), R.color.accentToggleOff);
                        bannerText.setBackground(tintedDrawable);
                        return false;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        bannerText.setBackground(ImageEffects.removeTint(bannerText.getBackground()));
                        return false;
                    }
                }
                return false;
            }
        };

        bannerText.setOnTouchListener(tintBackgroundOnTouch);
        bannerBack.setOnTouchListener(tintBackgroundOnTouch);
    }

    public void setBannerBackground(Drawable background) {
        bannerText.setBackground(background);
    }
    public void setBannerText(CharSequence chars) {
        bannerText.setText(chars);
    }

    public void setBannerTypeface(Typeface typeface) {
        bannerText.setTypeface(typeface);
    }
}
