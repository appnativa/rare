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

import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;

/**
 * A class that holds paint information
 *
 * @author Don DeCoteau
 */
public class PaintBucket implements Cloneable {
  protected UIColor                   backgroundColor;
  protected iBackgroundPainter        backgroundPainter;
  protected iPlatformBorder           border;
  protected iPlatformComponentPainter cachedComponentPainter;
  protected UIFont                    font;
  protected UIColor                   foregroundColor;
  protected iImagePainter             imagePainter;

  /**
   * Creates a new instance of PaintBucket
   */
  public PaintBucket() {}

  /**
   * Creates a new instance of PaintBucket
   *
   * @param bp the component painter
   */
  public PaintBucket(iBackgroundPainter bp) {
    backgroundPainter = bp;
  }

  /**
   * Creates a new instance of PaintBucket
   *
   *
   * @param bg the backgroundColor color
   */
  public PaintBucket(UIColor bg) {
    this(null, bg);
  }

  /**
   * Creates a new instance of PaintBucket
   *
   *
   * @param fg the foregroundColor color
   * @param bg the backgroundColor color
   */
  public PaintBucket(UIColor fg, UIColor bg) {
    foregroundColor = fg;
    backgroundColor = bg;

    if (bg instanceof UIColorShade) {
      backgroundPainter = ((UIColorShade) bg).getBackgroundPainter();
    }
  }

  /**
   * Creates and returns a copy of this object.
   *
   * @return  a copy of this object.
   */
  @Override
  public Object clone() {
    try {
      PaintBucket pb = (PaintBucket) super.clone();

      pb.cachedComponentPainter = null;

      return pb;
    } catch(CloneNotSupportedException ex) {
      PaintBucket ch = new PaintBucket();

      ch.font              = font;
      ch.border            = border;
      ch.foregroundColor   = foregroundColor;
      ch.backgroundColor   = backgroundColor;
      ch.backgroundPainter = backgroundPainter;

      return ch;
    }
  }

  /**
   * Removes all paint information
   */
  public void empty() {
    backgroundColor        = null;
    backgroundPainter      = null;
    border                 = null;
    font                   = null;
    foregroundColor        = null;
    imagePainter           = null;
    cachedComponentPainter = null;
  }

  /**
   * Install the paint values for the specified component
   *
   * @param c the component
   */
  public void install(iPlatformComponent c) {
    install(c, true);
  }

  /**
   * Install the paint values for the specified component
   *
   * @param c the component
   * @param useFont true to install the available font; false otherwise
   */
  public void install(iPlatformComponent c, boolean useFont) {
    if (foregroundColor != null) {
      c.setForeground(foregroundColor);
    }

    if (useFont && (font != null)) {
      c.setFont(font);
    }

    iPlatformComponentPainter cp = getComponentPainter(c.getComponentPainter(), false);

    if (cp != null) {
      c.setComponentPainter(cp);

      if (cp.getBackgroundPainter() == null) {
        if (backgroundColor != null) {
          c.setBackground(backgroundColor);
        }
      }

      return;
    }

    if (border != null) {
      c.setBorder(border);
    }

    if (backgroundColor != null) {
      c.setBackground(backgroundColor);
    }
  }

  public void setBackgroundColor(UIColor bg) {
    cachedComponentPainter = null;

    if (bg instanceof UIColorShade) {
      UIColorShade s = (UIColorShade) bg;

      switch(s.getShade()) {
        case BGPAINT :
          this.setBackgroundPainter(s.getBackgroundPainter());

          break;

        default :
          break;
      }
    }

    backgroundColor = bg;
  }

  /**
   *   Sets the backgroundColor painter
   *
   *   @param bp the backgroundColor painter
   */
  public void setBackgroundPainter(iBackgroundPainter bp) {
    cachedComponentPainter = null;
    backgroundPainter      = bp;
  }

  /**
   * Sets the border
   *
   * @param border the border
   */
  public void setBorder(iPlatformBorder border) {
    cachedComponentPainter = null;
    this.border            = border;
  }

  /**
   * @param font the font to set
   */
  public void setFont(UIFont font) {
    this.font = font;
  }

  /**
   * Sets the foregroundColor color
   *
   * @param fg the foregroundColor color
   */
  public void setForegroundColor(UIColor fg) {
    foregroundColor = fg;
  }

  /**
   * Sets the image painter
   *
   * @param ip the image painter
   */
  public void setImagePainter(iImagePainter ip) {
    cachedComponentPainter = null;
    this.imagePainter      = ip;
  }

  public UIColor getBackgroundColor() {
    return backgroundColor;
  }

  public UIColor getBackgroundColorAlways() {
    if(backgroundColor!=null) {
      return backgroundColor;
    }
    if(backgroundPainter!=null) {
      return backgroundPainter.getBackgroundColor();
    }
    return null;
  }

  public static UIColor getBackgroundColor(RenderableDataItem item) {
    iPlatformComponentPainter cp = item.getComponentPainter();
    UIColor                   bg = item.getBackground();

    if (bg == null) {
      if (cp != null) {
        bg = cp.getBackgroundColor();

        if ((bg == null) && (cp.getBackgroundPainter() != null)) {
          bg = cp.getBackgroundPainter().getBackgroundColor();
        }
      }
    }

    return bg;
  }

  /**
   *   Gets the backgroundColor painter
   *
   *   @return the backgroundColor painter
   */
  public iBackgroundPainter getBackgroundPainter() {
    return backgroundPainter;
  }

  /**
   * Gets the color shade
   *
   * @return the color shade
   */
  public UIColor getBackgroundShade() {
    if ((backgroundColor instanceof UIColorShade) && ((UIColorShade) backgroundColor).getBackgroundPainter() != null) {
      return backgroundColor;
    }

    if (backgroundPainter != null) {
      return new UIColorShade(backgroundPainter);
    }

    return backgroundColor;
  }

  /**
   * Gets the border
   *
   * @return the border
   */
  public iPlatformBorder getBorder() {
    return border;
  }

  public iPlatformComponentPainter getCachedComponentPainter() {
    if ((cachedComponentPainter != null) && cachedComponentPainter.isDisposed()) {
      cachedComponentPainter = null;
    }

    if (cachedComponentPainter == null) {
      cachedComponentPainter = getComponentPainter(true);
    }

    return cachedComponentPainter;
  }

  /**
   * Gets a component that can paint from this paint buck
   *
   * @return a component that can paint from this paint buck
   */
  public iPlatformComponentPainter getComponentPainter() {
    return getComponentPainter(null, true);
  }

  /**
   * Gets a component that can paint from this paint buck
   *
   * @param always to always return a component painter object
   * @return a component that can paint from this paint buck
   */
  public iPlatformComponentPainter getComponentPainter(boolean always) {
    return getComponentPainter(null, always);
  }

  /**
   * @return the font
   */
  public UIFont getFont() {
    return font;
  }

  /**
   * Gets the foregroundColor color
   *
   * @return the foregroundColor color
   */
  public UIColor getForegroundColor() {
    return foregroundColor;
  }

  public iGradientPainter getGradientPainter() {
    if (backgroundPainter instanceof iGradientPainter) {
      return (iGradientPainter) backgroundPainter;
    }

    return null;
  }

  /**
   * Gets the image painter
   *
   * @return the image painter
   */
  public iImagePainter getImagePainter() {
    return imagePainter;
  }

  /**
   * Gets a painter for the bucket
   * Will return a background painter, a component painter or null
   * depending on the content of the bucket.
   *
   * @return a painter for the bucket
   */
  public iPlatformPainter getPainter() {
    if ((border == null) && (backgroundColor == null)) {
      return getBackgroundPainter();
    }

    if (border == null) {
      return (backgroundPainter == null)
             ? UIColorHelper.getBackgroundPainter(backgroundColor)
             : backgroundPainter;
    }

    return getComponentPainter(true);
  }

  /**
   * Returns if the paint bucket will paint the background
   * @return true if the paint bucket will paint the background
   */
  public boolean isBackgroundPaint() {
    if (backgroundColor != null) {
      return true;
    }

    if (backgroundPainter != null) {
      return true;
    }

    if (imagePainter != null) {
      return true;
    }

    return false;
  }

  /**
   * Returns if the paint bucket is empty
   * @return true if the paint bucket is empty; false otherwise
   */
  public boolean isEmpty() {
    if (foregroundColor != null) {
      return false;
    }

    if (backgroundColor != null) {
      return false;
    }

    if (backgroundPainter != null) {
      return false;
    }

    if (imagePainter != null) {
      return false;
    }

    return true;
  }

  protected iPlatformComponentPainter getComponentPainter(iPlatformComponentPainter cp, boolean always) {
    if (always || (imagePainter != null) || (backgroundPainter != null)
        || ((backgroundColor != null) && (border != null) &&!border.isPaintLast())) {
      cp = (cp == null)
           ? new UIComponentPainter()
           : cp;
      cp.setBackgroundOverlayPainter(imagePainter);
      cp.setBackgroundPainter(backgroundPainter, false);

      if ((backgroundPainter == null) && (backgroundColor != null)) {
        cp.setBackgroundPainter(new UISimpleBackgroundPainter(backgroundColor), false);
      }

      if (border != null) {
        cp.setBorder(border);
      }
    } else if ((cp != null) && (backgroundColor != null)) {
      cp.setBackgroundPainter(new UISimpleBackgroundPainter(backgroundColor), false);
    }
    
    if ((cp != null) && (border != null)) {
      cp.setBorder(border);
    }

    return cp;
  }
}
