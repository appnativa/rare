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

import java.util.ArrayList;
import java.util.List;

public abstract class aLineHelper {
  protected static Line STANDARD_LINE = new Line(StrokeStyle.SOLID, UIScreen.platformPixels(1), null, 0, 0);
  protected boolean     horizontal    = true;
  protected float       thickness     = ScreenUtils.platformPixelsf(1);
  protected int         position      = UIConstants.CENTER;
  protected List<Line>  lines;
  private int           padding;

  /**
   * Line stroke style
   */
  public static enum StrokeStyle { SOLID, DOTTED, DASHED }

  /**
   * Constructs a new instance
   *
   * @param horizontal
   *          true for horizontal; false for vertical
   */
  public aLineHelper(boolean horizontal) {
    this.horizontal = horizontal;
  }

  public void addLine(Line line) {
    if (line == null) {
      return;
    }

    if (lines == null) {
      lines = new ArrayList<Line>();
    }

    lines.add(line);
    thickness = -1;
  }

  public boolean isStandardLine() {
    return (lines == null) || ((lines.size() == 1) && getLine(0).standard);
  }

  public void dispose() {
    if (lines != null) {
      lines.clear();
      lines = null;
    }
  }

  public Line createLine() {
    return new Line();
  }

  public Line createLine(String style, float thickness, UIColor color, int loff, int roff) {
    StrokeStyle s = null;

    if ("solid".equals(style)) {
      s = StrokeStyle.SOLID;
    } else if ("dotted".equals(style)) {
      s = StrokeStyle.DOTTED;
    } else if ("dashed".equals(style)) {
      s = StrokeStyle.DASHED;
    }

    return new Line(s, thickness, color, loff, roff);
  }

  public Line createLine(StrokeStyle style, float thickness, UIColor color, int loff, int roff) {
    return new Line(style, thickness, color, loff, roff);
  }

  public void paint(iPlatformGraphics g, float x, float y, float w, float h) {
    if (thickness == -1) {
      calculateThickness();
    }

    switch(position) {
      case UIConstants.CENTER :
        y = horizontal
            ? ((h - thickness) / 2)
            : 0;
        x = horizontal
            ? 0
            : ((w - thickness) / 2);

        break;

      case UIConstants.BOTTOM :
        y = horizontal
            ? h - thickness
            : 0;
        x = horizontal
            ? 0
            : w - thickness;

        break;

      default :
        break;
    }

    if (padding != 0) {
      if (horizontal) {
        w -= padding * 2;
        x += padding;
      } else {
        h -= padding * 2;
        y += padding;
      }
    }

    if (y < 0) {
      y = 0;
    }

    if (x < 0) {
      x = 0;
    }

    if (lines == null) {
      STANDARD_LINE.paintStandard(g, x, y, w, h, horizontal);

      return;
    }

    int  len = lines.size();
    Line line;

    for (int i = 0; i < len; i++) {
      line = lines.get(i);

      if (horizontal) {
        line.paintHorizontal(g, x, y, w);
        y += line.getThickness();
      } else {
        line.paintVertical(g, x, y, h);
        x += line.getThickness();
      }
    }
  }

  public Line removeLine(int pos) {
    return (lines == null)
           ? null
           : lines.remove(pos);
  }

  public void removeLines() {
    lines = null;
  }

  /**
   * Sets the orientation of the line
   *
   * @param horizontal
   *          true for a horizontal line; false for a vertical line
   */
  public void setHorizontal(boolean horizontal) {
    this.horizontal = horizontal;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public Line getLine(int pos) {
    return (lines == null)
           ? null
           : lines.get(pos);
  }

  public int getPosition() {
    return position;
  }

  public static UIStroke getStroke(String style, float thickness) {
    StrokeStyle s;

    if ("dotted".equals(style)) {
      s = StrokeStyle.DOTTED;
    } else if ("dashed".equals(style)) {
      s = StrokeStyle.DASHED;
    } else {
      s = StrokeStyle.SOLID;
    }

    return getStroke(s, thickness);
  }

  public static UIStroke getStroke(StrokeStyle style, float thickness) {
    float th = thickness;

    if (style == null) {
      return new UIStroke(th);
    } else {
      switch(style) {
        case DASHED :
          return new UIStroke(th, new float[] { 1f, 1f }, 0f);

        case DOTTED :
          return new UIStroke(th, new float[] { 2f, 1f }, 0f);

        default :
          return new UIStroke(th);
      }
    }
  }

  public float getThickness() {
    if (thickness == -1) {
      calculateThickness();
    }

    return thickness;
  }

  public boolean hasValue() {
    return true;
  }

  /**
   * Returns whether the line is horizontal
   *
   * @return true for horizontal; false for vertical
   */
  public boolean isHorizontal() {
    return this.horizontal;
  }

  protected void calculateThickness() {
    if (lines == null) {
      thickness = 2;
    } else {
      int len = lines.size();

      thickness = 0;

      for (int i = 0; i < len; i++) {
        thickness += Math.ceil(lines.get(i).getThickness());
      }
    }

    thicknessRecalculated();
  }

  protected abstract void thicknessRecalculated();

  public int getPadding() {
    return padding;
  }

  public void setPadding(int padding) {
    this.padding = padding;
  }

  public static class Line {
    protected int      leftOffset  = 0;
    protected int      rightOffset = 0;
    protected float    thickness   = UIScreen.platformPixels(1);
    protected UIColor  color;
    protected boolean  standard;
    protected UIStroke stroke;

    public Line() {
      standard = true;
    }

    public Line(StrokeStyle style, float thickness, UIColor color, int loff, int roff) {
      if (thickness < 0) {
        thickness = 0;
      }

      this.thickness = thickness;
      this.color     = color;
      leftOffset     = loff;
      rightOffset    = roff;
      setStyle(style);
    }

    public void paintHorizontal(iPlatformGraphics g, float x, float y, float w) {
      if (standard) {
        paintStandard(g, x, y, w, 0, true);

        return;
      }

      UIColor c = (color == null)
                  ? Platform.getUIDefaults().getColor("Rare.LineBorder.color")
                  : color;

      if (c == null) {
        c = Platform.getUIDefaults().getColor("Rare.backgroundShadow");
      }

      w -= (leftOffset + rightOffset);
      x += leftOffset;
      g.setColor(c);

      for (int i = 0; i < thickness; i++) {
        g.drawLine(x, y + i, x + w - 1, y + i);
      }
    }

    public void paintStandard(iPlatformGraphics g, float x, float y, float w, float h, boolean horizontal) {
      UIColor top, bottom;

      if (color != null) {
        top    = color;
        bottom = top.brighter();
      } else {
        top    = Platform.getUIDefaults().getColor("Rare.backgroundShadow");
        bottom = Platform.getUIDefaults().getColor("Rare.backgroundLtHighlight");
      }

      if (horizontal) {
        w -= (leftOffset + rightOffset);
        x += leftOffset;

        float dy = this.thickness;

        g.setColor(top);
        g.fillRect(x, y, w, dy);
        g.setColor(bottom);
        g.fillRect(x, y + dy, w, dy);
      } else {
        h -= (leftOffset + rightOffset);
        y += leftOffset;

        float dx = this.thickness;

        g.setColor(top);
        g.fillRect(x, y, dx, h);
        g.setColor(bottom);
        g.fillRect(x + dx, y, dx, h);
      }
    }

    public void paintVertical(iPlatformGraphics g, float x, float y, float h) {
      if (standard) {
        paintStandard(g, x, y, 0, h, false);

        return;
      }

      h -= (leftOffset + rightOffset);
      y += leftOffset;

      UIColor c = (color == null)
                  ? Platform.getUIDefaults().getColor("Rare.backgroundShadow")
                  : color;

      g.setColor(c);

      for (int i = 0; i < thickness; i++) {
        g.drawLine(x + i, y, x + i, y + h - 1);
      }
    }

    public void setColor(UIColor color) {
      this.color = color;
    }

    public void setLeftOffset(int leftOffset) {
      this.leftOffset = leftOffset;
    }

    public void setRightOffset(int rightOffset) {
      this.rightOffset = rightOffset;
    }

    public void setStyle(StrokeStyle style) {
      float th = (thickness < 2)
                 ? 0
                 : 1.25f;

      if (style == null) {
        stroke   = new UIStroke(th);
        standard = true;
      } else {
        switch(style) {
          case DASHED :
            stroke = new UIStroke(th, new float[] { 1f, 1f }, 0f);

            break;

          case DOTTED :
            stroke = new UIStroke(th, new float[] { 2f, 1f }, 0f);

            break;

          default :
            stroke = new UIStroke(th);

            break;
        }
      }
    }

    public void setThickness(float thickness) {
      this.thickness = thickness;
    }

    public UIColor getColor() {
      return color;
    }

    public int getLeftOffset() {
      return leftOffset;
    }

    public int getRightOffset() {
      return rightOffset;
    }

    public float getThickness() {
      return standard
             ? thickness * 2
             : thickness;
    }
  }
}
