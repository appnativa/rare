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

package com.appnativa.rare.ui.tabpane;

import com.appnativa.rare.platform.PlatformHelper;

public class BasicTabPainter extends aBasicTabPainter {
  public BasicTabPainter(int cornerSize) {
    super(cornerSize);
  }

  @Override
  protected TabShape createTabShape() {
    return new TabShape(PlatformHelper.createPath(), PlatformHelper.createPath());
  }

//  protected void paintLine(iPlatformGraphics g, int x, int y, int width, int height) {
//    g.setColor((selectedTabBorderColor == null)
//               ? tabBorderColor
//               : selectedTabBorderColor);
//
//    if (isHorizontal()) {
//      g.drawLine(x, y + height - 1f, x + width, y + height - 1f);
//    } else if (position == Location.LEFT) {
//      g.drawLine(x, y + width - 1f, x + height, y + width - 1f);
//    } else {
//      g.drawLine(x, y + width, x + height, y + width);
//    }
//  }
  @Override
  protected boolean isVerticalWhenFindingTabs() {
    return false;
  }
}
