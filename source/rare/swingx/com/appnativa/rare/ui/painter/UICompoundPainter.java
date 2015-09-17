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

import com.appnativa.rare.ui.iPlatformGraphics;

import java.awt.Paint;

/**
 * A compound painter
 *
 * @author Don DeCoteau
 */
public class UICompoundPainter extends aUICompoundPainter {
  public UICompoundPainter(iPlatformPainter firstPainter, iPlatformPainter secondPainter) {
    super(firstPainter, secondPainter);
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {
    if ((firstPainter != null) && firstPainter.isEnabled()) {
      firstPainter.paint(g, x, y, width, height, orientation);
    }

    if ((secondPainter != null) && secondPainter.isEnabled()) {
      secondPainter.paint(g, x, y, width, height, orientation);
    }
  }

  public Paint getPaint() {
    return null;
  }
}
