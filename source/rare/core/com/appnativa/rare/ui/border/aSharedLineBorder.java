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

package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPath;
import com.appnativa.rare.ui.iPlatformShape;
import com.appnativa.rare.ui.listener.iViewListener;
import com.appnativa.util.SNumber;

import com.google.j2objc.annotations.Weak;

public abstract class aSharedLineBorder extends UILineBorder implements iViewListener {
  UIDimension                  size;
  protected UIColor            backgroundColor;
  @Weak
  protected iPlatformComponent bottomComponent;
  protected UIRectangle        tempRect1  = new UIRectangle();
  protected UIRectangle        tempRect2  = new UIRectangle();
  protected UIPoint            tempPoint1 = new UIPoint();
  protected UIPoint            tempPoint2 = new UIPoint();
  @Weak
  protected iPlatformComponent topComponent;

  public aSharedLineBorder(UIColor color) {
    super(color);
  }

  public aSharedLineBorder(UIColor color, float thickness) {
    super(color, thickness);
  }

  public aSharedLineBorder(UIColor color, float thickness, boolean roundedCorners) {
    super(color, thickness, roundedCorners);
  }

  public aSharedLineBorder(UIColor color, float thickness, int arc) {
    super(color, thickness, arc);
  }

  public aSharedLineBorder(UIColor color, float thickness, int arcWidth, int arcHeight) {
    super(color, thickness, arcWidth, arcHeight);
  }

  @Override
  protected iPlatformPath createBorderPath(iPlatformPath p, float x, float y, float width, float height, float aw,
          float ah, boolean clip) {
    return super.createBorderPath(p, x, y, width, height, aw, ah, clip);
  }

  @Override
  public void clip(iPlatformGraphics g, float x, float y, float width, float height) {
    iPlatformComponent c = g.getComponent();

    updateShape(c, width, height);

    if (!roundedCorners) {
      if (clipInsets == null) {
        clipInsets = new UIInsets();
      }

      UIInsets in = getBorderInsets(clipInsets);

      if (noBottom) {
        in.bottom = 0;
      }

      if (noTop) {
        in.top = 0;
      }

      g.clipRect(x + in.left, y + in.top, width - in.right - in.left, height - in.bottom - in.top);

      return;
    }

    if (clipShape != null) {
      clipShape.reset();
    }

    clipShape = getPath(clipShape, x, y, width, height, true);
    g.clipShape(clipShape);
  }

  @Override
  public boolean clipsContents() {
    return true;
  }

  @Override
  public Object clone() {
    aSharedLineBorder b = (aSharedLineBorder) super.clone();

    b.tempRect1  = new UIRectangle();
    b.tempRect2  = new UIRectangle();
    b.tempPoint1 = new UIPoint();
    b.tempPoint2 = new UIPoint();
    b.setSharers(null, null);

    return b;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean end) {
    if (end != isPaintLast()) {
      return;
    }

    iPlatformComponent c = g.getComponent();

    if (updateShape(c, width, height)) {
      super.paint(g, x, y, width, height, end);

      if ((c != topComponent) && (noBottom || noTop || noRight || noLeft)) {
        UIStroke stroke = g.getStroke();
        UIColor  oc     = g.getColor();

        if (lineStroke != null) {
          g.setStroke(lineStroke);
        } else {
          g.setStrokeWidth(thickness);
        }

        paintPartialLine(g, x, y, width, height);
        g.setStroke(stroke);
        g.setColor(oc);
      }
    } else {
      super.paint(g, x, y, width, height, end);
    }
  }

  public void repaintTopComponent() {
    if (topComponent != null) {
      topComponent.repaint();
    }
  }

  @Override
  public boolean usesPath() {
    return false;
  }

  @Override
  public void viewHidden(ChangeEvent e) {
    if ((topComponent != null) && topComponent.isShowing()) {
      topComponent.repaint();
    }
  }

  @Override
  public void viewResized(ChangeEvent e) {
    if (topComponent != null) {
      topComponent.repaint();
    }
  }

  @Override
  public void viewShown(ChangeEvent e) {
    if (topComponent != null) {
      topComponent.repaint();
    }
  }

  @Override
  public boolean wantsResizeEvent() {
    return true;
  }

  public void setBackgroundColor(UIColor background) {
    backgroundColor = background;
  }

  public void setBottomComponent(iPlatformComponent c) {
    if (bottomComponent != null) {
      bottomComponent.removeViewListener(this);
    }

    if (c != null) {
      c.removeViewListener(this);
      c.addViewListener(this);
    }

    bottomComponent = c;
  }

  public void setSharers(iPlatformComponent top, iPlatformComponent bottom) {
    setBottomComponent(bottom);
    topComponent = top;
  }

  public UIColor getBackgroundColor() {
    return backgroundColor;
  }

  public iPlatformComponent getBottomComponent() {
    return topComponent;
  }

  @Override
  public iPlatformShape getShape(iPlatformGraphics g, float x, float y, float width, float height) {
    iPlatformComponent c = g.getComponent();

    updateShape(c, width, height);

    if (paintShape != null) {
      paintShape.reset();
    }

    paintShape = getPath(paintShape, x, y, width, height, false);

    return paintShape;
  }

  public iPlatformComponent getTopComponent() {
    return topComponent;
  }

  protected float getRoundedOffset() {
    return (thickness < 3)
           ? (thickness < 2)
             ? .25f
             : 0.5f
           : 0.5f;
  }

  protected void paintPartialLine(iPlatformGraphics g, float x, float y, float width, float height) {
    float   y2      = -1;
    float   height2 = -1;
    float   x2      = -1;
    float   width2  = -1;
    float   ro      = roundedCorners
                      ? getRoundedOffset()
                      : 0;
    int     d       = ScreenUtils.PLATFORM_PIXELS_1;
    UIPoint p1      = topComponent.getLocationOnScreen(tempPoint1);
    UIPoint p2      = bottomComponent.getLocationOnScreen(tempPoint2);
    float   w       = topComponent.getWidth();
    float   h       = topComponent.getHeight();

    if (insets == null) {
      insets = new UIInsets();
    }

    calculateInsets(insets, padForArc);

    float size = (insets.top == 0)
                 ? insets.bottom
                 : insets.top;

    g.setColor((backgroundColor == null)
               ? bottomComponent.getBackground()
               : backgroundColor);

    UIRectangle r = tempRect1;

    if (noTop || noBottom) {
      if (noBottom) {
        y += height - size;
      }

      height   = size;
      r.x      = x;
      r.y      = y;
      r.height = height;
      r.width  = w;

      float dx;

      if (p1.x - p2.x > d) {
        dx      = (p1.x - p2.x) + ro;
        r.x     += dx;
        r.width -= size + ro + ro + dx;
        g.fillRect(r.x, r.y, r.width, r.height);

        if (p2.x + width > p1.x + w + d) {
          width2 = (p2.x + width) - (p1.x + w);
          x2     = x + width - width2 - size;
          width  = p1.x - p2.x + size;
        } else {
          width -= w;
          x     += size;
        }
      } else {
        dx      = (p2.x - p1.x) + size + ro;
        r.x     += dx + size;
        r.width -= dx + size + size + ro;
        g.fillRect(r.x, r.y, r.width, r.height);
        x     = r.x + r.width;
        width -= w - ro;
      }
    } else if (noLeft || noRight) {
      if (noRight) {
        x += width - size;
      } else {
        x -= ro;
      }

      width = size;
      g.fillRect(x, y + (p2.y - p1.y) + size, width, h - size);

      if (p1.y - p2.y > 1) {
        if (p2.y + height > p1.y + h + d) {
          height2 = (p2.y + height) - (p1.y + h);
          y2      = y + height - height2 - size;
          height  = p1.y - p2.y + size;
        } else {
          y      += h;
          height -= h;
        }
      } else {
        y      += h;
        height -= h;
      }
    }

    g.setColor(getLineColor());

    if (roundedCorners) {
      x += getClipingOffset();
    }

    g.fillRect(x, y, width, height);

    if (width2 > 0) {
      g.fillRect(x2, y, width2 + ro, height);
    }

    if (height2 > 0) {
      g.fillRect(x, y2, width, height2);
    }

    if (y2 > -1) {
      g.fillRect(x, y2, width, height2);
    }
  }

  public UIInsets getBorderInsetsEx(iPlatformComponent c, UIInsets insets) {
    float w = (c == null)
              ? ScreenUtils.PLATFORM_PIXELS_1
              : c.getWidth();
    float h = (c == null)
              ? ScreenUtils.PLATFORM_PIXELS_1
              : c.getHeight();

    updateShape(c, w, h);

    return getBorderInsetsEx(insets);
  }

  public boolean updateShape(iPlatformComponent c, float width, float height) {
    noTop      = false;
    flatBottom = false;
    flatTop    = false;
    noBottom   = false;
    noLeft     = false;
    noRight    = false;

    boolean hasPartial = false;

    do {
      if (((bottomComponent == null) || (topComponent == null))) {
        break;
      }

      UIPoint p1 = ((topComponent != null) && topComponent.isShowing())
                   ? topComponent.getLocationOnScreen(tempPoint1)
                   : null;
      UIPoint p2 = ((bottomComponent != null) && bottomComponent.isShowing())
                   ? bottomComponent.getLocationOnScreen(tempPoint2)
                   : null;

      if ((p1 == null) || (p2 == null)) {
        break;
      }

      if (SNumber.isEqual(p1.y, p2.y)) {
        break;
      }

      UIRectangle        rect1           = tempRect1;
      UIRectangle        rect2           = tempRect2;
      boolean            partialOnBottom = false;
      iPlatformComponent tc              = topComponent;
      iPlatformComponent bc              = bottomComponent;
      UIPoint            tp              = p1;
      UIPoint            bp              = p2;

      if (p1.y > p2.y) {    // swap
        tc              = bottomComponent;
        bc              = topComponent;
        p1              = bp;
        p2              = tp;
        partialOnBottom = true;
      }

      if (c == tc) {
        rect1.setSize(width, height);
        size         = bc.getOrientedSize(size);
        rect2.width  = size.width;
        rect2.height = size.height;
      } else {
        size         = tc.getOrientedSize(size);
        rect1.width  = size.width;
        rect1.height = size.height;
        rect2.setSize(width, height);
      }

      if (rect1.isEmpty() || rect2.isEmpty()) {
        break;
      }

      rect1.setLocation(p1);
      rect2.setLocation(p2);

      int     d           = ScreenUtils.PLATFORM_PIXELS_1;
      ;
      boolean top         = c == topComponent;
      boolean leftOrRight = ((bp.x + rect2.width != tp.x + rect1.width) && (bp.x != tp.x));

      if (leftOrRight && (rect1.y + rect1.height - d <= rect2.y)) {
        leftOrRight = false;
      }

      if (!leftOrRight) {
        rect2.y += d;

        if (rect1.intersects(rect2)) {
          break;
        }

        rect2.y -= d;
      }

      hasPartial = true;

      if (leftOrRight) {
        if (partialOnBottom) {
          if (tp.x - bp.x > 1) {
            noRight = !top;
            noLeft  = top;
          } else {
            noRight = top;
            noLeft  = !top;
          }
        }
      } else if (top) {
        noTop      = partialOnBottom;
        flatBottom = !partialOnBottom;
        flatTop    = partialOnBottom;
        noBottom   = !partialOnBottom;
      } else {
        noTop      = !partialOnBottom;
        noBottom   = partialOnBottom;
        flatBottom = partialOnBottom;
        flatTop    = !partialOnBottom;
      }

      modCount++;
    } while(false);

    return hasPartial;
  }

  @Override
  protected UIColor getFocusColor(iPlatformComponent pc, boolean always) {
    if (bottomComponent != null) {
      return null;
    }

    return super.getFocusColor(pc, always);
  }
}
