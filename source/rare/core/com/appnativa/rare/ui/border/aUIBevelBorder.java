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
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPath;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aUIBevelBorder extends aUIPlatformBorder {
  public static final int      LOWERED = 0;
  public static final int      RAISED  = 1;
  private static UIBevelBorder sharedBevelLoweredBorder;
  private static UIBevelBorder sharedBevelRaisedBorder;
  private static UIBevelBorder sharedLoweredBorder;
  private static UIBevelBorder sharedRaisedBorder;
  int                          arcHeight;
  int                          arcWidth;
  protected float              thickness  = ScreenUtils.PLATFORM_PIXELS_1;
  protected UIInsets           mainInsets = new UIInsets(ScreenUtils.PLATFORM_PIXELS_1);
  protected int                bevelType;
  protected boolean            flatBottom;
  protected UIColor            highlightInner;
  protected UIColor            highlightOuter;
  protected UIColor            shadowInner;
  protected UIColor            shadowOuter;
  private int                  colorAlpha     = 255;
  private boolean              fourColorBevel = true;
  protected UIInsets           padding;

  /**
   * Creates a one-line bevel border with the specified type and whose colors
   * will be derived from the background color of the component passed into the
   * paintBorder method.
   *
   * @param bevelType
   *          the type of bevel for the border
   */
  public aUIBevelBorder(int bevelType) {
    this.bevelType = bevelType;
  }

  /**
   * Creates a one-line bevel border with the specified type and whose colors
   * will be derived from the background color of the component passed into the
   * paintBorder method.
   *
   * @param bevelType
   *          the type of bevel for the border
   * @param fourcolor
   *          true for a four color bevel; false for a 2 color
   */
  public aUIBevelBorder(int bevelType, boolean fourcolor) {
    this(bevelType);
    setFourColorBevel(fourcolor);
  }

  /**
   * Creates a one-line bevel border with the specified type, highlight and
   * shadow colors.
   *
   * @param bevelType
   *          the type of bevel for the border
   * @param highlight
   *          the color to use for the bevel highlight
   * @param shadow
   *          the color to use for the bevel shadow
   */
  public aUIBevelBorder(int bevelType, UIColor highlight, UIColor shadow, boolean fourColor) {
    this(bevelType, highlight.brighter(), highlight, shadow, shadow.brighter(), fourColor);
  }

  /**
   * Creates a bevel border with the specified type, highlight and shadow
   * colors.
   * <p>
   * Note: The shadow inner and outer colors are switched for a lowered bevel
   * border.
   *
   * @param bevelType
   *          the type of bevel for the border
   * @param highlightOuterColor
   *          the color to use for the bevel outer highlight
   * @param highlightInnerColor
   *          the color to use for the bevel inner highlight
   * @param shadowOuterColor
   *          the color to use for the bevel outer shadow
   * @param shadowInnerColor
   *          the color to use for the bevel inner shadow
   */
  public aUIBevelBorder(int bevelType, UIColor highlightOuterColor, UIColor highlightInnerColor,
                        UIColor shadowOuterColor, UIColor shadowInnerColor) {
    this(bevelType, highlightOuterColor, highlightInnerColor, shadowOuterColor, shadowInnerColor, true);
  }

  public aUIBevelBorder(int bevelType, UIColor highlightOuterColor, UIColor highlightInnerColor,
                        UIColor shadowOuterColor, UIColor shadowInnerColor, boolean fourColor) {
    this(bevelType);
    this.highlightOuter = highlightOuterColor;
    this.highlightInner = highlightInnerColor;
    this.shadowOuter    = shadowOuterColor;
    this.shadowInner    = shadowInnerColor;
    setFourColorBevel(fourColor);
  }

  @Override
  public Object clone() {
    aUIBevelBorder b = (aUIBevelBorder) super.clone();

    b.mainInsets = new UIInsets(mainInsets);

    return b;
  }

  public static UIBevelBorder createBorder(int bevelType, boolean fourcolor) {
    if (bevelType == UIBevelBorder.RAISED) {
      if (!fourcolor) {
        if (sharedRaisedBorder == null) {
          resetBorderCache();
        }

        return fourcolor
               ? sharedBevelRaisedBorder
               : sharedRaisedBorder;
      }
    } else if (bevelType == UIBevelBorder.LOWERED) {
      if (!fourcolor) {
        if (sharedLoweredBorder == null) {
          resetBorderCache();
        }

        return fourcolor
               ? sharedBevelLoweredBorder
               : sharedLoweredBorder;
      }
    }

    return new UIBevelBorder(bevelType, fourcolor);
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean last) {
    if (last != isPaintLast()) {
      return;
    }

    UIStroke stroke = g.getStroke();
    UIColor  c      = g.getColor();

    g.setStroke(UIStroke.SOLID_STROKE);
    g.setStrokeWidth(1.25f);

    if (bevelType == RAISED) {
      paintRaisedBevel(g, x, y, width, height);
    } else if (bevelType == LOWERED) {
      paintLoweredBevel(g, x, y, width, height);
    }

    g.setStroke(stroke);
    g.setColor(c);
  }

  public static void resetBorderCache() {
    sharedRaisedBorder       = new UIBevelBorder(UIBevelBorder.RAISED, false);
    sharedLoweredBorder      = new UIBevelBorder(UIBevelBorder.LOWERED, false);
    sharedBevelRaisedBorder  = new UIBevelBorder(UIBevelBorder.RAISED, true);
    sharedBevelLoweredBorder = new UIBevelBorder(UIBevelBorder.LOWERED, true);
  }

  public void setColorAlpha(int colorAlpha) {
    this.colorAlpha = colorAlpha % 256;
  }

  public void setInsetsPadding(float top, float right, float bottom, float left) {
    if (padding == null) {
      padding = new UIInsets();
    }

    padding.set(top, right, bottom, left);
  }

  /**
   * Sets the border colors
   *
   * @param so
   *          the color to use for the bevel outer shadow
   * @param ho
   *          the color to use for the bevel outer highlight
   */
  public void setColors(UIColor so, UIColor ho) {
    if ((ho == null) && (so != null)) {
      ho = so.brighter();
      so = so.darker();
    }

    shadowOuter    = so;
    highlightOuter = ho;
  }

  /**
   * Sets the border colors
   *
   * @param so
   *          the color to use for the bevel outer shadow
   * @param si
   *          the color to use for the bevel inner shadow
   * @param ho
   *          the color to use for the bevel outer highlight
   * @param hi
   *          the color to use for the bevel inner highlight
   */
  public void setColors(UIColor so, UIColor si, UIColor ho, UIColor hi) {
    int alpha = 255;

    if ((so != null) && (si == null) && (ho == null) && (hi == null)) {
      alpha = so.getAlpha();
    }

    if ((ho == null) && (so != null)) {
      ho = so.brighter();
      so = so.darker();
    }

    if ((si == null) && (so != null)) {
      si = so;
      so = so.darker();
    }

    if ((hi == null) && (ho != null)) {
      hi = ho;
      ho = ho.brighter();
    }

    if (alpha != 255) {
      si = new UIColor(si.getRed(), si.getGreen(), si.getBlue(), alpha);
      ho = new UIColor(ho.getRed(), ho.getGreen(), ho.getBlue(), alpha);
      hi = new UIColor(hi.getRed(), hi.getGreen(), hi.getBlue(), alpha);
    }

    shadowOuter    = so;
    shadowInner    = si;
    highlightOuter = ho;
    highlightInner = hi;
    modCount++;
  }

  public void setFlatBottom(boolean flat) {
    flatBottom = flat;
  }

  public void setFourColorBevel(boolean fourcolor) {
    this.fourColorBevel = fourcolor;

    if (fourcolor) {
      thickness = 2;
    }

    mainInsets.left = mainInsets.right = mainInsets.top = mainInsets.bottom = thickness;
    modCount++;
  }

  public void setInsets(UIInsets in) {
    if (in == null) {
      mainInsets.set(0);
    } else {
      mainInsets.set(in.top, in.right, in.bottom, in.left);
    }

    modCount++;
  }

  public void setNoBottom(boolean noBottom) {
    this.flatBottom = noBottom;

    if (noBottom) {
      mainInsets.bottom = 0;
    }
  }

  @Override
  public UIInsets getBorderInsets(UIInsets insets) {
    insets        = getBorderInsetsEx(insets);
    insets.left   = (mainInsets.left > 0)
                    ? mainInsets.left
                    : 0;
    insets.right  = (mainInsets.right > 0)
                    ? mainInsets.right
                    : 0;
    insets.top    = (mainInsets.top > 0)
                    ? mainInsets.top
                    : 0;
    insets.bottom = (mainInsets.bottom > 0)
                    ? mainInsets.bottom
                    : 0;

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

    insets.left   = (mainInsets.left > 0)
                    ? mainInsets.left
                    : 0;
    insets.right  = (mainInsets.right > 0)
                    ? mainInsets.right
                    : 0;
    insets.top    = (mainInsets.top > 0)
                    ? mainInsets.top
                    : 0;
    insets.bottom = (mainInsets.bottom > 0)
                    ? mainInsets.bottom
                    : 0;

    return insets;
  }

  public int getColorAlpha() {
    return colorAlpha;
  }

  public UIColor getHighlightInnerColor() {
    UIColor co = highlightInner;

    if (co == null) {
      co = Platform.getUIDefaults().getColor("Rare.BevelBorder.highlightInnerColor");
    }

    if (colorAlpha != 255) {
      co = new UIColor(co.getRed(), co.getGreen(), co.getBlue(), colorAlpha);
    }

    return co;
  }

  public UIColor getHighlightOuterColor() {
    UIColor co = highlightOuter;

    if (co == null) {
      co = Platform.getUIDefaults().getColor("Rare.BevelBorder.highlightOuterColor");
    }

    if (colorAlpha != 255) {
      co = new UIColor(co.getRed(), co.getGreen(), co.getBlue(), colorAlpha);
    }

    return co;
  }

  public UIColor getShadowInnerColor() {
    UIColor co = shadowInner;

    if (co == null) {
      co = Platform.getUIDefaults().getColor("Rare.BevelBorder.shadowInnerColor");
    }

    if (colorAlpha != 255) {
      co = new UIColor(co.getRed(), co.getGreen(), co.getBlue(), colorAlpha);
    }

    return co;
  }

  public UIColor getShadowOuterColor() {
    UIColor co = shadowOuter;

    if (co == null) {
      co = Platform.getUIDefaults().getColor("Rare.BevelBorder.shadowOuterColor");
    }

    if (colorAlpha != 255) {
      co = new UIColor(co.getRed(), co.getGreen(), co.getBlue(), colorAlpha);
    }

    return co;
  }

  public boolean isFourColorBevel() {
    return fourColorBevel;
  }

  @Override
  public boolean isPadForArc() {
    return padForArc;
  }

  @Override
  public boolean isPaintLast() {
    return true;
  }

  protected void paintBevel(iPlatformGraphics g, float x, float y, float w, float h, UIColor top, UIColor bottom,
                            UIColor top2, UIColor bottom2) {
    // float xx = x + w;
    // float yy = y + h;
    boolean arc     = ((arcWidth > 0) || (arcHeight > 0));
    int     ah      = arcHeight / 4;
    int     aw      = arcWidth / 4;
    boolean clipped = false;

    if (arc) {
      float d1 = ScreenUtils.PLATFORM_PIXELS_1;
      float d3 = ScreenUtils.PLATFORM_PIXELS_3;

      g.saveState();
      g.setStrokeWidth(1.33f);
      g.setColor(top);
      g.clipRect(x, y, w, h / 2);
      g.drawRoundRect(x, y, w - d1, h - d1, arcWidth, arcHeight);

      if (top2 != null) {
        g.setColor(top2);
        g.drawRoundRect(x + d1, y + d1, w - d3, h - d3, arcWidth, arcHeight);
      }

      g.restoreState();

      if (!flatBottom) {
        g.saveState();
        g.setStrokeWidth(1.33f);
        g.clipRect(x, y + (h / 2), w, h / 2 + d1);
        g.setColor(bottom);
        g.drawRoundRect(x, y, w - d1, h - d1, arcWidth, arcHeight);

        if (bottom2 != null) {
          g.setColor(bottom2);
          g.drawRoundRect(x + d1, y + d1, w - d3, h - d3, arcWidth, arcHeight);
        }

        g.restoreState();
      }

      iPlatformPath p = PlatformHelper.createPath();

      p.drawRoundedRect(x, y, w, h, arcWidth + 1, arcHeight + 1);
      g.clipShape(p);
      clipped = true;
    }

    paintLines(g, x, y, w, h, top, bottom, top2, bottom2, ah, aw, true);

    if (clipped) {
      g.restoreState();
    }

    if (flatBottom) {
      paintLines(g, x, y + (h / 2) - 1, w, h, top, bottom, top2, bottom2, 0, 0, false);
    }
  }

  protected void paintRaisedBevel(iPlatformGraphics g, float x, float y, float w, float h) {
    if (isFourColorBevel()) {
      paintBevel(g, x, y, w, h, getShadowOuterColor(), getHighlightOuterColor(), getShadowInnerColor(),
                 getHighlightInnerColor());
    } else {
      paintBevel(g, x, y, w, h, getHighlightOuterColor(), getShadowOuterColor(), null, null);
    }
  }

  protected void paintLoweredBevel(iPlatformGraphics g, float x, float y, float w, float h) {
    if (isFourColorBevel()) {
      paintBevel(g, x, y, w, h, getHighlightOuterColor(), getShadowOuterColor(), getHighlightInnerColor(),
                 getShadowInnerColor());
    } else {
      paintBevel(g, x, y, w, h, getShadowOuterColor(), getHighlightOuterColor(), null, null);
    }
  }

  protected void paintLines(iPlatformGraphics g, float x, float y, float w, float h, UIColor top, UIColor bottom,
                            UIColor top2, UIColor bottom2, int ah, int aw, boolean drawtop) {
    UIColor oldColor = g.getColor();

    g.setColor(top);

    float d1 = ScreenUtils.PLATFORM_PIXELS_1;

    if (drawtop && (mainInsets.top > 0)) {
      g.fillRect(x + aw, y, w - (2 * aw), d1);
      // g.drawLine(x + aw, y, xx - (2 * aw), y); // top
    }

    if (mainInsets.left > 0) {
      g.fillRect(x, y + ah, d1, h - (2 * ah));    // left
      // g.drawLine(x, y + ah, x, yy - (2 * ah)); // left
    }

    g.setColor(bottom);

    if (mainInsets.bottom > 0) {
      g.fillRect(x + aw, y + h - d1, w - (2 * aw), d1);    // bottom
      // g.drawLine(x + aw, yy, xx - (2 * aw), yy); // bottom
    }

    if (mainInsets.right > 0) {
      g.fillRect(x + w - d1, y + ah, d1, h - (2 * ah));    // right
      // g.drawLine(xx, y + ah, xx, yy - (2 * ah)); // right
    }

    y += d1;
    x += d1;
    // yy -= 1;
    // xx -= 1;
    w -= d1;
    h -= d1;

    if (top2 != null) {
      g.setColor(top2);

      if (drawtop && (mainInsets.top > 0)) {
        g.fillRect(x + aw, y, w - (2 * aw), d1);
        // g.drawLine(x + aw, y, xx - (2 * aw), y); // top
      }

      if (mainInsets.left > 0) {
        g.fillRect(x, y + ah, d1, h - (2 * ah));    // left
        // g.drawLine(x, y + ah, x, yy - (2 * ah)); // left
      }
    }

    if (bottom2 != null) {
      g.setColor(bottom2);

      if (mainInsets.bottom > 0) {
        g.fillRect(x + aw, y + h - d1, w - (2 * aw), d1);    // bottom
        // g.drawLine(x + aw, yy, xx - (2 * aw), yy); // bottom
      }

      if (mainInsets.right > 0) {
        g.fillRect(x + w - d1, y + ah, d1, h - (2 * ah));    // right
        // g.drawLine(xx, y + ah, xx, yy - (2 * ah)); // right
      }
    }

    g.setColor(oldColor);
  }
}
