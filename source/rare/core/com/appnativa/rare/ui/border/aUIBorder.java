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
import com.appnativa.rare.ui.iPlatformPath;
import com.appnativa.rare.ui.iPlatformShape;

import java.util.Map;

public abstract class aUIBorder implements Cloneable, iPlatformBorder {
  protected UIInsets clipInsets;
  protected int      modCount;
  protected boolean  padForArc;

  public aUIBorder() {}

  @Override
  public void clip(iPlatformGraphics g, float x, float y, float width, float height) {
    if (clipInsets == null) {
      clipInsets = new UIInsets();
    }

    UIInsets in = getBorderInsets(clipInsets);

    g.clipRect(x + in.left, y + in.top, width - in.right - in.left, height - in.bottom - in.top);
  }

  @Override
  public boolean clipsContents() {
    return false;
  }

  @Override
  public Object clone() {
    try {
      aUIBorder b = (aUIBorder) super.clone();

      b.clipInsets = null;

      return b;
    } catch(CloneNotSupportedException e) {
      throw new InternalError();
    }
  }

  @Override
  public void handleCustomProperties(Map map) {}

  @Override
  public abstract void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean end);

  @Override
  public iPlatformPath getPath(iPlatformPath p, float x, float y, float width, float height, boolean forClip) {
    return null;
  }

  public String toCSS() {
    return "";
  }

  public StringBuilder toCSS(StringBuilder sb) {
    return sb;
  }

  @Override
  public void setPadForArc(boolean pad) {
    padForArc = pad;
  }

  @Override
  public float getArcHeight() {
    return 0;
  }

  @Override
  public float getArcWidth() {
    return 0;
  }

  @Override
  public UIInsets getBorderInsets(UIInsets insets) {
    if (insets != null) {
      insets.top    = 0;
      insets.left   = 0;
      insets.right  = 0;
      insets.bottom = 0;

      return insets;
    } else {
      return new UIInsets(0, 0, 0, 0);
    }
  }

  @Override
  public UIInsets getBorderInsetsEx(UIInsets insets) {
    return getBorderInsets(insets);
  }

  @Override
  public int getModCount() {
    return modCount;
  }

  @Override
  public iPlatformShape getShape(iPlatformGraphics g, float x, float y, float width, float height) {
    return null;
  }

  @Override
  public boolean isPadForArc() {
    return padForArc;
  }

  @Override
  public boolean isPaintLast() {
    return true;
  }

  @Override
  public boolean isRectangular() {
    return true;
  }
}
