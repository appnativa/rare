//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/effects/iTransitionAnimator.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/iFunctionCallback.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/effects/iPlatformAnimator.h"
#include "com/appnativa/rare/ui/effects/iTransitionAnimator.h"
#include "com/appnativa/rare/ui/iParentComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"


@interface RAREiTransitionAnimator : NSObject
@end

@implementation RAREiTransitionAnimator

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "animateWithRAREiParentComponent:withRAREUIRectangle:withRAREiFunctionCallback:", NULL, "V", 0x401, NULL },
    { "cancel", NULL, "V", 0x401, NULL },
    { "dispose", NULL, "V", 0x401, NULL },
    { "setIncommingComponentWithRAREiPlatformComponent:", NULL, "V", 0x401, NULL },
    { "setOutgoingComponentWithRAREiPlatformComponent:", NULL, "V", 0x401, NULL },
    { "getInAnimator", NULL, "LRAREiPlatformAnimator", 0x401, NULL },
    { "getOutAnimator", NULL, "LRAREiPlatformAnimator", 0x401, NULL },
    { "isRunning", NULL, "Z", 0x401, NULL },
    { "isAutoDispose", NULL, "Z", 0x401, NULL },
    { "setAutoDisposeWithBoolean:", NULL, "V", 0x401, NULL },
    { "setCallbackWithRAREiFunctionCallback:", NULL, "V", 0x401, NULL },
    { "setOutgoingPersistsWithBoolean:", NULL, "V", 0x401, NULL },
    { "setOutgoingHiddenWithBoolean:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiTransitionAnimator = { "iTransitionAnimator", "com.appnativa.rare.ui.effects", NULL, 0x201, 13, methods, 0, NULL, 0, NULL};
  return &_RAREiTransitionAnimator;
}

@end
