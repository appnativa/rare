/*
 * @(#)SlideAnimation.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.effects;

import org.jdesktop.core.animation.timing.Animator;

public class PullBackAnimation extends aPullBackAnimation {

  public PullBackAnimation() {
    this(true, true);
  }

  public PullBackAnimation(boolean horizontal) {
    this(horizontal, true);
  }

  public PullBackAnimation(boolean horizontal, boolean fromLeftOrTop) {
    super(horizontal, fromLeftOrTop);
    slide.notificationTarget     = this;
    slideBack.notificationTarget = this;
  }

  @Override
  public void begin(Animator source) {
    if (source == slideBack.animator) {
      return;
    }

    super.begin(source);
  }

  @Override
  public void end(Animator source) {
    if (source == slide.animator) {
      slideBack.animate(component, null);

      return;
    }

    super.end(source);
  }
}
