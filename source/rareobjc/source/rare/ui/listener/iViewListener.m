//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/listener/iViewListener.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/event/ChangeEvent.h"
#include "com/appnativa/rare/ui/listener/iViewListener.h"


@interface RAREiViewListener : NSObject
@end

@implementation RAREiViewListener

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "viewResizedWithRAREChangeEvent:", NULL, "V", 0x401, NULL },
    { "viewHiddenWithRAREChangeEvent:", NULL, "V", 0x401, NULL },
    { "viewShownWithRAREChangeEvent:", NULL, "V", 0x401, NULL },
    { "wantsResizeEvent", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiViewListener = { "iViewListener", "com.appnativa.rare.ui.listener", NULL, 0x201, 4, methods, 0, NULL, 0, NULL};
  return &_RAREiViewListener;
}

@end
