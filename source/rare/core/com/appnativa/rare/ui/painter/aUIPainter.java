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

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.Displayed;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformPaint;

/**
 * A base class for painters
 *
 * @author Don DeCoteau
 */
public abstract class aUIPainter implements iPlatformPainter, iBackgroundPainter, Cloneable {
  private static int      staticCallCount = 0;
  private static UIInsets rsInsets        = new UIInsets();
  int                     referenceCount;
  protected int           modCount;
  private boolean         enabled     = true;
  protected RenderType    renderType  = RenderType.TILED;
  protected RenderSpace   renderSpace = RenderSpace.WITHIN_BORDER;
  protected Displayed     displayed   = Displayed.ALWAYS;
  private boolean         disposable;
  private boolean         disposed;

  /**
   * Constructs a new painter
   */
  public aUIPainter() {}

  @Override
  public iBackgroundPainter alpha(int alpha) {
    return this;
  }

  @Override
  public Object clone() {
    try {
      aUIPainter p = (aUIPainter) super.clone();

      p.referenceCount = 0;

      return p;
    } catch(CloneNotSupportedException e) {
      throw new RuntimeException("Clone Not Supported");
    }
  }

  @Override
  public synchronized void dispose() {
    if (referenceCount > 0) {
      referenceCount--;
    }

    if (referenceCount == 0) {
      if (isDisposable()) {
        disposeEx();
      }
    }
  }

  public void forceableDispose() {
    referenceCount = 0;
    disposable     = true;
    dispose();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (obj == this) {
      return true;
    }

    return false;
  }

  @Override
  public int hashCode() {
    int hash = 5;

    hash = 17 * hash + super.hashCode();

    return hash;
  }

  @Override
  public synchronized iPlatformPainter reference() {
    referenceCount++;

    return this;
  }

  @Override
  public void setDisplayed(Displayed displayed) {
    this.displayed = (displayed == null)
                     ? Displayed.ALWAYS
                     : displayed;
  }

  @Override
  public void setDisposable(boolean disposable) {
    this.disposable = disposable;
  }

  /**
   * @param enabled
   *          the enabled to set
   */
  @Override
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public void setRenderSpace(RenderSpace space) {
    renderSpace = (space == null)
                  ? RenderSpace.COMPONENT
                  : space;
  }

  @Override
  public void setRenderType(RenderType type) {
    this.renderType = (type == null)
                      ? RenderType.TILED
                      : type;
  }

  /**
   * Gets the background color for the painter
   *
   * @return the background color for the painter or null
   */
  @Override
  public UIColor getBackgroundColor() {
    return null;
  }

  @Override
  public Displayed getDisplayed() {
    return displayed;
  }

  public int getModCount() {
    return modCount;
  }

  @Override
  public String toString() {
    UIColor c = getBackgroundColor();

    if (c != null) {
      return c.toString();
    }

    return super.toString();
  }

  @Override
  public iPlatformPaint getPaint(float width, float height) {
    return null;
  }

  public static UIRectangle getRenderLocation(Object comp, RenderSpace rs, RenderType rt, float x, float y, float w,
          float h, float iw, float ih, UIRectangle outRect) {
    UIRectangle rect = outRect;

    if (rect == null) {
      rect = new UIRectangle();
    }

    staticCallCount++;

    if ((rs != null) && (comp != null)) {
      switch(rs) {
        case WITHIN_BORDER :
        case WITHIN_MARGIN : {
          iPlatformComponent component = Platform.findPlatformComponent(comp);

          if (component != null) {
            iPlatformBorder b = component.getBorder();

            if (b != null) {
              UIInsets insets;

              if (rs == RenderSpace.WITHIN_BORDER) {
                insets = b.getBorderInsetsEx((staticCallCount == 1)
                                             ? rsInsets
                                             : null);
              } else {
                insets = b.getBorderInsets((staticCallCount == 1)
                                           ? rsInsets
                                           : null);
              }

              x += insets.left;
              y += insets.top;
              w -= (insets.left + insets.right);
              h -= (insets.top + insets.bottom);
            }
          }

          break;
        }

        default :
          break;
      }
    }

    if (rt != null) {
      switch(rt) {
        case HORIZONTAL_TILE :
          break;

        case VERTICAL_TILE :
          break;

        case STRETCHED :
          break;

        case STRETCH_WIDTH_MIDDLE :
        case STRETCH_WIDTH :
          y = (h - ih) / 2;
          h = ih;

          break;

        case STRETCH_HEIGHT_MIDDLE :
        case STRETCH_HEIGHT :
          x = (w - iw) / 2;
          w = iw;

          break;

        case UPPER_LEFT :
          w = iw;
          h = ih;

          break;

        case UPPER_RIGHT :
          x = w - iw;
          w = iw;
          h = ih;

          break;

        case LOWER_LEFT :
          y = h - ih;
          w = iw;
          h = ih;

          break;

        case LOWER_RIGHT :
          x = w - iw;
          y = h - ih;
          w = iw;
          h = ih;

          break;

        case LOWER_MIDDLE :
          x = (w - iw) / 2;
          y = h - ih;
          w = iw;
          h = ih;

          break;

        case UPPER_MIDDLE :
          x = (w - iw) / 2;
          w = iw;
          h = ih;

          break;

        case LEFT_MIDDLE :
          y = (h - ih) / 2;
          w = iw;
          h = ih;

          break;

        case RIGHT_MIDDLE :
          x = w - iw;
          y = (h - ih) / 2;
          w = iw;
          h = ih;

          break;

        case CENTERED :
          x = (w - iw) / 2;
          y = (h - ih) / 2;
          w = iw;
          h = ih;

          break;

        default :
          break;
      }
    }

    rect.setBounds(x, y, w, h);
    staticCallCount--;

    return rect;
  }

  @Override
  public RenderSpace getRenderSpace() {
    return renderSpace;
  }

  @Override
  public RenderType getRenderType() {
    return renderType;
  }

  @Override
  public boolean isDisposable() {
    return disposable;
  }

  /**
   * @return the enabled
   */
  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public boolean isDisposed() {
    return disposed;
  }

  protected void disposeEx() {
    disposed = true;
  }
}
