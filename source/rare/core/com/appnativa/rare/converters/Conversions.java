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

package com.appnativa.rare.converters;

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.CharScanner;
import com.appnativa.util.SDecimal;
import com.appnativa.util.SNumber;
import com.appnativa.util.SimpleDateFormatEx;

import java.io.StringReader;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.List;

/**
 *
 * @author Don DeCoteau
 */
public class Conversions {
  private static ThreadLocal<CharScanner> perThreadScanner = new ThreadLocal<CharScanner>() {
    @Override
    protected synchronized CharScanner initialValue() {
      return new CharScanner();
    }
  };

  /** Creates a new instance of Conversions */
  public Conversions() {}

  public static UIColor colorFromHexString(String s) {
    if (s == null) {
      return null;
    }

    int len = s.length();
    int r   = 0;
    int g   = 0;
    int b   = 0;
    int i   = 0;
    int a   = 255;

    if (len == 0) {
      return UIColor.BLACK;
    }

    if (s.charAt(0) == '#') {
      i++;
    }

    final int inc = (len - i == 3)
                    ? 1
                    : 2;

    if ((i + 1) < len) {
      r = ((Character.digit(s.charAt(i), 16) << 4) & 0xff);
      r += (Character.digit(s.charAt(i + 1), 16) & 0xff);
      i += inc;
    }

    if ((i + 1) < len) {
      g = ((Character.digit(s.charAt(i), 16) << 4) & 0xff);
      g += (Character.digit(s.charAt(i + inc - 1), 16) & 0xff);
      i += inc;
    }

    if ((i + 1) < len) {
      b = ((Character.digit(s.charAt(i), 16) << 4) & 0xff);
      b += (Character.digit(s.charAt(i + inc - 1), 16) & 0xff);
      i += inc;
    }

    if ((i + 1) < len) {
      a = r;
      r = b;
      b = g;
      g = ((Character.digit(s.charAt(i), 16) << 4) & 0xff);
      g += (Character.digit(s.charAt(i + inc - 1), 16) & 0xff);
    }

    return new UIColor(r, g, b, a);
  }

  public static UIColor colorFromHexString(char[] chars, int pos, int len) {
    int r = 0;
    int g = 0;
    int b = 0;
    int i = pos;
    int a = 255;

    if (len == 0) {
      return UIColor.BLACK;
    }

    len += pos;

    if (chars[i] == '#') {
      i++;
    }

    final int inc = (len - i == 3)
                    ? 1
                    : 2;

    if ((i + 1) < len) {
      r = ((Character.digit(chars[i], 16) << 4) & 0xff);
      r += (Character.digit(chars[i + inc - 1], 16) & 0xff);
      i += inc;
    }

    if ((i + inc - 1) < len) {
      g = ((Character.digit(chars[i], 16) << 4) & 0xff);
      g += (Character.digit(chars[i + inc - 1], 16) & 0xff);
      i += inc;
    }

    if ((i + inc - 1) < len) {
      b = ((Character.digit(chars[i], 16) << 4) & 0xff);
      b += (Character.digit(chars[i + inc - 1], 16) & 0xff);
      i += inc;
    }

    if ((i + inc - 1) < len) {
      a = r;
      r = g;
      g = b;
      b = ((Character.digit(chars[i], 16) << 4) & 0xff);
      b += (Character.digit(chars[i + inc - 1], 16) & 0xff);
    }

    if ((r < 0) || (r > 255) || (g < 0) || (g > 255) || (b < 0) || (b > 255) || (a < 0) || (a > 255)) {
      return null;
    }

    return new UIColor(r, g, b, a);
  }

  public static UIColor colorFromRGBString(String s) {
    if (s == null) {
      return null;
    }

    return colorFromRGBString(s.toCharArray(), 0, s.length());
  }

  public static UIColor colorFromRGBString(char chars[], int pos, int len) {
    int r = 0;
    int g = 0;
    int b = 0;
    int a = 255;

    if (len < 1) {
      return UIColor.BLACK;
    }

    CharScanner sc = perThreadScanner.get();

    sc.reset(chars, pos, len, false);

    if (sc.getCurrentChar() == '(') {
      sc.consume(1);
    }

    int[] tok = sc.trim(sc.findToken(','));

    if (tok != null) {
      r = (int) SNumber.longValue(chars, tok[0], tok[1], false) % 256;

      if (chars[tok[0] + tok[1] - 1] == '%') {
        r = ((int) ((r) * 2.55f)) % 256;
      }

      tok = sc.trim(sc.findToken(','));
    }

    if (tok != null) {
      g = (int) SNumber.longValue(chars, tok[0], tok[1], false) % 256;

      if (chars[tok[0] + tok[1] - 1] == '%') {
        g = ((int) ((g) * 2.55f)) % 256;
      }

      tok = sc.trim(sc.findToken(','));
    }

    if (tok != null) {
      b = (int) SNumber.longValue(chars, tok[0], tok[1], false) % 256;

      if (chars[tok[0] + tok[1] - 1] == '%') {
        b = ((int) ((b) * 2.55f)) % 256;
      }

      tok = sc.trim(sc.findToken(','));
    }

    if (tok != null) {
      a = (int) (SNumber.floatValue(chars, tok[0], tok[1], false) * 255) % 256;
    }

    if (r < 0) {
      r = 0;
    }

    if (g < 0) {
      g = 0;
    }

    if (b < 0) {
      b = 0;
    }

    if (a < 0) {
      a = 255;
    }

    return new UIColor(r, g, b, a);
  }

  public static String colorToHEXString(UIColor c) {
    return colorToHEXString(new StringBuilder(9), c);
  }

  public static String colorToHEXString(StringBuilder sb, UIColor c) {
    if (c == null) {
      return null;
    }

    int    r = c.getRed();
    int    g = c.getGreen();
    int    b = c.getBlue();
    int    a = c.getAlpha();
    String s;

    sb.append("#");

    if (a != 255) {
      s = Integer.toHexString(a);

      if (s.length() == 1) {
        sb.append('0');
      }

      sb.append(s);
    }

    s = Integer.toHexString(r);

    if (s.length() == 1) {
      sb.append('0');
    }

    sb.append(s);
    s = Integer.toHexString(g);

    if (s.length() == 1) {
      sb.append('0');
    }

    sb.append(s);
    s = Integer.toHexString(b);

    if (s.length() == 1) {
      sb.append('0');
    }

    sb.append(s);

    return sb.toString();
  }

  public static String colorToCSSString(UIColor c) {
    if (c == null) {
      return null;
    }

    int alpha = c.getAlpha();

    if (alpha == 255) {
      return "color: rgb(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ");";
    } else {
      float a = (int) ((float) alpha / 255.0f * 100);

      a /= 100;

      return "color: rgba(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + "," + a + ");";
    }
  }

  public static String colorToRGBString(UIColor c) {
    if (c == null) {
      return null;
    }

    int alpha = c.getAlpha();

    if (alpha == 255) {
      return "rgb(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ");";
    } else {
      float a = (int) ((float) alpha / 255.0f * 100);

      a /= 100;

      return "rgba(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + "," + a + ");";
    }
  }

  public static DateContext createDateContext(String format, boolean localize) {
    if (format == null) {
      return null;
    }

    return (DateContext) createContext(format, localize, true);
  }

  public static NumberContext createNumberContext(String format, boolean localize) {
    if (format == null) {
      return null;
    }

    return (NumberContext) createContext(format, localize, false);
  }

  /**
   * Creates a date formating object
   *
   * @param format the format string
   * @param localize true to localize the format string; false otherwise
   *
   * @return the format object or null if it could not be created
   */
  public static SimpleDateFormatEx createDateFormat(String format, boolean localize) {
    if (format == null) {
      return null;
    }

    try {
      SimpleDateFormatEx df = new SimpleDateFormatEx(format);

      if (localize) {
        format = df.toLocalizedPattern();
        df.applyLocalizedPattern(format);
      }

      return df;
    } catch(Exception e) {
      Platform.getDefaultExceptionHandler(null).handleException(e);

      return null;
    }
  }

  /**
   * Creates a number formating object
   *
   * @param format the format string
   * @param localize true to localize the format string; false otherwise
   *
   * @return the format object or null if it could not be created
   */
  public static NumberFormat createNumberFormat(String format, boolean localize) {
    if (format == null) {
      return null;
    }

    try {
      NumberFormat nf = NumberFormat.getInstance();

      if (!format.equals("*") && (nf instanceof DecimalFormat)) {
        DecimalFormat df = (DecimalFormat) nf;

        df.applyPattern(format);

        if (localize) {
          format = df.toLocalizedPattern();
          df.applyLocalizedPattern(format);
        }
      }

      return nf;
    } catch(Exception e) {
      Platform.getDefaultExceptionHandler(null).handleException(e);

      return null;
    }
  }

  /**
   * Creates a SPOT object from the specified string
   *
   * @param context the context widget
   * @param source the source in RML format
   *
   * @return the SPOT object
   */
  public static iSPOTElement createSPOTElement(iWidget context, String source) {
    if ((source == null) || (source.length() == 0)) {
      return null;
    }

    StringReader r = new StringReader(source);

    try {
      if (source.startsWith("<")) {
        throw new UnsupportedOperationException("XML format not supported");
      } else {
        return DataParser.loadSPOTObjectSDF(context, r, null, null, null);
      }
    } catch(Exception ex) {
      if (ex instanceof RuntimeException) {
        throw(RuntimeException) ex;
      } else {
        throw new ApplicationException(ex);
      }
    }
  }

  public static String fontToString(UIFont f) {
    String style = ", Plain, ";

    if (f.isBold()) {
      style = f.isItalic()
              ? ", BoldItalic, "
              : ", Bold, ";
    } else if (f.isItalic()) {
      style = ", Italic, ";
    }

    return f.getFamily() + style + f.getSize();
  }

  public static UIFont stringToFont(String value) {
    if ((value == null) || (value.length() == 0)) {
      return null;
    }

    String family = CharScanner.getPiece(value, ',', 1);
    String sstyle = CharScanner.getPiece(value, ',', 2);

    if (sstyle == null) {
      sstyle = "plain";
    }

    sstyle = sstyle.toLowerCase();

    int style = UIFont.PLAIN;

    if (sstyle.contains("italic")) {
      style |= UIFont.ITALIC;
    }

    if (sstyle.contains("bold")) {
      style |= UIFont.BOLD;
    }

    int size = SNumber.intValue(CharScanner.getPiece(value, ',', 3));

    if (size < 8) {
      size = 8;
    }

    return new UIFont(family, style, size);
  }

  /**
   * Converts a string to a BigDecimal
   *
   * @param value the string value
   *
   * @return the number or null if the conversion could not be performed
   */
  public static BigDecimal toBigDecimal(String value) {
    SNumber num = new SNumber(value);

    return SDecimal.toBigDecimal(num);
  }

  /**
   * Converts a string to a Boolean
   *
   * @param value the string value
   *
   * @return the number or null if the conversion could not be performed
   */
  public static Boolean toBoolean(String value) {
    return Boolean.valueOf(SNumber.booleanValue(value));
  }

  /**
   * Converts a string to a Long
   *
   * @param value the string value
   *
   * @return the number or null if the conversion could not be performed
   */
  public static Long toLong(String value) {
    return Long.valueOf(SNumber.longValue(value));
  }

  /**
   * Converts a string to a number
   *
   * @param value the string value
   *
   * @return the number or null if the conversion could not be performed
   */
  public Number toNumber(String value) {
    return new SNumber(value);
  }

  private static Object createContext(String format, boolean localize, boolean date) {
    if (format == null) {
      return null;
    }

    String sep;

    if (date) {
      sep = Platform.getUIDefaults().getString("Rare.format.multiDateItemSeparator");
    } else {
      sep = Platform.getUIDefaults().getString("Rare.format.multiNumberItemSeparator");
    }

    if (sep == null) {
      sep = ";";
    }

    try {
      String   iformat  = null;
      String   dformat  = null;
      String[] patterns = null;
      int      n        = format.indexOf('|');

      if (n == -1) {
        iformat = format;
      } else {
        int len = format.length();

        if (n == 0) {
          dformat = format.substring(1);
        } else if (n == (len - 1)) {
          iformat = format.substring(0, len - 1);
        } else {
          iformat = format.substring(0, n);
          dformat = format.substring(n + 1);
        }
      }

      if (iformat != null) {
        if (iformat.indexOf(sep) != -1) {
          List<String> list = CharScanner.getTokens(iformat, sep.charAt(0), true);

          patterns = list.toArray(new String[list.size()]);
        } else {
          patterns = new String[] { iformat };
        }
      }

      if (date) {
        DateFormat[] a = null;

        if (patterns != null) {
          a = new DateFormat[patterns.length];

          for (int i = 0; i < a.length; i++) {
            a[i] = createDateFormat(patterns[i], localize);
          }
        }

        SimpleDateFormatEx ddf = (dformat == null)
                                 ? null
                                 : createDateFormat(dformat, localize);
        DateContext        def;

        if (date) {
          def = Platform.getAppContext().getDefaultDateContext();
        } else {
          def = Platform.getAppContext().getDefaultDateTimeContext();
        }

        return (def == null)
               ? new DateContext(a, ddf)
               : def.create(a, ddf);
      } else {
        NumberFormat[] a = null;

        if (patterns != null) {
          a = new NumberFormat[patterns.length];

          for (int i = 0; i < a.length; i++) {
            a[i] = createNumberFormat(patterns[i], localize);
          }
        }

        NumberFormat  ddf = (dformat == null)
                            ? null
                            : createNumberFormat(dformat, localize);
        NumberContext nc  = new NumberContext(a, ddf);

        return nc;
      }
    } catch(Exception e) {
      Platform.getDefaultExceptionHandler(null).handleException(e);

      return null;
    }
  }
}
