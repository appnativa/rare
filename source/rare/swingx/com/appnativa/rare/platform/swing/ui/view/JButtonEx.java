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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToolTip;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.swing.ui.util.ImageHelper;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

/**
 *
 *
 * @version    0.3, 2007-06-13
 * @author     Don DeCoteau
 */
public class JButtonEx extends JButton implements iPainterSupport, iView {
  Dimension                         sized;
  AffineTransform                   transform;
  protected SwingGraphics           graphics;
  private iPlatformComponentPainter componentPainter;
  private boolean                   rightJustified;

  public JButtonEx() {
    this("");
  }

  public JButtonEx(Icon icon) {
    this(null, icon);
  }

  public JButtonEx(String value) {
    this(value, null);
  }

  public JButtonEx(String text, Icon icon) {
    super(text, icon);
    super.setOpaque(false);
  }

  public void addActionListener(iActionListener l) {
    listenerList.add(iActionListener.class, l);
  }

  @Override
  public JToolTip createToolTip() {
    return JToolTipEx.createNewToolTip(this);
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

  public void removeActionListener(iActionListener l) {
    listenerList.remove(iActionListener.class, l);
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  public void setIconRightJustified(boolean rightJustified) {
    this.rightJustified = rightJustified;
  }

  @Override
  public void setTransformEx(AffineTransform tx) {
    transform = tx;
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  @Override
  public Icon getDisabledIcon() {
    Icon icon = super.getDisabledIcon();

    if (icon == null) {
      Icon ic = getIcon();

      if (ic instanceof iPlatformIcon) {
        icon = ((iPlatformIcon) ic).getDisabledVersion();
      }
    }

    return icon;
  }

  @Override
  public Color getForeground() {
    Color c=super.getForeground();
    if(c instanceof UIColor && !isEnabled()) {
      c=((UIColor)c).getDisabledColor();
    }
    return c;
  }

  private boolean drawArrow;

  public boolean isDrawArrow() {
    return drawArrow;
  }

  public void setDrawArrow(boolean drawArrow) {
    this.drawArrow = drawArrow;
  }

  @Override
  public Icon getPressedIcon() {
    Icon ic = super.getPressedIcon();

    if ((ic == null) && (getClientProperty(iConstants.RARE_CREATEPRESSED_ICON) == Boolean.TRUE)
        && (getIcon() != null)) {
      ic = ImageHelper.createPressedIcon(this, getIcon());
      setPressedIcon(ic);
    }

    return ic;
  }

  @Override
  public AffineTransform getTransformEx() {
    return transform;
  }

  public boolean hasValue() {
    return (getText() != null) && (getText().length() > 0);
  }

  public boolean isIconRightJustified() {
    return rightJustified;
  }

  @Override
  public boolean isOpaque() {
    return ((componentPainter != null) && componentPainter.isBackgroundPaintEnabled())
           ? false
           : super.isOpaque();
  }

  public boolean isWordWrap() {
    return false;
  }

  @Override
  protected void paintBorder(Graphics g) {
    if (componentPainter == null) {
      super.paintBorder(g);
    }
  }

  @Override
  protected void paintChildren(Graphics g) {
    graphics = SwingGraphics.fromGraphics(g, this, graphics);
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
    graphics = SwingGraphics.fromGraphics(g, this, graphics);

    iPlatformComponentPainter cp = getComponentPainter();

    if (cp != null) {
      float height = getHeight();
      float width  = getWidth();

      cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, false);
    }

    super.paintComponent(g);
  }

  private static UIDimension size    = new UIDimension();

  @Override
  public Dimension getPreferredSize() {
    Number num      = (Number) getClientProperty(iConstants.RARE_WIDTH_FIXED_VALUE);
    int    maxWidth = 0;

    if ((num != null) && (num.intValue() > 0)) {
      maxWidth = num.intValue();
    }

    getPreferredSize(size, maxWidth);

    return new Dimension(size.intWidth(), size.intHeight());
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxWidth) {
    Dimension d = super.getPreferredSize();

    size.width  = d.width;
    size.height = d.height;

    if (isFontSet() && getFont().isItalic()) {
      if (getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey) == null) {
        size.width += 4;
      }
    }

    if (getVerticalTextPosition() == BOTTOM) {
      size.height += 16;
    }

    if (drawArrow) {
      size.width += 16;

      String s = getText();

      if ((s != null) && (s.length() > 0)) {
        size.width += 4;
      }
    }
  }

  @Override
  public void getMinimumSize(UIDimension size, int maxWidth) {
    Dimension d = super.getMinimumSize();

    size.width  = d.width;
    size.height = d.height;

    if (drawArrow) {
      size.width += 16;

      String s = getText();

      if ((s != null) && (s.length() > 0)) {
        size.width += 4;
      }
    }
  }
}
