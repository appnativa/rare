//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/EqualityFilter.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/util/EqualityFilter.h"
#include "com/appnativa/util/iStringConverter.h"

@implementation RAREUTEqualityFilter

- (id)init {
  return [self initRAREUTEqualityFilterWithNSString:nil withBoolean:NO];
}

- (id)initWithNSString:(NSString *)value {
  return [self initRAREUTEqualityFilterWithNSString:value withBoolean:NO];
}

- (id)initRAREUTEqualityFilterWithNSString:(NSString *)value
                               withBoolean:(BOOL)ignorecase {
  if (self = [super init]) {
    theValue_ = value;
    ignoreCase_ = ignorecase;
  }
  return self;
}

- (id)initWithNSString:(NSString *)value
           withBoolean:(BOOL)ignorecase {
  return [self initRAREUTEqualityFilterWithNSString:value withBoolean:ignorecase];
}

- (NSString *)getValue {
  return theValue_;
}

- (BOOL)isIgnoreCase {
  return ignoreCase_;
}

- (BOOL)passesWithId:(id)value
withRAREUTiStringConverter:(id<RAREUTiStringConverter>)converter {
  if ((converter != nil) && (value != nil)) {
    value = [converter toStringWithId:value];
  }
  if (theValue_ == value) {
    return YES;
  }
  if ((value == nil) || (theValue_ == nil)) {
    return value == theValue_;
  }
  NSString *s = ([value isKindOfClass:[NSString class]]) ? (NSString *) check_class_cast(value, [NSString class]) : [nil_chk(value) description];
  if (ignoreCase_) {
    return [((NSString *) nil_chk(theValue_)) equalsIgnoreCase:s];
  }
  else {
    return [((NSString *) nil_chk(theValue_)) isEqual:s];
  }
}

- (void)setIgnoreCaseWithBoolean:(BOOL)ignoreCase {
  self->ignoreCase_ = ignoreCase;
}

- (void)setValueWithNSString:(NSString *)theValue {
  self->theValue_ = theValue;
}

- (void)copyAllFieldsTo:(RAREUTEqualityFilter *)other {
  [super copyAllFieldsTo:other];
  other->ignoreCase_ = ignoreCase_;
  other->theValue_ = theValue_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getValue", NULL, "LNSString", 0x1, NULL },
    { "isIgnoreCase", NULL, "Z", 0x1, NULL },
    { "passesWithId:withRAREUTiStringConverter:", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREUTEqualityFilter = { "EqualityFilter", "com.appnativa.util", NULL, 0x1, 3, methods, 0, NULL, 0, NULL};
  return &_RAREUTEqualityFilter;
}

@end
