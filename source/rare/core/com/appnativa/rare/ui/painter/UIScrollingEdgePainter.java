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
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.painter.iGradientPainter.Direction;
import com.appnativa.rare.ui.painter.iGradientPainter.Type;

public class UIScrollingEdgePainter extends UISimpleBackgroundPainter {
  protected float                       shadowSize = ScreenUtils.PLATFORM_PIXELS_16;
  protected iPlatformPainter            horizontalTopPainter;
  protected iPlatformPainter            horizontalBottomPainter;
  protected iPlatformPainter            verticalLeftPainter;
  protected iPlatformPainter            verticalRightPainter;
  private static UIScrollingEdgePainter instance;
  private iScrollerSupport              scrollerSupport;
  private static boolean                paintVerticalScrollEdge;
  private static boolean                paintHorizontalScrollEdge;

  static {
    paintVerticalScrollEdge   = Platform.getUIDefaults().getBoolean("Rare.Table.paintVerticalScrollEdge", false);
    paintHorizontalScrollEdge = Platform.getUIDefaults().getBoolean("Rare.Table.paintHorizontalScrollEdge", true);
  }

  public UIScrollingEdgePainter() {
    this(ColorUtils.getBackground().isDarkColor()
         ? ColorUtils.getBackground().brighter()
         : ColorUtils.getBackground().darker());
  }

  public static UIScrollingEdgePainter getInstance() {
    if (instance == null) {
      UIColor start = Platform.getUIDefaults().getColor("Rare.Table.scrollEdgeStartColor");
      UIColor end   = Platform.getUIDefaults().getColor("Rare.Table.scrollEdgeStartColor");

      if (start == null) {
        start = ColorUtils.getBackground();
        start = start.isDarkColor()
                ? start.brighter()
                : start.darker();
        start = start.alpha(92);
      }

      if (end == null) {
        end = start.alpha(5);
      }

      instance = new UIScrollingEdgePainter(start, end);
    }

    return instance;
  }

  public UIScrollingEdgePainter(UIColor color) {
    super(color);

    UIColor start = color.alpha(92);
    UIColor end   = color.alpha(5);

    createDefaultGradientPainters(start, end);
  }

  public UIScrollingEdgePainter(UIColor start, UIColor end) {
    super(start);
    createDefaultGradientPainters(start, end);
  }

  public void createDefaultGradientPainters(UIColor start, UIColor end) {
    verticalLeftPainter     = Platform.getUIDefaults().getPainter("Rare.Table.verticalLeftEdgePainter");
    verticalRightPainter    = Platform.getUIDefaults().getPainter("Rare.Table.verticalRightEdgePainter");
    horizontalTopPainter    = Platform.getUIDefaults().getPainter("Rare.Table.horizontalTopEdgePainter");
    horizontalBottomPainter = Platform.getUIDefaults().getPainter("Rare.Table.horizontalBottomEdgePainter");

    UIColor[] colors       = new UIColor[] { start, end };
    float[]   distribution = new float[] { .4f, 1f };

//    UIColor[] colors = new UIColor[] { start, end, end };
//    float[] distribution = new float[] { .4f, .8f, 1f };
    if (paintVerticalScrollEdge) {
      if (horizontalBottomPainter == null) {
        horizontalBottomPainter = new UIBackgroundPainter(Type.LINEAR, Direction.VERTICAL_BOTTOM, colors, distribution,
                100);
      }

      if (horizontalTopPainter == null) {
        horizontalTopPainter = new UIBackgroundPainter(Type.LINEAR, Direction.VERTICAL_TOP, colors, distribution, 100);
      }
    }

    if (paintHorizontalScrollEdge) {
      if (verticalLeftPainter == null) {
        verticalLeftPainter = new UIBackgroundPainter(Type.LINEAR, Direction.HORIZONTAL_LEFT, colors, distribution,
                100);
      }

      if (verticalRightPainter == null) {
        verticalRightPainter = new UIBackgroundPainter(Type.LINEAR, Direction.HORIZONTAL_RIGHT, colors, distribution,
                100);
      }
    }
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {
    paint(g, x, y, width, height, true);
    paint(g, x, y, width, height, false);
  }

  public void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean horizontal) {
    iScrollerSupport s = scrollerSupport;

    if (s == null) {
      iPlatformComponent c = g.getComponent();

      if (!(c instanceof iScrollerSupport)) {
        return;
      }

      s = (iScrollerSupport) c;
    }

    if (horizontal) {
      if (!s.isAtBottomEdge()) {
        if (horizontalBottomPainter != null) {
          horizontalBottomPainter.paint(g, x, y + height - shadowSize, width, shadowSize, iPainter.HORIZONTAL);
        }
      }

      if (!s.isAtTopEdge()) {
        if (horizontalTopPainter != null) {
          horizontalTopPainter.paint(g, x, y, width, shadowSize, iPainter.HORIZONTAL);
        }
      }
    } else {
      if (!s.isAtLeftEdge()) {
        if (verticalLeftPainter != null) {
          verticalLeftPainter.paint(g, x, y, shadowSize, height, iPainter.VERTICAL);
        }
      }

      if (!s.isAtRightEdge()) {
        if (verticalRightPainter != null) {
          verticalRightPainter.paint(g, x + width - shadowSize, y, shadowSize, height, iPainter.VERTICAL);
        }
      }
    }
  }

  public float getShadowSize() {
    return shadowSize;
  }

  public void setShadowSize(float shadowSize) {
    this.shadowSize = shadowSize;
  }

  public iPlatformPainter getHorizontalTopPainter() {
    return horizontalTopPainter;
  }

  public void setHorizontalTopPainter(iPlatformPainter horizontalTopPainter) {
    this.horizontalTopPainter = horizontalTopPainter;
  }

  public iPlatformPainter getHorizontalBottomPainter() {
    return horizontalBottomPainter;
  }

  public void setHorizontalBottomPainter(iPlatformPainter horizontalBottomPainter) {
    this.horizontalBottomPainter = horizontalBottomPainter;
  }

  public iPlatformPainter getVerticalLeftPainter() {
    return verticalLeftPainter;
  }

  public void setVerticalLeftPainter(iPlatformPainter verticalLeftPainter) {
    this.verticalLeftPainter = verticalLeftPainter;
  }

  public iPlatformPainter getVerticalRightPainter() {
    return verticalRightPainter;
  }

  public void setVerticalRightPainter(iPlatformPainter verticalRightPainter) {
    this.verticalRightPainter = verticalRightPainter;
  }

  public iScrollerSupport getScrollerSupport() {
    return scrollerSupport;
  }

  public void setScrollerSupport(iScrollerSupport scrollerSupport) {
    this.scrollerSupport = scrollerSupport;
  }

  public static boolean isPaintVerticalScrollEdge() {
    return paintVerticalScrollEdge;
  }

  public static void setPaintVerticalScrollEdge(boolean paintVerticalScrollEdge) {
    UIScrollingEdgePainter.paintVerticalScrollEdge = paintVerticalScrollEdge;
  }

  public static boolean isPaintHorizontalScrollEdge() {
    return paintHorizontalScrollEdge;
  }

  public static void setPaintHorizontalScrollEdge(boolean paintHorizontalScrollEdge) {
    UIScrollingEdgePainter.paintHorizontalScrollEdge = paintHorizontalScrollEdge;
  }
}
