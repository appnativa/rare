//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iMenuBarComponent.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/ui/UIMenuItem.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iMenuBarComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "java/lang/CharSequence.h"


@interface RAREiMenuBarComponent : NSObject
@end

@implementation RAREiMenuBarComponent

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addWithRAREUIMenuItem:", NULL, "V", 0x401, NULL },
    { "addActionListenerWithRAREiActionListener:", NULL, "V", 0x401, NULL },
    { "removeWithRAREUIMenuItem:", NULL, "V", 0x401, NULL },
    { "removeAll", NULL, "V", 0x401, NULL },
    { "removeActionListenerWithRAREiActionListener:", NULL, "V", 0x401, NULL },
    { "setTitleWithJavaLangCharSequence:", NULL, "V", 0x401, NULL },
    { "setTitleIconWithRAREiPlatformIcon:", NULL, "V", 0x401, NULL },
    { "setVisibleWithBoolean:", NULL, "V", 0x401, NULL },
    { "getTitleComponent", NULL, "LRAREiActionComponent", 0x401, NULL },
    { "hasTitleComponent", NULL, "Z", 0x401, NULL },
    { "isVisible", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiMenuBarComponent = { "iMenuBarComponent", "com.appnativa.rare.ui", NULL, 0x201, 11, methods, 0, NULL, 0, NULL};
  return &_RAREiMenuBarComponent;
}

@end
