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
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.aLineHelper;

import java.awt.Graphics;

public class LineView extends JPanelEx {
  private LineHelper lineHelper;

  public LineView() {
    lineHelper = new LineHelper(true);
    setOpaque(false);
  }

  public aLineHelper getLineHelper() {
    return lineHelper;
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    int th = UIScreen.snapToSize(lineHelper.getThickness());

    size.width  = th;
    size.height = th;
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxWidth) {
    int th = UIScreen.snapToSize(lineHelper.getThickness());

    size.width  = th;
    size.height = th;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    lineHelper.paint(graphics, 0, 0, getWidth(), getHeight());
  }

  class LineHelper extends aLineHelper {
    public LineHelper(boolean horizontal) {
      super(horizontal);
    }

    @Override
    protected void thicknessRecalculated() {}
  }
}
