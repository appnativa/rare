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

import android.content.res.ColorStateList;
import android.content.res.Configuration;

import android.graphics.drawable.Drawable;

import android.widget.TextView;

import com.appnativa.rare.Platform;
import com.appnativa.rare.converters.Conversions;
import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.platform.android.ui.view.BackgroundDrawable;
import com.appnativa.rare.ui.ColorUtils.Shade;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.UISimpleBackgroundPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;

/**
 *
 * @author Don DeCoteau
 */
public class UIColorShade extends UIColor implements Cloneable {
  static final int[]         STATE_DISABLED = { -android.R.attr.state_enabled };
  protected Configuration    lastConfiguration;
  private int                alpha = 255;
  private iBackgroundPainter backgroundPainter;
  private String             colorKey;
  private ColorStateList     colorStateList;
  private Drawable           drawable;
  private int                lumAdjustment;
  private PainterHolder      painterHolder;
  private int                resID;
  private Shade              shade;
  private UIColor            sourceColor;
  private int                cachedColor;
  private boolean            colorCached;
  private int                cachedRGB;

  public UIColorShade(ColorStateList list) {
    super(list.getDefaultColor());
    this.colorStateList = list;
    this.shade          = Shade.COLOR_LIST;
  }

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
    super(list.getDefaultColor().getColor());
  }

  public UIColorShade(Drawable drawable, int resid) {
    super(UIColor.TRANSPARENT.getColor());
    this.drawable = drawable;
    this.shade    = Shade.DRAWABLE;
    this.resID    = resid;

    if ((resID > 0) && (drawable != null) && (drawable.getChangingConfigurations() != 0)) {
      lastConfiguration = AppContext.getContext().getConfiguration();
    }
  }

  public UIColorShade(UIColor color, iBackgroundPainter bp) {
    this(color, Shade.BGPAINT);
    backgroundPainter = bp;
  }

  public UIColorShade(UIColor c, int lumAdjustment) {
    super(c.getColor());
    sourceColor        = c;
    shade              = Shade.LUM_ADJUSTMENT;
    this.lumAdjustment = lumAdjustment;
  }

  public UIColorShade(UIColor bg, PainterHolder ph) {
    this(bg, Shade.BGPAINT);

    if (ph.normalPainter != null) {
      backgroundPainter = ph.normalPainter.getBackgroundPainter();
    }

    painterHolder = ph;
  }

  public UIColorShade(UIColor c, Shade shade) {
    super(c.getColor());
    this.sourceColor = c;
    this.shade       = shade;
  }

  public UIColorShade(UIColor c, String key) {
    super(c.getColor());
    sourceColor   = c;
    this.shade    = Shade.UIMANAGER;
    this.colorKey = key;
  }

  public void copyShade(UIColorShade cs) {
    this.sourceColor       = cs.sourceColor;
    this.shade             = cs.shade;
    this.backgroundPainter = cs.backgroundPainter;
    this.lumAdjustment     = cs.lumAdjustment;
    this.alpha             = cs.alpha;
    this.resID             = cs.resID;
    this.colorStateList    = cs.colorStateList;
    colorCached            = false;
  }

  public Object clone() {
    return super.clone();
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
    } else if (drawable != null) {
      pb.setBackgroundPainter(new UISimpleBackgroundPainter(drawable, resID));
    } else {
      pb.setBackgroundColor(this);
    }
  }

  public void setTextColor(TextView tv) {
    if (colorStateList != null) {
      tv.setTextColor(colorStateList);
    } else {
      tv.setTextColor(color);
    }
  }

  public void setToColor(UIColor c) {
    this.sourceColor       = c;
    this.shade             = Shade.UIMANAGER;
    this.backgroundPainter = null;
    this.lumAdjustment     = 0;
    this.alpha             = 255;
    this.drawable          = null;
    colorCached            = false;
  }

  public iBackgroundPainter getBackgroundPainter() {
    return backgroundPainter;
  }

  public int getColor() {
    if (!isDynamic()) {
      return super.getColor();
    }

    if (shade == Shade.UIMANAGER) {
      int     c;
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

    int c = sourceColor.getColor();

    if (colorCached && (c == cachedColor)) {
      return cachedRGB;
    }

    colorCached = true;
    cachedColor = c;
    c           = sourceColor.getColor();

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

    cachedRGB = c;

    return c;
  }

  public int getColor(int stateSet[]) {
    if (colorStateList != null) {
      return colorStateList.getColorForState(stateSet, colorStateList.getDefaultColor());
    }

    return getColor();
  }

  public String getColorKey() {
    return colorKey;
  }

  public ColorStateList getColorStateList() {
    return colorStateList;
  }

  public UIColor getDisabledColor(UIColor def) {
    if ((colorStateList != null) && (colorStateList.getColorForState(STATE_DISABLED, 0) != 0)) {
      return this;
    }

    return def;
  }

  public Drawable getDrawable() {
    if (drawable != null) {
      if ((lastConfiguration != null) && (AppContext.getContext().getConfiguration() != lastConfiguration)) {
        drawable = UIImage.reloadDrawable(resID, drawable);
      }

      return drawable;
    }

    if (backgroundPainter != null) {
      return backgroundPainter.getDrawable(null);
    }

    return new BackgroundDrawable(this);
  }

  public PainterHolder getPainterHolder() {
    return painterHolder;
  }

  public int getResourceID() {
    return resID;
  }

  public Shade getShade() {
    return shade;
  }

  public boolean isDynamic() {
    return ((shade != Shade.UIMANAGER) || ColorUtils.KEEP_COLOR_KEYS)
           || ((sourceColor != null) && sourceColor.isDynamic());
  }

  public boolean isSimpleColor() {
    if (drawable != null) {
      return false;
    }

    if (backgroundPainter != null) {
      return false;
    }

    return true;
  }

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

      case DRAWABLE :
        return cs.drawable == drawable;

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

  public void setShade(Shade shade) {
    this.shade  = shade;
    colorCached = false;
  }
}
