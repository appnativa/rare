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
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.widget.iWidget;

import java.util.List;

public class UIFontHelper {
  public static UIFont parseFont(iWidget context, String s) {
    return FontUtils.parseFont(context, null, s);
  }

  public static UIFont parseFont(UIFont base, String s) {
    return FontUtils.parseFont(Platform.getContextRootViewer(), base, s);
  }

  public static float stringWidth(UIFont font, String string) {
    return UIFontMetrics.getMetrics(font).stringWidth(string);
  }

  public static void setDefaultFont(UIFont font) {
    FontUtils.setDefaultFont(font);
  }

  public static void setRelativeFontSize(float size) {
    FontUtils.setRelativeFontSize(size);
  }

  /**
   * Returns a list of all the names of available fonts in the system
   *
   * @return a list of all the names of available fonts in the system
   */
  public static List<String> getAvailableFontNames() {
    return PlatformHelper.getAvailableFontNames();
  }

  /**
   * Returns a list of all available fonts in the system
   *
   * @return a list of all available fonts in the system
   */
  public static List<UIFont> getAvailableFonts() {
    return PlatformHelper.getAvailableFonts();
  }

  /**
   *  Get the width of the 'w' character for the default font
   *
   *  @return the width of the 'w' character
   */
  public static int getCharacterWidth() {
    return FontUtils.getCharacterWidth(getDefaultFont());
  }

  /**
   *  Get the width of the 'w' character for the given font
   *
   *  @param f the font
   *  @return the width of the 'w' character
   */
  public static int getCharacterWidth(UIFont f) {
    return FontUtils.getCharacterWidth(f);
  }

  public static UIFont getDefaultFont() {
    return FontUtils.getDefaultFont();
  }

  public static float getDefaultFontSize() {
    return FontUtils.getDefaultFontSize();
  }

  public static float getDefaultLineHeight() {
    return FontUtils.getDefaultLineHeight();
  }

  public static UIFont getFont(com.appnativa.rare.spot.Font fi) {
    if (fi == null) {
      return null;
    }

    UIFont f = null;

    if (fi.spot_hasValue()) {
      String size   = fi.size.getValue();
      int    style  = fi.style.spot_valueWasSet()
                      ? fi.style.intValue()
                      : -1;
      String family = fi.family.getValue();
      UIFont base   = null;

      if ((family != null) && "true".equals(fi.family.spot_getAttribute("is_property"))) {
        base = Platform.getUIDefaults().getFont(family);

        if ((base != null) && (size == null) && (style == -1)) {
          return base;
        }
      }

      if (base == null) {
        base = UIFontHelper.getSystemFont();
      }

      f = FontUtils.getFont(base, family, style, size, fi.monospaced.booleanValue(), fi.underlined.booleanValue(),
                            fi.strikeThrough.booleanValue());
    }

    return f;
  }

  /**
   * Returns the UI font corresponding the specified font configuration object
   *
   * @param context
   *          the widget context
   * @param fi
   *          the font configuration object
   *
   * @return the corresponding UI font
   */
  public static UIFont getFont(iWidget context, com.appnativa.rare.spot.Font fi) {
    if (fi == null) {
      return null;
    }

    UIFont f = null;

    if (fi.spot_hasValue()) {
      iPlatformComponent c      = context.getDataComponent();
      String             size   = fi.size.getValue();
      int                style  = fi.style.spot_valueWasSet()
                                  ? fi.style.intValue()
                                  : -1;
      String             family = fi.family.getValue();
      UIFont             base   = null;

      if ((family != null) && "true".equals(fi.family.spot_getAttribute("is_property"))) {
        base = context.getAppContext().getUIDefaults().getFont(family);

        if ((base != null) && (size == null) && (style == -1)) {
          return base;
        }
      }

      if ((base == null) && (c != null)) {
        base = c.getFontEx();
      }

      if (base == null) {
        base = FontUtils.getDefaultFont();
      }

      f = FontUtils.getFont(base, family, style, size, fi.monospaced.booleanValue(), fi.underlined.booleanValue(),
                            fi.strikeThrough.booleanValue());
    }

    return f;
  }

  public static UIFont getFont(UIFont base, com.appnativa.rare.spot.Font fi) {
    UIFont f = base;

    if ((fi != null) && fi.spot_hasValue()) {
      int style = fi.style.spot_valueWasSet()
                  ? fi.style.intValue()
                  : -1;

      f = FontUtils.getFont(base, fi.family.getValue(), style, fi.size.getValue(), fi.monospaced.booleanValue(),
                            fi.underlined.booleanValue(), fi.strikeThrough.booleanValue());
    }

    return f;
  }

  public static UIFont getFont(UIFont base, String family, int style, String ssize, boolean monospaced) {
    return FontUtils.getFont(base, family, style, ssize, monospaced, false, false);
  }

  public static UIFont getFont(UIFont base, String family, String style, String ssize, boolean monospaced) {
    return FontUtils.getFont(base, family, style, ssize, monospaced);
  }

  public static float getFontHeight(UIFont font) {
    return FontUtils.getFontHeight(font, true);
  }

  public static float getRelativeFontSize() {
    return FontUtils.getRelativeFontSize();
  }

  public static UIFont getSystemFont() {
    return FontUtils.getSystemFont();
  }
}
