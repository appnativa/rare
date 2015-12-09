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

package com.appnativa.rare.platform.android.ui.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;

/**
 *
 * @author Don DeCoteau
 */
public class GradientDrawableEx extends GradientDrawable {
  float       magnitude   = 1.0f;
  Orientation orientation = Orientation.TOP_BOTTOM;
  boolean     centered;
  int         height;
  boolean     radial;
  boolean     radiusSet;
  int         width;

  public GradientDrawableEx() {
    super();
  }

  public GradientDrawableEx(Orientation orientation, int[] colors) {
    super(orientation, colors);
    this.orientation = orientation;
  }

  @Override
  public void draw(Canvas canvas) {
    Rect bounds = getBounds();

    if ((!radiusSet && radial && (width != bounds.width())) || (height != bounds.height())) {
      width  = bounds.width();
      height = bounds.height();

      if (centered) {
        float hm = height * magnitude / 2;
        float wm = width * magnitude / 2;

        super.setGradientRadius((hm > wm)
                                ? wm
                                : hm);
      } else {
        switch(orientation) {
          case TOP_BOTTOM :
          case BOTTOM_TOP :
            super.setGradientRadius(height * magnitude);

            break;

          case BL_TR :
          case TL_BR :
          case TR_BL :
          case BR_TL :
            final float w2 = width / 2;
            final float h2 = height / 2;

            super.setGradientRadius((float) Math.sqrt((w2 * w2) + (h2 * h2)) * magnitude);

            break;

          default :
            super.setGradientRadius(width * magnitude);
        }
      }
    }

    super.draw(canvas);
  }

  public void setCentered(boolean centered) {
    this.centered = centered;
  }

  @Override
  public void setGradientRadius(float gradientRadius) {
    super.setGradientRadius(gradientRadius);
    radiusSet = true;
  }

  @Override
  public void setGradientType(int gradient) {
    super.setGradientType(gradient);
    radial = gradient == RADIAL_GRADIENT;
  }

  public void setMagnitude(int magnitude) {
    this.magnitude = (magnitude) / 100f;
    setUseLevel(true);
    setLevel(magnitude * 100);
  }
}
