package com.innovations.aguilar.pocketstrats;

import android.view.View;

import java.util.Stack;

public interface Container {
    boolean onBackPressed();

    void removeViewToBackStack(View view);
}
