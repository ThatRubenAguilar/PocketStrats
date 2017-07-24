package com.innovations.aguilar.pocketstrats;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainPaneContainer extends FrameLayout implements Container {
    MainScreenView mainView;

    public MainPaneContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mainView = (MainScreenView)findViewById(R.id.main_screen_view);
    }

    public boolean onBackPressed() {
        // TODO: Keep a backstack and pop off here.
        if (mainViewAttached()) {
            removeViewAt(0);
            addView(mainView);
            return true;
        }
        return false;
    }



    boolean mainViewAttached() {
        return mainView.getParent() != null;
    }

}
