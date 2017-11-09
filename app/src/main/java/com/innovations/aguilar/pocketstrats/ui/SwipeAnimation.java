package com.innovations.aguilar.pocketstrats.ui;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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
}
