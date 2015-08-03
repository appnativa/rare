/*
 * @(#)DateUtils.java
 * 
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
 */

package java.net;

/*
* ====================================================================
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
* ====================================================================
*
* This software consists of voluntary contributions made by many
* individuals on behalf of the Apache Software Foundation.  For more
* information on the Apache Software Foundation, please see
* <http://www.apache.org/>.
*
 */

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A utility class for parsing and formatting HTTP dates as used in cookies and
 * other headers.  This class handles dates as defined by RFC 2616 section
 * 3.3.1 as well as some other common non-standard formats.
 *
 *
 * @since 4.0
 */
public final class HTTPDateUtils {

  /**
   * Date format pattern used to parse HTTP date headers in ANSI C
   * <code>asctime()</code> format.
   */
  public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";

  /**
   * Date format pattern used to parse HTTP date headers in RFC 1036 format.
   */
  public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";

  /**
   * Date format pattern used to parse HTTP date headers in RFC 1123 format.
   */
  public static final String    PATTERN_RFC1123  = "EEE, dd MMM yyyy HH:mm:ss zzz";
  private static final String[] DEFAULT_PATTERNS = new String[] { PATTERN_RFC1036, PATTERN_RFC1123, PATTERN_ASCTIME };
  public static final TimeZone  GMT              = TimeZone.getTimeZone("GMT");
  private static final Date     DEFAULT_TWO_DIGIT_YEAR_START;

  static {
    Calendar calendar = Calendar.getInstance();

    calendar.setTimeZone(GMT);
    calendar.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    DEFAULT_TWO_DIGIT_YEAR_START = calendar.getTime();
  }

  /** This class should not be instantiated. */
  private HTTPDateUtils() {}

  /**
   * Formats the given date according to the RFC 1123 pattern.
   *
   * @param date The date to format.
   * @return An RFC 1123 formatted date string.
   *
   * @see #PATTERN_RFC1123
   */
  public static String formatDate(Date date) {
    return formatDate(date, PATTERN_RFC1123);
  }

  /**
   * Formats the given date according to the specified pattern.  The pattern
   * must conform to that used by the {@link SimpleDateFormat simple date
   * format} class.
   *
   * @param date The date to format.
   * @param pattern The pattern to use for formatting the date.
   * @return A formatted date string.
   *
   * @throws IllegalArgumentException If the given date pattern is invalid.
   *
   * @see SimpleDateFormat
   */
  public static String formatDate(Date date, String pattern) {
    if (date == null) {
      throw new IllegalArgumentException("date is null");
    }

    if (pattern == null) {
      throw new IllegalArgumentException("pattern is null");
    }

    SimpleDateFormat formatter = DateFormatHolder.formatFor(pattern);

    return formatter.format(date);
  }

  /**
   * Parses a date value.  The formats used for parsing the date value are retrieved from
   * the default http params.
   *
   * @param dateValue the date value to parse
   *
   * @return the parsed date
   *
   * @throws DateParseException if the value could not be parsed using any of the
   * supported date formats
   */
  public static Date parseDate(String dateValue) throws ParseException {
    return parseDate(dateValue, null, null);
  }

  /**
   * Parses the date value using the given date formats.
   *
   * @param dateValue the date value to parse
   * @param dateFormats the date formats to use
   *
   * @return the parsed date
   *
   * @throws DateParseException if none of the dataFormats could parse the dateValue
   */
  public static Date parseDate(final String dateValue, String[] dateFormats) throws ParseException {
    return parseDate(dateValue, dateFormats, null);
  }

  /**
   * Parses the date value using the given date formats.
   *
   * @param dateValue the date value to parse
   * @param dateFormats the date formats to use
   * @param startDate During parsing, two digit years will be placed in the range
   * <code>startDate</code> to <code>startDate + 100 years</code>. This value may
   * be <code>null</code>. When <code>null</code> is given as a parameter, year
   * <code>2000</code> will be used.
   *
   * @return the parsed date
   *
   * @throws ParseException if none of the dataFormats could parse the dateValue
   */
  public static Date parseDate(String dateValue, String[] dateFormats, Date startDate) throws ParseException {
    if (dateValue == null) {
      throw new IllegalArgumentException("dateValue is null");
    }

    if (dateFormats == null) {
      dateFormats = DEFAULT_PATTERNS;
    }

    if (startDate == null) {
      startDate = DEFAULT_TWO_DIGIT_YEAR_START;
    }

    // trim single quotes around date if present
    // see issue #5279
    if ((dateValue.length() > 1) && dateValue.startsWith("'") && dateValue.endsWith("'")) {
      dateValue = dateValue.substring(1, (int)dateValue.length() - 1);
    }
   	ParsePosition p=new ParsePosition(0);
    for (String dateFormat : dateFormats) {
      SimpleDateFormat dateParser = DateFormatHolder.formatFor(dateFormat);

      dateParser.set2DigitYearStart(startDate);
      p.setIndex(0);
      p.setErrorIndex(-1);
      Date date=dateParser.parse(dateValue,p);
      if (p.getIndex() != 0) {
        return date;
      }
    }
    p=null;
    // we were unable to parse the date
    throw new ParseException("Unable to parse the date " + dateValue, 0);
  }

  /**
   * A factory for {@link SimpleDateFormat}s. The instances are stored in a
   * threadlocal way because SimpleDateFormat is not threadsafe as noted in
   * {@link SimpleDateFormat its javadoc}.
   *
   */
  final static class DateFormatHolder {
    private static final ThreadLocal<HashMap<String, SimpleDateFormat>> THREADLOCAL_FORMATS =
      new ThreadLocal<HashMap<String, SimpleDateFormat>>() {
      protected HashMap<String, SimpleDateFormat> initialValue() {
        return new HashMap<String, SimpleDateFormat>();
      }
    };

    /**
     * creates a {@link SimpleDateFormat} for the requested format string.
     *
     * @param pattern
     *            a non-<code>null</code> format String according to
     *            {@link SimpleDateFormat}. The format is not checked against
     *            <code>null</code> since all paths go through
     *            {@link HTTPDateUtils}.
     * @return the requested format. This simple dateformat should not be used
     *         to {@link SimpleDateFormat#applyPattern(String) apply} to a
     *         different pattern.
     */
    public static SimpleDateFormat formatFor(String pattern) {
      HashMap<String, SimpleDateFormat> formats     = THREADLOCAL_FORMATS.get();
  
      SimpleDateFormat format = formats.get(pattern);

      if (format == null) {
        format = new SimpleDateFormat(pattern, Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        formats.put(pattern, format);
      }

      return format;
    }
  }
}
