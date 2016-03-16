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
import com.appnativa.rare.ui.iPlatformPath;

/**
 *
 * @author Don DeCoteau
 */
public class UICompoundBorder extends aUICompoundBorder {
  public UICompoundBorder() {
    super();
  }

  public UICompoundBorder(iPlatformBorder[] borders) {
    super(borders);
  }

  public UICompoundBorder(iPlatformBorder outsideBorder, iPlatformBorder insideBorder) {
    super(outsideBorder, insideBorder);
  }

  @Override
  public boolean canUseMainLayer() {
    return usesPath();
  }

  @Override
  public boolean requiresPanel() {
    int len = borders.length;

    while(len-- > 0) {
      if ((borders[len] != null) && borders[len].requiresPanel()) {
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean usesPath() {
    int len = borders.length;

    if (len < 3) {
      iPlatformBorder b1 = (len > 0)
                           ? borders[0]
                           : null;;
      iPlatformBorder b2 = (len > 1)
                           ? borders[1]
                           : null;

      if ((b1 != null) && b1.usesPath()) {
        return (b2 == null) || (b2 instanceof UIEmptyBorder);
      }
    }

    return false;
  }

  @Override
  public iPlatformPath getPath(iPlatformPath p, float x, float y, float width, float height, boolean forClip) {
    int len = borders.length;

    while(len-- > 0) {
      if ((borders[len] != null) &&!borders[len].isRectangular()) {
        break;
      }
    }

    if (len < 0) {
      return null;
    }

    UIInsets in = null;

    for (int i = 0; i < len; i++) {
      if (borders[i] != null) {
        in     = borders[i].getBorderInsets(in);
        x      += in.left;
        y      += in.top;
        width  -= (in.left + in.right);
        height -= (in.top + in.bottom);
      }
    }

    return borders[len].getPath(p, x, y, width, height, forClip);
  }

  public void updateModCount() {
    super.updateModCount();

    int len = borders.length;

    while(len-- > 0) {
      if (borders[len] != null) {
        borders[len].updateModCount();
      }
    }
  }
}
