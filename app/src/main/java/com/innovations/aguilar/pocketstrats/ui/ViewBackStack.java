package com.innovations.aguilar.pocketstrats.ui;

import android.view.View;
import android.view.ViewGroup;

import java.util.Stack;

/**
 * Created by Ruben on 10/1/2017.
 */
public class ViewBackStack {
    Stack<View> backStack = new Stack<>();
    private ViewGroup mainView;

    public ViewBackStack(ViewGroup mainView) {
        this.mainView = mainView;
    }

    private View getCurrentView() {
        return mainView.getChildAt(0);
    }

    public boolean onBackPressed() {
        View currentView = getCurrentView();
        if (currentView instanceof Container) {
            boolean handled = ((Container) currentView).onBackPressed();
            if (handled) return true;
        }
        if (!backStack.isEmpty()) {
            mainView.removeViewAt(0);
            mainView.addView(backStack.pop());
            return true;
        }
        return false;
    }

    public void removeViewToBackStack(View view) {
        backStack.push(view);
        mainView.removeView(view);
    }
}
