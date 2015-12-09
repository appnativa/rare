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

import com.appnativa.rare.platform.apple.ui.view.aView;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.iPaintedButton;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;

/**
 *
 * @author Don DeCoteau
 */
public class UIComponentPainter extends aUIComponentPainter {
  public UIComponentPainter() {}

  @Override
  public void updateForState(aView v) {
    PainterHolder ph = painterHolder;

    if (ph != null) {
      final boolean              selected  = v.isSelected();
      final boolean              pressed   = v.isPressed();
      final boolean              enabled   = v.isEnabled();
      final boolean              mouseOver = v.isMouseOver();
      iPaintedButton.ButtonState state     = Utils.getState(enabled, pressed, selected, mouseOver);
      iPlatformBorder            b         = ph.getBorder(state);

      if (b == null) {
        b = v.isUsePainterBorder()
            ? border
            : null;
      }

      iBackgroundPainter bp = ph.getBackgroundPainter(state);
      UIColor            bg = ph.getBackground(state);

      if (bp == null) {
        bp = backgroundPainter;
      }
      v.setBorder(b);
      if(bp==null && bg!=null) {
        v.setBackgroundColorEx(bg);
      }
      v.setBackgroundPainter(bp);
    }
  }

  public static boolean isOkToPaint(iPlatformComponent c, iPlatformBorder border) {
    if (c == null) {
      return true;
    }

    if (!c.getView().letComponentPainterPaint(border)) {
      return false;
    }

    return !border.canUseMainLayer() &&!border.usesPath();
  }

  public static boolean isOkToPaint(iPlatformComponent c, iPlatformPainter p) {
    return isOkToPaint(c, p, true, false);
  }

  public static boolean isOkToPaint(iPlatformComponent c, iPlatformPainter p, boolean hasValue, boolean isOverlay) {
    if (c == null) {
      return true;
    }

    if (!aUIComponentPainter.isOkToPaint(c, p, hasValue, isOverlay)) {
      return false;
    }

    if (c.getView().letComponentPainterPaint(p)) {
      return true;
    }

    if (p.canUseLayer() && c.getView().isUseMainLayerForPainter()) {
      return false;
    }

    return true;
  }

  @Override
  public UIColor getBackgroundColorEx() {
    return getBackgroundColor();
  }
}
