//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../spot/src/com/appnativa/spot/SPOTDate.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/spot/SPOTDate.h"
#include "com/appnativa/spot/SPOTDateTime.h"
#include "com/appnativa/spot/SPOTException.h"
#include "com/appnativa/spot/aSPOTElement.h"
#include "com/appnativa/spot/iSPOTConstants.h"
#include "com/appnativa/util/SNumber.h"
#include "java/lang/NumberFormatException.h"
#include "java/text/SimpleDateFormat.h"
#include "java/util/Calendar.h"
#include "java/util/Date.h"

@implementation SPOTDate

- (id)init {
  return [self initSPOTDateWithBoolean:YES];
}

- (id)initSPOTDateWithBoolean:(BOOL)optional {
  if (self = [super init]) {
    _isOptional_ = optional;
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initSPOTDateWithBoolean:optional];
}

- (id)initWithJavaUtilCalendar:(JavaUtilCalendar *)val {
  if (self = [super init]) {
    _isOptional_ = NO;
    [self setValueWithJavaUtilCalendar:val];
  }
  return self;
}

- (id)initWithRAREUTSNumber:(RAREUTSNumber *)val {
  if (self = [super init]) {
    _isOptional_ = NO;
    [self setValueWithRAREUTSNumber:val];
  }
  return self;
}

- (id)initWithNSString:(NSString *)val {
  if (self = [super init]) {
    [self setValuesWithNSString:val withNSString:nil withNSString:nil withBoolean:NO];
  }
  return self;
}

- (id)initWithNSString:(NSString *)val
          withNSString:(NSString *)defaultval
           withBoolean:(BOOL)optional {
  if (self = [super init]) {
    [self setValuesWithNSString:val withNSString:nil withNSString:nil withBoolean:optional];
    [self setDefaultValueWithNSString:defaultval];
  }
  return self;
}

- (id)initWithNSString:(NSString *)val
          withNSString:(NSString *)min
          withNSString:(NSString *)max {
  if (self = [super init]) {
    [self setValuesWithNSString:val withNSString:min withNSString:max withBoolean:NO];
  }
  return self;
}

- (id)initWithNSString:(NSString *)val
          withNSString:(NSString *)min
          withNSString:(NSString *)max
           withBoolean:(BOOL)optional {
  if (self = [super init]) {
    [self setValuesWithNSString:val withNSString:min withNSString:max withBoolean:optional];
  }
  return self;
}

- (id)initWithNSString:(NSString *)val
          withNSString:(NSString *)min
          withNSString:(NSString *)max
          withNSString:(NSString *)defaultval
           withBoolean:(BOOL)optional {
  if (self = [super init]) {
    [self setValuesWithNSString:val withNSString:min withNSString:max withBoolean:optional];
    [self setDefaultValueWithNSString:defaultval];
  }
  return self;
}

- (BOOL)equalsWithASPOTElement:(aSPOTElement *)o {
  if ([o isKindOfClass:[SPOTDate class]]) {
    return [super equalsWithASPOTElement:o];
  }
  return NO;
}

- (long long int)longValue {
  JavaUtilCalendar *t1 = (_cValue_ != nil) ? _cValue_ : _cDefValue_;
  if (t1 == nil) {
    @throw [[JavaLangNumberFormatException alloc] initWithNSString:[iSPOTConstants STR_NULL_VALUE]];
  }
  long long int date = [t1 getWithInt:JavaUtilCalendar_YEAR];
  date -= 1700;
  date *= 10000;
  date += (100 * (([t1 getWithInt:JavaUtilCalendar_MONTH] + 1)));
  date += [t1 getWithInt:JavaUtilCalendar_DAY_OF_MONTH];
  return date;
}

+ (NSString *)now {
  return [((SPOTDate *) [[SPOTDate alloc] init]) description];
}

+ (SPOTDate *)nowDate {
  SPOTDate *d = [[SPOTDate alloc] init];
  [d setToCurrentTime];
  return d;
}

- (RAREUTSNumber *)numberValue {
  JavaUtilCalendar *t1 = (_cValue_ != nil) ? _cValue_ : _cDefValue_;
  if (t1 == nil) {
    return nil;
  }
  long long int date = [t1 getWithInt:JavaUtilCalendar_YEAR];
  date -= 1700;
  date *= 10000;
  date += (100 * (([t1 getWithInt:JavaUtilCalendar_MONTH] + 1)));
  date += [t1 getWithInt:JavaUtilCalendar_DAY_OF_MONTH];
  return [((RAREUTSNumber *) nil_chk([self numValueNumber])) setValueWithLong:date];
}

+ (RAREUTSNumber *)numberValueWithJavaUtilCalendar:(JavaUtilCalendar *)val
                                 withRAREUTSNumber:(RAREUTSNumber *)num {
  long long int date = [((JavaUtilCalendar *) nil_chk(val)) getWithInt:JavaUtilCalendar_YEAR];
  date -= 1700;
  date *= 10000;
  date += (100 * (([val getWithInt:JavaUtilCalendar_MONTH] + 1)));
  date += [val getWithInt:JavaUtilCalendar_DAY_OF_MONTH];
  num = (num == nil) ? [[RAREUTSNumber alloc] initWithLong:date] : [num setValueWithLong:date];
  return num;
}

- (int)spot_getType {
  return iSPOTConstants_SPOT_TYPE_DATE;
}

- (NSString *)spot_stringValue {
  JavaUtilCalendar *t1 = (_cValue_ != nil) ? _cValue_ : _cDefValue_;
  return (t1 == nil) ? nil : [SPOTDateTime toStringWithJavaUtilCalendar:t1 withBoolean:YES withBoolean:NO];
}

- (NSString *)description {
  JavaUtilCalendar *t1 = (_cValue_ != nil) ? _cValue_ : _cDefValue_;
  return (t1 == nil) ? nil : [SPOTDateTime toStringWithJavaUtilCalendar:t1 withBoolean:YES withBoolean:NO];
}

- (NSString *)toStringEx {
  JavaUtilCalendar *t1 = (_cValue_ != nil) ? _cValue_ : _cDefValue_;
  JavaTextSimpleDateFormat *df = [[JavaTextSimpleDateFormat alloc] initWithNSString:@"dd MMM yyyy"];
  return [df formatWithJavaUtilDate:[t1 getTime]];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithJavaUtilCalendar:", NULL, NULL, 0x1, "SPOTException" },
    { "initWithRAREUTSNumber:", NULL, NULL, 0x1, "SPOTException" },
    { "initWithNSString:", NULL, NULL, 0x1, "SPOTException" },
    { "initWithNSString:withNSString:withBoolean:", NULL, NULL, 0x1, "SPOTException" },
    { "initWithNSString:withNSString:withNSString:", NULL, NULL, 0x1, "SPOTException" },
    { "initWithNSString:withNSString:withNSString:withBoolean:", NULL, NULL, 0x1, "SPOTException" },
    { "initWithNSString:withNSString:withNSString:withNSString:withBoolean:", NULL, NULL, 0x1, "SPOTException" },
    { "equalsWithASPOTElement:", NULL, "Z", 0x1, NULL },
    { "now", NULL, "LNSString", 0x9, NULL },
    { "nowDate", NULL, "LSPOTDate", 0x9, NULL },
    { "numberValue", NULL, "LRAREUTSNumber", 0x1, NULL },
    { "numberValueWithJavaUtilCalendar:withRAREUTSNumber:", NULL, "LRAREUTSNumber", 0x9, NULL },
    { "spot_getType", NULL, "I", 0x11, NULL },
    { "spot_stringValue", NULL, "LNSString", 0x1, NULL },
    { "toStringEx", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcClassInfo _SPOTDate = { "SPOTDate", "com.appnativa.spot", NULL, 0x1, 15, methods, 0, NULL, 0, NULL};
  return &_SPOTDate;
}

@end
