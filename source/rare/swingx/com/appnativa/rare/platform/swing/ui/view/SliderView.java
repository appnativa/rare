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

import com.appnativa.rare.platform.swing.plaf.SkinableSliderUI;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.iSlider;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.BoundedRangeModel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderView extends JSlider implements iSlider, iPainterSupport, iView, ChangeListener {
  com.appnativa.rare.ui.event.ChangeEvent ChangeEvent;
  protected iChangeListener               changeListener;
  protected iPlatformComponentPainter     componentPainter;
  protected AffineTransform               transform;
  protected SwingGraphics                 graphics;

  public SliderView() {
    setOpaque(false);
  }

  public SliderView(BoundedRangeModel brm) {
    super(brm);
    setOpaque(false);
  }

  public SliderView(int orientation) {
    super(orientation);
    setOpaque(false);
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  @Override
  public Color getForeground() {
    Color c = super.getForeground();

    if ((c instanceof UIColor) &&!isEnabled()) {
      c = ((UIColor) c).getDisabledColor();
    }

    return c;
  }

  @Override
  public void getMinimumSize(UIDimension size, int maxWidth) {
    Dimension d = getMinimumSize();

    size.width  = d.width;
    size.height = d.height;
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxWidth) {
    Dimension d = super.getPreferredSize();

    size.width  = d.width;
    size.height = d.height;
  }

  @Override
  public AffineTransform getTransformEx() {
    return transform;
  }

  @Override
  public boolean isHorizontal() {
    return getOrientation() == HORIZONTAL;
  }

  @Override
  public boolean isOpaque() {
    return ((componentPainter != null) && componentPainter.isBackgroundPaintEnabled())
           ? false
           : super.isOpaque();
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

  public void setChangeListener(iChangeListener l) {
    removeChangeListener(this);
    addChangeListener(this);
    changeListener = l;
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  @Override
  public void setHorizontal(boolean horizontal) {
    setOrientation(horizontal
                   ? HORIZONTAL
                   : VERTICAL);
  }

  @Override
  public void setShowTicks(boolean show) {
    setPaintTicks(show);
  }

  @Override
  public void setThumbOffset(int off) {
    if (getUI() instanceof SkinableSliderUI) {
      ((SkinableSliderUI) getUI()).setThumbOffset(off);
    }
  }

  @Override
  public void setTransformEx(AffineTransform tx) {
    transform = tx;
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    if (changeEvent == null) {
      changeEvent = new ChangeEvent(this);
    }

    if (changeListener != null) {
      changeListener.stateChanged(changeEvent);
    }
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
}
