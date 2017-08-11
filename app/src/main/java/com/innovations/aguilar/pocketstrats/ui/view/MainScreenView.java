package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.innovations.aguilar.pocketstrats.ui.CustomTypeFaces;
import com.innovations.aguilar.pocketstrats.ui.MainActivity;
import com.innovations.aguilar.pocketstrats.ui.MainPaneContainer;
import com.innovations.aguilar.pocketstrats.R;

/**
 * Created by Ruben on 7/3/2017.
 */

public class MainScreenView extends LinearLayout {
    Button buttonMode;
    final MainPanePresenter presenter;
    Supplier<MainPaneContainer> mainContainer;

    public MainScreenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        presenter = new MainPanePresenter();
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

        buttonMode = (Button)findViewById(R.id.button_modes);
        buttonMode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.modesClicked();
            }
        });

        buttonMode.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(getContext().getAssets()));
    }

    protected void showMapSearchView() {
        mainContainer.get().removeViewToBackStack(this);
        View.inflate(getContext(), R.layout.map_search, mainContainer.get());
    }


    class MainPanePresenter {
        public void modesClicked() {
            showMapSearchView();
        }
    }
}
