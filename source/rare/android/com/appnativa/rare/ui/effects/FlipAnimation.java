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

import android.graphics.Camera;
import android.graphics.Matrix;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.appnativa.util.SNumber;

import java.util.Map;

public class FlipAnimation extends aPlatformAnimator {
  public FlipAnimation() {
    this(true, true);
  }

  public FlipAnimation(boolean horizontal, boolean in) {
    this(horizontal, 0, in
                        ? 90
                        : -90);
  }

  public FlipAnimation(boolean horizontal, float fromDegrees, float toDegrees) {
    super(new FlipAnimationImpl());

    FlipAnimationImpl a = (FlipAnimationImpl) animation;

    a.horizontal = horizontal;
    a.setInterpolator(new AccelerateInterpolator());
    a.setFillAfter(true);
    setDuration(500);
    a.fromDegrees = fromDegrees;
    a.toDegrees   = toDegrees;
  }

  public void handleCustomProperties(Map map) {
    super.handleCustomProperties(map);

    FlipAnimationImpl a = (FlipAnimationImpl) animation;
    String            s = (String) map.get("fromDegrees");

    if (s != null) {
      a.fromDegrees = SNumber.floatValue(s);
    }

    s = (String) map.get("toDegrees");

    if (s != null) {
      a.toDegrees = SNumber.floatValue(s);
    }

    s = (String) map.get("horizontal");

    if (s != null) {
      a.horizontal = SNumber.booleanValue(s);
    }
  }

  public static class FlipAnimationImpl extends Animation {
    private float   centerX;
    private float   centerY;
    private float   fromDegrees;
    private Camera  graphicsCamera;
    private boolean horizontal;
    private float   toDegrees;

    public void initialize(int width, int height, int parentWidth, int parentHeight) {
      super.initialize(width, height, parentWidth, parentHeight);
      graphicsCamera = new Camera();
      centerX        = ((float) parentWidth) / 2f;
      centerY        = ((float) parentHeight) / 2f;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
      float        degrees = fromDegrees + ((toDegrees - fromDegrees) * interpolatedTime);
      final Camera camera  = graphicsCamera;
      final Matrix matrix  = t.getMatrix();

      camera.save();

      if (horizontal) {
        camera.rotateY(degrees);
      } else {
        camera.rotateX(degrees);
      }

      camera.getMatrix(matrix);
      camera.restore();
      matrix.preTranslate(-centerX, -centerY);
      matrix.postTranslate(centerX, centerY);
    }

    @Override
    protected void ensureInterpolator() {
      if (getInterpolator() == null) {
        setInterpolator(new AccelerateInterpolator());
      }
    }
  }
}
