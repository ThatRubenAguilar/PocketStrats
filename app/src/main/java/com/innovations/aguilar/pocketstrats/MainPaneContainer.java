package com.innovations.aguilar.pocketstrats;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Stack;

public class MainPaneContainer extends FrameLayout implements Container {
    MainScreenView mainView;
    Stack<View> backStack = new Stack<>();

    public MainPaneContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mainView = (MainScreenView)findViewById(R.id.layout_main_screen_view);
    }

    @Override
    public boolean onBackPressed() {
        if (!backStack.isEmpty()) {
            removeViewAt(0);
            addView(backStack.pop());
            return true;
        }
        return false;
    }

    @Override
    public void removeViewToBackStack(View view) {
        backStack.push(view);
        removeView(view);
    }

}
