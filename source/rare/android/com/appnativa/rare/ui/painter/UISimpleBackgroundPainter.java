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

package com.appnativa.rare.ui.painter;

import android.content.res.Configuration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import android.view.View;

import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.view.BackgroundDrawable;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.iPlatformPaint;

/**
 *
 * @author Don DeCoteau
 */
public class UISimpleBackgroundPainter extends aUIPlatformPainter implements iBackgroundPainter {
  public static final UISimpleBackgroundPainter NULL_BGPAINTER = new UISimpleBackgroundPainter(new UIColor(0, 0, 0, 0));
  Paint                                         paint;
  protected Drawable                            drawable;
  private Configuration                         lastConfiguration;
  private Rect                                  paintRect;
  private int                                   resID;
  private UIColor                               backgroundColor;

  public UISimpleBackgroundPainter() {
    this.drawable = NullDrawable.getInstance();
  }

  public UISimpleBackgroundPainter(UIColor color) {
    setBackgroundColor(color);
  }

  public UISimpleBackgroundPainter(Drawable drawable, int resid) {
    this.drawable = drawable;
    this.resID    = resid;

    if ((resID > 0) && (drawable != null) && (drawable.getChangingConfigurations() != 0)) {
      lastConfiguration = AppContext.getContext().getConfiguration();
    }
  }

  public void paint(View c, Canvas g, float x, float y, float width, float height, int orientation) {
    paintEx(c, g, x, y, width, height, orientation);
  }

  public void setBackgroundColor(UIColor bg) {
    if (bg == null) {
      backgroundColor = ColorUtils.NULL_COLOR;

      return;
    }

    backgroundColor = bg;

    if (drawable instanceof BackgroundDrawable) {
      ((BackgroundDrawable) drawable).setColor(bg);
    }
  }

  public Drawable getDrawable(View view) {
    if (drawable == null) {
      drawable = new BackgroundDrawable(backgroundColor);
    } else if ((lastConfiguration != null) && (AppContext.getContext().getConfiguration() != lastConfiguration)) {
      drawable = UIImage.reloadDrawable(resID, drawable);
    }

    return drawable;
  }

  public iPlatformPaint getPaint(float width, float height) {
    return backgroundColor;
  }

  public boolean isGradientPaintEnabled() {
    return false;
  }

  public boolean isSingleColorPainter() {
    return true;
  }

  protected void paintEx(View v, Canvas g, float x, float y, float width, float height, int orientation) {
    if (backgroundColor != null) {
      if (backgroundColor == ColorUtils.NULL_COLOR) {
        return;
      }

      if (paint == null) {
        paint = new Paint();
        paint.setStyle(Style.FILL);
        paint.setColor(backgroundColor.getColor());
      }

      if (v != null) {
        paint.setColor(backgroundColor.getColor(v.getDrawableState()));
      }

      g.drawRect(x, y, x + width, y + height, paint);
    } else if (drawable != null) {
      Rect r = drawable.getBounds();

      width  += x;
      height += y;

      if ((r.left != x) || (r.top != y) || (r.right != width) || (r.bottom != height)) {
        drawable.setBounds((int) x, (int) y, (int) width, (int) height);
      }

      drawable.draw(g);
    }
  }

  /**
   * Gets the paint rectangle for the component
   *
   * @param c
   *          the component
   * @param rect
   *          the rectangle to user or null
   * @return the paint rectangle for the component
   */
  protected final Rect getRect(View c, Rect rect) {
    if (rect == null) {
      if (paintRect == null) {
        paintRect = new Rect(0, 0, c.getWidth(), c.getHeight());
      } else {
        paintRect.set(0, 0, c.getWidth(), c.getHeight());
      }

      rect = paintRect;
    }

    return rect;
  }

  public UIColor getBackgroundColor() {
    return backgroundColor;
  }
}
