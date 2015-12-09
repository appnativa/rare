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
import com.appnativa.rare.converters.Conversions;
import com.appnativa.rare.ui.ColorUtils.Shade;
import com.appnativa.rare.ui.iPaintedButton.ButtonState;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.UISimpleBackgroundPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;

/**
 *
 * @author Don DeCoteau
 */
public class UIColorShade extends UIColor implements Cloneable {
  private int                alpha = 255;
  private iBackgroundPainter backgroundPainter;
  public static long         colorChangeTime;

  /** cached value of the converted color */
  private int cachedShadeColor;

  /** cached value of the source color */
  private int                  cachedsourceColor;
  private String               colorKey;
  private SimpleColorStateList colorStateList;
  private int                  lumAdjustment;
  private PainterHolder        painterHolder;
  private Shade                shade;
  private UIColor              sourceColor;
  public long                  myColorUpdateTime;

  public UIColorShade(iBackgroundPainter painter) {
    super((painter.getBackgroundColor() == null)
          ? 0
          : painter.getBackgroundColor().getColor());
    this.backgroundPainter = painter;
    this.shade             = Shade.BGPAINT;
  }

  public UIColorShade(int color) {
    super(color);
  }

  public UIColorShade(SimpleColorStateList list) {
    super(list.getDefaultColor());
    this.colorStateList = list;
    this.shade          = Shade.COLOR_LIST;
  }

  public UIColorShade(int c, Shade shade) {
    this(c);
    this.shade = shade;
  }

  public UIColorShade(UIColor color, iBackgroundPainter bp) {
    this(color, Shade.BGPAINT);
    backgroundPainter = bp;
  }

  public UIColorShade(UIColor c, int lumAdjustment) {
    this(c);
    shade              = Shade.LUM_ADJUSTMENT;
    this.lumAdjustment = lumAdjustment;
  }

  public UIColorShade(UIColor bg, PainterHolder ph) {
    this(bg, Shade.BGPAINT);
    this.painterHolder = ph;

    if (ph.normalPainter != null) {
      backgroundPainter = ph.normalPainter.getBackgroundPainter();
    }
  }

  public UIColorShade(UIColor c, Shade shade) {
    this(c);
    this.shade = shade;
  }

  public UIColorShade(UIColor c, String key) {
    this(c);
    this.shade    = Shade.UIMANAGER;
    this.colorKey = key;
  }

  protected UIColorShade(UIColor color) {
    super(color.getColor());
    sourceColor = color;
  }

  public void copyShade(UIColorShade cs) {
    this.sourceColor       = cs.sourceColor;
    this.shade             = cs.shade;
    this.backgroundPainter = cs.backgroundPainter;
    this.lumAdjustment     = cs.lumAdjustment;
    this.alpha             = cs.alpha;
    this.colorStateList    = cs.colorStateList;
  }

  @Override
  public Object clone() {
    return super.clone();
  }

  @Override
  public Object getAPColor() {
    if (myColorUpdateTime < colorChangeTime) {
      proxy = null;
    }

    return super.getAPColor();
  }

  @Override
  public String toString() {
    if (colorKey != null) {
      return colorKey;
    }

    if (backgroundPainter != null) {
      return backgroundPainter.toString();
    }

    String s = Conversions.colorToHEXString(this);

    return ColorUtils.shadeKeyToString(shade, s, lumAdjustment, alpha);
  }

  public void setAlpha(int alpha) {
    this.alpha = alpha;
  }

  public void setPaintBucket(PaintBucket pb) {
    if (backgroundPainter != null) {
      pb.setBackgroundPainter(backgroundPainter);
    } else {
      pb.setBackgroundColor(this);
    }
  }

  public void setToColor(UIColor c) {
    this.sourceColor       = c;
    this.shade             = Shade.UIMANAGER;
    this.backgroundPainter = null;
    this.lumAdjustment     = 0;
    this.alpha             = 255;
  }

  public iBackgroundPainter getBackgroundPainter() {
    return backgroundPainter;
  }

  @Override
  public int getColor() {
    if (!isDynamic()) {
      return super.getColor();
    }

    int c;

    if (shade == Shade.UIMANAGER) {
      UIColor cc = (colorKey == null)
                   ? null
                   : Platform.getUIDefaults().getColor(colorKey);

      cc = (cc == null)
           ? sourceColor
           : cc;

      if (cc instanceof UIColorShade) {
        if (cc == this) {
          c = sourceColor.getColor();
        } else {
          c = cc.getColor();
        }
      } else {
        sourceColor = cc;    // reset the source to the new color
        c           = cc.getColor();
      }

      return c;
    }

    if (sourceColor == null) {
      return color;
    }

    c = sourceColor.getColor();

    if (c == cachedsourceColor) {
      return cachedShadeColor;
    }

    cachedsourceColor = c;
    proxy             = null;

    switch(shade) {
      case DARKER :
        c = ColorUtils.darker(c);

        break;

      case DARKER_DARKER :
        c = ColorUtils.darker(ColorUtils.darker(c));

        break;

      case BRIGHTER :
        c = ColorUtils.brighter(c);

        break;

      case BRIGHTER_BRIGHTER :
        c = ColorUtils.brighter(ColorUtils.brighter(c));

        break;

      case LUM_ADJUSTMENT :
        c = ColorUtils.adjustLuminance(c, lumAdjustment);

        break;

      case DYN_LUM_ADJUSTMENT :
        if (sourceColor.isDarkColor()) {
          c = ColorUtils.adjustLuminance(c, lumAdjustment);
        } else {
          c = ColorUtils.adjustLuminance(c, -lumAdjustment);
        }

        break;

      case ALPHA :
        c = ColorUtils.setAlpha(c, alpha);

        break;

      default :
        break;
    }

    cachedShadeColor = c;

    return c;
  }

  public String getColorKey() {
    return colorKey;
  }

  public SimpleColorStateList getColorStateList() {
    return colorStateList;
  }
  
  public UIColor getColor(ButtonState state) {
    if(colorStateList!=null) {
      return colorStateList.getColor(state);
    }
    return super.getColor(state);
  }
  
  @Override
  public UIColor getDisabledColor() {
    if (colorStateList != null) {
      return colorStateList.getDisabledColor();
    }

    return super.getDisabledColor();
  }

  public PainterHolder getPainterHolder() {
    return painterHolder;
  }

  public Shade getShade() {
    return shade;
  }

  @Override
  public boolean isDynamic() {
    return (colorKey != null) || ((sourceColor != null) && sourceColor.isDynamic());
  }

  @Override
  public boolean isSimpleColor() {
    if (backgroundPainter != null) {
      return false;
    }

    return true;
  }

  @Override
  public boolean equals(Object obj) {
    if ((obj == null) ||!(obj instanceof UIColorShade)) {
      return false;
    }

    if (obj == this) {
      return true;
    }

    final UIColorShade cs = (UIColorShade) obj;

    if (cs.shade != shade) {
      return false;
    }

    switch(shade) {
      case UIMANAGER :
        return colorKey.equals(cs.colorKey);

      case BGPAINT :
        return cs.backgroundPainter == backgroundPainter;

      case ALPHA :
        return cs.alpha == alpha;

      case COLOR_LIST :
        return cs.colorStateList == colorStateList;

      case LUM_ADJUSTMENT :
        return cs.lumAdjustment == lumAdjustment;

      case BRIGHTER :
      case BRIGHTER_BRIGHTER :
      case DARKER :
      case DARKER_DARKER :
        return cs.sourceColor.equals(sourceColor);

      default :
        return false;
    }
  }

  public void setColorKey(String name) {
    colorKey = name;
  }

  @Override
  protected iBackgroundPainter getPainter() {
    if (backgroundPainter != null) {
      return backgroundPainter;
    }

    return new UISimpleBackgroundPainter(this);
  }

  public void setShade(Shade shade) {
    this.shade = shade;
  }
}
