/*
 * @(#)ShakeAnimation.java
 *
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.effects;

public class ShakeAnimation extends CompoundAnimation {
  public ShakeAnimation() {
    super(true);

    int            duration = 50;
    SlideAnimation fleft    = new SlideAnimation(true, false);

    fleft.setPercent(0.05f);
    fleft.setDuration(duration);

    SlideAnimation left = new SlideAnimation(true, false);

    left.setPercent(0.1f);
    left.setDuration(duration);

    SlideAnimation right = new SlideAnimation(true, true);

    right.setPercent(0.1f);
    right.setDuration(duration);

    SlideAnimation lright = new SlideAnimation(true, true);

    lright.setPercent(0.05f);
    lright.setDuration(duration);
    
    addAnimation(fleft);
    addAnimation(right);
    addAnimation(left);
    addAnimation(right);
    addAnimation(left);
    addAnimation(lright);
  }
}
