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

package com.appnativa.rare.platform.swing.ui.view.spinner;

import com.appnativa.rare.platform.swing.ui.view.JPanelEx;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.aSpinnerComponent;

import java.awt.Graphics;

public class SpinnerView extends JPanelEx {
  UIRectangle rect;

  public SpinnerView() {
    super();
  }

  @Override
  protected void layoutContainerEx(int width, int height) {
    ((aSpinnerComponent) getComponentEx()).layout(width, height);
  }

  @Override
  protected void paintChildren(Graphics g) {
    aSpinnerComponent sp = (aSpinnerComponent) getComponentEx();

    if (rect == null) {
      rect = new UIRectangle();
    }

    rect.setBounds(0, 0, getWidth(), getHeight());
    sp.paintButtons(graphics, rect);
    super.paintChildren(g);
  }
}
