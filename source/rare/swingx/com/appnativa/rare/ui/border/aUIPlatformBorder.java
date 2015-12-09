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

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;

import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JComponent;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aUIPlatformBorder extends aUIBorder implements iPlatformBorder {
  @Override
  public void paintBorder(java.awt.Component c, Graphics g, int x, int y, int width, int height) {
    iPlatformComponent pc = Component.fromView((JComponent) c);
    iPlatformGraphics  pg = (pc == null)
                            ? new SwingGraphics(g, c)
                            : pc.graphicsWrap(g);

    paint(pg, x, y, width, height, false);
    paint(pg, x, y, width, height, true);

    if (pc == null) {
      ((SwingGraphics) pg).clear();
    } else {
      pc.graphicsUnwrap(pg);
    }
  }

  @Override
  public Insets getBorderInsets(java.awt.Component c) {
    return SwingHelper.setInsets(null, getBorderInsets((UIInsets) null));
  }

  @Override
  public boolean isBorderOpaque() {
    return false;
  }
}
