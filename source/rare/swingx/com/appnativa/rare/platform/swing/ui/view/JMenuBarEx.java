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

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.UIMenuItem;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iMenuBarComponent;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;

import javax.swing.JMenuBar;
import javax.swing.border.Border;

/**
 *
 * @author Don DeCoteau
 */
public class JMenuBarEx extends JMenuBar implements iPainterSupport, iMenuBarComponent {
  ComponentEvent                    event;
  protected SwingGraphics           graphics;
  private iPlatformComponentPainter componentPainter;
  private boolean                   hasShapedBorder;

  public JMenuBarEx() {
    super();
  }

  @Override
  public void add(UIMenuItem mi) {
    add(mi.getMenuItem());
  }

  public void callSuperPaintComponent(Graphics g, java.awt.Shape shape) {
    if (isOpaque() &&!isBackgroundSet()) {
      super.paintComponent(g);
    }
  }

  @Override
  public void paint(Graphics g) {
    graphics = SwingGraphics.fromGraphics(g, this, graphics);
    super.paint(g);
    graphics.clear();
  }

  @Override
  public void remove(UIMenuItem mi) {
    if (mi.getMenuItem() != null) {
      remove(mi.getMenuItem());
    }
  }

  @Override
  public void setBorder(Border border) {
    super.setBorder(border);

    if (border instanceof iPlatformBorder) {
      hasShapedBorder = !((iPlatformBorder) border).isRectangular();
    }
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  public boolean hasValue() {
    return true;
  }

  @Override
  public boolean isOpaque() {
    if ((componentPainter != null) && componentPainter.isBackgroundPaintEnabled()) {
      return false;
    }

    return !hasShapedBorder && super.isOpaque();
  }

  @Override
  protected void paintBorder(Graphics g) {}

  @Override
  protected void paintChildren(Graphics g) {
    super.paintChildren(graphics.getGraphics());

    iPlatformComponentPainter cp = getComponentPainter();

    if (cp != null) {
      float height = getHeight();
      float width  = getWidth();

      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, true);
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    iPlatformComponentPainter cp = getComponentPainter();

    if (cp != null) {
      float height = getHeight();
      float width  = getWidth();

      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, false);
    }

    super.paintComponent(g);
  }

  @Override
  public void addActionListener(iActionListener l) {}

  @Override
  public void removeActionListener(iActionListener l) {}

  @Override
  public void setTitle(CharSequence title) {}

  @Override
  public void setTitleIcon(iPlatformIcon icon) {}

  @Override
  public iActionComponent getTitleComponent() {
    return null;
  }

  @Override
  public boolean hasTitleComponent() {
    return false;
  }
}
