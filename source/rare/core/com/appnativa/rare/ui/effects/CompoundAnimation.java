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

import java.util.ArrayList;
import java.util.List;

public class CompoundAnimation extends aPlatformAnimator implements iAnimatorListener {
  List<iPlatformAnimator> animations = new ArrayList<iPlatformAnimator>(3);
  private int             runCount;
  private boolean         running;
  private boolean         serial;

  public CompoundAnimation() {
    this(false);
  }

  public CompoundAnimation(boolean serial) {
    this.serial = serial;
  }

  public CompoundAnimation(boolean serial, iPlatformAnimator... animations) {
    this.serial = serial;

    if (animations != null) {
      for (iPlatformAnimator a : animations) {
        addAnimation(a);
      }
    }
  }

  public void addAnimation(iPlatformAnimator animator) {
    animator.removeListener(this);
    animator.addListener(this);
    animations.add(animator);
  }

  @Override
  public void animate(iPlatformComponent c, Object postAnimateAction) {
    this.component         = c;
    this.postAnimateAction = postAnimateAction;
    running                = true;
    runCount               = 0;
    notifyListeners(this, false);

    if (animations.isEmpty()) {
      notifyListeners(this, true);
    } else {
      if (serial) {
        animations.get(runCount).animate(component, null);
      } else {
        for (iPlatformAnimator a : animations) {
          a.animate(c, null);
        }
      }
    }
  }

  @Override
  public void animationEnded(iPlatformAnimator source) {
    runCount++;

    if (runCount >= animations.size()) {
      notifyListeners(this, Boolean.TRUE);
    } else if (serial) {
      animations.get(runCount).animate(component, null);
    }
  }

  @Override
  public void animationStarted(iPlatformAnimator source) {}

  @Override
  public void dispose() {
    for (iPlatformAnimator a : animations) {
      a.dispose();
    }

    animations.clear();
    super.dispose();
  }

  public void removeAnimation(iPlatformAnimator animator) {
    animations.remove(animator);
  }

  public void setSerial(boolean serial) {
    this.serial = serial;
  }

  public List<iPlatformAnimator> getAnimations() {
    return animations;
  }

  @Override
  public boolean isRunning() {
    return running;
  }

  public boolean isSerial() {
    return serial;
  }
}
