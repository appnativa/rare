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

import com.appnativa.rare.converters.Conversions;
import com.appnativa.rare.ui.iPaintedButton.ButtonState;
import com.appnativa.rare.ui.painter.UISimpleBackgroundPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;

/*-[
 #import "AppleHelper.h"
 ]-*/

/**
 *
 * @author Don DeCoteau
 */
public class UIColor implements iPlatformPaint, Cloneable {
  protected static final double FACTOR = 0.7;

  /**
   * The color yellow. In the default sRGB space.
   */
  public final static UIColor YELLOW = new UIColor(255, 255, 0);

  /**
   * The color white. In the default sRGB space.
   */
  public final static UIColor WHITE = new UIColor(255, 255, 255);

  /**
   * The color blue. In the default sRGB space.
   */
  public final static UIColor TRANSPARENT = new UIColor(0, 0, 0, 0);

  /**
   * The color red. In the default sRGB space.
   */
  public final static UIColor RED = new UIColor(255, 0, 0);

  /**
   * The color pink. In the default sRGB space.
   */
  public final static UIColor PINK = new UIColor(255, 175, 175);

  /**
   * The color orange. In the default sRGB space.
   */
  public final static UIColor ORANGE = new UIColor(255, 200, 0);

  /**
   * The color magenta. In the default sRGB space.
   */
  public final static UIColor MAGENTA = new UIColor(255, 0, 255);

  /**
   * The color light gray. In the default sRGB space.
   */
  public final static UIColor LIGHTGRAY = new UIColor(192, 192, 192);

  /**
   * The color green. In the default sRGB space.
   */
  public final static UIColor GREEN = new UIColor(0, 255, 0);

  /**
   * The color gray. In the default sRGB space.
   */
  public final static UIColor GRAY = new UIColor(128, 128, 128);

  /**
   * The color dark gray. In the default sRGB space.
   */
  public final static UIColor DARKGRAY = new UIColor(64, 64, 64);

  /**
   * The color cyan. In the default sRGB space.
   */
  public final static UIColor CYAN = new UIColor(0, 255, 255);

  /**
   * The color blue. In the default sRGB space.
   */
  public final static UIColor BLUE = new UIColor(0, 0, 255);

  /**
   * The color black. In the default sRGB space.
   */
  public final static UIColor BLACK = new UIColor(0, 0, 0);
  protected int               color;
  protected Object            proxy;
  private UIColor             disabledColor;

  public UIColor(int rgba) {
    this.color = rgba;
  }

  public UIColor(Object proxy) {
    this.proxy = proxy;
    this.color = getIntColor(proxy);
  }

  public UIColor(UIColor color) {
    this.color = color.color;
    this.proxy = color.proxy;
  }

  public UIColor(int r, int g, int b) {
    color = (0xFF << 24) | (r << 16) | (g << 8) | b;
  }

  public UIColor(int r, int g, int b, int a) {
    color = (a << 24) | (r << 16) | (g << 8) | b;
  }

  public native void addToAttributedString(Object astring, int offset, int length)
  /*-[
    NSMutableAttributedString* s=(NSMutableAttributedString*)astring;
    [s addAttribute: NSForegroundColorAttributeName
    value: [self getAPColor]
    range: NSMakeRange(offset,length)];
  ]-*/
  ;

  public UIColor alpha(int alpha) {
    if (alpha == getAlpha()) {
      return this;
    }

    return new UIColor(getRed(), getGreen(), getBlue(), alpha);
  }

  public UIColor brighter() {
    return new UIColor(ColorUtils.brighter(getColor()));
  }

  public UIColor brighterBrighter() {
    return new UIColor(ColorUtils.brighter(ColorUtils.brighter(getColor())));
  }

  public UIColor darker() {
    return new UIColor(ColorUtils.darker(getColor()));
  }

  public UIColor darkerDarker() {
    return new UIColor(ColorUtils.darker(ColorUtils.darker(getColor())));
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (o instanceof UIColorShade) {
      return false;
    }

    if (o instanceof UIColor) {
      return color == ((UIColor) o).color;
    }

    return false;
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch(CloneNotSupportedException e) {
      throw new InternalError();
    }
  }

  @Override
  public int hashCode() {
    return color;
  }

  public UIColor light(int lum) {
    return new UIColor(ColorUtils.adjustLuminance(getColor(), lum));
  }

  public int lightEx(int lum) {
    return ColorUtils.adjustLuminance(getColor(), lum);
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {
    UIColor c = g.getColor();

    g.setColor(this);
    g.fillRect(x, y, width, height);
    g.setColor(c);
  }

  public String toCSSString() {
    StringBuilder sb = new StringBuilder();

    sb.append("color").append(": ");

    if (getAlpha() == 0) {
      sb.append("transparent;");
    } else {
      Conversions.colorToHEXString(sb, this);
      sb.append(";");
    }

    return sb.toString();
  }

  @Override
  public String toString() {
    return Conversions.colorToHEXString(this);
  }

  public String toHexString() {
    return Conversions.colorToHEXString(this);
  }

  public native Object getAPColor()
  /*-[
   if (proxy_ == nil) {
    #if TARGET_OS_IPHONE
    proxy_ = [UIColor colorWithRed:[self getRed]/255.0
                             green:[self getGreen]/255.0
                              blue:[self getBlue]/255.0
                             alpha:[self getAlpha]/255.0];
    #else
    proxy_ = [NSColor colorWithCalibratedRed:[self getRed]/255.0
                                       green:[self getGreen]/255.0
                                        blue:[self getBlue]/255.0
                                       alpha:[self getAlpha]/255.0];
    #endif
    }
    return proxy_;
  ]-*/
  ;

  public int getARGB() {
    return getColor();
  }

  public UIColor getColor(ButtonState state) {
    switch(state) {
      case DISABLED :
      case DISABLED_SELECTED :
        return getDisabledColor();

      default :
        return this;
    }
  }

  public UIColor getDisabledColor() {
    if (disabledColor == null) {
      disabledColor = ColorUtils.getDisabledVersion(this);
    }

    return disabledColor;
  }

  public int getAlpha() {
    return getColor() >>> 24;
  }

  public int getBlue() {
    return getColor() & 0xFF;
  }

  public native Object getCGColor()
  /*-[
    [self getAPColor];
  #if TARGET_OS_IPHONE
    return (id)((UIColor*)proxy_).CGColor;
  #else
    return (id)((NSColor*)proxy_).CGColor;
  #endif
  ]-*/
  ;

  public int getColor() {
    return color;
  }

  public int getGreen() {
    return (getColor() >> 8) & 0xFF;
  }

  @Override
  public UIColor getPlatformPaintColor() {
    return this;
  }

  public int getRGB() {
    return getColor();
  }

  public int getRed() {
    return (getColor() >> 16) & 0xFF;
  }

  public boolean isDarkColor() {
    return (getRed() + getGreen() + getBlue()) / 3 < 128;
  }

  public boolean isDynamic() {
    return false;
  }

  @Override
  public boolean isPainter() {
    return false;
  }

  public boolean isSimpleColor() {
    return true;
  }

  native int getIntColor(Object proxy)
  /*-[
   #if TARGET_OS_IPHONE
           return [AppleHelper colorToInt:(UIColor*)proxy];
   #else
           return [AppleHelper colorToInt:(NSColor*)proxy];
   #endif
   ]-*/
  ;

  protected iBackgroundPainter getPainter() {
    return new UISimpleBackgroundPainter(this);
  }
}
