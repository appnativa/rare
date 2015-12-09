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
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.GridCell;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UISimpleBackgroundPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTPrintableString;

import java.io.StringReader;

/**
 * A class with convenience color related methods in the UI* namespace.
 *
 * @author Don DeCoteau
 *
 */
public class UIColorHelper {
  public static int HSVToColor(int alpha, float[] hsv) {
    return ColorUtils.HSVToColor(alpha, hsv);
  }

  /**
   * Returns a color shade for a the specified colors
   *
   * @return a color object represented by the specified colors as an android
   *         color state list
   */
  public static UIColorShade getColorStateList(UIColor fg, UIColor disabled) {
    return PlatformHelper.getColorStateList(fg, disabled);
  }

  /**
   * Returns a color shade for a the specified colors
   *
   * @return a color object represented by the specified colors as an android
   *         color state list
   */
  public static UIColorShade getColorStateList(UIColor fg, UIColor disabled, UIColor pressed) {
    return PlatformHelper.getColorStateList(fg, disabled, pressed);
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

  /**
   * Gets the background color represented by the configuration string
   * @param color  the configuration string
   * @return a color object representing the color string
   */
  public static UIColor getBackgroundColor(SPOTPrintableString color) {
    return ColorUtils.getBackgroundColor(color);
  }

  /**
   * Gets the background color represented by the string
   * @param color  the color string
   * @return a color object representing the color string
   */
  public static UIColor getBackgroundColor(String color) {
    return ColorUtils.getBackgroundColor(color);
  }

  /**
   * Gets a background painter the will paint the specified color
   * @param color  the color string
   * @return a background painter the will paint the specified color
   */
  public static iBackgroundPainter getBackgroundPainter(String color) {
    return ColorUtils.getBackgroundPainter(color);
  }

  /**
   * Gets a background painter the will paint the specified color
   * @param bg  the color
   * @return a background painter the will paint the specified color
   */
  public static iBackgroundPainter getBackgroundPainter(UIColor bg) {
    return new UISimpleBackgroundPainter(bg);
  }

  /**
   * Gets the color represented by the string
   * @param color  the color string
   * @return a color object representing the color string
   */
  public static UIColor getColor(String color) {
    return ColorUtils.getColor(color);
  }

  /**
   * Gets the default foreground color
   * 
   * @return a color object representing the color string
   */
  public static UIColor getForeground() {
    return ColorUtils.getForeground();
  }

  /**
   * Gets a paint bucket for background painting from a configuration string
   *
   * @param bgColor the configuration string containing the background color information
   *
   * @return a paint bucket with the paints or null if the configuration does not specify any paints
   */
  public static PaintBucket getPaintBucket(SPOTPrintableString bgColor) {
    return getPaintBucket(bgColor, null);
  }

  /**
   * Gets a paint bucket for background painting from a configuration string
   *
   * @param bgColor the configuration string containing the background color information
   * @param pb a paint bucket object to hold the paints (can be null)
   *
   * @return a paint bucket with the paints or null if the configuration does not specify any paints
   */
  public static PaintBucket getPaintBucket(SPOTPrintableString bgColor, PaintBucket pb) {
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
   * Returns a paint bucket for the specified color string
   * @param color the color string
   * @return the paint bucket
   */
  public static PaintBucket getPaintBucket(String color) {
    return ColorUtils.getPaintBucket(color);
  }

  /**
   * Gets a paint bucket for background painting from a grid cell configuration
   *
   * @param context the context
   * @param gc the grid cell configuration containing the background color information
   *
   * @return a paint bucket with the paints or null if the configuration does not specify any paints
   */
  public static PaintBucket getPaintBucket(iWidget context, GridCell gc) {
    return getPaintBucket(context, gc, null);
  }

  /**
   * Gets a paint bucket for background painting from a grid cell configuration
   *
   * @param context the context
   * @param gc the grid cell configuration containing the background color information
   * @param pb a paint bucket object to hold the paints (can be null)
   *
   * @return a paint bucket with the paints or null if the configuration does not specify any paints
   */
  public static PaintBucket getPaintBucket(iWidget context, GridCell gc, PaintBucket pb) {
    if (pb == null) {
      pb = (gc == null)
           ? null
           : new PaintBucket();
    }

    if (gc != null) {
      ColorUtils.configure(context, gc, pb);
    }

    return pb;
  }

  /**
   * Gets a paint bucket for background painting from a string representing a grid cell configuration
   *
   * @param context the context
   * @param gridCell the string representing the grid cell configuration containing the background color information
   * @param pb a paint bucket object to hold the paints (can be null)
   *
   * @return a paint bucket with the paints or null if the configuration does not specify any paints
   */
  public static PaintBucket getPaintBucketForCellString(iWidget context, String gridCell, PaintBucket pb) {
    if (pb == null) {
      pb = (gridCell == null)
           ? null
           : new PaintBucket();
    }

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
