//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/MutableInteger.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/util/MutableInteger.h"
#include "java/lang/Integer.h"

@implementation RAREUTMutableInteger

- (id)initWithInt:(int)value {
  if (self = [super init]) {
    self->value_ = value;
  }
  return self;
}

- (RAREUTMutableInteger *)addWithInt:(int)v {
  value_ += v;
  return self;
}

- (id)clone {
  return [[RAREUTMutableInteger alloc] initWithInt:value_];
}

- (int)compareToWithId:(id)o {
  if ([o isKindOfClass:[JavaLangInteger class]]) {
    return value_ - [((JavaLangInteger *) check_class_cast(o, [JavaLangInteger class])) intValue];
  }
  return value_ - [((RAREUTMutableInteger *) check_class_cast(o, [RAREUTMutableInteger class])) intValue];
}

- (BOOL)isEqual:(id)obj {
  if ([obj isKindOfClass:[RAREUTMutableInteger class]]) {
    return value_ == [((RAREUTMutableInteger *) check_class_cast(obj, [RAREUTMutableInteger class])) intValue];
  }
  return NO;
}

- (int)getAndIncrement {
  return value_++;
}

- (int)getAndDecrement {
  return ++value_;
}

- (int)getValue {
  return value_;
}

- (int)get {
  return value_;
}

- (NSUInteger)hash {
  return value_;
}

- (int)incrementAndGet {
  return ++value_;
}

- (int)intValue {
  return value_;
}

- (void)setValueWithInt:(int)v {
  value_ = v;
}

- (NSString *)description {
  return [NSString valueOfInt:value_];
}

- (int)decrementAndGet {
  return --value_;
}

- (void)setWithInt:(int)value {
  self->value_ = value;
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RAREUTMutableInteger *)other {
  [super copyAllFieldsTo:other];
  other->value_ = value_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addWithInt:", NULL, "LRAREUTMutableInteger", 0x1, NULL },
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "getAndIncrement", NULL, "I", 0x11, NULL },
    { "getAndDecrement", NULL, "I", 0x11, NULL },
    { "getValue", NULL, "I", 0x11, NULL },
    { "get", NULL, "I", 0x11, NULL },
    { "incrementAndGet", NULL, "I", 0x11, NULL },
    { "intValue", NULL, "I", 0x11, NULL },
    { "setValueWithInt:", NULL, "V", 0x11, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "value_", NULL, 0x4, "I" },
  };
  static J2ObjcClassInfo _RAREUTMutableInteger = { "MutableInteger", "com.appnativa.util", NULL, 0x1, 9, methods, 1, fields, 0, NULL};
  return &_RAREUTMutableInteger;
}

@end
