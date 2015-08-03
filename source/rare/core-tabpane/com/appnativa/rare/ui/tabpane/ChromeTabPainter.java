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

import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.iPlatformPath;

public class ChromeTabPainter extends BasicTabPainter {
  public ChromeTabPainter() {
    super(ScreenUtils.PLATFORM_PIXELS_6);
    overlapOffset = ScreenUtils.platformPixels(30);
    padding       = ScreenUtils.PLATFORM_PIXELS_4;
    setStartOffset(ScreenUtils.PLATFORM_PIXELS_4);
    cornerSize       = ScreenUtils.PLATFORM_PIXELS_6;
    textInsets.left  = 0;
    textInsets.right = ScreenUtils.platformPixels(16);
  }

  @Override
  protected void updateShape(iPlatformPath path, float width, float height, float size) {
    if (path == null) {
      return;
    }

    path.reset();
    path.moveTo(0, height);
    path.lineTo(1, height);
    path.cubicTo(size * 2, height, size * 2, size * 2, size * 4, 0);
    path.lineTo(width - size * 4, 0);
    path.cubicTo(width - size * 2, size * 2, width - size * 2, height - size, width - size, height);
    path.lineTo(width, height);
    path.moveTo(width, height);
    path.close();
  }
}
