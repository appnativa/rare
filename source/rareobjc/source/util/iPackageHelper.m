//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/iPackageHelper.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "com/appnativa/util/iPackageHelper.h"
#include "java/lang/ClassNotFoundException.h"


@interface RAREUTiPackageHelper : NSObject
@end

@implementation RAREUTiPackageHelper

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "loadClassWithNSString:", NULL, "LIOSClass", 0x401, "JavaLangClassNotFoundException" },
    { "getPackageNameWithIOSClass:", NULL, "LNSString", 0x401, NULL },
    { "getFieldClassWithId:", NULL, "LIOSClass", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREUTiPackageHelper = { "iPackageHelper", "com.appnativa.util", NULL, 0x201, 3, methods, 0, NULL, 0, NULL};
  return &_RAREUTiPackageHelper;
}

@end
