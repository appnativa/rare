//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/BackgroundColorSpan.java
//
//  Created by decoteaud on 5/11/15.
//

#include "android/text/TextUtils.h"
#include "android/text/style/BackgroundColorSpan.h"

@implementation RAREBackgroundColorSpan

- (id)initWithInt:(int)color {
  if (self = [super init]) {
    mColor_ = color;
  }
  return self;
}

- (int)getSpanTypeId {
  return AndroidTextTextUtils_BACKGROUND_COLOR_SPAN;
}

- (int)getBackgroundColor {
  return mColor_;
}

- (void)copyAllFieldsTo:(RAREBackgroundColorSpan *)other {
  [super copyAllFieldsTo:other];
  other->mColor_ = mColor_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "mColor_", NULL, 0x12, "I" },
  };
  static J2ObjcClassInfo _RAREBackgroundColorSpan = { "BackgroundColorSpan", "android.text.style", NULL, 0x1, 0, NULL, 1, fields, 0, NULL};
  return &_RAREBackgroundColorSpan;
}

@end