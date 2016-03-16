//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/TypefaceSpan.java
//
//  Created by decoteaud on 3/11/16.
//

#include "android/text/TextUtils.h"
#include "android/text/style/TypefaceSpan.h"

@implementation RARETypefaceSpan

- (id)initWithNSString:(NSString *)family {
  if (self = [super init]) {
    mFamily_ = family;
  }
  return self;
}

- (int)getSpanTypeId {
  return AndroidTextTextUtils_TYPEFACE_SPAN;
}

- (NSString *)getFamily {
  return mFamily_;
}

- (void)copyAllFieldsTo:(RARETypefaceSpan *)other {
  [super copyAllFieldsTo:other];
  other->mFamily_ = mFamily_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getFamily", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "mFamily_", NULL, 0x12, "LNSString" },
  };
  static J2ObjcClassInfo _RARETypefaceSpan = { "TypefaceSpan", "android.text.style", NULL, 0x1, 1, methods, 1, fields, 0, NULL};
  return &_RARETypefaceSpan;
}

@end
