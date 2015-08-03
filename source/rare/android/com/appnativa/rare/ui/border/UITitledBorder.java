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

import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformGraphics;

public class UITitledBorder extends aUITitledBorder {
  public UITitledBorder(iPlatformBorder border, String title, int titleJustification, int titlePosition,
                        UIFont titleFont, UIColor titleColor) {
    super(border, title, titleJustification, titlePosition, titleFont, titleColor);
  }

  public UITitledBorder(iPlatformBorder border, String title, int titleJustification, int titlePosition,
                        UIFont titleFont) {
    super(border, title, titleJustification, titlePosition, titleFont);
  }

  public UITitledBorder(iPlatformBorder border, String title, int titleJustification, int titlePosition) {
    super(border, title, titleJustification, titlePosition);
  }

  public UITitledBorder(iPlatformBorder border, String title) {
    super(border, title);
  }

  public UITitledBorder(iPlatformBorder border) {
    super(border);
  }

  public UITitledBorder(String title) {
    super(title);
  }

  protected void paintIinnerBorder(iPlatformBorder b, iPlatformGraphics g, float x, float y, float width, float height,
                                   boolean last) {
    b.paint(g, x, y, width, height, last);
  }
}
