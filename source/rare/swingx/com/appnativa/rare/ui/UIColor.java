/*
 * @(#)UIColor.java   2011-11-11
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Color;
import java.awt.Paint;
import java.awt.color.ColorSpace;

import javax.swing.plaf.UIResource;

import com.appnativa.rare.converters.Conversions;

public class UIColor extends Color implements iPlatformPaint,Cloneable {
  protected static final double FACTOR = 0.7;

  /**
   * The color yellow.  In the default sRGB space.
   */
  public final static UIColor YELLOW = new UIColor(255, 255, 0);

  /**
   * The color white.  In the default sRGB space.
   */
  public final static UIColor WHITE       = new UIColor(255, 255, 255);
  public static final UIColor TRANSPARENT = new UIColor(255, 255, 255, 0);

  /**
   * The color red.  In the default sRGB space.
   */
  public final static UIColor RED = new UIColor(255, 0, 0);

  /**
   * The color pink.  In the default sRGB space.
   */
  public final static UIColor PINK = new UIColor(255, 175, 175);

  /**
   * The color orange.  In the default sRGB space.
   */
  public final static UIColor ORANGE = new UIColor(255, 200, 0);

  /**
   * The color magenta.  In the default sRGB space.
   */
  public final static UIColor MAGENTA = new UIColor(255, 0, 255);

  /**
   * The color light gray.  In the default sRGB space.
   */
  public final static UIColor LIGHTGRAY = new UIColor(192, 192, 192);

  /**
   * The color green.  In the default sRGB space.
   */
  public final static UIColor GREEN = new UIColor(0, 255, 0);

  /**
   * The color gray.  In the default sRGB space.
   */
  public final static UIColor GRAY = new UIColor(128, 128, 128);

  /**
   * The color dark gray.  In the default sRGB space.
   */
  public final static UIColor DARKGRAY = new UIColor(64, 64, 64);

  /**
   * The color cyan.  In the default sRGB space.
   */
  public final static UIColor CYAN = new UIColor(0, 255, 255);

  /**
   * The color blue.  In the default sRGB space.
   */
  public final static UIColor BLUE = new UIColor(0, 0, 255);

  /**
   * The color black.  In the default sRGB space.
   */
  public final static UIColor BLACK = new UIColor(0, 0, 0);

  public UIColor(Color color) {
    super(color.getRGB(), true);
  }

  public UIColor(int rgba) {
    super(rgba,true);
  }

  public UIColor(ColorSpace cspace, float[] components, float alpha) {
    super(cspace, components, alpha);
  }

  public UIColor(float r, float g, float b) {
    super(r, g, b);
  }

  public UIColor(int r, int g, int b) {
    super(r, g, b);
  }

  public UIColor(float r, float g, float b, float a) {
    super(r, g, b, a);
  }

  public UIColor(int r, int g, int b, int a) {
    super(r, g, b, a);
  }

  public UIColor alpha(int alpha) {
    if(alpha==getAlpha()) {
      return this;
    }
    return new UIColor(getRed(), getGreen(), getBlue(), alpha);
  }

  @Override
  public UIColor brighter() {
    return new UIColor(ColorUtils.brighter(getRGB()));
  }

  public UIColor brighterBrighter() {
    return new UIColor(ColorUtils.brighter(ColorUtils.brighter(getRGB())));
  }

  @Override
  public UIColor darker() {
    return new UIColor(ColorUtils.darker(getRGB()));
  }

  public UIColor darkerDarker() {
    return new UIColor(ColorUtils.darker(ColorUtils.darker(getRGB())));
  }

  public static final UIColor fromColor(Color color) {
    if (color instanceof UIColor) {
      return (UIColor) color;
    }

    return (color == null)
           ? null
           : new UIColor(color);
  }

  public UIColor light(int lum) {
    return new UIColor(ColorUtils.adjustLuminance(getRGB(), lum));
  }

  public int lightEx(int lum) {
    return ColorUtils.adjustLuminance(getRGB(), lum);
  }

  @Override
  public String toString() {
    return Conversions.colorToHEXString(this);
  }

  public String toHexString() {
    return Conversions.colorToHEXString(this);
  }

  @Override
  public boolean equals(Object o) {
    if(o==this) {
      return true;
    }
    if(o instanceof UIColorShade) {
      return false;
    }
    if(o instanceof UIColor) {
      return getARGB()==((UIColor)o).getARGB();
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    return getARGB();
  }
  
  public int getARGB() {
    return getRGB();
  }

  @Override
  public Paint getPaint() {
    return this;
  }

  public boolean isDarkColor() {
    return (getRed() + getGreen() + getBlue()) / 3 < 128;
  }

  public boolean isDynamic() {
    return false;
  }

  public boolean isSimpleColor() {
    return true;
  }
  
  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      throw new InternalError();
    }
  }
  public UIColor getDisabledColor(UIColor def) {
    return def;
  }


  public static class UIColorResource extends UIColor implements UIResource {
    public UIColorResource(Color c) {
      super(c);
    }
  }
}
