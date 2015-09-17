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

package com.appnativa.rare.platform.swing.ui.view;

import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.aLineHelper.Line;

public class SeparatorView extends LineView {
  boolean forToolbar;

  public SeparatorView(boolean forToolbar) {
    super();
    this.forToolbar = forToolbar;

    Line l = getLineHelper().createLine();

    l.setLeftOffset(2);
    l.setRightOffset(2);
    getLineHelper().addLine(l);
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    super.setBounds(x, y, width, height);

    if (!forToolbar) {
      setHorizontal(width > height);
    }
  }

  public void setHorizontal(boolean horizontal) {
    getLineHelper().setHorizontal(horizontal);
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    size.width  = 8;
    size.height = 8;
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxView) {
    size.width  = 8;
    size.height = 8;
  }
}
