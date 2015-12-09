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

package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformShape;

public abstract class aUICompoundBorder extends aUIPlatformBorder {
  protected final iPlatformBorder borders[];

  public aUICompoundBorder() {
    this(new iPlatformBorder[2]);
  }

  public aUICompoundBorder(iPlatformBorder[] borders) {
    this.borders = borders;
  }

  public aUICompoundBorder(iPlatformBorder outsideBorder, iPlatformBorder insideBorder) {
    this();
    setOutsideBorder(outsideBorder);
    setInsideBorder(insideBorder);
  }

  @Override
  public void clip(iPlatformGraphics g, float x, float y, float width, float height) {
    paintOrClip(g, x, y, width, height, false, false);
  }

  @Override
  public boolean clipsContents() {
    for (int i = 0; i < borders.length; i++) {
      if ((borders[i] != null) && borders[i].clipsContents()) {
        return true;
      }
    }

    return false;
  }

  public void updateModCount() {
    modCount++;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean end) {
    if (isPaintLast() != end) {
      return;
    }

    paintOrClip(g, x, y, width, height, true, end);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("UICompoundBorder {\n");

    int n = 0;

    for (int i = 0; i < borders.length; i++) {
      if (borders[i] != null) {
        sb.append(borders[i].toString());
        n++;
      }

      if (n > 1) {
        sb.append("\n");
      }
    }

    sb.append("}");

    return sb.toString();
  }

  public void setInsideBorder(iPlatformBorder b) {
    borders[borders.length - 1] = b;
    modCount++;
  }

  public void setOutsideBorder(iPlatformBorder b) {
    borders[0] = b;
    modCount++;
  }

  @Override
  public void setPadForArc(boolean pad) {
    if (borders[1] != null) {
      borders[1].setPadForArc(pad);
    }

    if (borders[0] != null) {
      borders[0].setPadForArc(pad);
    }

    modCount++;
  }

  @Override
  public float getArcHeight() {
    if ((borders[1] != null) &&!borders[1].isRectangular()) {
      return borders[1].getArcHeight();
    }

    if (borders[0] != null) {
      return borders[0].getArcHeight();
    }

    return 0;
  }

  @Override
  public float getArcWidth() {
    if ((borders[1] != null) &&!borders[1].isRectangular()) {
      return borders[1].getArcWidth();
    }

    if (borders[0] != null) {
      return borders[0].getArcWidth();
    }

    return 0;
  }

  @Override
  public UIInsets getBorderInsets(UIInsets insets) {
    int       left   = 0;
    int       right  = 0;
    int       top    = 0;
    int       bottom = 0;
    final int len    = borders.length;

    for (int i = 0; i < len; i++) {
      if (borders[i] != null) {
        insets = borders[i].getBorderInsets(insets);
        left   += insets.left;
        top    += insets.top;
        right  += insets.right;
        bottom += insets.bottom;
      }
    }

    if (insets == null) {
      insets = new UIInsets(top, right, bottom, left);
    } else {
      insets.set(top, right, bottom, left);
    }

    return insets;
  }

  @Override
  public UIInsets getBorderInsetsEx(UIInsets insets) {
    int             left   = 0;
    int             right  = 0;
    int             top    = 0;
    int             bottom = 0;
    final int       len    = borders.length;
    iPlatformBorder b;
    iPlatformBorder lb = null;

    for (int i = 0; i < len; i++) {
      b = borders[i];

      if (b != null) {
        if (lb == null) {
          lb = b;

          continue;
        }

        insets = lb.getBorderInsets(insets);
        left   += insets.left;
        top    += insets.top;
        right  += insets.right;
        bottom += insets.bottom;
        lb     = b;
      }
    }

    if (lb != null) {    //for compound borders we only call the 'ex' method on the last border
      insets = lb.getBorderInsetsEx(insets);
      left   += insets.left;
      top    += insets.top;
      right  += insets.right;
      bottom += insets.bottom;
    }

    if (insets == null) {
      insets = new UIInsets(top, right, bottom, left);
    } else {
      insets.set(top, right, bottom, left);
    }

    return insets;
  }

  public iPlatformBorder getInsideBorder() {
    return borders[borders.length - 1];
  }

  public iPlatformBorder getOutsideBorder() {
    return borders[0];
  }

  @Override
  public iPlatformShape getShape(iPlatformGraphics g, float x, float y, float width, float height) {
    final int len        = borders.length;
    UIInsets  nextInsets = null;
    float     px, py, pw, ph;

    px = x;
    py = y;
    pw = width;
    ph = height;

    iPlatformShape shape;

    for (int i = 0; i < len; i++) {
      iPlatformBorder b = borders[i];

      if (b != null) {
        shape = b.getShape(g, px, py, pw, ph);

        if (shape != null) {
          return shape;
        }

        nextInsets = b.getBorderInsets(nextInsets);
        px         += nextInsets.left;
        py         += nextInsets.top;
        pw         = pw - nextInsets.right - nextInsets.left;
        ph         = ph - nextInsets.bottom - nextInsets.top;
      }
    }

    return null;
  }

  @Override
  public boolean isPadForArc() {
    return (getInsideBorder() == null)
           ? getOutsideBorder().isPadForArc()
           : getInsideBorder().isPadForArc();
  }

  @Override
  public boolean isPaintLast() {
    for (int i = 0; i < borders.length; i++) {
      if ((borders[i] != null) &&!borders[i].isPaintLast()) {
        return false;
      }
    }

    return true;
  }

  @Override
  public boolean isRectangular() {
    for (int i = 0; i < borders.length; i++) {
      if ((borders[i] != null) &&!borders[i].isRectangular()) {
        return false;
      }
    }

    return true;
  }

  @Override
  public boolean isFocusAware() {
    for (int i = 0; i < borders.length; i++) {
      if ((borders[i] != null) &&!borders[i].isFocusAware()) {
        return true;
      }
    }

    return false;
  }

  protected void paintOrClip(iPlatformGraphics g, float x, float y, float width, float height, boolean paint,
                             boolean end) {
    final int len        = borders.length;
    UIInsets  nextInsets = null;
    float     px, py, pw, ph;

    px = x;
    py = y;
    pw = width;
    ph = height;

    for (int i = 0; i < len; i++) {
      iPlatformBorder b = borders[i];

      if (b != null) {
        if (paint) {
          b.paint(g, px, py, pw, ph, b.isPaintLast());
        } else {
          b.clip(g, px, py, pw, ph);
        }

        nextInsets = b.getBorderInsets(nextInsets);
        px         += nextInsets.left;
        py         += nextInsets.top;
        pw         = pw - nextInsets.right - nextInsets.left;
        ph         = ph - nextInsets.bottom - nextInsets.top;
      }
    }
  }
}
