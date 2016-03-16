//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iPopup.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/WindowPane.h"
#include "com/appnativa/rare/ui/effects/iPlatformAnimator.h"
#include "com/appnativa/rare/ui/event/iPopupMenuListener.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPopup.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "java/util/Map.h"


@interface RAREiPopup : NSObject
@end

@implementation RAREiPopup

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addPopupMenuListenerWithRAREiPopupMenuListener:", NULL, "V", 0x401, NULL },
    { "cancelPopupWithBoolean:", NULL, "V", 0x401, NULL },
    { "dispose", NULL, "V", 0x401, NULL },
    { "hidePopup", NULL, "V", 0x401, NULL },
    { "removePopupMenuListenerWithRAREiPopupMenuListener:", NULL, "V", 0x401, NULL },
    { "showPopup", NULL, "V", 0x401, NULL },
    { "showPopupWithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "showPopupWithRAREiPlatformComponent:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "setComponentPainterWithRAREiPlatformComponentPainter:", NULL, "V", 0x401, NULL },
    { "setContentWithRAREiPlatformComponent:", NULL, "V", 0x401, NULL },
    { "setFocusableWithBoolean:", NULL, "V", 0x401, NULL },
    { "setMovableWithBoolean:", NULL, "V", 0x401, NULL },
    { "setOptionsWithJavaUtilMap:", NULL, "V", 0x401, NULL },
    { "setPopupLocationWithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "setPopupOwnerWithRAREiPlatformComponent:", NULL, "V", 0x401, NULL },
    { "setSizeWithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "setTimeoutWithInt:", NULL, "V", 0x401, NULL },
    { "setTitleWithNSString:", NULL, "V", 0x401, NULL },
    { "setTransientWithBoolean:", NULL, "V", 0x401, NULL },
    { "setViewerWithRAREiViewer:", NULL, "LRAREiViewer", 0x401, NULL },
    { "getPreferredSizeWithRAREUIDimension:", NULL, "V", 0x401, NULL },
    { "isFocusable", NULL, "Z", 0x401, NULL },
    { "isShowing", NULL, "Z", 0x401, NULL },
    { "isTransient", NULL, "Z", 0x401, NULL },
    { "showModalPopup", NULL, "V", 0x401, NULL },
    { "getWindowPane", NULL, "LRAREWindowPane", 0x401, NULL },
    { "setAnimatorWithRAREiPlatformAnimator:", NULL, "V", 0x401, NULL },
    { "setBackgroundColorWithRAREUIColor:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiPopup = { "iPopup", "com.appnativa.rare.ui", NULL, 0x201, 28, methods, 0, NULL, 0, NULL};
  return &_RAREiPopup;
}

@end
