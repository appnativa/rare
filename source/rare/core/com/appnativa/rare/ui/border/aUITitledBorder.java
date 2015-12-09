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
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.UIFontMetrics;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformShape;

import java.util.Locale;

public abstract class aUITitledBorder extends aUIPlatformBorder {
  public static final int        ABOVE_BOTTOM            = 4;
  public static final int        ABOVE_TOP               = 1;
  public static final int        BELOW_BOTTOM            = 6;
  public static final int        BELOW_TOP               = 3;
  public static final int        BOTTOM                  = 5;
  public static final int        CENTER                  = 2;
  public static final int        DEFAULT_JUSTIFICATION   = 0;
  public static final int        DEFAULT_POSITION        = 0;
  public static final int        LEADING                 = 4;
  public static final int        LEFT                    = 1;
  public static final int        RIGHT                   = 3;
  public static final int        TOP                     = 2;
  public static final int        TRAILING                = 5;
  protected static final float   EDGE_SPACING            = ScreenUtils.PLATFORM_PIXELS_2;
  protected static final float   TEXT_SPACING            = ScreenUtils.PLATFORM_PIXELS_2;
  protected static final float   TEXT_HORIZONTAL_SPACING = ScreenUtils.PLATFORM_PIXELS_4;
  private static iPlatformBorder defaultBorder           = new UILineBorder(UILineBorder.getDefaultLineColor(),
                                                             ScreenUtils.PLATFORM_PIXELS_1, true);
  protected String          title;
  protected iPlatformBorder titleBorder;
  protected UIColor         titleColor;
  protected UIFont          titleFont;
  protected int             titleJustification;
  protected int             titlePosition;

  public aUITitledBorder(final iPlatformBorder border) {
    this(border, "", LEADING, TOP, null, null);
  }

  public aUITitledBorder(final String title) {
    this(null, title, LEADING, TOP, null, null);
  }

  public aUITitledBorder(final iPlatformBorder border, final String title) {
    this(border, title, LEADING, TOP, null, null);
  }

  public aUITitledBorder(final iPlatformBorder border, final String title, final int titleJustification,
                         final int titlePosition) {
    this(border, title, titleJustification, titlePosition, null, null);
  }

  public aUITitledBorder(final iPlatformBorder border, final String title, final int titleJustification,
                         final int titlePosition, final UIFont titleFont) {
    this(border, title, titleJustification, titlePosition, titleFont, null);
  }

  public aUITitledBorder(final iPlatformBorder border, final String title, final int titleJustification,
                         final int titlePosition, final UIFont titleFont, final UIColor titleColor) {
    setTitleJustification(titleJustification);
    setTitlePosition(titlePosition);
    this.title = title;
    setBorder(border);
    setTitleFont(titleFont);
    setTitleColor(titleColor);
  }

  @Override
  public void clip(iPlatformGraphics g, float x, float y, float width, float height) {
    UIRectangle r = getInnerRect(null, x, y, width, height);

    getBorder().clip(g, r.x, r.y, r.width, r.height);
  }

  @Override
  public boolean clipsContents() {
    return getBorder().clipsContents();
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean last) {
    if (last != isPaintLast()) {
      return;
    }

    iPlatformBorder innerBorder  = getBorder();
    UIInsets        borderInsets = innerBorder.getBorderInsets((UIInsets) null);
    UIFont          f            = titleFont;

    if (f == null) {
      f = UIFontHelper.getDefaultFont();
    }

    UIFontMetrics fm                    = UIFontMetrics.getMetrics(f);
    float         h                     = fm.getHeight();
    float         stringWidth           = ((title == null)
            ? 0
            : fm.stringWidth(title));
    float         stringHeight          = h;
    float         borderTop             = 0;
    float         borderHeight          = (int) height;
    float         titleTop              = 0;
    float         titleLeft             = 0;
    boolean       titleTallerThanBorder = false;
    int           count                 = g.saveState();

    if (stringWidth > 0) {
      switch(convertLeadTrail(titleJustification)) {
        case DEFAULT_JUSTIFICATION :
        case LEFT :
          titleLeft = EDGE_SPACING + TEXT_HORIZONTAL_SPACING + TEXT_SPACING + borderInsets.left;

          break;

        case CENTER :
          titleLeft = (int) ((width - stringWidth) / 2);

          break;

        case RIGHT :
          titleLeft = (int) (width - stringWidth - borderInsets.right - TEXT_HORIZONTAL_SPACING - TEXT_SPACING
                             - EDGE_SPACING);

          break;
      }

      switch(titlePosition) {
        case ABOVE_TOP :
          borderTop    = stringHeight + TEXT_SPACING + TEXT_SPACING;
          titleTop     = TEXT_SPACING;
          borderHeight -= borderTop;

          break;

        case DEFAULT_POSITION :
        case TOP :
          borderTop             = stringHeight / 2 + TEXT_SPACING;
          titleTop              = borderTop - (stringHeight / 2) - TEXT_SPACING;
          borderHeight          -= borderTop;
          titleTallerThanBorder = (stringHeight > borderInsets.top);

          if (!titleTallerThanBorder) {
            titleTop  = (borderInsets.top - stringHeight) / 2;
            borderTop = 0;
          }

          break;

        case BELOW_TOP :
          titleTop = borderInsets.top + TEXT_SPACING;

          break;

        case ABOVE_BOTTOM :
          titleTop = (int) height - borderInsets.bottom - stringHeight - EDGE_SPACING;

          break;

        case BOTTOM :
          titleTallerThanBorder = (stringHeight > borderInsets.bottom);

          if (!titleTallerThanBorder) {
            borderHeight = (int) height;
            titleTop     = borderHeight - ((stringHeight + borderInsets.bottom) / 2) - EDGE_SPACING;
          } else {
            borderHeight -= (stringHeight / 2);
            titleTop     = borderHeight - ((stringHeight + borderInsets.bottom) / 2) - TEXT_SPACING - EDGE_SPACING;
          }

          break;

        case BELOW_BOTTOM :
          titleTop     = (int) height - stringHeight - TEXT_SPACING - EDGE_SPACING;
          borderHeight -= stringHeight + 1;

          break;
      }

      if (titleTallerThanBorder) {
        UIRectangle r = new UIRectangle(x + titleLeft + EDGE_SPACING, y + titleTop - 1, stringWidth + EDGE_SPACING,
                                        stringHeight + 2 * TEXT_SPACING);

        g.clipRect(r.x, r.y, r.width, r.height, iPlatformGraphics.Op.DIFFERENCE);
      }
    }

    paintIinnerBorder(innerBorder, g, x + EDGE_SPACING, y + EDGE_SPACING + borderTop, width - (2 * EDGE_SPACING),
                      borderHeight - (2 * EDGE_SPACING), innerBorder.isPaintLast());
    g.restoreToState(count);

    if ((title != null) && (title.length() > 0)) {
      g.setColor(getTitleColor());
      g.setFont(getTitleFont());
      g.drawString(title, x + titleLeft + EDGE_SPACING, y + titleTop + EDGE_SPACING, stringHeight);
    }
  }

  public static int toTitlePosition(String title) {
    if (title == null) {
      return DEFAULT_POSITION;
    }

    title = title.toUpperCase(Locale.ENGLISH);

    if (title.equals("ABOVE_TOP")) {
      return ABOVE_TOP;
    }

    if (title.equals("TOP")) {
      return TOP;
    }

    if (title.equals("BELOW_TOP")) {
      return BELOW_TOP;
    }

    if (title.equals("ABOVE_BOTTOM")) {
      return TOP;
    }

    if (title.equals("BOTTOM")) {
      return TOP;
    }

    if (title.equals("BELOW_BOTTOM")) {
      return TOP;
    }

    return DEFAULT_POSITION;
  }

  public void setBorder(final iPlatformBorder border) {
    this.titleBorder = border;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public void setTitleColor(final UIColor color) {
    titleColor = color;
  }

  public void setTitleFont(final UIFont font) {
    titleFont = font;
  }

  public void setTitleJustification(final int justification) {
    if ((justification < DEFAULT_JUSTIFICATION) || (TRAILING < justification)) {
      throw new IllegalArgumentException();
    }

    titleJustification = justification;
  }

  public void setTitleLocation(String loc) {
    if (loc == null) {
      return;
    }

    loc = loc.toLowerCase(Locale.ENGLISH);

    if (loc.equals("auto")) {
      setTitlePosition(aUITitledBorder.ABOVE_TOP);
      setTitleJustification(aUITitledBorder.LEFT);
    } else if (loc.equals("frame_top_left")) {
      setTitlePosition(aUITitledBorder.TOP);
      setTitleJustification(aUITitledBorder.LEADING);
    } else if (loc.equals("frame_top_center")) {
      setTitlePosition(aUITitledBorder.TOP);
      setTitleJustification(aUITitledBorder.CENTER);
    } else if (loc.equals("frame_top_right")) {
      setTitlePosition(aUITitledBorder.TOP);
      setTitleJustification(aUITitledBorder.TRAILING);
    } else if (loc.equals("top_left")) {
      setTitlePosition(aUITitledBorder.ABOVE_TOP);
      setTitleJustification(aUITitledBorder.LEADING);
    } else if (loc.equals("top_center")) {
      setTitlePosition(aUITitledBorder.ABOVE_TOP);
      setTitleJustification(aUITitledBorder.CENTER);
    } else if (loc.equals("top_right")) {
      setTitlePosition(aUITitledBorder.ABOVE_TOP);
      setTitleJustification(aUITitledBorder.TRAILING);
    } else if (loc.equals("inside_top_left")) {
      setTitlePosition(aUITitledBorder.BELOW_TOP);
      setTitleJustification(aUITitledBorder.LEADING);
    } else if (loc.equals("inside_top_center")) {
      setTitlePosition(aUITitledBorder.BELOW_TOP);
      setTitleJustification(aUITitledBorder.CENTER);
    } else if (loc.equals("inside_top_right")) {
      setTitlePosition(aUITitledBorder.BELOW_TOP);
      setTitleJustification(aUITitledBorder.TRAILING);
    } else if (loc.equals("inside_bottom_left")) {
      setTitlePosition(aUITitledBorder.ABOVE_BOTTOM);
      setTitleJustification(aUITitledBorder.LEADING);
    } else if (loc.equals("inside_bottom_center")) {
      setTitlePosition(aUITitledBorder.ABOVE_BOTTOM);
      setTitleJustification(aUITitledBorder.CENTER);
    } else if (loc.equals("inside_bottom_right")) {
      setTitlePosition(aUITitledBorder.ABOVE_BOTTOM);
      setTitleJustification(aUITitledBorder.TRAILING);
    } else if (loc.equals("bottom_left")) {
      setTitlePosition(aUITitledBorder.BELOW_BOTTOM);
      setTitleJustification(aUITitledBorder.LEADING);
    } else if (loc.equals("bottom_center")) {
      setTitlePosition(aUITitledBorder.BELOW_BOTTOM);
      setTitleJustification(aUITitledBorder.CENTER);
    } else if (loc.equals("bottom_right")) {
      setTitlePosition(aUITitledBorder.BELOW_BOTTOM);
      setTitleJustification(aUITitledBorder.TRAILING);
    } else if (loc.equals("frame_bottom_left")) {
      setTitlePosition(aUITitledBorder.BOTTOM);
      setTitleJustification(aUITitledBorder.LEADING);
    } else if (loc.equals("frame_bottom_center")) {
      setTitlePosition(aUITitledBorder.BOTTOM);
      setTitleJustification(aUITitledBorder.CENTER);
    } else if (loc.equals("frame_bottom_right")) {
      setTitlePosition(aUITitledBorder.BOTTOM);
      setTitleJustification(aUITitledBorder.TRAILING);
    }
  }

  public void setTitlePosition(final int pos) {
    if ((pos < DEFAULT_POSITION) || (BELOW_BOTTOM < pos)) {
      throw new IllegalArgumentException();
    }

    titlePosition = pos;
  }

  public iPlatformBorder getBorder() {
    return (titleBorder != null)
           ? titleBorder
           : getDefaultBorder();
  }

  @Override
  public UIInsets getBorderInsets(UIInsets insets) {
    float spacing  = EDGE_SPACING;
    float tspacing = TEXT_SPACING * 2;

    if (insets == null) {
      insets = new UIInsets(spacing, spacing, spacing, spacing);
    } else {
      insets.set(spacing, spacing, spacing, spacing);
    }

    UIInsets insideInsets = getBorder().getBorderInsets((UIInsets) null);

    insets.addInsets(insideInsets);

    if ((title != null) && (title.length() > 0)) {
      UIFont f = titleFont;

      if (f == null) {
        f = UIFontHelper.getDefaultFont();
      }

      UIFontMetrics fm = UIFontMetrics.getMetrics(f);
      int           h  = (int) fm.getHeight();

      switch(titlePosition) {
        case ABOVE_TOP :
          insets.top += h;
          insets.top += tspacing;

          break;

        case TOP :
        case DEFAULT_POSITION :
          if (h > insets.bottom) {
            insets.top += (h / 2);
            insets.top += tspacing + tspacing;
          }

          break;

        case BELOW_TOP :
          insets.top += h;
          insets.top += tspacing;

          break;

        case ABOVE_BOTTOM :
          insets.bottom += h;
          insets.bottom += tspacing;

          break;

        case BOTTOM :
          if (h > insets.bottom) {
            insets.bottom += (h / 2);
            insets.bottom += tspacing;
          }

          break;

        case BELOW_BOTTOM :
          insets.bottom += h;
          insets.bottom += tspacing;

          break;
      }
    }

    return insets;
  }

  @Override
  public iPlatformShape getShape(iPlatformGraphics g, float x, float y, float width, float height) {
    UIRectangle r = getInnerRect(null, x, y, width, height);

    return getBorder().getShape(g, r.x, r.y, r.width, r.height);
  }

  public String getTitle() {
    return title;
  }

  public UIColor getTitleColor() {
    return (titleColor != null)
           ? titleColor
           : getDefaultTitleColor();
  }

  public UIFont getTitleFont() {
    return (titleFont != null)
           ? titleFont
           : getDefaultTitleFont();
  }

  public int getTitleJustification() {
    return titleJustification;
  }

  public int getTitlePosition() {
    return titlePosition;
  }

  public boolean isBorderOpaque() {
    return false;
  }

  @Override
  public boolean isPaintLast() {
    return getBorder().isPaintLast()
           ? true
           : false;
  }

  @Override
  public boolean isRectangular() {
    return getBorder().isRectangular();
  }

  protected static int convertLeadTrail(final int pos) {
    final boolean isLTR = true;

    if (pos == LEADING) {
      return isLTR
             ? LEFT
             : RIGHT;
    }

    if (pos == TRAILING) {
      return isLTR
             ? RIGHT
             : LEFT;
    }

    return pos;
  }

  protected abstract void paintIinnerBorder(iPlatformBorder b, iPlatformGraphics g, float x, float y, float width,
          float height, boolean end);

  protected iPlatformBorder getDefaultBorder() {
    iPlatformBorder b = Platform.getUIDefaults().getBorder("Rare.TitledBorder.border");

    if (b == null) {
      b = defaultBorder;
    }

    return b;
  }

  protected static UIColor getDefaultTitleColor() {
    UIColor c = Platform.getUIDefaults().getColor("Rare.TitledBorder.titleColor");

    if (c == null) {
      c = Platform.getUIDefaults().getColor("Rare.foreground");
    }

    return c;
  }

  protected static UIFont getDefaultTitleFont() {
    UIFont f = Platform.getUIDefaults().getFont("Rare.TitledBorder.font");

    if (f == null) {
      f = FontUtils.getDefaultFont();
    }

    return f;
  }

  protected UIRectangle getInnerRect(UIRectangle r, float x, float y, float width, float height) {
    if (r == null) {
      r = new UIRectangle();
    }

    iPlatformBorder innerBorder  = getBorder();
    UIInsets        borderInsets = innerBorder.getBorderInsets((UIInsets) null);

    if ((title != null) && (title.length() > 0)) {
      UIFont f = titleFont;

      if (f == null) {
        f = UIFontHelper.getDefaultFont();
      }

      UIFontMetrics fm                    = UIFontMetrics.getMetrics(f);
      float         h                     = fm.getHeight();
      float         stringHeight          = h;
      float         borderTop             = 0;
      float         borderHeight          = (int) height;
      boolean       titleTallerThanBorder = false;

      switch(titlePosition) {
        case ABOVE_TOP :
          borderTop    = stringHeight + TEXT_SPACING + TEXT_SPACING;
          borderHeight -= borderTop;

          break;

        case DEFAULT_POSITION :
        case TOP :
          borderTop             = stringHeight / 2 + TEXT_SPACING;
          borderHeight          -= borderTop;
          titleTallerThanBorder = (stringHeight > borderInsets.top);

          if (!titleTallerThanBorder) {
            borderTop = 0;
          }

          break;

        case BELOW_TOP :
          break;

        case BOTTOM :
          titleTallerThanBorder = (stringHeight > borderInsets.bottom);

          if (!titleTallerThanBorder) {
            borderHeight = (int) height;
          } else {
            borderHeight -= (stringHeight / 2);
          }

          break;

        case BELOW_BOTTOM :
          borderHeight -= stringHeight + 1;

          break;
      }

      r.setBounds(x + EDGE_SPACING, y + EDGE_SPACING + borderTop, width - (2 * EDGE_SPACING),
                  borderHeight - (2 * EDGE_SPACING));
    } else {
      r.setBounds(x, y, width, height);
    }

    return r;
  }
}
