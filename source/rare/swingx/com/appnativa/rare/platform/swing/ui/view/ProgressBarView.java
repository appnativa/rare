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
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.BoundedRangeModel;
import javax.swing.JProgressBar;

public class ProgressBarView extends JProgressBar implements iPainterSupport, iView {
  public ProgressBarView() {
    super();
  }

  public ProgressBarView(BoundedRangeModel newModel) {
    super(newModel);
  }

  public ProgressBarView(int orient, int min, int max) {
    super(orient, min, max);
  }

  public ProgressBarView(int min, int max) {
    super(min, max);
  }

  public ProgressBarView(int orient) {
    super(orient);
  }

  AffineTransform                   transform;
  protected SwingGraphics           graphics;
  private iPlatformComponentPainter componentPainter;

  @Override
  public boolean isOpaque() {
    return ((componentPainter != null) && componentPainter.isBackgroundPaintEnabled())
           ? false
           : super.isOpaque();
  }

  @Override
  public void setTransformEx(AffineTransform tx) {
    transform = tx;
  }

  @Override
  public AffineTransform getTransformEx() {
    return transform;
  }

  @Override
  public void paint(Graphics g) {
    Graphics2D      g2 = (Graphics2D) g;
    AffineTransform tx = g2.getTransform();

    if (transform != null) {
      g2.transform(transform);
    }

    graphics = SwingGraphics.fromGraphics(g2, this, graphics);
    super.paint(g2);

    if (tx != null) {
      g2.setTransform(tx);
    }

    graphics.clear();
  }

  @Override
  protected void paintBorder(Graphics g) {
    if (componentPainter == null) {
      super.paintBorder(g);
    }
  }

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
  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    Dimension d = getMinimumSize();

    size.width  = d.width;
    size.height = d.height;
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxWidth) {
    Dimension d = getPreferredSize();

    size.width  = d.width;
    size.height = d.height;
  }
}
