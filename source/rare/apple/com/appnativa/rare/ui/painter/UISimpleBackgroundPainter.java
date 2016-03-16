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

package com.appnativa.rare.ui.painter;

import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Displayed;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPaint;

/**
 *
 * @author Don DeCoteau
 */
public class UISimpleBackgroundPainter extends aUIPlatformPainter implements iBackgroundPainter, iPlatformPaint {
  public static final UISimpleBackgroundPainter NULL_BGPAINTER = new UISimpleBackgroundPainter(UIColor.TRANSPARENT);

  /** the background color */
  protected UIColor backgroundColor;

  public UISimpleBackgroundPainter() {
    setBackgroundColor(ColorUtils.NULL_COLOR);
  }

  public UISimpleBackgroundPainter(UIColor bg) {
    setBackgroundColor(bg);
  }

  @Override
  public boolean canUseLayer() {
    return displayed == Displayed.ALWAYS;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {
    if (backgroundColor == ColorUtils.NULL_COLOR) {
      return;
    }

    g.setColor(backgroundColor);
    g.fillRect(x, y, width, height);
  }

  @Override
  public void fill(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {
    //Never called because isPainter returns false
  }

  /**
   * Sets the background color for the painter
   *
   * @param bg the background color for the painter
   */
  public void setBackgroundColor(UIColor bg) {
    if (bg == null) {
      bg = ColorUtils.NULL_COLOR;
    }

    backgroundColor = bg;
  }

  @Override
  public UIColor getBackgroundColor() {
    return backgroundColor;
  }

  @Override
  public boolean canUseMainLayer() {
    return displayed == Displayed.ALWAYS;
  }

  @Override
  public boolean isSingleColorPainter() {
    return true;
  }

  @Override
  public UIColor getPlatformPaintColor() {
    return backgroundColor;
  }

  @Override
  public boolean isPainter() {
    return false;
  }

  @Override
  public iPlatformPaint getPaint(float width, float height) {
    return this;
  }
}
