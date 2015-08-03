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

package com.appnativa.rare.ui;

import java.util.Arrays;

/**
 *
 * @author Don DeCoteau
 */
public class UIStroke implements Cloneable {
  public static final UIStroke SOLID_STROKE  = new UIStroke(ScreenUtils.PLATFORM_PIXELS_1);
  public static final UIStroke DOTTED_STROKE = new UIStroke(ScreenUtils.PLATFORM_PIXELS_1, Cap.BUTT, Join.MITER,
                                                 new float[] { ScreenUtils.PLATFORM_PIXELS_1,
          ScreenUtils.PLATFORM_PIXELS_1 }, 0f);
  public static final UIStroke DASHED_STROKE = new UIStroke(ScreenUtils.PLATFORM_PIXELS_1, Cap.BUTT, Join.MITER,
                                                 new float[] { ScreenUtils.PLATFORM_PIXELS_2,
          ScreenUtils.PLATFORM_PIXELS_2 }, 0f);
  public static final UIStroke HALF_SOLID_STROKE  = new UIStroke((float) ScreenUtils.PLATFORM_PIXELS_1 / 2f);
  public static final UIStroke HALF_DOTTED_STROKE = new UIStroke((float) ScreenUtils.PLATFORM_PIXELS_1 / 2f, Cap.BUTT,
                                                      Join.MITER, new float[] { ScreenUtils.PLATFORM_PIXELS_1,
          ScreenUtils.PLATFORM_PIXELS_1 }, 0f);
  public static final UIStroke HALF_DASHED_STROKE = new UIStroke((float) ScreenUtils.PLATFORM_PIXELS_1 / 2f, Cap.BUTT,
                                                      Join.MITER, new float[] { ScreenUtils.PLATFORM_PIXELS_2,
          ScreenUtils.PLATFORM_PIXELS_2 }, 0f);
  public Cap   cap        = Cap.BUTT;
  public Join  join       = Join.MITER;
  public float miterLimit = ScreenUtils.PLATFORM_PIXELS_10;
  public float width      = ScreenUtils.PLATFORM_PIXELS_1;
  public float dashIntervals[];
  public float dashPhase;

  public static enum Cap { BUTT, ROUND, SQUARE }

  public static enum Join { BEVEL, ROUND, MITER }

  public UIStroke() {}

  public UIStroke(float width) {
    this.width = width;
  }

  public UIStroke(float width, Cap cap, Join join) {
    this(width, cap, join, ScreenUtils.PLATFORM_PIXELS_10);
  }

  public UIStroke(float width, float[] dashIntervals, float dashhase) {
    this.width = width;
    setDashInterval(dashIntervals, dashhase);
  }

  public UIStroke(float width, Cap cap, Join join, float miterlimit) {
    this.width      = width;
    this.cap        = cap;
    this.join       = join;
    this.miterLimit = miterlimit;
  }

  public UIStroke(float width, Cap cap, Join join, float[] dashIntervals, float dashhase) {
    this(width, cap, join, ScreenUtils.PLATFORM_PIXELS_10);
    setDashInterval(dashIntervals, dashhase);
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch(CloneNotSupportedException e) {
      throw new InternalError("should not happen");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof UIStroke) {
      UIStroke s = (UIStroke) o;

      if (!isEqualButForWidth(s)) {
        return false;
      }

      return s.width == width;
    }

    return false;
  }

  public void setDashInterval(float[] intervals, float phase) {
    this.dashIntervals = intervals;
    this.dashPhase     = phase;
  }

  public void setWidth(float width) {
    this.width = width;
  }

  public static UIStroke getStroke(String style) {
    if ("dotted".equals(style)) {
      return UIStroke.DOTTED_STROKE;
    }

    if ("dashed".equals(style)) {
      return UIStroke.DASHED_STROKE;
    }

    return UIStroke.SOLID_STROKE;
  }

  public boolean isEqualButForWidth(UIStroke stroke) {
    if (dashPhase != stroke.dashPhase) {
      return false;
    }

    if (cap != stroke.cap) {
      return false;
    }

    if (join != stroke.join) {
      return false;
    }

    if (miterLimit != stroke.miterLimit) {
      return false;
    }

    if (dashIntervals != stroke.dashIntervals) {
      if ((dashIntervals != null) && (stroke.dashIntervals != null)) {
        if (Arrays.equals(dashIntervals, stroke.dashIntervals)) {
          return true;
        }
      }

      return false;
    }

    return true;
  }
}
