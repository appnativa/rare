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

package com.appnativa.rare.ui.tabpane;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UIBackgroundPainter;
import com.appnativa.rare.ui.painter.aUIBackgroundPainter;
import com.appnativa.rare.ui.painter.iGradientPainter.Direction;
import com.appnativa.rare.ui.painter.iPainter;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aBoxTabPainter extends aPlatformTabPainter {
  private Direction horizontalGradientDirection;
  private Direction horizontalSelectedGradientDirection;
  private boolean   borderPainted;

  public aBoxTabPainter() {
    super();

    StringBuilder sb = new StringBuilder();

    sb.append("Rare.TabPane.");

    int         sblen = sb.length();
    PaintBucket pb    = (PaintBucket) getUIDefaults(sb, sblen, false, "tabPainter");
    PaintBucket spb   = (PaintBucket) getUIDefaults(sb, sblen, false, "selectedTabPainter");

    if (spb == null) {
      spb = createDefaultSelectedPainter();
    }

    if (pb == null) {
      pb = createDefaultPainter();
    }

    setNormalPaint(pb);
    setSelectedPaint(spb);
    startOffset         = 0;
    iconPosition        = IconPosition.TOP_CENTER;
    alwaysShowMoreText  = true;
    supportsUniformTabs = true;
    setInsets();
  }

  @Override
  public void dispose() {
    super.dispose();
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    paintBackground(g, x, y, width, height);
    super.paint(g, x, y, width, height);

    if (borderPainted) {
      paintBorder(getPosition(), g, x, y, width, height);
    }
  }

  @Override
  public void setMoreButton(iActionComponent more) {
    super.setMoreButton(more);

    if (more != null) {
      more.setAlignment(HorizontalAlign.CENTER, VerticalAlign.CENTER);
    }
  }

  @Override
  public void setNormalPaint(PaintBucket pb) {
    super.setNormalPaint(pb);
    horizontalGradientDirection = null;

    if (pb.getBackgroundPainter() instanceof aUIBackgroundPainter) {
      aUIBackgroundPainter p = (aUIBackgroundPainter) pb.getBackgroundPainter();

      horizontalGradientDirection = p.getGradientDirection();

      if ((position != Location.TOP) && (position != Location.BOTTOM)) {
        p.rotateGradientDirection(true);
      }
    }
  }

  @Override
  public void setSelectedPaint(PaintBucket pb) {
    super.setSelectedPaint(pb);
    horizontalSelectedGradientDirection = null;

    if (selectedComponentPainter.getBackgroundPainter() instanceof aUIBackgroundPainter) {
      aUIBackgroundPainter p = (aUIBackgroundPainter) pb.getBackgroundPainter();

      horizontalSelectedGradientDirection = p.getGradientDirection();

      if ((position != Location.TOP) && (position != Location.BOTTOM)) {
        p.rotateGradientDirection(true);
      }
    }
  }

  @Override
  public void setSelectedTabBorderColor(UIColor fg) {
    super.setSelectedTabBorderColor(fg);

    if (fg != null) {
      tabBorderColor = fg;
    }
  }

  @Override
  public boolean isHandlesBottomRotation() {
    return true;
  }

  @Override
  public boolean isHandlesRightLeftRotation() {
    return true;
  }

  protected PaintBucket createDefaultPainter() {
    PaintBucket pb = new PaintBucket();

    pb.setBackgroundPainter(new UIBackgroundPainter(new UIColor[] { new UIColor(0xff3f3f3f), UIColor.BLACK },
            Direction.VERTICAL_TOP));

    UIColor fg = Platform.getUIDefaults().getColor("Rare.TabPane.tabForeground");

    if (fg == null) {
      fg = UIColor.WHITE;
    }

    pb.setForegroundColor(fg);

    return pb;
  }

  protected PaintBucket createDefaultSelectedPainter() {
    PaintBucket pb = new PaintBucket();

    pb.setBackgroundColor(new UIColor(0x40ffffff));

    return pb;
  }

  @Override
  protected iTabLabel createNewRenderer(UIAction a) {
    iTabLabel l = super.createNewRenderer(a);

    l.setAlignment(HorizontalAlign.CENTER, VerticalAlign.CENTER);

    return l;
  }

  @Override
  protected void locationChanged() {
    super.locationChanged();
    setInsets();

    if (horizontalSelectedGradientDirection != null) {
      aUIBackgroundPainter p = (aUIBackgroundPainter) selectedComponentPainter.getBackgroundPainter();

      p.setGradientDirection(horizontalSelectedGradientDirection);

      if ((position != Location.TOP) && (position != Location.BOTTOM)) {
        p.rotateGradientDirection(true);
      }
    }

    if (horizontalGradientDirection != null) {
      aUIBackgroundPainter p = (aUIBackgroundPainter) normalComponentPainter.getBackgroundPainter();

      p.setGradientDirection(horizontalGradientDirection);

      if ((position != Location.TOP) && (position != Location.BOTTOM)) {
        p.rotateGradientDirection(true);
      }
    }
  }

  protected void paintBackground(iPlatformGraphics g, float x, float y, float width, float height) {
    normalComponentPainter.paint(g, x, y, width, height, iPainter.UNKNOWN);
  }

  protected void paintBorder(Location loc, iPlatformGraphics g, float x, float y, float width, float height) {
    UIColor c = g.getColor();
    float   d = ScreenUtils.PLATFORM_PIXELS_1;

    g.setColor(tabBorderColor);

    switch(loc) {
      case TOP :
        g.drawLine(x, y + height - d, x + width, y + height - d);

        break;

      case BOTTOM :
        g.drawLine(x, y, x + width, y);

        break;

      case LEFT :
        g.drawLine(x + width - d, y, x + width - d, y + height);

        break;

      default :
        g.drawLine(x, y, x, y + height);

        break;
    }

    g.setColor(c);
  }

  @Override
  protected void paintTab(iPlatformGraphics g, iTabLabel tab, float x, float y, float width, float height, int index) {
    float dp1 = ScreenUtils.PLATFORM_PIXELS_1;

    if (index == getSelectedTab()) {
      switch(location) {
        case TOP :
          height -= dp1;

          break;

        case BOTTOM :
          height -= dp1;
          y      += dp1;

          break;

        case LEFT :
          width -= dp1;

          break;

        default :
          width -= dp1;
          x     += dp1;

          break;
      }

      selectedComponentPainter.paint(g, x, y, width, height, iPainter.UNKNOWN);
    }
  }

  protected void setInsets() {
    iPlatformBorder b = selectedPainter.getBorder();

    if (b != null) {
      textInsets = b.getBorderInsets(textInsets);
    } else {
      textInsets.set(0, 0, 0, 0);
    }

    float of = ScreenUtils.PLATFORM_PIXELS_4;

    textInsets.left   += of;
    textInsets.top    += of;
    textInsets.bottom += of;
    textInsets.right  += of;
  }

  protected Object getUIDefaults(StringBuilder sb, int sblen, boolean painter, String key) {
    Object o;

    sb.setLength(sblen);
    sb.append(key);

    if (painter) {
      o = Platform.getUIDefaults().getBackgroundPainter(sb.toString());
    } else {
      o = Platform.getUIDefaults().getPaintBucket(sb.toString());
    }

    return o;
  }

  public boolean isBorderPainted() {
    return borderPainted;
  }

  public void setBorderPainted(boolean borderPainted) {
    this.borderPainted = borderPainted;
  }
}
