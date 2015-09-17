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

import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 *
 * @author Don DeCoteau
 */
public class UIProxyIcon implements iPlatformIcon {
  Icon icon;

  public UIProxyIcon(Icon icon) {
    this.icon = icon;
  }

  @Override
  public void paintIcon(Component c, Graphics g, int x, int y) {
    icon.paintIcon(c, g, x, y);
  }

  @Override
  public int getIconWidth() {
    return icon.getIconWidth();
  }

  @Override
  public int getIconHeight() {
    return icon.getIconHeight();
  }

  public Object getPlatformObject() {
    return icon;
  }

  public static iPlatformIcon fromIcon(Icon icon) {
    if (icon instanceof iPlatformIcon) {
      return (iPlatformIcon) icon;
    }

    return (icon == null)
           ? null
           : new UIProxyIcon(icon);
  }

  @Override
  public iPlatformIcon getDisabledVersion() {
    return this;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    if (icon != null) {
      icon.paintIcon(g.getView(), g.getGraphics(), UIScreen.snapToPosition(x), UIScreen.snapToPosition(y));
    }
  }
}
