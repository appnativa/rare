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

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

import javax.swing.JComponent;

public class GlassPanel extends JComponent {
  SwingGraphics            graphics;
  private UIColor          lockedColor;
  private iPlatformPainter lockedPainter;
  boolean                  overlayContainer;
  MouseAdapter             listener;
  
  public GlassPanel(boolean overlay) {
    super();
    lockedColor=Platform.getUIDefaults().getColor("Rare.disabledTransparentColor");
    if(lockedColor==null) {
      lockedColor=ColorUtils.DISABLED_TRANSPARENT_COLOR;
    }
    setOpaque(false);
    setFocusable(false);
    overlayContainer = overlay;

    if (!overlay) {
      MouseAdapter l = new MouseAdapter() {}
      ;

      super.addMouseListener(l);
      addMouseMotionListener(l);
      addMouseWheelListener(l);
      addKeyListener(new KeyAdapter() {}
      );
    }
  }

  public void dispose() {}

  @Override
  public void paint(Graphics g) {
    if (overlayContainer) {
      super.paint(g);

      return;
    }
    if (((lockedColor != null) && (lockedColor != ColorUtils.TRANSPARENT_COLOR)) || (lockedPainter != null)) {
      graphics = SwingGraphics.fromGraphics(g, this, graphics);

      if (lockedPainter != null) {
        UIComponentPainter.paintPainter(lockedPainter, graphics, getWidth(), getHeight());
      } else if (lockedColor != null) {
        g.setColor(lockedColor);
        g.fillRect(0, 0, getWidth(), getHeight());
      }

      graphics.clear();
    }
  }

  /**
   * @param lockedColor
   *          the lockedColor to set
   */
  public void setLockedColor(UIColor lockedColor) {
    this.lockedColor   = lockedColor;
    this.lockedPainter = lockedColor==null ? null : ColorUtils.getPainter(lockedColor);
  }

  /**
   * @param lockedPainter
   *          the lockedPainter to set
   */
  public void setLockedPainter(iPlatformPainter lockedPainter) {
    this.lockedPainter = lockedPainter;
  }

  /**
   * @return the lockedColor
   */
  public UIColor getLockedColor() {
    return lockedColor;
  }

  /**
   * @return the lockedPainter
   */
  public iPainter getLockedPainter() {
    return lockedPainter;
  }

  @Override
  public boolean isFocusable() {
    return false;
  }
}
