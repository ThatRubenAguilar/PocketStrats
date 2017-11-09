package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Sourced from neevek at https://stackoverflow.com/questions/2646028/horizontalscrollview-within-scrollview-touch-handling
public class VerticalScrollView extends ScrollView {
    protected static Logger log = LoggerFactory.getLogger(VerticalScrollView.class);
    private float xDistance, yDistance, lastX, lastY;

    public VerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        log.debug("InterceptTouchEvent : {}", ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                lastX = ev.getX();
                lastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                xDistance += Math.abs(curX - lastX);
                yDistance += Math.abs(curY - lastY);
                lastX = curX;
                lastY = curY;
                log.debug("Moved ({},{})", xDistance, yDistance);
                if(xDistance > yDistance)
                    return false;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        log.debug("TouchEvent : {}", ev);
        return super.onTouchEvent(ev);
    }
}
