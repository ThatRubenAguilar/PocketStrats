package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.common.base.Supplier;
import com.innovations.aguilar.pocketstrats.ui.Container;
import com.innovations.aguilar.pocketstrats.ui.CustomTypeFaces;
import com.innovations.aguilar.pocketstrats.ui.MainActivity;
import com.innovations.aguilar.pocketstrats.R;

/**
 * Created by Ruben on 7/3/2017.
 */

public class MainScreenView extends LinearLayout {
    Button buttonMode;
    Supplier<Container> mainContainer;

    public MainScreenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mainContainer = MainActivity.generateContainerRef(this);

        buttonMode = (Button)findViewById(R.id.button_modes);
        final MainScreenView mainScreenRef = this;
        buttonMode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MapSearchView.MapSearchPresenter searchPresenter =
                        new MapSearchView.MapSearchPresenter(mainContainer.get(), mainScreenRef);
                searchPresenter.presentMapSearch();
            }
        });

        buttonMode.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(getContext().getAssets()));
    }

}
