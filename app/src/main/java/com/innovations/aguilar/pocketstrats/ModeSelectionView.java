package com.innovations.aguilar.pocketstrats;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

public class ModeSelectionView extends LinearLayout {
    Button buttonAssault;
    Button buttonControl;
    Button buttonEscort;
    Button buttonHybrid_AE;
    ModeSelectionPresenter presenter;

    public ModeSelectionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        presenter = new ModeSelectionPresenter();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        buttonAssault = (Button)findViewById(R.id.button_assault);
        buttonControl = (Button)findViewById(R.id.button_control);
        buttonEscort = (Button)findViewById(R.id.button_escort);
        buttonHybrid_AE = (Button)findViewById(R.id.button_hybrid_assault_escort);
    }

    class ModeSelectionPresenter {

    }
}
