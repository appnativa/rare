//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/StringReaderEx.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/util/StringReaderEx.h"

@implementation RAREUTStringReaderEx

- (id)initWithNSString:(NSString *)s {
  if (self = [super initWithNSString:s]) {
    theString_ = s;
  }
  return self;
}

- (NSString *)getString {
  return theString_;
}

- (void)copyAllFieldsTo:(RAREUTStringReaderEx *)other {
  [super copyAllFieldsTo:other];
  other->theString_ = theString_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getString", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "theString_", NULL, 0x4, "LNSString" },
  };
  static J2ObjcClassInfo _RAREUTStringReaderEx = { "StringReaderEx", "com.appnativa.util", NULL, 0x1, 1, methods, 1, fields, 0, NULL};
  return &_RAREUTStringReaderEx;
}

@end
