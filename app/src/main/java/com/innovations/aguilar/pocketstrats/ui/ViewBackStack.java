package com.innovations.aguilar.pocketstrats.ui;

import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.Stack;

public class ViewBackStack implements ViewBackStackManager {
    Stack<ViewContainer> backStack = new Stack<>();
    Set<String> usedTags = Sets.newHashSet();
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
            makeTopOfBackStackCurrentView();
            return true;
        }
        return false;
    }

    public void makeTopOfBackStackCurrentView() {
        ViewContainer viewContainer = clearTopOfBackStack();
        mainView.removeViewAt(0);
        mainView.addView(viewContainer.getView());
    }

    private ViewContainer clearTopOfBackStack() {
        ViewContainer viewContainer = backStack.pop();
        if (viewContainer.getTag() != null)
            usedTags.remove(viewContainer.getTag());
        return viewContainer;
    }
    private void addTopOfBackStack(ViewContainer viewContainer) {
        backStack.push(viewContainer);
        if (viewContainer.getTag() != null)
            usedTags.add(viewContainer.getTag());
    }

    public boolean clearBackStackToTag(String tag) {
        Preconditions.checkNotNull(tag, "tag cannot be null");
        String currentTag = null;
        while(!backStack.isEmpty()) {
            ViewContainer container = clearTopOfBackStack();
            currentTag = container.getTag();
            if (tag.equals(currentTag)) {
                addTopOfBackStack(container);
                return true;
            }
        }
        return false;
    }

    public void removeViewToBackStack(View view) {
        removeViewToBackStack(view, null);
    }
    public void removeViewToBackStack(View view, String tag) {
        addTopOfBackStack(new ViewContainer(view, tag));
        mainView.removeView(view);
    }

    private class ViewContainer {
        public View getView() {
            return view;
        }

        private final View view;

        public String getTag() {
            return tag;
        }

        private final String tag;

        public ViewContainer(View view, String tag) {
            this.view = view;
            this.tag = tag;
        }
    }

    public static final ViewBackStackManager DoNothingViewBackStack = new ViewBackStackManager() {
        @Override
        public void makeTopOfBackStackCurrentView() {

        }

        @Override
        public boolean clearBackStackToTag(String tag) {
            return false;
        }

        @Override
        public void removeViewToBackStack(View view) {

        }

        @Override
        public void removeViewToBackStack(View view, String tag) {

        }
    };
}
