//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iWindow.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/event/iWindowListener.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformMenuBar.h"
#include "com/appnativa/rare/ui/iStatusBar.h"
#include "com/appnativa/rare/ui/iToolBarHolder.h"
#include "com/appnativa/rare/ui/iWindow.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/viewer/iTarget.h"


@interface RAREiWindow : NSObject
@end

@implementation RAREiWindow

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addWindowListenerWithRAREiWindowListener:", NULL, "V", 0x401, NULL },
    { "center", NULL, "V", 0x401, NULL },
    { "close", NULL, "V", 0x401, NULL },
    { "dispose", NULL, "V", 0x401, NULL },
    { "disposeOfWindow", NULL, "V", 0x401, NULL },
    { "hideWindow", NULL, "V", 0x401, NULL },
    { "moveByWithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "moveToWithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "pack", NULL, "V", 0x401, NULL },
    { "removeWindowListenerWithRAREiWindowListener:", NULL, "V", 0x401, NULL },
    { "showWindow", NULL, "V", 0x401, NULL },
    { "showWindowWithInt:withInt:", NULL, "V", 0x401, NULL },
    { "toBack", NULL, "V", 0x401, NULL },
    { "toFront", NULL, "V", 0x401, NULL },
    { "update", NULL, "V", 0x401, NULL },
    { "setBoundsWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "setCanCloseWithBoolean:", NULL, "V", 0x401, NULL },
    { "setLocationWithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "setMenuBarWithRAREiPlatformMenuBar:", NULL, "V", 0x401, NULL },
    { "setResizableWithBoolean:", NULL, "V", 0x401, NULL },
    { "setSizeWithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "setStatusBarWithRAREiStatusBar:", NULL, "V", 0x401, NULL },
    { "setTitleWithNSString:", NULL, "V", 0x401, NULL },
    { "setToolBarHolderWithRAREiToolBarHolder:", NULL, "V", 0x401, NULL },
    { "getBounds", NULL, "LRAREUIRectangle", 0x401, NULL },
    { "getComponent", NULL, "LRAREiPlatformComponent", 0x401, NULL },
    { "getHeight", NULL, "I", 0x401, NULL },
    { "getMenuBar", NULL, "LRAREiPlatformMenuBar", 0x401, NULL },
    { "getScreenX", NULL, "I", 0x401, NULL },
    { "getScreenY", NULL, "I", 0x401, NULL },
    { "getSize", NULL, "LRAREUIDimension", 0x401, NULL },
    { "getStatusBar", NULL, "LRAREiStatusBar", 0x401, NULL },
    { "getTarget", NULL, "LRAREiTarget", 0x401, NULL },
    { "getTargetName", NULL, "LNSString", 0x401, NULL },
    { "getTitle", NULL, "LNSString", 0x401, NULL },
    { "getToolBarHolder", NULL, "LRAREiToolBarHolder", 0x401, NULL },
    { "getUIWindow", NULL, "LNSObject", 0x401, NULL },
    { "getViewer", NULL, "LRAREiContainer", 0x401, NULL },
    { "getWidth", NULL, "I", 0x401, NULL },
    { "getInnerSizeWithRAREUIDimension:", NULL, "LRAREUIDimension", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiWindow = { "iWindow", "com.appnativa.rare.ui", NULL, 0x201, 40, methods, 0, NULL, 0, NULL};
  return &_RAREiWindow;
}

@end
