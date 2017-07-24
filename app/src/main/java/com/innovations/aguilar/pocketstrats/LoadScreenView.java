package com.innovations.aguilar.pocketstrats;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

public class LoadScreenView extends RelativeLayout {
    // TODO: Spruce up loading screen with switching images ala: https://stackoverflow.com/a/41343275/986215
    ProgressBar progBar;
    Supplier<MainPaneContainer> mainContainer;

    public LoadScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mainContainer = Suppliers.memoize(new Supplier<MainPaneContainer>() {
            @Override
            public MainPaneContainer get() {
                return (MainPaneContainer)((MainActivity)getContext()).findViewById(R.id.main_container);
            }
        });

        progBar = (ProgressBar)findViewById(R.id.progressBar);
        progBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                showMainScreen();
            }
        }, 3000);
    }

    void showMainScreen() {
        mainContainer.get().removeView(this);
        View mainScreen = mainContainer.get().findViewById(R.id.main_screen_view);
        mainScreen.setVisibility(VISIBLE);
    }
}
