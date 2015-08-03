/*
 * @(#)SimpleDateFormat.java   2013-01-16
 * 
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package java.text;

import java.util.Date;
import java.util.Locale;
/*-[
#import "AppleHelper.h"
#import "APView+Component.h"
]-*/

public class SimpleDateFormat extends DateFormat {
  protected DateFormatSymbols format;
  protected String            pattern;

  /**
   * Constructs a new {@code SimpleDateFormat} for formatting and parsing
   * dates and times in the {@code SHORT} style for the user's default locale.
   * See "<a href="../util/Locale.html#default_locale">Be wary of the default locale</a>".
   */
  public SimpleDateFormat() {
    this(null, Locale.getDefault());
  }

  /**
   * Constructs a new {@code SimpleDateFormat} using the specified
   * non-localized pattern and the {@code DateFormatSymbols} and {@code
   * Calendar} for the user's default locale.
   * See "<a href="../util/Locale.html#default_locale">Be wary of the default locale</a>".
   *
   * @param pattern
   *            the pattern.
   * @throws NullPointerException
   *            if the pattern is {@code null}.
   * @throws IllegalArgumentException
   *            if {@code pattern} is not considered to be usable by this
   *            formatter.
   */
  public SimpleDateFormat(String pattern) {
    this(pattern, Locale.getDefault());
  }

  public SimpleDateFormat(String template, DateFormatSymbols symbols) {
    pattern = template;
    format  = symbols;
    initialize();
    if ((pattern != null) && (pattern.length() > 0)) {
      applyPattern(pattern);
    }
  }

  /**
   * Constructs a new {@code SimpleDateFormat} using the specified
   * non-localized pattern and the {@code DateFormatSymbols} and {@code
   * Calendar} for the specified locale.
   *
   * @param template
   *            the pattern.
   * @param locale
   *            the locale.
   * @throws NullPointerException
   *            if the pattern is {@code null}.
   * @throws IllegalArgumentException
   *            if the pattern is invalid.
   */
  public SimpleDateFormat(String template, Locale locale) {
    pattern = template;
    format  = new DateFormatSymbols(locale);
    initialize();

    if ((pattern != null) && (pattern.length() > 0)) {
      applyPattern(pattern);
    }
  }

  /**
   * Changes the pattern of this simple date format to the specified pattern
   * which uses localized pattern characters.
   *
   * @param template
   *            the localized pattern.
   */
  public native void applyLocalizedPattern(String template)/*-[
    [((NSDateFormatter*)proxy_) setDateFormat:template_];
  ]-*/
  ;

  /**
   * Changes the pattern of this simple date format to the specified pattern
   * which uses non-localized pattern characters.
   *
   * @param template
   *            the non-localized pattern.
   * @throws NullPointerException
   *                if the pattern is {@code null}.
   * @throws IllegalArgumentException
   *                if the pattern is invalid.
   */
  public native void applyPattern(String template)/*-[
    [((NSDateFormatter*)proxy_) setDateFormat:template_];
  ]-*/
  ;

  /**
   * Returns a new {@code SimpleDateFormat} with the same pattern and
   * properties as this simple date format.
   *
   * @return a shallow copy of this simple date format.
   * @see java.lang.Cloneable
   */
  public Object clone() {
    try {
      return new SimpleDateFormat(pattern, (DateFormatSymbols) format.clone());
    } catch(CloneNotSupportedException ex) {
      throw new InternalError("Cant clone");
    }
  }
  
 public StringBuffer format(Date date,
                            StringBuffer toAppendTo,
                            FieldPosition fieldPosition) {
   toAppendTo.append(format(date));
   return toAppendTo;
 }
 
 private native void initialize()/*-[
    proxy_=format__->proxy_;
  ]-*/
  ;
 
  public native Date parse(String source, ParsePosition pos)
  /*-[
      NSDate* date=[((NSDateFormatter*)proxy_) dateFromString:source];
      if(!date) {
          return nil;
      }
      [pos setIndexWithInt:source.length];
      return [[JavaUtilDate alloc] initWithLong: (([date timeIntervalSince1970])*1000)];
    ]-*/
  ;

  @Override
  public Object parseObject(String string) throws ParseException {
     return parse(string);
 }
 

  /**
   * Returns the pattern of this simple date format using localized pattern
   * characters.
   *
   * @return the localized pattern.
   */
  public  String toLocalizedPattern(){
    return pattern;
  }

  /**
   * Returns the pattern of this simple date format using non-localized
   * pattern characters.
   *
   * @return the non-localized pattern.
   */
  public String toPattern() {
    return pattern;
  }
  
  public Object getNSDateFormatter() {
    return proxy;
  }
  

  public native void set2DigitYearStart(Date startDate) /*-[
    [((NSDateFormatter*)proxy_) setTwoDigitStartDate:[NSDate fromJavaDate: startDate]];
  ]-*/;

}
