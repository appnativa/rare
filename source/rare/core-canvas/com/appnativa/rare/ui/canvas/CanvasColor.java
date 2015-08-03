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

package com.appnativa.rare.ui.canvas;

import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.canvas.iContext.iCanvasPaint;
import com.appnativa.rare.ui.iPlatformPaint;

public class CanvasColor implements iCanvasPaint, Cloneable {
  UIColor color;

  public CanvasColor(UIColor color) {
    this.color = color;
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch(CloneNotSupportedException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public iCanvasPaint cloneCopy() {
    return this;
  }

  @Override
  public iPlatformPaint getPaint() {
    return color;
  }

  public UIColor getUIColor() {
    return color;
  }
}
