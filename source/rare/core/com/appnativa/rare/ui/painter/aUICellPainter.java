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

import com.appnativa.rare.ui.Displayed;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPaint;

/**
 * An object that can paint a rectangular cell
 *
 * @author Don DeCoteau
 */
public abstract class aUICellPainter extends PaintBucket implements iPlatformPainter {
  public int height = -1;
  public int width  = -1;
  public int x;
  public int y;

  /** the column corresponding to the x-position */
  public int column = 1;

  /** the row corresponding to the x-position */
  public int row = 1;

  /** the row span corresponding to the width */
  public int columnSpan;

  /** the row span corresponding to the width */
  public int          rowSpan;
  private Displayed   displayed;
  private boolean     enabled;
  private RenderSpace renderSpace;
  private RenderType  renderType;

  /**
   * Creates a new instance
   */
  protected aUICellPainter() {}

  public void setBackground(UIColor bg) {
    backgroundColor = bg;
  }

  /**
   * Sets the display criteria for the painter
   *
   * @param displayed
   *          the display criteria for the painter
   */
  @Override
  public void setDisplayed(Displayed displayed) {
    this.displayed = displayed;
  }

  @Override
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public void setRenderSpace(RenderSpace space) {
    renderSpace = space;
  }

  /**
   * Sets the render type for the painter
   *
   * @param type
   *          the render type for the painter
   */
  @Override
  public void setRenderType(RenderType type) {
    this.renderType = type;
  }

  @Override
  public UIColor getBackgroundColor() {
    return backgroundColor;
  }

  /**
   * Gets the border for the cell
   *
   * @return the border for the cell
   */
  @Override
  public iPlatformBorder getBorder() {
    return border;
  }

  @Override
  public Displayed getDisplayed() {
    return displayed;
  }

  @Override
  public RenderSpace getRenderSpace() {
    return renderSpace;
  }

  @Override
  public RenderType getRenderType() {
    return renderType;
  }

  public boolean hasValue() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public void dispose() {}

  @Override
  public void setDisposable(boolean disposable) {}

  @Override
  public boolean isDisposed() {
    return false;
  }

  @Override
  public boolean isDisposable() {
    return false;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {
    g.saveState();

    try {
      if (this.x != -1) {
        x += this.x;
      }

      if (this.y != -1) {
        y += this.y;
      }

      if (this.width != -1) {
        width = Math.min(width, this.width);
      }

      if (this.height != -1) {
        height = Math.min(height, this.height);
      }

      if ((width < 1) || (height < 1)) {
        return;
      }

      iComponentPainter cp = getCachedComponentPainter();

      cp.paint(g, x, y, width, height, orientation);
      // UIComponentPainter.p
      // if (border != null) {
      // border.paint(g, x, y, width, height, false);
      // border.clip(g, x, y, width, height);
      // } else {
      // g.clipRect(x, y, x + width+1, y + height+1);
      // }
      // if (backgroundColor != null) {
      //
      // g.setColor(backgroundColor);
      // g.fillRect(x, y, width, height);
      // }
      //
      // if (backgroundPainter != null) {
      // backgroundPainter.paint(g, x, y, width, height, orientation);
      // }
      //
      // if (imagePainter != null) {
      // imagePainter.paint(g, x, y, width, height, orientation);
      // }
    } finally {
      g.restoreState();
    }
    // if (border != null) {
    // border.paint(g, x, y, width, height, true);
    // }
  }

  @Override
  public iPlatformPainter reference() {
    return this;
  }

  @Override
  public iPlatformPaint getPaint(float width, float height) {
    return null;
  }
}
