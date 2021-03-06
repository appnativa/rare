//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/RelativeSizeSpan.java
//
//  Created by decoteaud on 3/11/16.
//

#include "android/text/TextUtils.h"
#include "android/text/style/RelativeSizeSpan.h"

@implementation RARERelativeSizeSpan

- (id)initWithFloat:(float)proportion {
  if (self = [super init]) {
    mProportion_ = proportion;
  }
  return self;
}

- (int)getSpanTypeId {
  return AndroidTextTextUtils_RELATIVE_SIZE_SPAN;
}

- (int)describeContents {
  return 0;
}

- (float)getSizeChange {
  return mProportion_;
}

- (void)copyAllFieldsTo:(RARERelativeSizeSpan *)other {
  [super copyAllFieldsTo:other];
  other->mProportion_ = mProportion_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "mProportion_", NULL, 0x12, "F" },
  };
  static J2ObjcClassInfo _RARERelativeSizeSpan = { "RelativeSizeSpan", "android.text.style", NULL, 0x1, 0, NULL, 1, fields, 0, NULL};
  return &_RARERelativeSizeSpan;
}

@end
