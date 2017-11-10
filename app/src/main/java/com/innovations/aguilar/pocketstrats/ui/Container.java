package com.innovations.aguilar.pocketstrats.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.Stack;

public interface Container {
    ViewGroup getViewGroup();

    boolean onBackPressed();

    ViewBackStackManager getBackStackManager();
}
