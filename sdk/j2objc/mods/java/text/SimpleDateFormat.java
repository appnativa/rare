/*
 * @(#)SimpleDateFormat.java   2013-01-16
 *
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package java.text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static java.text.DateFormat.AM_PM_FIELD;
import static java.text.DateFormat.DATE_FIELD;
import static java.text.DateFormat.DAY_OF_WEEK_FIELD;
import static java.text.DateFormat.DAY_OF_WEEK_IN_MONTH_FIELD;
import static java.text.DateFormat.DAY_OF_YEAR_FIELD;
import static java.text.DateFormat.ERA_FIELD;
import static java.text.DateFormat.HOUR0_FIELD;
import static java.text.DateFormat.HOUR1_FIELD;
import static java.text.DateFormat.HOUR_OF_DAY0_FIELD;
import static java.text.DateFormat.HOUR_OF_DAY1_FIELD;
import static java.text.DateFormat.MILLISECOND_FIELD;
import static java.text.DateFormat.MINUTE_FIELD;
import static java.text.DateFormat.MONTH_FIELD;
import static java.text.DateFormat.SECOND_FIELD;
import static java.text.DateFormat.TIMEZONE_FIELD;
import static java.text.DateFormat.WEEK_OF_MONTH_FIELD;
import static java.text.DateFormat.WEEK_OF_YEAR_FIELD;
import static java.text.DateFormat.YEAR_FIELD;

/*-[
#import "AppleHelper.h"
#import "APView+Component.h"
]-*/
public class SimpleDateFormat extends DateFormat {
  protected DateFormatSymbols format;
  protected String            pattern;
  protected Calendar          calendar;
  protected NumberFormat      numberFormat;
  
  // 'L' and 'c' are ICU-compatible extensions for stand-alone month and stand-alone weekday.
  static final String PATTERN_CHARS = "GyMdkHmsSEDFwWahKzZLc";
  // The index of 'Z' in the PATTERN_CHARS string. This pattern character is supported by the RI,
  // but has no corresponding public constant.
  private static final int RFC_822_TIMEZONE_FIELD = 18;
  // The index of 'L' (cf. 'M') in the PATTERN_CHARS string. This is an ICU-compatible extension
  private static final int STAND_ALONE_MONTH_FIELD = 19;
  // The index of 'c' (cf. 'E') in the PATTERN_CHARS string. This is an ICU-compatible extension
  private static final int STAND_ALONE_DAY_OF_WEEK_FIELD = 20;

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
  public native void applyLocalizedPattern(String template)
  /*-[
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
  public native void applyPattern(String template)
  /*-[
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
  @Override
  public Object clone() {
    try {
      return new SimpleDateFormat(pattern, (DateFormatSymbols) format.clone());
    } catch(CloneNotSupportedException ex) {
      throw new InternalError("Cant clone");
    }
  }

  @Override
  public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
    toAppendTo.append(format(date));

    return toAppendTo;
  }

  private void initialize() {
    proxy=format.proxy;
  }
  
  @Override
  public native Date parse(String source, ParsePosition pos)
  /*-[
    NSDate* date=[((NSDateFormatter*)proxy_) dateFromString:source];
    if(!date) {
      [pos setErrorIndexWithInt:(int)source.length];
      return nil;
    }
    [pos setIndexWithInt:(int)source.length];
    return [[JavaUtilDate alloc] initWithLong: (([date timeIntervalSince1970])*1000)];
  ]-*/
  ;

  @Override
  public Object parseObject(String string) throws ParseException {
    return parse(string);
  }

  @Override
  public Object parseObject(String source, ParsePosition pos) {
    return parse(source, pos);
  }

  /**
   * Returns the pattern of this simple date format using localized pattern
   * characters.
   *
   * @return the localized pattern.
   */
  public String toLocalizedPattern() {
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

  public native void set2DigitYearStart(Date startDate)
  /*-[
    [((NSDateFormatter*)proxy_) setTwoDigitStartDate:[NSDate fromJavaDate: startDate]];
  ]-*/
  ;

  /**
   * Formats the specified object using the rules of this simple date format
   * and returns an {@code AttributedCharacterIterator} with the formatted
   * date and attributes.
   *
   * @param object
   *            the object to format.
   * @return an {@code AttributedCharacterIterator} with the formatted date
   *         and attributes.
   * @throws NullPointerException
   *            if the object is {@code null}.
   * @throws IllegalArgumentException
   *            if the object cannot be formatted by this simple date
   *            format.
   */
  @Override
  public AttributedCharacterIterator formatToCharacterIterator(Object object) {
    if (object == null) {
      throw new NullPointerException("object == null");
    }

    if (object instanceof Date) {
      return formatToCharacterIteratorImpl((Date) object);
    }

    if (object instanceof Number) {
      return formatToCharacterIteratorImpl(new Date(((Number) object).longValue()));
    }

    throw new IllegalArgumentException("Bad class: " + object.getClass());
  }

  private AttributedCharacterIterator formatToCharacterIteratorImpl(Date date) {
    StringBuffer             buffer = new StringBuffer();
    ArrayList<FieldPosition> fields = new ArrayList<FieldPosition>();

    // format the date, and find fields
    formatImpl(date, buffer, null, fields);

    // create and AttributedString with the formatted buffer
    AttributedString as = new AttributedString(buffer.toString());

    // add DateFormat field attributes to the AttributedString
    for (FieldPosition pos : fields) {
      Format.Field attribute = pos.getFieldAttribute();

      as.addAttribute(attribute, attribute, pos.getBeginIndex(), pos.getEndIndex());
    }

    // return the CharacterIterator from AttributedString
    return as.getIterator();
  }

  /**
   *     Formats the date.
   *     <p>
   *     If the FieldPosition {@code field} is not null, and the field
   *     specified by this FieldPosition is formatted, set the begin and end index
   *     of the formatted field in the FieldPosition.
   *     <p>
   *     If the list {@code fields} is not null, find fields of this
   *     date, set FieldPositions with these fields, and add them to the fields
   *     vector.
   *    
   *     @param date
   *                Date to Format
   *     @param buffer
   *                StringBuffer to store the resulting formatted String
   *     @param field
   *                FieldPosition to set begin and end index of the field
   *                specified, if it is part of the format for this date
   *     @param fields
   *                list used to store the FieldPositions for each field in this
   *                date
   *     @return the formatted Date
   *     @throws IllegalArgumentException
   *                if the object cannot be formatted by this Format.
   */
  private StringBuffer formatImpl(Date date, StringBuffer buffer, FieldPosition field, List<FieldPosition> fields) {
    boolean quote = false;
    int     next,
            last  = -1,
            count = 0;
    if(getCalendar()==null) {
      setCalendar(Calendar.getInstance());
    }
    if(getNumberFormat()==null) {
      setNumberFormat(NumberFormat.getInstance());
    }
    getCalendar().setTime(date);

    if (field != null) {
      field.setBeginIndex(0);
      field.setEndIndex(0);
    }

    final int patternLength = pattern.length();

    for (int i = 0; i < patternLength; i++) {
      next = (pattern.charAt(i));

      if (next == '\'') {
        if (count > 0) {
          append(buffer, field, fields, (char) last, count);
          count = 0;
        }

        if (last == next) {
          buffer.append('\'');
          last = -1;
        } else {
          last = next;
        }

        quote = !quote;

        continue;
      }

      if (!quote && ((last == next) || ((next >= 'a') && (next <= 'z')) || ((next >= 'A') && (next <= 'Z')))) {
        if (last == next) {
          count++;
        } else {
          if (count > 0) {
            append(buffer, field, fields, (char) last, count);
          }

          last  = next;
          count = 1;
        }
      } else {
        if (count > 0) {
          append(buffer, field, fields, (char) last, count);
          count = 0;
        }

        last = -1;
        buffer.append((char) next);
      }
    }

    if (count > 0) {
      append(buffer, field, fields, (char) last, count);
    }

    return buffer;
  }

  private void append(StringBuffer buffer, FieldPosition position, List<FieldPosition> fields, char format, int count) {
    int field = -1;
    int index = PATTERN_CHARS.indexOf(format);

    if (index == -1) {
      throw new IllegalArgumentException("Unknown pattern character '" + format + "'");
    }

    int   beginPosition   = buffer.length();
    Field dateFormatField = null;

    switch(index) {
      case ERA_FIELD :
        dateFormatField = Field.ERA;
        buffer.append(this.format.getEras()[getCalendar().get(Calendar.ERA)]);

        break;

      case YEAR_FIELD :
        dateFormatField = Field.YEAR;

        int year = getCalendar().get(Calendar.YEAR);

        /*
         * For 'y' and 'yyy', we're consistent with Unicode and previous releases
         * of Android. But this means we're inconsistent with the RI.
         *     http://unicode.org/reports/tr35
         */
        if (count == 2) {
          appendNumber(buffer, 2, year % 100);
        } else {
          appendNumber(buffer, count, year);
        }

        break;

      case STAND_ALONE_MONTH_FIELD :    // 'L'
        dateFormatField = Field.MONTH;
        appendMonth(buffer, count, true);

        break;

      case MONTH_FIELD :                // 'M'
        dateFormatField = Field.MONTH;
        appendMonth(buffer, count, false);

        break;

      case DATE_FIELD :
        dateFormatField = Field.DAY_OF_MONTH;
        field           = Calendar.DATE;

        break;

      case HOUR_OF_DAY1_FIELD :         // 'k'
        dateFormatField = Field.HOUR_OF_DAY1;

        int hour = getCalendar().get(Calendar.HOUR_OF_DAY);

        appendNumber(buffer, count, (hour == 0)
                                    ? 24
                                    : hour);

        break;

      case HOUR_OF_DAY0_FIELD :         // 'H'
        dateFormatField = Field.HOUR_OF_DAY0;
        field           = Calendar.HOUR_OF_DAY;

        break;

      case MINUTE_FIELD :
        dateFormatField = Field.MINUTE;
        field           = Calendar.MINUTE;

        break;

      case SECOND_FIELD :
        dateFormatField = Field.SECOND;
        field           = Calendar.SECOND;

        break;

      case MILLISECOND_FIELD :
        dateFormatField = Field.MILLISECOND;

        int value = getCalendar().get(Calendar.MILLISECOND);

        appendNumber(buffer, count, value);

        break;

      case STAND_ALONE_DAY_OF_WEEK_FIELD :
        dateFormatField = Field.DAY_OF_WEEK;
        appendDayOfWeek(buffer, count, true);

        break;

      case DAY_OF_WEEK_FIELD :
        dateFormatField = Field.DAY_OF_WEEK;
        appendDayOfWeek(buffer, count, false);

        break;

      case DAY_OF_YEAR_FIELD :
        dateFormatField = Field.DAY_OF_YEAR;
        field           = Calendar.DAY_OF_YEAR;

        break;

      case DAY_OF_WEEK_IN_MONTH_FIELD :
        dateFormatField = Field.DAY_OF_WEEK_IN_MONTH;
        field           = Calendar.DAY_OF_WEEK_IN_MONTH;

        break;

      case WEEK_OF_YEAR_FIELD :
        dateFormatField = Field.WEEK_OF_YEAR;
        field           = Calendar.WEEK_OF_YEAR;

        break;

      case WEEK_OF_MONTH_FIELD :
        dateFormatField = Field.WEEK_OF_MONTH;
        field           = Calendar.WEEK_OF_MONTH;

        break;

      case AM_PM_FIELD :
        dateFormatField = Field.AM_PM;
        buffer.append(this.format.getAmPmStrings()[getCalendar().get(Calendar.AM_PM)]);

        break;

      case HOUR1_FIELD :                // 'h'
        dateFormatField = Field.HOUR1;
        hour            = getCalendar().get(Calendar.HOUR);
        appendNumber(buffer, count, (hour == 0)
                                    ? 12
                                    : hour);

        break;

      case HOUR0_FIELD :                // 'K'
        dateFormatField = Field.HOUR0;
        field           = Calendar.HOUR;

        break;

      case TIMEZONE_FIELD :             // 'z'
        dateFormatField = Field.TIME_ZONE;
        appendTimeZone(buffer, count, true);

        break;

      case RFC_822_TIMEZONE_FIELD :     // 'Z'
        dateFormatField = Field.TIME_ZONE;
        appendNumericTimeZone(buffer, count, false);

        break;
    }

    if (field != -1) {
      appendNumber(buffer, count, getCalendar().get(field));
    }

    if (fields != null) {
      position = new FieldPosition(dateFormatField);
      position.setBeginIndex(beginPosition);
      position.setEndIndex(buffer.length());
      fields.add(position);
    } else {
      // Set to the first occurrence
      if (((position
              .getFieldAttribute() == dateFormatField) || ((position.getFieldAttribute() == null) && (position
                .getField() == index))) && (position.getEndIndex() == 0)) {
        position.setBeginIndex(beginPosition);
        position.setEndIndex(buffer.length());
      }
    }
  }

  // See http://www.unicode.org/reports/tr35/#Date_Format_Patterns for the different counts.
  private void appendDayOfWeek(StringBuffer buffer, int count, boolean standAlone) {
    String[] days;

    if (count == 4) {
      days = format.getWeekdays();
    } else if (count == 5) {
      days = format.getShortWeekdays();
    } else {
      days = format.getShortWeekdays();
    }

    buffer.append(days[getCalendar().get(Calendar.DAY_OF_WEEK)]);
  }

  // See http://www.unicode.org/reports/tr35/#Date_Format_Patterns for the different counts.
  private void appendMonth(StringBuffer buffer, int count, boolean standAlone) {
    int month = getCalendar().get(Calendar.MONTH);

    if (count <= 2) {
      appendNumber(buffer, count, month + 1);

      return;
    }

    String[] months;

    if (count == 4) {
      months = format.getMonths();
    } else if (count == 5) {
      months = format.getShortMonths();
    } else {
      months = format.getShortMonths();
    }

    buffer.append(months[month]);
  }

  /**
   * Append a representation of the time zone of 'calendar' to 'buffer'.
   *
   * @param count the number of z or Z characters in the format string; "zzz" would be 3,
   * for example.
   * @param generalTimeZone true if we should use a display name ("PDT") if available;
   * false implies that we should use RFC 822 format ("-0800") instead. This corresponds to 'z'
   * versus 'Z' in the format string.
   */
  private void appendTimeZone(StringBuffer buffer, int count, boolean generalTimeZone) {
    if (generalTimeZone) {
      TimeZone tz       = getCalendar().getTimeZone();
      boolean  daylight = (getCalendar().get(Calendar.DST_OFFSET) != 0);
      int      style    = (count < 4)
                          ? TimeZone.SHORT
                          : TimeZone.LONG;

      buffer.append(tz.getDisplayName(daylight, style, format.locale));

      return;
    }

    // We didn't find what we were looking for, so default to a numeric time zone.
    appendNumericTimeZone(buffer, count, generalTimeZone);
  }

  // See http://www.unicode.org/reports/tr35/#Date_Format_Patterns for the different counts.
  // @param generalTimeZone "GMT-08:00" rather than "-0800".
  private void appendNumericTimeZone(StringBuffer buffer, int count, boolean generalTimeZone) {
    int  offset = getCalendar().get(Calendar.ZONE_OFFSET) + getCalendar().get(Calendar.DST_OFFSET);
    char sign   = '+';

    if (offset < 0) {
      sign   = '-';
      offset = -offset;
    }

    if (generalTimeZone || (count == 4)) {
      buffer.append("GMT");
    }

    buffer.append(sign);
    appendNumber(buffer, 2, offset / 3600000);

    if (generalTimeZone || (count >= 4)) {
      buffer.append(':');
    }

    appendNumber(buffer, 2, (offset % 3600000) / 60000);
  }

  private void appendNumber(StringBuffer buffer, int count, int value) {
    // TODO: we could avoid using the NumberFormat in most cases for a significant speedup.
    // The only problem is that we expose the NumberFormat to third-party code, so we'd have
    // some work to do to work out when the optimization is valid.
    int minimumIntegerDigits = getNumberFormat().getMinimumIntegerDigits();

    getNumberFormat().setMinimumIntegerDigits(count);
    getNumberFormat().format(Integer.valueOf(value), buffer, new FieldPosition(0));
    getNumberFormat().setMinimumIntegerDigits(minimumIntegerDigits);
  }

  private Date error(ParsePosition position, int offset, TimeZone zone) {
    position.setErrorIndex(offset);
    getCalendar().setTimeZone(zone);

    return null;
  }

  public Calendar getCalendar() {
    if(calendar==null) {
      TimeZone tz=getTimeZone();
      calendar = Calendar.getInstance(tz!=null ? tz :TimeZone.getDefault(), format.locale);
    }
    return calendar;
  }

  public void setCalendar(Calendar calendar) {
    this.calendar = calendar;
  }

  public NumberFormat getNumberFormat() {
    if(numberFormat==null) {
      numberFormat=NumberFormat.getInstance(format.locale);
      numberFormat.setGroupingUsed(false);
    }
    return numberFormat;
  }

  public void setNumberFormat(NumberFormat numberFormat) {
    this.numberFormat = numberFormat;
  }
}
