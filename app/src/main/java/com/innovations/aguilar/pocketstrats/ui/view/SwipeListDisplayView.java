package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.ui.CustomTypeFaces;
import com.innovations.aguilar.pocketstrats.ui.OnIndexClickListener;

public class SwipeListDisplayView extends RelativeLayout {
    private ImageView leftScroll;
    private ImageView rightScroll;
    private TextView textDisplay;

    public DataAdapter getDataAdapter() {
        return adapter;
    }

    private DataAdapter adapter;

    public int getSelectedIndex() {
        return selectedIndex;
    }

    private int selectedIndex = 0;

    public SwipeListDisplayView(Context context) {
        super(context);
        inflateLayout();
    }

    public SwipeListDisplayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateLayout();
    }

    public SwipeListDisplayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout();
    }

    private void inflateLayout() {
        inflate(getContext(), R.layout.list_swipe_display_layout, this);
        leftScroll = (ImageView)findViewById(R.id.btn_left_scroll);
        rightScroll = (ImageView)findViewById(R.id.btn_right_scroll);
        textDisplay = (TextView)findViewById(R.id.text_display);

        Drawable rightArrow = ContextCompat.getDrawable(getContext(), R.drawable.btn_forward);
        rightScroll.setBackground(rightArrow);
        Drawable leftArrow = ContextCompat.getDrawable(getContext(), R.drawable.btn_backward);
        leftScroll.setBackground(leftArrow);

        Animation rightArrowAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.right_bob);
        rightScroll.startAnimation(rightArrowAnimation);
        Animation leftArrowAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.left_bob);
        leftScroll.startAnimation(leftArrowAnimation);

        textDisplay.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(getContext().getAssets()));

        leftScroll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPrev();
            }
        });

        rightScroll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectNext();
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setDataAdapter(DataAdapter adapter) {
        setDataAdapter(adapter, 0);
    }
    public void setDataAdapter(DataAdapter adapter, int selectedIndex) {
        Preconditions.checkNotNull(adapter, "adapter must not be null");
        if (this.adapter != adapter) {
            this.adapter = adapter;
        }
        setSelectedIndex(selectedIndex);
    }

    public void setOnNextClickListener(final OnIndexClickListener listener) {
        rightScroll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectNext();
                listener.onIndexClick(v, selectedIndex);
            }
        });
    }
    public void setOnPrevClickListener(final OnIndexClickListener listener) {
        leftScroll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPrev();
                listener.onIndexClick(v, selectedIndex);
            }
        });
    }


    public void setSelectedIndex(int index) {
        Preconditions.checkNotNull(adapter, "adapter must not be null");
        Preconditions.checkArgument(index < adapter.getItemCount() && index >= 0, "Selected Index '%s' out of range of list size '%s'", index, adapter.getItemCount());
        setSelectedIndexUnchecked(index);
    }

    public void selectNext() {
        Preconditions.checkNotNull(adapter, "adapter must not be null");
        setSelectedIndexUnchecked((selectedIndex + 1) % adapter.getItemCount());
    }
    public void selectPrev() {
        Preconditions.checkNotNull(adapter, "adapter must not be null");
        int mod = (selectedIndex - 1) % adapter.getItemCount();
        setSelectedIndexUnchecked(mod >= 0 ? mod : mod + adapter.getItemCount());
    }

    private void setSelectedIndexUnchecked(int index) {
        selectedIndex = index;
        adapter.onBindData(this, selectedIndex);
    }

    public void setText(Spannable span) {
        textDisplay.setText(span);
    }
    public void setText(String text) {
        textDisplay.setText(text);
    }

    public static abstract class DataAdapter {
        abstract public int getItemCount();

        abstract public void onBindData(SwipeListDisplayView view, int position);
    }

}
