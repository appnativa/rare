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
import com.appnativa.rare.platform.swing.ui.util.SwingPaint;
import com.appnativa.rare.ui.iPlatformPaint;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

public abstract class aUIPlatformPainter extends aUIPainter {
  protected SwingGraphics graphics;
  protected SwingPaint    platformPaint;

  public aUIPlatformPainter() {
    super();
  }

  @Override
  public void paint(Component c, Graphics g, boolean hasValue) {
    paint(c, (Graphics2D) g, 0, 0, c.getWidth(), c.getHeight(), hasValue, iPainter.UNKNOWN);
  }

  @Override
  public void paint(Component c, Graphics2D g, int x, int y, int width, int height, boolean hasValue, int orientation) {
    graphics = SwingGraphics.fromGraphics(g, c, graphics);
    paint(graphics, x, y, width, height, orientation);
    graphics.clear();
  }

  public Paint getPaintEx(float width, float height) {
    return null;
  }

  @Override
  public iPlatformPaint getPaint(float width, float height) {
    Paint p = getPaintEx(width, height);

    if (p == null) {
      return null;
    }

    if (platformPaint == null) {
      platformPaint = new SwingPaint(p);
    } else {
      platformPaint.setPaint(p);
    }

    return platformPaint;
  }
}
