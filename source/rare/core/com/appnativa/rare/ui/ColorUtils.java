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
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.GridCell;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.PainterHolder;
import com.appnativa.rare.ui.painter.UIBackgroundPainter;
import com.appnativa.rare.ui.painter.UISimpleBackgroundPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iGradientPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSet;
import com.appnativa.util.CharScanner;
import com.appnativa.util.Helper;
import com.appnativa.util.SNumber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class ColorUtils {

  /** whether colors keys should be kept (Default is false) */
  public static Boolean KEEP_COLOR_KEYS;

  /** the UI Defaults key for whether colors keys should be kept */
  public static final String KEEP_COLOR_KEYS_KEY = "Rare.keepColorKeys";

  /** completely transparent color */
  public static final UIColor TRANSPARENT_COLOR = UIColor.TRANSPARENT;

  /** transparent color used as a null value for a color */
  public static final UIColor NULL_COLOR = new UIColor(255, 255, 255, 0);

  /** transparent color used to paint disabled items */
  public static UIColor                         DISABLED_TRANSPARENT_COLOR = new UIColor(0, 0, 0, 128);
  public static iBackgroundPainter              NULL_BGPAINTER;
  static UIColor                                background;
  static UISimpleBackgroundPainter              controlPainter;
  static UIColor                                foreground;
  private static final float[][]                DISTRIBUTIONS    = {
    { 0f, 1f }, { 0f, .5f, 1f }, { 0f, .499f, .5f, 1f }
  };
  private static final double                   FACTOR           = 0.7;
  private static final char[]                   colorTokens      = { ',', '|' };
  private static final float                    oneThird         = 1f / 3f;
  private static PaintBucket                    paintBucket      = new PaintBucket();
  private static final HashMap<String, UIColor> colorMap         = new HashMap<String, UIColor>();
  private static final ThreadLocal<CharScanner> perThreadScanner = new ThreadLocal<CharScanner>() {
    @Override
    protected synchronized CharScanner initialValue() {
      return new CharScanner();
    }
  };
  private static final ThreadLocal<List<UIColor>> perThreadList = new ThreadLocal<List<UIColor>>() {
    @Override
    protected synchronized List<UIColor> initialValue() {
      return new ArrayList<UIColor>(4);
    }
  };
  private static final float       twoThirds    = 2f / 3f;
  static UISimpleBackgroundPainter whitePainter = new UISimpleBackgroundPainter(UIColor.WHITE);
  private static PaintBucket       backgroundBucket;
  private static UIColor           disabledForeground;

  static {
    colorMap.put("transparent", TRANSPARENT_COLOR);
    colorMap.put("null", TRANSPARENT_COLOR);
    colorMap.put("aliceblue", new UIColor(0xfff0f8ff));
    colorMap.put("antiquewhite", new UIColor(0xfffaebd7));
    colorMap.put("aqua", new UIColor(0xff00ffff));
    colorMap.put("aquamarine", new UIColor(0xff7fffd4));
    colorMap.put("azure", new UIColor(0xfff0ffff));
    colorMap.put("beige", new UIColor(0xfff5f5dc));
    colorMap.put("bisque", new UIColor(0xffffe4c4));
    colorMap.put("black", UIColor.BLACK);
    colorMap.put("blanchedalmond", new UIColor(0xffffebcd));
    colorMap.put("blue", new UIColor(0, 0, 255));
    colorMap.put("blueviolet", new UIColor(0xff8a2be2));
    colorMap.put("brown", new UIColor(0xff804000));
    colorMap.put("burlywood", new UIColor(0xffdeb887));
    colorMap.put("cadetblue", new UIColor(0xff5f9ea0));
    colorMap.put("chartreuse", new UIColor(0xff7fff00));
    colorMap.put("chocolate", new UIColor(0xffd2691e));
    colorMap.put("coral", new UIColor(0xffff7f50));
    colorMap.put("cornflowerblue", new UIColor(0xff6495ed));
    colorMap.put("cornsilk", new UIColor(0xfffff8dc));
    colorMap.put("crimson", new UIColor(0xffdc143c));
    colorMap.put("cyan", UIColor.CYAN);
    colorMap.put("darkblue", new UIColor(0xff00008b));
    colorMap.put("darkcyan", new UIColor(0xff008b8b));
    colorMap.put("darkgoldenrod", new UIColor(0xffb8860b));
    colorMap.put("darkgray", new UIColor(0xffa9a9a9));
    colorMap.put("darkgreen", new UIColor(0xff006400));
    colorMap.put("darkkhaki", new UIColor(0xffbdb76b));
    colorMap.put("darkmagenta", new UIColor(0xff8b008b));
    colorMap.put("darkolivegreen", new UIColor(0xff556b2f));
    colorMap.put("darkorange", new UIColor(0xffff8c00));
    colorMap.put("darkorchid", new UIColor(0xff9932cc));
    colorMap.put("darkred", new UIColor(0xff8b0000));
    colorMap.put("darksalmon", new UIColor(0xffe9967a));
    colorMap.put("darkseagreen", new UIColor(0xff8fbc8f));
    colorMap.put("darkslateblue", new UIColor(0xff483d8b));
    colorMap.put("darkslategray", new UIColor(0xff2f4f4f));
    colorMap.put("darkturquoise", new UIColor(0xff00ced1));
    colorMap.put("darkviolet", new UIColor(0xff9400d3));
    colorMap.put("deeppink", new UIColor(0xffff1493));
    colorMap.put("deepskyblue", new UIColor(0xff00bfff));
    colorMap.put("dimgray", new UIColor(0xff696969));
    colorMap.put("dodgerblue", new UIColor(0xff1e90ff));
    colorMap.put("firebrick", new UIColor(0xffb22222));
    colorMap.put("floralwhite", new UIColor(0xfffffaf0));
    colorMap.put("forestgreen", new UIColor(0xff228b22));
    colorMap.put("fuchsia", new UIColor(0xffff00ff));
    colorMap.put("gainsboro", new UIColor(0xffdcdcdc));
    colorMap.put("ghostwhite", new UIColor(0xfff8f8ff));
    colorMap.put("gold", new UIColor(0xffffd700));
    colorMap.put("goldenrod", new UIColor(0xffdaa520));
    colorMap.put("gray", UIColor.GRAY);
    colorMap.put("green", UIColor.GREEN);
    colorMap.put("greenyellow", new UIColor(0xffadff2f));
    colorMap.put("honeydew", new UIColor(0xfff0fff0));
    colorMap.put("hotpink", new UIColor(0xffff69b4));
    colorMap.put("indianred", new UIColor(0xffcd5c5c));
    colorMap.put("indigo", new UIColor(0xff4b0082));
    colorMap.put("ivory", new UIColor(0xfffffff0));
    colorMap.put("khaki", new UIColor(0xfff0e68c));
    colorMap.put("lavender", new UIColor(0xffe6e6fa));
    colorMap.put("lavenderblush", new UIColor(0xfffff0f5));
    colorMap.put("lawngreen", new UIColor(0xff7cfc00));
    colorMap.put("lemonchiffon", new UIColor(0xfffffacd));
    colorMap.put("lightblue", new UIColor(0xffadd8e6));
    colorMap.put("lightcoral", new UIColor(0xfff08080));
    colorMap.put("lightcyan", new UIColor(0xffe0ffff));
    colorMap.put("lightgoldenrodyellow", new UIColor(0xfffafad2));
    colorMap.put("lightgrey", new UIColor(0xffd3d3d3));
    colorMap.put("lightgreen", new UIColor(0xff90ee90));
    colorMap.put("lightpink", new UIColor(0xffffb6c1));
    colorMap.put("lightsalmon", new UIColor(0xffffa07a));
    colorMap.put("lightseagreen", new UIColor(0xff20b2aa));
    colorMap.put("lightskyblue", new UIColor(0xff87cefa));
    colorMap.put("lightslategray", new UIColor(0xff778899));
    colorMap.put("lightsteelblue", new UIColor(0xffb0c4de));
    colorMap.put("lightyellow", new UIColor(0xffffffe0));
    colorMap.put("lime", new UIColor(0xff00ff00));
    colorMap.put("limegreen", new UIColor(0xff32cd32));
    colorMap.put("linen", new UIColor(0xfffaf0e6));
    colorMap.put("magenta", UIColor.MAGENTA);
    colorMap.put("maroon", new UIColor(0xff800000));
    colorMap.put("mediumaquamarine", new UIColor(0xff66cdaa));
    colorMap.put("mediumblue", new UIColor(0xff0000cd));
    colorMap.put("mediumorchid", new UIColor(0xffba55d3));
    colorMap.put("mediumpurple", new UIColor(0xff9370d8));
    colorMap.put("mediumseagreen", new UIColor(0xff3cb371));
    colorMap.put("mediumslateblue", new UIColor(0xff7b68ee));
    colorMap.put("mediumspringgreen", new UIColor(0xff00fa9a));
    colorMap.put("mediumturquoise", new UIColor(0xff48d1cc));
    colorMap.put("mediumvioletred", new UIColor(0xffc71585));
    colorMap.put("midnightblue", new UIColor(0xff191970));
    colorMap.put("mintcream", new UIColor(0xfff5fffa));
    colorMap.put("mistyrose", new UIColor(0xffffe4e1));
    colorMap.put("moccasin", new UIColor(0xffffe4b5));
    colorMap.put("navajowhite", new UIColor(0xffffdead));
    colorMap.put("navy", new UIColor(0xff000080));
    colorMap.put("oldlace", new UIColor(0xfffdf5e6));
    colorMap.put("olive", new UIColor(0xff808000));
    colorMap.put("olivedrab", new UIColor(0xff6b8e23));
    colorMap.put("orange", UIColor.ORANGE);
    colorMap.put("orangered", new UIColor(0xffff4500));
    colorMap.put("orchid", new UIColor(0xffda70d6));
    colorMap.put("palegoldenrod", new UIColor(0xffeee8aa));
    colorMap.put("palegreen", new UIColor(0xff98fb98));
    colorMap.put("paleturquoise", new UIColor(0xffafeeee));
    colorMap.put("palevioletred", new UIColor(0xffd87093));
    colorMap.put("papayawhip", new UIColor(0xffffefd5));
    colorMap.put("peachpuff", new UIColor(0xffffdab9));
    colorMap.put("peru", new UIColor(0xffcd853f));
    colorMap.put("pink", UIColor.PINK);
    colorMap.put("plum", new UIColor(0xffdda0dd));
    colorMap.put("powderblue", new UIColor(0xffb0e0e6));
    colorMap.put("purple", new UIColor(0xff800080));
    colorMap.put("red", UIColor.RED);
    colorMap.put("rosybrown", new UIColor(0xffbc8f8f));
    colorMap.put("royalblue", new UIColor(0xff4169e1));
    colorMap.put("saddlebrown", new UIColor(0xff8b4513));
    colorMap.put("salmon", new UIColor(0xfffa8072));
    colorMap.put("sandybrown", new UIColor(0xfff4a460));
    colorMap.put("seagreen", new UIColor(0xff2e8b57));
    colorMap.put("seashell", new UIColor(0xfffff5ee));
    colorMap.put("sienna", new UIColor(0xffa0522d));
    colorMap.put("silver", new UIColor(0xffc0c0c0));
    colorMap.put("skyblue", new UIColor(0xff87ceeb));
    colorMap.put("slateblue", new UIColor(0xff6a5acd));
    colorMap.put("slategray", new UIColor(0xff708090));
    colorMap.put("snow", new UIColor(0xfffffafa));
    colorMap.put("springgreen", new UIColor(0xff00ff7f));
    colorMap.put("steelblue", new UIColor(0xff4682b4));
    colorMap.put("tan", new UIColor(0xffd2b48c));
    colorMap.put("teal", new UIColor(0xff008080));
    colorMap.put("thistle", new UIColor(0xffd8bfd8));
    colorMap.put("tomato", new UIColor(0xffff6347));
    colorMap.put("turquoise", new UIColor(0xff40e0d0));
    colorMap.put("violet", new UIColor(0xffee82ee));
    colorMap.put("wheat", new UIColor(0xfff5deb3));
    colorMap.put("white", UIColor.WHITE);
    colorMap.put("whitesmoke", new UIColor(0xfff5f5f5));
    colorMap.put("yellow", UIColor.YELLOW);
    colorMap.put("yellowgreen", new UIColor(0xff9acd32));
  }

  public static enum Shade {
    DARKER, DARKER_DARKER, BRIGHTER, BRIGHTER_BRIGHTER, LUM_ADJUSTMENT, DYN_LUM_ADJUSTMENT, ALPHA, UIMANAGER, BGPAINT,
    COLOR_LIST, DRAWABLE_LIST, DRAWABLE
  }

  public static int HSVToColor(float[] hsv) {
    return HSVToColor(255, hsv);
  }

  public static int HSVToColor(int alpha, float[] hsv) {
    /*
     * Copyright (C) 2006 The Android Open Source Project
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *      http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */
    float h = hsv[0];
    float s = hsv[1];
    float b = hsv[2];

    if (h < 0) {
      h = 0;
    }

    if (s < 0) {
      s = 0;
    }

    if (b < 0) {
      b = 0;
    }

    if (h > 1) {
      h = 1;
    }

    if (s > 1) {
      s = 1;
    }

    if (b > 1) {
      b = 1;
    }

    float       red   = 0.0f;
    float       green = 0.0f;
    float       blue  = 0.0f;
    final float hf    = (h - (int) h) * 6.0f;
    final int   ihf   = (int) hf;
    final float f     = hf - ihf;
    final float pv    = b * (1.0f - s);
    final float qv    = b * (1.0f - s * f);
    final float tv    = b * (1.0f - s * (1.0f - f));

    switch(ihf) {
      case 0 :    // Red is the dominant color
        red   = b;
        green = tv;
        blue  = pv;

        break;

      case 1 :    // Green is the dominant color
        red   = qv;
        green = b;
        blue  = pv;

        break;

      case 2 :
        red   = pv;
        green = b;
        blue  = tv;

        break;

      case 3 :    // Blue is the dominant color
        red   = pv;
        green = qv;
        blue  = b;

        break;

      case 4 :
        red   = tv;
        green = pv;
        blue  = b;

        break;

      case 5 :    // Red is the dominant color
        red   = b;
        green = pv;
        blue  = qv;

        break;
    }

    return ((alpha & 0xff) << 24) | (((int) (red * 255.0f)) << 16) | (((int) (green * 255.0f)) << 8)
           | ((int) (blue * 255.0f));
  }

  /**
   * Adds a color to the color map
   *
   * @param name
   *          the name of the color
   * @param c
   *          the color to add
   */
  public static void addMappedColor(String name, UIColor c) {
    colorMap.put(name, c);
  }

  /**
   * Adjusts the luminance of a color
   *
   * @param color
   *          the color
   * @param lum
   *          the luminance
   *
   * @return the new color
   */
  public static int adjustLuminance(int color, int lum) {
    // formula's from easyrgb.com
    float var_R   = getRed(color) / 255f;
    float var_G   = getGreen(color) / 255f;
    float var_B   = getBlue(color) / 255f;
    float var_Min = Math.min(Math.min(var_R, var_G), var_B);    // Min. value of
    // RGB
    float var_Max = Math.max(Math.max(var_R, var_G), var_B);    // Max. value of
    // RGB
    float del_Max = var_Max - var_Min;    // Delta RGB value
    float H       = 0;
    float S;
    float L;

    L = (var_Max + var_Min) / 2;

    if (del_Max == 0) {
      H = 0;
      S = 0;
    } else {
      if (L < 0.5) {
        S = del_Max / (var_Max + var_Min);
      } else {
        S = del_Max / (2 - var_Max - var_Min);
      }

      float del_R = (((var_Max - var_R) / 6f) + (del_Max / 2f)) / del_Max;
      float del_G = (((var_Max - var_G) / 6f) + (del_Max / 2f)) / del_Max;
      float del_B = (((var_Max - var_B) / 6f) + (del_Max / 2f)) / del_Max;

      if (SNumber.isEqual(var_R, var_Max)) {
        H = del_B - del_G;
      } else if (SNumber.isEqual(var_G, var_Max)) {
        H = oneThird + del_R - del_B;
      } else if (SNumber.isEqual(var_B, var_Max)) {
        H = twoThirds + del_G - del_R;
      }

      if (H < 0) {
        H += 1;
      }

      if (H > 1) {
        H -= 1;
      }
    }

    // convert back to RGB
    float R;
    float G;
    float B;

    lum = (int) ((L * 255) + lum);

    if (lum < 0) {
      lum = 0;
    } else if (lum > 255) {
      lum = 255;
    }

    L = lum / 255f;

    if (S == 0) {
      R = L * 255f;
      G = L * 255f;
      B = L * 255f;
    } else {
      float var_2 = 0;
      float var_1 = 0;

      if (L < 0.5f) {
        var_2 = L * (1f + S);
      } else {
        var_2 = (L + S) - (S * L);
      }

      var_1 = 2 * L - var_2;
      R     = 255 * Hue_2_RGB(var_1, var_2, H + oneThird);
      G     = 255 * Hue_2_RGB(var_1, var_2, H);
      B     = 255 * Hue_2_RGB(var_1, var_2, H - oneThird);
    }

    return getARGB((int) R, (int) G, (int) B, getAlpha(color));
  }

  /**
   * Creates a new <code>UIColor</code> that is a brighter version of the
   * specified color. Alpha values are preserved.
   *
   * @param color
   *          the color
   * @return the new color
   */
  public static int brighter(int color) {
    /*
     * From 2D group: 1. black.brighter() should return grey 2. applying
     * brighter to blue will always return blue, brighter 3. non pure color (non
     * zero rgb) will eventually return white
     */
    int r = getRed(color);
    int g = getGreen(color);
    int b = getBlue(color);
    int a = getAlpha(color);
    int i = (int) (1.0 / (1.0 - FACTOR));

    if ((r == 0) && (g == 0) && (b == 0)) {
      return getARGB(64, 64, 64, a);
    }

    if ((r > 0) && (r < i)) {
      r = i;
    }

    if ((g > 0) && (g < i)) {
      g = i;
    }

    if ((b > 0) && (b < i)) {
      b = i;
    }

    return getARGB(Math.min((int) (r / FACTOR), 255), Math.min((int) (g / FACTOR), 255),
                   Math.min((int) (b / FACTOR), 255), a);
  }

  /**
   * Returns the brightness component of a color int.
   *
   * @return A value between 0.0f and 1.0f
   *
   *         Sourced from Android
   */
  public static float brightness(int color) {
    int r = (color >> 16) & 0xFF;
    int g = (color >> 8) & 0xFF;
    int b = color & 0xFF;
    int V = Math.max(b, Math.max(r, g));

    return (V / 255.f);
  }

  public static PaintBucket configure(iWidget context, GridCell gc, PaintBucket pb) {
    if (gc == null) {
      return pb;
    }

    if (pb == null) {
      pb = new PaintBucket();
    } else {
      pb.empty();
    }

    configureBackgroundPainter(pb, gc.bgColor.getValue(), gc.bgColor.spot_getAttributesEx(), true);

    String s = gc.spot_getAttribute("foreground");

    if (s != null) {
      pb.setForegroundColor(getColor(s));
    }

    s = gc.spot_getAttribute("font");

    if (s != null) {
      pb.setFont(UIFontHelper.parseFont(context, s));
    }

    if (gc.bgImageURL.getValue() != null) {
      pb.setImagePainter(Utils.configureImage(context, null, gc.bgImageURL, false));
    }

    SPOTSet set = gc.getBorders();

    if (set != null) {
      pb.setBorder(BorderUtils.createBorder(context, set, null));
    }

    return pb;
  }

  public static void configureBackgroundPainter(iPlatformComponent comp, SPOTPrintableString bgColor) {
    PaintBucket pb = paintBucket;

    pb.empty();

    if (configureBackgroundPainter(pb, bgColor.getValue(), bgColor.spot_getAttributesEx(), false)) {
      pb.install(comp);
    }
  }

  public static void configureBackgroundPainter(iWidget context, SPOTPrintableString bgColor) {
    PaintBucket pb = paintBucket;

    pb.empty();

    if (configureBackgroundPainter(pb, bgColor.getValue(), bgColor.spot_getAttributesEx(), false)) {
      pb.install(context.getContainerComponent());
    }
  }

  public static void configureBackgroundPainter(PaintBucket pb, SPOTPrintableString bgColor) {
    configureBackgroundPainter(pb, bgColor.getValue(), bgColor.spot_getAttributesEx(), false);
  }

  /**
   * Configures a background painter
   *
   * @param context
   *          the context
   * @param color
   *          the color string
   * @param attrs
   *          the configuration attributes
   * @param alwaysUsePainter
   *          true to use a background painter for single color backgrounds;
   *          false otherwise
   */
  public static void configureBackgroundPainter(iWidget context, String color, Map<String, String> attrs,
          boolean alwaysUsePainter) {
    if (context == null) {
      return;
    }

    PaintBucket pb = paintBucket;

    pb.empty();

    if (configureBackgroundPainter(pb, color, attrs, alwaysUsePainter)) {
      pb.install(context.getContainerComponent(), false);
    }
  }

  /**
   * Configures a background painter
   *
   * @param pb
   *          the paint bucket
   * @param color
   *          the color string
   * @param attrs
   *          the configuration attributes
   * @param alwaysUsePainter
   *          true to use a background painter for single color backgrounds;
   *          false otherwise
   */
  public static boolean configureBackgroundPainter(PaintBucket pb, String color, Map<String, String> attrs,
          boolean alwaysUsePainter) {
    if (color == null) {
      return false;
    }

    if (color.equals("null")) {
      pb.setBackgroundPainter(UISimpleBackgroundPainter.NULL_BGPAINTER);

      return true;
    }

    char[]      a       = color.toCharArray();
    int         len     = a.length;
    CharScanner scanner = perThreadScanner.get();

    scanner.reset(a, 0, len, false);
    scanner.unquote(true);

    if ((scanner.indexOf(';') != -1) && (scanner.indexOf('=') != -1)) {
      LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

      CharScanner.parseOptionStringEx(scanner, map, ';', true);

      UIColor c;

      if (color.contains("drawable/")) {
        c = PlatformHelper.getDrawableStateList(map);
      } else {
        c = PlatformHelper.getColorStateList(map);
      }

      pb.setBackgroundColor(c);

      return true;
    }

    scanner.setTokenDelimiters(colorTokens);
    scanner.trim();

    UIColor[] colors       = null;
    float[]   distribution = null;
    int[]     tok;
    UIColor   sc = null;
    UIColor   ec = null;

    tok = scanner.findToken(false, false);

    if (tok != null) {
      tok = scanner.trim(tok);

      if (tok != null) {
        sc = getColor(a, tok[0], tok[1], color);
      }

      if (scanner.foundDelimiter < 1) {
        if (sc != null) {
          if (sc instanceof UIColorShade) {
            UIColorShade cs = (UIColorShade) sc;

            if (cs.getBackgroundPainter() != null) {
              pb.setBackgroundPainter(cs.getBackgroundPainter());

              return true;
            }
          }

          if (alwaysUsePainter) {
            if (UIColor.WHITE.equals(sc)) {
              pb.setBackgroundPainter(whitePainter);
            } else if (sc.equals(background)) {
              pb.setBackgroundPainter(controlPainter);
            } else {
              pb.setBackgroundPainter(new UISimpleBackgroundPainter(sc));
            }
          } else {
            pb.setBackgroundColor(sc);
          }
        }

        return true;
      }
    }

    tok = scanner.findToken(false, false);

    if (tok != null) {
      tok = scanner.trim(tok);

      if (tok != null) {
        ec = getColor(a, tok[0], tok[1], color);
      }
    }

    if (scanner.getLength() > 0) {
      List<UIColor> l = perThreadList.get();

      l.clear();
      l.add(sc);
      l.add(ec);

      while((tok = scanner.findToken(false, false)) != null) {
        tok = scanner.trim(tok);

        if (tok != null) {
          l.add(getColor(a, tok[0], tok[1], color));
        } else {
          l.add(null);
        }
      }

      colors = new UIColor[l.size()];
      colors = l.toArray(colors);
    } else {
      colors = new UIColor[] { sc, ec };
    }

    iGradientPainter.Direction direction = iGradientPainter.Direction.VERTICAL_TOP;
    iGradientPainter.Type      type      = iGradientPainter.Type.LINEAR;
    int                        magnitude = 100;
    int                        clen      = colors.length;
    float                      radius    = 0;

    if (attrs != null) {
      String s = attrs.get("type");

      if (s != null) {
        try {
          scanner.reset(s);
          scanner.unquote(true).toUpperCase();
          type = iGradientPainter.Type.valueOf(scanner.getLeftOver());
        } catch(Exception e) {
          Platform.debugLog(Helper.expandString(Platform.getResourceAsString("Rare.runtime.text.badGradientType"), s));
        }
      }

      s = attrs.get("magnitude");

      if (s != null) {
        magnitude = SNumber.intValue(s);

        if (magnitude < 0) {
          magnitude = 100;
        }
      }

      s = attrs.get("radius");

      if (s != null) {
        radius = SNumber.floatValue(s);

        if (radius < 0) {
          radius = 0;
        }
      }

      s = attrs.get("direction");

      if (s != null) {
        try {
          scanner.reset(s);
          scanner.unquote(true).toUpperCase();
          direction = iGradientPainter.Direction.valueOf(scanner.getLeftOver());
        } catch(Exception e) {
          Platform.debugLog(Helper.expandString(Platform.getResourceAsString("Rare.runtime.text.badGradientDirection"),
                  s));
        }
      }

      s = attrs.get("distribution");

      if ((s != null) && (s.length() > 0)) {
        distribution = new float[clen];
        a            = s.toCharArray();
        scanner.reset(a, 0, a.length, false);
        scanner.trim();

        int i    = 0;
        int plen = 1;

        while(i < a.length) {
          if (a[i++] == ',') {
            plen++;
          }
        }

        if (plen > clen) {
          i = 0;
        } else {
          i = clen - plen;
        }

        while((tok = scanner.findToken(false, false)) != null && (i < clen)) {
          tok = scanner.trim(tok);

          if (tok != null) {
            distribution[i++] = SNumber.floatValue(a, tok[0], tok[1], false);
          }
        }

        scanner.trim();

        if ((i < clen) || (scanner.getLength() > 0)) {
          Platform.debugLog(Platform.getResourceAsString("Rare.runtime.text.badLGDistributionLen"));
        }

        float p = -1f;

        for (i = 0; i < clen; i++) {
          float f = distribution[i];

          if ((f <= p) || (f < 0f) || (f > 1.0f)) {
            if (f == p) {
              distribution[i] = f + .001f;
            }
          }

          p = f;
        }
      }
    }

    if ((distribution == null) && (clen > 1) && (clen < 5)) {
      distribution = DISTRIBUTIONS[clen - 2];
    }

    iGradientPainter gp = new UIBackgroundPainter(type, direction, colors, distribution, magnitude);

    if (radius > 0) {
      gp.setGradientRadius(radius);
    }

    pb.setBackgroundPainter(gp);
    pb.setBackgroundColor(sc);

    return true;
  }

  public static UIColor[] createNamedShades(UIColor[] a) {
    int len = (a == null)
              ? 0
              : a.length;

    if (len == 0) {
      return a;
    }

    Iterator<Entry<String, UIColor>> it = colorMap.entrySet().iterator();

    while(it.hasNext()) {
      Entry<String, UIColor> e  = it.next();
      UIColor                nc = e.getValue();

      for (int i = 0; i < len; i++) {
        UIColor c = a[i];

        if (c.equals(nc)) {
          a[i] = new UIColorShade(c, e.getKey());

          break;
        }
      }
    }

    return a;
  }

  public static int darker(int color) {
    int red   = Math.max((int) (getRed(color) * FACTOR), 0);
    int green = Math.max((int) (getGreen(color) * FACTOR), 0);
    int blue  = Math.max((int) (getBlue(color) * FACTOR), 0);

    return getARGB(red, green, blue, getAlpha(color));
  }

  /**
   * Returns the hue component of a color int.
   *
   * @return A value between 0.0f and 1.0f
   *
   *         Sourced from Android
   */
  public static float hue(int color) {
    int   r    = (color >> 16) & 0xFF;
    int   g    = (color >> 8) & 0xFF;
    int   b    = color & 0xFF;
    int   V    = Math.max(b, Math.max(r, g));
    int   temp = Math.min(b, Math.min(r, g));
    float H;

    if (V == temp) {
      H = 0;
    } else {
      final float vtemp = V - temp;
      final float cr    = (V - r) / vtemp;
      final float cg    = (V - g) / vtemp;
      final float cb    = (V - b) / vtemp;

      if (r == V) {
        H = cb - cg;
      } else if (g == V) {
        H = 2 + cr - cb;
      } else {
        H = 4 + cg - cr;
      }

      H /= 6.f;

      if (H < 0) {
        H++;
      }
    }

    return H;
  }

  /**
   * Returns the saturation component of a color int.
   *
   * @return A value between 0.0f and 1.0f
   *
   *         Sourced from Android
   */
  public static float saturation(int color) {
    int   r    = (color >> 16) & 0xFF;
    int   g    = (color >> 8) & 0xFF;
    int   b    = color & 0xFF;
    int   V    = Math.max(b, Math.max(r, g));
    int   temp = Math.min(b, Math.min(r, g));
    float S;

    if (V == temp) {
      S = 0;
    } else {
      S = (V - temp) / (float) V;
    }

    return S;
  }

  public static void updateColor(String name, Object value) {
    UIColor c  = colorMap.get(name);
    UIColor nc = (value instanceof UIColor)
                 ? (UIColor) value
                 : getColor((String) value);

    Platform.getUIDefaults().put(name, nc);

    if (!(c instanceof UIColorShade)) {
      colorMap.put(name, nc);

      return;
    }

    UIColorShade cs = (UIColorShade) c;

    if (nc instanceof UIColorShade) {
      cs.copyShade(((UIColorShade) nc));
    } else {
      cs.setToColor(nc);
    }
  }

  public static int setAlpha(int argb, int alpha) {
    return ((alpha & 0xFF) << 24) | (argb & 0x00ffffff);
  }

  public static void setBaseColors() {
    foreground     = Platform.getUIDefaults().getColor("Rare.foreground");
    background     = Platform.getUIDefaults().getColor("Rare.background");
    controlPainter = new UISimpleBackgroundPainter(background);
  }

  public static int getARGB(int r, int g, int b, int a) {
    return (a << 24) | (r << 16) | (g << 8) | b;
  }

  public static int getAlpha(int color) {
    return color >>> 24 & 0xFF;
  }

  public static String shadeKeyToString(Shade shade, String colorKey, int lumAdjustment, int alpha) {
    switch(shade) {
      case DARKER :
        return colorKey + "~darker";

      case DARKER_DARKER :
        return colorKey + "~darker_darker";

      case BRIGHTER :
        return colorKey + "~brighter";

      case BRIGHTER_BRIGHTER :
        return colorKey + "~brighter_brighter";

      case LUM_ADJUSTMENT :
        lumAdjustment = Math.round(lumAdjustment * 100f / 255f) % 101;

        if (lumAdjustment > 0) {
          return colorKey + "+" + lumAdjustment;
        }

        return colorKey + lumAdjustment;

      case ALPHA :
        alpha = alpha * 100 / 255;

        return colorKey + "@" + alpha;

      default :
        return colorKey;
    }
  }

  public static UIColor getBackground() {
    if (background == null) {
      background = Platform.getUIDefaults().getColor("Rare.background");
    }

    return background;
  }

  public static PaintBucket getBackgroundBucket() {
    if (backgroundBucket == null) {
      backgroundBucket = new PaintBucket(getBackground());
    }

    return backgroundBucket;
  }

  /**
   * Returns a color object represented by the specified color string
   *
   * @param bgColor
   *          the string representing the color
   *
   * @return a color object represented by the specified color string
   */
  public static UIColor getBackgroundColor(SPOTPrintableString bgColor) {
    PaintBucket pb = paintBucket;

    pb.empty();

    if (configureBackgroundPainter(pb, bgColor.getValue(), bgColor.spot_getAttributesEx(), false)) {
      return pb.getBackgroundShade();
    }

    return null;
  }

  /**
   * Returns the background color for the color configuration string.
   *
   * @param color
   *          the color configuration string.
   *
   * @return a color object represented by the specified color configuration
   *         string.
   */
  public static UIColor getBackgroundColor(String color) {
    if (color == null) {
      return null;
    }

    PaintBucket pb = getPaintBucket(color, paintBucket, true);

    if (pb != null) {
      return pb.getBackgroundShade();
    }

    return null;
  }

  /**
   * Returns the background color for the color configuration string.
   *
   * @param color
   *          the color configuration string.
   * @param alwaysUsePainter
   *          true to use a background painter for single color backgrounds;
   *          false otherwise
   *
   * @return a color object represented by the specified color configuration
   *         string.
   */
  public static UIColor getBackgroundColor(String color, boolean alwaysUsePainter) {
    if (color == null) {
      return null;
    }

    PaintBucket pb = getPaintBucket(color, paintBucket, alwaysUsePainter);

    if (pb != null) {
      return pb.getBackgroundShade();
    }

    return null;
  }

  /**
   * Returns a painter represented by the specified color string
   *
   * @param bgColor
   *          the string representing the color
   *
   * @return a painter or null
   */
  public static iBackgroundPainter getBackgroundPainter(SPOTPrintableString bgColor) {
    PaintBucket pb = paintBucket;

    pb.empty();

    if (configureBackgroundPainter(pb, bgColor.getValue(), bgColor.spot_getAttributesEx(), false)) {
      iBackgroundPainter bp = pb.getBackgroundPainter();

      if ((bp == null) || (pb.getBackgroundColor() != null)) {
        bp = new UISimpleBackgroundPainter(pb.getBackgroundColor());
      }

      return bp;
    }

    return null;
  }

  public static iBackgroundPainter getBackgroundPainter(String color) {
    if (color == null) {
      return null;
    }

    PaintBucket pb = getPaintBucket(color, paintBucket, true);

    return pb.getBackgroundPainter();
  }

  public static int getBlue(int color) {
    return color & 0xFF;
  }

  /**
   * Returns a color object represented by the specified color string
   *
   * @param color
   *          the string representing the color
   *
   * @return a color object represented by the specified color string
   */
  public static UIColor getColor(String color) {
    if (color == null) {
      return null;
    }

    UIColor c = colorMap.get(color.toLowerCase(Locale.US));

    if (c == null) {
      c = Platform.getUIDefaults().getColor(color);
    }

    if (c != null) {
      return c;
    }

    if (color.indexOf('=') != -1) {
      return PlatformHelper.getColorStateList(getStateListMap(color));
    }

    if (color.indexOf(',') != -1) {
      return getBackgroundColor(color, false);
    }

    char[] a = color.toCharArray();

    c = getColor(a, 0, a.length, color);

    if (c == null) {
      if (color.equals("null")) {
        return UIColor.TRANSPARENT;
      }

      c = PlatformHelper.getResourceColor(color);
    }

    if (c == null) {
      c = getBackground();
      Platform.debugLog("Undefined color definition: " + color);
    }

    return c;
  }

  /**
   * Returns a color object represented by the specified color string
   *
   * @param color
   *          the string representing the color
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters to use
   * @param source
   *          the source that represents the she specified characters (can be
   *          null)
   *
   * @return a color object represented by the specified color string
   */
  public static UIColor getColor(char[] color, int pos, int len, String source) {
    if ((color == null) || (len == 0)) {
      return null;
    }

    Shade   shade = null;
    int     nlen  = len + pos;
    char    ch;
    int     i;
    int     inc     = 0;
    int     alpha   = -1;
    boolean rgb     = false;
    boolean dynamic = false;

    for (i = pos; i < nlen; i++) {
      ch = color[i];

      if (ch == '(') {
        pos = i;
        rgb = true;

        continue;
      }

      if (ch == '-') {
        inc = SNumber.intValue(color, i, nlen - i, false);

        break;
      }

      if (ch == '+') {
        inc = SNumber.intValue(color, i + 1, nlen - i - 1, false);

        break;
      }

      if (ch == '^') {
        inc     = SNumber.intValue(color, i + 1, nlen - i - 1, false);
        dynamic = true;

        break;
      }

      if (ch == ',') {
        rgb = true;
      }

      if (ch == '@') {
        alpha = SNumber.intValue(color, i + 1, nlen - i - 1, false);

        break;
      }

      if (ch == '~') {
        try {
          shade = Shade.valueOf(new String(color, i + 1, nlen - i - 1).toUpperCase(Locale.US));
        } catch(Exception e) {
          Platform.ignoreException(null, e);
        }

        break;
      }
    }

    UIColor c = null;

    if ((color[pos] != '#') &&!rgb) {
      String s = source;

      if ((s == null) || (s.length() != len) || (i - pos != len) || (i != 0)) {
        s = new String(color, pos, i - pos);
      }

      c = colorMap.get(s);

      if (c == null) {
        c = Platform.getUIDefaults().getColor(s);
      }

      if (c == null) {
        c = PlatformHelper.getResourceColor(s);
      }
    }

    if (c == null) {
      if (rgb) {
        c = Conversions.colorFromRGBString(color, pos, i - pos);
      } else {
        c = Conversions.colorFromHexString(color, pos, i - pos);
      }
    }

    if ((c != null) && (inc != 0)) {
      inc = Math.round((255f * inc) / 100f) % 256;

      if (dynamic) {
        if (!c.isDarkColor()) {
          inc = -inc;
        }
      }

      c = c.light(inc);
    } else if ((c != null) && (alpha > 0)) {
      alpha = (255 * alpha) / 100;
      c     = c.alpha(alpha);
    } else if ((c != null) && (shade != null)) {
      c = new UIColorShade(c, shade);
    }

    return c;
  }

  /**
   * Creates a an array of color object from a comma separated list of colors
   *
   * @param color
   *          the string representing the color
   * @param min
   *          the minimum number of array elements to create
   *
   * @return the color array
   */
  public static UIColor[] getColors(String color, int min) {
    List<String> list = CharScanner.getTokens(color, ',', true);
    int          len  = list.size();

    min = (min > len)
          ? min
          : len;

    UIColor[] c = new UIColor[min];

    for (int i = 0; i < len; i++) {
      c[i] = getColor(list.get(i));
    }

    return c;
  }

  public static UIColor getControlDkGradient() {
    return Platform.getUIDefaults().getColor("Rare.backgroundDkGradient");
  }

  public static UIColor getControlDkShadow() {
    return Platform.getUIDefaults().getColor("Rare.backgroundDkShadow");
  }

  public static UIColor getControlHighlight() {
    return Platform.getUIDefaults().getColor("Rare.backgroundHighlight");
  }

  public static UIColor getControlLtGradient() {
    return Platform.getUIDefaults().getColor("Rare.backgroundLtGradient");
  }

  public static UIColor getControlLtHighlight() {
    return Platform.getUIDefaults().getColor("Rare.backgroundLtHighlight");
  }

  public static UIColor getControlLtShadow() {
    return Platform.getUIDefaults().getColor("Rare.backgroundLtShadow");
  }

  public static UIColor getControlShadow() {
    return Platform.getUIDefaults().getColor("Rare.backgroundShadow");
  }

  public static UIColor getDisabledForeground() {
    if (disabledForeground == null) {
      disabledForeground = Platform.getUIDefaults().getColor("Rare.disabledForeground");
    }

    return disabledForeground;
  }

  public static UIColor getDisabledVersion(UIColor fg) {
    return fg.alpha(128);
  }

  public static UIColor getForeground() {
    if (foreground == null) {
      foreground = Platform.getUIDefaults().getColor("Rare.foreground");
    }

    return foreground;
  }

  /**
   * Gets a color from the color map
   *
   * @param color
   *          the color to retrieve
   *
   * @return the color or null if the named color does not exist
   */
  public static UIColor getFromColorMap(String color) {
    return colorMap.get(color);
  }

  public static int getGreen(int color) {
    return (color >> 8) & 0xFF;
  }

  public static UIColor getListBackground() {
    return Platform.getUIDefaults().getColor("Rare.List.background");
  }

  public static UIColor getListDisabledForeground() {
    return Platform.getUIDefaults().getColor("Rare.List.disabledForeground");
  }

  public static UIColor getListDividerColor() {
    UIColor c = Platform.getUIDefaults().getColor("Rare.List.dividerLineColor");

    if (c == null) {
      if (c == null) {
        c = Platform.getUIDefaults().getColor("Rare.LineBorder.color");

        if (c == null) {
          c = Platform.getUIDefaults().getColor("Rare.backgroundShadow");
        }
      }
    }

    if (c == null) {
      c = UIColor.GRAY;
    }

    return c;
  }

  public static UIColor getListForeground() {
    return Platform.getUIDefaults().getColor("Rare.List.foreground");
  }

  public static UIColor getNamedColor(String name) {
    if (name == null) {
      return null;
    }

    UIColor c = colorMap.get(name.toLowerCase(Locale.US));

    if (c == null) {
      c = Platform.getUIDefaults().getColor(name);
    }

    return c;
  }

  public static PaintBucket getPaintBucket(SPOTPrintableString color) {
    if ((color == null) || (color.getValue() == null)) {
      return null;
    }

    PaintBucket pb = paintBucket;

    configureBackgroundPainter(pb, color);

    if (pb.isEmpty()) {
      return null;
    }

    return (PaintBucket) pb.clone();
  }

  public static PaintBucket getPaintBucket(String color) {
    if ((color == null) || (color.length() == 0)) {
      return null;
    }

    PaintBucket pb = getPaintBucket(color, paintBucket, false);

    if (pb.isEmpty()) {
      return null;
    }

    return (PaintBucket) pb.clone();
  }

  public static iPlatformPainter getPainter(UIColor c) {
    if (c instanceof UIColorShade) {
      return ((UIColorShade) c).getBackgroundPainter();
    }

    return null;
  }

  public static UIColor getPressedVersion(UIColor fg) {
    return fg.alpha(132);
  }

  public static int getRed(int color) {
    return (color >> 16) & 0xFF;
  }

  /**
   * Returns a color shade for a string representing a simple color state list
   *
   * @param map
   *          the color state map
   *
   * @return a color object represented by the specified color string
   */
  public static UIColorShade getSimpleColorStateList(Map<String, String> map) {
    if (map == null) {
      return null;
    }

    CharScanner sc = perThreadScanner.get();

    return new UIColorShade(SimpleColorStateList.create(sc, map));
  }

  /**
   * Returns a color shade for a string representing a simple color state list
   *
   * @param map
   *          the color state map
   *
   * @return a color object represented by the specified color string
   */
  public static UIColorShade getSimpleDrawableStateList(Map<String, String> map) {
    if (map == null) {
      return null;
    }

    CharScanner                     sc = perThreadScanner.get();
    String                          color;
    Entry<String, String>           e;
    Iterator<Entry<String, String>> it = map.entrySet().iterator();
    PaintBucket                     c;
    int                             a[];
    List<String>                    list = new ArrayList<String>(3);
    PainterHolder                   ph   = new PainterHolder();

    while(it.hasNext()) {
      e     = it.next();
      color = e.getValue();
      c     = ColorUtils.getPaintBucket(color);
      color = e.getKey();
      sc.reset(color);
      sc.toLowerCase();

      if ("normal".equals(color)) {
        ph.normalPainter = c;

        continue;
      }

      list = sc.getTokens(',', true, list);
      a    = new int[list.size()];

      for (int n = 0; n < a.length; n++) {
        String s = list.get(n);

        if (s.equals("disabled") || s.endsWith("not_enabled")) {
          ph.disabledPainter = c;
        } else if (s.equals("selected")) {
          ph.selectedPainter = c;
        } else if (s.equals("selected_disabled") || s.equals("disabled_selected")) {
          ph.disabledSelectedPainter = c;
        } else if (s.equals("rollover")) {
          ph.rolloverPainter = c;
        } else if (s.equals("pressed")) {
          ph.pressedPainter = c;
        }
      }
    }

    UIColor bg = (ph.normalPainter == null)
                 ? null
                 : ph.normalPainter.getBackgroundColor();

    if (bg == null) {
      bg = TRANSPARENT_COLOR;
    }

    return new UIColorShade(bg, ph);
  }

  /**
   * Gets the a standard gradient distribution for the specified number of
   * colors
   *
   * @param colors
   *          the number of colors
   *
   * @return the distributions
   */
  public static float[] getStandardDistrubution(int colors) {
    colors -= 2;

    if ((colors > -1) && (colors < DISTRIBUTIONS.length)) {
      return DISTRIBUTIONS[colors];
    }

    return null;
  }

  public static Map<String, String> getStateListMap(String color) {
    return CharScanner.parseOptionStringEx(color, ';');
  }

  public static UIColor getTableGridColor() {
    return Platform.getUIDefaults().getColor("Rare.Table.gridColor");
  }

  public static UIColor getTextHilight() {
    return Platform.getUIDefaults().getColor("Rare.textHighlight");
  }

  public static UIColor getTextHilightText() {
    return Platform.getUIDefaults().getColor("Rare.textHighlightText");
  }

  /**
   * Tests is the specified gradient distribution is a standard distribution
   *
   * @param distributions
   *          the distributions to test
   * @return true if the specified gradient distribution is a standard
   *         distribution; false otherwise
   */
  public static boolean isStandardDistribution(float[] distributions) {
    for (int i = 0; i < DISTRIBUTIONS.length; i++) {
      if (distributions == DISTRIBUTIONS[i]) {
        return true;
      }
    }

    return false;
  }

  private static float Hue_2_RGB(float v1, float v2, float vH) {
    if (vH < 0f) {
      vH += 1f;
    }

    if (vH > 1f) {
      vH -= 1f;
    }

    if ((6f * vH) < 1f) {
      return v1 + (v2 - v1) * 6f * vH;
    }

    if ((2f * vH) < 1f) {
      return v2;
    }

    if ((3f * vH) < 2f) {
      return v1 + (v2 - v1) * (twoThirds - vH) * 6f;
    }

    return v1;
  }

  private static PaintBucket getPaintBucket(String color, PaintBucket pb, boolean alwaysUsePainter) {
    if (color == null) {
      return null;
    }

    pb.empty();

    Map<String, String> map = null;
    int                 n   = color.indexOf('[');

    if (n != -1) {
      int p = color.indexOf(']');

      if (p > n) {
        String s = color.substring(n + 1, p);

        color = color.substring(0, n);
        map   = CharScanner.parseOptionStringEx(s, ',');
      }
    }

    configureBackgroundPainter(pb, color, map, alwaysUsePainter);

    return pb;
  }
}
