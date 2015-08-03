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
import com.appnativa.rare.spot.GridCell;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UISimpleBackgroundPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTPrintableString;

import java.io.StringReader;

public class UIColorHelper {
  public static int HSVToColor(int alpha, float[] hsv) {
    return ColorUtils.HSVToColor(alpha, hsv);
  }

  public static PaintBucket configure(iWidget context, GridCell gc, PaintBucket pb) {
    return ColorUtils.configure(context, gc, pb);
  }

  public static void configureBackgroundColor(iPlatformComponent comp, SPOTPrintableString bgColor) {
    ColorUtils.configureBackgroundPainter(comp, bgColor);
  }

  public static void configureBackgroundColor(iWidget widget, SPOTPrintableString bgColor) {
    ColorUtils.configureBackgroundPainter(widget, bgColor);
  }

  public static PaintBucket configureBackgroundColor(SPOTPrintableString bgColor, PaintBucket pb) {
    if ((bgColor == null) || (bgColor.getValue() == null)) {
      return null;
    }

    if (pb == null) {
      pb = new PaintBucket();
    }

    ColorUtils.configureBackgroundPainter(pb, bgColor);

    return pb;
  }

  /**
   * Updates a color defined in the UI properties
   * @param name the name of the color
   * @param value the new value
   */
  public static void updateColor(String name, Object value) {
    ColorUtils.updateColor(name, value);
  }

  public static int setAlpha(int argb, int alpha) {
    return ColorUtils.setAlpha(argb, alpha);
  }

  public static UIColor getBackground() {
    return ColorUtils.getBackground();
  }

  public static UIColor getBackgroundColor(SPOTPrintableString color) {
    return ColorUtils.getBackgroundColor(color);
  }

  public static UIColor getBackgroundColor(String color) {
    return ColorUtils.getBackgroundColor(color);
  }

  public static iBackgroundPainter getBackgroundPainter(String color) {
    return ColorUtils.getBackgroundPainter(color);
  }

  public static iBackgroundPainter getBackgroundPainter(UIColor bg) {
    return new UISimpleBackgroundPainter(bg);
  }

  public static UIColor getColor(String color) {
    return ColorUtils.getColor(color);
  }

  public static UIColor getDisabledForeground() {
    return ColorUtils.getDisabledForeground();
  }

  public static UIColor getForeground() {
    return ColorUtils.getForeground();
  }

  public static PaintBucket getPaintBucket(SPOTPrintableString bgColor) {
    if ((bgColor == null) || (bgColor.getValue() == null)) {
      return null;
    }

    PaintBucket pb = new PaintBucket();

    ColorUtils.configureBackgroundPainter(pb, bgColor);

    return pb;
  }

  public static PaintBucket getPaintBucket(String color) {
    return ColorUtils.getPaintBucket(color);
  }

  public static PaintBucket getPaintBucket(iWidget context, GridCell cell) {
    PaintBucket pb = (cell == null)
                     ? null
                     : new PaintBucket();

    if (cell != null) {
      ColorUtils.configure(context, cell, pb);
    }

    return pb;
  }

  public static PaintBucket getPaintBucketForCellString(iWidget context, String gridCell) {
    PaintBucket pb = (gridCell == null)
                     ? null
                     : new PaintBucket();

    if (gridCell != null) {
      GridCell gc = new GridCell();

      try {
        DataParser.loadSPOTObjectSDF(context, new StringReader(gridCell.trim()), gc, null, null);
      } catch(Exception ex) {
        Platform.ignoreException("Invalid GridCell definition", ex);
      }

      ColorUtils.configure(context, gc, pb);
    }

    return pb;
  }
}
