//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/iFileResolver.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/util/iFileResolver.h"
#include "java/io/IOException.h"
#include "java/io/InputStream.h"
#include "java/io/Reader.h"


@interface RAREUTiFileResolver : NSObject
@end

@implementation RAREUTiFileResolver

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getReaderWithNSString:", NULL, "LJavaIoReader", 0x401, "JavaIoIOException" },
    { "getStreamWithNSString:", NULL, "LJavaIoInputStream", 0x401, "JavaIoIOException" },
  };
  static J2ObjcClassInfo _RAREUTiFileResolver = { "iFileResolver", "com.appnativa.util", NULL, 0x201, 2, methods, 0, NULL, 0, NULL};
  return &_RAREUTiFileResolver;
}

@end
