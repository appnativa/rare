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

public abstract class aUIPlatformPainter extends aUIPainter {
  public aUIPlatformPainter() {
    super();
  }

  @Override
  public boolean canUseLayer() {
    return false;
  }

  @Override
  public boolean canUseMainLayer() {
    return false;
  }
  
  public void updateModCount() {
    modCount++;
  }

  public void fill(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {
    paint(g, x, y, width, height, orientation);
  }
 }
