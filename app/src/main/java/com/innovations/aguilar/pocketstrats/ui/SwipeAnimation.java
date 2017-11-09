package com.innovations.aguilar.pocketstrats.ui;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SwipeAnimation {
    public Animation getInAnimation() {
        return inAnimation;
    }

    private final Animation inAnimation;

    public Animation getOutAnimation() {
        return outAnimation;
    }

    private final Animation outAnimation;

    public SwipeAnimation(Context context, @AnimRes int inAnimId, @AnimRes int outAnimId) {
        this.inAnimation = AnimationUtils.loadAnimation(context, inAnimId);
        this.outAnimation = AnimationUtils.loadAnimation(context, outAnimId);
    }

    public void swapTransition(final View inView, final View outView) {
        outView.setVisibility(VISIBLE);
        getOutAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                outView.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        outView.startAnimation(getOutAnimation());
        inView.startAnimation(getInAnimation());
    }
}
