package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

public class ListViewItem<T> extends AppCompatButton {
    private T itemData;

    public ListViewItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }

    public ListViewItem(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
    }

    public ListViewItem(Context context) {

        super(context);
    }

    public void setItemData(T itemData) {
        this.itemData = itemData;
    }

    public T getItemData() {
        return this.itemData;
    }
}
