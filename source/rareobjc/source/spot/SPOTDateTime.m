//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../spot/src/com/appnativa/spot/SPOTDateTime.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSCharArray.h"
#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/spot/SPOTDateTime.h"
#include "com/appnativa/spot/SPOTException.h"
#include "com/appnativa/spot/aSPOTElement.h"
#include "com/appnativa/spot/iSPOTConstants.h"
#include "com/appnativa/util/Helper.h"
#include "com/appnativa/util/SNumber.h"
#include "java/lang/Character.h"
#include "java/lang/Math.h"
#include "java/lang/NumberFormatException.h"
#include "java/lang/StringBuilder.h"
#include "java/lang/ThreadLocal.h"
#include "java/text/SimpleDateFormat.h"
#include "java/util/Calendar.h"
#include "java/util/Date.h"
#include "java/util/TimeZone.h"

@implementation SPOTDateTime

static IOSObjectArray * SPOTDateTime_months_;
static double SPOTDateTime_LN_10_;

+ (int)DAY_MASK {
  return SPOTDateTime_DAY_MASK;
}

+ (int)HHMM_MASK {
  return SPOTDateTime_HHMM_MASK;
}

+ (int)MILL_MASK {
  return SPOTDateTime_MILL_MASK;
}

+ (int)MIN_MASK {
  return SPOTDateTime_MIN_MASK;
}

+ (int)MONTH_MASK {
  return SPOTDateTime_MONTH_MASK;
}

+ (int)SEC_MASK {
  return SPOTDateTime_SEC_MASK;
}

+ (int)TIMERR_MASK {
  return SPOTDateTime_TIMERR_MASK;
}

+ (int)YEAR_MASK {
  return SPOTDateTime_YEAR_MASK;
}

+ (IOSObjectArray *)months {
  return SPOTDateTime_months_;
}

+ (double)LN_10 {
  return SPOTDateTime_LN_10_;
}

- (id)init {
  return [self initSPOTDateTimeWithBoolean:YES];
}

- (id)initSPOTDateTimeWithBoolean:(BOOL)optional {
  if (self = [super init]) {
    _cDefValue_ = nil;
    _cRangeMax_ = nil;
    _cRangeMin_ = nil;
    _cValue_ = nil;
    _isOptional_ = optional;
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initSPOTDateTimeWithBoolean:optional];
}

- (id)initWithJavaUtilCalendar:(JavaUtilCalendar *)val {
  if (self = [super init]) {
    _cDefValue_ = nil;
    _cRangeMax_ = nil;
    _cRangeMin_ = nil;
    _cValue_ = nil;
    _isOptional_ = NO;
    [self setValueWithJavaUtilCalendar:val];
  }
  return self;
}

- (id)initWithRAREUTSNumber:(RAREUTSNumber *)val {
  if (self = [super init]) {
    _cDefValue_ = nil;
    _cRangeMax_ = nil;
    _cRangeMin_ = nil;
    _cValue_ = nil;
    _isOptional_ = NO;
    [self setValueWithRAREUTSNumber:val];
  }
  return self;
}

- (id)initWithNSString:(NSString *)val {
  if (self = [super init]) {
    _cDefValue_ = nil;
    _cRangeMax_ = nil;
    _cRangeMin_ = nil;
    _cValue_ = nil;
    [self setValuesWithNSString:val withNSString:nil withNSString:nil withBoolean:NO];
  }
  return self;
}

- (id)initWithJavaUtilCalendar:(JavaUtilCalendar *)val
                   withBoolean:(BOOL)optional {
  if (self = [super init]) {
    _cDefValue_ = nil;
    _cRangeMax_ = nil;
    _cRangeMin_ = nil;
    _cValue_ = nil;
    _isOptional_ = optional;
    [self setValueWithJavaUtilCalendar:val];
  }
  return self;
}

- (id)initWithNSString:(NSString *)val
           withBoolean:(BOOL)optional {
  if (self = [super init]) {
    _cDefValue_ = nil;
    _cRangeMax_ = nil;
    _cRangeMin_ = nil;
    _cValue_ = nil;
    _isOptional_ = optional;
    [self setValuesWithNSString:val withNSString:nil withNSString:nil withBoolean:NO];
  }
  return self;
}

- (id)initWithNSString:(NSString *)val
          withNSString:(NSString *)defaultval
           withBoolean:(BOOL)optional {
  if (self = [super init]) {
    _cDefValue_ = nil;
    _cRangeMax_ = nil;
    _cRangeMin_ = nil;
    _cValue_ = nil;
    _isOptional_ = optional;
    [self setDefaultValueWithNSString:defaultval];
    [self setValuesWithNSString:val withNSString:nil withNSString:nil withBoolean:NO];
  }
  return self;
}

- (id)initWithNSString:(NSString *)val
          withNSString:(NSString *)min
          withNSString:(NSString *)max {
  if (self = [super init]) {
    _cDefValue_ = nil;
    _cRangeMax_ = nil;
    _cRangeMin_ = nil;
    _cValue_ = nil;
    [self setValuesWithNSString:val withNSString:min withNSString:max withBoolean:NO];
  }
  return self;
}

- (id)initWithNSString:(NSString *)val
          withNSString:(NSString *)min
          withNSString:(NSString *)max
           withBoolean:(BOOL)optional {
  if (self = [super init]) {
    _cDefValue_ = nil;
    _cRangeMax_ = nil;
    _cRangeMin_ = nil;
    _cValue_ = nil;
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
    _cDefValue_ = nil;
    _cRangeMax_ = nil;
    _cRangeMin_ = nil;
    _cValue_ = nil;
    [self setValuesWithNSString:val withNSString:min withNSString:max withBoolean:optional];
    [self setDefaultValueWithNSString:defaultval];
  }
  return self;
}

- (void)addWithInt:(int)field
           withInt:(int)amount {
  if (_cValue_ == nil) {
    _cValue_ = [JavaUtilCalendar getInstance];
  }
  [((JavaUtilCalendar *) nil_chk(_cValue_)) addWithInt:field withInt:amount];
}

- (BOOL)booleanValue {
  @throw [[JavaLangNumberFormatException alloc] initWithNSString:[iSPOTConstants STR_ILLEGAL_VALUE]];
}

- (id)clone {
  SPOTDateTime *dt = (SPOTDateTime *) check_class_cast([super clone], [SPOTDateTime class]);
  if (_cValue_ != nil) {
    ((SPOTDateTime *) nil_chk(dt))->_cValue_ = (JavaUtilCalendar *) check_class_cast([_cValue_ clone], [JavaUtilCalendar class]);
  }
  return dt;
}

- (int)compareToWithId:(id)o {
  return [self compareToWithSPOTDateTime:(SPOTDateTime *) check_class_cast(o, [SPOTDateTime class])];
}

- (int)compareToWithSPOTDateTime:(SPOTDateTime *)o {
  if (o == nil) {
    return 1;
  }
  JavaUtilCalendar *t1 = (_cValue_ != nil) ? _cValue_ : _cDefValue_;
  JavaUtilCalendar *t2 = (((SPOTDateTime *) nil_chk(o))->_cValue_ != nil) ? o->_cValue_ : o->_cDefValue_;
  if ((t1 == nil) || (t2 == nil)) {
    return (t1 == t2) ? 0 : ((t1 != nil) ? 1 : -1);
  }
  return (int) ([self longValue] - [o longValue]);
}

- (double)doubleValue {
  RAREUTSNumber *num = [self numberValue];
  if (num == nil) {
    @throw [[SPOTException alloc] initWithNSString:[iSPOTConstants STR_NULL_VALUE] withNSString:(_theName_ == nil) ? [[self getClass] getName] : _theName_];
  }
  return [((RAREUTSNumber *) nil_chk(num)) doubleValue];
}

- (BOOL)equalsWithASPOTElement:(aSPOTElement *)e {
  if (!([e isKindOfClass:[SPOTDateTime class]])) {
    return NO;
  }
  SPOTDateTime *o = (SPOTDateTime *) check_class_cast(e, [SPOTDateTime class]);
  JavaUtilCalendar *t1 = (_cValue_ != nil) ? _cValue_ : _cDefValue_;
  JavaUtilCalendar *t2 = (((SPOTDateTime *) nil_chk(o))->_cValue_ != nil) ? o->_cValue_ : o->_cDefValue_;
  if ((t1 == nil) || (t2 == nil)) {
    if (t1 != t2) {
      return NO;
    }
  }
  else if ([self longValue] != [o longValue]) {
    return NO;
  }
  return [aSPOTElement spot_attributesEqualWithISPOTElement:self withISPOTElement:o];
}

- (NSUInteger)hash {
  JavaUtilCalendar *t1 = (_cValue_ != nil) ? _cValue_ : _cDefValue_;
  return (t1 != nil) ? [t1 hash] : [super hash];
}

- (int)intValue {
  return (int) [self longValue];
}

- (long long int)longValue {
  JavaUtilCalendar *t1 = (_cValue_ != nil) ? _cValue_ : _cDefValue_;
  if (t1 == nil) {
    @throw [[SPOTException alloc] initWithNSString:[iSPOTConstants STR_NULL_VALUE] withNSString:(_theName_ == nil) ? [[self getClass] getName] : _theName_];
  }
  long long int date = [t1 getWithInt:JavaUtilCalendar_YEAR];
  date -= 1700;
  date *= 10000;
  date += (100 * (([t1 getWithInt:JavaUtilCalendar_MONTH] + 1)));
  date += [t1 getWithInt:JavaUtilCalendar_DAY_OF_MONTH];
  return date;
}

+ (NSString *)now {
  return [SPOTDateTime toStringWithJavaUtilCalendar:[JavaUtilCalendar getInstance]];
}

+ (SPOTDateTime *)nowDateTime {
  SPOTDateTime *d = [[SPOTDateTime alloc] init];
  [d setToCurrentTime];
  return d;
}

- (RAREUTSNumber *)numberValue {
  JavaUtilCalendar *t1 = (_cValue_ != nil) ? _cValue_ : _cDefValue_;
  if (t1 == nil) {
    return nil;
  }
  return [SPOTDateTime numberValueWithJavaUtilCalendar:t1 withRAREUTSNumber:[self numValueNumber]];
}

+ (RAREUTSNumber *)numberValueWithJavaUtilCalendar:(JavaUtilCalendar *)val
                                 withRAREUTSNumber:(RAREUTSNumber *)num {
  JavaUtilCalendar *c = val;
  long long int date = [((JavaUtilCalendar *) nil_chk(c)) getWithInt:JavaUtilCalendar_YEAR];
  date -= 1700;
  date *= 10000;
  date += (100 * (([c getWithInt:JavaUtilCalendar_MONTH] + 1)));
  date += [c getWithInt:JavaUtilCalendar_DAY_OF_MONTH];
  long long int time = ([c getWithInt:JavaUtilCalendar_HOUR_OF_DAY]);
  time = time * 10000;
  time += ([c getWithInt:JavaUtilCalendar_MINUTE] * 100);
  time += [c getWithInt:JavaUtilCalendar_SECOND];
  int dec = (int) ([JavaLangMath logWithDouble:time] / SPOTDateTime_LN_10_);
  dec++;
  return (num == nil) ? [[RAREUTSNumber alloc] initWithLong:date withLong:time withInt:dec] : [num setValueWithLong:date withLong:time withInt:dec];
}

+ (RAREUTSNumber *)numberValueWithJavaUtilDate:(JavaUtilDate *)val
                             withRAREUTSNumber:(RAREUTSNumber *)num {
  JavaUtilCalendar *c = [JavaUtilCalendar getInstance];
  [((JavaUtilCalendar *) nil_chk(c)) setTimeWithJavaUtilDate:val];
  return [SPOTDateTime numberValueWithJavaUtilCalendar:c withRAREUTSNumber:num];
}

+ (RAREUTSNumber *)numberValueWithNSString:(NSString *)val
                         withRAREUTSNumber:(RAREUTSNumber *)num {
  JavaUtilCalendar *c = [JavaUtilCalendar getInstance];
  [SPOTDateTime setValueWithNSString:val withJavaUtilCalendar:c];
  return [SPOTDateTime numberValueWithJavaUtilCalendar:c withRAREUTSNumber:num];
}

- (void)spot_clear {
  [super spot_clear];
  _cValue_ = nil;
}

- (IOSObjectArray *)spot_getRange {
  if ((_cRangeMin_ == nil) && (_cRangeMax_ == nil)) {
    return nil;
  }
  return [IOSObjectArray arrayWithObjects:(id[]){ _cRangeMin_, _cRangeMax_ } count:2 type:[IOSClass classWithClass:[NSObject class]]];
}

- (int)spot_getType {
  return iSPOTConstants_SPOT_TYPE_DATETIME;
}

- (NSString *)spot_getValidityRange {
  if ((_cRangeMin_ != nil) || (_cRangeMax_ != nil)) {
    NSString *s = @"";
    s = (_cRangeMin_ != nil) ? ([NSString stringWithFormat:@"%@..", [SPOTDateTime toStringWithJavaUtilCalendar:_cRangeMin_]]) : @"..";
    if (_cRangeMax_ != nil) {
      s = [NSString stringWithFormat:@"%@%@", s, [SPOTDateTime toStringWithJavaUtilCalendar:_cRangeMax_]];
    }
    return s;
  }
  else {
    return @"";
  }
}

- (id)spot_getValue {
  return [self getValue];
}

- (void)spot_setRangeWithNSString:(NSString *)min
                     withNSString:(NSString *)max {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if ((min != nil) && ([min sequenceLength] > 0)) {
    _cRangeMin_ = [JavaUtilCalendar getInstance];
    [SPOTDateTime setValueWithNSString:min withJavaUtilCalendar:_cRangeMin_];
  }
  else {
    _cRangeMin_ = nil;
  }
  if ((max != nil) && ([max sequenceLength] > 0)) {
    _cRangeMax_ = [JavaUtilCalendar getInstance];
    [SPOTDateTime setValueWithNSString:max withJavaUtilCalendar:_cRangeMax_];
  }
  else {
    _cRangeMax_ = nil;
  }
}

- (NSString *)spot_stringValue {
  JavaUtilCalendar *t1 = (_cValue_ != nil) ? _cValue_ : _cDefValue_;
  return (t1 == nil) ? nil : [SPOTDateTime toStringWithJavaUtilCalendar:t1];
}

- (NSString *)spot_stringValueEx {
  return ((_cValue_ == nil) && ![self spot_attributesWereSet]) ? nil : [self spot_stringValue];
}

+ (NSString *)to1700DateWithJavaUtilCalendar:(JavaUtilCalendar *)val
                                 withBoolean:(BOOL)time {
  RAREUTSNumber *num = (RAREUTSNumber *) check_class_cast([((JavaLangThreadLocal *) nil_chk([aSPOTElement perThreadNumber])) get], [RAREUTSNumber class]);
  (void) [SPOTDateTime numberValueWithJavaUtilCalendar:val withRAREUTSNumber:num];
  if (!time) {
    (void) [num setValueWithLong:[((RAREUTSNumber *) nil_chk(num)) longLongValue]];
  }
  return [((RAREUTSNumber *) nil_chk(num)) description];
}

- (NSString *)description {
  JavaUtilCalendar *t1 = (_cValue_ != nil) ? _cValue_ : _cDefValue_;
  return (t1 == nil) ? @"" : [SPOTDateTime toStringWithJavaUtilCalendar:t1];
}

+ (NSString *)toStringWithJavaUtilCalendar:(JavaUtilCalendar *)date {
  return [SPOTDateTime toStringWithJavaUtilCalendar:date withBoolean:NO withBoolean:NO];
}

+ (NSString *)toStringWithJavaUtilCalendar:(JavaUtilCalendar *)date
                               withBoolean:(BOOL)dateonly
                               withBoolean:(BOOL)timeonly {
  JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] init];
  int y;
  if (!timeonly) {
    (void) [sb appendWithInt:[((JavaUtilCalendar *) nil_chk(date)) getWithInt:JavaUtilCalendar_YEAR]];
    (void) [sb appendWithChar:'-'];
    y = ([date getWithInt:JavaUtilCalendar_MONTH] + 1);
    if (y < 10) {
      (void) [sb appendWithChar:'0'];
    }
    (void) [sb appendWithInt:y];
    (void) [sb appendWithChar:'-'];
    y = [date getWithInt:JavaUtilCalendar_DAY_OF_MONTH];
    if (y < 10) {
      (void) [sb appendWithChar:'0'];
    }
    (void) [sb appendWithInt:y];
  }
  if (dateonly) {
    return [sb description];
  }
  (void) [sb appendWithChar:'T'];
  y = [((JavaUtilCalendar *) nil_chk(date)) getWithInt:JavaUtilCalendar_HOUR_OF_DAY];
  if (y < 10) {
    (void) [sb appendWithChar:'0'];
  }
  (void) [sb appendWithInt:y];
  (void) [sb appendWithChar:':'];
  y = [date getWithInt:JavaUtilCalendar_MINUTE];
  if (y < 10) {
    (void) [sb appendWithChar:'0'];
  }
  (void) [sb appendWithInt:y];
  (void) [sb appendWithChar:':'];
  y = [date getWithInt:JavaUtilCalendar_SECOND];
  if (y < 10) {
    (void) [sb appendWithChar:'0'];
  }
  (void) [sb appendWithInt:y];
  y = [((JavaUtilTimeZone *) nil_chk([date getTimeZone])) getRawOffset] / 3600000;
  if (y == 0) {
    (void) [sb appendWithChar:'Z'];
  }
  else {
    if (y > 0) {
      (void) [sb appendWithChar:'+'];
    }
    else {
      (void) [sb appendWithChar:'-'];
      y *= -1;
    }
    if (y < 10) {
      (void) [sb appendWithChar:'0'];
    }
    (void) [sb appendWithInt:y];
    (void) [sb appendWithNSString:@":00"];
  }
  return [sb description];
}

- (NSString *)toStringEx {
  JavaUtilCalendar *date = (_cValue_ == nil) ? _cDefValue_ : _cValue_;
  if (date == nil) {
    @throw [[SPOTException alloc] initWithNSString:[iSPOTConstants STR_NULL_VALUE] withNSString:(_theName_ == nil) ? [[self getClass] getName] : _theName_];
  }
  JavaTextSimpleDateFormat *df = [[JavaTextSimpleDateFormat alloc] initWithNSString:@"dd MMM yyyy hh:mma"];
  return [df formatWithJavaUtilDate:[date getTime]];
}

- (NSString *)toStringExWithNSString:(NSString *)format {
  JavaUtilCalendar *date = (_cValue_ == nil) ? _cDefValue_ : _cValue_;
  if (date == nil) {
    @throw [[SPOTException alloc] initWithNSString:[iSPOTConstants STR_NULL_VALUE] withNSString:(_theName_ == nil) ? [[self getClass] getName] : _theName_];
  }
  JavaTextSimpleDateFormat *df = [[JavaTextSimpleDateFormat alloc] initWithNSString:format];
  return [df formatWithJavaUtilDate:[date getTime]];
}

- (NSString *)toStringFMWithBoolean:(BOOL)dateonly
                        withBoolean:(BOOL)timeonly
                        withBoolean:(BOOL)ampm {
  if (_cValue_ == nil) {
    return @"";
  }
  return [((JavaLangStringBuilder *) nil_chk([SPOTDateTime toStringFMWithJavaLangStringBuilder:[[JavaLangStringBuilder alloc] init] withJavaUtilCalendar:_cValue_ withBoolean:dateonly withBoolean:timeonly withBoolean:ampm])) description];
}

+ (JavaLangStringBuilder *)toStringFMWithJavaLangStringBuilder:(JavaLangStringBuilder *)sb
                                          withJavaUtilCalendar:(JavaUtilCalendar *)date
                                                   withBoolean:(BOOL)dateonly
                                                   withBoolean:(BOOL)timeonly
                                                   withBoolean:(BOOL)ampm {
  int y;
  if (!timeonly) {
    y = [((JavaUtilCalendar *) nil_chk(date)) getWithInt:JavaUtilCalendar_MONTH];
    (void) [((JavaLangStringBuilder *) nil_chk(sb)) appendWithNSString:IOSObjectArray_Get(nil_chk(SPOTDateTime_months_), y)];
    (void) [sb appendWithChar:' '];
    y = [date getWithInt:JavaUtilCalendar_DAY_OF_MONTH];
    if (y < 10) {
      (void) [sb appendWithChar:'0'];
    }
    (void) [sb appendWithInt:y];
    (void) [sb appendWithNSString:@", "];
    (void) [sb appendWithInt:[date getWithInt:JavaUtilCalendar_YEAR]];
  }
  if (dateonly) {
    return sb;
  }
  (void) [((JavaLangStringBuilder *) nil_chk(sb)) appendWithChar:'@'];
  y = ampm ? [((JavaUtilCalendar *) nil_chk(date)) getWithInt:JavaUtilCalendar_HOUR] : [((JavaUtilCalendar *) nil_chk(date)) getWithInt:JavaUtilCalendar_HOUR_OF_DAY];
  if (y < 10) {
    (void) [sb appendWithChar:'0'];
  }
  (void) [sb appendWithInt:y];
  (void) [sb appendWithChar:':'];
  y = [((JavaUtilCalendar *) nil_chk(date)) getWithInt:JavaUtilCalendar_MINUTE];
  if (y < 10) {
    (void) [sb appendWithChar:'0'];
  }
  (void) [sb appendWithInt:y];
  if (ampm) {
    (void) [sb appendWithNSString:([date getWithInt:JavaUtilCalendar_AM_PM] == JavaUtilCalendar_AM) ? @"AM" : @"PM"];
  }
  return sb;
}

- (void)setDefaultValueWithNSString:(NSString *)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if (val == nil) {
    _cDefValue_ = nil;
    return;
  }
  else {
    _cDefValue_ = [JavaUtilCalendar getInstance];
    [SPOTDateTime setValueWithNSString:val withJavaUtilCalendar:_cDefValue_];
    return;
  }
}

- (void)setTimeZoneWithNSString:(NSString *)s {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  JavaUtilTimeZone *tz = [JavaUtilTimeZone getTimeZoneWithNSString:s];
  if (_cValue_ == nil) {
    _cValue_ = [JavaUtilCalendar getInstance];
  }
  [((JavaUtilCalendar *) nil_chk(_cValue_)) setTimeZoneWithJavaUtilTimeZone:tz];
}

- (void)setToCurrentTime {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  _cValue_ = [JavaUtilCalendar getInstance];
}

- (void)setValueWithBoolean:(BOOL)val {
  @throw [[SPOTException alloc] initWithInt:iSPOTConstants_NOT_SUPPORTED withNSString:[iSPOTConstants STR_NOT_SUPPORTED] withNSString:@"Sequence"];
}

- (void)setValueWithJavaUtilCalendar:(JavaUtilCalendar *)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if (val == nil) {
    [self spot_clear];
    return;
  }
  _cValue_ = (JavaUtilCalendar *) check_class_cast([((JavaUtilCalendar *) nil_chk(val)) clone], [JavaUtilCalendar class]);
}

- (void)setValueWithJavaUtilDate:(JavaUtilDate *)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if (val == nil) {
    [self spot_clear];
    return;
  }
  if (_cValue_ == nil) {
    _cValue_ = [JavaUtilCalendar getInstance];
  }
  [((JavaUtilCalendar *) nil_chk(_cValue_)) setTimeWithJavaUtilDate:val];
}

- (void)setValueWithDouble:(double)val {
  [self setValueWithRAREUTSNumber:[[RAREUTSNumber alloc] initWithDouble:val]];
}

- (void)setValueWithLong:(long long int)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  int y = (int) ((val >> 12) & SPOTDateTime_YEAR_MASK);
  int m = (int) ((val >> 5) & SPOTDateTime_MONTH_MASK);
  int d = (int) (val & SPOTDateTime_DAY_MASK);
  if (_cValue_ == nil) {
    _cValue_ = [JavaUtilCalendar getInstance];
  }
  [((JavaUtilCalendar *) nil_chk(_cValue_)) setWithInt:y withInt:m withInt:d];
}

- (void)setValueWithRAREUTSNumber:(RAREUTSNumber *)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  long long int date = [((RAREUTSNumber *) nil_chk(val)) longLongValue];
  long long int time = [val fractionalPart];
  int y = (int) (date / 10000);
  y += 1700;
  date = date % 10000;
  int m = (int) (date / 100) - 1;
  int d = (int) (date % 100);
  int hm = (int) (time / 10000);
  if (hm > 23) {
    hm = hm / 10;
  }
  time = time % 10000;
  int min = (int) (time / 100);
  int sec = (int) (time % 100);
  if (min > 59) {
    min = min / 10;
  }
  if (sec > 59) {
    sec = sec / 10;
  }
  if (_cValue_ == nil) {
    _cValue_ = [JavaUtilCalendar getInstance];
  }
  [((JavaUtilCalendar *) nil_chk(_cValue_)) setWithInt:y withInt:m withInt:d withInt:hm withInt:min withInt:sec];
}

- (void)setValueWithSPOTDateTime:(SPOTDateTime *)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  JavaUtilCalendar *cal = ((SPOTDateTime *) nil_chk(val))->_cValue_;
  if (cal == nil) {
    cal = val->_cDefValue_;
  }
  if (cal == nil) {
    [self spot_clear];
    return;
  }
  if (_cValue_ == nil) {
    _cValue_ = [JavaUtilCalendar getInstance];
  }
  [((JavaUtilCalendar *) nil_chk(_cValue_)) setTimeInMillisWithLong:[((JavaUtilCalendar *) nil_chk(cal)) getTimeInMillis]];
}

- (void)setValueWithNSString:(NSString *)val {
  if (![aSPOTElement OPTIMIZE_RUNTIME]) {
    [self checkReadOnly];
  }
  if (val == nil) {
    [self spot_clear];
    return;
  }
  if ([((NSString *) nil_chk(val)) sequenceLength] == 0) {
    [self setValueWithJavaUtilDate:[[JavaUtilDate alloc] init]];
  }
  else {
    unichar ch = [val charAtWithInt:0];
    ch = [JavaLangCharacter toUpperCaseWithChar:ch];
    switch (ch) {
      case 'T':
      case 'Y':
      case 'N':
      case 'M':
      case 'W':
      _cValue_ = [RAREUTHelper createCalendarWithNSString:val];
      break;
      default:
      if (_cValue_ == nil) {
        _cValue_ = [JavaUtilCalendar getInstance];
      }
      [SPOTDateTime setValueWithNSString:val withJavaUtilCalendar:_cValue_];
      break;
    }
  }
}

+ (void)setValueWithNSString:(NSString *)val
        withJavaUtilCalendar:(JavaUtilCalendar *)cal {
  int i = 0;
  NSString *time = nil;
  NSString *date = nil;
  int n = [((NSString *) nil_chk(val)) indexOf:'T'];
  if (n == -1) {
    n = [val indexOf:' '];
  }
  if (n != -1) {
    date = [val substring:0 endIndex:n];
    time = [val substring:n + 1];
  }
  else {
    date = val;
  }
  IOSCharArray *data = [((NSString *) nil_chk(date)) toCharArray];
  if ((int) [((IOSCharArray *) nil_chk(data)) count] != 10) {
    @throw [[SPOTException alloc] initWithNSString:[NSString stringWithFormat:@"Invalid date format: %@", val]];
  }
  i = 0;
  int y = (int) [RAREUTSNumber longValueWithCharArray:data withInt:i withInt:4 withBoolean:YES];
  i += 5;
  int m = ((IOSCharArray_Get(data, i) - '0') * 10) + (IOSCharArray_Get(data, i + 1) - '0');
  i += 3;
  int d = ((IOSCharArray_Get(data, i) - '0') * 10) + (IOSCharArray_Get(data, i + 1) - '0');
  if (time != nil) {
    data = [time toCharArray];
    i = 0;
    int h = ((IOSCharArray_Get(nil_chk(data), i) - '0') * 10) + (IOSCharArray_Get(data, i + 1) - '0');
    i += 3;
    int mm = ((IOSCharArray_Get(data, i) - '0') * 10) + (IOSCharArray_Get(data, i + 1) - '0');
    i += 3;
    int sec = 0;
    int tzOffset = 0;
    int mil = 0;
    if (i + 1 < (int) [data count]) {
      sec = ((IOSCharArray_Get(data, i) - '0') * 10) + (IOSCharArray_Get(data, i + 1) - '0');
      i += 2;
      if ((i < (int) [data count]) && (IOSCharArray_Get(data, i) == '.')) {
        i++;
        mil = ((IOSCharArray_Get(data, i) - '0') * 100) + ((IOSCharArray_Get(data, i + 1) - '0') * 10) + (IOSCharArray_Get(data, i + 2) - '0');
        i += 3;
      }
    }
    if ((i < (int) [data count]) && (IOSCharArray_Get(data, i) != 'Z')) {
      tzOffset = 1;
      n = [time indexOf:'-' fromIndex:i];
      if (n == -1) {
        n = [time indexOf:'+' fromIndex:i];
      }
      if ((n == -1) || (n + 6) > (int) [data count]) {
        @throw [[SPOTException alloc] initWithNSString:[NSString stringWithFormat:@"Invalid date format date: %@", val]];
      }
      i = n;
      if (IOSCharArray_Get(data, i++) == '-') {
        tzOffset = -1;
      }
      int hrOff = ((IOSCharArray_Get(data, i) - '0') * 10) + (IOSCharArray_Get(data, i + 1) - '0');
      i += 3;
      int minOff = ((IOSCharArray_Get(data, i) - '0') * 10) + (IOSCharArray_Get(data, i + 1) - '0');
      tzOffset *= (((hrOff * 60) + minOff) * 60 * 1000);
    }
    if (tzOffset != 0) {
      tzOffset -= [((JavaUtilTimeZone *) nil_chk([JavaUtilTimeZone getDefault])) getRawOffset];
      [((JavaUtilCalendar *) nil_chk(cal)) setWithInt:y withInt:m - 1 withInt:d withInt:h withInt:mm withInt:sec];
      [cal setWithInt:JavaUtilCalendar_MILLISECOND withInt:mil];
      [cal addWithInt:JavaUtilCalendar_MILLISECOND withInt:tzOffset];
    }
    else if ((i < (int) [data count]) && (IOSCharArray_Get(data, i) == 'Z')) {
      [((JavaUtilCalendar *) nil_chk(cal)) setWithInt:y withInt:m - 1 withInt:d withInt:h withInt:mm withInt:sec];
      [cal setWithInt:JavaUtilCalendar_MILLISECOND withInt:mil];
      [cal addWithInt:JavaUtilCalendar_MILLISECOND withInt:-[((JavaUtilTimeZone *) nil_chk([JavaUtilTimeZone getDefault])) getRawOffset]];
    }
    else {
      [((JavaUtilCalendar *) nil_chk(cal)) setWithInt:y withInt:m - 1 withInt:d withInt:h withInt:mm withInt:sec];
      [cal addWithInt:JavaUtilCalendar_MILLISECOND withInt:mil];
    }
  }
  else {
    [((JavaUtilCalendar *) nil_chk(cal)) setWithInt:y withInt:m - 1 withInt:d];
  }
}

- (void)setValueExWithNSString:(NSString *)val {
  if ((val != nil) && ([val sequenceLength] > 0)) {
    [self setValueWithRAREUTSNumber:[self numValueNumberWithNSString:val]];
  }
}

- (JavaUtilCalendar *)getCalendar {
  return _cValue_;
}

- (JavaUtilDate *)getDate {
  return (_cValue_ == nil) ? nil : [_cValue_ getTime];
}

+ (NSString *)getDefaultTimeZone {
  return [((JavaUtilTimeZone *) nil_chk([JavaUtilTimeZone getDefault])) getID];
}

- (NSString *)getTimeZone {
  return (_cValue_ == nil) ? [SPOTDateTime getDefaultTimeZone] : [((JavaUtilTimeZone *) nil_chk([_cValue_ getTimeZone])) getID];
}

- (JavaUtilCalendar *)getValue {
  if (_cValue_ == nil) {
    return (_cDefValue_ != nil) ? _cDefValue_ : nil;
  }
  return _cValue_;
}

- (BOOL)isToday {
  if (_cValue_ == nil) {
    return NO;
  }
  JavaUtilCalendar *c = [JavaUtilCalendar getInstance];
  if ([((JavaUtilCalendar *) nil_chk(c)) getWithInt:JavaUtilCalendar_YEAR] != [((JavaUtilCalendar *) nil_chk(_cValue_)) getWithInt:JavaUtilCalendar_YEAR]) {
    return NO;
  }
  if ([c getWithInt:JavaUtilCalendar_MONTH] != [_cValue_ getWithInt:JavaUtilCalendar_MONTH]) {
    return NO;
  }
  if ([c getWithInt:JavaUtilCalendar_DAY_OF_MONTH] != [_cValue_ getWithInt:JavaUtilCalendar_DAY_OF_MONTH]) {
    return NO;
  }
  return YES;
}

- (int)spot_checkRangeValidityEx {
  if ((_cValue_ == nil) && (_cDefValue_ != nil)) {
    return iSPOTConstants_VALUE_NULL_WITH_DEFAULT;
  }
  if ((_cValue_ == nil) && _isOptional_) {
    return iSPOTConstants_VALUE_NULL_AND_OPTIONAL;
  }
  if (_cValue_ == nil) {
    return iSPOTConstants_VALUE_NULL;
  }
  if ((_cRangeMin_ != nil) && [((JavaUtilCalendar *) nil_chk(_cValue_)) beforeWithId:_cRangeMin_]) {
    return iSPOTConstants_VALUE_TO_SMALL;
  }
  if ((_cRangeMax_ != nil) && [((JavaUtilCalendar *) nil_chk(_cValue_)) afterWithId:_cRangeMax_]) {
    return iSPOTConstants_VALUE_TO_BIG;
  }
  return iSPOTConstants_VALUE_OK;
}

- (void)setValuesWithNSString:(NSString *)val
                 withNSString:(NSString *)min
                 withNSString:(NSString *)max
                  withBoolean:(BOOL)optional {
  _isOptional_ = optional;
  [self spot_setRangeWithNSString:min withNSString:max];
  [self setValueWithNSString:val];
}

+ (void)initialize {
  if (self == [SPOTDateTime class]) {
    SPOTDateTime_months_ = [IOSObjectArray arrayWithObjects:(id[]){ @"JAN", @"FEB", @"MAR", @"APR", @"MAY", @"JUN", @"JUL", @"AUG", @"SEP", @"OCT", @"NOV", @"DEC" } count:12 type:[IOSClass classWithClass:[NSString class]]];
    SPOTDateTime_LN_10_ = [JavaLangMath logWithDouble:10];
  }
}

- (void)copyAllFieldsTo:(SPOTDateTime *)other {
  [super copyAllFieldsTo:other];
  other->_cDefValue_ = _cDefValue_;
  other->_cRangeMax_ = _cRangeMax_;
  other->_cRangeMin_ = _cRangeMin_;
  other->_cValue_ = _cValue_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithJavaUtilCalendar:", NULL, NULL, 0x1, "SPOTException" },
    { "initWithRAREUTSNumber:", NULL, NULL, 0x1, "SPOTException" },
    { "initWithNSString:", NULL, NULL, 0x1, "SPOTException" },
    { "initWithJavaUtilCalendar:withBoolean:", NULL, NULL, 0x1, "SPOTException" },
    { "initWithNSString:withBoolean:", NULL, NULL, 0x1, "SPOTException" },
    { "initWithNSString:withNSString:withBoolean:", NULL, NULL, 0x1, "SPOTException" },
    { "initWithNSString:withNSString:withNSString:", NULL, NULL, 0x1, "SPOTException" },
    { "initWithNSString:withNSString:withNSString:withBoolean:", NULL, NULL, 0x1, "SPOTException" },
    { "initWithNSString:withNSString:withNSString:withNSString:withBoolean:", NULL, NULL, 0x1, "SPOTException" },
    { "booleanValue", NULL, "Z", 0x1, NULL },
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "equalsWithASPOTElement:", NULL, "Z", 0x1, NULL },
    { "now", NULL, "LNSString", 0x9, NULL },
    { "nowDateTime", NULL, "LSPOTDateTime", 0x9, NULL },
    { "numberValue", NULL, "LRAREUTSNumber", 0x1, NULL },
    { "numberValueWithJavaUtilCalendar:withRAREUTSNumber:", NULL, "LRAREUTSNumber", 0x9, NULL },
    { "numberValueWithJavaUtilDate:withRAREUTSNumber:", NULL, "LRAREUTSNumber", 0x9, NULL },
    { "numberValueWithNSString:withRAREUTSNumber:", NULL, "LRAREUTSNumber", 0x9, NULL },
    { "spot_getRange", NULL, "LIOSObjectArray", 0x1, NULL },
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
    { "spot_getValue", NULL, "LNSObject", 0x1, NULL },
    { "spot_stringValue", NULL, "LNSString", 0x1, NULL },
    { "spot_stringValueEx", NULL, "LNSString", 0x1, NULL },
    { "to1700DateWithJavaUtilCalendar:withBoolean:", NULL, "LNSString", 0x9, NULL },
    { "toStringWithJavaUtilCalendar:", NULL, "LNSString", 0x9, NULL },
    { "toStringWithJavaUtilCalendar:withBoolean:withBoolean:", NULL, "LNSString", 0x9, NULL },
    { "toStringEx", NULL, "LNSString", 0x1, NULL },
    { "toStringExWithNSString:", NULL, "LNSString", 0x1, NULL },
    { "toStringFMWithBoolean:withBoolean:withBoolean:", NULL, "LNSString", 0x1, NULL },
    { "toStringFMWithJavaLangStringBuilder:withJavaUtilCalendar:withBoolean:withBoolean:withBoolean:", NULL, "LJavaLangStringBuilder", 0x9, NULL },
    { "getCalendar", NULL, "LJavaUtilCalendar", 0x1, NULL },
    { "getDate", NULL, "LJavaUtilDate", 0x1, NULL },
    { "getDefaultTimeZone", NULL, "LNSString", 0x9, NULL },
    { "getTimeZone", NULL, "LNSString", 0x1, NULL },
    { "getValue", NULL, "LJavaUtilCalendar", 0x1, NULL },
    { "isToday", NULL, "Z", 0x1, NULL },
    { "spot_checkRangeValidityEx", NULL, "I", 0x4, NULL },
    { "setValuesWithNSString:withNSString:withNSString:withBoolean:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "DAY_MASK_", NULL, 0x18, "I" },
    { "HHMM_MASK_", NULL, 0x18, "I" },
    { "MILL_MASK_", NULL, 0x18, "I" },
    { "MIN_MASK_", NULL, 0x18, "I" },
    { "MONTH_MASK_", NULL, 0x18, "I" },
    { "SEC_MASK_", NULL, 0x18, "I" },
    { "TIMERR_MASK_", NULL, 0x18, "I" },
    { "YEAR_MASK_", NULL, 0x18, "I" },
    { "months_", NULL, 0x18, "LIOSObjectArray" },
    { "LN_10_", NULL, 0x18, "D" },
    { "_cDefValue_", NULL, 0x0, "LJavaUtilCalendar" },
    { "_cRangeMax_", NULL, 0x0, "LJavaUtilCalendar" },
    { "_cRangeMin_", NULL, 0x0, "LJavaUtilCalendar" },
    { "_cValue_", NULL, 0x0, "LJavaUtilCalendar" },
  };
  static J2ObjcClassInfo _SPOTDateTime = { "SPOTDateTime", "com.appnativa.spot", NULL, 0x1, 38, methods, 14, fields, 0, NULL};
  return &_SPOTDateTime;
}

@end
