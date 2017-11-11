package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.innovations.aguilar.pocketstrats.ui.listener.OnSwipeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ruben on 11/5/2017.
 */
public class SwipeableLayout extends LinearLayout {
    protected static Logger log = LoggerFactory.getLogger(SwipeableLayout.class);
    private float xDistance, yDistance, lastX, lastY;

    private GestureDetectorCompat swipeListener;

    public SwipeableLayout(Context context) {
        super(context);
    }

    public SwipeableLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeableLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSwipeListener(OnSwipeListener listener) {
        swipeListener = new GestureDetectorCompat(getContext(), listener);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        log.debug("InterceptTouchEvent : {}", ev);
        boolean result = super.onInterceptTouchEvent(ev);
        if (swipeListener != null && !result) {
            result = swipeListener.onTouchEvent(ev);
        }
        return result;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        log.debug("TouchEvent : {}", ev);
        boolean result = super.onTouchEvent(ev);
        if (swipeListener != null && !result) {
            result = swipeListener.onTouchEvent(ev);
        }

        return result;
    }
}
