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
import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.border.UIBevelBorder;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UIBackgroundPainter;
import com.appnativa.rare.ui.painter.iPainter;

public class BoxStackedTabPainter extends BoxTabPainter {
  protected int largestTabSize;

  public BoxStackedTabPainter() {
    super();
    iconPosition = IconPosition.LEADING;
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    super.getPreferredSize(size);
  }

  @Override
  public void updateTabSizes() {
    super.updateTabSizes();

    int max = 0;
    int a[] = tabLayoutSizes;
    int len = (a == null)
              ? 0
              : a.length;

    for (int i = 0; i < len; i++) {
      max = Math.max(max, a[i]);
    }

    largestTabSize = max + ScreenUtils.platformPixels(24);
  }

  @Override
  protected PaintBucket createDefaultPainter() {
    PaintBucket pb = new PaintBucket(ColorUtils.getBackground());
    UIColor     fg = Platform.getUIDefaults().getColor("Rare.TabPane.tabForeground");

    if (fg == null) {
      fg = ColorUtils.getForeground();
    }

    pb.setForegroundColor(fg);

    return pb;
  }

  @Override
  protected PaintBucket createDefaultSelectedPainter() {
    PaintBucket pb = new PaintBucket();
    UIColor     bg = ColorUtils.getBackground();

    pb.setBackgroundPainter(new UIBackgroundPainter(bg.light(-30), bg));

    UIBevelBorder    bb;
    UICompoundBorder b = new UICompoundBorder(BorderUtils.TWO_POINT_EMPTY_BORDER,
                           bb = new UIBevelBorder(UIBevelBorder.LOWERED, false));
    UIColor[] ca = BorderUtils.getBevelColors(bg, false);

    bb.setColors(ca[0], ca[1]);
//    UICompoundBorder b = new UICompoundBorder(BorderUtils.TWO_POINT_EMPTY_BORDER, new UILineBorder(
//        UILineBorder.getDefaultLineColor(), ScreenUtils.PLATFORM_PIXELS_1, ScreenUtils.PLATFORM_PIXELS_6));
    pb.setBorder(b);

    return pb;
  }

  @Override
  protected Object getUIDefaults(StringBuilder sb, int sblen, boolean painter, String key) {
    Object o;

    sb.setLength(sblen);
    sb.append("stacked.").append(key);

    if (painter) {
      o = Platform.getUIDefaults().getBackgroundPainter(sb.toString());
    } else {
      o = Platform.getUIDefaults().get(sb.toString());
    }

    return o;
  }

  @Override
  protected void labelAdded(iTabLabel label) {
    super.labelAdded(label);
    label.setAlignment(HorizontalAlign.LEADING, VerticalAlign.CENTER);
  }

  @Override
  protected void layoutTab(iTabLabel tab, float x, float y, float width, float height, int index) {
    int w = tabSizes[index];

    x += (width - w) / 2;
    x -= (largestTabSize - w);
    super.layoutTab(tab, x, y, width, height, index);
  }

  protected void paintTab(iPlatformGraphics g, iTabLabel tab, int x, int y, int width, int height, int index) {
    if (index == getSelectedTab()) {
      selectedComponentPainter.paint(g, x, y, width, height, iPainter.UNKNOWN);
    } else {
      normalComponentPainter.paint(g, x, y, width, height, iPainter.UNKNOWN);
    }
  }

  @Override
  protected void setLocation(Location location) {
    switch(location) {
      case TOP :
        location = Location.LEFT;

        break;

      case BOTTOM :
        location = Location.RIGHT;

        break;

      case LEFT :
        location = Location.TOP;

        break;

      default :
        location = Location.BOTTOM;

        break;
    }

    super.setLocation(location);
  }

  protected void setupDefaultPainters() {}
}
