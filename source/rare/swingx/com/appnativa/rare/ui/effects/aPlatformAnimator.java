/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.ui.effects;

import com.appnativa.rare.ui.iPlatformComponent;

import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.Animator.Builder;
import org.jdesktop.core.animation.timing.Animator.RepeatBehavior;
import org.jdesktop.core.animation.timing.Interpolator;
import org.jdesktop.core.animation.timing.TimingSource;
import org.jdesktop.core.animation.timing.TimingTarget;
import org.jdesktop.core.animation.timing.interpolators.AccelerationInterpolator;
import org.jdesktop.core.animation.timing.interpolators.LinearInterpolator;
import org.jdesktop.swing.animation.timing.sources.SwingTimerTimingSource;

import java.util.concurrent.TimeUnit;

public abstract class aPlatformAnimator extends aAnimator implements TimingTarget {
  static {
    TimingSource ts = new SwingTimerTimingSource();

    Animator.setDefaultTimingSource(ts);
    ts.init();
  }

  protected Builder            builder = new Builder();
  protected float              acceleration;
  protected Animator           animator;
  protected iPlatformComponent component;
  protected float              deceleration;
  protected TimingTarget       notificationTarget;

  public aPlatformAnimator() {
    builder.setDuration(250, TimeUnit.MILLISECONDS);
  }

  @Override
  public void animate(iPlatformComponent c, Object postAnimateAction) {
    this.component         = c;
    this.postAnimateAction = postAnimateAction;
    start();
  }

  @Override
  public void begin(Animator source) {
    if ((component != null) && component.isDisposed()) {
      return;
    }

    if (component != null) {
      setAnimatingProperty(component, true);
    }

    if (notificationTarget != null) {
      notificationTarget.begin(source);
    }

    notifyListeners(this, Boolean.FALSE);
  }

  @Override
  public void end(Animator source) {
    if ((component != null) && component.isDisposed()) {
      return;
    }

    if (component != null) {
      setAnimatingProperty(component, false);
    }

    if (notificationTarget != null) {
      notificationTarget.end(source);
    }

    notifyListeners(this, Boolean.TRUE);
    clear();
  }

  @Override
  public void repeat(Animator source) {}

  @Override
  public void reverse(Animator source) {}

  @Override
  public void stop() {
    if (animator != null) {
      animator.stop();
    }
  }

  @Override
  public void timingEvent(Animator source, double fraction) {
    if (isComponentDisposed()) {
      cancel();

      return;
    }

    update((float) fraction);
  }

  @Override
  public void setAcceleration(float acceleration) {
    this.acceleration = acceleration;
    animator          = null;
  }

  @Override
  public void setAutoReverse(boolean autoReverse) {
    if (autoReverse) {
      builder.setRepeatBehavior(RepeatBehavior.REVERSE);
    } else {
      builder.setRepeatBehavior(RepeatBehavior.LOOP);
    }

    animator = null;
  }

  @Override
  public void setDeceleration(float deceleration) {
    this.deceleration = deceleration;
    animator          = null;
  }

  @Override
  public void setDuration(int duration) {
    builder.setDuration(duration, TimeUnit.MILLISECONDS);
    animator = null;
  }

  @Override
  public void setRepeatCount(int repeatCount) {
    if (repeatCount == 0) {
      repeatCount = 1;
    }

    builder.setRepeatCount(repeatCount);
    animator = null;
  }

  @Override
  public boolean isRunning() {
    return (animator != null) && animator.isRunning();
  }

  @Override
  protected void clear() {
    super.clear();
    component = null;
  }

  protected void start() {
    if ((animator != null) && animator.isRunning()) {
      animator.cancel();
      animator = null;
    }

    if (animator == null) {
      Interpolator i = null;

      if ((acceleration != 0) && (deceleration != 0)) {
        i = new AccelerationInterpolator(acceleration, deceleration);
      } else if (acceleration != 0) {
        i = new AccelerationInterpolator(acceleration, 0.99 - acceleration);
      } else if (deceleration != 0) {
        i = new AccelerationInterpolator(0.99 - deceleration, deceleration);
      } else {
        i = LinearInterpolator.getInstance();
      }

      builder.setInterpolator(i);
      animator = builder.build();
      animator.addTarget(this);
    }

    animator.start();
  }

  protected boolean isComponentDisposed() {
    if (((component != null) && component.isDisposed())) {
      return true;
    }

    return false;
  }

  protected void update(float fraction) {}
}
