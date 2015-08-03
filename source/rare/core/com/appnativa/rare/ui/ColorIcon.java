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

import com.appnativa.rare.Platform;

public class ColorIcon extends aUIPlatformIcon {
  static UIImage     nullColor;
  private static int imageHeight;
  private static int imageWidth;
  private int        iconHeight = ScreenUtils.PLATFORM_PIXELS_8 + ScreenUtils.PLATFORM_PIXELS_10;
  private int        iconWidth  = iconHeight;
  private UIColor    color;

  public ColorIcon(UIColor color) {
    this.color = color;

    if (nullColor == null) {
      nullColor   = Platform.getAppContext().getResourceAsImage("Rare.icon.checkered");
      imageWidth  = nullColor.getWidth();
      imageHeight = nullColor.getHeight();
    }
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    float d = ScreenUtils.PLATFORM_PIXELS_2;

    x      += d;
    y      += d;
    height = height - d - d;
    width  = width - d - d;

    if (color != null) {
      g.setColor(color);
      g.fillRect(x, y, width, height);
    } else if (nullColor != null) {
      UIRectangle src = new UIRectangle(0, 0, Math.min(width, imageWidth), Math.min(height, imageHeight));
      UIRectangle dst = new UIRectangle(x, y, width, height);

      g.drawImage(nullColor, src, dst, null);
    }

    g.setStrokeWidth(ScreenUtils.PLATFORM_PIXELS_1);
    g.setColor(UIColor.BLACK);
    g.drawRect(x, y, width, height);
  }

  public void setColor(UIColor color) {
    this.color = color;
  }

  public void setIconHeight(int iconHeight) {
    this.iconHeight = iconHeight;
  }

  public void setIconWidth(int iconWidth) {
    this.iconWidth = iconWidth;
  }

  public UIColor getColor() {
    return color;
  }

  @Override
  public iPlatformIcon getDisabledVersion() {
    return this;
  }

  @Override
  public int getIconHeight() {
    return iconHeight;
  }

  @Override
  public int getIconWidth() {
    return iconWidth;
  }
}
