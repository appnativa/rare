//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/ScaleXSpan.java
//
//  Created by decoteaud on 5/11/15.
//

#include "android/text/TextUtils.h"
#include "android/text/style/ScaleXSpan.h"

@implementation RAREScaleXSpan

- (id)initWithFloat:(float)proportion {
  if (self = [super init]) {
    mProportion_ = proportion;
  }
  return self;
}

- (int)getSpanTypeId {
  return AndroidTextTextUtils_SCALE_X_SPAN;
}

- (int)describeContents {
  return 0;
}

- (float)getScaleX {
  return mProportion_;
}

- (void)copyAllFieldsTo:(RAREScaleXSpan *)other {
  [super copyAllFieldsTo:other];
  other->mProportion_ = mProportion_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "mProportion_", NULL, 0x12, "F" },
  };
  static J2ObjcClassInfo _RAREScaleXSpan = { "ScaleXSpan", "android.text.style", NULL, 0x1, 0, NULL, 1, fields, 0, NULL};
  return &_RAREScaleXSpan;
}

@end