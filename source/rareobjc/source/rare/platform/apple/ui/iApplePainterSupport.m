//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/iApplePainterSupport.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/platform/apple/ui/iApplePainterSupport.h"
#include "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/UIRectangle.h"


@interface RAREiApplePainterSupport : NSObject
@end

@implementation RAREiApplePainterSupport

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "paintBackgroundWithRAREAppleGraphics:withRAREView:withRAREUIRectangle:", NULL, "V", 0x401, NULL },
    { "paintOverlayWithRAREAppleGraphics:withRAREView:withRAREUIRectangle:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiApplePainterSupport = { "iApplePainterSupport", "com.appnativa.rare.platform.apple.ui", NULL, 0x201, 2, methods, 0, NULL, 0, NULL};
  return &_RAREiApplePainterSupport;
}

@end
