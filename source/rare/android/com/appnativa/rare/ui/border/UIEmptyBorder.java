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

import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iPlatformBorder;

/**
 *
 * @author Don DeCoteau
 */
public class UIEmptyBorder extends NullDrawable {
  private boolean adjustForOrientation = false;

  public UIEmptyBorder(float size) {
    super(0, 0);
    setInsets(size, size, size, size);
  }

  public UIEmptyBorder(UIInsets insets) {
    this(insets, false);
  }

  public UIEmptyBorder(UIInsets insets, boolean adjustForOrientation) {
    super(0, 0);

    if (insets != null) {
      setInsets(insets);
    }

    this.adjustForOrientation = adjustForOrientation;
  }

  public UIEmptyBorder(float top, float right, float bottom, float left) {
    this(top, right, bottom, left, false);
  }

  public UIEmptyBorder(float top, float right, float bottom, float left, boolean adjustForOrientation) {
    super(0, 0);
    setInsets(new UIInsets(top, right, bottom, left));
    this.adjustForOrientation = adjustForOrientation;
  }

  /**
   * @param adjustForOrientation the adjustForOrientation to set
   */
  public void setAdjustForOrientation(boolean adjustForOrientation) {
    this.adjustForOrientation = adjustForOrientation;
  }

  public void setInsets(UIInsets borderInsets) {
    if (this.insets == null) {
      this.insets = new UIInsets(borderInsets);
    } else {
      insets.set(borderInsets);
    }
  }

  public void setInsets(float top, float right, float bottom, float left) {
    if (this.insets == null) {
      this.insets = new UIInsets(top, right, bottom, left);
    } else {
      insets.set(top, right, bottom, left);
    }
  }

  public UIInsets getBorderInsetsEx(UIInsets insets) {
    if (insets == null) {
      insets = new UIInsets();
    } else {
      insets.set(0, 0, 0, 0);
    }

    return insets;
  }

  /**
   * @return the adjustForOrientation
   */
  public boolean isAdjustForOrientation() {
    return adjustForOrientation;
  }

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
}
