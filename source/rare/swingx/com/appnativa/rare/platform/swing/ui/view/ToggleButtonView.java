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
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.util.CharArray;
import com.appnativa.util.xml.XMLUtils;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.Icon;
import javax.swing.JToggleButton;

public class ToggleButtonView extends JToggleButton implements iPainterSupport, iView {
  private String  originalText;
  private boolean wordWrap;

  public ToggleButtonView() {
    super();
  }

  public ToggleButtonView(Icon icon) {
    super(icon);
  }

  public ToggleButtonView(String text) {
    super(text);
  }

  public ToggleButtonView(String text, Icon icon) {
    super(text, icon);
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

  @Override
  public Icon getDisabledIcon() {
    Icon icon = super.getDisabledIcon();

    if (icon == null) {
      icon = getIcon();

      if (icon instanceof iPlatformIcon) {
        return ((iPlatformIcon) icon).getDisabledVersion();
      }
    }

    return icon;
  }

  public Icon getDisabledSelectedIcon() {
    Icon icon = super.getDisabledSelectedIcon();

    if (icon == null) {
      icon = getSelectedIcon();

      if (icon == null) {
        icon = getIcon();
      }

      if (icon instanceof iPlatformIcon) {
        return ((iPlatformIcon) icon).getDisabledVersion();
      }
    }

    return icon;
  }

  @Override
  public void setText(String text) {
    if (text == null) {
      text = "";
    }

    originalText = text;

    int len = text.length();

    if (wordWrap && (len > 0) &&!text.startsWith("<html>")) {
      CharArray ca = new CharArray(text.length() + 20);

      ca.append("<html>");
      XMLUtils.escape(text.toCharArray(), 0, len, true, ca);
      ca.append("</html>");
      text = ca.toString();
    }

    super.setText(text);
  }

  public void setWordWrap(boolean wordWrap) {
    this.wordWrap = wordWrap;
  }

  @Override
  public String getText() {
    return originalText;
  }

  public boolean isWordWrap() {
    return wordWrap;
  }
}
