/*
 * @(#)UIColorShade.java   2011-01-20
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

import com.appnativa.rare.Platform;
import com.appnativa.rare.converters.Conversions;
import com.appnativa.rare.ui.ColorUtils.Shade;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.UISimpleBackgroundPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;

/**
 * A class representing dynamic color shades
 *
 * @author Don DeCoteau
 */
public class UIColorShade extends UIColor implements Cloneable {

  /** a colorshade with a transparent bg color and a no-op gradient painter */
  public static UIColorShade NULL_SHADE = new UIColorShade(ColorUtils.TRANSPARENT_COLOR, UISimpleBackgroundPainter.NULL_BGPAINTER);
  String                     colorKey;
  SimpleColorStateList       colorStateList;
  private int                alpha      = 255;
  private iBackgroundPainter backgroundPainter;
  private UIColor            cachedColor;
  private int                cachedRGB;
  private int                lumAdjustment;
  private PainterHolder      painterHolder;
  private Shade              shade;
  private UIColor            sourceColor;

  public UIColorShade(iBackgroundPainter bp) {
    this(UIColor.fromColor(ColorUtils.getBackground()), Shade.BGPAINT);
    backgroundPainter = bp;
  }

  // public UIColorShade(UIColor c, String key,boolean named) {
  // this(c, named ? Shade. : Shade.UIMANAGER);
  // this.colorKey = key;
  // }
  public UIColorShade(SimpleColorStateList csl) {
    this(csl.getDefaultColor(), Shade.COLOR_LIST);
    colorStateList = csl;
  }

  @Override
  public UIColor getDisabledColor(UIColor def) {
    if (colorStateList != null) {
      return colorStateList.getDisabledColor(def);
    }

    return def;
  }

  @Override
  public Object clone() {
    return super.clone();
  }

  public UIColorShade(UIColor c) {
    this(c, Shade.DARKER_DARKER);
  }

  public boolean isColorStateList() {
    return colorStateList != null;
  }

  public UIColorShade(UIColor color, iBackgroundPainter bp) {
    this(color, Shade.BGPAINT);
    backgroundPainter = bp;
  }

  @Override
  public Paint getPaint() {
    if (backgroundPainter != null) {
      iPlatformPaint p = backgroundPainter.getPaint(50, 50);
      if (p != null) {
        return p.getPaint();
      }
    }
    return this;
  }

  public UIColorShade(UIColor c, int lumAdjustment) {
    super(c);
    sourceColor = c;
    shade = Shade.LUM_ADJUSTMENT;
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
    super(c);
    sourceColor = c;
    this.shade = shade;
  }

  public UIColorShade(UIColor c, String key) {
    this(c, Shade.UIMANAGER);
    this.colorKey = key;
  }

  @Override
  public UIColor brighter() {
    return getColor().brighter();
  }

  public void copyShade(UIColorShade cs) {
    this.sourceColor = cs.sourceColor;
    this.shade = cs.shade;
    this.backgroundPainter = cs.backgroundPainter;
    this.lumAdjustment = cs.lumAdjustment;
    this.alpha = cs.alpha;
    this.cachedRGB = cs.cachedRGB;
    this.cachedColor = cs.cachedColor;
  }

  public void setPaintBucket(PaintBucket pb) {
    if (backgroundPainter != null) {
      pb.setBackgroundPainter(backgroundPainter);
    } else {
      pb.setBackgroundColor(this);
    }
  }

  @Override
  public synchronized PaintContext createContext(ColorModel cm, Rectangle r, Rectangle2D r2d, AffineTransform xform,
      RenderingHints hints) {
    return getColor().createContext(cm, r, r2d, xform, hints);
  }

  @Override
  public UIColor darker() {
    return getColor().darker();
  }


  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof UIColorShade)) {
      return false;
    }
    if(obj==this) {
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

  @Override
  public int hashCode() {
    return getColor().hashCode();
  }

  public String toAttributeString() {
    if (colorKey != null) {
      return colorKey;
    }

    if (backgroundPainter != null) {
      return backgroundPainter.toString();
    }

    return Conversions.colorToHEXString(getColor());
  }

  public String toHexString() {
    return Conversions.colorToHEXString(this);
  }

  public String toRGBString() {
    return Conversions.colorToRGBString(this);
  }

  @Override
  public String toString() {
    if (colorKey != null) {
      return colorKey;
    }
    if (backgroundPainter != null) {
      return backgroundPainter.toString();
    }

    String s=Conversions.colorToHEXString(getColor());
    return ColorUtils.shadeKeyToString(shade, s, lumAdjustment, alpha);
  }

  public void setAlpha(int alpha) {
    this.alpha = alpha;
  }

  public void setColorKey(String key) {
    if (key == null) {
      key = null;
    }

    this.colorKey = key;
  }

  public void setLumAdjustment(int lumAdjustment) {
    if (this.lumAdjustment != lumAdjustment) {
      this.lumAdjustment = lumAdjustment;
      cachedColor = null;
    }
  }

  public void setShade(Shade shade) {
    if (shade != this.shade) {
      this.shade = shade;
      cachedColor = null;
    }
  }

  public void setSourceColor(UIColor color) {
    if (!color.equals(sourceColor)) {
      this.sourceColor = color;
      cachedColor = null;
    }
  }

  public void setToColor(UIColor c) {
    this.sourceColor = c;
    this.shade = Shade.UIMANAGER;
    this.backgroundPainter = null;
    this.lumAdjustment = 0;
    this.alpha = 255;
    this.cachedColor = null;
  }

  @Override
  public int getAlpha() {
    return getColor().getAlpha();
  }

  public iBackgroundPainter getBackgroundPainter() {
    return backgroundPainter;
  }

  @Override
  public int getBlue() {
    return getColor().getBlue();
  }

  @Override
  public float[] getColorComponents(float[] compArray) {
    return getColor().getColorComponents(compArray);
  }

  @Override
  public float[] getColorComponents(ColorSpace cspace, float[] compArray) {
    return getColor().getColorComponents(cspace, compArray);
  }

  public String getColorKey() {
    return colorKey;
  }

  @Override
  public ColorSpace getColorSpace() {
    return getColor().getColorSpace();
  }

  public SimpleColorStateList getColorStateList() {
    return colorStateList;
  }

  @Override
  public float[] getComponents(float[] compArray) {
    return getColor().getComponents(compArray);
  }

  @Override
  public float[] getComponents(ColorSpace cspace, float[] compArray) {
    return getColor().getComponents(cspace, compArray);
  }

  @Override
  public int getGreen() {
    return getColor().getGreen();
  }

  public int getLumAdjustment() {
    return lumAdjustment;
  }

  public PainterHolder getPainterHolder() {
    return painterHolder;
  }

  @Override
  public int getRGB() {
    return getColor().getRGB();
  }

  @Override
  public float[] getRGBColorComponents(float[] compArray) {
    return getColor().getRGBColorComponents(compArray);
  }

  @Override
  public float[] getRGBComponents(float[] compArray) {
    return getColor().getRGBComponents(compArray);
  }

  @Override
  public int getRed() {
    return getColor().getRed();
  }

  public Shade getShade() {
    return shade;
  }

  public UIColor getSourceColor() {
    return sourceColor;
  }

  @Override
  public int getTransparency() {
    return getColor().getTransparency();
  }

  private UIColor getColor() {
    if (shade == Shade.UIMANAGER) {
      UIColor c = (colorKey == null) ? null : Platform.getUIDefaults().getColor(colorKey);

      c = (c == null) ? sourceColor : c;

      if (c instanceof UIColorShade) {
        if (c == this) {
          c = sourceColor;
        } else {
          c = ((UIColorShade) c).getColor();
        }
      } else {
        sourceColor = c; // reset the source to the new color
      }

      return c;
    }

    if (cachedColor != null) {
      if (sourceColor.getRGB() != cachedRGB) {
        cachedColor = null;
      }
    }

    if (cachedColor == null) {
      cachedRGB = sourceColor.getRGB();

      switch (getShade()) {
        case DARKER:
          cachedColor = sourceColor.darker();

          break;

        case DARKER_DARKER:
          cachedColor = sourceColor.darker().darker();

          break;

        case BRIGHTER:
          cachedColor = sourceColor.brighter();

          break;

        case BRIGHTER_BRIGHTER:
          cachedColor = sourceColor.brighter().brighter();

          break;

        case LUM_ADJUSTMENT:
          cachedColor = new UIColor(ColorUtils.adjustLuminance(sourceColor.getARGB(), getLumAdjustment()));

          break;

        case DYN_LUM_ADJUSTMENT:
          if(sourceColor.isDarkColor()) {
            cachedColor = new UIColor(ColorUtils.adjustLuminance(sourceColor.getARGB(), getLumAdjustment()));
          }
          else {
            cachedColor = new UIColor(ColorUtils.adjustLuminance(sourceColor.getARGB(), -getLumAdjustment()));
          }

          break;

        case ALPHA:
          cachedColor = new UIColor(sourceColor.getRed(), sourceColor.getGreen(), sourceColor.getBlue(), alpha);
          
          break;

        default:
          cachedColor = sourceColor;
      }
    }

    return cachedColor;
  }
}
