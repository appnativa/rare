/*
 * Copyright SparseWare Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appnativa.util;

import com.appnativa.util.xml.XMLUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

/**
 * The class provides some general-purpose utility functions and inner classes.
 *
 * @author Don DeCoteau
 * @version 2.3
 */
public final class Helper {
  public static double HALFSECOND = 0.5;
  // Gregorian Calendar adopted Oct. 15, 1582 (2299161)
  public static int JGREG = 15 + 31 * (10 + 12 * 1582);

  /** empty string value */
  public static final String emptyString = "";

  /** OS line separator */
  public static final String lineSeparator = System.getProperty("line.separator");

  /** OS line separator */
  public static final String fileSeparator = System.getProperty("file.separator");

  /** OS version */
  public static final String osVersion = System.getProperty("os.version");

  /** OS name */
  public static final String osName = System.getProperty("os.name");

  /** java version */
  public static final String javaVersion = System.getProperty("java.version");

  /** array of spaces used for padding */
  private final static char[] paddingSpace = new char[256];
  private static final double d2r          = Math.PI / 180d;

  /**
   * Compares two byte regions.
   *
   * @param ba1   the first set of bytes
   * @param pos1  the starting position within the first set bytes
   * @param len1  the first set of bytes length
   * @param ba2   the second set of bytes
   * @param pos2  the starting position within the second set bytes
   * @param len2  the second set of bytes length
   *
   * @return   the value <code>0</code> if the second set of bytes is equal to the first
   *           set of bytes; a value less than <code>0</code> if the first set of bytes is
   *           lexicographically less than the second set of bytes; and a value greater than <code>0</code>
   *           if the first set of bytes is lexicographically greater than the second set of
   *           bytes.
   */
  public static final int compareTo(byte[] ba1, int pos1, int len1, byte[] ba2, int pos2, int len2) {
    int  n = Math.min(len1, len2);
    int  i = pos1;
    int  j = pos2;
    byte c1;
    byte c2;

    if (i == j) {
      int k   = i;
      int lim = n + i;

      while(k < lim) {
        c1 = ba1[k];
        c2 = ba2[k];

        if (c1 != c2) {
          return c1 - c2;
        }

        k++;
      }
    } else {
      while(n-- != 0) {
        c1 = ba1[i++];
        c2 = ba2[j++];

        if (c1 != c2) {
          return c1 - c2;
        }
      }
    }

    return len1 - len2;
  }

  /**
   * Compares two character regions.
   *
   * @param ba1   the first set of characters
   * @param pos1  the starting position within the first set characters
   * @param len1  the first set of characters length
   * @param ba2   the second set of characters
   * @param pos2  the starting position within the second set characters
   * @param len2  the second set of characters length
   *
   * @return   the value <code>0</code> if the second set of characters is equal to the first
   *           set of characters; a value less than <code>0</code> if the first set of characters is
   *           lexicographically less than the second set of characters; and a value greater than <code>0</code>
   *           if the first set of characters is lexicographically greater than the second set of
   *           characters.
   */
  public static final int compareTo(char[] ba1, int pos1, int len1, char[] ba2, int pos2, int len2) {
    int  n = Math.min(len1, len2);
    int  i = pos1;
    int  j = pos2;
    char c1;
    char c2;

    if (i == j) {
      int k   = i;
      int lim = n + i;

      while(k < lim) {
        c1 = ba1[k];
        c2 = ba2[k];

        if (c1 != c2) {
          return c1 - c2;
        }

        k++;
      }
    } else {
      while(n-- != 0) {
        c1 = ba1[i++];
        c2 = ba2[j++];

        if (c1 != c2) {
          return c1 - c2;
        }
      }
    }

    return len1 - len2;
  }

  // Cloning

  /**
   * Creates a Calendar object from a relative date/time specification
   * <p>
   * Examples of valid data/time specifieds are:
   * <ul>
   * <li>T    (for today)</li>
   * <li>T+1@8 (tomorrow at 8AM)</li>
   * <li>T+3@6pm (for 3 days from now at 6pm)</li>
   * <li>W+3 (for 3 weeks in the future)</li>
   * <li>M+3 (for 3 months in the future)</li>
   * <li>T   (for TODAY),  T+1 (for TOMORROW),  T+2,  T+7,  etc.</li>
   * <li>T-1 (for YESTERDAY),  T-3W (for 3 WEEKS AGO), etc.</li>
   * <li>N  (for NOW)</li>
   * <li>N+3  (for 3 minutes from now)</li>
   * <li>N+3H  (for 3 hours from now)</li>
   * </ul>
   * </p>
   *
   * @param spec the date/time specification
   *
   * @return a Calendar object representing the date/time specification
   */
  public static Calendar createCalendar(String spec) {
    int len = (spec == null)
              ? 0
              : spec.length();

    if (len == 0) {
      return null;
    }

    char     fc  = Character.toUpperCase(spec.charAt(0));
    int      n   = spec.indexOf('@');
    Calendar c   = Calendar.getInstance();
    int      num = 0;

    if (len > 1) {
      num = SNumber.intValue(spec.substring(1));
    }

    if ((fc == 'T') && (len > 3)) {
      switch(Character.toUpperCase(spec.charAt(3))) {
        case 'M' :
          fc = 'M';

          break;

        case 'W' :
          fc = 'W';

          break;

        case 'Y' :
          fc = 'Y';

          break;
      }
    }

    switch(fc) {
      case 'T' :
        c.add(Calendar.DAY_OF_MONTH, num);

        break;

      case 'M' :
        c.add(Calendar.MONTH, num);

        break;

      case 'W' :
        c.add(Calendar.WEEK_OF_YEAR, num);

        break;

      case 'Y' :
        c.add(Calendar.YEAR, num);

        break;

      case 'N' :
        if ((n == -1) && (spec.endsWith("h") || spec.endsWith("H"))) {
          c.add(Calendar.HOUR, num);
        } else {
          c.add(Calendar.MINUTE, num);
        }

        break;

      default :
        break;
    }

    if (n != -1) {
      spec = spec.substring(n + 1);

      int h = SNumber.intValue(spec);

      if (h > 1000) {
        h = h / 1000;
      } else if (h > 100) {
        h = h / 100;
      }

      int min = 0;
      int sec = 0;

      n   = spec.indexOf(':');
      len = spec.length();

      if (n != -1) {
        min = SNumber.intValue(spec.substring(n + 1));
        n   = spec.indexOf(':', n + 1);

        if (n != -1) {
          sec = SNumber.intValue(spec.substring(n + 1));
        }
      } else {
        if (len > 2) {
          min = SNumber.intValue(spec.substring(2)) % 60;
        }
      }

      if (spec.toLowerCase(Locale.ENGLISH).endsWith("pm")) {
        h += 12;

        if (h > 23) {
          h = 0;
        }
      }

      c.set(Calendar.HOUR_OF_DAY, h);
      c.set(Calendar.MINUTE, min);
      c.set(Calendar.SECOND, sec);
      c.set(Calendar.MILLISECOND, 0);
    }

    return c;
  }

  /**
   * Creates a date object from a relative date/time specification.
   * @param spec the date/time specification
   *
   * @return a date object representing the date/time specification
   *
   * @see #createCalendar(java.lang.String)
   */
  public static Date createDate(String spec) {
    Calendar c = createCalendar(spec);

    return (c == null)
           ? null
           : c.getTime();
  }

  /**
   * Calculates the number of days between start and end dates, taking
   * into consideration leap years, year boundaries etc.
   *
   * @param start the start date
   * @param end the end date, must be later than the start date
   * @return the number of days between the start and end dates
   */
  public static int daysBetween(Calendar start, Calendar end) {
    double dstart = toJulian(new int[] { start.get(Calendar.YEAR), start.get(Calendar.MONTH) + 1,
            start.get(Calendar.DATE) });
    double dend = toJulian(new int[] { end.get(Calendar.YEAR), end.get(Calendar.MONTH) + 1, end.get(Calendar.DATE) });

    return (int) (dend - dstart);
  }

  /**
   * Calculates the number of days between start and end dates, taking
   * into consideration leap years, year boundaries etc.
   *
   * @param start the start date
   * @param end the end date, must be later than the start date
   * @return the number of days between the start and end dates
   */
  public static int daysBetween(Date start, Date end) {
    Calendar c1 = Calendar.getInstance();
    Calendar c2 = Calendar.getInstance();

    c1.setTime(start);
    c2.setTime(end);

    return daysBetween(c1, c2);
  }

  /**
   * Gets the text of an exception using the getMessage() function. If
   * getMessage() returns an empty string then toString() is used to retrieve
   * the text
   *
   * @param e the exception
   *
   * @return the text associated with the exception
   */
  public static String exceptionString(Throwable e) {
    String s = e.getMessage();

    if ((s == null) || (s.length() == 0)) {
      s = e.toString();
    }

    return s;
  }

  /**
   * Expands a <code>printf</code> formatted string. The only valid format characters
   * are '<b>s</b>' and '<b>S</b>'.
   * <pre>
   *            Example: value[0]="morning"
   *                     value[1]="Don
   *                     expandString("Good %s %s", value)
   *                      will return "Good morning Don"
   * </pre>
   *
   * @param spec    is a <code>printf</code> type format specifier
   * @param values  are the values to insert
   *
   * @return   the formatted string
   */
  public static String expandString(String spec, String... values) {
    char[]        format   = spec.toCharArray();
    StringBuilder expanded = new StringBuilder(format.length + 10);
    final int     len      = format.length;
    char          c;
    int           i    = 0;
    int           n    = 0;
    int           vlen = values.length;

    while(i < len) {
      c = format[i++];

      switch(c) {
        case '%' :
          if (i == len) {
            expanded.append(c);
          } else {
            c = format[i];

            if ((c == 'S') || (c == 's')) {
              if (n < vlen) {
                expanded.append(values[n++]);
              }

              i++;
            } else {
              expanded.append('%');
            }
          }

          break;

        default :
          expanded.append(c);

          break;
      }
    }

    return expanded.toString();
  }

  public static String expandStringRepeat(String spec, String value) {
    char[]        format   = spec.toCharArray();
    StringBuilder expanded = new StringBuilder(format.length + 10);
    final int     len      = format.length;
    char          c;
    int           i = 0;

    while(i < len) {
      c = format[i++];

      switch(c) {
        case '%' :
          if (i == len) {
            expanded.append(c);
          } else {
            c = format[i];

            if ((c == 'S') || (c == 's')) {
              expanded.append(value);
              i++;
            } else {
              expanded.append('%');
            }
          }

          break;

        default :
          expanded.append(c);

          break;
      }
    }

    return expanded.toString();
  }

  /**
   * Converts a Julian day to a calendar date
   * ref :
   * Numerical Recipes in C, 2nd ed., Cambridge University Press 1992
   * http://www.rgagnon.com/javadetails/java-0506.html
   */
  public static int[] fromJulian(double injulian) {
    int    jalpha, ja, jb, jc, jd, je, year, month, day;
    double julian = injulian + HALFSECOND / 86400.0;

    ja = (int) julian;

    if (ja >= JGREG) {
      jalpha = (int) (((ja - 1867216) - 0.25) / 36524.25);
      ja     = ja + 1 + jalpha - jalpha / 4;
    }

    jb    = ja + 1524;
    jc    = (int) (6680.0 + ((jb - 2439870) - 122.1) / 365.25);
    jd    = 365 * jc + jc / 4;
    je    = (int) ((jb - jd) / 30.6001);
    day   = jb - jd - (int) (30.6001 * je);
    month = je - 1;

    if (month > 12) {
      month = month - 12;
    }

    year = jc - 4715;

    if (month > 2) {
      year--;
    }

    if (year <= 0) {
      year--;
    }

    return new int[] { year, month, day };
  }

  /**
   * Calculate haversine distance for linear distance
   *
   * @param lat1 first latitude in degrees
   * @param long1 first longitude in degrees
   * @param lat2 second latitude in degrees
   * @param long2 second longitude in degrees
   * @param kilometers true for the result in kilometers; false fro miles
   *
   * @return returns the haversine distance for linear distance
   */
  public static double haversine(double lat1, double long1, double lat2, double long2, boolean kilometers) {
    double dlong = (long2 - long1) * d2r;
    double dlat  = (lat2 - lat1) * d2r;
    double a     = Math.pow(Math.sin(dlat / 2.0), 2)
                   + Math.cos(lat1 * d2r) * Math.cos(lat2 * d2r) * Math.pow(Math.sin(dlong / 2.0), 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double d = (kilometers
                ? 6367
                : 3956) * c;

    return d;
  }

  /**
   * Searches for the first occurrence of the given object.
   *
   * @param array  the array of objects
   * @param object      the object to find
   *
   * @return   the index of the first occurrence of the object ; returns <tt>-1</tt> if the
   *           object is not found.
   */
  public static int indexOf(Object[] array, Object object) {
    if (array == null) {
      return -1;
    }

    return indexOf(array, 0, array.length, object);
  }

  /**
   * Searches for the first occurrence of the given byte.
   *
   * @param bytes  the array of bytes
   * @param pos    the starting position within the array
   * @param len    the number of bytes to search
   * @param b      the byte to find
   *
   * @return   the index of the first occurrence of the byte ; returns <tt>-1</tt> if the
   *           byte is not found.
   */
  public static int indexOf(byte[] bytes, int pos, int len, byte b) {
    if (bytes == null) {
      return -1;
    }

    len += pos;

    while(pos < len) {
      if (bytes[pos] == b) {
        return pos;
      }

      pos++;
    }

    return -1;
  }

  /**
   * Searches for the first occurrence of the given object.
   *
   * @param array  the array of objects
   * @param pos    the starting position within the array
   * @param len    the number of bytes to search
   * @param object      the object to find
   *
   * @return   the index of the first occurrence of the object ; returns <tt>-1</tt> if the
   *           object is not found.
   */
  public static int indexOf(Object[] array, int pos, int len, Object object) {
    if ((array == null) || (len == 0)) {
      return -1;
    }

    len += pos;

    while(pos < len) {
      if (array[pos] == object) {
        return pos;
      }

      pos++;
    }

    return -1;
  }

  /**
   * Searches for the first occurrence of the given object using
   * the equals() method to test for equality
   *
   * @param array  the array of objects
   * @param object      the object to find
   *
   * @return   the index of the first occurrence of the object ; returns <tt>-1</tt> if the
   *           object is not found.
   */
  public static int indexOfEquals(Object[] array, Object object) {
    if (array == null) {
      return -1;
    }

    return indexOfEquals(array, 0, array.length, object);
  }

  /**
   * Searches for the first occurrence of the given object using
   * the equals() method to test for equality
   *
   * @param array  the array of objects
   * @param pos    the starting position within the array
   * @param len    the number of bytes to search
   * @param object      the object to find
   *
   * @return   the index of the first occurrence of the object ; returns <tt>-1</tt> if the
   *           object is not found.
   */
  public static int indexOfEquals(Object[] array, int pos, int len, Object object) {
    if ((array == null) || (len == 0) || (object == null)) {
      return -1;
    }

    len += pos;

    while(pos < len) {
      if (object.equals(array[pos])) {
        return pos;
      }

      pos++;
    }

    return -1;
  }

  /**
   * Returns a list of objects representing the keys in the specified map
   *
   * @param map   the map
   * @param list  an optional list to use to store the results
   *
   * @return   the list of objects
   */
  public static List keysToObjectList(Map map, List list) {
    Iterator it = map.keySet().iterator();

    if (list == null) {
      list = new ArrayList(map.size());
    }

    while(it.hasNext()) {
      list.add(it.next());
    }

    return list;
  }

  /**
   * Returns a list of strings representing the keys in the specified map.
   *
   * @param map   the map
   * @param list  The list
   *
   * @return   the list of strings
   */
  public static List<String> keysToStringList(Map<String, ?> map, List<String> list) {
    Iterator<String> it = map.keySet().iterator();

    if (list == null) {
      list = new ArrayList<String>(map.size());
    }

    while(it.hasNext()) {
      list.add(it.next());
    }

    return list;
  }

  /**
   * Returns the index of the last occurrence of the specified byte in the specified
   * array.
   *
   * @param bytes  the array of bytes
   * @param pos    the starting position within the array
   * @param len    the number of bytes to search
   * @param b      the byte the byte to find
   *
   * @return   the index of the last occurrence of the specified byte; returns -1 if the
   *           object is not found.
   */
  public static int lastIndexOf(byte[] bytes, int pos, int len, byte b) {
    for (int i = len - 1; i >= pos; i--) {
      if (bytes[i] == b) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Replaces the linefeeds in the specified string with HTML line breaks (&lt;BR&gt;)
   *
   * @param s the string containing linefeeds
   * @param html true to enclose the string between &lt;html&gt;&lt;/html&gt; ; false otherwise
   * @return the converted string
   */
  public static String linefeedToHTMLBreak(String s, boolean html) {
    return tokenReplacement(s, "\n", "<br/>", html, null, null, -1);
  }

  /**
   * Converts an object to a <code>long</code> value.
   *
   * @param o  the object
   *
   * @return   the <code>long</code> representation of the object
   */
  public static long longFromObject(Object o) {
    if (o != null) {
      if (o instanceof Integer) {
        return ((Integer) o).longValue();
      }

      if (o instanceof Long) {
        return ((Long) o).longValue();
      }

      if (o instanceof Double) {
        return ((Double) o).longValue();
      }

      if (o instanceof Float) {
        return ((Float) o).longValue();
      }

      if (o instanceof String) {
        return (Long.parseLong((String) o));
      }

      throw new NumberFormatException("Cannot convert the specified object to a long value");
    } else {
      return 0;
    }
  }

  /**
   * Returns a string representing the values in the map
   * The string is in the format of <b>name=value&gt;cr&lt;</b>
   *
   * @param map   the map
   *
   * @return   the list of strings
   */
  public static String mapToString(Map map) {
    Iterator      it = map.entrySet().iterator();
    Map.Entry     me;
    StringBuilder sb = new StringBuilder();
    Object        val;

    while(it.hasNext()) {
      me  = (Map.Entry) it.next();
      val = me.getValue();
      sb.append(me.getKey().toString()).append('=');

      if (val != null) {
        if (val instanceof Object[]) {
          Object[] a   = (Object[]) val;
          int      len = a.length;

          sb.append('(');

          int i = 0;

          sb.append(a[i++]);

          while(i < len) {
            sb.append(',').append(a[i++]);
          }

          sb.append(')');
        } else {
          sb.append(val);
        }
      }

      sb.append(Helper.lineSeparator);
    }

    return sb.toString();
  }

  /**
   * Peals an exception and returns the original initiating exception
   *
   * @param t the exception to peal
   *
   * @return the original initiating exception
   */
  public static Throwable pealException(Throwable t) {
    Throwable c;
    int       cnt = 0;

    if (t != null) {
      while((c = t.getCause()) != null) {
        if (cnt > 100) {
          break;
        }

        t = c;
        cnt++;    // just in case someone creates an exception that forms a circular chain;
      }
    }

    return t;
  }

  /**
   * Puts the system to sleep for a specified period of time until a maximum amount
   * of time has been reached. Recursive calls to this function where the <code>starttime</code>
   * does no change will cause the value returned by this function to eventually
   * reach zero.
   * <pre>
   *              starttime = System.currentTimeMillis();
   *              sleep=1000;
   *              timeout=30000;
   *              deviceOpened=false;
   *              try {
   *                while(recursiveTimedWait(timeout, starttime, sleep)!=0) {
   *                  if(openDevice()) {
   *                    deviceOpened=true;
   *                    break;
   *                  }
   *                }
   *              }
   *              catch(InterruptedException e) {
   *              }
   *      </pre>
   *
   * @param timeout    the maximum amount of time that should elapse before the timeout is
   *                   achieved
   * @param starttime  the time that the program first initiated this timeout period
   * @param sleep      the amount of time to sleep
   *
   * @return   a integer representing the amount of time remaining.
   *
   * @throws   InterruptedException if the thread is interrupted
   */
  public static int recursiveTimedWait(int timeout, long starttime, int sleep) throws InterruptedException {
    long gone;

    if (timeout != -1) {
      gone = System.currentTimeMillis() - starttime;

      if ((timeout - gone) < 1) {
        return 0;
      }

      timeout -= gone;
      Thread.sleep(sleep);
    } else {
      Thread.sleep(sleep);
    }

    return timeout;
  }

  /**
   * Tests if two byte regions are equal.
   *
   * @param ba1   the first set of bytes
   * @param pos1  the starting position within the first set bytes
   * @param ba2   the second set of bytes
   * @param pos2  the starting position within the second set bytes
   * @param len   the number of bytes to compare
   *
   * @return   <code>true</code> if the specified sub-region of the first set of bytes
   *           matches the specified sub-region of the second set of bytes <code>false</code>
   *           otherwise.
   */
  public static final boolean regionMatches(byte[] ba1, int pos1, byte[] ba2, int pos2, int len) {
    int to = +pos1;
    int po = pos2;

    // Note: toffset, ooffset, or len might be near -1>>>1.
    if ((pos2 < 0) || (pos1 < 0) || (pos1 > ((long) ba1.length - len)) || (pos2 > ((long) ba2.length - len))) {
      return false;
    }

    while(len-- > 0) {
      byte c1 = ba1[to++];
      byte c2 = ba2[po++];

      if (c1 == c2) {
        continue;
      }

      return false;
    }

    return true;
  }

  /**
   * Removes elements from the specified array
   *
   * @param array the array
   * @param startPos the starting position
   * @param length the number of elements to remove
   * @return the shortened array
   */
  public static char[] removeElements(char[] array, int startPos, int length) {
    if ((length < 1) || (startPos < 0)) {
      return array;
    }

    if (startPos + length > array.length) {
      length = array.length - startPos;

      if (length < 1) {
        return array;
      }
    }

    int newLength = array.length - length;

    if (newLength < 0) {
      newLength = Math.max(0, array.length - startPos);
    }

    char[] copy = new char[newLength];

    if (startPos > 0) {
      System.arraycopy(array, 0, copy, 0, startPos);
    }

    System.arraycopy(array, startPos + length, copy, startPos, newLength - startPos);

    return copy;
  }

  /**
   * Removes elements from the specified array
   *
   * @param array the array
   * @param startPos the starting position
   * @param length the number of elements to remove
   * @return the shortened array
   */
  public static <T> T[] removeElements(T[] array, int startPos, int length) {
    if (length < 1) {
      return array;
    }

    if ((length < 1) || (startPos < 0)) {
      return array;
    }

    if (startPos + length > array.length) {
      length = array.length - startPos;

      if (length < 1) {
        return array;
      }
    }

    int newLength = array.length - length;

    if (newLength < 0) {
      newLength = Math.max(0, array.length - startPos);
    }

    T[] copy = ((Object) array.getClass() == (Object) Object[].class)
               ? (T[]) new Object[newLength]
               : (T[]) Array.newInstance(array.getClass().getComponentType(), newLength);

    if (startPos > 0) {
      System.arraycopy(array, 0, copy, 0, startPos);
    }

    System.arraycopy(array, startPos + length, copy, startPos, newLength - startPos);

    return copy;
  }

  /**
   * Converts an exception to a <code>RuntimeException</code> exception (if
   * necessary).
   *
   * @param e  the exception
   *
   * @return   a <code>RuntimeException</code> exception
   */
  public static final RuntimeException runtimeException(Exception e) {
    if (e instanceof RuntimeException) {
      return (RuntimeException) e;
    }

    return new RuntimeException(e);
  }

  /**
   * Returns the Julian day number that begins at noon of
   * this day, Positive year signifies A.D., negative year B.C.
   * Remember that the year after 1 B.C. was 1 A.D.
   *
   * ref :
   *  Numerical Recipes in C, 2nd ed., Cambridge University Press 1992
   *  http://www.rgagnon.com/javadetails/java-0506.html
   */
  public static double toJulian(int[] ymd) {
    int year       = ymd[0];
    int month      = ymd[1];    // jan=1, feb=2,...
    int day        = ymd[2];
    int julianYear = year;

    if (year < 0) {
      julianYear++;
    }

    int julianMonth = month;

    if (month > 2) {
      julianMonth++;
    } else {
      julianYear--;
      julianMonth += 13;
    }

    double julian = (java.lang.Math.floor(365.25 * julianYear) + java.lang.Math.floor(30.6001 * julianMonth) + day
                     + 1720995.0);

    if (day + 31 * (month + 12 * year) >= JGREG) {
      // change over to Gregorian calendar
      int ja = (int) (0.01 * julianYear);

      julian += 2 - ja + (0.25 * ja);
    }

    return java.lang.Math.floor(julian);
  }

  /**
   * Returns a string representing the specified array of integers
   *
   * @param list the integer array
   * @param bracket paren whether to surround the list with brackets
   *
   * @return the string
   */
  public static String toString(int[] list, boolean bracket) {
    if ((list == null) || (list.length == 0)) {
      return "";
    }

    StringBuilder sb = new StringBuilder();

    if (bracket) {
      sb.append('[');
    }

    sb.append(list[0]);

    for (int i = 1; i < list.length; i++) {
      sb.append(',');
      sb.append(list[i]);
    }

    if (bracket) {
      sb.append(']');
    }

    return sb.toString();
  }

  /**
   * Returns a string representing the specified array of objects
   *
   * @param list the object array
   * @param sep the item separator
   *
   * @return the string
   */
  public static String toString(List list, String sep) {
    return toString(null, list, sep).toString();
  }

  /**
   * Returns a string representing the specified array of objects
   *
   * @param list the object array
   * @param sep the item separator
   *
   * @return the string
   */
  public static String toString(Object[] list, String sep) {
    int len = (list == null)
              ? 0
              : list.length;

    if (len == 0) {
      return "";
    }

    if (len == 1) {
      return (list[0] == null)
             ? ""
             : list[0].toString();
    }

    StringBuilder sb = new StringBuilder();

    sb.append((list[0] == null)
              ? ""
              : list[0].toString());

    for (int i = 1; i < len; i++) {
      if (sep != null) {
        sb.append(sep);
      }

      sb.append(list[i]);
    }

    return sb.toString();
  }

  /**
   * Returns a string representing the date and/or time represented by the specified calendar object
   *
   * @param cal the Calendar object
   * @param dateonly return only the date component
   * @param timeonly return only the time component
   * @return the string
   */
  public static String toString(Calendar cal, boolean dateonly, boolean timeonly) {
    StringBuilder sb = new StringBuilder();
    int           y;

    if (!timeonly) {
      sb.append(cal.get(Calendar.YEAR));
      sb.append('-');
      y = (cal.get(Calendar.MONTH) + 1);

      if (y < 10) {
        sb.append('0');
      }

      sb.append(y);
      sb.append('-');
      y = cal.get(Calendar.DAY_OF_MONTH);

      if (y < 10) {
        sb.append('0');
      }

      sb.append(y);
    }

    if (dateonly) {
      return sb.toString();
    }

    sb.append('T');
    y = cal.get(Calendar.HOUR_OF_DAY);

    if (y < 10) {
      sb.append('0');
    }

    sb.append(y);
    sb.append(':');
    y = cal.get(Calendar.MINUTE);

    if (y < 10) {
      sb.append('0');
    }

    sb.append(y);
    sb.append(':');
    y = cal.get(Calendar.SECOND);

    if (y < 10) {
      sb.append('0');
    }

    sb.append(y);
    y = cal.getTimeZone().getRawOffset() / 3600000;    // 60*60*1000

    if (y == 0) {
      sb.append('Z');
    } else {
      if (y > 0) {
        sb.append('+');
      } else {
        sb.append('-');
        y *= -1;
      }

      if (y < 10) {
        sb.append('0');
      }

      sb.append(y);
      sb.append(":00");
    }

    return sb.toString();
  }

  /**
   * Returns the string representation of the specified method's signature
   *
   * @param m the methor
   * @param sig the buffer to write the signature to
   * @param prefix class name prefix to strip form parameters
   * @return the passed in buffer
   */
  public static StringBuilder toString(Method m, StringBuilder sig, String prefix) {
    String s;
    int    n;

    sig.append(m.getName() + "(");

    Class[] params = m.getParameterTypes();    // avoid clone

    for (int j = 0; j < params.length; j++) {
      s = getTypeName(params[j]);
      n = cleanClassNamePos(s, prefix);

      if (n == -1) {
        sig.append(s);
      } else {
        sig.append(s, n + 1, s.length());
      }

      if (j < (params.length - 1)) {
        sig.append(", ");
      }
    }

    sig.append(") => ");
    s = getTypeName(m.getReturnType());
    n = cleanClassNamePos(s, prefix);

    if (n == -1) {
      sig.append(s);
    } else {
      sig.append(s, n + 1, s.length());
    }

    return sig;
  }

  /**
   * Returns a string representing the specified array of objects
   *
   * @param list the object array
   * @param len the number of items in the list to use
   * @param lines true to separate the items with linefeeds; false to use tabs to separate the items
   *
   * @return the string
   */
  public static String toString(Object[] list, int len, boolean lines) {
    if (len == 0) {
      return "";
    }

    if (len == 1) {
      return list[0].toString();
    }

    StringBuilder sb = new StringBuilder();

    sb.append(list[0]);

    for (int i = 1; i < len; i++) {
      sb.append(lines
                ? "\r\n"
                : "\t");
      sb.append(list[i]);
    }

    return sb.toString();
  }

  /**
   * Returns a string builder representing the specified array of objects
   *
   * @param sb the string builder to append to (can be null)
   * @param list the object array
   * @param sep the item separator
   *
   * @return the string builder
   */
  public static StringBuilder toString(StringBuilder sb, List list, String sep) {
    int len = (list == null)
              ? 0
              : list.size();

    if (sb == null) {
      sb = new StringBuilder();
    }

    if (len == 0) {
      return sb;
    }

    Object o;

    if (len == 1) {
      o = list.get(0);

      if (o != null) {
        sb.append(o);
      }
    } else {
      o = list.get(0);

      if (o != null) {
        sb.append(o);
      }

      for (int i = 1; i < len; i++) {
        if (sep != null) {
          sb.append(sep);
        }

        o = list.get(i);
        sb.append(o);
      }
    }

    return sb;
  }

  /**
   * Replaces the specified token in the specified string with another token
   * Also optionally adds a prefix and or suffix to the string
   *
   * @param s the string containing linefeeds
   * @param what the token to replace
   * @param with what to replace the token with
   * @param html true to enclose the string between &lt;html&gt;&lt;/html&gt; ; false otherwise
   * @param prefix optional prefix
   * @param suffix optional suffix
   * @param maxLineCount then maximum number of lines to return. If specified and there is more than maxLineCount
   *                     then the string is truncated to maxLineCount and '...' is appended to the end of the string
   * @return the converted string
   */
  public static String tokenReplacement(String s, String what, String with, boolean html, String prefix, String suffix,
          int maxLineCount) {
    int n = s.indexOf(what);

    if ((n == -1) &&!html && (prefix == null) && (suffix == null)) {
      return s;
    }

    int           p = 0;
    int           nn;
    char[]        a   = s.toCharArray();
    int           tl  = what.length();
    int           len = a.length;
    char          c   = what.charAt(0);
    boolean       one = tl == 1;
    StringBuilder sb  = new StringBuilder(len);

    if (maxLineCount < 1) {
      maxLineCount = Integer.MAX_VALUE;
    }

    int lines = 0;

    if (what.startsWith("<html")) {
      html = false;
    }

    if (html) {
      sb.append("<html><body>");
    }

    CharArray ca = html
                   ? new CharArray()
                   : null;

    if (prefix != null) {
      sb.append(prefix);
    }

    while((lines < maxLineCount) && (n != -1)) {
      nn = n - p;

      if ((n > 0) && (a[n - 1] == '\r')) {
        nn--;
      }

      lines++;

      if (ca != null) {
        ca._length = 0;
        XMLUtils.encode(a, p, nn, ca);
        sb.append(ca.A, 0, ca._length);
      } else {
        sb.append(a, p, nn);
      }

      sb.append(with);
      p = n + tl;

      if (p >= len) {
        break;
      }

      n = one
          ? s.indexOf(what, p)
          : s.indexOf(c, p);
    }

    n = len - p;

    if ((lines < maxLineCount) && (n > 0)) {
      if (ca != null) {
        ca._length = 0;
        XMLUtils.encode(a, p, n, ca);
        sb.append(ca.A, 0, ca._length);
      } else {
        sb.append(a, p, n);
      }
    }

    if ((lines == maxLineCount) && (n > 0)) {
      sb.append("...");
    }

    if (suffix != null) {
      sb.append(suffix);
    }

    if (html) {
      sb.append("</body></html>");
    }

    return sb.toString();
  }

  public static boolean valuesEquals(Map map1, Map map2) {
    if ((map1 == null) || (map2 == null)) {
      return map1 == map2;
    }

    if (map1.size() != map2.size()) {
      return false;
    }

    Entry           e;
    Object          v1, v2;
    Object          k;
    Iterator<Entry> it = map1.entrySet().iterator();

    while(it.hasNext()) {
      e  = it.next();
      k  = e.getKey();
      v1 = e.getValue();
      v2 = map2.get(k);

      if (v1 == null) {
        if (v2 != null) {
          return false;
        }
      } else {
        if (!v1.equals(v2)) {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * Returns a list of objects representing the values in the specified map.
   *
   * @param map   the map
   * @param list  an optional list to use to store the results
   *
   * @return   the list of objects
   */
  public static List valuesToList(Map map, List list) {
    Iterator it = map.values().iterator();

    if (list == null) {
      list = new ArrayList(map.size());
    }

    while(it.hasNext()) {
      list.add(it.next());
    }

    return list;
  }

  /**
   * Returns a list of strings representing the keys in the specified map.
   *
   * @param map   the map
   * @param list  The list
   *
   * @return   the list of strings
   */
  public static List<String> valuesToStringList(Map<?, String> map, List<String> list) {
    Iterator<String> it = map.values().iterator();

    if (list == null) {
      list = new ArrayList<String>(map.size());
    }

    while(it.hasNext()) {
      list.add(it.next());
    }

    return list;
  }

  /**
   * Writes space padding for formatted output. Padding is 2 spaces per depth unit.
   *
   * @param out    the buffer to output to
   * @param depth  the depth for the requested padding
   */
  public static void writePadding(StringBuilder out, int depth) {
    if (depth == 0) {
      return;
    }

    char[] padding = getPadding();
    int    len     = padding.length;

    depth *= 2;

    while(depth > len) {
      out.append(padding, 0, len);
      depth -= len;
    }

    if (depth > 0) {
      out.append(padding, 0, depth);
    }
  }

  /**
   * Writes space padding for formatted output. Padding is 2 spaces per depth unit.
   *
   * @param out    the writer to output to
   * @param depth  the depth for the requested padding
   *
   * @throws  IOException If an I/O error occurs
   */
  public static void writePadding(Writer out, int depth) throws IOException {
    if (depth == 0) {
      return;
    }

    char[] padding = getPadding();
    int    len     = padding.length;

    depth *= 2;

    while(depth > len) {
      out.write(padding, 0, len);
      depth -= len;
    }

    if (depth > 0) {
      out.write(padding, 0, depth);
    }
  }

  /**
   * Sets the value of the specified calendar object
   *
   * @param val the value to the the calendar object to
   * @param cal the calendar object
   * @param settime if the tims should be set alos
   */
  @SuppressWarnings("resource")
  public static void setDateTime(String val, Calendar cal, boolean settime) {
    int    i    = 0;
    String time = null;
    String date = null;

    cal.clear();

    int n = val.indexOf('T');

    if (n == -1) {
      n = val.indexOf(' ');
    }

    if (n != -1) {
      date = val.substring(0, n);
      time = val.substring(n + 1);
    } else {
      date = val;
    }

    int  y, m, d;
    char tok = 0;

    if (date.indexOf('-') != -1) {
      tok = '-';
    } else if (date.indexOf('/') != -1) {
      tok = '/';
    } else if (date.indexOf('.') != -1) {
      tok = '.';
    }

    if ((tok == 0) && (date.length() != 10)) {
      try {
        if (settime) {
          cal.setTime(DateFormat.getDateTimeInstance().parse(val));
        } else {
          cal.setTime(DateFormat.getDateInstance().parse(val));
        }
      } catch(Exception ex) {
        throw new FormatException("Unparseable date: " + val);
      }

      return;
    }

    char[] data = date.toCharArray();

    if (tok != 0) {
      CharScanner sc = new CharScanner(data, false);

      y = SNumber.intValue(sc.nextToken(tok));
      m = SNumber.intValue(sc.nextToken(tok));
      d = SNumber.intValue(sc.nextToken(tok));

      if (d > 31) {
        if (tok == '.') {
          n = d;
          d = y;
          y = n;
        } else {
          n = d;
          d = m;
          m = y;
          y = n;
        }
      }

      if ((y < 1) || (y > 3000)) {
        throw new FormatException("Unparseable date: " + val);
      }

      if (y < 100) {
        y += ((y < 50)
              ? 2000
              : 1900);
      }

      if ((m < 1) || (m > 12)) {
        throw new FormatException("Unparseable date: " + val);
      }

      if ((d < 1) || (d > 31)) {
        throw new FormatException("Unparseable date: " + val);
      }
    } else {
      i = 0;
      y = (int) SNumber.longValue(data, i, 4, true);
      i += 5;
      m = ((data[i] - '0') * 10) + (data[i + 1] - '0');
      i += 3;
      d = ((data[i] - '0') * 10) + (data[i + 1] - '0');
    }

    if ((time != null) && settime) {
      data = time.toCharArray();
      i    = 0;

      int h = ((data[i] - '0') * 10) + (data[i + 1] - '0');

      i += 3;

      int mm = ((data[i] - '0') * 10) + (data[i + 1] - '0');

      i += 3;

      int sec = 0;
      int mil = 0;

      if (i + 2 < data.length) {
        sec = ((data[i] - '0') * 10) + (data[i + 1] - '0');
        i   += 2;

        if ((i < data.length) && (data[i] == '.')) {    // milliseconds
          i++;
          mil = ((data[i] - '0') * 100) + ((data[i + 1] - '0') * 10) + (data[i + 2] - '0');
          i   += 3;
        }
      }

      if (i < data.length) {
        if (data[i] != 'Z') {
          StringBuilder sb = new StringBuilder(8);

          sb.append("GMT");
          sb.append(data, i, data.length - i);

          TimeZone tz = TimeZone.getTimeZone(sb.toString());

          if (tz != null) {
            cal.setTimeZone(tz);
          }
        }
      }

      cal.set(y, m - 1, d, h, mm, sec);
      cal.set(Calendar.MILLISECOND, mil);
    } else {
      cal.set(y, m - 1, d);
    }
  }

  /**
   * Sets the value of the specified calendar object
   *
   * @param val the value to the the calendar object to
   * @param cal the calendar object
   *
   */
  public static void setTime(String val, Calendar cal) {
    int    i    = 0;
    char[] data = val.toCharArray();

    cal.clear();

    if (data.length != 5) {
      throw new FormatException("Unparseable date: " + val);
    }

    try {
      i = 0;

      int h = ((data[i] - '0') * 10) + (data[i + 1] - '0');

      i += 3;

      int mm = ((data[i] - '0') * 10) + (data[i + 1] - '0');

      i += 3;

      int sec = ((data[i] - '0') * 10) + (data[i + 1] - '0');

      i += 2;

      int mil = 0;

      if ((i < data.length) && (data[i] == '.')) {    // milliseconds
        i++;
        mil = ((data[i] - '0') * 100) + ((data[i + 1] - '0') * 10) + (data[i + 2] - '0');
        i   += 3;
      }

      if (i < data.length) {
        if (data[i] != 'Z') {
          StringBuilder sb = new StringBuilder(8);

          sb.append("GMT");
          sb.append(data, i, data.length - i);

          TimeZone tz = TimeZone.getTimeZone(sb.toString());

          if (tz != null) {
            cal.setTimeZone(tz);
          }
        }
      }

      cal.set(Calendar.HOUR_OF_DAY, h);
      cal.set(Calendar.MINUTE, mm);
      cal.set(Calendar.SECOND, sec);
      cal.set(Calendar.MILLISECOND, mil);
    } catch(Exception e) {
      throw new FormatException("Unparseable date: " + val);
    }
  }

  /**
   * Returns a character set helper for the named character set.
   *
   * @param cs  the character set name
   *
   * @return   the character set helper
   *
   * @throws   UnsupportedEncodingException if the character set is not supported
   */
  public static iCharsetHelper getCharsetHelper(String cs) throws UnsupportedEncodingException {
    if (cs.equalsIgnoreCase("UTF-8")) {
      return UTF8Helper.getInstance();
    }

    if (cs.equalsIgnoreCase("UTF8")) {
      return UTF8Helper.getInstance();
    }

    if (cs.equalsIgnoreCase("ISO-8859-1")) {
      return ISO88591Helper.getInstance();
    }

    if (cs.equalsIgnoreCase("ISO88591")) {
      return ISO88591Helper.getInstance();
    }

    return new GenericCharsetHelper(cs);
  }

  /**
   * Retrieves the specified field of the given object.
   *
   * @param instanceClass the class that contains the field
   * @param fieldName the name of the field
   * @return the field
   * @throws NoSuchFieldException when the field does not exist
   */
  public static Field getField(Class instanceClass, String fieldName) throws NoSuchFieldException {
    try {
      Field field = null;

      while(field == null) {
        try {
          field = instanceClass.getDeclaredField(fieldName);
        } catch(NoSuchFieldException e) {
          instanceClass = instanceClass.getSuperclass();

          if (instanceClass == null) {
            throw e;
          }
          // System.out.println("trying parent class [" + instanceClass.getName() + "]");
        }
      }

      // field.setAccessible(true);
      return field;
    } catch(SecurityException e) {
      e.printStackTrace();

      throw new NoSuchFieldException("Unable to access field [" + fieldName + "]: " + e.toString());
    } catch(IllegalArgumentException e) {
      e.printStackTrace();

      throw new NoSuchFieldException("Unable to access field [" + fieldName + "]: " + e.toString());
    }
  }

  /**
   * Get the array of postfixes to use yo check for
   * local specific resources
   *
   * @param locale
   * @return the array of postfixes
   */
  public static String[] getLocalResourcePostfix(Locale locale) {
    if (locale == null) {
      return new String[] {};
    }

    String        p1       = null;
    String        p2       = null;
    String        p3       = null;
    StringBuilder sb       = new StringBuilder();
    String        language = locale.getLanguage();
    String        country  = locale.getCountry();
    String        variant  = locale.getVariant();

    if (!"".equals(language)) {
      sb.append("_");
      sb.append(language);
      p1 = sb.toString();

      if (!"".equals(country)) {
        sb.append("_");
        sb.append(country);
        p2 = sb.toString();

        if (!"".equals(variant)) {
          sb.append("_");
          sb.append(variant);
          p3 = sb.toString();
        }
      }
    }

    if (p3 != null) {
      return new String[] { p3, p2, p1 };
    }

    if (p2 != null) {
      return new String[] { p2, p1 };
    }

    return (p1 == null)
           ? new String[] {}
           : new String[] { p1 };
  }

  public static final char[] getPadding() {
    if (paddingSpace[0] != ' ') {
      int len = paddingSpace.length;

      for (int i = 0; i < len; i++) {
        paddingSpace[i] = ' ';
      }
    }

    return paddingSpace;
  }

  /**
   * Utility method to return a readable type name for a java type object
   * @param type the java type
   *
   * @return a readable type name for a java type object
   */
  public static String getTypeName(Class type) {
    if (type.isArray()) {
      try {
        Class cl         = type;
        int   dimensions = 0;

        while(cl.isArray()) {
          dimensions++;
          cl = cl.getComponentType();
        }

        StringBuilder sb = new StringBuilder();

        sb.append(cl.getName());

        for (int i = 0; i < dimensions; i++) {
          sb.append("[]");
        }

        return sb.toString();
      } catch(Throwable e) {    /* FALLTHRU */
      }
    }

    return type.getName();
  }

  /**
   * Returns whether the byte ordering of the native platform is BIG_ENDIAN
   * In this order, the bytes of a multibyte value are ordered from most
   * significant to least significant.
   *
   * @return <code>true</code> for BIG_ENDIAN <code>false</code>otherwise
   */
  public static boolean isBigEndian() {
    return java.nio.ByteOrder.nativeOrder().equals(java.nio.ByteOrder.BIG_ENDIAN);
  }

  /**
   * Tests whether the specified string is numeric.
   *
   * @param str       the string
   * @param tzero        <code>true</code> to allow a trailing zero after a decimal; <code>false</code>
   *                    otherwise
   * @param lzero       <code>true</code> to allow a leading zeros; <code>false</code>
   *                    otherwise
   *
   * @return   <code>true</code> if the string is numeric; <code>false</code> otherwise
   */
  public static boolean isNumeric(String str, boolean tzero, boolean lzero) {
    if ((str == null) || (str.length() == 0)) {
      return false;
    }

    char[] chars = str.toCharArray();

    return isNumeric(chars, 0, chars.length, tzero, lzero);
  }

  /**
   * Tests whether the specified string is numeric.
   *
   * @param chars       the character array
   * @param pos         the position of the array
   * @param len         the number of characters in the array to use
   * @param tzero        <code>true</code> to allow a trailing zero after a decimal; <code>false</code>
   *                    otherwise
   * @param lzero       <code>true</code> to allow a leading zeros; <code>false</code>
   *                    otherwise
   *
   * @return   <code>true</code> if the string is numeric; <code>false</code> otherwise
   */
  public static boolean isNumeric(char[] chars, int pos, int len, boolean tzero, boolean lzero) {
    boolean dot = false;

    if ((chars == null) || (len == 0)) {
      return false;
    }

    len += pos;

    char    c   = chars[pos];
    boolean neg = false;

    if (c == '+') {
      return false;
    }

    if (c == '-') {
      neg = true;
      pos++;
    }

    int p = pos + 1;

    while(pos < len) {
      c = chars[pos++];

      if ((c == '0') && (p == pos)) {
        if ((pos == len) &&!neg) {
          return true;
        }

        if (!lzero) {
          return false;
        }
      }

      if ((c < 48) || (c > 57)) {
        if ((c == '.') &&!dot) {
          dot = true;

          continue;
        }

        return false;
      }
    }

    if ((c == '.') || (c == '-')) {
      return false;
    }

    return (!tzero && dot && (c == '0'))
           ? false
           : true;
  }

  /**
   * Returns whether the two specified times are on the same day
   * @param cal1 the first time
   * @param cal2 the second time
   * @return true if the two specified times are on the same day; false otherwise
   */
  public static boolean isSameDay(Calendar cal1, Calendar cal2) {
    if (cal1.get(Calendar.DAY_OF_YEAR) != cal2.get(Calendar.DAY_OF_YEAR)) {
      return false;
    }

    if (cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR)) {
      return false;
    }

    return true;
  }

  private static int cleanClassNamePos(String name, String prefix) {
    if (name.startsWith("java.lang.")) {
      return "java.lang.".length() - 1;
    }

    if ((prefix != null) && name.startsWith(prefix)) {
      return name.lastIndexOf('.');
    }

    return -1;
  }
}
