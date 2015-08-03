/*
 * @(#)SlideAnimation.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.effects;


public class SlideAnimation extends aSlideAnimation {

  public SlideAnimation() {
    this(true, false);
  }

  public SlideAnimation(boolean horizontal) {
    this(horizontal, false);
  }

  public SlideAnimation(boolean horizontal, boolean fromLeftOrTop) {
    super(horizontal, fromLeftOrTop);
  }
}
