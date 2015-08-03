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

import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iPlatformGraphics;

public abstract class aStatusBarBorder extends aUIPlatformBorder {
  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean end) {
    if (end) {
      g.setColor(ColorUtils.getColor("Rare.backgroundDkShadow"));
      g.drawLine(x, y + 2, x + width, y + 2);
      g.setColor(ColorUtils.getColor("Rare.backgroundShadow"));
      g.drawLine(x, y + 3, x + width, y + 3);
      g.drawLine(x, y + height - 1, x + width, y + height - 1);
      g.setColor(ColorUtils.getColor("Rare.backgroundLtShadow"));
      g.drawLine(x, y + 4, x + width, y + 4);
      g.drawLine(x, y + height - 2, x + width, y + height - 2);
      g.setColor(ColorUtils.getColor("Rare.backgroundHighlight"));
      g.drawLine(x, y + 5, x + width, y + 5);
      g.drawLine(x, y + height - 3, x + width, y + height - 3);
    }
  }

  @Override
  public UIInsets getBorderInsets(UIInsets insets) {
    if (insets != null) {
      insets.set(6, 0, 2, 0);

      return insets;
    } else {
      return new UIInsets(6, 0, 2, 0);
    }
  }
}
