//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/iStreamable.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/util/iStreamable.h"
#include "java/io/IOException.h"
#include "java/io/InputStream.h"
#include "java/io/OutputStream.h"


@interface RAREUTiStreamable : NSObject
@end

@implementation RAREUTiStreamable

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "fromStreamWithJavaIoInputStream:", NULL, "V", 0x401, "JavaIoIOException" },
    { "toStreamWithJavaIoOutputStream:", NULL, "V", 0x401, "JavaIoIOException" },
  };
  static J2ObjcClassInfo _RAREUTiStreamable = { "iStreamable", "com.appnativa.util", NULL, 0x201, 2, methods, 0, NULL, 0, NULL};
  return &_RAREUTiStreamable;
}

@end
