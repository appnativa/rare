//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/MetricAffectingSpan.java
//
//  Created by decoteaud on 5/11/15.
//

#include "android/text/style/MetricAffectingSpan.h"

@implementation RAREMetricAffectingSpan

- (RAREMetricAffectingSpan *)getUnderlying {
  return self;
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getUnderlying", NULL, "LRAREMetricAffectingSpan", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREMetricAffectingSpan = { "MetricAffectingSpan", "android.text.style", NULL, 0x401, 1, methods, 0, NULL, 0, NULL};
  return &_RAREMetricAffectingSpan;
}

@end
@implementation RAREMetricAffectingSpan_Passthrough

- (id)initWithRAREMetricAffectingSpan:(RAREMetricAffectingSpan *)cs {
  if (self = [super init]) {
    mStyle_ = cs;
  }
  return self;
}

- (RAREMetricAffectingSpan *)getUnderlying {
  return [((RAREMetricAffectingSpan *) nil_chk(mStyle_)) getUnderlying];
}

- (void)copyAllFieldsTo:(RAREMetricAffectingSpan_Passthrough *)other {
  [super copyAllFieldsTo:other];
  other->mStyle_ = mStyle_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getUnderlying", NULL, "LRAREMetricAffectingSpan", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREMetricAffectingSpan_Passthrough = { "Passthrough", "android.text.style", "MetricAffectingSpan", 0x8, 1, methods, 0, NULL, 0, NULL};
  return &_RAREMetricAffectingSpan_Passthrough;
}

@end