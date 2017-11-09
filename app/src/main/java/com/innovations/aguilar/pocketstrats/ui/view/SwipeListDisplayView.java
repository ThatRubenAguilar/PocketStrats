package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.text.Spannable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.ui.CustomTypeFaces;
import com.innovations.aguilar.pocketstrats.ui.OnIndexClickListener;
import com.innovations.aguilar.pocketstrats.ui.OnSwipeListener;
import com.innovations.aguilar.pocketstrats.ui.SwipeAnimation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwipeListDisplayView extends RelativeLayout {
    protected static Logger log = LoggerFactory.getLogger(SwipeListDisplayView.class);

    private ImageView leftScroll;
    private ImageView rightScroll;
    private TextView textDisplay;
    private TextView textDisplayTransition;

    private OnIndexClickListener nextListener;
    private OnIndexClickListener prevListener;

    private SwipeAnimation nextSwipeTransition;
    private SwipeAnimation prevSwipeTransition;
    private SwipeAnimation fadeSwipeTransition;

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
        textDisplayTransition = (TextView)findViewById(R.id.text_display_transition);

        Animation rightArrowAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.right_bob);
        rightScroll.startAnimation(rightArrowAnimation);
        Animation leftArrowAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.left_bob);
        leftScroll.startAnimation(leftArrowAnimation);

        textDisplay.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(getContext().getAssets()));
        textDisplayTransition.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(getContext().getAssets()));

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

        nextSwipeTransition = new SwipeAnimation(getContext(), R.anim.right_swipe_fade_on, R.anim.right_swipe_fade_off);
        prevSwipeTransition = new SwipeAnimation(getContext(), R.anim.left_swipe_fade_on, R.anim.left_swipe_fade_off);
        fadeSwipeTransition = new SwipeAnimation(getContext(), R.anim.fade_in, R.anim.fade_out);
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
        nextListener = listener;
    }
    public void setOnPrevClickListener(final OnIndexClickListener listener) {
        prevListener = listener;
    }


    public void setSelectedIndex(int index) {
        Preconditions.checkNotNull(adapter, "adapter must not be null");
        Preconditions.checkArgument(index < adapter.getItemCount() && index >= 0, "Selected Index '%s' out of range of list size '%s'", index, adapter.getItemCount());
        animationSwipeTransition(fadeSwipeTransition);
        setSelectedIndexUnchecked(index);
    }

    public void selectNext() {
        Preconditions.checkNotNull(adapter, "adapter must not be null");
        animationSwipeTransition(nextSwipeTransition);
        setSelectedIndexUnchecked((selectedIndex + 1) % adapter.getItemCount());
        if (nextListener != null)
            nextListener.onIndexClick(this, selectedIndex);
    }
    public void selectPrev() {
        Preconditions.checkNotNull(adapter, "adapter must not be null");
        animationSwipeTransition(prevSwipeTransition);
        int mod = (selectedIndex - 1) % adapter.getItemCount();
        setSelectedIndexUnchecked(mod >= 0 ? mod : mod + adapter.getItemCount());
        if (prevListener != null)
            prevListener.onIndexClick(this, selectedIndex);
    }

    private void setSelectedIndexUnchecked(int index) {
        selectedIndex = index;
        adapter.onBindData(this, selectedIndex);
    }

    private void animationSwipeTransition(SwipeAnimation swipeAnimation) {
        textDisplayTransition.setText(textDisplay.getText());
        swipeAnimation.swapTransition(textDisplay, textDisplayTransition);
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

    public static class SwipeListGestureListener extends OnSwipeListener {
        private SwipeListDisplayView view;

        public SwipeListGestureListener(SwipeListDisplayView view) {
            this.view = view;
        }

        @Override
        public boolean onSwipe(Direction direction) {
            if (direction == Direction.left) {
                view.selectPrev();
                return true;
            }
            else if (direction == Direction.right) {
                view.selectNext();
                return true;
            }
            return super.onSwipe(direction);
        }
    }

}
