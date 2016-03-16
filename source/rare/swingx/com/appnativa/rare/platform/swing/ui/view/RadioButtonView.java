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
import javax.swing.JRadioButton;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.util.CharArray;
import com.appnativa.util.XMLUtils;

public class RadioButtonView extends JRadioButton implements iPainterSupport, iView {
  protected static iPlatformIcon deselectedIconDisabled_;
  protected static iPlatformIcon deselectedIcon_;
  protected static iPlatformIcon deselectedPressedIcon_;
  protected static iPlatformIcon selectedIconDisabled_;
  protected static iPlatformIcon selectedIcon_;
  protected static iPlatformIcon selectedPressedIcon_;
  private String                 originalText;
  private boolean                wordWrap;

  public RadioButtonView() {
    super();
    initialize();
  }

  public RadioButtonView(Icon icon) {
    super(icon);
    initialize();
  }

  public RadioButtonView(String text) {
    super(text);
    initialize();
  }

  public RadioButtonView(String text, Icon icon) {
    super(text, icon);
    initialize();
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
  public Color getForeground() {
    Color c=super.getForeground();
    if(c instanceof UIColor && !isEnabled()) {
      c=((UIColor)c).getDisabledColor();
    }
    return c;
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
    graphics = SwingGraphics.fromGraphics(g, this, graphics);

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
  public void getMinimumSize(UIDimension size, int maxWidth) {
    Dimension d = getMinimumSize();

    size.width  = d.width;
    size.height = d.height;
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

  protected void initialize() {
    setOpaque(false);
    setFont(FontUtils.getDefaultFont());
    setForeground(ColorUtils.getForeground());

    if (selectedIcon_ == null) {
      iPlatformAppContext app = Platform.getAppContext();

      if (ColorUtils.getForeground().isDarkColor()) {
        selectedIcon_           = app.getResourceAsIcon("Rare.icon.radiobutton.on.light");
        deselectedIcon_         = app.getResourceAsIcon("Rare.icon.radiobutton.off.light");
        selectedPressedIcon_    = app.getResourceAsIcon("Rare.icon.radiobutton.on.pressed.light");
        deselectedPressedIcon_  = app.getResourceAsIcon("Rare.icon.radiobutton.off.pressed.light");
        selectedIconDisabled_   = app.getResourceAsIcon("Rare.icon.radiobutton.on.disabled.light");
        deselectedIconDisabled_ = app.getResourceAsIcon("Rare.icon.radiobutton.off.disabled.light");
      } else {
        selectedIcon_           = app.getResourceAsIcon("Rare.icon.radiobutton.on.dark");
        deselectedIcon_         = app.getResourceAsIcon("Rare.icon.radiobutton.off.dark");
        selectedPressedIcon_    = app.getResourceAsIcon("Rare.icon.radiobutton.on.pressed.dark");
        deselectedPressedIcon_  = app.getResourceAsIcon("Rare.icon.radiobutton.off.pressed.dark");
        selectedIconDisabled_   = app.getResourceAsIcon("Rare.icon.radiobutton.on.disabled.dark");
        deselectedIconDisabled_ = app.getResourceAsIcon("Rare.icon.radiobutton.off.disabled.dark");
      }
    }

    setSelectedIcon(selectedIcon_);
    setDisabledIcon(deselectedIconDisabled_);
    setPressedIcon(deselectedPressedIcon_);
    setIcon(deselectedIcon_);
    setDisabledSelectedIcon(selectedIconDisabled_);
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
  private static UIDimension size    = new UIDimension();

  @Override
  public Dimension getPreferredSize() {
    if (size == null) {
      size = new UIDimension();
    }

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
  }
}
