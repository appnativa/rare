//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iProgressBar.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iProgressBar.h"


@interface RAREiProgressBar : NSObject
@end

@implementation RAREiProgressBar

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setIndeterminateWithBoolean:", NULL, "V", 0x401, NULL },
    { "setMaximumWithInt:", NULL, "V", 0x401, NULL },
    { "setMinimumWithInt:", NULL, "V", 0x401, NULL },
    { "setValueWithInt:", NULL, "V", 0x401, NULL },
    { "getComponent", NULL, "LRAREiPlatformComponent", 0x401, NULL },
    { "setGraphicSizeWithInt:", NULL, "V", 0x401, NULL },
    { "getValue", NULL, "I", 0x401, NULL },
    { "isIndeterminate", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiProgressBar = { "iProgressBar", "com.appnativa.rare.ui", NULL, 0x201, 8, methods, 0, NULL, 0, NULL};
  return &_RAREiProgressBar;
}

@end