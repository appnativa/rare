/*
 * @(#)ValueAnimator.java
 *
 * Copyright (c) appNativa, Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.effects;

import org.jdesktop.core.animation.timing.Animator;

public class ValueRangeAnimator extends aPlatformAnimator {
  float   delta;
  float   endValue;
  boolean intValue;
  float   startValue;

  public ValueRangeAnimator(float start, float end) {
    super();
    startValue = start;
    endValue = end;
    delta = end - start;
  }

  public ValueRangeAnimator(int start, int end) {
    super();
    startValue = start;
    endValue = end;
    delta = end - start;
    intValue = true;
  }

  @Override
  public void addValueListener(iAnimatorValueListener l) {
    super.addValueListener(l);
  }

  @Override
  public void removeValueListener(iAnimatorValueListener l) {
    super.removeValueListener(l);
  }

  public void setRange(float start, float end) {
    startValue = start;
    endValue = end;
    delta = end - start;
  }

  public void setRange(int start, int end) {
    startValue = start;
    endValue = end;
    delta = end - start;
    intValue = true;
  }

  @Override
  public void start() {
    super.start();
  }

  @Override
  public void timingEvent(Animator source, double fraction) {
    float value = startValue + (delta * (float) fraction);

    if (intValue) {
      value = (float) Math.floor(value);
    }

    notifyValueListeners(this, value);
  }

  public static ValueRangeAnimator ofFloat(float start, float end) {
    return new ValueRangeAnimator(start, end);
  }

  public static ValueRangeAnimator ofInt(int start, int end) {
    return new ValueRangeAnimator(start, end);
  }
}
