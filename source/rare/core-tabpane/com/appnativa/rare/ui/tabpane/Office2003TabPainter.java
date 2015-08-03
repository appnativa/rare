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
/*-[
#import "com/appnativa/rare/ui/UIInsets.h"
 ]-*/

public class Office2003TabPainter extends BasicTabPainter {
  public Office2003TabPainter() {
    super(ScreenUtils.platformPixels(6));
    overlapOffset = ScreenUtils.platformPixels(16);
    setStartOffset(ScreenUtils.PLATFORM_PIXELS_4);
    cornerSize       = ScreenUtils.PLATFORM_PIXELS_6;
    padding          = ScreenUtils.PLATFORM_PIXELS_4;
    textInsets.left  = ScreenUtils.PLATFORM_PIXELS_5;
    textInsets.right = ScreenUtils.PLATFORM_PIXELS_8;
  }

  @Override
  protected void updateShape(iPlatformPath path, float width, float height, float size) {
    if (path == null) {
      return;
    }

    path.reset();
    path.moveTo(0, height);
//    if (!full) {
//      path.lineTo(0, height - 4);
//    }
//
    path.cubicTo(size * 2, height, size, size * 2, size * 4, 0);
    path.lineTo(width - size, 0);
    path.quadTo(width, 0, width, size);
    path.lineTo(width, height);
    path.moveTo(width, height);
    path.close();
  }
}
