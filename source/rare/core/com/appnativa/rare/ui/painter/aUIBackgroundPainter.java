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

import com.appnativa.rare.converters.Conversions;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.util.SNumber;

import java.util.Locale;

/**
 * The default background painter that handles painting gradients
 *
 * @author Don DeCoteau
 */
public abstract class aUIBackgroundPainter extends aUIPlatformPainter implements iGradientPainter {
  protected Direction    gradientDirection = Direction.VERTICAL_TOP;
  protected int          gradientMagnitude = 100;
  protected boolean      alwaysFill;
  protected BGCOLOR_TYPE bgColorType;
  protected UIColor[]    gradientColors;
  protected float[]      gradientDistribution;
  protected float        gradientRadius;
  protected Type         gradientType;
  protected boolean      hasNull;
  private String         _toString;
  private int            toStringModCount;
  protected UIColor      backgroundColor;
  private UIPoint        startPoint;
  private UIRectangle    calcRect;

  protected enum BGCOLOR_TYPE {
    DARK, LITE, MIDDLE, DARK_DARK
  }

  /** Creates a new instance of BackgroundRenderer */
  public aUIBackgroundPainter() {}

  /**
   * Creates a new instance of BackgroundRenderer
   *
   * @param bg
   *          the background color
   */
  public aUIBackgroundPainter(UIColor bg) {
    backgroundColor = bg;
  }

  /**
   * Creates a new instance of BackgroundRenderer
   *
   * @param start
   *          the start color
   * @param end
   *          the end color
   */
  public aUIBackgroundPainter(UIColor start, UIColor end) {
    this(start, end, Direction.VERTICAL_TOP);
  }

  @Override
  public UIColor getBackgroundColor() {
    if (backgroundColor != null) {
      return backgroundColor;
    }

    if ((gradientColors != null) && (gradientColors.length > 0)) {
      return gradientColors[0];
    }

    return null;
  }

  /**
   * Creates a new instance of BackgroundRenderer
   *
   * @param colors
   *          the gradient colors
   * @param direction
   *          the direction
   */
  public aUIBackgroundPainter(UIColor[] colors, Direction direction) {
    gradientType      = Type.LINEAR;
    gradientDirection = (direction == null)
                        ? Direction.VERTICAL_TOP
                        : direction;
    setGradientColors(colors);
  }

  /**
   * Creates a background gradient painter
   *
   * @param start
   *          the start color
   * @param end
   *          the end color
   * @param direction
   *          the direction
   */
  public aUIBackgroundPainter(UIColor start, UIColor end, Direction direction) {
    setGradientPaint(start, end, direction);
  }

  /**
   * Creates a background gradient painter
   *
   * @param type
   *          the type of gradient
   * @param colors
   *          the colors
   * @param direction
   *          the direction
   * @param distribution
   *          the distribution
   * @param magnitude
   *          the magnitude
   */
  public aUIBackgroundPainter(Type type, Direction direction, UIColor[] colors, float[] distribution, int magnitude) {
    setGradientPaint(type, direction, colors, distribution, magnitude);
  }

  @Override
  public iBackgroundPainter alpha(int alpha) {
    if (isSingleColorPainter()) {
      return super.alpha(alpha);
    }

    aUIBackgroundPainter p      = (aUIBackgroundPainter) clone();
    UIColor[]            colors = getGradientColors();

    if (colors != null) {
      UIColor[] a = new UIColor[colors.length];

      for (int i = 0; i < colors.length; i++) {
        if (a[i] != null) {
          a[i] = colors[i].alpha(alpha);
        }
      }

      p.setGradientColors(a);
    }

    return null;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (obj instanceof aUIBackgroundPainter) {
      return ((UIBackgroundPainter) obj).toString().equals(toString());
    }

    return false;
  }

  public void flipGradientDirection() {
    switch(gradientDirection) {
      case DIAGONAL_BOTTOM_LEFT :
        gradientDirection = Direction.DIAGONAL_TOP_RIGHT;

        break;

      case DIAGONAL_BOTTOM_RIGHT :
        gradientDirection = Direction.DIAGONAL_TOP_LEFT;

        break;

      case DIAGONAL_TOP_LEFT :
        gradientDirection = Direction.DIAGONAL_BOTTOM_RIGHT;

        break;

      case DIAGONAL_TOP_RIGHT :
        gradientDirection = Direction.DIAGONAL_BOTTOM_LEFT;

        break;

      case HORIZONTAL_LEFT :
        gradientDirection = Direction.HORIZONTAL_RIGHT;

        break;

      case HORIZONTAL_RIGHT :
        gradientDirection = Direction.HORIZONTAL_LEFT;

        break;

      case VERTICAL_TOP :
        gradientDirection = Direction.VERTICAL_BOTTOM;

        break;

      case VERTICAL_BOTTOM :
        gradientDirection = Direction.VERTICAL_TOP;

        break;

      case CENTER :
        return;
    }

    clearCache();
  }

  public void setBackgroundColor(UIColor bg) {
    backgroundColor = bg;
    clearCache();
  }

  @Override
  public int hashCode() {
    int hash = 5;

    hash = 29 * hash + (int)toString().hashCode();

    return hash;
  }

  public void rotateGradientDirection(boolean right) {
    switch(gradientDirection) {
      case DIAGONAL_BOTTOM_LEFT :
        gradientDirection = right
                            ? Direction.DIAGONAL_TOP_LEFT
                            : Direction.DIAGONAL_BOTTOM_RIGHT;

        break;

      case DIAGONAL_BOTTOM_RIGHT :
        gradientDirection = right
                            ? Direction.DIAGONAL_BOTTOM_LEFT
                            : Direction.DIAGONAL_TOP_RIGHT;

        break;

      case DIAGONAL_TOP_LEFT :
        gradientDirection = right
                            ? Direction.DIAGONAL_TOP_RIGHT
                            : Direction.DIAGONAL_BOTTOM_LEFT;

        break;

      case DIAGONAL_TOP_RIGHT :
        gradientDirection = right
                            ? Direction.DIAGONAL_BOTTOM_RIGHT
                            : Direction.DIAGONAL_TOP_LEFT;

        break;

      case HORIZONTAL_LEFT :
        gradientDirection = right
                            ? Direction.VERTICAL_TOP
                            : Direction.VERTICAL_BOTTOM;

        break;

      case HORIZONTAL_RIGHT :
        gradientDirection = right
                            ? Direction.VERTICAL_BOTTOM
                            : Direction.VERTICAL_TOP;

        break;

      case VERTICAL_TOP :
        gradientDirection = right
                            ? Direction.HORIZONTAL_LEFT
                            : Direction.HORIZONTAL_RIGHT;

        break;

      case VERTICAL_BOTTOM :
        gradientDirection = right
                            ? Direction.HORIZONTAL_RIGHT
                            : Direction.HORIZONTAL_LEFT;

        break;

      case CENTER :
        return;
    }

    clearCache();
  }

  @Override
  public String toString() {
    if ((_toString == null) || (toStringModCount != modCount)) {
      toStringModCount = modCount;
      _toString        = toStringEx();
    }

    return _toString;
  }

  public String toStringEx() {
    if (gradientColors == null) {
      return (this.backgroundColor == null)
             ? ""
             : Conversions.colorToHEXString(backgroundColor);
    }

    StringBuilder sb  = new StringBuilder();
    int           len = gradientColors.length;

    for (int i = 0; i < len; i++) {
      UIColor c = gradientColors[i];

      if (c != null) {
        if ((c instanceof UIColorShade) && ((UIColorShade) c).getColorKey() != null) {
          sb.append(((UIColorShade) c).getColorKey());
        } else {
          sb.append(Conversions.colorToHEXString(c));
        }
      }

      sb.append(", ");
    }

    sb.setLength(sb.length() - 2);

    boolean bracket = false;

    if (gradientType != Type.LINEAR) {
      sb.append(" [type=\"").append(gradientType.toString().toLowerCase(Locale.US)).append("\"");
      bracket = true;
    }

    if (gradientDirection != Direction.VERTICAL_TOP) {
      if (!bracket) {
        sb.append(" [");
        bracket = true;
      } else {
        sb.append(", ");
      }

      sb.append("direction=\"").append(gradientDirection.toString().toLowerCase(Locale.US)).append("\"");
    }

    if (this.gradientMagnitude != 100) {
      if (!bracket) {
        sb.append(" [");
        bracket = true;
      } else {
        sb.append(", ");
      }

      sb.append("magnitude=").append(gradientMagnitude);
    }

    if (!ColorUtils.isStandardDistribution(gradientDistribution)) {
      if (!bracket) {
        sb.append(" [");
        bracket = true;
      } else {
        sb.append(", ");
      }

      sb.append("distribution=\"");
      len = gradientDistribution.length;

      for (int i = 0; i < len; i++) {
        sb.append(SNumber.toString(gradientDistribution[i])).append(", ");
      }

      sb.setCharAt(sb.length() - 2, '\"');
    }

    if (bracket) {
      sb.append(']');
    }

    return sb.toString();
  }

  @Override
  public void setAlwaysFill(boolean alwaysFill) {
    this.alwaysFill = alwaysFill;
  }

  /**
   * Sets the colors for the gradient
   *
   * @param gradientColors
   *          the colors
   */
  public void setGradientColors(UIColor[] gradientColors) {
    if (gradientColors != null) {
      if (gradientColors.length == 1) {
        UIColor[] c = new UIColor[] { null, gradientColors[0] };

        gradientColors = c;
      } else if (gradientColors.length == 0) {
        gradientColors = null;
      }
    }

    int len = (gradientColors == null)
              ? 0
              : gradientColors.length;

    for (int i = 0; i < len; i++) {
      if (gradientColors[i] == null) {
        hasNull = true;

        break;
      }
    }

    this.gradientColors = gradientColors;

    if (gradientColors != null) {
      if ((this.gradientDistribution == null) || (this.gradientDistribution.length != this.gradientColors.length)) {
        gradientDistribution = ColorUtils.getStandardDistrubution(gradientColors.length);
      }
    }

    clearCache();
  }

  @Override
  public void setGradientDirection(Direction direction) {
    if (gradientDirection != direction) {
      gradientDirection = direction;
      clearCache();
    }
  }

  /**
   * Sets the gradient distribution
   *
   * @param gradientDistribution
   *          the distribution
   */
  public void setGradientDistribution(float[] gradientDistribution) {
    this.gradientDistribution = gradientDistribution;
    clearCache();
  }

  @Override
  public void setGradientMagnitude(int magnitude) {
    if (magnitude == 0) {
      magnitude = 100;
    }

    if (gradientMagnitude != magnitude) {
      this.gradientMagnitude = magnitude;
      clearCache();
    }
  }

  /**
   * Sets the gradient paint options. If the colors are null then the background
   * color of the component will be utilized to construct the start and end
   * colors for the gradient
   *
   * @param start
   *          the start color
   * @param end
   *          the end color
   * @param direction
   *          the direction of gradient
   */
  @Override
  public void setGradientPaint(UIColor start, UIColor end, Direction direction) {
    if ((gradientColors == null) || (gradientColors.length != 2)) {
      gradientColors = new UIColor[2];
    }

    gradientColors[0] = start;
    gradientColors[1] = end;
    gradientType      = Type.LINEAR;
    gradientDirection = direction;

    if ((start == null) && (end == null)) {
      bgColorType = BGCOLOR_TYPE.DARK;
    } else if ((start == null) || (end == null)) {
      hasNull = true;
    }

    if ((this.gradientDistribution == null) || (this.gradientDistribution.length != this.gradientColors.length)) {
      gradientDistribution = ColorUtils.getStandardDistrubution(gradientColors.length);
    }

    clearCache();
  }

  /**
   * Sets the gradient paint options.
   *
   * @param type
   *          the type of gradient color
   * @param direction
   *          the direction of gradient
   * @param colors
   *          the gradient colors
   * @param distribution
   *          the gradient color distribution
   * @param magnitude
   *          the magnitude
   */
  @Override
  public void setGradientPaint(Type type, Direction direction, UIColor[] colors, float[] distribution, int magnitude) {
    if (colors == null) {
      gradientType = null;

      return;
    }

    gradientType      = type;
    gradientDirection = direction;

    if (distribution == null) {
      int   len = colors.length;
      float p   = (float) 1.0 / len;

      distribution = new float[len];

      for (int i = 0; i < len; i++) {
        distribution[i] = p;
      }
    }

    gradientDistribution = distribution;
    gradientMagnitude    = magnitude;
    setGradientColors(colors);
  }

  @Override
  public void setGradientPaintEnabled(boolean enabled) {
    gradientDirection = enabled
                        ? Direction.VERTICAL_TOP
                        : null;
  }

  @Override
  public void setGradientRadius(float radius) {
    gradientRadius = radius;
    clearCache();
  }

  /**
   * Sets the type of gradient
   *
   * @param type
   *          the type of gradient
   */
  @Override
  public void setGradientType(Type type) {
    gradientType = type;
    clearCache();
  }

  @Override
  public int getGradientColorCount() {
    return (gradientColors == null)
           ? 0
           : gradientColors.length;
  }

  @Override
  public UIColor[] getGradientColors() {
    return gradientColors;
  }

  @Override
  public UIColor[] getGradientColors(UIColor bg) {
    if (bg == null) {
      bg = backgroundColor;
    }

    if (bg == null) {
      bg = ColorUtils.getBackground();
    }

    if (bgColorType != null) {
      switch(bgColorType) {
        case LITE :
          gradientColors[0] = bg.light(15);
          gradientColors[1] = bg;

          break;

        case MIDDLE :
          gradientColors[0] = bg.light(30);
          gradientColors[1] = bg.light(-30);

          break;

        case DARK_DARK :
          gradientColors[0] = bg.light(60);
          gradientColors[1] = bg.light(-60);

          break;

        default :
          gradientColors[0] = bg;
          gradientColors[1] = bg.light(-45);

          break;
      }
    } else if (hasNull) {
      UIColor[] a = new UIColor[gradientColors.length];

      getGradientColors(a, bg);
      hasNull        = false;
      gradientColors = a;
    }

    return gradientColors;
  }

  @Override
  public void getGradientColors(UIColor[] a, UIColor bg) {
    if ((gradientColors == null) || (a == null)) {
      return;
    }

    UIColor[] b   = this.getGradientColors();
    int       len = b.length;

    if (bgColorType != null) {
      switch(bgColorType) {
        case LITE :
          a[0] = bg.light(15);
          a[1] = bg;

          break;

        case MIDDLE :
          a[0] = bg.light(30);
          a[1] = bg.light(-30);

          break;

        default :
          a[0] = bg;
          a[1] = bg.light(-45);

          break;
      }
    } else if (hasNull) {
      UIColor nc = backgroundColor;

      if (nc == null) {
        nc = bg;
      }

      UIColor c = null;
      int     n;

      for (n = 0; n < len; n++) {
        c    = b[n];
        a[n] = c;

        if (c != null) {
          break;
        }
      }

      if (c == null) {
        c = nc;
      }

      int i;

      for (i = n - 1; i >= 0; i--) {
        a[i] = c.brighterBrighter();
        c    = a[i];
      }

      if (n < len) {
        for (i = n + 1; i < len; i++) {
          a[i] = b[i];

          if (a[i] == null) {
            a[i] = a[i - 1].darkerDarker();
          }
        }
      }
    } else {
      for (int i = 0; i < len; i++) {
        a[i] = b[i];
      }
    }
  }

  @Override
  public Direction getGradientDirection() {
    return gradientDirection;
  }

  @Override
  public float[] getGradientDistribution() {
    return gradientDistribution;
  }

  @Override
  public UIColor getGradientEndColor(UIColor bg) {
    UIColor a[] = getGradientColors(bg);

    return (a == null)
           ? null
           : a[1];
  }

  @Override
  public int getGradientMagnitude() {
    return gradientMagnitude;
  }

  @Override
  public float getGradientRadius() {
    return gradientRadius;
  }

  @Override
  public UIColor getGradientStartColor(UIColor bg) {
    UIColor a[] = getGradientColors(bg);

    return (a == null)
           ? null
           : a[0];
  }

  @Override
  public Type getGradientType() {
    return gradientType;
  }

  @Override
  public boolean isAlwaysFill() {
    return alwaysFill;
  }

  @Override
  public boolean isGradientPaintEnabled() {
    return (gradientType != null) && (gradientColors != null);
  }

  @Override
  public boolean isSingleColorPainter() {
    return false;
  }

  protected UIPoint calculateRadialCenter(float width, float height) {
    float x = 0;
    float y = 0;

    switch(gradientDirection) {
      case VERTICAL_TOP :
        x = width / 2;

        break;

      case VERTICAL_BOTTOM :
        x = width / 2;
        y = height;

        break;

      case HORIZONTAL_LEFT :
        y = height / 2;

        break;

      case HORIZONTAL_RIGHT :
        y = height / 2;
        x = width;

        break;

      case DIAGONAL_TOP_LEFT :
        break;

      case DIAGONAL_BOTTOM_LEFT :
        y = height;

        break;

      case DIAGONAL_TOP_RIGHT :
        x = width;

        break;

      case DIAGONAL_BOTTOM_RIGHT :
        x = width;
        y = height;

        break;

      default :
        x = width / 2;
        y = height / 2;

        break;    // default centered
    }

    if (startPoint == null) {
      startPoint = new UIPoint(x, y);
    } else {
      startPoint.x = x;
      startPoint.y = y;
    }

    return startPoint;
  }

  protected UIRectangle calculateLinearRect(float width, float height) {
    float magnitude = (float) Math.ceil((gradientMagnitude) / 100f);

    if (magnitude < 0.1) {
      magnitude = 0.01f;
    }

    float x  = 0;
    float y  = 0;
    float x2 = 0;
    float y2 = 0;
    float hm = height * magnitude;
    float wm = width * magnitude;

    switch(gradientDirection) {
      case VERTICAL_TOP :
        x  = width / 2;
        x2 = x;
        y2 = hm;

        break;

      case VERTICAL_BOTTOM :
        x  = width / 2;
        x2 = x;
        y  = height;
        y2 = height - hm;

        break;

      case HORIZONTAL_LEFT :
        x2 = wm;
        y  = height / 2;
        y2 = y;

        break;

      case HORIZONTAL_RIGHT :
        x  = width;
        y  = height / 2;
        y2 = y;
        x2 = width - wm;

        break;

      case DIAGONAL_TOP_LEFT :
        x2 = wm;
        y2 = hm;

        break;

      case DIAGONAL_BOTTOM_LEFT :
        y  = height;
        x2 = wm;
        y2 = height - hm;

        break;

      case DIAGONAL_TOP_RIGHT :
        x  = width;
        x2 = width - wm;
        y2 = hm;

        break;

      case DIAGONAL_BOTTOM_RIGHT :
        x  = width;
        y  = height;
        x2 = width - wm;
        y2 = height - hm;

        break;

      default :
        x  = width / 2;
        y  = height / 2;
        y2 = hm;
        x2 = wm;

        break;    // default centered
    }

    if (calcRect == null) {
      calcRect = new UIRectangle(x, y, x2 - x, y2 - y);
    } else {
      calcRect.set(x, y, x2 - x, y2 - y);
    }

    return calcRect;
  }

  protected float calculateRadius(float w, float h) {
    float radius = gradientRadius;

    if (radius <= 0) {
      float magnitude = (float) Math.ceil((gradientMagnitude) / 100f);

      if (magnitude < 0.1) {
        magnitude = 0.01f;
      }

      if (radius < 0) {
        w = Math.min(w, h);
        h = w;
      }

      switch(gradientDirection) {
        case HORIZONTAL_LEFT :
        case HORIZONTAL_RIGHT :
          radius = h / 2 * magnitude;

          break;

        case DIAGONAL_TOP_RIGHT :
        case DIAGONAL_TOP_LEFT :
        case DIAGONAL_BOTTOM_LEFT :
        case DIAGONAL_BOTTOM_RIGHT :
          radius = (float) Math.sqrt(Math.pow(w / 2, 2) + Math.pow(h / 2, 2)) * magnitude;

          break;

        case CENTER :
          float hm = h * magnitude / 2;
          float wm = w * magnitude / 2;

          radius = (hm > wm)
                   ? wm
                   : hm;

          break;

        default :
          radius = w / 2 * magnitude;

          break;
      }
    }

    return radius;
  }

  protected void clearCache() {
    modCount++;
  }
}
