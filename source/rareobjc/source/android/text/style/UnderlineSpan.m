//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/UnderlineSpan.java
//
//  Created by decoteaud on 5/11/15.
//

#include "android/text/TextUtils.h"
#include "android/text/style/UnderlineSpan.h"

@implementation RAREUnderlineSpan

- (id)init {
  return [super init];
}

- (int)getSpanTypeId {
  return AndroidTextTextUtils_UNDERLINE_SPAN;
}

- (int)describeContents {
  return 0;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREUnderlineSpan = { "UnderlineSpan", "android.text.style", NULL, 0x1, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREUnderlineSpan;
}

@end