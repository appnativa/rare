//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/DynamicDrawableSpan.java
//
//  Created by decoteaud on 5/11/15.
//

#include "android/text/style/DynamicDrawableSpan.h"

@implementation RAREDynamicDrawableSpan

+ (int)ALIGN_BOTTOM {
  return RAREDynamicDrawableSpan_ALIGN_BOTTOM;
}

+ (int)ALIGN_BASELINE {
  return RAREDynamicDrawableSpan_ALIGN_BASELINE;
}

- (id)init {
  if (self = [super init]) {
    mVerticalAlignment_ = RAREDynamicDrawableSpan_ALIGN_BOTTOM;
  }
  return self;
}

- (id)initWithInt:(int)verticalAlignment {
  if (self = [super init]) {
    mVerticalAlignment_ = verticalAlignment;
  }
  return self;
}

- (int)getVerticalAlignment {
  return mVerticalAlignment_;
}

- (void)copyAllFieldsTo:(RAREDynamicDrawableSpan *)other {
  [super copyAllFieldsTo:other];
  other->mVerticalAlignment_ = mVerticalAlignment_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithInt:", NULL, NULL, 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "ALIGN_BOTTOM_", NULL, 0x19, "I" },
    { "ALIGN_BASELINE_", NULL, 0x19, "I" },
    { "mVerticalAlignment_", NULL, 0x14, "I" },
  };
  static J2ObjcClassInfo _RAREDynamicDrawableSpan = { "DynamicDrawableSpan", "android.text.style", NULL, 0x401, 1, methods, 3, fields, 0, NULL};
  return &_RAREDynamicDrawableSpan;
}

@end
