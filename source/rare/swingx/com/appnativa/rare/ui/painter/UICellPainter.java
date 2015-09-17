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

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPaint;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * An object that can paint a rectangular cell
 *
 * @author Don DeCoteau
 */
public class UICellPainter extends aUICellPainter {

  /** a cell painter that does nothing */
  public static UICellPainter NULL_CELLPAINTER = new UICellPainter() {
    @Override
    public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {}
  };

  /**
   * Creates a new instance
   */
  public UICellPainter() {}

  @Override
  public void paint(Component c, Graphics g, boolean hasValue) {
    paint(c, (Graphics2D) g, c.getX(), c.getY(), c.getWidth(), c.getHeight(), hasValue, iPainter.UNKNOWN);
  }

  @Override
  public void paint(Component c, Graphics2D g, int x, int y, int width, int height, boolean hasValue, int orientation) {
    SwingGraphics sg = new SwingGraphics(g, c);

    paint(sg, x, y, width, height, orientation);
  }

  public iPlatformPaint getPaint(float x, float y, float width, float height) {
    return null;
  }
}
