//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/listener/iMouseMotionListener.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/ui/event/MouseEvent.h"
#include "com/appnativa/rare/ui/listener/iMouseMotionListener.h"


@interface RAREiMouseMotionListener : NSObject
@end

@implementation RAREiMouseMotionListener

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "mouseDraggedWithRAREMouseEvent:", NULL, "V", 0x401, NULL },
    { "mouseMovedWithRAREMouseEvent:", NULL, "V", 0x401, NULL },
    { "wantsMouseMovedEvents", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiMouseMotionListener = { "iMouseMotionListener", "com.appnativa.rare.ui.listener", NULL, 0x201, 3, methods, 0, NULL, 0, NULL};
  return &_RAREiMouseMotionListener;
}

@end
