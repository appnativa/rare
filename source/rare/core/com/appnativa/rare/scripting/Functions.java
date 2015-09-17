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

package com.appnativa.rare.scripting;

import com.appnativa.rare.FunctionCallbackChainner;
import com.appnativa.rare.FunctionCallbackWaiter;
import com.appnativa.rare.Platform;
import com.appnativa.rare.converters.Conversions;
import com.appnativa.rare.converters.DateConverter;
import com.appnativa.rare.converters.DateTimeConverter;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iFunctionHandler;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.net.URLEncoder;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIBorderHelper;
import com.appnativa.rare.ui.UIBorderIcon;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.UICompoundIcon;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIProperties;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.UITextIcon;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.effects.iAnimator;
import com.appnativa.rare.ui.effects.iAnimatorValueListener;
import com.appnativa.rare.ui.iEventHandler;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.UICompoundPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.ui.text.Segment;
import com.appnativa.rare.util.DataItemJSONParser;
import com.appnativa.rare.util.DataItemParserHandler;
import com.appnativa.rare.util.Grouper;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.Base64;
import com.appnativa.util.CharArray;
import com.appnativa.util.CharScanner;
import com.appnativa.util.ContainsFilter;
import com.appnativa.util.EqualityFilter;
import com.appnativa.util.FilterableList;
import com.appnativa.util.Helper;
import com.appnativa.util.ISO88591Helper;
import com.appnativa.util.IdentityArrayList;
import com.appnativa.util.IntList;
import com.appnativa.util.ObjectCache;
import com.appnativa.util.ObjectHolder;
import com.appnativa.util.RegularExpressionFilter;
import com.appnativa.util.SNumber;
import com.appnativa.util.SimpleDateFormatEx;
import com.appnativa.util.Streams;
import com.appnativa.util.StringCache;
import com.appnativa.util.iCancelable;
import com.appnativa.util.iFilter;
import com.appnativa.util.iFilterableList;
import com.appnativa.util.iPreferences;
import com.appnativa.util.json.JSONArray;
import com.appnativa.util.json.JSONException;
import com.appnativa.util.json.JSONObject;
import com.appnativa.util.xml.XMLUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.net.URL;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class implementing support for functions exposed via the "rare" object within
 * the scripting environment and as embedded functions within configuration
 * strings.
 *
 * @author Don DeCoteau
 */
public class Functions implements iFunctionHandler {
  protected static final int FUNC_ADD           = 44;
  protected static final int FUNC_APPURL        = 34;
  protected static final int FUNC_BASE64        = 17;
  protected static final int FUNC_BOLD          = 20;
  protected static final int FUNC_CHOP          = 6;
  protected static final int FUNC_CODEBASE      = 33;
  protected static final int FUNC_COLOR         = 43;
  protected static final int FUNC_CONCAT        = 22;
  protected static final int FUNC_CURRENTDATE   = 38;
  protected static final int FUNC_CURRENTTIME   = 28;
  protected static final int FUNC_CURRENT_TIME  = 42;
  protected static final int FUNC_DATE          = 25;
  protected static final int FUNC_DATE_TIME     = 26;
  protected static final int FUNC_DOCBASE       = 37;
  protected static final int FUNC_END_TAG       = 24;
  protected static final int FUNC_ESCAPE        = 27;
  protected static final int FUNC_HMAC_MD5      = 12;
  protected static final int FUNC_HMAC_SHA      = 13;
  protected static final int FUNC_HTML          = 40;
  protected static final int FUNC_ITALIC        = 21;
  protected static final int FUNC_LENGTH        = 4;
  protected static final int FUNC_LOCATION      = 46;
  protected static final int FUNC_LOWER_CASE    = 2;
  protected static final int FUNC_MD5           = 15;
  protected static final int FUNC_NANOTIME      = 29;
  protected static final int FUNC_ORIENTATION   = 45;
  protected static final int FUNC_PIECE         = 7;
  protected static final int FUNC_PROPERTY      = 32;
  protected static final int FUNC_RANDOM        = 11;
  protected static final int FUNC_REPLACE_PIECE = 18;
  protected static final int FUNC_RESOLVE       = 35;
  protected static final int FUNC_RFORMAT       = 41;
  protected static final int FUNC_SERVERBASE    = 36;
  protected static final int FUNC_SHA           = 14;
  protected static final int FUNC_SOUND         = 19;
  protected static final int FUNC_START_TAG     = 23;
  protected static final int FUNC_SUBSTRING     = 3;
  protected static final int FUNC_TRIM          = 5;
  protected static final int FUNC_UNDERLINED    = 39;
  protected static final int FUNC_UPPER_CASE    = 1;
  // last=46
  private static final char[]           BR                 = "<br/>".toCharArray();
  private static ThreadLocal<CharArray> perThreadCharArray = new ThreadLocal<CharArray>() {
    @Override
    protected synchronized CharArray initialValue() {
      return new CharArray();
    }
  };
  private static ThreadLocal<CharScanner> perThreadScanner = new ThreadLocal<CharScanner>() {
    @Override
    protected synchronized CharScanner initialValue() {
      return new CharScanner();
    }
  };
  private static ThreadLocal<ArrayList<String>> perThreadStringList = new ThreadLocal<ArrayList<String>>() {
    @Override
    protected synchronized ArrayList<String> initialValue() {
      return new ArrayList<String>();
    }
  };
  private static final HashMap<String, Integer> functionMap      = new HashMap<String, Integer>();
  private static final Pattern                  urlPattern       =
    Pattern.compile("\\b(www\\.|http://|https://)\\S+\\b");
  private static char[]                         wwwPrefixChars   = "www.".toCharArray();
  private static char[]                         urlParamsTokens  = "?&;".toCharArray();
  private static char[]                         httpsPrefixChars = "https://".toCharArray();
  private static char[]                         httpPrefixChars  = "http://".toCharArray();
  private static boolean                        _initialized;
  private static Functions                      _instance;
  private static String                         osVersion;
  private static Random                         randomGenerator;
  private String[]                              zero_string = new String[] {};

  /**
   * Creates a new instance of Functions
   */
  public Functions() {
    if (!_initialized) {
      _initialized = true;
      initializeFunctionMap();
      _instance = this;
    }
  }

  /**
   * Converts the specified objects to numbers and add them together
   *
   * @param a
   *          object a
   * @param b
   *          a object a
   * @return the result of the operation
   */
  public static Number add(Object a, Object b) {
    return new SNumber(a.toString()).add(b.toString());
  }

  /**
   * Decrypts the specified value using AES 128 bit encryption
   *
   * @param val
   *          the value to be decrypted
   * @param password
   *          the decryption key
   * @param salt
   *          the salt
   * @param iteration
   *          the number of iterations
   * @param base64
   *          true to return the value as a base64 string; false otherwise
   *
   * @return the decrypted value
   */
  public static String aesDecrypt(String val, String password, String salt, int iteration, boolean base64) {
    return FunctionHelper.aesDecrypt(val, password, salt, iteration, base64);
  }

  /**
   * Encrypts the specified value using AES 128 bit encryption
   *
   * @param val
   *          the value to be decrypted
   * @param password
   *          the decryption key
   * @param salt
   *          the salt
   * @param iteration
   *          the number of iterations
   *
   * @param base64
   *          true to return the value as a base64 string; false otherwise
   * @return the decrypted value
   */
  public static String aesEncrypt(String val, String password, String salt, int iteration, boolean base64) {
    return FunctionHelper.aesEncrypt(val, password, salt, iteration, base64);
  }

  /**
   * Returns a string representing the application's main URL
   *
   * @return a string representing the application's main URL
   */
  public static String applicationURL() {
    return Platform.getAppContext().getApplicationURL().toString();
  }

  /**
   * Returns a string representing the application's main URL
   *
   * @param context
   *          the widget context
   * @return a string representing the application's main URL
   */
  public static String applicationURL(iWidget context) {
    return context.getAppContext().getApplicationURL().toString();
  }

  /**
   * Returns the specified object as a list. If the object is already it is
   * simply returned If it is an array a new is created that is backed by the
   * array other a list with the specified item as it's only value is returned
   *
   * @param o
   *          the object
   * @return the list
   */
  public static List asList(Object o) {
    if (o instanceof List) {
      return (List) o;
    }

    if (o instanceof Object[]) {
      return Arrays.asList((Object[]) o);
    }

    return Collections.nCopies(1, o);
  }

  /**
   * Base64 encodes the specified string
   *
   * @param val
   *          the value to be encoded
   *
   * @return the base64 encoding of the specified string
   */
  public static String base64(String val) {
    if (val == null) {
      return "";
    }

    return Base64.encode(ISO88591Helper.getInstance().getBytes(val));
  }
  /**
   * Base64 encodes the specified string
   *
   * @param val
   *          the value to be encoded
   *
   * @return the base64 encoding of the specified string
   */
  public static String base64NOLF(String val) {
    if (val == null) {
      return "";
    }

    return Base64.encodeBytes(ISO88591Helper.getInstance().getBytes(val),Base64.DONT_BREAK_LINES);
  }

  /**
   * Returns the boolean representation of the specified object.
   *
   * @param o
   *          the object
   * @return the boolean representing the object
   */
  public static boolean booleanValue(Object o) {
    if (o == null) {
      return false;
    }

    if (o instanceof Boolean) {
      return ((Boolean) o).booleanValue();
    }

    if (o instanceof Number) {
      return ((Number) o).floatValue() != 0;
    }

    return SNumber.booleanValue(o.toString());
  }

  /**
   * Caches data using the file system
   *
   * @param name
   *          the name to cache the item under (must be a valid file name)
   * @param in
   *          the input stream to cache
   * @return true if the data was cached; false otherwise
   */
  public static boolean cacheData(String name, InputStream in) {
    try {
      File             f = createCacheFile(name);
      FileOutputStream o = new FileOutputStream(f);

      try {
        Streams.streamToStream(in, o, null);
      } finally {
        o.close();
      }

      return true;
    } catch(Exception e) {
      Platform.ignoreException(null, e);

      return false;
    }
  }

  /**
   * Caches data using the file system
   *
   * @param name
   *          the name to cache the item under (must be a valid file name)
   * @param data
   *          the data to cache
   *
   * @return true if the data was cached; false otherwise
   */
  public static boolean cacheData(String name, String data) {
    try {
      File               f = createCacheFile(name);
      OutputStreamWriter o = new OutputStreamWriter(new FileOutputStream(f), "utf-8");

      try {
        o.write(data);
      } finally {
        o.close();
      }

      return true;
    } catch(Exception e) {
      Platform.ignoreException(null, e);

      return false;
    }
  }

  /**
   * Converts a character to a string
   *
   * @param c
   *          the character
   *
   * @return the string representation
   */
  public static String charToString(char c) {
    return String.valueOf(c);
  }

  /**
   * Chops the specified amount of characters for the specified string
   *
   * @param val
   *          the value to chop
   * @param size
   *          the amount of characters to chop
   *
   * @return the chopped string
   */
  public static String chop(String val, int size) {
    if (val == null) {
      return "";
    }

    if (size == 0) {
      return val;
    }

    boolean left = false;

    if (size < 0) {
      left = true;
      size *= -1;
    }

    int len = val.length();

    if (size >= len) {
      return "";
    }

    val = left
          ? val.substring(size)
          : val.substring(0, len - size);

    return val;
  }

  /**
   * Returns a string representing the codebase for an application launched via
   * webstart
   *
   * @return a string representing the codebase or null if the app was not
   *         launched via webstart
   */
  public static String codeBase() {
    return codeBase(Platform.getContextRootViewer());
  }

  /**
   * Returns a string representing the codebase for an application launched via
   * webstart
   *
   * @param context
   *          the widget context
   * @return a string representing the codebase or null if the app was not
   *         launched via webstart
   */
  public static String codeBase(iWidget context) {
    Object u = context.getAppContext().getCodeBase();

    return (u == null)
           ? null
           : u.toString();
  }

  /**
   * Converts a color to its hex string representation
   *
   * @param c
   *          the color
   * @return the hex string representation
   */
  public static String colorToHexString(UIColor c) {
    return Conversions.colorToHEXString(c);
  }

  /**
   * Converts a color to its RGB string representation
   *
   * @param c
   *          the color
   * @return the RGB string representation
   */
  public static String colorToRGBString(UIColor c) {
    return Conversions.colorToRGBString(c);
  }

  /**
   * Concatenates the specified objects into a single string
   *
   * @param a
   *          object a
   * @param b
   *          a object a
   * @return the result of the operation
   */
  public static String concat(Object a, Object b) {
    return a.toString() + b.toString();
  }

  /**
   * Converts a date string in the default item format to the default display
   * format
   *
   * @param context
   *          the context
   * @param date
   *          the date string or date /calendar object in the default item
   *          format
   * @return the date string in the default display format
   *
   */
  public static String convertDate(iWidget context, Object date) {
    return convertDate(context, date, true);
  }

  /**
   * Converts a date string in the default item format to the default display
   * format
   *
   * @param context
   *          the context
   * @param date
   *          the date string or date /calendar object in the default item
   *          format
   * @param display
   *          true to convert the date for display; false to convert the date to
   *          the item (server) format
   * @return the date string in the default display format
   *
   */
  public static String convertDate(iWidget context, Object date, boolean display) {
    if (date == null) {
      return "";
    }

    Date d;

    if (date instanceof Date) {
      d = (Date) date;
    } else if (date instanceof Calendar) {
      d = ((Calendar) date).getTime();
    } else {
      DateConverter cvt = new DateConverter();

      d = (Date) cvt.fromString(date.toString(), context.getAppContext().getDefaultDateContext());
    }

    DateFormat df = display
                    ? context.getAppContext().getDefaultDateContext().getDisplayFormat()
                    : context.getAppContext().getDefaultDateContext().getItemFormat();

    return df.format(d);
  }

  /**
   * Converts a date string in the default item format to the specified format
   *
   * @param context
   *          the context
   * @param date
   *          the date string or date /calendar object in the default item
   *          format
   * @param outputFormat
   *          the output format
   * @return the date string in the output format
   *
   * @throws java.text.ParseException
   */
  public static String convertDate(iWidget context, Object date, String outputFormat) throws ParseException {
    if (date == null) {
      return "";
    }

    Date d;

    if (date instanceof Date) {
      d = (Date) date;
    } else if (date instanceof Calendar) {
      d = ((Calendar) date).getTime();
    } else {
      String s = (String) date;

      if (s.length() == 0) {
        return null;
      }

      DateConverter cvt = new DateConverter();

      d = (Date) cvt.fromString(s, context.getAppContext().getDefaultDateContext());
    }

    SimpleDateFormatEx df = new SimpleDateFormatEx(outputFormat);

    return df.format(d);
  }

  /**
   * Converts a date string from one format to another
   *
   * @param context
   *          the context
   * @param date
   *          the date string or date /calendar object in the default item
   *          format
   * @param inputFormat
   *          the input format
   * @param outputFormat
   *          the output format
   * @return the date string in the output format
   *
   * @throws java.text.ParseException
   */
  public static String convertDate(iWidget context, Object date, String inputFormat, String outputFormat)
          throws ParseException {
    if (date == null) {
      return "";
    }

    Date d;

    if (date instanceof Date) {
      d = (Date) date;
    } else if (date instanceof Calendar) {
      d = ((Calendar) date).getTime();
    } else {
      String s = (String) date;

      if (s.length() == 0) {
        return null;
      }

      DateConverter cvt = new DateConverter();

      d = (Date) cvt.fromString(s, context.getAppContext().getDefaultDateContext());
    }

    SimpleDateFormatEx df = new SimpleDateFormatEx(outputFormat);

    return df.format(d);
  }

  /**
   * Converts a date/time string in the default item format to the default
   * display format
   *
   * @param context
   *          the context
   * @param date
   *          the date string or date /calendar object in the default item
   *          format
   * @return the date/time string in the default display format
   *
   * @throws java.text.ParseException
   */
  public static String convertDateTime(iWidget context, Object date) throws ParseException {
    return convertDateTime(context, date, true);
  }

  /**
   * Converts a date/time string in the default item format to the default
   * display format
   *
   * @param context
   *          the context
   * @param date
   *          the date string or date /calendar object in the default item
   *          format
   * @param display
   *          true to convert the date for display; false to convert the date to
   *          the item (server) format
   * @return the date/time string in the default display format
   *
   * @throws java.text.ParseException
   */
  public static String convertDateTime(iWidget context, Object date, boolean display) throws ParseException {
    if (date == null) {
      return "";
    }

    Date d;

    if (date instanceof Date) {
      d = (Date) date;
    } else if (date instanceof Calendar) {
      d = ((Calendar) date).getTime();
    } else {
      String s = (String) date;

      if (s.length() == 0) {
        return null;
      }

      DateTimeConverter cvt = new DateTimeConverter();

      d = (Date) cvt.fromString(s, context.getAppContext().getDefaultDateTimeContext());
    }

    DateFormat df = display
                    ? context.getAppContext().getDefaultDateTimeContext().getDisplayFormat()
                    : context.getAppContext().getDefaultDateTimeContext().getItemFormat();

    return df.format(d);
  }

  /**
   * Creates a new background painter
   *
   * @param bg
   *          the background color
   *
   * @return a new background painter
   */
  public static iBackgroundPainter createBackgroundPainter(UIColor bg) {
    if ((bg instanceof UIColorShade) && ((UIColorShade) bg).getBackgroundPainter() != null) {
      return ((UIColorShade) bg).getBackgroundPainter();
    }

    return UIColorHelper.getBackgroundPainter(bg);
  }

  /**
   * Creates a border from a configuration string
   *
   * @param borderString
   *          the border string
   *
   * @return the border instance
   */
  public static iPlatformBorder createBorder(String borderString) {
    return UIBorderHelper.createBorder(borderString);
  }

  /**
   * Creates an icon that has a border around it
   *
   * @param border
   *          the border
   * @param icon
   *          the icon to place the border around
   * @return the border icon instance
   */
  public static iPlatformIcon createBorderIcon(iPlatformBorder border, iPlatformIcon icon) {
    return new UIBorderIcon(border, icon);
  }

  /**
   * Creates a file in the file caching directory
   *
   * @param name
   *          the name of the file
   *
   * @return the new file
   */
  public static File createCacheFile(String name) throws IOException {
    return Platform.createCacheFile(name);
  }

  /**
   * Creates an object cache that can be bounded and will automatically release
   * memory as needed. The put(key,value) and get(key) methods can be used work
   * with the cache
   *
   * @param len
   *          the initial capacity for the map;
   * @param maxSize
   *          the maximum size the cache can grow (use -1 for a cache that only
   *          get purges when additional memory is needed)
   * @return the object cache
   */
  public static ObjectCache createCacheMap(int len, int maxSize) {
    ObjectCache oc = new ObjectCache(len);

    oc.setBufferSize(maxSize);

    return oc;
  }

  /**
   * Creates a Calendar object from a relative date/time specification
   * <p>
   * Examples of valid data/time specifiers are:
   * <ul>
   * <li>T (for today)</li>
   * <li>T+1@8 (tomorrow at 8AM)</li>
   * <li>T+3@6pm (for 3 days from now at 6pm)</li>
   * <li>W+3 (for 3 weeks in the future)</li>
   * <li>M+3 (for 3 months in the future)</li>
   * <li>T (for TODAY), T+1 (for TOMORROW), T+2, T+7, etc.</li>
   * <li>T-1 (for YESTERDAY), T-3W (for 3 WEEKS AGO), etc.</li>
   * <li>N (for NOW)</li>
   * <li>N+3 (for 3 minutes from now)</li>
   * <li>N+3H (for 3 hours from now)</li>
   * </ul>
   * </p>
   *
   * @param spec
   *          the date/time specification
   *
   * @return a Calendar object representing the date/time specification
   */
  public static Calendar createCalendar(String spec) {
    return (spec == null)
           ? Calendar.getInstance()
           : Helper.createCalendar(spec);
  }

  /**
   * Creates an icon that fills the specified width and height with the
   * specified color
   *
   * @param color
   *          the color
   * @param width
   *          the width
   * @param height
   *          the height
   * @param border
   *          optional border
   * @return the icon
   */
  public static iPlatformIcon createColorIcon(final UIColor color, final int width, final int height,
          final iPlatformBorder border) {
    return FunctionHelper.createColorIcon(color, width, height, border);
  }

  /**
   * Creates a compound border from the two specified borders
   *
   * @param firstBorder
   *          the first border
   * @param secondBorder
   *          the second border
   *
   * @return the new compound border
   */
  public static UICompoundBorder createCompoundBorder(iPlatformBorder firstBorder, iPlatformBorder secondBorder) {
    return new UICompoundBorder(firstBorder, secondBorder);
  }

  /**
   * Creates a compound icon from the two specified icons
   *
   * @param icons
   *          the list of icons
   *
   * @return the new compound icon
   */
  public static UICompoundIcon createCompoundIcon(iPlatformIcon... icons) {
    return UICompoundIcon.create(icons);
  }

  /**
   * Creates a compound painter from the two specified painters
   *
   * @param firstPainter
   *          the first painter
   * @param secondPainter
   *          the second painter
   *
   * @return the new compound painter
   */
  public static UICompoundPainter createCompoundPainter(iPlatformPainter firstPainter, iPlatformPainter secondPainter) {
    return new UICompoundPainter(firstPainter, secondPainter);
  }

  /**
   * Creates a hash map that is thread-safe
   *
   * @param len
   *          the initial capacity for the map;
   * @return the hash map
   */
  public static Map createConcurrentHashMap(int len) {
    return (len < 1)
           ? new ConcurrentHashMap()
           : new ConcurrentHashMap();    // TODO:
    // add
    // back
    // len
    // as
    // parameter
  }

  /**
   * Creates a filter that test for contains
   *
   * @param value
   *          the value for the filter
   * @param startsWith
   *          whether to check only if the value being tested starts with the
   *          filter value
   *
   * @return a filter
   */
  public static ContainsFilter createContainsFilter(String value, boolean startsWith) {
    return new ContainsFilter(value, true, startsWith);
  }

  /**
   * Convenience method for creating a date/time from a relative time
   * specification.
   * <p>
   * Examples of valid data/time specifiers are:
   * <ul>
   * <li>T (for today)</li>
   * <li>T+1@8 (tomorrow at 8AM)</li>
   * <li>T+3@6pm (for 3 days from now at 6pm)</li>
   * <li>W+3 (for 3 weeks in the future)</li>
   * <li>M+3 (for 3 months in the future)</li>
   * <li>T (for TODAY), T+1 (for TOMORROW), T+2, T+7, etc.</li>
   * <li>T-1 (for YESTERDAY), T-3W (for 3 WEEKS AGO), etc.</li>
   * <li>N (for NOW)</li>
   * <li>N+3 (for 3 minutes from now)</li>
   * <li>N+3H (for 3 hours from now)</li>
   * </ul>
   * </p>
   *
   * @param spec
   *          the date/time specification
   *
   * @return a date object
   */
  public static Date createDate(String spec) {
    return Helper.createDate(spec);
  }

  /**
   * Creates an empty icon that can be used as a spacer
   *
   * @param width
   *          the width for the icon
   * @param height
   *          the height for the icon
   * @param borderColor
   *          optional line border color
   * @return the icon
   */
  public static iPlatformIcon createEmptyIcon(final int width, final int height, final UIColor borderColor) {
    return FunctionHelper.createEmptyIcon(width, height, borderColor);
  }

  /**
   * Creates a filter to test for equality
   *
   * @param value
   *          the value to test for
   * @param ignorecase
   *          true if case should be ignored; false otherwise
   *
   * @return a filter
   */
  public static EqualityFilter createEqualityFilter(String value, boolean ignorecase) {
    return new EqualityFilter(value, ignorecase);
  }

  /**
   * Creates an object that can be used to chain callback requests. This object
   * becomes the root of the callback chain.
   *
   * @return an object that can be used to chain callback requests
   */
  public static FunctionCallbackChainner createFunctionCallbackChainner() {
    return new FunctionCallbackChainner();
  }

  /**
   * Creates an object that can be used to wait on the completion of multiple
   * callbacks.
   *
   *
   * @return an object that can be used to wait on the completion of multiple
   *         callbacks.
   */
  public static FunctionCallbackWaiter createFunctionCallbackWaiter() {
    return new FunctionCallbackWaiter();
  }

  /**
   * Creates a grouper that can be use to grow table-based data
   *
   * @return the grouper
   */
  public static Grouper createGrouper() {
    return new Grouper();
  }

  /**
   * Creates a hash map that maintains its insertion order
   *
   * @param len
   *          the initial capacity for the map;
   * @return the hash map
   */
  public static Map createHashMap(int len) {
    return (len < 1)
           ? new LinkedHashMap()
           : new LinkedHashMap(len);
  }

  /**
   * Creates a hash map whose equality test is based on '==' and not the
   * equals() method
   *
   * @param len
   *          the initial capacity for the map;
   * @return the hash map
   */
  public static Map createIdentityHashMap(int len) {
    return (len < 1)
           ? new IdentityHashMap()
           : new IdentityHashMap(len);
  }

  /**
   * Creates a list whose equality test is based on '==' and not the equals()
   * method
   *
   * @param len
   *          the initial capacity for the map;
   * @return the list
   */
  public static List createIdentityList(int len) {
    return (len < 1)
           ? new IdentityArrayList()
           : new IdentityArrayList(len);
  }

  /**
   * Create an integer array of the specified size
   *
   * @param size
   *          the size of the array
   * @return the new array
   */
  public static int[] createIntArray(int size) {
    return new int[size];
  }

  /**
   * Create an integer array of the specified size
   *
   * @param size
   *          the size of the array
   * @return the new array
   */
  public static IntList createIntArrayList(int size) {
    return new IntList((size < 1)
                       ? 10
                       : size);
  }

  /**
   * Creates a new JSON array
   *
   * @param o
   *          a List,an array, a String or null
   *
   * @return the JSON array
   */
  public static JSONArray createJSONArray(Object o) throws JSONException {
    if (o instanceof String) {
      return new JSONArray((String) o);
    }

    if (o instanceof Collection) {
      return new JSONArray((Collection) o);
    }

    if (o != null) {
      return new JSONArray(o.toString());
    }

    return new JSONArray();
  }

  /**
   * Creates a new JSON object
   *
   * @param o
   *          the object to use to populate the JSON object (can be null)
   *
   * @return the JSON object
   */
  public static JSONObject createJSONObject(Object o) throws JSONException {
    if (o instanceof String) {
      return new JSONObject((String) o);
    }

    if (o instanceof Map) {
      return new JSONObject((Map) o);
    }

    if (o != null) {
      return new JSONObject(o.toString());
    }

    return new JSONObject();
  }

  /**
   * Creates a list object to use to hold items
   *
   * @param obj
   *          the initial capacity for the list or an array to populate the list
   *          with;
   * @return the list
   */
  public static List createList(Object obj) {
    if (obj instanceof List) {
      return new FilterableList((List) obj);
    }

    if ((obj == null) || (obj instanceof Number)) {
      int len = (obj == null)
                ? -1
                : ((Number) obj).intValue();

      return (len == -1)
             ? new FilterableList()
             : new FilterableList(len);
    }

    List l = new FilterableList(((Object[]) obj).length);

    return l;
  }

  /**
   * Create an object array of the specified size
   *
   * @param size
   *          the size of the array
   * @return the new array
   */
  public static Object[] createObjectArray(int size) {
    return new Object[size];
  }

  /**
   * Create an object holder that holds a key value pair and associated flags
   *
   * @param key
   *          the key
   * @param value
   *          the value
   * @return the object holder
   */
  public static ObjectHolder createObjectHolder(Object key, Object value) {
    return new ObjectHolder(key, value);
  }

  /**
   * Creates a filter that uses regular expressions
   *
   * @param value
   *          the value for the filter
   * @param parse
   *          true if the value is a wildcard expression that should be parsed
   *          to create the regular expression; false if the value represents a
   *          valid regular expression
   *
   * @return a filter
   */
  public static RegularExpressionFilter createRegExFilter(String value, boolean parse) {
    if (parse) {
      return RegularExpressionFilter.createFilter(value);
    }

    return new RegularExpressionFilter(value);
  }

  /**
   * Create a script-able variable for the specified java object. Wraps the
   * specified object as necessary to be used within the scripting environment
   * for the given widget.
   *
   * @param context
   *          the widget context
   * @param javaobj
   *          the java object.
   *
   * @return the string representation of the specified script variable
   */
  public static Object createScriptableVar(iWidget context, Object javaobj) {
    if (context == null) {
      context = Platform.getContextRootViewer();
    }

    return context.getScriptHandler().createVariableValue(context.getScriptingContext(), javaobj);
  }

  /**
   * Creates a map maintains automatically sorts is key
   *
   * @return the sported map
   */
  public static Map createSortedMap() {
    return new TreeMap();
  }

  /**
   * Create a string array of the specified size
   *
   * @param size
   *          the size of the array
   * @return the new array
   */
  public static String[] createStringArray(int size) {
    return new String[size];
  }

  /**
   * Creates an icon that will display the specified text
   *
   * @param text
   *          the text for the icon (html is not supported)
   * @param font
   *          optional font for the text
   * @param border
   *          optional border for the text
   * @return the icon
   */
  public static UITextIcon createTextIcon(String text, UIColor fg, UIFont font, iPlatformBorder border) {
    return new UITextIcon(text, fg, font, border);
  }

  /**
   * Creates an image that will display the specified text
   *
   * @param text
   *          the text for the image (html is supported)
   * @param font
   *          optional font for the text
   *
   * @return the image
   */
  public static UIImage createTextImage(String text, UIFont font) {
    return createTextImage(text, font, null, null, null, false);
  }

  /**
   * Creates an image that will display the specified text
   *
   * @param text
   *          the text for the image (html is supported)
   * @param font
   *          optional font for the text
   * @param fg
   *          optional foreground color
   * @param bg
   *          optional background color
   * @param b
   *          optional border
   * @param square
   *          true to create a square image; false otherwise
   * @return the image
   */
  public static UIImage createTextImage(String text, UIFont font, UIColor fg, UIColor bg, iPlatformBorder b,
          boolean square) {
    return FunctionHelper.createTextImage(text, font, fg, bg, b, square);
  }

  /**
   * Creates an painter that will display the specified text
   *
   * @param text
   *          the text for the painter (html is not supported)
   *
   * @return the painter
   */
  public static UITextIcon createTextPainter(String text) {
    return new UITextIcon(text);
  }

  /**
   * Creates a value based animator
   *
   * @param start
   *          the starting value
   * @param end
   *          the ending value
   * @param inc
   *          the increment
   * @param accelerate
   *          true to accelerate into the animation; false otherwise
   * @param decelerate
   *          true to decelerate out of the animation; false otherwise
   * @param l
   *          the value listener
   *
   * @return the value animator or null if this type of animation is not
   *         supported
   */
  public static iAnimator createValueAnimator(double start, double end, double inc, boolean accelerate,
          boolean decelerate, iAnimatorValueListener l) {
    return FunctionHelper.createValueAnimator(start, end, inc, accelerate, decelerate, l);
  }

  /**
   * Returns the current date as a string
   *
   * @param format
   *          a format specified for the date output (null for the default
   *          format)
   *
   * @return a string object representing the current date
   */
  public static String currentDate(String format) {
    return currentDate(null, format);
  }

  /**
   * Returns the current date as a string
   *
   * @param context
   *          the widget context
   * @param format
   *          a format specified for the date output
   *
   * @return a string object representing the current date
   */
  public static String currentDate(iWidget context, String format) {
    if (context == null) {
      context = Platform.getContextRootViewer();
    }

    Date       date = new Date();
    DateFormat df   = (format == null)
                      ? context.getAppContext().getDefaultDateContext().getDisplayFormat()
                      : new SimpleDateFormatEx(format);

    return df.format(date);
  }

  /**
   * Returns a string representing the current time in milliseconds
   *
   * @return a string representing the current time in milliseconds
   */
  public static long currentTime() {
    return System.currentTimeMillis();
  }

  /**
   * Returns the current time as a string
   *
   * @param format
   *          a format specified for the time output (null for the default
   *          format)
   *
   * @return a string object representing the current time
   */
  public static String currentTime(String format) {
    return currentTime(null, format);
  }

  /**
   * Returns the current time as a string
   *
   * @param context
   *          the widget context
   * @param format
   *          a format specified for the time output
   *
   * @return a string object representing the current time
   */
  public static String currentTime(iWidget context, String format) {
    if (context == null) {
      context = Platform.getContextRootViewer();
    }

    Date       date = new Date();
    DateFormat df   = (format == null)
                      ? context.getAppContext().getDefaultTimeContext().getDisplayFormat()
                      : new SimpleDateFormatEx(format);

    return df.format(date);
  }

  /**
   * Convenience method for creating a date/time string from a relative time
   * specification.
   * <p>
   * Examples of valid data/time specifiers are:
   * <ul>
   * <li>T (for today)</li>
   * <li>T+1@8 (tomorrow at 8AM)</li>
   * <li>T+3@6pm (for 3 days from now at 6pm)</li>
   * <li>W+3 (for 3 weeks in the future)</li>
   * <li>M+3 (for 3 months in the future)</li>
   * <li>T (for TODAY), T+1 (for TOMORROW), T+2, T+7, etc.</li>
   * <li>T-1 (for YESTERDAY), T-3W (for 3 WEEKS AGO), etc.</li>
   * <li>N (for NOW)</li>
   * <li>N+3 (for 3 minutes from now)</li>
   * <li>N+3H (for 3 hours from now)</li>
   * </ul>
   * </p>
   *
   * @param spec
   *          the date/time specification
   *
   * @return a string object representing the date/time specification
   */
  public static String date(String spec) {
    return date(Platform.getContextRootViewer(), spec);
  }

  /**
   * Convenience method for creating a date string from a relative time
   * specification
   *
   * @param context
   *          the widget context
   * @param spec
   *          the date specification
   *
   * @return a string object representing the time specification
   */
  public static String date(iWidget context, String spec) {
    Date date = Helper.createDate(spec);

    return context.getAppContext().getDefaultDateContext().getDisplayFormat().format(date);
  }

  /**
   * Convenience method for creating a date string from a relative time
   * specification
   *
   * @param spec
   *          the date specification
   * @param format
   *          the date format string
   *
   * @return a string object representing the date/time specification
   */
  public static String date(String spec, String format) {
    return date(Platform.getContextRootViewer(), spec);
  }

  /**
   * Convenience method for creating a date string from a relative time
   * specification
   *
   * @param spec
   *          the date/time specification
   *
   * @return a string object representing the date/time specification
   */
  public static String dateTime(String spec) {
    return dateTime(Platform.getContextRootViewer(), spec);
  }

  /**
   * Convenience method for creating a date string from a relative time
   * specification
   *
   * @param context
   *          the widget context
   * @param spec
   *          the date/time specification
   *
   * @return a string object representing the date/time specification
   */
  public static String dateTime(iWidget context, String spec) {
    Date       date = Helper.createDate(spec);
    DateFormat df   = context.getAppContext().getDefaultDateTimeContext().getDisplayFormat();

    return df.format(date);
  }

  /**
   * Convenience method for creating a date string from a relative time
   * specification
   *
   * @param spec
   *          the date/time specification
   * @param format
   *          a format specified for the data output
   *
   * @return a string object representing the date/time specification
   */
  public static String dateTime(String spec, String format) {
    return dateTime(Platform.getContextRootViewer(), spec, format);
  }

  /**
   * Convenience method for creating a date string from a relative time
   * specification
   *
   * @param context
   *          the widget context
   * @param spec
   *          the date/time specification
   * @param format
   *          a format specified for the data output
   *
   * @return a string object representing the date/time specification
   */
  public static String dateTime(iWidget context, String spec, String format) {
    Date       date = Helper.createDate(spec);
    DateFormat df   = (format == null)
                      ? context.getAppContext().getDefaultDateContext().getDisplayFormat()
                      : new SimpleDateFormatEx(format);

    return df.format(date);
  }

  /**
   * Decodes a string that was encoded using the encode function
   *
   * @param str
   *          the string to encode
   * @see #encode(java.lang.String)
   * @return the escaped string
   */
  public static String decode(String str) {
    return URLEncoder.decode(str);
  }

  /**
   * Base64 decodes the specified string
   *
   * @param val
   *          the value to be decoded
   *
   * @return the decoded string
   */
  public static String decodeBase64(String val) {
    if (val == null) {
      return "";
    }

    byte[] b = Base64.decode(ISO88591Helper.getInstance().getBytes(val));

    return ISO88591Helper.getInstance().getString(b);
  }

  /**
   * Deletes previously cached data from the file system
   *
   * @param name
   *          the name to cache the item under (must be a valid file name)
   */
  public static void deleteCachedData(String name) {
    try {
      File f = createCacheFile(name);

      if ((f != null) && f.exists()) {
        f.delete();
      }
    } catch(Exception e) {
      Platform.ignoreException(null, e);
    }
  }

  /**
   * Deletes a file
   *
   * @param file
   *          the file to delete
   *
   */
  public static void deleteFile(File file) {
    try {
      file.delete();
    } catch(Exception ignore) {}
  }

  /**
   * Disables debug logging
   */
  public static void disableDebugLogging() {}

  /**
   * Returns a string representing the documentbase for an applet or the
   * codebase for an application launched via webstart
   *
   * @return a string representing the document base or codebase or null
   */
  public static String documentBase() {
    return documentBase(Platform.getContextRootViewer());
  }

  /**
   * Returns a string representing the documentbase for an applet or the
   * codebase for an application launched via webstart
   *
   * @param context
   *          the widget context
   * @return a string representing the document base or codebase or null
   */
  public static String documentBase(iWidget context) {
    URL u = context.getAppContext().getDocumentBase();

    return (u == null)
           ? null
           : JavaURLConnection.toExternalForm(u);
  }

  /**
   * Returns a string representing the document server base for an
   * application/applet or the codebase for an application launched via webstart
   *
   * @return a string representing the document base or codebase or null
   */
  public static String documentServerBase() {
    URL u = Platform.getAppContext().getDocumentBase();

    return (u == null)
           ? null
           : JavaURLConnection.baseToExternalForm(u);
  }

  /**
   * Encodes the specified string for use as part of a url
   *
   * @param str
   *          the string to encode
   * @see #decode
   *
   * @return the encoded url string
   */
  public static String encode(String str) {
    return URLEncoder.encode(str);
  }

  /**
   * Encodes the specified string for use as part of a url The only non
   * alpha-numeric characters that are not escaped are '.' and '_'
   *
   * @param str
   *          the string to encode
   * @see #decode
   *
   * @return the encoded url string
   */
  public static String encodeFull(String str) {
    return URLEncoder.encodeEx(str);
  }

  /**
   * Prints the string representation of the specified object to standard error
   *
   * @param o
   *          the object to print
   */
  public static void eprintln(Object o) {
    System.err.println(o);
  }

  /**
   * Escapes a string, converting characters larger than 7bit to uincode
   * representation and escapes linefeeds,tabs, etc
   *
   * @param val
   *          the value to encode
   * @return the encoded value
   */
  public static String escape(String val) {
    return CharScanner.escape(val);
  }

  /**
   * Encodes the specified string for use as part of an HTML document
   *
   * @param str
   *          the string to encode
   *
   * @param whitespace
   *          true to handle whitespace; false otherwise
   * @param addHTMLTag
   *          true to add the surrounding HTML tags; false otherwise
   * @return the escaped string
   */
  public static String escapeHTML(String str, boolean whitespace, boolean addHTMLTag) {
    if (str == null) {
      return "";
    }

    CharArray ca = perThreadCharArray.get();

    ca._length = 0;

    if (addHTMLTag) {
      ca.append("<html>");
    }

    char a[] = str.toCharArray();

    XMLUtils.escape(a, 0, a.length, whitespace, ca);

    if (addHTMLTag) {
      ca.append("</html>");
    }

    String s = ca.toString();

    if (ca._length > 512) {
      ca._length = 0;
      ca.A       = new char[512];
    }

    return s;
  }

  /**
   * Executes a function
   *
   * @param context
   *          the context
   * @param function
   *          the string representation of the function
   *
   * @return the function result
   * @throws ParseException
   */
  @Override
  public String execute(iWidget context, String function) throws ParseException {
    String[]    parameters = zero_string;
    String      name       = null;
    CharScanner sc         = perThreadScanner.get();

    sc.reset(function);

    int n = sc.indexOf('(');

    if (sc.getCurrentChar() == '$') {
      name = (n == -1)
             ? function.substring(1)
             : function.substring(1, n);
    } else {
      name = (n == -1)
             ? function
             : function.substring(0, n);
    }

    if (n > 0) {
      sc.consume(n + 1);
      sc.chop(1);

      ArrayList<String> list = perThreadStringList.get();

      list.clear();
      sc.getTokens(',', true, true, true, list);

      int len = list.size();

      if (len > 0) {
        parameters = new String[len];

        String s;
        char   c;

        // this loop needs to be recursion safe
        for (int i = 0; i < len; i++) {
          s = list.get(i);
          n = s.length();
          c = (n == 0)
              ? 0
              : s.charAt(0);

          if ((c == '\'') || (c == '\"')) {
            s = CharScanner.cleanQuoted(s);
          } else if (n > 0) {
            Object o = context.getAttribute(s);

            s = (o == null)
                ? ""
                : o.toString();
          }

          parameters[i] = s;
        }
      }
    }

    return executeFunction(context, name, parameters);
  }

  /**
   * Returns a shared instance of the event handler represented by the specified
   * class name. At any given point an time only one instance of a handler is
   * utilized. If you hold on to this instance then it guarantees that all
   * references to this class name as a script handler will use this instance.
   *
   *
   * @param className
   *          the name of the class that represents the handler
   * @return the event handler
   */
  public static iEventHandler getEventHandler(String className) {
    return EventHandlerInterface.getHandler(className);
  }

  /**
   * Executes a function
   *
   * @param context
   *          the context
   * @param name
   *          the name of the function
   * @param parameters
   *          the function parameters
   *
   * @return the function result
   */
  @Override
  public String executeFunction(iWidget context, String name, String[] parameters) {
    name = name.toLowerCase(Locale.ENGLISH);

    Integer in = functionMap.get(name);

    if (in == null) {
      throw new UnsupportedOperationException("UnsupportedOperationException: $" + name);
    }

    String   s;
    String[] a;
    int      start = 1;
    int      end   = 1;
    int      plen  = (parameters == null)
                     ? 0
                     : parameters.length;

    switch(in.intValue()) {
      case FUNC_ESCAPE :
        checkParmLength(context, plen, 1);

        return URLEncoder.encode(parameters[0]);

      case FUNC_UPPER_CASE :
        checkParmLength(context, plen, 1);

        return upperCase(parameters[0]);

      case FUNC_LOWER_CASE :
        checkParmLength(context, plen, 1);

        return lowerCase(parameters[0]);

      case FUNC_TRIM :
        checkParmLength(context, plen, 1);

        return trim(parameters[0]);

      case FUNC_LENGTH :
        checkParmLength(context, plen, 1);

        if (plen == 1) {
          s = parameters[0];

          if (s == null) {
            return "0";
          }

          return StringCache.valueOf(s.length());
        }

        return StringCache.valueOf(length(parameters[0], parameters[1]));

      case FUNC_CHOP :
        checkParmLength(context, plen, 1);

        if (plen > 1) {
          start = SNumber.intValue(parameters[1]);
        }

        return chop(parameters[0], start);

      case FUNC_RANDOM :
        if (plen > 0) {
          return StringCache.valueOf(SNumber.longValue(parameters[0]));
        }

        return StringCache.valueOf(randomLong());

      case FUNC_SUBSTRING :
        checkParmLength(context, plen, 2);
        start = SNumber.intValue(parameters[1]);
        end   = -1;

        if (plen > 1) {
          end = SNumber.intValue(parameters[2]);
        }

        return substring(parameters[0], start, end);

      case FUNC_BASE64 :
        checkParmLength(context, plen, 1);

        return base64(parameters[0]);

      case FUNC_HMAC_MD5 :
        checkParmLength(context, plen, 2);

        return hmacMD5(parameters[0], parameters[1], true);

      case FUNC_HMAC_SHA :
        checkParmLength(context, plen, 2);

        return hmacSHA(parameters[0], parameters[1], true);

      case FUNC_PIECE :
        checkParmLength(context, plen, 2);

        if (plen > 2) {
          start = SNumber.intValue(parameters[2]);
        }

        if (plen > 3) {
          end = SNumber.intValue(parameters[3]);
        }

        return piece(parameters[0], parameters[1], start, end);

      case FUNC_BOLD :
        checkParmLength(context, plen, 1);

        return "<b>" + parameters[0] + "</b>";

      case FUNC_ITALIC :
        checkParmLength(context, plen, 1);

        return "<i>" + parameters[0] + "</i>";

      case FUNC_UNDERLINED :
        checkParmLength(context, plen, 1);

        return "<u>" + parameters[0] + "</u>";

      case FUNC_HTML :
        checkParmLength(context, plen, 1);

        return "<html>" + parameters[0] + "</html>";

      case FUNC_COLOR :
        checkParmLength(context, plen, 1);

        String color = colorToHexString(ColorUtils.getColor(parameters[0]));

        if (plen > 1) {
          return "<font color=\"" + color + "\">" + parameters[1] + "</font>";
        }

        return color;

      case FUNC_CONCAT :
        checkParmLength(context, plen, 2);

        return parameters[0].toString() + parameters[1].toString();

      case FUNC_ADD :
        checkParmLength(context, plen, 2);

        return add(parameters[0], parameters[1]).toString();

      case FUNC_START_TAG :
        checkParmLength(context, plen, 1);

        return "<" + parameters[0] + ">";

      case FUNC_END_TAG :
        checkParmLength(context, plen, 1);

        return "</" + parameters[0] + ">";

      case FUNC_DATE :
        checkParmLength(context, plen, 1);

        return date(context, parameters[0]);

      case FUNC_CURRENT_TIME :
        checkParmLength(context, plen, 1);

        return currentTime(context, parameters[0]);

      case FUNC_DATE_TIME :
        checkParmLength(context, plen, 1);
        s = (plen < 2)
            ? null
            : parameters[1];

        return dateTime(context, parameters[0], s);

      case FUNC_CURRENTTIME :
        return String.valueOf(currentTime());

      case FUNC_NANOTIME :
        return String.valueOf(nanoTime());

      case FUNC_RESOLVE :
        checkParmLength(context, plen, 1);

        return resolve(context, parameters[0]);

      case FUNC_CODEBASE :
        return codeBase(context);

      case FUNC_RFORMAT :
        checkParmLength(context, plen, 2);

        if (plen == 2) {
          return rformat(context, parameters[0], parameters[1]);
        }

        a = new String[plen - 1];
        System.arraycopy(parameters, 1, a, 0, plen - 1);

        return rformat(context, parameters[0], (Object[]) a);

      case FUNC_SERVERBASE :
        return serverBase(context);

      case FUNC_DOCBASE :
        return documentBase(context);

      case FUNC_APPURL :
        return applicationURL(context);

      case FUNC_CURRENTDATE :
        return currentDate(context, (plen > 0)
                                    ? parameters[0]
                                    : null);

      case FUNC_PROPERTY :
        checkParmLength(context, plen, 1);
        s = (plen < 2)
            ? ""
            : parameters[1];

        return property(parameters[0], s);

      case FUNC_ORIENTATION :
        return getScreenOrientation();

      case FUNC_LOCATION :
        s = getLocation();

        return (s == null)
               ? ""
               : s;

      default :
        throw new UnsupportedOperationException("$" + name);
    }
  }

  /**
   * Expand a string using the specified pattern and arguments Unlike format,
   * all the argument values have to be strings and only "%s" format specified
   * is supported. This method should be faster than format
   *
   * @param pattern
   *          the printf-style pattern
   * @param args
   *          the arguments
   *
   * @return the expanded string
   *
   */
  public static String expand(String pattern, String... args) {
    return Helper.expandString(pattern, args);
  }

  /**
   * Filters the passed in list on a background thread The resultValue will be
   * an {@link ObjectHolder} with the list as the source the filter as the type
   * and the value as the source List filtered if it was an
   * {@link iFilterableList} or a new {@link iFilterableList}
   *
   * @param list
   *          the list to filter
   * @param filter
   *          the filter
   * @param cb
   *          the callback to call back with the results
   * @return a handle to use to cancel the task
   *
   * @see ObjectHolder
   */
  public static iCancelable filterInBackground(final List list, final iFilter filter, final iFunctionCallback cb) {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        final iFilterableList fl;

        if (list instanceof iFilterableList) {
          fl = (iFilterableList) list;
        } else {
          fl = new FilterableList(list);
        }

        fl.filter(filter);
        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            cb.finished(false, new ObjectHolder(list, filter, fl));
          }
        });
      }
    };

    return Platform.getAppContext().executeBackgroundTask(r);
  }

  /**
   * Finds a widget within the specified widget that has the given name If the
   * name is a path then recursion is performed in order to find the widget that
   * represents the last path component.
   *
   * @param w
   *          the widget to search
   * @param name
   *          the name of the widget to find
   * @param useNameMap
   *          true to use a name map to improve future search performance; false
   *          otherwise
   * @return the found widget
   */
  public static Widget findWidget(Widget w, String name, boolean useNameMap) {
    int n = name.indexOf('/');

    if (n == -1) {
      return w.findWidget(name);
    }

    if (n > 0) {
      w = w.findWidget(name.substring(0, n), useNameMap);
    }

    return (w == null)
           ? null
           : findWidget(w, name.substring(n + 1), useNameMap);
  }

  /**
   * Returns the float representation of the specified string. String that don't
   * start with a numeric sequence always return zero
   *
   * @param s
   *          the string
   * @return the float representation of the string
   */
  public static float floatValue(String s) {
    return SNumber.floatValue(s);
  }

  /**
   * Cause a focus request to be dispatched to the specified widget after all
   * current events are processed and the invoking script finishes execution
   *
   * @param widget
   *          the widget to request focus for
   */
  public static void focusLater(final iWidget widget) {
    Platform.invokeLater(new Runnable() {
      @Override
      public void run() {
        widget.requestFocus();
      }
    });
  }

  /**
   * Formats a string using the specified pattern and arguments
   *
   * @param pattern
   *          If the object is a number then a number format pattern, if the
   *          object is a Date or Calendar then a date format pattern otherwise
   *          a printf-style pattern
   * @param args
   *          the arguments
   *
   * @return the formatted string
   */
  public static String format(String pattern, Object... args) {
    if ((args == null) || (args.length == 0)) {
      return null;
    }

    if (args.length == 1) {
      Object obj = args[0];

      if (obj instanceof Number) {
        DecimalFormat df = new DecimalFormat(pattern);

        return df.format(obj);
      }

      if ((obj instanceof Date) || (obj instanceof Calendar)) {
        try {
          return convertDate(Platform.getContextRootViewer(), obj, pattern);
        } catch(ParseException e) {
          throw new ApplicationException(e);
        }
      }
    }

    return PlatformHelper.format(pattern, args);
  }

  /**
   * Generates a password-based key
   *
   * @param password
   *          the password
   * @param salt
   *          the salt
   * @param iteration
   *          the iteration
   *
   * @return the key as a ISO-88591 string
   */
  public static String generateKey(String password, String salt, int iteration) {
    return FunctionHelper.generateKey(password, salt, iteration);
  }

  /**
   * Generates a set of random bytes that can be use a salt for cryptographic
   * purposes
   *
   * @param bytes
   *          the number of bytes to generate
   * @return the salt as a ISO-88591 string
   */
  public static String generateSalt(int bytes) {
    return FunctionHelper.generateSalt(bytes);
  }

  /**
   * Computes a Hash-based Message Authentication Code (HMAC) using the MD5 hash
   * function.
   *
   * @param val
   *          the value
   * @param key
   *          the key
   * @param base64
   *          true to return the value as a base64 string; false otherwise
   *
   * @return the results of the computation
   */
  public static String hmacMD5(String val, String key, boolean base64) {
    return FunctionHelper.hmacMD5(val, key, base64);
  }

  /**
   * Computes a Hash-based Message Authentication Code (HMAC) using the SHA hash
   * function.
   *
   * @param val
   *          the value
   * @param key
   *          the key
   * @param base64
   *          true to return the value as a base64 string; false otherwise
   *
   * @return the results of the computation
   */
  public static String hmacSHA(String val, String key, boolean base64) {
    return FunctionHelper.hmacSHA(val, key, base64);
  }

  /**
   * Replaces text within an html string (i.e. string not contained within &lt;
   * &gt;
   *
   * @param s
   *          the string
   * @param urlPattern
   *          the regular expression to use to test if a string segment matches
   *          what you want to replace
   * @param replacement
   *          the replacement string
   * @param escape
   *          true to escape the special html characters; false otherwise
   * @param tag
   *          optional html tag to surround the text with (e.g. 'html' or 'div')
   * @return the replaced string
   *
   */
  public static String htmlReplace(String s, Pattern urlPattern, String replacement, boolean escape, String tag) {
    CharArray   ca = perThreadCharArray.get();
    CharScanner sc = perThreadScanner.get();
    int         i  = 0;
    int         len;

    ca._length = 0;

    try {
      if (escape) {
        XMLUtils.escape(s, true, ca);
        sc.reset(ca, true);
      } else {
        sc.reset(s);
      }

      char    a[] = sc.getContent();
      Segment seg = new Segment(a, 0, a.length);

      ca._length = 0;

      if (tag != null) {
        ca.append('<').append(tag).append('>');
      }

      while(i != -1) {
        i = sc.indexOf('<');

        if (i == -1) {
          len = a.length - seg.offset;
        } else {
          len = i - seg.offset;
        }

        sc.consume(len + 1);
        seg.count = len;

        if (len > 0) {
          ca.append(urlPattern.matcher(seg).replaceAll(replacement));
        }

        if (i == -1) {
          break;
        }

        seg.offset = i;
        i          = sc.indexOf('>');

        if (i == -1) {
          len = a.length - seg.offset;
        } else {
          len = i - seg.offset + 1;
        }

        sc.consume(len);
        ca.append(a, seg.offset, len);
        seg.offset += len;
      }

      if (tag != null) {
        ca.append("</").append(tag).append('>');
      }

      return ca.toString();
    } catch(Exception e) {
      return s;
    } finally {
      sc.clear();
    }
  }

  /**
   * Wraps the specified string so that each line is no longer than the
   * specified width
   *
   * @param s
   *          the string to wrap
   * @param width
   *          the maximum line width
   * @param html_tag
   *          true to add the surround the text with the html tags
   * @return the wrapped string
   */
  public static String htmlWordWrap(String s, final int width, final boolean html_tag) {
    StringBuilder buf       = new StringBuilder(s.length() + 20);
    int           lastspace = -1;
    int           linestart = 0;
    int           i         = 0;
    final int     brlen     = BR.length;

    if (html_tag) {
      buf.append("<html>");
      i += 7;
    }

    buf.append(s);

    while(i < buf.length()) {
      if (buf.charAt(i) == ' ') {
        lastspace = i;
      }

      if (buf.charAt(i) == '\n') {
        buf.setCharAt(i, BR[0]);
        buf.insert(i + 1, BR, 1, brlen - 1);
        linestart = i + brlen;
        lastspace = -1;
      }

      if (i > linestart + width - 1) {
        if (lastspace != -1) {
          buf.setCharAt(lastspace, BR[0]);
          buf.insert(lastspace + 1, BR, 1, brlen - 1);
          linestart = lastspace + brlen;
          lastspace = -1;
        } else {
          buf.setCharAt(i, BR[0]);
          buf.insert(i + 1, BR, 1, brlen - 1);
          linestart = i + brlen;
        }
      }

      i++;
    }

    if (html_tag) {
      buf.append("</html>");
    }

    return buf.toString();
  }

  /**
   * Returns the integer representation of the specified object. String's that
   * don't start with a numeric sequence always return zero
   *
   * @param o
   *          the object
   * @return the integer representing the object
   */
  public static int intValue(Object o) {
    if (o == null) {
      return 0;
    }

    if (o instanceof String) {
      return SNumber.intValue((String) o);
    }

    if (o instanceof Number) {
      return ((Number) o).intValue();
    }

    return SNumber.intValue(o.toString());
  }

  /**
   * Returns a string representing the specified array of objects
   *
   * @param list
   *          the object array
   * @param sep
   *          the item separator
   *
   * @return the string
   */
  public static String join(List list, String sep) {
    return Helper.toString(list, sep);
  }

  /**
   * Returns a string representing the specified array of objects
   *
   * @param array
   *          the object array
   * @param sep
   *          the item separator
   *
   * @return the string
   */
  public static String join(Object[] array, String sep) {
    return Helper.toString(array, sep);
  }

  /**
   * Returns the length of tokenized segments in the specified string
   *
   * @param val
   *          the string
   * @param tok
   *          the token
   *
   * @return the length of the specified string (e.g. length("a^b","^") will
   *         return 2 length("ab","^") will return 1)
   */
  public static int length(String val, String tok) {
    if (val == null) {
      return 0;
    }

    int n  = 1;
    int i  = 0;
    int tl = tok.length();

    while((i = val.indexOf(tok, i)) != -1) {
      i += tl;
      n++;
    }

    return n;
  }

  /**
   * Replaces the linefeeds in the specified string with HTML line breaks
   * (&lt;BR&gt;)
   *
   * @param s
   *          the string containing linefeeds
   *
   * @return the converted string
   */
  public static String linefeedToHTMLBreak(String s) {
    if (s == null) {
      return s;
    }

    return tokenToHTMLBreak(s, "\n", false, null, null, -1);
  }

  /**
   * Replaces the linefeeds in the specified string with HTML line breaks
   * (&lt;BR&gt;)
   *
   * @param s
   *          the string containing linefeeds
   * @param html
   *          true to enclose the string between &lt;html&gt;&lt;/html&gt; ;
   *          false otherwise
   *
   * @return the converted string
   */
  public static String linefeedToHTMLBreak(String s, boolean html) {
    if (s == null) {
      return s;
    }

    return tokenToHTMLBreak(s, "\n", html, null, null, -1);
  }

  /**
   * Returns the lowercase representation of the specified string
   *
   * @param val
   *          the string
   *
   * @return the lowercase representation of the specified string
   */
  public static String lowerCase(String val) {
    return (val == null)
           ? ""
           : val.toLowerCase(Locale.getDefault());
  }

  /**
   * Processes a string looking for text patterns that match valid URLs and
   * converts them to hyperlinks
   *
   * @param s
   *          the string to hyperlinks
   * @param escape
   *          true to escape the special html characters; false otherwise
   * @param tag
   *          optional html tag to surround the text with (e.g. 'html' or 'div')
   * @return the processed string
   */
  public static String makeHyperlinks(String s, boolean escape, String tag) {
    CharArray   ca = perThreadCharArray.get();
    CharScanner sc = perThreadScanner.get();
    int         i  = 0;
    int         len;
    Pattern     pattern = urlPattern;

    try {
      sc.reset(s);

      if (escape) {
        ca._length = 0;
        XMLUtils.escape(sc.getContent(), 0, sc.getLength(), true, ca);

        if (ca._length != sc.getLength()) {
          sc.reset(ca, true);
        }
      }

      int     alen = sc.getLength();
      char    a[]  = sc.getContent();
      Segment seg  = new Segment(a, 0, alen);
      Matcher matcher;
      int     pos, end, start;

      ca._length = 0;

      if (tag != null) {
        ca.append('<').append(tag).append('>');
      }

      while(i != -1) {
        i = sc.indexOf('<');

        if (i == -1) {
          len = alen - seg.offset;
        } else {
          len = i - seg.offset;
        }

        sc.consume(len);
        seg.count = len;

        if (len > 0) {
          matcher = pattern.matcher(seg);
          end     = 0;
          pos     = seg.offset;

          while(matcher.find()) {
            start = matcher.start();
            len   = start - end;
            ca.append(a, pos, len);
            pos += len;
            end = matcher.end();
            len = end - start;

            if (CharArray.indexOf(a, pos, len, wwwPrefixChars, 0, wwwPrefixChars.length, 0) == 0) {
              ca.append("<a href=\"http://");
              ca.append(a, pos, len);
              ca.append("\">");
              ca.append(a, pos, len);
              ca.append("</a>");
            } else if ((CharArray.indexOf(a, pos, len, httpPrefixChars, 0, httpPrefixChars.length, 0) == 0)
                       || (CharArray.indexOf(a, pos, len, httpsPrefixChars, 0, httpsPrefixChars.length, 0) == 0)) {
              ca.append("<a href=\"");
              ca.append(a, pos, len);
              ca.append("\">");
              ca.append(a, pos, len);
              ca.append("</a>");
            } else {
              ca.append(a, pos, len);
            }

            pos += len;
          }

          if (pos < seg.getEndIndex()) {
            ca.append(a, pos, seg.getEndIndex() - pos);
          }
        }

        if (i == -1) {
          break;
        }

        seg.offset = i;
        i          = sc.indexOf('>');

        if (i == -1) {
          len = alen - seg.offset;
        } else {
          len = i - seg.offset + 1;
        }

        sc.consume(len);
        ca.append(a, seg.offset, len);
        seg.offset += len;
      }

      if (tag != null) {
        ca.append("</").append(tag).append('>');
      }

      return ca.toString();
    } catch(Exception e) {
      ca._length = 0;
      XMLUtils.escape(s, true, ca);

      return ca.toString();
    } finally {
      sc.clear();
    }
  }

  /**
   * Computes the MD5 hash of the specified value
   *
   * @param val
   *          the value
   *
   * @return the MD5 hash of the specified value
   */
  public static String md5(String val) {
    return md5(val, true);
  }

  /**
   * Computes the MD5 hash of the specified value
   *
   * @param val
   *          the value
   * @param base64
   *          true to return the value as a base64 string; false otherwise
   *
   * @return the MD5 hash of the specified value
   */
  public static String md5(String val, boolean base64) {
    return FunctionHelper.md5(val, base64);
  }

  /**
   * Returns a string representing the number nanoseconds of elapsed time
   *
   * @return a string representing the number nanoseconds of elapsed time
   */
  public static long nanoTime() {
    return System.nanoTime();
  }

  /**
   * Converts a date string in the default item format to a date object
   *
   * @param context
   *          the context
   * @param date
   *          the date string or object in the default item format
   * @return the date string in the default display format
   *
   * @throws java.text.ParseException
   */
  public static Date parseDateString(iWidget context, String date) throws ParseException {
    if ((date == null) || (date.length() == 0)) {
      return null;
    }

    DateConverter cvt = new DateConverter();

    return (Date) cvt.fromString(date, context.getAppContext().getDefaultDateContext());
  }

  /**
   * Converts a date string from one format to another
   *
   * @param context
   *          the context
   * @param date
   *          the date string
   * @param inputFormat
   *          the input format
   * @return the date string in the output format
   *
   * @throws java.text.ParseException
   */
  public static Date parseDateString(iWidget context, String date, String inputFormat) throws ParseException {
    SimpleDateFormatEx df = new SimpleDateFormatEx(inputFormat);

    return df.parse(date);
  }

  /**
   * Parses a JSON objects that contains rows of data items. The object contains
   * an array field "_rows" that is the array of rows and optionally an array
   * field called "_columns" if the data is tabular
   *
   * @param context
   *          the context
   * @param json
   *          the json object
   * @return a list of data items
   * @throws IOException
   */
  public static List<RenderableDataItem> parseJSONObject(iWidget context, JSONObject json, boolean tabular)
          throws IOException {
    DataItemJSONParser    p  = new DataItemJSONParser();
    DataItemParserHandler ph = new DataItemParserHandler(context);

    p.parse(context, json, ph);

    return ph.getListEx();
  }

  /**
   * Converts a date/time string in the default item format to a date object
   *
   * @param context
   *          the context
   * @param date
   *          the date string or object in the default item format
   * @return the date string in the default display format
   *
   * @throws java.text.ParseException
   */
  public static Date parseDateTimeString(iWidget context, String date) throws ParseException {
    if ((date == null) || (date.length() == 0)) {
      return null;
    }

    DateConverter cvt = new DateConverter();

    return (Date) cvt.fromString(date, context.getAppContext().getDefaultDateTimeContext());
  }

  /**
   * Parses a string for options. The options are name/value pairs separated by
   * an equal sign. Individual options are assumed to be separated by an
   * ampersand
   *
   * @param options
   *          the string of options
   * @return a map containing the name/value pairs
   */
  public static Map<String, String> parseOptionsString(String options) {
    return CharScanner.parseOptionStringEx(options, '&', true);
  }

  /**
   * Parses a string for options. The options are name/value pairs separated by
   * an equal sign
   *
   * @param options
   *          the string of options
   * @param delimiter
   *          the delimiter that separates options
   *
   * @param unquote
   *          true to unquote and decode strings
   * @return a map containing the name/value pairs
   */
  public static Map<String, String> parseOptionsString(String options, char delimiter, boolean unquote) {
    return CharScanner.parseOptionStringEx(options, delimiter, unquote);
  }

  /**
   * Returns a portion of a token delimited string
   *
   * @param val
   *          the string
   * @param tok
   *          the token
   * @return a string representing the result
   */
  public static String piece(String val, String tok) {
    return piece(val, tok, 1, 1);
  }

  /**
   * Returns a portion of a token delimited string
   *
   * @param val
   *          the string
   * @param tok
   *          the token
   * @param start
   *          the position if the starting token to retrieve
   * @return a string representing the result
   */
  public static String piece(String val, String tok, int start) {
    return piece(val, tok, start, start);
  }

  /**
   * Returns a portion of a token delimited string
   *
   * @param val
   *          the string
   * @param tok
   *          the token
   * @param start
   *          the position if the starting token to retrieve
   * @param end
   *          the position if the ending token to retrieve
   * @return a string representing the result
   */
  public static String piece(String val, String tok, int start, int end) {
    int i  = 0;
    int n  = 1;
    int oi = 0;
    int pos;
    int tl = tok.length();

    if ((end < 1) || (end < start) || (tl == 0)) {
      return "";
    }

    while((n < start) && ((i = val.indexOf(tok, i)) != -1)) {
      i += tl;
      n++;
    }

    if ((n < start) || (i == -1)) {
      return "";
    }

    oi = i;
    i  = val.indexOf(tok, i);

    if (i == -1) {
      return val.substring(oi);
    }

    if (start == end) {
      return (oi == i)
             ? ""
             : val.substring(oi, i);
    }

    pos = oi;
    i   += tl;

    while((n < end) && ((i = val.indexOf(tok, i)) != -1)) {
      n++;
      i += tl;
    }

    if (i == -1) {
      return val.substring(pos);
    }

    i -= tl;

    return (pos == i)
           ? ""
           : val.substring(pos, i);
  }

  /**
   * Prints the string representation of the specified object to standard output
   *
   * @param o
   *          the object to print
   */
  public static void print(Object... o) {
    int len = (o == null)
              ? 0
              : o.length;

    for (int i = 0; i < len; i++) {
      System.out.print(o[i]);
    }
  }

  /**
   * Prints the string representation of the specified object to standard output
   * followed by a line feed
   *
   * @param o
   *          the object to print
   */
  public static void println(Object... o) {
    int len = (o == null)
              ? 0
              : o.length;

    for (int i = 0; i < len; i++) {
      System.out.println(o[i]);
    }
  }

  /**
   * Returns the system property with the specified name
   *
   * @param name
   *          the name of the variable
   * @param def
   *          the default value if the property is undefined
   * @return the system property with the specified name
   */
  public static String property(String name, String def) {
    String o = null;

    try {
      o = System.getProperty(name, def);
    } catch(Exception e) {}

    return (o == null)
           ? def
           : o;
  }

  /**
   * Produce a string in double quotes with backslash sequences in all the right
   * places.
   *
   * @param str
   *          the String
   * @return A String correctly formatted for insertion in a JSON text.
   */
  public static String quote(String str) {
    CharArray ca = perThreadCharArray.get();

    ca._length = 0;

    return CharScanner.escape(str, true, ca).toString();
  }

  /**
   * Returns a random number
   *
   * @return the random number
   */
  public static long randomLong() {
    synchronized(functionMap) {
      if (randomGenerator == null) {
        randomGenerator = new Random(System.currentTimeMillis());
      }
    }

    long r = 0;

    synchronized(functionMap) {
      r = randomGenerator.nextLong();

      if (r < 0) {
        r *= -1;
      }
    }

    return r;
  }

  /**
   * Returns a string representing a random number that is greater than or equal
   * to zero and less than the specified value.
   *
   * @param max
   *          a number representing an upper bound for the random number
   *
   * @return the random number
   */
  public static long randomLong(long max) {
    return randomLong() % max;
  }

  /**
   * Reads the data for the specified entity
   *
   * @param context
   *          the widget context
   *
   * @param entity
   *          the URL, File, or string representing a URL from which to read
   *
   * @return the contents of the specified entity
   * @throws IOException
   */
  public static String read(iWidget context, Object entity) throws IOException {
    URL u = null;

    if (context == null) {
      context = Platform.getContextRootViewer();
    }

    if (entity instanceof URL) {
      u = (URL) entity;
    }

    if (entity instanceof File) {
      u = PlatformHelper.fileToURL(((File) entity));
    } else if (entity != null) {
      u = context.getURL(entity.toString());
    }

    if (u == null) {
      return null;
    }

    iURLConnection c = context.getAppContext().openConnection(u);

    try {
      return c.getContentAsString();
    } finally {
      if (c != null) {
        c.close();
      }
    }
  }

  /**
   * Removes all of the shared elements between the main and remove map from the
   * main map
   *
   * @param main
   *          the main map
   * @param remove
   *          the map containing the shared elements
   *
   * @return the main map
   */
  public static Map removeAll(Map main, Map remove) {
    if (remove != null) {
      Iterator it = remove.keySet().iterator();

      while(it.hasNext()) {
        main.remove(it.next());
      }
    }

    return main;
  }

  /**
   * Replaces a portion of a token delimited string with a specified value
   *
   * @param val
   *          the string
   * @param tok
   *          the token
   * @param tlen
   *          the token length
   * @param pos
   *          the starting token position to replace from
   * @param pos2
   *          the ending token position to replace to
   * @param rval
   *          the value to replace with
   * @return a string representing the result
   */
  public static String replacePiece(String val, String tok, int tlen, int pos, int pos2, String rval) {
    int           i         = 0;
    int           n         = 0;
    StringBuilder strBuffer = new StringBuilder();

    if (val == null) {
      val = "";
    }

    n = 1;

    if (tlen == 0) {
      if (pos == 1) {
        strBuffer.append(rval);
      } else {
        strBuffer.append(val);
        strBuffer.append(rval);
      }
    } else {
      while(n < pos) {
        i = val.indexOf(tok, i);

        if (i == -1) {
          break;
        }

        i += tlen;
        n++;
      }

      if (n < pos) {
        strBuffer.append(val);

        while(n < pos) {
          strBuffer.append(tok, 0, tlen);
          n++;
        }
      } else {
        if (i != 0) {
          strBuffer.append(val, 0, i);
        }
      }

      strBuffer.append(rval);

      if (i > -1) {
        pos2++;

        while(n < pos2) {
          i = val.indexOf(tok, i);

          if (i == -1) {
            break;
          }

          i += tlen;
          n++;
        }
      }

      if ((n == pos2) && (i > 0)) {
        strBuffer.append(val, i - tlen, -1);
      }
    }

    return strBuffer.toString();
  }

  /**
   * Convenience method for resolving embedded functions and attributes within
   * the specified string
   *
   * @param str
   *          the string to resolve
   *
   * @return the resolved string
   */
  public static String resolve(String str) {
    return Platform.getContextRootViewer().expandString(str, false);
  }

  /**
   * Convenience method for resolving embedded functions and attributes within
   * the specified string
   *
   * @param context
   *          the context
   * @param str
   *          the string to resolve
   *
   * @return the resolved string
   */
  public static String resolve(iWidget context, String str) {
    return context.expandString(str, false);
  }

  /**
   * Formats a string using the specified pattern and arguments
   *
   * @param context
   *          the context
   * @param resource_string
   *          the resource string representing a the printf-style pattern
   * @param args
   *          the arguments
   *
   * @return the formatted string
   */
  public static String rformat(iWidget context, String resource_string, Object... args) {
    resource_string = context.getAppContext().getResourceAsString(resource_string);

    if ((args != null) &&!(args instanceof Object[])) {
      args = new Object[] { args };
    }

    return PlatformHelper.format(resource_string, args);
  }

  /**
   * Filters the passed in list on a background thread. The resultValue will be
   * an ObjectHolder with the list as the source the filter as the type and the
   * value as either an {@link IntList}, {@link List}, or null
   *
   * @param list
   *          the list to filter
   * @param filter
   *          the filter
   * @param returnIndexes
   *          true to return indexes; false to return the found item
   * @param cb
   *          the callback to call back with the results
   * @return a handle to use to cancel the task
   *
   * @see ObjectHolder
   * @see IntList
   * @see List
   */
  public static iCancelable searchInBackground(final List list, final iFilter filter, final boolean returnIndexes,
          final iFunctionCallback cb) {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        List    itemResults  = null;
        IntList indexResults = null;
        int     len          = list.size();

        for (int i = 0; i < len; i++) {
          Object o = list.get(i);

          if (o == null) {
            return;
          }

          if (filter.passes(o, null)) {
            if (returnIndexes) {
              if (indexResults == null) {
                indexResults = new IntList();
              }

              indexResults.add(i);
            } else {
              if (itemResults == null) {
                itemResults = new ArrayList();
              }

              itemResults.add(o);
            }
          }
        }

        final Object results = returnIndexes
                               ? indexResults
                               : itemResults;

        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            cb.finished(false, new ObjectHolder(list, filter, results));
          }
        });
      }
    };

    return Platform.getAppContext().executeBackgroundTask(r);
  }

  /**
   * Returns a string representing the server's base for an application. This is
   * the url to the server (http://server:port/)
   *
   * @return a string representing the base server URL
   */
  public static String serverBase() {
    return serverBase(Platform.getContextRootViewer());
  }

  /**
   * Returns a string representing the server's base for an application. This is
   * the url to the server (http://server:port/)
   *
   * @param context
   *          the widget context
   * @return a string representing the base server URL
   */
  public static String serverBase(iWidget context) {
    URL u = context.getAppContext().getCodeBase();

    if (u == null) {
      u = context.getAppContext().getApplicationURL();
    }

    return JavaURLConnection.baseToExternalForm(u);
  }

  /**
   * Computes the SHA hash of the specified value
   *
   * @param val
   *          the value
   *
   * @return the SHA hash of the specified value as a base64 string
   */
  public static String sha1(String val) {
    return sha1(val, true);
  }

  /**
   * Computes the SHA hash of the specified value
   *
   * @param val
   *          the value
   * @param base64
   *          true to return the value as a base64 string; false otherwise
   * @return the SHA hash of the specified value
   */
  public static String sha1(byte[] val, boolean base64) {
    return FunctionHelper.sha1(val, base64);
  }

  /**
   * Computes the SHA hash of the specified value
   *
   * @param val
   *          the value
   * @param base64
   *          true to return the value as a base64 string; false otherwise
   * @return the SHA hash of the specified value
   */
  public static String sha1(String val, boolean base64) {
    return FunctionHelper.sha1(val, base64);
  }

  /**
   * Filters the passed in list on a background thread The resultValue will be
   * an {@link ObjectHolder} with the list as the source the comparator as the
   * type and the value as the original list sorted
   *
   * @param list
   *          the list to filter
   * @param comparator
   *          the comparator
   * @param cb
   *          the callback to call back with the results
   * @return a handle to use to cancel the task
   *
   * @see ObjectHolder
   */
  public static iCancelable sortInBackground(final List list, final Comparator comparator, final iFunctionCallback cb) {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        if (comparator != null) {
          Collections.sort(list, comparator);
        } else {
          Collections.sort(list);
        }

        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            cb.finished(false, new ObjectHolder(list, comparator, list));
          }
        });
      }
    };

    return Platform.getAppContext().executeBackgroundTask(r);
  }

  /**
   * Returns the string representation for an integer
   *
   * @param number
   *          the integer
   * @return the string representation
   */
  public static String stringValue(int number) {
    return StringCache.valueOf(number);
  }

  /**
   * Returns the string representation for an integer
   *
   * @param number
   *          the number
   * @return the string representation
   */
  public static String stringValue(long number) {
    return StringCache.valueOf(number);
  }

  /**
   * Returns the string representation for the specified double
   *
   * @param number
   *          the number
   * @return the string representation
   */
  public static String stringValue(double number) {
    return SNumber.toString(number);
  }
  
  /**
   * Returns the string representation for the specified object
   *
   * @param obj
   *          the object
   * @return the string representation
   */
  public static String stringValue(Object obj) {
    if (obj == null) {
      return null;
    }

    if (obj instanceof String) {
      return (String) obj;
    }

    if (obj instanceof Integer) {
      return StringCache.valueOf(((Integer) obj).intValue());
    }

    if (obj instanceof Long) {
      return StringCache.valueOf(((Long) obj).longValue());
    }

    if (obj instanceof Number) {
      return SNumber.toString(((Number) obj).doubleValue());
    }

    if ((obj instanceof Date) || (obj instanceof Calendar)) {
      try {
        return convertDateTime(Platform.getContextRootViewer(), obj, true);
      } catch(ParseException ex) {
        return obj.toString();
      }
    }

    if (obj instanceof URL) {
      return JavaURLConnection.toExternalForm((URL) obj);
    }

    return obj.toString();
  }

  /**
   * Strips the ampersand designating a mnemonic from a string
   *
   * @param text
   *          the text
   * @return the cleaned text
   */
  public static String stripMnemonic(String text) {
    return Utils.stripMnemonic(text);
  }

  /**
   * Returns a new string that is a substring of the specified string. The
   * substring begins at the specified <code>start</code> and extends to the
   * character at index <code>end - 1</code>. Thus the length of the substring
   * is <code>end-start</code>.
   *
   * @param val
   *          the string
   * @param start
   *          the beginning index, inclusive.
   * @param end
   *          the ending index, exclusive.
   * @return the specified substring.
   */
  public static String substring(String val, int start, int end) {
    if (val == null) {
      return "";
    }

    int len = val.length();

    if (end == -1) {
      end = len;
    }

    if (end > len) {
      end = len;
    }

    if ((end < 1) || (end < start) || (start > end)) {
      return "";
    }

    return val.substring(start, end);
  }

  /**
   * Returns the title case representation of the specified string
   *
   * @param val
   *          the string
   *
   * @return the title case representation of the specified string
   */
  @SuppressWarnings("resource")
  public static String titleCase(String val) {
    return (val == null)
           ? ""
           : new CharArray(val).toTitleCase().toString();
  }

  /**
   * Returns the titlecase representation of the specified string
   *
   * @param val
   *          the string
   * @param wordSeparators
   *          a string containing word separator characters
   *
   * @return the titlecase representation of the specified string
   */
  @SuppressWarnings("resource")
  public static String titleCase(String val, String wordSeparators) {
    if (wordSeparators == null) {
      return (val == null)
             ? ""
             : new CharArray(val).toTitleCase().toString();
    }

    return (val == null)
           ? ""
           : new CharArray(val).toTitleCase(wordSeparators.toCharArray()).toString();
  }

  /**
   * Returns the string representation of the specified url
   *
   * @param url
   *          the URL
   *
   * @return the string representation of the specified url
   */
  public static String toExternalForm(URL url) {
    return (url == null)
           ? null
           : JavaURLConnection.toExternalForm(url);
  }

  /**
   * Replaces the specified token in the specified string with another token
   * Also optionally adds a prefix and or suffix to the string
   *
   * @param s
   *          the string containing linefeeds
   * @param what
   *          the token to replace
   * @param with
   *          what to replace the token with
   * @param html
   *          true to enclose the string between &lt;html&gt;&lt;/html&gt; ;
   *          false otherwise
   * @param prefix
   *          optional prefix
   * @param suffix
   *          optional suffix
   * @param maxLineCount
   *          then maximum number of lines to return. If specified and there is
   *          more than maxLineCount then the string is truncated to
   *          maxLineCount and '...' is appended to the end of the string
   * @return the converted string
   */
  public static String tokenReplacement(String s, String what, String with, boolean html, String prefix, String suffix,
          int maxLineCount) {
    return Helper.tokenReplacement(s, what, with, html, prefix, suffix, maxLineCount);
  }

  /**
   * Replaces the linefeeds in the specified string with HTML line breaks
   * (&lt;BR&gt;) Also optionally adds a prefix and or suffix to the string
   *
   * @param s
   *          the string containing linefeeds
   * @param tok
   *          the token to replace
   * @param html
   *          true to enclose the string between &lt;html&gt;&lt;/html&gt; ;
   *          false otherwise then the string is truncated to maxLineCount and
   *          '...' is appended to the end of the string
   * @return the converted string
   */
  public static String tokenToHTMLBreak(String s, String tok, boolean html) {
    return Helper.tokenReplacement(s, tok, "<br/>", html, null, null, -1);
  }

  /**
   * Replaces the linefeeds in the specified string with HTML line breaks
   * (&lt;BR&gt;) Also optionally adds a prefix and or suffix to the string
   *
   * @param s
   *          the string containing linefeeds
   * @param tok
   *          the token to replace
   * @param html
   *          true to enclose the string between &lt;html&gt;&lt;/html&gt; ;
   *          false otherwise
   * @param prefix
   *          optional prefix
   * @param suffix
   *          optional suffix
   * @param maxLineCount
   *          then maximum number of lines to return. If specified and there is
   *          more than maxLineCount then the string is truncated to
   *          maxLineCount and '...' is appended to the end of the string
   * @return the converted string
   */
  public static String tokenToHTMLBreak(String s, String tok, boolean html, String prefix, String suffix,
          int maxLineCount) {
    return Helper.tokenReplacement(s, tok, "<br/>", html, prefix, suffix, maxLineCount);
  }

  /**
   * Trims leading an trailing whitespace from the specified string
   *
   * @param val
   *          the string
   *
   * @return the trimmed string
   */
  public static String trim(String val) {
    return (val == null)
           ? ""
           : val.trim();
  }

  /**
   * Cleans a string removing backslash encode sequences
   *
   * @param val
   *          the value to be cleaned
   *
   * @return the cleaned value
   */
  public static String unescape(String val) {
    return CharScanner.unescape(val);
  }

  /**
   * Cleans a quoted string, removing the quotes and backslash encode sequences
   *
   * @param val
   *          the value to be cleaned
   *
   * @return the cleaned value
   */
  public static String unescapeQuotedString(String val) {
    CharScanner sc = perThreadScanner.get();

    sc.reset(val);
    sc.unesacpe();
    sc.unquote(false);

    return sc.toString();
  }

  /**
   * Un-groups a grouped lists
   *
   * @param list
   *          the grouped list
   * @param groupings
   *          the number of column groupings
   * @param out
   *          the list to use to store the results (can be null)
   *
   * @return the un-grouped list
   */
  public static List<RenderableDataItem> ungroup(List<RenderableDataItem> list, int groupings,
          List<RenderableDataItem> out) {
    if (out == null) {
      out = createList(10);
    }

    int len = list.size();

    groupings--;

    RenderableDataItem item;

    for (int i = 0; i < len; i++) {
      item = list.get(i);

      if (groupings > 0) {
        ungroup(item.getItems(), groupings, out);
      } else {
        out.addAll(item);
      }
    }

    return out;
  }

  /**
   * Updates a color defined in the UI properties
   *
   * @param name
   *          the name of the color
   * @param value
   *          the new value
   */
  public static void updateUIColor(String name, Object value) {
    UIColorHelper.updateColor(name, value);
  }

  /**
   * Returns the lowercase representation of the specified string
   *
   * @param val
   *          the string
   *
   * @return the lowercase representation of the specified string
   */
  public static String upperCase(String val) {
    return (val == null)
           ? ""
           : val.toUpperCase(Locale.getDefault());
  }

  /**
   * Converts a string to a utf-8 encoded set of bytes and then converts those
   * bytes to an iso-8859-1 encoded string
   *
   * @param value
   *          the value to convert
   * @return the converted value
   */
  public static String utf8String(String value) {
    return Utils.utf8String(value);
  }

  /**
   * Sets whether optimizations are enabled
   *
   * @param enabled
   *          true to enable; false otherwise
   */
  public static void setOptimizationEnabled(boolean enabled) {
    PlatformHelper.setOptimizationEnabled(enabled);
  }

  /**
   * Sets the relative font size for the application
   *
   * @param size
   *          the relative size (1.0 = normal size)
   */
  public static void setRelativeFontSize(float size) {
    UIFontHelper.setRelativeFontSize(size);
  }

  /**
   * Sets the strict mode for the scripting environment
   *
   * @param strict
   *          true for strict mode; false otherwise
   */
  public static void setStrictScriptingMode(boolean strict) {
    PlatformHelper.setStrictScriptingMode(strict);
  }

  /**
   * Returns previously cached data
   *
   * @param name
   *          the name used to cache the data
   * @param asString
   *          true to return the data as a string; false to return the
   *          InputStream
   *
   * @return the cached data or null
   */
  public static Object getCachedData(String name, boolean asString) {
    try {
      File f = Platform.createCacheFile(name);

      if ((f == null) ||!f.exists()) {
        return null;
      }

      InputStream in = new FileInputStream(f);

      if (asString && (in != null)) {
        return Streams.readerToString(new InputStreamReader(in, "utf-8"));
      }

      return in;
    } catch(Exception ex) {
      return null;
    }
  }

  @Override
  public Functions getFunctions() {
    return this;
  }

  /**
   * Gets an instance of this function handler
   *
   * @return an instance of this function handler
   */
  public static iFunctionHandler getInstance() {
    if (_instance == null) {
      _instance = new Functions();
    }

    return _instance;
  }

  /**
   * Gets the last time a successful connection was made
   *
   * @return the last time a successful connection was made (in milliseconds)
   */
  public static long getLastConnectionSuccessTime() {
    return JavaURLConnection.getLastConnectionSuccessTime();
  }

  /**
   * Gets the number of lines from the specified string. The string is parsed
   * for linefeeds and the specified number of lines are returned.
   *
   * @param s
   *          the string to parse
   * @param lines
   *          the number of lines to return
   *
   * @return the starting specified number of lines from the string
   */
  public static String getLines(String s, int lines) {
    if (s == null) {
      return s;
    }

    int n = -1;

    while(lines-- > 0) {
      n = s.indexOf('\n', n + 1);

      if (n == -1) {
        break;
      }
    }

    return (n == -1)
           ? s
           : s.substring(0, n);
  }

  /**
   * Returns the location of the runtime
   *
   * @return the location of the runtime (lat,long,alt,accuracy,time) or null
   */
  public static String getLocation() {
    return FunctionHelper.getLocation();
  }

  /**
   * Gets the months of the year for the current locale
   *
   * @return the months of the year for the current locale
   */
  public static List<String> getMonths() {
    String[]     a    = new DateFormatSymbols().getMonths();
    int          len  = a.length;
    List<String> list = new FilterableList<String>(12);

    for (int i = 0; i < len; i++) {
      if ((a[i] != null) && (a[i].length() > 0)) {
        list.add(a[i]);
      }
    }

    return list;
  }

  /**
   * Gets the short names for the months of the year for the current locale
   *
   * @return the months of the year for the current locale
   */
  public static List<String> getMonthsShortNames() {
    String[]     a    = new DateFormatSymbols().getShortMonths();
    int          len  = a.length;
    List<String> list = new FilterableList<String>(12);

    for (int i = 0; i < len; i++) {
      if ((a[i] != null) && (a[i].length() > 0)) {
        list.add(a[i]);
      }
    }

    return list;
  }

  /**
   * Returns the OS
   *
   * @return the OS
   */
  public static String getOs() {
    return Platform.getOsType();
  }

  /**
   * Returns the OS version
   *
   * @return the OS version
   */
  public static String getOsVersion() {
    if (osVersion == null) {
      osVersion = SNumber.toString(Platform.getOsVersion());
    }

    return osVersion;
  }

  /**
   * Gets the density of the screen. This is the multiplier to apply when doing
   * pixel math to adjust for higher than normal screen density.
   *
   * @return the density of the screen
   */
  public static float getPixelMultipler() {
    return ScreenUtils.getPixelMultiplier();
  }

  /**
   * Returns a java preference for the specified rare application key
   *
   * @param appKey
   *          the name for the preferences. This is the unique name for the
   *          application
   * @return the preferences object
   */
  public static iPreferences getPreferences(String appKey) {
    return PlatformHelper.getPreferences(appKey);
  }

  /**
   * Gets the relative font size for the application
   *
   * @return the relative size (1.0 = normal size)
   */
  public static float getRelativeFontSize() {
    return UIFontHelper.getRelativeFontSize();
  }

  public static String getScreenOrientation() {
    return UIScreen.getOrientationName();
  }

  /**
   * Get the logical size of the screen
   *
   * @return the logical size of the screen
   */
  public static UIDimension getScreenSize() {
    return UIScreen.getScreenSize();
  }

  /**
   * Returns the UIProperties map
   *
   * @return the UIProperties map
   */
  public static UIProperties getUIDefaults() {
    return Platform.getUIDefaults();
  }

  /**
   * Gets a parameter value from a url query string
   *
   * @param url
   *          the url
   * @param param
   *          the name of the parameter
   * @return the value or null
   */
  public static String getURLQueryParameter(String url, String param) {
    if ((url == null) || (param == null)) {
      return null;
    }

    CharScanner sc = perThreadScanner.get();

    sc.reset(url);
    sc.setTokenDelimiters(urlParamsTokens);

    String s;

    try {
      while((s = sc.nextToken()) != null) {
        if (s.startsWith(param)) {
          int n = s.indexOf('=');

          if (n == param.length()) {
            return s.substring(n + 1).trim();
          }
        }
      }

      return null;
    } finally {
      sc.setTokenDelimiters(null);
    }
  }

  /**
   * Returns whether the OS is a version of Android
   *
   * @return true if the OS is a version of Android; false otherwise
   */
  public static boolean isAndroid() {
    return Platform.isAndroid();
  }

  /**
   * Returns whether the specified object is an exception. Usefully for testing
   * callback results
   *
   * @return true if the object is an exception object; false otherwise
   */
  public static boolean isException(Object o) {
    return o instanceof Throwable;
  }

  /**
   * Returns whether or not the JDK version is 6 and above.
   *
   * @return <tt>true</tt> if the application is running on JDK 6 and above,
   *         <tt>false</tt> otherwise.
   */
  public static boolean isJava6OrAbove() {
    return Platform.getJavaVersion() >= 1.6d;
  }

  /**
   * Returns whether or not the JDK version is 1.7 and above.
   *
   * @return <tt>true</tt> if the application is running on JDK 1.7 and above,
   *         <tt>false</tt> otherwise.
   */
  public static boolean isJava7OrAbove() {
    return Platform.getJavaVersion() >= 1.7d;
  }

  public static boolean isLinux() {
    return Platform.isLinux();
  }

  /**
   * Returns whether the desktop OS is a Mac OS
   *
   * @return true if the desktop OS is a Mac OS; false otherwise
   */
  public static boolean isMac() {
    return Platform.isMac();
  }

  /**
   * Gets whether optimizations are enabled
   *
   * @return true if enabled; false otherwise
   */
  public static boolean isOptimizationEnabled() {
    return PlatformHelper.isOptimizationEnabled();
  }

  /**
   * Printable character.
   *
   * @param c
   *          the character to test
   * @return true if the character is printable ;false otherwise
   */
  public static boolean isPrintableChar(char c) {
    if (Character.isJavaIdentifierStart(c)) {
      // Letters and $ _
      return true;
    }

    if (Character.isDigit(c)) {
      return true;
    }

    switch(Character.getType(c)) {
      case Character.MODIFIER_SYMBOL :
        return true;    // ` ^

      case Character.DASH_PUNCTUATION :
        return true;    // -

      case Character.MATH_SYMBOL :
        return true;    // = ~ + | < >

      case Character.OTHER_PUNCTUATION :
        return true;    // !@#%&*;':",./?

      case Character.START_PUNCTUATION :
        return true;    // ( [ {

      case Character.END_PUNCTUATION :
        return true;    // ) ] }
    }

    return false;
  }

  /**
   * Returns whether the current thread is running in the background
   *
   * @return true if the current thread is running in the background; false
   *         otherwise
   */
  public static boolean isRunningInBackground() {
    return !Platform.isUIThread();
  }

  /**
   * Returns if strict mode is enabled for the scripting environment
   *
   * @return true for strict mode; false otherwise
   */
  public static boolean isStrictScriptingMode() {
    return PlatformHelper.getStrictScriptingMode();
  }

  /**
   * Returns whether the device is primarily a touch device
   *
   * @return true if it is; false otherwise;
   */
  public static boolean isTouchDevice() {
    return Platform.isTouchDevice();
  }

  /**
   * Returns whether the device supports touch.
   *
   * @return true if it does; false otherwise;
   */
  public static boolean isTouchableDevice() {
    return Platform.isTouchableDevice();
  }

  /**
   * Returns whether the desktop OS is a version of UNIX
   *
   * @return true if the desktop OS is a version of UNIX; false otherwise
   */
  public static boolean isUnix() {
    return true;
  }

  /**
   * Returns whether the desktop OS is a version of windows
   *
   * @return true if the desktop OS is a version of windows; false otherwise
   */
  public static boolean isWindows() {
    return Platform.isWindows();
  }

  private static void checkParmLength(iWidget w, int actual, int required) {
    if (actual < required) {
      throw new IllegalArgumentException(w.getAppContext().getResourceAsString("Rare.runtime.text.missingParameter"));
    }
  }

  private static void initializeFunctionMap() {
    Integer in;

    functionMap.put("toupr", in = Integer.valueOf(FUNC_UPPER_CASE));
    functionMap.put("uppercase", in);
    functionMap.put("ucase", in);
    functionMap.put("tolwr", in = Integer.valueOf(FUNC_LOWER_CASE));
    functionMap.put("lowercase", in);
    functionMap.put("lcase", in);
    functionMap.put("sub", in = Integer.valueOf(FUNC_SUBSTRING));
    functionMap.put("substr", in);
    functionMap.put("substring", in);
    functionMap.put("len", in = Integer.valueOf(FUNC_LENGTH));
    functionMap.put("length", in);
    functionMap.put("trim", in = Integer.valueOf(FUNC_TRIM));
    functionMap.put("concat", in = Integer.valueOf(FUNC_CONCAT));
    functionMap.put("add", in = Integer.valueOf(FUNC_ADD));
    functionMap.put("chop", in = Integer.valueOf(FUNC_CHOP));
    functionMap.put("piece", in = Integer.valueOf(FUNC_PIECE));
    functionMap.put("replacepiece", in = Integer.valueOf(FUNC_REPLACE_PIECE));
    functionMap.put("sha", in = Integer.valueOf(FUNC_SHA));
    functionMap.put("md5", in = Integer.valueOf(FUNC_MD5));
    functionMap.put("random", in = Integer.valueOf(FUNC_RANDOM));
    functionMap.put("hmacsha", in = Integer.valueOf(FUNC_HMAC_SHA));
    functionMap.put("hmacmd5", in = Integer.valueOf(FUNC_HMAC_MD5));
    functionMap.put("base64", in = Integer.valueOf(FUNC_BASE64));
    functionMap.put("sound", in = Integer.valueOf(FUNC_SOUND));
    functionMap.put("bold", in = Integer.valueOf(FUNC_BOLD));
    functionMap.put("b", in);
    functionMap.put("italic", in = Integer.valueOf(FUNC_ITALIC));
    functionMap.put("i", in);
    functionMap.put("underlined", in = Integer.valueOf(FUNC_UNDERLINED));
    functionMap.put("u", in);
    functionMap.put("html", in = Integer.valueOf(FUNC_HTML));
    functionMap.put("h", in);
    functionMap.put("color", in = Integer.valueOf(FUNC_COLOR));
    functionMap.put("starttag", in = Integer.valueOf(FUNC_START_TAG));
    functionMap.put("stag", in);
    functionMap.put("endtag", in = Integer.valueOf(FUNC_END_TAG));
    functionMap.put("etag", in);
    functionMap.put("date", in = Integer.valueOf(FUNC_DATE));
    functionMap.put("datetime", in = Integer.valueOf(FUNC_DATE_TIME));
    functionMap.put("escape", in = Integer.valueOf(FUNC_ESCAPE));
    functionMap.put("currenttime", in = Integer.valueOf(FUNC_CURRENTTIME));
    functionMap.put("nanotime", in = Integer.valueOf(FUNC_NANOTIME));
    functionMap.put("property", in = Integer.valueOf(FUNC_PROPERTY));
    functionMap.put("codebase", in = Integer.valueOf(FUNC_CODEBASE));
    functionMap.put("applicationurl", in = Integer.valueOf(FUNC_APPURL));
    functionMap.put("appurl", in = Integer.valueOf(FUNC_APPURL));
    functionMap.put("resolve", in = Integer.valueOf(FUNC_RESOLVE));
    functionMap.put("serverbase", in = Integer.valueOf(FUNC_SERVERBASE));
    functionMap.put("documentbase", in = Integer.valueOf(FUNC_DOCBASE));
    functionMap.put("currentdate", in = Integer.valueOf(FUNC_CURRENTDATE));
    functionMap.put("currentTime", in = Integer.valueOf(FUNC_CURRENTTIME));
    functionMap.put("time", in = Integer.valueOf(FUNC_CURRENTTIME));
    functionMap.put("rformat", in = Integer.valueOf(FUNC_RFORMAT));
    functionMap.put("screenorientation", in = Integer.valueOf(FUNC_ORIENTATION));
    functionMap.put("location", in = Integer.valueOf(FUNC_LOCATION));
  }
}
