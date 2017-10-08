package com.innovations.aguilar.pocketstrats.ui;

import android.view.View;

public interface OnDataClickListener<TData> {
    void onClick(View v, TData data);
}
