/*
 * @(#)DateFormatSymbols.java   2013-01-14
 * 
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package java.text;

import java.util.Locale;
/*-[
#import "AppleHelper.h"
 ]-*/
/**
 * Encapsulates localized date-time formatting data, such as the names of the
 * months, the names of the days of the week, and the time zone data.
 * {@code DateFormat} and {@code SimpleDateFormat} both use
 * {@code DateFormatSymbols} to encapsulate this information.
 *
 * <p>Typically you shouldn't use {@code DateFormatSymbols} directly. Rather, you
 * are encouraged to create a date/time formatter with the {@code DateFormat}
 * class's factory methods: {@code getTimeInstance}, {@code getDateInstance},
 * or {@code getDateTimeInstance}. These methods automatically create a
 * {@code DateFormatSymbols} for the formatter so that you don't have to. After
 * the formatter is created, you may modify its format pattern using the
 * {@code setPattern} method. For more information about creating formatters
 * using {@code DateFormat}'s factory methods, see {@link DateFormat}.
 *
 * <p>Direct use of {@code DateFormatSymbols} is likely to be less efficient
 * because the implementation cannot make assumptions about user-supplied/user-modifiable data
 * to the same extent that it can with its own built-in data.
 *
 * @see DateFormat
 * @see SimpleDateFormat
 */
public class DateFormatSymbols implements Cloneable {
  Locale locale;
  Object proxy;

  /**
   * Constructs a new {@code DateFormatSymbols} instance containing the
   * symbols for the user's default locale.
   * See "<a href="../util/Locale.html#default_locale">Be wary of the default locale</a>".
   */
  public DateFormatSymbols() {
    this(Locale.getDefault());
  }

  /**
   * Constructs a new {@code DateFormatSymbols} instance containing the
   * symbols for the specified locale.
   *
   * @param locale
   *            the locale.
   */
  public DateFormatSymbols(Locale locale) {
    initialize(locale);
    this.locale = locale;
  }

  public  Object clone() throws CloneNotSupportedException {
    DateFormatSymbols dfs=(DateFormatSymbols) super.clone();
    dfs.initialize(locale);
    return dfs;
  }

  /**
   * Sets the array of strings which represent AM and PM. Use the
   * {@link java.util.Calendar} constants {@code Calendar.AM} and
   * {@code Calendar.PM} as indices for the array.
   *
   * @param data
   *            the array of strings for AM and PM.
   */
  public native void setAmPmStrings(String[] data)/*-[
    NSArray* a=[AppleHelper toNSArray:data];
    [((NSDateFormatter*)proxy_) setAMSymbol: (NSString*)[a objectAtIndex: 0]];
    [((NSDateFormatter*)proxy_) setPMSymbol: (NSString*)[a objectAtIndex: 1]];
  ]-*/;

  /**
   * Sets the array of Strings which represent BC and AD. Use the
   * {@link java.util.Calendar} constants {@code GregorianCalendar.BC} and
   * {@code GregorianCalendar.AD} as indices for the array.
   *
   * @param data
   *            the array of strings for BC and AD.
   */
  public native void setEras(String[] data)/*-[
    [((NSDateFormatter*)proxy_) setEraSymbols:([AppleHelper toNSArray:data])];
  ]-*/;

  /**
   * Sets the array of strings containing the full names of the months. Use
   * the {@link java.util.Calendar} constants {@code Calendar.JANUARY} etc. as
   * indices for the array.
   *
   * @param data
   *            the array of strings.
   */
  public native void setMonths(String[] data)/*-[
    [((NSDateFormatter*)proxy_) setMonthSymbols:([AppleHelper toNSArray:data])];
  ]-*/;

  /**
   * Sets the array of strings containing the abbreviated names of the months.
   * Use the {@link java.util.Calendar} constants {@code Calendar.JANUARY}
   * etc. as indices for the array.
   *
   * @param data
   *            the array of strings.
   */
  public native void setShortMonths(String[] data) /*-[
    [((NSDateFormatter*)proxy_) setShortMonthSymbols:([AppleHelper toNSArray:data])];
  ]-*/;

  /**
   * Sets the array of strings containing the abbreviated names of the days of
   * the week. Use the {@link java.util.Calendar} constants
   * {@code Calendar.SUNDAY} etc. as indices for the array.
   *
   * @param data
   *            the array of strings.
   */
  public native void setShortWeekdays(String[] data)/*-[
    [((NSDateFormatter*)proxy_) setShortWeekdaySymbols:([AppleHelper toNSArray:data])];
  ]-*/;

  /**
   * Sets the array of strings containing the full names of the days of the
   * week. Use the {@link java.util.Calendar} constants
   * {@code Calendar.SUNDAY} etc. as indices for the array.
   *
   * @param data
   *            the array of strings.
   */
  public native void setWeekdays(String[] data)/*-[
    [((NSDateFormatter*)proxy_) setWeekdaySymbols:([AppleHelper toNSArray:data])];
  ]-*/;

  /**
   * Returns the array of strings which represent AM and PM. Use the
   * {@link java.util.Calendar} constants {@code Calendar.AM} and
   * {@code Calendar.PM} as indices for the array.
   *
   * @return an array of strings.
   */
  public String[] getAmPmStrings() {
    return new String[]{getAmString(),getPmString()};
  }

  /**
   * Returns the array of strings which represent BC and AD. Use the
   * {@link java.util.Calendar} constants {@code GregorianCalendar.BC} and
   * {@code GregorianCalendar.AD} as indices for the array.
   *
   * @return an array of strings.
   */
  public native String[] getEras()/*-[
   NSArray* a=[((NSDateFormatter*)proxy_) eraSymbols];
    return [AppleHelper toStringArray:a];
   ]-*/;

  /**
   * Returns a new {@code DateFormatSymbols} instance for the user's default locale.
   * See "<a href="../util/Locale.html#default_locale">Be wary of the default locale</a>".
   *
   * @return an instance of {@code DateFormatSymbols}
   * @since 1.6
   */
  public  DateFormatSymbols getInstance() {
    return new DateFormatSymbols();
  }

  /**
   * Returns a new {@code DateFormatSymbols} for the given locale.
   *
   * @param locale the locale
   * @return an instance of {@code DateFormatSymbols}
   * @throws NullPointerException if {@code locale == null}
   * @since 1.6
   */
  public static DateFormatSymbols getInstance(Locale locale) {
    if (locale == null) {
      throw new NullPointerException();
    }

    return new DateFormatSymbols(locale);
  }

  /**
   *  Returns the array of strings containing the full names of the months. Use
   *  the {@link java.util.Calendar} constants {@code Calendar.JANUARY} etc. as
   *  indices for the array.
   * 
   *  @return an array of strings.
   */
  public native String[] getMonths()/*-[
   NSArray* a=[((NSDateFormatter*)proxy_) monthSymbols];
    return [AppleHelper toStringArray:a];
  ]-*/;

  /**
   * Returns the array of strings containing the abbreviated names of the
   * months. Use the {@link java.util.Calendar} constants
   * {@code Calendar.JANUARY} etc. as indices for the array.
   *
   * @return an array of strings.
   */
  public native String[] getShortMonths()/*-[
   NSArray* a=[((NSDateFormatter*)proxy_) shortMonthSymbols];
    return [AppleHelper toStringArray:a];
  ]-*/;

  /**
   * Returns the array of strings containing the abbreviated names of the days
   * of the week. Use the {@link java.util.Calendar} constants
   * {@code Calendar.SUNDAY} etc. as indices for the array.
   *
   * @return an array of strings.
   */
  public native String[] getShortWeekdays()/*-[
   NSArray* a=[((NSDateFormatter*)proxy_) shortWeekdaySymbols];
    return [AppleHelper toStringArray:a];
  ]-*/;

  /**
   * Returns the array of strings containing the full names of the days of the
   * week. Use the {@link java.util.Calendar} constants
   * {@code Calendar.SUNDAY} etc. as indices for the array.
   *
   * @return an array of strings.
   */
  public native String[] getWeekdays()
  /*-[
   NSArray* a=[((NSDateFormatter*)proxy_) weekdaySymbols];
    return [AppleHelper toStringArray:a];
  ]-*/;

  private native void initialize(Locale locale)
  /*-[
    proxy_= [[NSDateFormatter alloc] init];
    if(locale!=nil) {
      [((NSDateFormatter*)proxy_) setLocale:([AppleHelper toNSLocale: locale])];
    }
  ]-*/;
  private native String getAmString()/*-[
   return [((NSDateFormatter*)proxy_) AMSymbol];
  ]-*/;
  private native String getPmString()/*-[
   return [((NSDateFormatter*)proxy_) PMSymbol];
  ]-*/;
}
