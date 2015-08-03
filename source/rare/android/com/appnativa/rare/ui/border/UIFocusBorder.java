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

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.UIColor;

/**
 *
 * @author Don DeCoteau
 */
public class UIFocusBorder extends UILineBorder {
  protected UIColor nofocusColor;

  public UIFocusBorder() {
    super(Platform.getUIDefaults().getColor("Rare.focusBorderColor"));
  }

  public UIFocusBorder(UIColor color) {
    super(color);
  }

  public UIFocusBorder(UIColor color, float thickness, int arc) {
    super(color, thickness, arc);
  }

  public UIFocusBorder(UIColor color, float thickness) {
    super(color, thickness);
  }

  public UIFocusBorder(UIColor color, float thickness, int arcWidth, int arcHeight, UIColor nofocus) {
    super(color, thickness, arcWidth, arcHeight);
    nofocusColor = nofocus;

    if ((nofocus != null) && (color == null)) {
      lineColor = Platform.getUIDefaults().getColor("Rare.focusBorderColor");
    }
  }

  public void setFocused(boolean focused) {
    if (focused) {
      if (lineColor == null) {
        lineColor = Platform.getUIDefaults().getColor("Rare.focusBorderColor");
      }
    } else if (nofocusColor != null) {
      UIColor lc = lineColor;

      lineColor = nofocusColor;
      lineColor = lc;
    }
  }

  public static UIFocusBorder createBorder(UIColor color, int thickness, int arcWidth, int arcHeight, UIColor nofocus) {
    return new UIFocusBorder(color, thickness, arcWidth, arcHeight, nofocus);
  }
}
