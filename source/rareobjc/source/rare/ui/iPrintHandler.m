//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iPrintHandler.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPrintHandler.h"
#include "com/appnativa/rare/ui/print/iPageSetup.h"


@interface RAREiPrintHandler : NSObject
@end

@implementation RAREiPrintHandler

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isPrintPreviewSupported", NULL, "Z", 0x401, NULL },
    { "createPageSetupWithRAREiPlatformComponent:", NULL, "LRAREiPageSetup", 0x401, NULL },
    { "pageSetupWithRAREiPlatformComponent:withRAREiPageSetup:", NULL, "V", 0x401, NULL },
    { "printWithRAREiPlatformComponent:withRAREiPageSetup:", NULL, "V", 0x401, NULL },
    { "printPreviewWithRAREiPlatformComponent:withRAREiPageSetup:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiPrintHandler = { "iPrintHandler", "com.appnativa.rare.ui", NULL, 0x201, 5, methods, 0, NULL, 0, NULL};
  return &_RAREiPrintHandler;
}

@end
