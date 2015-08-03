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

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.appnativa.util.SNumber;

import java.util.Map;

/**
 *
 * @author Don DeCoteau
 */
public class AnimationWrapper extends aPlatformAnimator {
  public AnimationWrapper(Animation animation) {
    super(animation);
  }

  @Override
  public void handleCustomProperties(Map map) {
    super.handleCustomProperties(map);

    if (map == null) {
      return;
    }

    String s = (String) map.get("interpolator");

    if (s == null) {
      return;
    }

    String ss = (String) map.get("interpolatorParam");
    float  f  = 0;

    if ((ss != null) && (ss.length() > 0)) {
      f = SNumber.floatValue(ss);
    }

    if (s.indexOf('.') != -1) {
      try {
        Class cls = Class.forName(s);

        if (ss == null) {
          animation.setInterpolator((android.view.animation.Interpolator) cls.newInstance());
        } else {
          animation.setInterpolator(
              (android.view.animation.Interpolator) cls.getConstructor(float.class).newInstance(f));
        }
      } catch(Exception e) {
        throw new RuntimeException("unknow interpolator class:" + s);
      }
    }

    if (s.equalsIgnoreCase("Accelerate")) {
      animation.setInterpolator((ss == null)
                                ? new AccelerateInterpolator()
                                : new AccelerateInterpolator(f));
    } else if (s.equalsIgnoreCase("Decelerate")) {
      animation.setInterpolator((ss == null)
                                ? new DecelerateInterpolator()
                                : new DecelerateInterpolator(f));
    } else if (s.equalsIgnoreCase("Overshoot")) {
      animation.setInterpolator((ss == null)
                                ? new OvershootInterpolator()
                                : new OvershootInterpolator(f));
    } else if (s.equalsIgnoreCase("AnticipateOvershoot")) {
      animation.setInterpolator((ss == null)
                                ? new AnticipateOvershootInterpolator()
                                : new AnticipateOvershootInterpolator(f));
    } else if (s.equalsIgnoreCase("Anticipate")) {
      animation.setInterpolator((ss == null)
                                ? new AnticipateInterpolator()
                                : new AnticipateInterpolator(f));
    } else if (s.equalsIgnoreCase("Cycle")) {
      animation.setInterpolator((ss == null)
                                ? new CycleInterpolator(10)
                                : new CycleInterpolator(f));
    } else if (s.equalsIgnoreCase("Bounce")) {
      animation.setInterpolator(new BounceInterpolator());
    } else if (s.equalsIgnoreCase("Linear")) {
      animation.setInterpolator(new LinearInterpolator());
    }
  }
}
