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
import com.appnativa.spot.SDFNode;
import com.appnativa.util.CharArray;
import com.appnativa.util.CharScanner;
import com.appnativa.util.SNumber;

import java.io.Reader;
import java.io.StringReader;

import java.net.URL;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class FontUtils {
  static UIFont                                    defaultFont;
  static UIFont                                    systemFont;
  private static float                             relativeFontSize = 1;
  private static ConcurrentHashMap<String, UIFont> customFonts;
  private static int                               defaultCharWidth;
  private static float                             defaultFontSize;
  private static int                               defaultLineHeight;

  public static void addCustomFont(String name, UIFont font) {
    if (customFonts == null) {
      customFonts = new ConcurrentHashMap<String, UIFont>();
    }

    customFonts.put(name, font);
  }

  public static void addCustomFontNamesToList(List<String> list) {
    if (customFonts != null) {
      list.addAll(customFonts.keySet());
    }
  }

  public static void addCustomFontsToList(List<UIFont> list) {
    if (customFonts != null) {
      list.addAll(customFonts.values());
    }
  }

  /**
   * Loads a new true type font
   *
   * @param name the name for the font
   * @param location the location of the font to load
   * @param type the type of font ( UIFont.TRUETYPE_FONT, or UIFont.TYPE1_FONT)
   */
  public static void loadFont(String name, URL location, String type) {
    PlatformHelper.loadFont(name, location, type);
  }

  @SuppressWarnings("resource")
  public static com.appnativa.rare.spot.Font parseFont(iWidget context, String s) {
    try {
      Reader r;

      if (!s.startsWith("{")) {
        r = new CharArray(s.length() + 2).set('{').append(s).append('}');
      } else {
        r = new StringReader(s);
      }

      SDFNode                      node = SDFNode.parse(r, context.getURLResolver(), null, false);
      com.appnativa.rare.spot.Font f    = new com.appnativa.rare.spot.Font();

      node = node.getFirstDataNode();

      if (node == null) {
        return null;
      }

      f.fromSDF(node);

      return f;
    } catch(Exception e) {
      return null;
    }
  }

  /**
   * Parses a front from a configuration string
   *
   * @param context the context
   * @param base the base font
   * @param s the configuration string
   *
   * @return the newly created font
   */
  public static UIFont parseFont(iWidget context, UIFont base, String s) {
    if (s == null) {
      return null;
    }

    try {
      Reader r;

      if (!s.startsWith("{")) {
        r = new CharArray(s.length() + 2).set('{').append(s).append('}');
      } else {
        r = new StringReader(s);
      }

      SDFNode node = SDFNode.parse(r, context.getURLResolver(), null, false);

      node = node.getFirstDataNode();

      if (node != null) {
        s = node.getNodeValue("style");

        int style = UIFont.PLAIN;

        if (s != null) {
          if (s.equals("bold")) {
            style = UIFont.BOLD;
          } else if (s.equals("italic")) {
            style = UIFont.ITALIC;
          } else if (s.equals("italic_bold")) {
            style = UIFont.ITALIC | UIFont.BOLD;
          }
        }

        boolean monospaced    = "true".equals(node.getNodeValue("monospaced"));
        boolean underlined    = "true".equals(node.getNodeValue("underlined"));
        boolean strikeThrough = "true".equals(node.getNodeValue("strikeThrough"));

        return getFont(base, node.getNodeValue("family"), style, node.getNodeValue("size"), monospaced, underlined,
                       strikeThrough);
      }
    } catch(Exception e) {
      Platform.ignoreException(null, e);
    }

    return null;
  }

  /**
   * Unloads a previous loaded font.
   * The font is removed for the custom font cache
   *
   * @param name the name of the custom font
   */
  public void unloadFont(String name) {
    if (customFonts != null) {
      customFonts.remove(name);
    }
  }

  public static void setDefaultFont(UIFont font) {
    defaultFont     = font;
    defaultFontSize = font.getSize2D();
    Platform.getUIDefaults().put("Rare.font.default", font);
    Platform.getUIDefaults().put("Rare.font.plaintext", getMonospacedFont(font.getSize() - 1));
    defaultCharWidth = 0;
    PlatformHelper.defaultFontUpdated(font);
    defaultLineHeight = ScreenUtils.calculateLineHeight();
  }

  public static void setRelativeFontSize(float size) {
    relativeFontSize = size;

    UIFont f = getDefaultFont();

    size *= defaultFontSize;

    if (!SNumber.isEqual(f.getSize2D(), size)) {
      setDefaultFont(f.deriveFontSize(size));
    }
  }

  public static void setSystemFont(UIFont font) {
    systemFont = font;
  }

  /**
   *  Get the width of the 'w' character for the given font and specified component
   *
   *  @param f the font
   *  @return the width of the 'w' character
   */
  public static int getCharacterWidth(UIFont f) {
    if(f==null) {
      f=defaultFont;
    }
    if (f == defaultFont) {
      if (defaultCharWidth == 0) {
        defaultCharWidth = PlatformHelper.getCharacterWidth(f);
      }

      return defaultCharWidth;
    }

    return PlatformHelper.getCharacterWidth(f);
  }

  public static UIFont getDefaultFont() {
    if (defaultFont == null) {
      return getSystemFont();
    }

    return defaultFont;
  }

  public static float getDefaultFontSize() {
    return defaultFontSize;
  }

  public static int getDefaultLineHeight() {
    return defaultLineHeight;
  }

  public static int getDefaultWidgetHeight() {
    return ScreenUtils.platformPixels(28);
  }

  /**
   * Returns a font created using the specified criteria
   *
   * @param base the base font (can be null)
   * @param family the font family (can be null)
   * @param sstyle  the font style (can be null)
   * @param ssize the font size (can be null)
   * @param monospaced true to create a monospaced font; false otherwise
   *
   * @return the newly created font
   */
  public static UIFont getFont(UIFont base, String family, String sstyle, String ssize, boolean monospaced) {
    int style = -1;

    if (sstyle != null) {
      if (sstyle.contains("italic")) {
        style = UIFont.ITALIC;

        if (sstyle.contains("bold")) {
          style |= UIFont.BOLD;
        }
      } else {
        if (sstyle.contains("bold")) {
          style = UIFont.BOLD;
        }
      }
    }

    return getFont(base, family, style, ssize, monospaced, false, false);
  }

  public static UIFont getFont(UIFont base, String family, int style, String ssize, boolean monospaced,
                               boolean underlined, boolean strikeThrough) {
    UIFont f = (base == null)
               ? getDefaultFont()
               : base;

    if (monospaced) {
      f = Platform.getUIDefaults().getFont("Rare.font.plaintext");

      if ((style == 0) && (ssize == null)) {
        if (strikeThrough || underlined) {
          f = new UIFont(f);
          f = f.derive(strikeThrough, underlined);
        }

        return f;
      }
    } else if (family != null) {
      if ("system".equalsIgnoreCase(family)) {
        f = getSystemFont();
      } else if (family.startsWith("(default)")) {
        family = null;
      }
    }

    if (f == null) {
      f = getDefaultFont();
    }

    if (style == -1) {
      style = f.getStyle();
    }

    float size = f.getSize();

    if (size == 0) {
      size = 8;
    }

    float         fsize = size;
    UIFont        ff;
    final boolean sizeSame = (ssize == null) || (ssize.indexOf(',') == -1);

    if (sizeSame && (ssize != null)) {
      fsize = getSize(ssize, size);
    }

    if ((family != null) && (family.indexOf(',') != -1)) {
      int    i = 0;
      String s, ss;

      while((s = CharScanner.getPiece(family, ',', ++i)) != null) {
        if (s.equalsIgnoreCase("sans-serif")) {
          s = "SansSerif";
        }

        if (!sizeSame) {
          ss = CharScanner.getPiece(ssize, ',', i);

          if ((ss != null) && (ss.length() > 0)) {
            fsize = getSize(ss, size);
          }
        }

        ff = new UIFont(s, style, (int) fsize);

        if (!ff.getFamily().equals("Dialog")) {
          if (fsize != (int) fsize) {
            ff = ff.deriveFontSize(fsize);
          }

          return ff;
        }
      }

      fsize  = size;
      family = null;
    } else if ("sans-serif".equalsIgnoreCase(family)) {
      family = "SansSerif";
    }

    if (family != null) {
      f = newFont(f, family, style, fsize, f.getFamily().equalsIgnoreCase(family));

      if (strikeThrough || underlined) {
        f = f.derive(strikeThrough, underlined);
      }

      return f;
    }

    f = newFont(f, f.getFamily(), style, fsize, true);

    if (strikeThrough || underlined) {
      f = f.derive(strikeThrough, underlined);
    }

    return f;
  }

  public static float getFontHeight(UIFont f, boolean full) {
    return PlatformHelper.getFontHeight(f, full);
  }

  public static UIFont getMonospacedFont(int size) {
    return PlatformHelper.getMonospacedFont(size);
  }

  public static float getRelativeFontSize() {
    return relativeFontSize;
  }

  public static float getSize(String ssize, float size) {
    if ((ssize != null) && (ssize.length() > 0)) {
      if (ssize.endsWith("px")) {
        size = ScreenUtils.getCssPixelSize(SNumber.floatValue(ssize));
      } else if (ssize.startsWith("+")) {
        size += SNumber.floatValue(ssize);
      } else if (ssize.startsWith("-")) {
        size += SNumber.floatValue(ssize);
      } else if (ssize.startsWith("*")) {
        size *= SNumber.floatValue(ssize.substring(1));
      } else if (ssize.endsWith("pt")) {
        size = ScreenUtils.toPlatformPixels(SNumber.floatValue(ssize), ScreenUtils.Unit.POINT, true);
      } else if (ssize.endsWith("em")) {
        size *= SNumber.floatValue(ssize);
      } else if (ssize.endsWith("%")) {
        size *= SNumber.floatValue(ssize);
        size /= 100;
      } else if (ssize.endsWith("pc")) {
        size = ScreenUtils.toPlatformPixels(SNumber.floatValue(ssize), ScreenUtils.Unit.PICA, true);
      } else {
        size = SNumber.floatValue(ssize);
      }
    }

    return size;
  }

  public static UIFont getSystemFont() {
    return systemFont;
  }

  private static UIFont newFont(UIFont base, String family, int style, float fsize, boolean sameFamily) {
    final int size = (int) fsize;
    UIFont    f;

    if (!sameFamily && (customFonts != null)) {
      f = customFonts.get(family);

      if (f != null) {
        base       = f;
        sameFamily = true;
      }
    }

    if (sameFamily) {
      f = base.deriveFont(style, fsize);
    } else {
      f = new UIFont(family, style, size);

      if (size != (int) fsize) {
        f = f.deriveFontSize(fsize);
      }
    }

    return f;
  }
}
