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

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import com.appnativa.rare.ui.iPlatformComponent;

public abstract class aPlatformAnimator extends aAnimator
        implements iPlatformAnimator, AnimatorListener, Animation.AnimationListener {
  protected float              acceleration;
  protected Animation          animation;
  protected Animator           animator;
  protected boolean            autoReverse;
  protected iPlatformComponent component;
  protected float              deceleration;
  protected int                duration = 250;
  protected int                repeatCount;

  public aPlatformAnimator(Animation a) {
    animation = a;

    if (a != null) {
      a.setAnimationListener(this);
    }
  }

  public void setAnimator(Animator animator) {
    this.animator = animator;

    if (animator != null) {
      animator.removeListener(this);
      animator.addListener(this);
    }
  }

  public aPlatformAnimator(Animator a) {
    animator = a;

    if (a != null) {
      a.removeListener(this);
      a.addListener(this);
    }
  }

  protected aPlatformAnimator() {}

  public void animate(iPlatformComponent c, Object postAnimateAction) {
    this.setPostAnimateAction(postAnimateAction);
    component = c;

    if (animator == null) {
      Animation a = animation;

      if (a != null) {
        a.reset();
        a.setRepeatMode(autoReverse
                        ? Animation.REVERSE
                        : Animation.RESTART);
        a.setDuration((duration < 0)
                      ? Long.MAX_VALUE - 1
                      : duration);

        if ((acceleration != 0) && (deceleration != 0)) {
          a.setInterpolator(new AccelerateDecelerateInterpolator());
        } else {
          if (acceleration != 0) {
            a.setInterpolator(new AccelerateInterpolator(acceleration));
          } else if (deceleration != 0) {
            a.setInterpolator(new DecelerateInterpolator(deceleration));
          }
        }

        a.setRepeatCount(repeatCount);
        ((View) c.getView()).startAnimation(a);
      }
    } else if (animator != null) {
      if ((acceleration != 0) && (deceleration != 0)) {
        //animator.setInterpolator(new AccelerateDecelerateInterpolator());
      } else if (acceleration != 0) {
        animator.setInterpolator(new AccelerateInterpolator(acceleration));
      } else if (deceleration != 0) {
        animator.setInterpolator(new DecelerateInterpolator(deceleration));
      } else {
        animator.setInterpolator(null);
      }

      animator.setDuration((duration < 0)
                           ? Long.MAX_VALUE - 1
                           : duration);
      animator.start();
    }
  }

  @Override
  public void onAnimationCancel(Animator animation) {}

  public void onAnimationEnd(Animation anmtn) {
    notifyListeners(this, true);
    handlePostAnimateAction();
  }

  @Override
  public void onAnimationEnd(Animator animation) {
    notifyListeners(this, true);
  }

  public void onAnimationRepeat(Animation anmtn) {}

  @Override
  public void onAnimationRepeat(Animator animation) {}

  public void onAnimationStart(Animation anmtn) {
    notifyListeners(this, false);
  }

  @Override
  public void onAnimationStart(Animator animation) {
    notifyListeners(this, false);
  }

  public void stop() {
    if (animator != null) {
      animator.cancel();
    } else if (animation != null) {
      animation.cancel();
      animation.reset();
    }
  }

  public void setAcceleration(float acceleration) {
    this.acceleration = acceleration;
  }

  public void setAutoReverse(boolean autoReverse) {
    this.autoReverse = autoReverse;
  }

  public void setDeceleration(float deceleration) {
    this.deceleration = deceleration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public void setRepeatCount(int repeatCount) {
    this.repeatCount = repeatCount;
  }

  public Animation getAnimation() {
    return animation;
  }

  public Animator getAnimator() {
    return animator;
  }

  public boolean isRunning() {
    if (animation != null) {
      return animation.hasStarted() &&!animation.hasEnded();
    }

    return (animator == null)
           ? false
           : animator.isRunning();
  }
}
