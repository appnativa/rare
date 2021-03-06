//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-canvas/com/appnativa/rare/ui/canvas/iCanvas.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/canvas/iCanvas.h"
#include "com/appnativa/rare/ui/canvas/iContext.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "java/io/IOException.h"


@interface RAREiCanvas : NSObject
@end

@implementation RAREiCanvas

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getCanvasComponent", NULL, "LRAREiPlatformComponent", 0x401, NULL },
    { "dispose", NULL, "V", 0x401, NULL },
    { "repaint", NULL, "V", 0x401, NULL },
    { "clear", NULL, "V", 0x401, NULL },
    { "toDataURLWithNSObjectArray:", NULL, "LNSString", 0x481, "JavaIoIOException" },
    { "setHeightWithInt:", NULL, "V", 0x401, NULL },
    { "setSizeWithInt:withInt:withBoolean:", NULL, "V", 0x401, NULL },
    { "setWidthWithInt:", NULL, "V", 0x401, NULL },
    { "getContextWithNSString:", NULL, "LRAREiContext", 0x401, NULL },
    { "getHeight", NULL, "I", 0x401, NULL },
    { "getImageWithBoolean:", NULL, "LRAREUIImage", 0x401, NULL },
    { "getWidth", NULL, "I", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiCanvas = { "iCanvas", "com.appnativa.rare.ui.canvas", NULL, 0x201, 12, methods, 0, NULL, 0, NULL};
  return &_RAREiCanvas;
}

@end
