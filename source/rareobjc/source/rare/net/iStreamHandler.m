//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/net/iStreamHandler.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/net/iStreamHandler.h"
#include "java/io/IOException.h"
#include "java/net/URL.h"
#include "java/net/URLConnection.h"


@interface RAREiStreamHandler : NSObject
@end

@implementation RAREiStreamHandler

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "openConnectionWithJavaNetURL:", NULL, "LJavaNetURLConnection", 0x401, "JavaIoIOException" },
    { "sameFileWithJavaNetURL:withJavaNetURL:", NULL, "Z", 0x401, NULL },
    { "toExternalFormWithJavaNetURL:", NULL, "LNSString", 0x401, NULL },
    { "toStringWithJavaNetURL:", NULL, "LNSString", 0x401, NULL },
    { "getFileWithJavaNetURL:", NULL, "LNSString", 0x401, NULL },
    { "getHostWithJavaNetURL:", NULL, "LNSString", 0x401, NULL },
    { "getPathWithJavaNetURL:", NULL, "LNSString", 0x401, NULL },
    { "getProtocolWithJavaNetURL:", NULL, "LNSString", 0x401, NULL },
    { "getQueryWithJavaNetURL:", NULL, "LNSString", 0x401, NULL },
    { "getRefWithJavaNetURL:", NULL, "LNSString", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiStreamHandler = { "iStreamHandler", "com.appnativa.rare.net", NULL, 0x201, 10, methods, 0, NULL, 0, NULL};
  return &_RAREiStreamHandler;
}

@end
