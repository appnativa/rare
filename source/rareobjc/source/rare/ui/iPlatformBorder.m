//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/iPlatformBorder.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "java/lang/StringBuilder.h"


@interface RAREiPlatformBorder : NSObject
@end

@implementation RAREiPlatformBorder

- (id)copyWithZoneWithNSZone:(NSZone *)zone {
  return [self clone];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "toCSS", NULL, "LNSString", 0x401, NULL },
    { "toCSSWithJavaLangStringBuilder:", NULL, "LJavaLangStringBuilder", 0x401, NULL },
    { "usesPath", NULL, "Z", 0x401, NULL },
    { "getPathWidth", NULL, "F", 0x401, NULL },
    { "getPathOffset", NULL, "F", 0x401, NULL },
    { "canUseMainLayer", NULL, "Z", 0x401, NULL },
    { "requiresPanel", NULL, "Z", 0x401, NULL },
    { "getModCount", NULL, "I", 0x401, NULL },
    { "updateModCount", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiPlatformBorder = { "iPlatformBorder", "com.appnativa.rare.ui", NULL, 0x201, 9, methods, 0, NULL, 0, NULL};
  return &_RAREiPlatformBorder;
}

@end
