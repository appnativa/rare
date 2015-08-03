/*
 * @(#)RendererPanel.java   2010-03-26
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;

import javax.swing.JMenuBar;
import javax.swing.border.Border;

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.UIMenuItem;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iMenuBarComponent;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

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
  public void addActionListener(iActionListener l) {
  }

  @Override
  public void removeActionListener(iActionListener l) {
  }

  @Override
  public void setTitle(CharSequence title) {
    
  }

  @Override
  public void setTitleIcon(iPlatformIcon icon) {
    
  }

  @Override
  public iActionComponent getTitleComponent() {
    return null;
  }

  @Override
  public boolean hasTitleComponent() {
    return false;
  }
}
