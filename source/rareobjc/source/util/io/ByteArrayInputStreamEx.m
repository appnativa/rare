//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src-apple-porting/com/appnativa/util/io/ByteArrayInputStreamEx.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSByteArray.h"
#include "com/appnativa/util/io/ByteArrayInputStreamEx.h"

@implementation RAREUTByteArrayInputStreamEx

- (id)initWithByteArray:(IOSByteArray *)buf {
  return [super initWithByteArray:buf];
}

- (IOSByteArray *)getArray {
  return buf_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getArray", NULL, "LIOSByteArray", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREUTByteArrayInputStreamEx = { "ByteArrayInputStreamEx", "com.appnativa.util.io", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RAREUTByteArrayInputStreamEx;
}

@end
