package com.innovations.aguilar.pocketstrats.ui.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public interface OnDataClickListener<TData> {
    void onClick(View v, TData data);
}

