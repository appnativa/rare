//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/URLSpan.java
//
//  Created by decoteaud on 5/11/15.
//

#include "android/text/TextUtils.h"
#include "android/text/style/URLSpan.h"

@implementation RAREURLSpan

- (id)initWithNSString:(NSString *)url {
  if (self = [super init]) {
    mURL_ = url;
  }
  return self;
}

- (int)getSpanTypeId {
  return AndroidTextTextUtils_URL_SPAN;
}

- (int)describeContents {
  return 0;
}

- (NSString *)getURL {
  return mURL_;
}

- (void)copyAllFieldsTo:(RAREURLSpan *)other {
  [super copyAllFieldsTo:other];
  other->mURL_ = mURL_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getURL", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "mURL_", NULL, 0x12, "LNSString" },
  };
  static J2ObjcClassInfo _RAREURLSpan = { "URLSpan", "android.text.style", NULL, 0x1, 1, methods, 1, fields, 0, NULL};
  return &_RAREURLSpan;
}

@end