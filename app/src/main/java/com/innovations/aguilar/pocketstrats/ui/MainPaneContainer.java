package com.innovations.aguilar.pocketstrats.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.ui.Container;
import com.innovations.aguilar.pocketstrats.ui.view.MainScreenView;

import java.util.Stack;

public class MainPaneContainer extends FrameLayout implements Container {
    MainScreenView mainView;
    ViewBackStack backStack = new ViewBackStack(this);

    public MainPaneContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mainView = (MainScreenView)findViewById(R.id.layout_main_screen_view);
    }

    @Override
    public ViewGroup getViewGroup() {
        return this;
    }

    @Override
    public boolean onBackPressed() {
        return backStack.onBackPressed();
    }

    @Override
    public ViewBackStackManager getBackStackManager() {
        return backStack;
    }

}
