//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/iPlatformAppContext.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/ui/iPlatformComponentFactory.h"


@interface RAREiPlatformAppContext : NSObject
@end

@implementation RAREiPlatformAppContext

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getComponentCreator", NULL, "LRAREiPlatformComponentFactory", 0x401, NULL },
    { "alwaysUseHeavyTargets", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiPlatformAppContext = { "iPlatformAppContext", "com.appnativa.rare", NULL, 0x201, 2, methods, 0, NULL, 0, NULL};
  return &_RAREiPlatformAppContext;
}

@end
