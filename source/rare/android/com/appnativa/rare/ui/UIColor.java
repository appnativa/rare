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

import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.appnativa.rare.converters.Conversions;
import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.view.BackgroundDrawable;
import com.appnativa.rare.ui.iPaintedButton.ButtonState;

/**
 *
 * @author Don DeCoteau
 */
public class UIColor implements iPlatformPaint, Cloneable {
  public final static UIColor BLACK       = new UIColor(android.graphics.Color.BLACK);
  public final static UIColor BLUE        = new UIColor(android.graphics.Color.BLUE);
  public final static UIColor GREEN       = new UIColor(android.graphics.Color.GREEN);
  public final static UIColor RED         = new UIColor(android.graphics.Color.RED);
  public final static UIColor PINK        = new UIColor(0xffffafaf);
  public final static UIColor ORANGE      = new UIColor(0xffffc800);
  public final static UIColor MAGENTA     = new UIColor(android.graphics.Color.MAGENTA);
  public final static UIColor LIGHTGRAY   = new UIColor(android.graphics.Color.LTGRAY);
  public final static UIColor GRAY        = new UIColor(android.graphics.Color.GRAY);
  public final static UIColor DARKGRAY    = new UIColor(android.graphics.Color.DKGRAY);
  public final static UIColor CYAN        = new UIColor(android.graphics.Color.CYAN);
  public final static UIColor WHITE       = new UIColor(android.graphics.Color.WHITE);
  public final static UIColor YELLOW      = new UIColor(android.graphics.Color.YELLOW);
  public final static UIColor TRANSPARENT = new UIColor(android.graphics.Color.TRANSPARENT);
  protected final int         color;
  protected Paint             paint;
  private UIColor disabledColor;

  public UIColor(int color) {
    this.color = color;
  }

  public UIColor(int r, int g, int b) {
    color = ColorUtils.getARGB(r, g, b, 255);
  }

  public UIColor(int r, int g, int b, int a) {
    color = ColorUtils.getARGB(r, g, b, a);
  }

  public UIColor alpha(int alpha) {
    if (alpha == getAlpha()) {
      return this;
    }

    return new UIColor(getRed(), getGreen(), getBlue(), alpha);
  }

  public UIColor brighter() {
    return new UIColor(ColorUtils.brighter(color));
  }

  public UIColor brighterBrighter() {
    return new UIColor(ColorUtils.brighter(ColorUtils.brighter(color)));
  }

  public UIColor darker() {
    return new UIColor(ColorUtils.darker(color));
  }

  public UIColor darkerDarker() {
    return new UIColor(ColorUtils.darker(ColorUtils.darker(color)));
  }

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

  public int getARGB() {
    return color;
  }

  public int hashCode() {
    return color;
  }

  public UIColor light(int lum) {
    return new UIColor(ColorUtils.adjustLuminance(color, lum));
  }

  public int lightEx(int lum) {
    return ColorUtils.adjustLuminance(color, lum);
  }

  public String toString() {
    return toHexString();
  }

  public String toHexString() {
    return Conversions.colorToHEXString(this);
  }

  public void setTextColor(TextView tv) {
    tv.setTextColor(color);
  }

  public int getAlpha() {
    return ColorUtils.getAlpha(color);
  }

  public int getBlue() {
    return ColorUtils.getBlue(color);
  }

  public int getColor() {
    return color;
  }

  public Object clone() {
    try {
      return super.clone();
    } catch(CloneNotSupportedException e) {
      throw new InternalError();
    }
  }

  public int getColor(int stateSet[]) {
    return color;
  }

  public Drawable getDrawable() {
    if (ColorUtils.getAlpha(color) == 0) {
      return NullDrawable.getInstance();
    }

    return new BackgroundDrawable(this);
  }

  public int getGreen() {
    return ColorUtils.getGreen(color);
  }

  public Paint getPaintEx() {
    if (paint == null) {
      paint = new Paint();
    }

    paint.setColor(getColor());

    return paint;
  }

  public int getRGB() {
    return color;
  }

  public int getRed() {
    return ColorUtils.getRed(color);
  }

  public Shader getShader() {
    return null;
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
    if(disabledColor==null) {
     disabledColor=ColorUtils.getDisabledVersion(this);
    }
    return disabledColor;
  }

  public boolean isColor() {
    return true;
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

  protected boolean equalsEx(UIColor c) {
    return c.color == color;
  }
}
