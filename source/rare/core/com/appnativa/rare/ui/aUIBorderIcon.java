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

package com.appnativa.rare.ui;

public abstract class aUIBorderIcon extends aPlatformIcon {
  protected iPlatformBorder border;
  protected iPlatformIcon   icon;
  protected UIInsets        insets;

  public aUIBorderIcon(iPlatformBorder border, iPlatformIcon icon) {
    this.border = border;
    this.icon   = icon;
    this.insets = border.getBorderInsetsEx(new UIInsets());
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    border.paint(g, x, y, width, height, border.isPaintLast());

    if (border.clipsContents()) {
      g.saveState();
      border.clip(g, x, y, width, height);
    }

    x      += insets.left;
    y      += insets.top;
    width  -= (insets.left + insets.right);
    height -= (insets.top + insets.bottom);
    icon.paint(g, x, y, width, height);

    if (border.clipsContents()) {
      g.restoreState();
    }
  }

  @Override
  public iPlatformIcon getDisabledVersion() {
    return this;
  }

  @Override
  public int getIconHeight() {
    return (int) (insets.top + insets.bottom + icon.getIconHeight());
  }

  @Override
  public int getIconWidth() {
    return (int) (insets.left + insets.right + icon.getIconWidth());
  }

  public iPlatformIcon getIcon() {
    return icon;
  }

  public void setIcon(iPlatformIcon icon) {
    this.icon = icon;
  }

  public iPlatformBorder getBorder() {
    return border;
  }

  public void setBorder(iPlatformBorder border) {
    this.border = border;
    this.insets = border.getBorderInsets(new UIInsets());
  }
}
