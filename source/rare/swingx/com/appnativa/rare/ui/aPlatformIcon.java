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

package com.appnativa.rare.ui;

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;

import java.awt.Component;
import java.awt.Graphics;

public abstract class aPlatformIcon implements iPlatformIcon, Cloneable {
  public aPlatformIcon() {}

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public void paintIcon(Component c, Graphics g, int x, int y) {
    SwingGraphics graphics = SwingGraphics.fromGraphics(g, c, null);

    paint(graphics, x, y, getIconWidth(), getIconHeight());
    graphics.clear();
  }
}
