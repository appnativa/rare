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

import java.awt.FontMetrics;

public class UIFontMetrics {
  FontMetrics    metrics;
  private UIFont font;

  public UIFontMetrics(UIFont font) {
    this.font = font;
    metrics   = Platform.getAppContext().getRootViewer().getContainerComponent().getView().getFontMetrics(font);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof UIFontMetrics) {
      if (o == this) {
        return true;
      }

      return ((UIFontMetrics) o).font.equals(font);
    }

    return false;
  }

  public int stringWidth(String str) {
    return metrics.stringWidth(str);
  }

  public void setFont(UIFont font) {
    this.font = font;
    metrics   = Platform.getAppContext().getRootViewer().getContainerComponent().getView().getFontMetrics(font);
  }

  public float getAscent() {
    return metrics.getAscent();
  }

  public float getDescent() {
    return metrics.getDescent();
  }

  public UIFont getFont() {
    return font;
  }

  public float getHeight() {
    return metrics.getHeight();
  }

  public float getLeading() {
    return metrics.getLeading();
  }

  public static UIFontMetrics getMetrics(UIFont font) {
    return new UIFontMetrics(font);
  }
}
