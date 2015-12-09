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

package com.appnativa.rare.platform.swing.ui;

import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPath;
import com.appnativa.rare.ui.iPlatformShape;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import java.util.Map;

import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;

/**
 *
 * @author Don DeCoteau
 */
public class UIProxyBorder implements iPlatformBorder {
  private static Component _insetsComponent;
  Border                   border;

  public UIProxyBorder(Border border) {
    this.border = border;
  }

  public void clip(Graphics g, float x, float y, float width, float height) {}

  @Override
  public void clip(iPlatformGraphics g, float x, float y, float width, float height) {}

  @Override
  public boolean clipsContents() {
    return false;
  }

  public static iPlatformBorder fromBorder(Border border) {
    if (border instanceof iPlatformBorder) {
      return (iPlatformBorder) border;
    }

    if (border instanceof UIResource) {
      return new UIProxyBorderResource(border);
    }

    return (border == null)
           ? null
           : new UIProxyBorder(border);
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean end) {
    if (end) {
      paintBorder(g.getView(), g.getGraphics(), UIScreen.snapToPosition(x), UIScreen.snapToPosition(y),
                  UIScreen.snapToSize(width), UIScreen.snapToSize(height));
    }
  }

  @Override
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    border.paintBorder(c, g, x, y, width, height);
  }

  @Override
  public void setPadForArc(boolean pad) {}

  @Override
  public float getArcHeight() {
    return 0;
  }

  @Override
  public float getArcWidth() {
    return 0;
  }

  public Border getBorder() {
    return border;
  }

  @Override
  public Insets getBorderInsets(Component c) {
    return border.getBorderInsets(c);
  }

  @Override
  public UIInsets getBorderInsets(UIInsets insets) {
    if (_insetsComponent == null) {
      _insetsComponent = new JLabel();
    }

    if (insets == null) {
      insets = new UIInsets();
    }

    SwingHelper.setUIInsets(insets, border.getBorderInsets(_insetsComponent));

    return insets;
  }

  @Override
  public UIInsets getBorderInsetsEx(UIInsets insets) {
    return getBorderInsets(insets);
  }

  @Override
  public int getModCount() {
    return 0;
  }

  public Object getPlatformObject() {
    return border;
  }

  @Override
  public iPlatformShape getShape(iPlatformGraphics g, float x, float y, float width, float height) {
    return null;
  }

  public UIInsets getUnpaddedBorderInsets(UIInsets insets) {
    return getBorderInsets(insets);
  }

  @Override
  public boolean isBorderOpaque() {
    return border.isBorderOpaque();
  }

  @Override
  public boolean isPadForArc() {
    return false;
  }

  @Override
  public boolean isPaintLast() {
    return true;
  }

  @Override
  public boolean isRectangular() {
    return true;
  }

  public static class UIProxyBorderResource extends UIProxyBorder implements UIResource {
    public UIProxyBorderResource(Border border) {
      super(border);
    }
  }


  @Override
  public void handleCustomProperties(Map map) {}

  @Override
  public iPlatformPath getPath(iPlatformPath p, float x, float y, float width, float height, boolean forClip) {
    return null;
  }

  @Override
  public boolean isFocusAware() {
    return false;
  }

  @Override
  public boolean isEnabledStateAware() {
    return false;
  }
}
