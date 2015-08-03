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

/**
 *
 *  @author Don DeCoteau
 */
public class UIEmptyBorder extends aUIPlatformBorder {
  private boolean    adjustForOrientation = false;
  protected UIInsets insets               = new UIInsets();

  public UIEmptyBorder(float size) {
    insets.set(size, size, size, size);
  }

  public UIEmptyBorder(UIInsets insets) {
    this(insets, false);
  }

  public UIEmptyBorder(UIInsets insets, boolean adjustForOrientation) {
    if (insets != null) {
      setInsets(insets);
    }

    this.adjustForOrientation = adjustForOrientation;
  }

  public UIEmptyBorder(float top, float right, float bottom, float left) {
    this(top, right, bottom, left, false);
  }

  public UIEmptyBorder(float top, float right, float bottom, float left, boolean adjustForOrientation) {
    setInsets(new UIInsets(top, right, bottom, left));
    this.adjustForOrientation = adjustForOrientation;
  }

  /**
   * @param adjustForOrientation the adjustForOrientation to set
   */
  public void setAdjustForOrientation(boolean adjustForOrientation) {
    this.adjustForOrientation = adjustForOrientation;
    modCount++;
  }

  public void setInsets(UIInsets borderInsets) {
    insets.set(borderInsets);
    modCount++;
  }

  public void setInsets(int top, int right, int bottom, int left) {
    insets.set(top, right, bottom, left);
  }

  /**
   * @return the adjustForOrientation
   */
  public boolean isAdjustForOrientation() {
    return adjustForOrientation;
  }

  @Override
  public boolean isPaintLast() {
    return true;
  }

  public void resetFromRealBorder(iPlatformBorder b) {
    if (b == null) {
      insets.set(0, 0, 0, 0);
    } else {
      insets = b.getBorderInsets(insets);
    }
  }

  @Override
  public void clip(iPlatformGraphics g, float x, float y, float width, float height) {}

  @Override
  public UIInsets getBorderInsets(UIInsets insets) {
    if (insets == null) {
      return new UIInsets(this.insets);
    } else {
      insets.set(this.insets);

      return insets;
    }
  }

  @Override
  public UIInsets getBorderInsetsEx(UIInsets insets) {
    if (insets == null) {
      insets = new UIInsets();
    } else {
      insets.set(0, 0, 0, 0);
    }

    return insets;
  }

  @Override
  public boolean canUseMainLayer() {
    return true;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean end) {}
}
