//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iStatusBar.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iStatusBar.h"


@interface RAREiStatusBar : NSObject
@end

@implementation RAREiStatusBar

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "configureForPopup", NULL, "V", 0x401, NULL },
    { "dispose", NULL, "V", 0x401, NULL },
    { "progressAbort", NULL, "V", 0x401, NULL },
    { "progressComplete", NULL, "V", 0x401, NULL },
    { "progressStartWithBoolean:", NULL, "V", 0x401, NULL },
    { "progressStartWithBoolean:withNSString:withId:", NULL, "V", 0x401, NULL },
    { "progressStartWithBoolean:withNSString:withId:withId:", NULL, "V", 0x401, NULL },
    { "progressStartIndeterminateWithBoolean:", NULL, "V", 0x401, NULL },
    { "showMessageWithNSString:", NULL, "V", 0x401, NULL },
    { "toggleVisibility", NULL, "V", 0x401, NULL },
    { "setCancelActionWithId:", NULL, "V", 0x401, NULL },
    { "setInsertOverwriteWithBoolean:", NULL, "V", 0x401, NULL },
    { "setInsertOverwriteEnabledWithBoolean:", NULL, "V", 0x401, NULL },
    { "setItemEnabledWithNSString:withBoolean:", NULL, "V", 0x401, NULL },
    { "setItemValueWithNSString:withId:", NULL, "V", 0x401, NULL },
    { "setMaxHistoryWithInt:", NULL, "V", 0x401, NULL },
    { "setProgressStatusWithNSString:", NULL, "V", 0x401, NULL },
    { "setProgressUpdateWithInt:", NULL, "V", 0x401, NULL },
    { "setVisibleWithBoolean:", NULL, "V", 0x401, NULL },
    { "getComponent", NULL, "LRAREiPlatformComponent", 0x401, NULL },
    { "getItemValueWithNSString:", NULL, "LNSObject", 0x401, NULL },
    { "getMaxHistory", NULL, "I", 0x401, NULL },
    { "getMessage", NULL, "LNSString", 0x401, NULL },
    { "isItemEnabledWithNSString:", NULL, "Z", 0x401, NULL },
    { "isProgressBarShowing", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiStatusBar = { "iStatusBar", "com.appnativa.rare.ui", NULL, 0x201, 25, methods, 0, NULL, 0, NULL};
  return &_RAREiStatusBar;
}

@end
