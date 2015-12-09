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

import java.util.ArrayList;
import java.util.List;

public class ColorPalette {
  private static ColorPalette colorPalette16;
  private static ColorPalette colorPalette40;
  private static ColorPalette grayPaletteGray16;
  private UIColor             colors[];
  private int                 columns;
  private String              name;
  private int                 rows;

  public ColorPalette() {}

  public ColorPalette(UIColor[] colors) {
    super();
    this.colors = colors;
  }

  public ColorPalette(String name, UIColor[] colors, int columns, int rows) {
    super();
    this.name    = name;
    this.colors  = colors;
    this.rows    = rows;
    this.columns = columns;
  }

  public RenderableDataItem createListItem(UIColor color) {
    if ((color == null) || (color.getAlpha() == 0)) {
      return new RenderableDataItem(null, null, new ColorIcon(null));
    }

    for (int i = 0; i < colors.length; i++) {
      if (color.equals(colors[i])) {
        return new RenderableDataItem(color.toString(), color, new ColorIcon(color));
      }
    }

    return new RenderableDataItem(color.toString(), color, new ColorIcon(color));
  }

  public List<RenderableDataItem> createListItems() {
    UIColor[]                     a    = colors;
    int                           len  = a.length;
    ArrayList<RenderableDataItem> list = new ArrayList<RenderableDataItem>();

    for (int i = 0; i < len; i++) {
      list.add(new RenderableDataItem(a[i].toString(), a[i], new ColorIcon(a[i])));
    }

    return list;
  }

  public void setColumns(int columns) {
    this.columns = columns;
  }

  public void setMatrix(int columns, int rows) {
    this.columns = columns;
    this.rows    = rows;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPalette(UIColor[] colors) {
    this.colors = colors;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public static ColorPalette getColorPalette16() {
    if (colorPalette16 == null) {
      UIColor a[] = new UIColor[16];

      a[0]           = new UIColorShade(UIColor.BLACK, "black");
      a[1]           = new UIColorShade(ColorUtils.getColor("maroon"), "maroon");
      a[2]           = new UIColorShade(UIColor.GREEN, "green");
      a[3]           = new UIColorShade(ColorUtils.getColor("olive"), "olive");
      a[4]           = new UIColorShade(ColorUtils.getColor("navy"), "navy");
      a[5]           = new UIColorShade(ColorUtils.getColor("purple"), "purple");
      a[6]           = new UIColorShade(ColorUtils.getColor("teal"), "teal");
      a[7]           = new UIColorShade(ColorUtils.getColor("silver"), "silver");
      a[8]           = new UIColorShade(UIColor.GRAY, "gray");
      a[9]           = new UIColorShade(UIColor.RED, "red");
      a[10]          = new UIColorShade(ColorUtils.getColor("lime"), "lime");
      a[11]          = new UIColorShade(UIColor.YELLOW, "yellow");
      a[12]          = new UIColorShade(UIColor.BLUE, "blue");
      a[13]          = new UIColorShade(ColorUtils.getColor("fuchsia"), "fuchsia");
      a[14]          = new UIColorShade(ColorUtils.getColor("aqua"), "aqua");
      a[15]          = new UIColorShade(UIColor.WHITE, "white");
      colorPalette16 = new ColorPalette("Color 16", a, 8, 5);;
    }

    return grayPaletteGray16;
  }

  public static ColorPalette getColorPalette40() {
    if (colorPalette40 == null) {
      UIColor[] colors = new UIColor[] {
        new UIColor(0, 0, 0), new UIColor(153, 51, 0), new UIColor(51, 51, 0), new UIColor(0, 51, 0),
        new UIColor(0, 51, 102), new UIColor(0, 0, 128), new UIColor(51, 51, 153), new UIColor(51, 51, 51),
        new UIColor(128, 0, 0), new UIColor(255, 102, 0), new UIColor(128, 128, 0), new UIColor(0, 128, 0),
        new UIColor(0, 128, 128), new UIColor(0, 0, 255), new UIColor(102, 102, 153), new UIColor(128, 128, 128),
        new UIColor(255, 0, 0), new UIColor(255, 153, 0), new UIColor(153, 204, 0), new UIColor(51, 153, 102),
        new UIColor(51, 204, 204), new UIColor(51, 102, 255), new UIColor(128, 0, 128), new UIColor(153, 153, 153),
        new UIColor(255, 0, 255), new UIColor(255, 204, 0), new UIColor(255, 255, 0), new UIColor(0, 255, 0),
        new UIColor(0, 255, 255), new UIColor(0, 204, 255), new UIColor(153, 51, 102), new UIColor(192, 192, 192),
        new UIColor(255, 153, 204), new UIColor(255, 204, 153), new UIColor(255, 255, 153), new UIColor(204, 255, 204),
        new UIColor(204, 255, 255), new UIColor(153, 204, 255), new UIColor(204, 153, 255), new UIColor(255, 255, 255)
      };

      colorPalette40 = new ColorPalette("40 Color", ColorUtils.createNamedShades(colors), 8, 5);
    }

    return colorPalette40;
  }

  public UIColor[] getColors() {
    return colors;
  }

  public int getColumns() {
    return columns;
  }

  public static ColorPalette getGrayPalette16() {
    if (grayPaletteGray16 == null) {
      UIColor a[] = new UIColor[16];

      for (int i = 0; i < 16; i++) {
        int n = i * 16;

        a[i] = new UIColor(n, n, n);
      }

      a                 = ColorUtils.createNamedShades(a);
      grayPaletteGray16 = new ColorPalette("Gray 16", a, 8, 5);;
    }

    return grayPaletteGray16;
  }

  public String getName() {
    return name;
  }

  public int getRows() {
    return rows;
  }

  public void dispose() {
  }
}
