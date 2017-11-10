package com.innovations.aguilar.pocketstrats.ui;

import android.view.View;

/**
 * Created by Ruben on 10/1/2017.
 */
public interface ViewBackStackManager {
    void makeTopOfBackStackCurrentView();
    boolean clearBackStackToTag(String tag);
    void removeViewToBackStack(View view);
    void removeViewToBackStack(View view, String tag);
}
