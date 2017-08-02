package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.innovations.aguilar.pocketstrats.ui.MainActivity;
import com.innovations.aguilar.pocketstrats.ui.MainPaneContainer;
import com.innovations.aguilar.pocketstrats.R;

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
                return (MainPaneContainer)((MainActivity)getContext()).findViewById(R.id.layout_main_container);
            }
        });

//        progBar = (ProgressBar)findViewById(R.id.progress_bar_loading);
//        progBar.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                removeLoadScreen();
//                showMainScreen();
//            }
//        }, 3000);
    }

    void removeLoadScreen() {
        mainContainer.get().removeView(this);
    }
    void showMainScreen() {
        View mainScreen = mainContainer.get().findViewById(R.id.layout_main_screen_view);
        mainScreen.setVisibility(VISIBLE);
    }
}
