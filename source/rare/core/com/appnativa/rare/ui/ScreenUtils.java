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
import com.appnativa.rare.ui.UIScreen.ScreenSize;
import com.appnativa.util.SNumber;

import com.appnativa.jgoodies.forms.layout.ConstantSize;
import com.appnativa.jgoodies.forms.layout.Sizes;

public class ScreenUtils {
  public static int      PLATFORM_PIXELS_1  = 1;
  public static int      PLATFORM_PIXELS_10 = 10;
  public static int      PLATFORM_PIXELS_16 = 16;
  public static int      PLATFORM_PIXELS_2  = 2;
  public static int      PLATFORM_PIXELS_3  = 3;
  public static int      PLATFORM_PIXELS_4  = 4;
  public static int      PLATFORM_PIXELS_5  = 5;
  public static int      PLATFORM_PIXELS_6  = 6;
  public static int      PLATFORM_PIXELS_7  = 7;
  public static int      PLATFORM_PIXELS_8  = 8;
  public static int      PLATFORM_PIXELS_9  = 9;
  public static float    screenDpi96Factor  = 1.333f;
  public static float    xScreenDpi         = 72;
  public static float    yScreenDpi         = 72;
  private static float   density            = 1;
  private static float   fontFactor         = 1f;
  private static int     lineHeight         = -1;
  private static float   pixelMultiplier    = 1;
  static ScreenSize      screenSize         = ScreenSize.small;
  private static float   iconDensity;
  private static UIFont  lineHeightFont;
  private static String  screenSizeName;
  private static boolean screenSizeSet;
  private static boolean sizeValuesSet;
  private static int     xsmallSize;
  private static int     mediumSize;
  private static int     smallSize;

  public static enum Unit {
    PIXELS, CENTIMETER, MILLIMETER, INCH, PERCENT, CHARACTERS, POINT, PICA, LINES, DIALOG, RARE_PIXELS
  }

  private ScreenUtils() {}

  public static UIDimension adjustForLineHeight(iPlatformComponent c, UIDimension d) {
    int h = lineHeight(c);

    if (h > d.height) {
      d.height = h;
    }

    return d;
  }

  public static UIDimension adjustForLineHeightEx(iPlatformComponent c, UIDimension d) {
    int h = lineHeightEx(c);

    if (h > d.height) {
      d.height = h;
    }

    return d;
  }

  public static int calculateLineHeight() {
    lineHeightFont = null;
    lineHeight     = lineHeight(null);
    lineHeightFont = FontUtils.getDefaultFont();

    return lineHeight;
  }

  public static ScreenSize calculateScreenSize(int xsmall, int small, int medium) {
    UIDimension d    = getSize();
    float       m    = Math.max(d.width, d.height) * fontFactor;
    ScreenSize  size = null;
    String      s    = System.getProperty("Rare.screenSize");

    if ((s != null) && Platform.isDebugEnabled()) {
      try {
        size = ScreenSize.valueOf(s);
      } catch(Exception ignore) {}
    }

    smallSize     = small;
    mediumSize    = medium;
    xsmallSize    = xsmall;
    sizeValuesSet = true;

    if (size == null) {
      if (m <= xsmall) {
        size = ScreenSize.xsmall;
      } else if (m <= small) {
        size = ScreenSize.small;
      } else {
        if ((medium < small) || (m > medium)) {
          size = ScreenSize.large;
        } else {
          size = ScreenSize.medium;
        }
      }
    }

    return size;
  }

  public static void centerOnScreenAndSize(iWindow win, float x, float y, float w, float h) {
    int screen = ScreenUtils.getScreen(win.getComponent());

    if (screen < 0) {
      screen = 0;
    }

    UIRectangle screenSize = ScreenUtils.getUsableScreenBounds(screen);

    if ((h == -1) || (w == -1)) {
      win.pack();

      UIDimension d = win.getComponent().getPreferredSize();

      if (h == -1) {
        h = d.height;
      }

      if (w == -1) {
        w = d.width;
      }
    }

    if (w < 50) {
      w = (w < -1)
          ? screenSize.width + w
          : 50;
    } else if (w + 10 > (screenSize.width)) {
      w = screenSize.width - 10;
    }

    if (h < 50) {
      h = (h < -1)
          ? screenSize.height + h
          : 50;
    } else if (h + 10 > (screenSize.height)) {
      h = screenSize.height - 20;
    }

    if (x < 0) {
      x = (screenSize.width - w) / 2 + screenSize.x;
    }

    if (y < 0) {
      y = (screenSize.height - h) / 2 + screenSize.y;
    }

    if (y < screenSize.y) {
      y = screenSize.y;
    }

    if (x < screenSize.x) {
      x = screenSize.x;
    }

    win.setBounds(x, y, w, h);
  }

  public static void centerOnScreen(iWindow win) {
    int screen = ScreenUtils.getScreen(win.getComponent());

    if (screen < 0) {
      screen = 0;
    }

    UIRectangle screenSize = ScreenUtils.getUsableScreenBounds(screen);
    float       w          = win.getWidth();
    float       h          = win.getHeight();

    if (w < 50) {
      w = (w < -1)
          ? screenSize.width + w
          : 50;
    } else if (w + 10 > (screenSize.width)) {
      w = screenSize.width - 10;
    }

    if (h < 50) {
      h = (h < -1)
          ? screenSize.height + h
          : 50;
    } else if (h + 10 > (screenSize.height)) {
      h = screenSize.height - 20;
    }

    float x = (screenSize.width - w) / 2 + screenSize.x;
    float y = (screenSize.height - h) / 2 + screenSize.y;

    if (y < screenSize.y) {
      y = screenSize.y;
    }

    if (x < screenSize.x) {
      x = screenSize.x;
    }

    win.setBounds(x, y, w, h);
  }

  public static void centerOnWindow(iWindow win, iWindow parent) {
    UIRectangle bounds = parent.getBounds();
    float       w      = win.getWidth();
    float       h      = win.getHeight();
    int         screen = ScreenUtils.getScreen(win.getComponent());

    if (screen < 0) {
      screen = 0;
    }

    UIRectangle screenSize = ScreenUtils.getUsableScreenBounds(screen);

    if (w < 50) {
      w = (w < -1)
          ? screenSize.width + w
          : 50;
    } else if (w + 10 > (screenSize.width)) {
      w = screenSize.width - 10;
    }

    if (h < 50) {
      h = (h < -1)
          ? screenSize.height + h
          : 50;
    } else if (h + 10 > (screenSize.height)) {
      h = screenSize.height - 20;
    }

    float x = (UIScreen.snapToSize(bounds.width) - w) / 2 + UIScreen.snapToPosition(bounds.x);
    float y = (UIScreen.snapToSize(bounds.height) - h) / 2 + UIScreen.snapToPosition(bounds.y);

    if (y < screenSize.y) {
      y = screenSize.y;
    }

    if (x < screenSize.x) {
      x = screenSize.x;
    }

    win.setBounds(x, y, w, h);
  }

  public static int platformPixels(float pixels) {
    return (int) Math.ceil(pixelMultiplier * pixels);
  }

  public static float platformPixelsf(float pixels) {
    return pixelMultiplier * pixels;
  }

  public static float fromPlatformPixels(float size, Unit unit) {
    return fromPlatformPixels(size, unit, true);
  }

  public static float fromPlatformPixels(float size, Unit unit, boolean width) {
    if ((unit == null) || (unit == Unit.RARE_PIXELS)) {
      return size / pixelMultiplier;
    }

    if (unit == Unit.PIXELS) {
      return size;
    }

    size /= 72;
    size *= pixelMultiplier;

    switch(unit) {
      case CENTIMETER :
        size /= 2.54;

        break;

      case MILLIMETER :
        size /= 2.54;
        size /= 10;

        break;

      case INCH :
        break;

      case POINT :
        size *= 72;

        break;

      case PICA :
        size *= 6;

        break;

      case DIALOG :
        size *= pixelMultiplier;

        break;

      default :
        break;
    }

    return size;
  }

  public static void initilize(float sdensity, float pmultiplier, float dpi, float xdpi, float ydpi, float fontfactor) {
    xScreenDpi        = xdpi;
    yScreenDpi        = ydpi;
    pixelMultiplier   = pmultiplier;
    density           = sdensity;
    lineHeightFont    = null;
    lineHeight        = -1;
    fontFactor        = fontfactor;
    screenDpi96Factor = 96f / dpi;
    calculateScreenSize();
    PLATFORM_PIXELS_1           = (int) Math.ceil(pixelMultiplier * 1);
    PLATFORM_PIXELS_2           = (int) Math.ceil(pixelMultiplier * 2);
    PLATFORM_PIXELS_3           = (int) Math.ceil(pixelMultiplier * 3);
    PLATFORM_PIXELS_4           = (int) Math.ceil(pixelMultiplier * 4);
    PLATFORM_PIXELS_5           = (int) Math.ceil(pixelMultiplier * 5);
    PLATFORM_PIXELS_6           = (int) Math.ceil(pixelMultiplier * 6);
    PLATFORM_PIXELS_7           = (int) Math.ceil(pixelMultiplier * 7);
    PLATFORM_PIXELS_8           = (int) Math.ceil(pixelMultiplier * 8);
    PLATFORM_PIXELS_9           = (int) Math.ceil(pixelMultiplier * 9);
    PLATFORM_PIXELS_10          = (int) Math.ceil(pixelMultiplier * 10);
    PLATFORM_PIXELS_16          = (int) Math.ceil(pixelMultiplier * 16);
    UIScreen.PLATFORM_PIXELS_1  = PLATFORM_PIXELS_1;
    UIScreen.PLATFORM_PIXELS_2  = PLATFORM_PIXELS_2;
    UIScreen.PLATFORM_PIXELS_3  = PLATFORM_PIXELS_3;
    UIScreen.PLATFORM_PIXELS_4  = PLATFORM_PIXELS_4;
    UIScreen.PLATFORM_PIXELS_5  = PLATFORM_PIXELS_5;
    UIScreen.PLATFORM_PIXELS_6  = PLATFORM_PIXELS_6;
    UIScreen.PLATFORM_PIXELS_7  = PLATFORM_PIXELS_7;
    UIScreen.PLATFORM_PIXELS_8  = PLATFORM_PIXELS_8;
    UIScreen.PLATFORM_PIXELS_9  = PLATFORM_PIXELS_9;
    UIScreen.PLATFORM_PIXELS_10 = PLATFORM_PIXELS_10;
    UIScreen.PLATFORM_PIXELS_16 = PLATFORM_PIXELS_16;
    ConstantSize.setPixelMultiplier(pmultiplier);
  }

  public static int lineHeight(iPlatformComponent c) {
    UIFont f = (c == null)
               ? null
               : c.getFont();

    if (f == null) {
      f = FontUtils.getDefaultFont();
    }

    if ((f == lineHeightFont) && (lineHeight > 0)) {
      return lineHeight;
    }

    float h = PlatformHelper.getFontHeight(f, true);

    return (int) Math.ceil(h + (.25 * pixelMultiplier));
  }

  public static int lineHeightEx(iPlatformComponent c) {
    UIFont f = (c == null)
               ? null
               : c.getFont();

    if (f == null) {
      f = FontUtils.getDefaultFont();
    }

    return (int) PlatformHelper.getFontHeight(f, true);
  }

  public static float lineHeightEx(iPlatformComponent c, UIFont f) {
    return (int) PlatformHelper.getFontHeight(f, false);
  }

  public static int toPlatformPixelHeight(String value, iPlatformComponent c, float heightForPercent) {
    if ((value == null) || (value.equals("-1"))) {
      return -1;
    }

    float h    = SNumber.floatValue(value);
    Unit  unit = getUnit(value);

    return toPlatformPixelHeight(h, unit, c, heightForPercent, false);
  }

  public static int toPlatformPixelHeight(String value, iPlatformComponent c, float heightForPercent,
          boolean charAdjust) {
    if ((value == null) || (value.equals("-1"))) {
      return -1;
    }

    float h    = SNumber.floatValue(value);
    Unit  unit = getUnit(value);

    return toPlatformPixelHeight(h, unit, c, heightForPercent, charAdjust);
  }

  public static int toPlatformPixelHeight(float h, Unit unit, iPlatformComponent c, float heightForPercent,
          boolean charAdjust) {
    if ((unit == null) || (unit == Unit.RARE_PIXELS)) {
      return (int) Math.ceil(h * pixelMultiplier);
    }

    if (unit == Unit.PIXELS) {
      return (int) Math.ceil(h);
    }

    final boolean neg = h < 0;

    if (neg) {
      h *= -1;
    }

    switch(unit) {
      case PERCENT :
        if (heightForPercent == -1) {
          heightForPercent = Platform.getAppContext().getWindowManager().getMainWindow().getHeight();
        }

        if (heightForPercent > 0) {
          h = heightForPercent * (h / 100);
        }

        break;

      case LINES :
        h *= lineHeight(c);

        if (charAdjust) {
          h += getCharWidthPadding();
        }

        break;

      case CHARACTERS : {
        UIFont f = (c == null)
                   ? null
                   : c.getFont();

        if (f == null) {
          f = FontUtils.getDefaultFont();
        }

        h *= FontUtils.getCharacterWidth(f);

        if (charAdjust) {
          h += getCharWidthPadding();
        }

        break;
      }

      case DIALOG :
        h = Sizes.dialogUnitYAsPixel((int) h, c);

        break;

      default :
        h = toPlatformPixels(h, unit, false);

        break;
    }

    if (neg) {
      h *= -1;
    } else {
      h += (.25 * pixelMultiplier);
    }

    return UIScreen.snapToSize(h);
  }

  public static int toPlatformPixelWidth(String value, iPlatformComponent c, float widthForPercent) {
    if (value == null) {
      return -1;
    }

    if ((value == null) || (value.equals("-1"))) {
      return -1;
    }

    float w    = SNumber.floatValue(value);
    Unit  unit = getUnit(value);

    return toPlatformPixelWidth(w, unit, c, widthForPercent, false);
  }

  public static int toPlatformPixelWidth(String value, iPlatformComponent c, float widthForPercent,
          boolean charAdjust) {
    if ((value == null) || (value.equals("-1"))) {
      return -1;
    }

    float w    = SNumber.floatValue(value);
    Unit  unit = getUnit(value);

    return toPlatformPixelWidth(w, unit, c, widthForPercent, charAdjust);
  }

  public static int toPlatformPixelWidth(float w, Unit unit, iPlatformComponent c, float widthForPercent,
          boolean charAdjust) {
    if ((unit == null) || (unit == Unit.RARE_PIXELS)) {
      return (int) Math.ceil(w * pixelMultiplier);
    }

    if (unit == Unit.PIXELS) {
      return (int) Math.ceil(w);
    }

    final boolean neg = w < 0;

    if (neg) {
      w *= -1;
    }

    switch(unit) {
      case PERCENT :
        if (widthForPercent == -1) {
          widthForPercent = Platform.getAppContext().getWindowManager().getMainWindow().getHeight();
        }

        if (widthForPercent > 0) {
          w = widthForPercent * (w / 100);
        }

        break;

      case LINES :
        w *= lineHeight(c);

        if (charAdjust) {
          w += getCharWidthPadding();
        }

        break;

      case CHARACTERS : {
        UIFont f = (c == null)
                   ? null
                   : c.getFont();

        if (f == null) {
          f = FontUtils.getDefaultFont();
        }

        w *= FontUtils.getCharacterWidth(f);

        if (charAdjust) {
          w += getCharWidthPadding();
        }

        break;
      }

      case DIALOG :
        w = Sizes.dialogUnitXAsPixel((int) w, c);

        break;

      default :
        w = toPlatformPixels(w, unit, true);

        break;
    }

    if (neg) {
      w *= -1;
    } else {
      w += .25;
    }

    return UIScreen.snapToSize(w);
  }

  public static int toPlatformPixels(float characters, iPlatformComponent c, boolean charAdjust) {
    return toPlatformPixelWidth(characters, Unit.CHARACTERS, c, (c == null)
            ? 100
            : c.getWidth(), charAdjust);
  }

  public static int toPlatformPixels(float size, Unit unit, boolean width) {
    if ((unit == null) || (unit == Unit.RARE_PIXELS)) {
      return (int) Math.ceil(size * pixelMultiplier);
    }

    if (unit == Unit.PIXELS) {
      return (int) Math.ceil(size);
    }

    size *= pixelMultiplier;
    size *= 72;

    switch(unit) {
      case CENTIMETER :
        size /= 2.54;

        break;

      case MILLIMETER :
        size /= 20.54;

        break;

      case INCH :
        break;

      case POINT :
        size /= 72;

        break;

      case PICA :
        size /= 6;

        break;

      case CHARACTERS :
        size /= 72;
        size *= 9;

        break;

      default :
        size /= 72;

        break;
    }

    return (int) Math.ceil(size);
  }

  public static void updateFontFactor(float value) {
    fontFactor = value;

    if (!screenSizeSet) {
      calculateScreenSize();
    }
  }

  public static void setOrientation(Object orientation) {
    PlatformHelper.setScreenOrientation(orientation);
  }

  public static void setScreenSize(ScreenSize size) {
    screenSize     = size;
    screenSizeName = screenSize.name();
    screenSizeSet  = true;
  }

  public static UIRectangle getBounds() {
    return PlatformHelper.getScreenBounds();
  }

  public static UIRectangle getBounds(int screen) {
    return PlatformHelper.getScreenBounds(screen);
  }

  public static float getCharWidthPadding() {
    return PLATFORM_PIXELS_6;
  }

  public static float getCssPixelSize(float pxsize) {
    return pxsize * (FontUtils.getDefaultFontSize() / FontUtils.getSystemFont().getSize());
  }

  public static float getDefaultIconDensity() {
    if (iconDensity == 0) {
      String s = Platform.getUIDefaults().getString("Rare.defaultIconDensity");

      do {
        if (s == null) {
          break;
        }

        iconDensity = SNumber.floatValue(s);

        if (iconDensity > 0) {
          break;
        }

        if ("xhdpi".equals(s)) {
          iconDensity = 2;

          break;
        }

        if ("hdpi".equals(s)) {
          iconDensity = 1.5f;

          break;
        }
      } while(false);

      if (iconDensity < 1) {
        iconDensity = density;
      }
    }

    return iconDensity;
  }

  public static float getDensity() {
    return density;
  }

  public static String getDensityName() {
    return PlatformHelper.getScreenDensityName();
  }

  public static int getHeight() {
    return PlatformHelper.getScreenHeight();
  }

  public static Object getOrientation() {
    return PlatformHelper.getScreenOrientation();
  }

  public static float getPixelMultiplier() {
    return pixelMultiplier;
  }

  public static UIFont getPrinterFont(UIFont f) {
    return f.deriveFontSize(getPrinterScaleFactor() * f.getSize());
  }

  public static float getPrinterScaleFactor() {
    return 72 / xScreenDpi;
  }

  public static ScreenSize getRelativeScreenSize() {
    return screenSize;
  }

  public static String getRelativeScreenSizeName() {
    return screenSizeName;
  }

  public static int getRotation() {
    return PlatformHelper.getScreenRotation();
  }

  public static int getRotation(Object orientation) {
    return PlatformHelper.getScreenRotation(orientation);
  }

  public static int getRotationForConfiguration(Object configuration) {
    if (configuration == null) {
      return PlatformHelper.getScreenRotation();
    }

    return PlatformHelper.getScreenRotation(configuration);
  }

  public static int getScreen(iPlatformComponent c) {
    return PlatformHelper.getScreen(c);
  }

  public static int getScreenCount() {
    return PlatformHelper.getScreenCount();
  }

  public static float getScreenDPI() {
    return xScreenDpi;
  }

  public static float getScreenFontSize(float javasize) {
    return ((javasize) * (xScreenDpi / 72));
  }

  public static int getScreenHeight(iPlatformComponent c) {
    return PlatformHelper.getScreenHeight(getScreen(c));
  }

  public static UIDimension getScreenSize() {
    return PlatformHelper.getScreenSize();
  }

  public static UIDimension getScreenSize(int screen) {
    return PlatformHelper.getScreenSize(screen);
  }

  public static UIDimension getScreenSize(iPlatformComponent c) {
    return PlatformHelper.getScreenSize(getScreen(c));
  }

  public static int getScreenWidth(iPlatformComponent c) {
    return PlatformHelper.getScreenWidth(getScreen(c));
  }

  public static UIDimension getSize() {
    return getScreenSize();
  }

  public static UIDimension getSize(String width, String height, iPlatformComponent comp) {
    return new UIDimension(toPlatformPixelWidth(width, comp, -1), toPlatformPixelHeight(width, comp, -1));
  }

  public static UIDimension getSizeForConfiguration(Object configuration) {
    if (configuration == null) {
      return PlatformHelper.getScreenSize();
    }

    return PlatformHelper.getScreenSizeForConfiguration(configuration);
  }

  public static Unit getUnit(String s) {
    int len = (s == null)
              ? 0
              : s.length();

    return getUnit(s, len);
  }

  public static Unit getUnit(String s, int len) {
    Unit unit = Unit.RARE_PIXELS;

    do {
      if (len == 0) {
        break;
      }

      char lc = s.charAt(len - 1);

      if (lc == '%') {
        unit = Unit.PERCENT;

        break;
      }

      if (len == 1) {
        break;
      }

      switch(s.charAt(len - 2)) {
        case 'c' :
          if (lc == 'h') {
            unit = Unit.CHARACTERS;
          } else if (lc == 'm') {
            unit = Unit.CENTIMETER;
          }

          break;

        case 'i' :
          if (lc == 'n') {
            unit = Unit.INCH;
          }

          break;

        case 'p' :
          if (lc == 'x') {
            if ((len > 2) && (s.charAt(len - 3) == 'd')) {
              unit = Unit.RARE_PIXELS;
            } else {
              unit = Unit.PIXELS;
            }
          } else if (lc == 't') {
            unit = Unit.POINT;
          } else if (lc == 'c') {
            unit = Unit.PICA;
          }

          break;

        case 'l' :
          if (lc == 'n') {
            unit = Unit.LINES;
          } else if ((lc == 'u') && (len > 2) && (s.charAt(len - 3) == 'd')) {
            unit = Unit.DIALOG;
          }

          break;

        default :
          break;
      }
    } while(false);

    return unit;
  }

  public static Unit getUnit(String s, Unit def) {
    int  len = (s == null)
               ? 0
               : s.length();
    Unit u   = getUnit(s, len);

    return (u == null)
           ? def
           : u;
  }

  public static UIDimension getUsableScreenSize() {
    return PlatformHelper.getUsableScreenBounds().getSize();
  }

  public static UIDimension getUsableScreenSize(iPlatformComponent c) {
    return PlatformHelper.getUsableScreenBounds(c).getSize();
  }

  public static UIRectangle getUsableScreenBounds(int screen) {
    return PlatformHelper.getUsableScreenBounds(screen);
  }

  public static UIRectangle getUsableScreenBounds(iPlatformComponent c) {
    return PlatformHelper.getUsableScreenBounds(c);
  }

  public static int getWidth() {
    return PlatformHelper.getScreenWidth();
  }

  public static boolean isExtraSmallScreen() {
    return screenSize == ScreenSize.xsmall;
  }

  public static boolean isHighDensity() {
    return PlatformHelper.isHighDensity();
  }

  public static boolean isLandscapeOrientation(Object orientation) {
    return PlatformHelper.isLandscapeOrientation(orientation);
  }

  public static boolean isLargeScreen() {
    return screenSize == ScreenSize.large;
  }

  public static boolean isLowDensity() {
    return PlatformHelper.isLowDensity();
  }

  public static boolean isMediumDensity() {
    return PlatformHelper.isMediumDensity();
  }

  public static boolean isMediumScreen() {
    return screenSize == ScreenSize.medium;
  }

  public static boolean isSmallScreen() {
    return screenSize == ScreenSize.small;
  }

  public static boolean isWider() {
    return PlatformHelper.getScreenWidth() > PlatformHelper.getScreenHeight();
  }

  public static boolean isWiderForConfiguration(Object configuration) {
    if (configuration == null) {
      return isWider();
    }

    return PlatformHelper.isLandscapeOrientation(configuration);
  }

  static void calculateScreenSize() {
    if (sizeValuesSet) {
      calculateScreenSize(xsmallSize, smallSize, mediumSize);
    } else {
      String s = System.getProperty("Rare.screenSize");

      screenSize = null;

      if (s != null) {
        try {
          screenSize = ScreenSize.valueOf(s);
        } catch(Exception ignore) {}
      }

      if (screenSize == null) {
        UIDimension d = getSize();
        float       m = Math.max(d.width, d.height) * fontFactor;

        if (m < 968) {
          screenSize = ScreenSize.small;
        } else {
          screenSize = ScreenSize.large;
        }
      }

      screenSizeName = screenSize.name();
    }
  }
}
