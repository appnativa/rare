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

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPath;
import com.appnativa.rare.ui.iPlatformShape;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.widget.iWidget;

public abstract class aUILineBorder extends aUIPlatformBorder implements Cloneable {
  protected static UILineBorder sharedBlackLineBorder;
  protected static UILineBorder sharedLineBorder;
  public static float           onePixelThickness = ScreenUtils.platformPixelsf(1.25f);
  protected String              lineStyle         = "solid";
  protected float               arcHeight;
  protected float               arcWidth;
  protected iPlatformPath       clipShape;
  protected iPlatformPath       paintShape;
  protected UIInsets            fixedBorderInsets;
  protected boolean             flatBottom;
  protected boolean             flatLeft;
  protected boolean             flatRight;
  protected boolean             flatTop;
  protected UIInsets            insets;
  protected UIColor             lineColor;
  protected UIStroke            lineStroke;
  protected boolean             noBottom;
  protected boolean             noLeft;
  protected boolean             noRight;
  protected boolean             noTop;
  protected UIInsets            padding;
  protected boolean             roundedCorners;
  protected float               thickness;
  protected boolean             has2MissingSides;

  /**
   * Constructs a new instance
   *
   * @param color
   *          {@inheritDoc}
   */
  public aUILineBorder(UIColor color) {
    this(color, onePixelThickness, false);
  }

  /**
   * Creates a line border with the specified color and thickness.
   *
   * @param color
   *          the color of the border
   * @param thickness
   *          the thickness of the border
   */
  public aUILineBorder(UIColor color, float thickness) {
    this(color, thickness, false);
  }

  /**
   * Creates a line border with the specified color, thickness, and corner
   * shape.
   *
   * @param color
   *          the color of the border
   * @param thickness
   *          the thickness of the border
   * @param roundedCorners
   *          whether or not border corners should be round
   * @since 1.3
   */
  public aUILineBorder(UIColor color, float thickness, boolean roundedCorners) {
    super();
    lineColor = color;
    setThicknessEx(thickness);
    this.roundedCorners = roundedCorners;

    if (roundedCorners) {
      arcHeight = arcWidth = thickness * 6;
    }
  }

  /**
   * Creates a line border with the specified color, thickness, and corner
   * shape.
   *
   * @param color
   *          the color of the border
   * @param thickness
   *          the thickness of the border
   * @param arc
   *          the arch
   * @since 1.3
   */
  public aUILineBorder(UIColor color, float thickness, float arc) {
    this(color, thickness, arc > 0);

    if (arc > 0) {
      arcHeight = arcWidth = arc;
    }
  }

  /**
   * Creates a line border with the specified color, thickness, and corner
   * shape.
   *
   * @param color
   *          the color of the border
   * @param thickness
   *          the thickness of the border
   * @param arcWidth
   *          the arc height
   * @param arcHeight
   *          the arc width
   */
  public aUILineBorder(UIColor color, float thickness, float arcWidth, float arcHeight) {
    this(color, thickness, (arcWidth > 0) || (arcHeight > 0));

    if (arcWidth > 0) {
      this.arcWidth = arcWidth;
    }

    if (arcHeight > 0) {
      this.arcHeight = arcHeight;
    }
  }

  @Override
  public void clip(iPlatformGraphics g, float x, float y, float width, float height) {
    if (!roundedCorners) {
      super.clip(g, x, y, width, height);

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
    return !isRectangular();
  }

  @Override
  public Object clone() {
    aUILineBorder b = (aUILineBorder) super.clone();

    if (padding != null) {
      b.padding = new UIInsets(padding);
    }

    b.clipShape  = null;
    b.paintShape = null;

    if (fixedBorderInsets != null) {
      b.fixedBorderInsets = new UIInsets(fixedBorderInsets);
    }

    return b;
  }

  public aUILineBorder copy() {
    return (aUILineBorder) clone();
  }

  @Override
  public float getArcHeight() {
    return arcHeight;
  }

  @Override
  public float getArcWidth() {
    return arcWidth;
  }

  @Override
  public UIInsets getBorderInsets(UIInsets insets) {
    if (insets == null) {
      insets = new UIInsets();
    }

    if (fixedBorderInsets != null) {
      insets.set(fixedBorderInsets);

      return insets;
    }

    calculateInsets(insets, padForArc);

    if (padding != null) {
      insets.addInsets(padding);
    }

    return insets;
  }

  @Override
  public UIInsets getBorderInsetsEx(UIInsets insets) {
    if (insets == null) {
      insets = new UIInsets();
    }

    calculateInsets(insets, false);

    return insets;
  }

  public UIColor getLineColor() {
    return (lineColor == null)
           ? getDefaultLineColor()
           : lineColor;
  }

  public String getLineStyle() {
    return lineStyle;
  }

  @Override
  public iPlatformPath getPath(iPlatformPath p, float x, float y, float width, float height, boolean forClip) {
    float left   = x;
    float top    = y;
    float right  = x + width;
    float bottom = y + height;
    float dx     = (thickness < 3)
                   ? (thickness < 2)
                     ? .625f
                     : onePixelThickness
                   : (thickness / 2f);
    float aw     = arcWidth;
    float ah     = arcHeight;
    float a      = 0;

    if (roundedCorners && forClip) {
      a = getClipingOffset();
    }

    left   += dx;
    top    += dx;
    right  -= dx;
    bottom -= dx;
    ah     += a;
    aw     += a;
    right  -= a;
    bottom -= a;

    if (noRight) {
      right = x + width;
    } else if (noLeft) {
      left = x;
    }

    if (noTop) {
      top = y;
    } else if (noBottom) {
      bottom = y + height;
    }

    return createBorderPath(p, left, top, right - left, bottom - top, aw, ah, forClip);
  }

  @Override
  public iPlatformShape getShape(iPlatformGraphics g, float x, float y, float width, float height) {
    if (paintShape != null) {
      paintShape.reset();
    }

    paintShape = getPath(paintShape, x, y, width, height, true);

    return paintShape;
  }

  /**
   * @return the thickness
   */
  public float getThickness() {
    return thickness;
  }

  @Override
  public boolean isFocusAware() {
    return true;
  }

  public boolean isMissingASide() {
    return noBottom || noTop || noRight || noLeft;
  }

  @Override
  public boolean isPadForArc() {
    return padForArc;
  }

  @Override
  public boolean isPaintLast() {
    return true;
  }

  @Override
  public boolean isRectangular() {
    return !roundedCorners;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean last) {
    if (last != isPaintLast()) {
      return;
    }

    UIColor color = getPaintColor(g.getComponent());

    if (color.getAlpha() == 0) {
      return;
    }

    UIStroke stroke = g.getStroke();
    UIColor  c      = g.getColor();

    g.setColor(color);

    if (lineStroke != null) {
      g.setStroke(lineStroke);
    } else {
      g.setStrokeWidth(thickness);
    }

    if (roundedCorners) {
      if (paintShape != null) {
        paintShape.reset();
      }

      paintShape = getPath(paintShape, x, y, width, height, false);
      g.drawShape(paintShape, 0, 0);
    } else {
      if (insets == null) {
        insets = new UIInsets();
      }

      float    left   = x;
      float    top    = y;
      float    right  = x + width;
      float    bottom = y + height;
      UIInsets in     = calculateInsets(insets, padForArc);

      paintLines(g, in, left, top, right, bottom);
    }

    g.setStroke(stroke);
    g.setColor(c);
  }

  public void setCornerArc(float arc) {
    if (arc > -1) {
      arcWidth       = arc;
      arcHeight      = arc;
      roundedCorners = arc > 0;
      modCount++;
    }
  }

  public aUILineBorder setFlatBottom(boolean flatBottom) {
    if (this.flatBottom == flatBottom) {
      return this;
    }

    if (((this == sharedLineBorder) || (this == sharedBlackLineBorder))) {
      return copy().setFlatBottom(flatBottom);
    }

    this.flatBottom = flatBottom;
    modCount++;

    return this;
  }

  public aUILineBorder setFlatLeft(boolean flatLeft) {
    if (this.flatLeft == flatLeft) {
      return this;
    }

    if (flatLeft && ((this == sharedLineBorder) || (this == sharedBlackLineBorder))) {
      return copy().setFlatLeft(flatLeft);
    }

    this.flatLeft = flatLeft;
    modCount++;

    return this;
  }

  public aUILineBorder setFlatRight(boolean flatRight) {
    if (this.flatRight == flatRight) {
      return this;
    }

    if (flatRight && ((this == sharedLineBorder) || (this == sharedBlackLineBorder))) {
      return copy().setFlatRight(flatRight);
    }

    this.flatRight = flatRight;
    modCount++;

    return this;
  }

  public aUILineBorder setFlatTop(boolean flatTop) {
    if (this.flatTop == flatTop) {
      return this;
    }

    if (((this == sharedLineBorder) || (this == sharedBlackLineBorder))) {
      return copy().setFlatTop(flatTop);
    }

    this.flatTop = flatTop;
    modCount++;

    return this;
  }

  /**
   * Sets the insets to use for the border. This will override the calculated
   * insets
   *
   * @param insets
   *          the insets to set
   *
   * @return a new instance if this was a the globally shared border or this
   *         instance
   */
  public aUILineBorder setInsets(UIInsets insets) {
    if (((this == sharedLineBorder) || (this == sharedBlackLineBorder))) {
      return copy().setInsets(insets);
    }

    if (insets != null) {
      this.fixedBorderInsets = (UIInsets) insets.clone();
    } else {
      this.fixedBorderInsets = null;
    }

    modCount++;

    return this;
  }

  public void setInsetsPadding(float top, float right, float bottom, float left) {
    if (padding == null) {
      padding = new UIInsets();
    }

    padding.set(top, right, bottom, left);
  }

  public aUILineBorder setLineColor(UIColor c) {
    if (this.lineColor.equals(c)) {
      return this;
    }

    if (((this == sharedLineBorder) || (this == sharedBlackLineBorder))) {
      return copy().setLineColor(c);
    }

    return setLineColorEx(c);
  }

  /**
   * Sets the line style
   *
   * @param style
   *          the line stroke (dotted, dashed, solid)
   * @return a new instance if this was a the globally shared border or this
   *         instance
   */
  public aUILineBorder setLineStyle(String style) {
    if ((style == null) || style.equals(lineStyle)) {
      return this;
    }

    if (((this == sharedLineBorder) || (this == sharedBlackLineBorder)) &&!"solid".equalsIgnoreCase(style)) {
      return copy().setLineStyle(style);
    }

    return setLineStyleEx(style);
  }

  public aUILineBorder setNoBottom(boolean noBottom) {
    if (this.noBottom == noBottom) {
      return this;
    }

    if (noBottom && ((this == sharedLineBorder) || (this == sharedBlackLineBorder))) {
      return copy().setNoBottom(noBottom);
    }

    this.flatBottom = noBottom;
    this.noBottom   = noBottom;
    modCount++;
    checkMissingSides();

    return this;
  }

  public aUILineBorder setNoLeft(boolean noLeft) {
    if (this.noLeft == noLeft) {
      return this;
    }

    if (noLeft && ((this == sharedLineBorder) || (this == sharedBlackLineBorder))) {
      return copy().setNoLeft(noLeft);
    }

    this.noLeft   = noLeft;
    this.flatLeft = noLeft;
    modCount++;
    checkMissingSides();

    return this;
  }

  public aUILineBorder setNoRight(boolean noRight) {
    if (this.noRight == noRight) {
      return this;
    }

    if (noRight && ((this == sharedLineBorder) || (this == sharedBlackLineBorder))) {
      return copy().setNoRight(noRight);
    }

    this.noRight   = noRight;
    this.flatRight = noRight;
    modCount++;
    checkMissingSides();

    return this;
  }

  public aUILineBorder setNoTop(boolean noTop) {
    if (this.noTop == noTop) {
      return this;
    }

    if (noTop && ((this == sharedLineBorder) || (this == sharedBlackLineBorder))) {
      return copy().setNoTop(noTop);
    }

    this.flatTop = noTop;
    this.noTop   = noTop;
    modCount++;
    checkMissingSides();

    return this;
  }

  /**
   * @param thickness
   *          the thickness to set
   */
  public aUILineBorder setThickness(float thickness) {
    if (thickness == this.thickness) {
      return this;
    }

    if (noTop && ((this == sharedLineBorder) || (this == sharedBlackLineBorder))) {
      return copy().setThickness(thickness);
    }

    return setThicknessEx(thickness);
  }

  public boolean usesPath() {
    return true;
  }

  protected void adjustStrokeForThickness() {
    if (lineStroke != null) {
      if ((lineStroke == UIStroke.SOLID_STROKE) || (lineStroke == UIStroke.DASHED_STROKE)
          || (lineStroke == UIStroke.DOTTED_STROKE)) {
        lineStroke = (UIStroke) lineStroke.clone();
      }

      lineStroke.setWidth(thickness);
    }
  }

  protected UIInsets calculateInsets(UIInsets insets, boolean pad) {
    int tw = (int) Math.floor(thickness);
    int th = tw;
    int tb = tw;

    if (pad) {
      tw += (arcWidth / 3);
      th += (arcHeight / 3);

      if (!flatBottom) {
        tb += (arcWidth / 3);
      }
    }

    if (flatTop) {
      th = noTop
           ? 0
           : th;
    }

    if (flatBottom) {
      tb = noBottom
           ? 0
           : tb;
    }

    if (insets != null) {
      insets.left   = noLeft
                      ? 0
                      : tw;
      insets.right  = noRight
                      ? 0
                      : tw;
      insets.top    = noTop
                      ? 0
                      : th;
      insets.bottom = noBottom
                      ? 0
                      : tb;
    }

    return insets;
  }

  protected iPlatformPath createBorderPath(iPlatformPath p, float x, float y, float width, float height, float aw,
          float ah, boolean clip) {
    if (has2MissingSides) {
      return createBorderPathWith2SidesMissing(p, x, y, width, height, aw, ah, clip);
    } else {
      if (clip) {
        p = BorderUtils.createLineBorderPath(p, width, height, aw, ah, flatBottom, flatTop, flatLeft, flatRight, false,
                false, false, false);
      } else {
        p = BorderUtils.createLineBorderPath(p, width, height, aw, ah, flatBottom, flatTop, flatLeft, flatRight, noTop,
                noBottom, noLeft, noRight);
      }

      p.translate(x, y);

      return p;
    }
  }

  protected iPlatformPath createBorderPathWith2SidesMissing(iPlatformPath p, float x, float y, float width,
          float height, float aw, float ah, boolean clip) {
    if (p == null) {
      p = BorderUtils.createPath();
    } else {
      p.reset();
    }

    if (noTop) {
      if (noRight) {
        if (clip) {
          p.moveTo(width, height);
          p.lineTo(aw, height);
          p.quadTo(0, height, 0, height - ah);
          p.lineTo(0, 0);
          p.lineTo(width, 0);
          p.lineTo(width, height);
        } else {
          p.moveTo(width, height);
          p.lineTo(aw, height);
          p.quadTo(0, height, 0, height - ah);
          p.lineTo(0, 0);
          p.moveTo(0, 0);
        }
      } else {
        if (clip) {
          p.moveTo(0, 0);
          p.lineTo(width, 0);
          p.lineTo(width, height - ah);
          p.quadTo(width, height, width - aw, height);
          p.lineTo(0, height);
          p.lineTo(0, 0);
        } else {
          p.moveTo(width, 0);
          p.lineTo(width, height - ah);
          p.quadTo(width, height, width - aw, height);
          p.lineTo(0, height);
          p.moveTo(0, height);
        }
      }
    } else {    // no bottom
      if (noRight) {
        if (clip) {
          p.moveTo(0, height);
          p.lineTo(0, ah);
          p.quadTo(0, 0, aw, 0);
          p.lineTo(width, 0);
          p.lineTo(width, height);
          p.lineTo(0, height);
        } else {
          p.moveTo(0, height);
          p.lineTo(0, ah);
          p.quadTo(0, 0, aw, 0);
          p.lineTo(width, 0);
          p.moveTo(width, 0);
        }
      } else {
        if (clip) {
          p.moveTo(0, 0);
          p.lineTo(width - aw, 0);
          p.quadTo(width, 0, width, ah);
          p.lineTo(width, height);
          p.lineTo(0, height);
          p.lineTo(0, 0);
        } else {
          p.moveTo(0, 0);
          p.lineTo(width - aw, 0);
          p.quadTo(width, 0, width, ah);
          p.lineTo(width, height);
          p.moveTo(width, height);
        }
      }
    }

    p.translate(x, y);

    return p;
  }

  protected float getClipingOffset() {
    return (thickness < 3)
           ? (thickness < 2)
             ? .25f
             : .5f
           : (thickness / 4f);
  }

  protected UIColor getDisabledLineColor() {
    return lineColor.getDisabledColor();
  }

  public UIColor getPaintColor(iPlatformComponent pc) {
    UIColor color = (pc == null)
                    ? null
                    : getFocusColor(pc, false);

    if (color == null) {
      color = lineColor;

      if ((pc != null) &&!pc.isEnabled()) {
        color = getDisabledLineColor();
      }
    }

    return color;
  }

  protected aUILineBorder setLineColorEx(UIColor c) {
    this.lineColor = c;
    modCount++;

    return this;
  }

  protected aUILineBorder setLineStyleEx(String style) {
    lineStyle = style;
    modCount++;
    lineStroke = UIStroke.getStroke(style);
    adjustStrokeForThickness();

    return this;
  }

  protected aUILineBorder setThicknessEx(float thickness) {
    modCount++;
    this.thickness = thickness;
    adjustStrokeForThickness();

    return this;
  }

  private void checkMissingSides() {
    int n = 0;

    if (noBottom) {
      n++;
    }

    if (noTop) {
      n++;
    }

    if (noRight) {
      n++;
    }

    if (noLeft) {
      n++;
    }

    has2MissingSides = n > 1;
  }

  public static aUILineBorder createBlackLineBorder() {
    if (sharedBlackLineBorder == null) {
      sharedBlackLineBorder = new UILineBorder(UIColor.BLACK);
    }

    return sharedBlackLineBorder;
  }

  public static aUILineBorder createBorder(iWidget context, UIColor color, float thickness, float arcWidth,
          float arcHeight) {
    if (color == null) {
      color = getDefaultLineColor();
    }

    if (sharedLineBorder == null) {
      sharedLineBorder = new UILineBorder(getDefaultLineColor(), onePixelThickness, 0, 0);
    }

    if (color.equals(sharedLineBorder.getLineColor())) {
      if ((thickness == onePixelThickness) && (arcWidth == 0) && (arcHeight == 0)) {
        return sharedLineBorder;
      }
    }

    if (color.equals(UIColor.BLACK)) {
      if ((thickness == onePixelThickness) && (arcWidth == 0) && (arcHeight == 0)) {
        return createBlackLineBorder();
      }
    }

    return new UILineBorder(color, thickness, arcWidth, arcHeight);
  }

  public static UILineBorder createFocusedBorder(float arcw, float arch) {
    UIColor c = Platform.getUIDefaults().getColor("Button.focus");

    if (c == null) {
      c = new UIColor(0, 0, 0, 128);
    }

    UILineBorder b = new UILineBorder(c, onePixelThickness, arcw, arch);

    b.setLineStyle("dotted");

    return b;
  }

  public static UIColor getDefaultLineColor() {
    UIColor c = Platform.getUIDefaults().getColor("Rare.LineBorder.color");

    if (c == null) {
      c = Platform.getUIDefaults().getColor("Rare.backgroundShadow");
    }

    if (c == null) {
      c = UIColor.GRAY;
    }

    return c;
  }

  public static void paintLines(iPlatformGraphics g, iPainter p, UIInsets in, float left, float top, float right,
                                float bottom) {
    if (in.top > 0) {
      p.paint(g, left, top, right - left, in.top, iPainter.HORIZONTAL);
    }

    if (in.left > 0) {
      p.paint(g, left, top, in.left, bottom - top, iPainter.VERTICAL);
    }

    if (in.bottom > 0) {
      p.paint(g, left, bottom - in.bottom, right - left, in.bottom, iPainter.HORIZONTAL);
    }

    if (in.right > 0) {
      p.paint(g, right - in.right, top, in.right, bottom - top, iPainter.VERTICAL);
    }
  }

  public static void paintLines(iPlatformGraphics g, UIInsets in, float left, float top, float right, float bottom) {
    UIColor c = g.getColor();

    if (c instanceof UIColorShade) {
      iPainter p = ((UIColorShade) c).getBackgroundPainter();

      if (p != null) {
        paintLines(g, p, in, left, top, right, bottom);

        return;
      }
    }

    if (in.top > 0) {
      g.fillRect(left, top, right - left, in.top);
    }

    if (in.left > 0) {
      g.fillRect(left, top, in.left, bottom - top);
    }

    if (in.bottom > 0) {
      g.fillRect(left, bottom - in.bottom, right - left, in.bottom);
    }

    if (in.right > 0) {
      g.fillRect(right - in.right, top, in.right, bottom - top);
    }
  }
}
