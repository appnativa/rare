//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/iExceptionHandler.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/iExceptionHandler.h"
#include "java/io/Writer.h"
#include "java/lang/Throwable.h"


@interface RAREiExceptionHandler : NSObject
@end

@implementation RAREiExceptionHandler

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getErrorWriter", NULL, "LJavaIoWriter", 0x401, NULL },
    { "handleExceptionWithJavaLangThrowable:", NULL, "V", 0x401, NULL },
    { "handleScriptExceptionWithJavaLangThrowable:", NULL, "V", 0x401, NULL },
    { "ignoreExceptionWithNSString:withJavaLangThrowable:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiExceptionHandler = { "iExceptionHandler", "com.appnativa.rare", NULL, 0x201, 4, methods, 0, NULL, 0, NULL};
  return &_RAREiExceptionHandler;
}

@end
