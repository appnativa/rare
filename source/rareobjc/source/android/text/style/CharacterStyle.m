//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/CharacterStyle.java
//
//  Created by decoteaud on 12/8/15.
//

#include "android/text/style/CharacterStyle.h"
#include "android/text/style/MetricAffectingSpan.h"

@implementation RARECharacterStyle

+ (RARECharacterStyle *)wrapWithRARECharacterStyle:(RARECharacterStyle *)cs {
  if ([cs isKindOfClass:[RAREMetricAffectingSpan class]]) {
    return [[RAREMetricAffectingSpan_Passthrough alloc] initWithRAREMetricAffectingSpan:(RAREMetricAffectingSpan *) check_class_cast(cs, [RAREMetricAffectingSpan class])];
  }
  else {
    return [[RARECharacterStyle_Passthrough alloc] initWithRARECharacterStyle:cs];
  }
}

- (RARECharacterStyle *)getUnderlying {
  return self;
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "wrapWithRARECharacterStyle:", NULL, "LRARECharacterStyle", 0x9, NULL },
    { "getUnderlying", NULL, "LRARECharacterStyle", 0x1, NULL },
  };
  static J2ObjcClassInfo _RARECharacterStyle = { "CharacterStyle", "android.text.style", NULL, 0x401, 2, methods, 0, NULL, 0, NULL};
  return &_RARECharacterStyle;
}

@end
@implementation RARECharacterStyle_Passthrough

- (id)initWithRARECharacterStyle:(RARECharacterStyle *)cs {
  if (self = [super init]) {
    mStyle_ = cs;
  }
  return self;
}

- (RARECharacterStyle *)getUnderlying {
  return [((RARECharacterStyle *) nil_chk(mStyle_)) getUnderlying];
}

- (void)copyAllFieldsTo:(RARECharacterStyle_Passthrough *)other {
  [super copyAllFieldsTo:other];
  other->mStyle_ = mStyle_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getUnderlying", NULL, "LRARECharacterStyle", 0x1, NULL },
  };
  static J2ObjcClassInfo _RARECharacterStyle_Passthrough = { "Passthrough", "android.text.style", "CharacterStyle", 0xa, 1, methods, 0, NULL, 0, NULL};
  return &_RARECharacterStyle_Passthrough;
}

@end
