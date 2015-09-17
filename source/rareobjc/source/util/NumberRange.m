//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/NumberRange.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/util/NumberRange.h"
#include "com/appnativa/util/SNumber.h"
#include "java/lang/Exception.h"
#include "java/lang/NumberFormatException.h"

@implementation RAREUTNumberRange

- (id)initWithNSNumber:(NSNumber *)lowValue
          withNSNumber:(NSNumber *)highValue {
  if (self = [super init]) {
    self->highValue_ = highValue;
    self->lowValue_ = lowValue;
  }
  return self;
}

- (double)doubleValue {
  return [((NSNumber *) nil_chk(lowValue_)) doubleValue];
}

- (float)floatValue {
  return [((NSNumber *) nil_chk(lowValue_)) floatValue];
}

- (int)intValue {
  return [((NSNumber *) nil_chk(lowValue_)) intValue];
}

- (BOOL)isEqual:(id)obj {
  if (obj == self) {
    return YES;
  }
  if (!([obj isKindOfClass:[RAREUTNumberRange class]])) {
    return NO;
  }
  RAREUTNumberRange *r = (RAREUTNumberRange *) check_class_cast(obj, [RAREUTNumberRange class]);
  return [((NSNumber *) nil_chk(((RAREUTNumberRange *) nil_chk(r))->highValue_)) isEqual:highValue_] && [((NSNumber *) nil_chk(r->lowValue_)) isEqual:lowValue_];
}

- (NSUInteger)hash {
  return [((NSNumber *) nil_chk(highValue_)) intValue] ^ [((NSNumber *) nil_chk(lowValue_)) intValue];
}

- (BOOL)isInRangeWithDouble:(double)value {
  return (value >= [((NSNumber *) nil_chk(lowValue_)) doubleValue]) && (value <= [((NSNumber *) nil_chk(highValue_)) doubleValue]);
}

- (BOOL)isInRangeWithFloat:(float)value {
  return (value >= [((NSNumber *) nil_chk(lowValue_)) floatValue]) && (value <= [((NSNumber *) nil_chk(highValue_)) floatValue]);
}

- (BOOL)isInRangeWithInt:(int)value {
  return (value >= [((NSNumber *) nil_chk(lowValue_)) intValue]) && (value <= [((NSNumber *) nil_chk(highValue_)) intValue]);
}

- (BOOL)isInRangeWithLong:(long long int)value {
  return (value >= [((NSNumber *) nil_chk(lowValue_)) longLongValue]) && (value <= [((NSNumber *) nil_chk(highValue_)) longLongValue]);
}

- (long long int)longLongValue {
  return [((NSNumber *) nil_chk(lowValue_)) longLongValue];
}

- (NSString *)description {
  return [NSString stringWithFormat:@"%@/%@", [((NSNumber *) nil_chk(lowValue_)) description], [((NSNumber *) nil_chk(highValue_)) description]];
}

+ (RAREUTNumberRange *)valueOfWithNSString:(NSString *)range {
  if (range == nil) {
    return nil;
  }
  @try {
    int n = [((NSString *) nil_chk(range)) indexOf:'/'];
    return [[RAREUTNumberRange alloc] initWithNSNumber:[[RAREUTSNumber alloc] initWithNSString:[range substring:0 endIndex:n]] withNSNumber:[[RAREUTSNumber alloc] initWithNSString:[range substring:n + 1]]];
  }
  @catch (JavaLangException *e) {
    @throw [[JavaLangNumberFormatException alloc] initWithNSString:range];
  }
}

- (NSNumber *)getHighValue {
  return highValue_;
}

- (NSNumber *)getLowValue {
  return lowValue_;
}

- (void)copyAllFieldsTo:(RAREUTNumberRange *)other {
  [super copyAllFieldsTo:other];
  other->highValue_ = highValue_;
  other->lowValue_ = lowValue_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isInRangeWithDouble:", NULL, "Z", 0x1, NULL },
    { "isInRangeWithFloat:", NULL, "Z", 0x1, NULL },
    { "isInRangeWithInt:", NULL, "Z", 0x1, NULL },
    { "isInRangeWithLong:", NULL, "Z", 0x1, NULL },
    { "valueOfWithNSString:", NULL, "LRAREUTNumberRange", 0x9, NULL },
    { "getHighValue", NULL, "LNSNumber", 0x1, NULL },
    { "getLowValue", NULL, "LNSNumber", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "highValue_", NULL, 0x4, "LNSNumber" },
    { "lowValue_", NULL, 0x4, "LNSNumber" },
  };
  static J2ObjcClassInfo _RAREUTNumberRange = { "NumberRange", "com.appnativa.util", NULL, 0x1, 7, methods, 2, fields, 0, NULL};
  return &_RAREUTNumberRange;
}

@end
