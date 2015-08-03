//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/text/DateFormat.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaTextDateFormat_H_
#define _JavaTextDateFormat_H_

@class IOSObjectArray;
@class JavaLangStringBuffer;
@class JavaTextFieldPosition;
@class JavaTextNumberFormat;
@class JavaTextParsePosition;
@class JavaUtilCalendar;
@class JavaUtilDate;
@class JavaUtilHashtable;
@class JavaUtilLocale;
@class JavaUtilTimeZone;

#import "JreEmulation.h"
#include "java/text/Format.h"

#define JavaTextDateFormat_AM_PM_FIELD 14
#define JavaTextDateFormat_DATE_FIELD 3
#define JavaTextDateFormat_DAY_OF_WEEK_FIELD 9
#define JavaTextDateFormat_DAY_OF_WEEK_IN_MONTH_FIELD 11
#define JavaTextDateFormat_DAY_OF_YEAR_FIELD 10
#define JavaTextDateFormat_DEFAULT 2
#define JavaTextDateFormat_ERA_FIELD 0
#define JavaTextDateFormat_FULL 0
#define JavaTextDateFormat_HOUR0_FIELD 16
#define JavaTextDateFormat_HOUR1_FIELD 15
#define JavaTextDateFormat_HOUR_OF_DAY0_FIELD 5
#define JavaTextDateFormat_HOUR_OF_DAY1_FIELD 4
#define JavaTextDateFormat_LONG 1
#define JavaTextDateFormat_MEDIUM 2
#define JavaTextDateFormat_MILLISECOND_FIELD 8
#define JavaTextDateFormat_MINUTE_FIELD 6
#define JavaTextDateFormat_MONTH_FIELD 2
#define JavaTextDateFormat_SECOND_FIELD 7
#define JavaTextDateFormat_SHORT 3
#define JavaTextDateFormat_TIMEZONE_FIELD 17
#define JavaTextDateFormat_WEEK_OF_MONTH_FIELD 13
#define JavaTextDateFormat_WEEK_OF_YEAR_FIELD 12
#define JavaTextDateFormat_YEAR_FIELD 1
#define JavaTextDateFormat_serialVersionUID 7218322306649953788

@interface JavaTextDateFormat : JavaTextFormat {
 @public
  JavaUtilCalendar *calendar_;
  JavaTextNumberFormat *numberFormat_;
}

+ (int)DEFAULT;
+ (int)FULL;
+ (int)LONG;
+ (int)MEDIUM;
+ (int)SHORT;
+ (int)ERA_FIELD;
+ (int)YEAR_FIELD;
+ (int)MONTH_FIELD;
+ (int)DATE_FIELD;
+ (int)HOUR_OF_DAY1_FIELD;
+ (int)HOUR_OF_DAY0_FIELD;
+ (int)MINUTE_FIELD;
+ (int)SECOND_FIELD;
+ (int)MILLISECOND_FIELD;
+ (int)DAY_OF_WEEK_FIELD;
+ (int)DAY_OF_YEAR_FIELD;
+ (int)DAY_OF_WEEK_IN_MONTH_FIELD;
+ (int)WEEK_OF_YEAR_FIELD;
+ (int)WEEK_OF_MONTH_FIELD;
+ (int)AM_PM_FIELD;
+ (int)HOUR1_FIELD;
+ (int)HOUR0_FIELD;
+ (int)TIMEZONE_FIELD;
- (id)init;
- (id)clone;
- (BOOL)isEqual:(id)object;
- (JavaLangStringBuffer *)formatWithId:(id)object
              withJavaLangStringBuffer:(JavaLangStringBuffer *)buffer
             withJavaTextFieldPosition:(JavaTextFieldPosition *)field;
- (NSString *)formatWithJavaUtilDate:(JavaUtilDate *)date;
- (JavaLangStringBuffer *)formatWithJavaUtilDate:(JavaUtilDate *)date
                        withJavaLangStringBuffer:(JavaLangStringBuffer *)buffer
                       withJavaTextFieldPosition:(JavaTextFieldPosition *)field;
+ (IOSObjectArray *)getAvailableLocales;
- (JavaUtilCalendar *)getCalendar;
+ (JavaTextDateFormat *)getDateInstance;
+ (JavaTextDateFormat *)getDateInstanceWithInt:(int)style;
+ (JavaTextDateFormat *)getDateInstanceWithInt:(int)style
                            withJavaUtilLocale:(JavaUtilLocale *)locale;
+ (JavaTextDateFormat *)getDateTimeInstance;
+ (JavaTextDateFormat *)getDateTimeInstanceWithInt:(int)dateStyle
                                           withInt:(int)timeStyle;
+ (JavaTextDateFormat *)getDateTimeInstanceWithInt:(int)dateStyle
                                           withInt:(int)timeStyle
                                withJavaUtilLocale:(JavaUtilLocale *)locale;
+ (JavaTextDateFormat *)getInstance;
- (JavaTextNumberFormat *)getNumberFormat;
+ (JavaTextDateFormat *)getTimeInstance;
+ (JavaTextDateFormat *)getTimeInstanceWithInt:(int)style;
+ (JavaTextDateFormat *)getTimeInstanceWithInt:(int)style
                            withJavaUtilLocale:(JavaUtilLocale *)locale;
- (JavaUtilTimeZone *)getTimeZone;
- (NSUInteger)hash;
- (BOOL)isLenient;
- (JavaUtilDate *)parseWithNSString:(NSString *)string;
- (JavaUtilDate *)parseWithNSString:(NSString *)string
          withJavaTextParsePosition:(JavaTextParsePosition *)position;
- (id)parseObjectWithNSString:(NSString *)string
    withJavaTextParsePosition:(JavaTextParsePosition *)position;
- (void)setCalendarWithJavaUtilCalendar:(JavaUtilCalendar *)cal;
- (void)setLenientWithBoolean:(BOOL)value;
- (void)setNumberFormatWithJavaTextNumberFormat:(JavaTextNumberFormat *)format;
- (void)setTimeZoneWithJavaUtilTimeZone:(JavaUtilTimeZone *)timezone;
+ (void)checkDateStyleWithInt:(int)style;
+ (void)checkTimeStyleWithInt:(int)style;
- (void)copyAllFieldsTo:(JavaTextDateFormat *)other;
@end

J2OBJC_FIELD_SETTER(JavaTextDateFormat, calendar_, JavaUtilCalendar *)
J2OBJC_FIELD_SETTER(JavaTextDateFormat, numberFormat_, JavaTextNumberFormat *)

#define JavaTextDateFormat_Field_serialVersionUID 7441350119349544720

@interface JavaTextDateFormat_Field : JavaTextFormat_Field {
 @public
  int calendarField_;
}

+ (JavaUtilHashtable *)table;
+ (void)setTable:(JavaUtilHashtable *)table;
+ (JavaTextDateFormat_Field *)ERA;
+ (JavaTextDateFormat_Field *)YEAR;
+ (JavaTextDateFormat_Field *)MONTH;
+ (JavaTextDateFormat_Field *)HOUR_OF_DAY0;
+ (JavaTextDateFormat_Field *)HOUR_OF_DAY1;
+ (JavaTextDateFormat_Field *)MINUTE;
+ (JavaTextDateFormat_Field *)SECOND;
+ (JavaTextDateFormat_Field *)MILLISECOND;
+ (JavaTextDateFormat_Field *)DAY_OF_WEEK;
+ (JavaTextDateFormat_Field *)DAY_OF_MONTH;
+ (JavaTextDateFormat_Field *)DAY_OF_YEAR;
+ (JavaTextDateFormat_Field *)DAY_OF_WEEK_IN_MONTH;
+ (JavaTextDateFormat_Field *)WEEK_OF_YEAR;
+ (JavaTextDateFormat_Field *)WEEK_OF_MONTH;
+ (JavaTextDateFormat_Field *)AM_PM;
+ (JavaTextDateFormat_Field *)HOUR0;
+ (JavaTextDateFormat_Field *)HOUR1;
+ (JavaTextDateFormat_Field *)TIME_ZONE;
- (id)initWithNSString:(NSString *)fieldName
               withInt:(int)calendarField;
- (int)getCalendarField;
+ (JavaTextDateFormat_Field *)ofCalendarFieldWithInt:(int)calendarField;
- (void)copyAllFieldsTo:(JavaTextDateFormat_Field *)other;
@end

#endif // _JavaTextDateFormat_H_