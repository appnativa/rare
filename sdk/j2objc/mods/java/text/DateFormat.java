/*
 * @(#)DateFormat.java   2013-01-16
 * 
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package java.text;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/*-[
#import "AppleHelper.h"
#import "APView+Component.h"
]-*/
public abstract class DateFormat extends Format {

  /**
   * FieldPosition selector for 'a' field alignment, corresponds to the
   * {@link Calendar#AM_PM} field.
   */
  public final static int AM_PM_FIELD = 14;

  /**
   * The {@code FieldPosition} selector for 'd' field alignment, corresponds
   * to the {@link Calendar#DATE} field.
   */
  public final static int DATE_FIELD = 3;

  /**
   * FieldPosition selector for 'E' field alignment, corresponds to the
   * {@link Calendar#DAY_OF_WEEK} field.
   */
  public final static int DAY_OF_WEEK_FIELD = 9;

  /**
   * FieldPosition selector for 'F' field alignment, corresponds to the
   * {@link Calendar#DAY_OF_WEEK_IN_MONTH} field.
   */
  public final static int DAY_OF_WEEK_IN_MONTH_FIELD = 11;

  /**
   * FieldPosition selector for 'D' field alignment, corresponds to the
   * {@link Calendar#DAY_OF_YEAR} field.
   */
  public final static int DAY_OF_YEAR_FIELD = 10;

  /**
   * The format style constant defining the default format style. The default
   * is MEDIUM.
   */
  public final static int DEFAULT = 2;

  /**
   * The {@code FieldPosition} selector for 'G' field alignment, corresponds
   * to the {@link Calendar#ERA} field.
   */
  public final static int ERA_FIELD = 0;

  /**
   * The format style constant defining the full style.
   */
  public final static int FULL = 0;

  /**
   * The {@code FieldPosition} selector for 'K' field alignment, corresponding to the
   * {@link Calendar#HOUR} field.
   */
  public final static int HOUR0_FIELD = 16;

  /**
   * FieldPosition selector for 'h' field alignment, corresponding to the
   * {@link Calendar#HOUR} field.
   */
  public final static int HOUR1_FIELD = 15;

  /**
   * The {@code FieldPosition} selector for 'H' field alignment, corresponds
   * to the {@link Calendar#HOUR_OF_DAY} field. {@code HOUR_OF_DAY0_FIELD} is
   * used for the zero-based 24-hour clock. For example, 23:59 + 01:00 results
   * in 00:59.
   */
  public final static int HOUR_OF_DAY0_FIELD = 5;

  /**
   * The {@code FieldPosition} selector for 'k' field alignment, corresponds
   * to the {@link Calendar#HOUR_OF_DAY} field. {@code HOUR_OF_DAY1_FIELD} is
   * used for the one-based 24-hour clock. For example, 23:59 + 01:00 results
   * in 24:59.
   */
  public final static int HOUR_OF_DAY1_FIELD = 4;

  /**
   * The format style constant defining the long style.
   */
  public final static int LONG = 1;

  /**
   * The format style constant defining the medium style.
   */
  public final static int MEDIUM = 2;

  /**
   * FieldPosition selector for 'S' field alignment, corresponds to the
   * {@link Calendar#MILLISECOND} field.
   */
  public final static int MILLISECOND_FIELD = 8;

  /**
   * FieldPosition selector for 'm' field alignment, corresponds to the
   * {@link Calendar#MINUTE} field.
   */
  public final static int MINUTE_FIELD = 6;

  /**
   * The {@code FieldPosition} selector for 'M' field alignment, corresponds
   * to the {@link Calendar#MONTH} field.
   */
  public final static int MONTH_FIELD = 2;

  /**
   * FieldPosition selector for 's' field alignment, corresponds to the
   * {@link Calendar#SECOND} field.
   */
  public final static int SECOND_FIELD = 7;

  /**
   * The format style constant defining the short style.
   */
  public final static int SHORT = 3;

  /**
   * The {@code FieldPosition} selector for 'z' field alignment, corresponds
   * to the {@link Calendar#ZONE_OFFSET} and {@link Calendar#DST_OFFSET}
   * fields.
   */
  public final static int TIMEZONE_FIELD = 17;

  /**
   * FieldPosition selector for 'W' field alignment, corresponds to the
   * {@link Calendar#WEEK_OF_MONTH} field.
   */
  public final static int WEEK_OF_MONTH_FIELD = 13;

  /**
   * FieldPosition selector for 'w' field alignment, corresponds to the
   * {@link Calendar#WEEK_OF_YEAR} field.
   */
  public final static int WEEK_OF_YEAR_FIELD = 12;

  /**
   * The {@code FieldPosition} selector for 'y' field alignment, corresponds
   * to the {@link Calendar#YEAR} field.
   */
  public final static int YEAR_FIELD = 1;
  TimeZone                timezone;
  protected Object        proxy;

  /**
   * Constructs a new instance of {@code DateFormat}.
   */
  protected DateFormat() {}

  /**
   * Returns a new instance of {@code DateFormat} with the same properties.
   *
   * @return a shallow copy of this {@code DateFormat}.
   *
   * @see java.lang.Cloneable
   */
  @Override
  public Object clone() {
    return super.clone();
  }

  /**
   *  Formats the specified date using the rules of this date format.
   *
   * @param date
   *            the date to format.
   * @return the formatted string.
   */
  public final native String format(Date date)    /*-[
    NSDate* ndate=[NSDate fromJavaDate:date];
    return [((NSDateFormatter*)proxy_) stringFromDate: ndate];
    ]-*/
  ;

  @Override
  public String format(Object object) {
    if (!(object instanceof Date)) {
      throw new UnsupportedOperationException("Not supported yet.");
    }

    return format((Date) object);
  }

  /**
   * Parses a date from the specified string using the rules of this date
   * format.
   *
   * @param source
   *            the string to parse.
   * @return the {@code Date} resulting from the parsing.
   * @throws ParseException
   *         if an error occurs during parsing.
   */
  public Date parse(String source) throws ParseException {
  	ParsePosition p=new ParsePosition(0);
  	Date date=parse(source,p);
    if (p.getIndex() == 0) {
      int ei=p.getErrorIndex();
      p=null;
      date=null;
      throw new ParseException("Unparseable date: \"" + source + "\"" ,
          ei);
    }
  	return date;
  }
  @Override
  public Object parseObject(String string) throws ParseException {
    return parse(string);
  }

  public Object parseObject(String source, ParsePosition pos) {
    return parse(source, pos);
  }
  
  public abstract Date parse(String source, ParsePosition pos);
  

  /**
   * Specifies whether or not date/time parsing shall be lenient. With lenient
   * parsing, the parser may use heuristics to interpret inputs that do not
   * precisely match this object's format. With strict parsing, inputs must
   * match this object's format.
   *
   * @param value
   *            {@code true} to set the calendar to be lenient, {@code false}
   *            otherwise.
   */
  public native void setLenient(boolean value)    /*-[
       [((NSDateFormatter*)proxy_) setLenient: value];
     ]-*/
  ;

  /**
   * Sets the time zone of the calendar used by this date format.
   *
   * @param timezone
   *            the new time zone.
   */
  public native void setTimeZone(TimeZone tz)     /*-[
        if(timezone_!=tz) {
          timezone_=tz;
          [((NSDateFormatter*)proxy_) setTimeZone:((NSTimeZone*)tz->nativeTimeZone_)];
        }
      ]-*/
  ;

  public static String getDateFormat(int dateStyle, Locale locale) {
    return getDefaultPattern(dateStyle==SHORT ? "yMd" : "yMMMMdd", locale);
  }

  /**
   * Returns a {@code DateFormat} instance for formatting and parsing dates in
   * the DEFAULT style for the default locale.
   *
   * @return the {@code DateFormat} instance for the default style and locale.
   */
  public static DateFormat getDateInstance() {
    return getDateInstance(DEFAULT);
  }

  /**
   * Returns a {@code DateFormat} instance for formatting and parsing dates in
   * the specified style for the user's default locale.
   * See "<a href="../util/Locale.html#default_locale">Be wary of the default locale</a>".
   * @param style
   *            one of SHORT, MEDIUM, LONG, FULL, or DEFAULT.
   * @return the {@code DateFormat} instance for {@code style} and the default
   *         locale.
   * @throws IllegalArgumentException
   *             if {@code style} is not one of SHORT, MEDIUM, LONG, FULL, or
   *             DEFAULT.
   */
  public static DateFormat getDateInstance(int style) {
    checkDateStyle(style);

    return getDateInstance(style, Locale.getDefault());
  }

  /**
   * Returns a {@code DateFormat} instance for formatting and parsing dates in
   * the specified style for the specified locale.
   *
   * @param style
   *            one of SHORT, MEDIUM, LONG, FULL, or DEFAULT.
   * @param locale
   *            the locale.
   * @throws IllegalArgumentException
   *             if {@code style} is not one of SHORT, MEDIUM, LONG, FULL, or
   *             DEFAULT.
   * @return the {@code DateFormat} instance for {@code style} and
   *         {@code locale}.
   */
  public static DateFormat getDateInstance(int style, Locale locale) {
    checkDateStyle(style);

    return new SimpleDateFormat(getDateFormat(style, locale), locale);
  }

  /**
   * Returns a {@code DateFormat} instance for formatting and parsing dates
   * and time values in the DEFAULT style for the default locale.
   *
   * @return the {@code DateFormat} instance for the default style and locale.
   */
  public static DateFormat getDateTimeInstance() {
    return getDateTimeInstance(DEFAULT, DEFAULT);
  }

  /**
   * Returns a {@code DateFormat} instance for formatting and parsing of both
   * dates and time values in the manner appropriate for the user's default locale.
   * See "<a href="../util/Locale.html#default_locale">Be wary of the default locale</a>".
   * @param dateStyle
   *            one of SHORT, MEDIUM, LONG, FULL, or DEFAULT.
   * @param timeStyle
   *            one of SHORT, MEDIUM, LONG, FULL, or DEFAULT.
   * @return the {@code DateFormat} instance for {@code dateStyle},
   *         {@code timeStyle} and the default locale.
   * @throws IllegalArgumentException
   *             if {@code dateStyle} or {@code timeStyle} is not one of
   *             SHORT, MEDIUM, LONG, FULL, or DEFAULT.
   */
  public static DateFormat getDateTimeInstance(int dateStyle, int timeStyle) {
    checkTimeStyle(timeStyle);
    checkDateStyle(dateStyle);

    return getDateTimeInstance(dateStyle, timeStyle, Locale.getDefault());
  }

  /**
   * Returns a {@code DateFormat} instance for formatting and parsing dates
   * and time values in the specified styles for the specified locale.
   *
   * @param dateStyle
   *            one of SHORT, MEDIUM, LONG, FULL, or DEFAULT.
   * @param timeStyle
   *            one of SHORT, MEDIUM, LONG, FULL, or DEFAULT.
   * @param locale
   *            the locale.
   * @return the {@code DateFormat} instance for {@code dateStyle},
   *         {@code timeStyle} and {@code locale}.
   * @throws IllegalArgumentException
   *             if {@code dateStyle} or {@code timeStyle} is not one of
   *             SHORT, MEDIUM, LONG, FULL, or DEFAULT.
   */
  public static DateFormat getDateTimeInstance(int dateStyle, int timeStyle, Locale locale) {
    checkTimeStyle(timeStyle);
    checkDateStyle(dateStyle);

    String pattern = getDateFormat(dateStyle, locale) + " " + getTimeFormat(timeStyle, locale);

    return new SimpleDateFormat(pattern, locale);
  }

  /**
   * Returns a {@code DateFormat} instance for formatting and parsing dates
   * and times in the SHORT style for the default locale.
   *
   * @return the {@code DateFormat} instance for the SHORT style and default
   *         locale.
   */
  public static DateFormat getInstance() {
    return getDateTimeInstance(SHORT, SHORT);
  }

  
  public static String getTimeFormat(int timeStyle, Locale locale) {
    return getDefaultPattern(timeStyle==SHORT ? "hmma" : "hmmssa", locale);
  }
  
  /**
   * Returns a {@code DateFormat} instance for formatting and parsing time
   * values in the DEFAULT style for the default locale.
   *
   * @return the {@code DateFormat} instance for the default style and locale.
   */
  public static DateFormat getTimeInstance() {
    return getTimeInstance(DEFAULT);
  }

  /**
   * Returns a {@code DateFormat} instance for formatting and parsing time
   * values in the specified style for the user's default locale.
   * See "<a href="../util/Locale.html#default_locale">Be wary of the default locale</a>".
   * @param style
   *            one of SHORT, MEDIUM, LONG, FULL, or DEFAULT.
   * @return the {@code DateFormat} instance for {@code style} and the default
   *         locale.
   * @throws IllegalArgumentException
   *             if {@code style} is not one of SHORT, MEDIUM, LONG, FULL, or
   *             DEFAULT.
   */
  public static DateFormat getTimeInstance(int style) {
    checkTimeStyle(style);

    return getTimeInstance(style, Locale.getDefault());
  }

  /**
   * Returns a {@code DateFormat} instance for formatting and parsing time
   * values in the specified style for the specified locale.
   *
   * @param style
   *            one of SHORT, MEDIUM, LONG, FULL, or DEFAULT.
   * @param locale
   *            the locale.
   * @throws IllegalArgumentException
   *             if {@code style} is not one of SHORT, MEDIUM, LONG, FULL, or
   *             DEFAULT.
   * @return the {@code DateFormat} instance for {@code style} and
   *         {@code locale}.
   */
  public static DateFormat getTimeInstance(int style, Locale locale) {
    checkTimeStyle(style);

    return new SimpleDateFormat(getTimeFormat(style, locale), locale);
  }

  /**
   * Returns the time zone of this date format's calendar.
   *
   * @return the time zone of the calendar used by this date format.
   */
  public native TimeZone getTimeZone()    /*-[
         if(timezone_==nil) {
           timezone_=[[JavaUtilTimeZone alloc] initWithId: [((NSDateFormatter*)proxy_) timeZone]];
         }
         return timezone_;
     ]-*/
  ;

  /**
   * Indicates whether the calendar used by this date format is lenient.
   *
   * @return {@code true} if the calendar is lenient; {@code false} otherwise.
   */
  public native boolean isLenient()    /*-[
         return [((NSDateFormatter*)proxy_) isLenient];
     ]-*/
  ;

  private static void checkDateStyle(int style) {
    if (!((style == SHORT) || (style == MEDIUM) || (style == LONG) || (style == FULL) || (style == DEFAULT))) {
      throw new IllegalArgumentException("Illegal date style " + style);
    }
  }

  private static void checkTimeStyle(int style) {
    if (!((style == SHORT) || (style == MEDIUM) || (style == LONG) || (style == FULL) || (style == DEFAULT))) {
      throw new IllegalArgumentException("Illegal time style " + style);
    }
  }

  private static native String getDefaultPattern(String components, Locale locale)    /*-[
        NSLocale* l=[AppleHelper toNSLocale:locale];
        return [NSDateFormatter dateFormatFromTemplate:components options:0 locale:l];
      ]-*/
  ;
}
