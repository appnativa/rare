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

package com.appnativa.rare.platform.swing.plaf;

import com.appnativa.rare.ui.painter.iPlatformPainter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 * An extension of basic arrow button that only paints the arrow
 * .
 * @author Don DeCcoteau
 */
public class ArrowButton extends BasicArrowButton {
  iPlatformPainter backgroundPainter;
  private boolean  paintPressed = true;
  private boolean  paintArrows  = true;

  public ArrowButton(int direction) {
    super(direction);
  }

  public ArrowButton(int direction, iPlatformPainter bp) {
    super(direction);
    backgroundPainter = bp;
  }

  public ArrowButton(int direction, Color background, Color shadow, Color darkShadow, Color highlight) {
    super(direction, background, shadow, darkShadow, highlight);
  }

  public ArrowButton(int direction, Color background, Color shadow, Color darkShadow, Color highlight,
                     iPlatformPainter bp) {
    super(direction, background, shadow, darkShadow, highlight);
    backgroundPainter = bp;
  }

  @Override
  public void paint(Graphics g) {
    Color   origColor;
    boolean isPressed, isEnabled;
    int     w, h, size;

    w         = getSize().width;
    h         = getSize().height;
    origColor = g.getColor();
    isPressed = getModel().isPressed();
    isEnabled = isEnabled();

    if (backgroundPainter != null) {
      backgroundPainter.paint(this, (Graphics2D) g, 0, 0, w, h, true, direction);
    }

    g.setColor(getBackground());

    // / Draw the proper Border
    if ((getBorder() != null) &&!(getBorder() instanceof UIResource)) {
      paintBorder(g);
    }

    // If there's no room to draw arrow, bail
    if (!isPaintArrows() || (h < 5) || (w < 5)) {
      return;
    }

    g.setColor(getForeground());

    if (isPressed && isPaintPressed()) {
      g.translate(1, 1);
    }

    // Draw the arrow
    size = Math.min((h - 2) / 3, (w - 2) / 3);
    size = Math.max(size, 2);
    paintTriangle(g, (w - size) / 2, (h - size) / 2 + 1, size, direction, isEnabled);

    // Reset the Graphics back to it's original settings
    if (isPressed && isPaintPressed()) {
      g.translate(-1, -1);
    }

    g.setColor(origColor);
  }

  /**
   * @param paintArrows the paintArrows to set
   */
  public void setPaintArrows(boolean paintArrows) {
    this.paintArrows = paintArrows;
  }

  public void setPaintPressed(boolean paintPressed) {
    this.paintPressed = paintPressed;
  }

  @Override
  public void setUI(ButtonUI ui) {
    super.setUI(ui);
  }

  /**
   * @return the paintArrows
   */
  public boolean isPaintArrows() {
    return paintArrows;
  }

  public boolean isPaintPressed() {
    return paintPressed;
  }
}
