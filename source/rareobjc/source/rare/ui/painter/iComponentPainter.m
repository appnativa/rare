//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/painter/iComponentPainter.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/border/SharedLineBorder.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/painter/PainterHolder.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iComponentPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformPainter.h"
#include "java/beans/PropertyChangeListener.h"


@implementation RAREiComponentPainter

static NSString * RAREiComponentPainter_PROPERTY_BACKGROUND_COLOR_ = @"BACKGROUND_COLOR";
static NSString * RAREiComponentPainter_PROPERTY_BACKGROUND_OVERLAY_PAINTER_ = @"BACKGROUND_OVERLAY_PAINTER";
static NSString * RAREiComponentPainter_PROPERTY_BACKGROUND_PAINTER_ = @"BACKGROUND_PAINTER";
static NSString * RAREiComponentPainter_PROPERTY_BORDER_ = @"BORDER";
static NSString * RAREiComponentPainter_PROPERTY_DISPOSED_ = @"DISPOSED";
static NSString * RAREiComponentPainter_PROPERTY_OVERLAY_PAINTER_ = @"OVERLAY_PAINTER";
static NSString * RAREiComponentPainter_PROPERTY_PAINTER_HOLDER_ = @"PROPERTY_PAINTER_HOLDER";

+ (NSString *)PROPERTY_BACKGROUND_COLOR {
  return RAREiComponentPainter_PROPERTY_BACKGROUND_COLOR_;
}

+ (NSString *)PROPERTY_BACKGROUND_OVERLAY_PAINTER {
  return RAREiComponentPainter_PROPERTY_BACKGROUND_OVERLAY_PAINTER_;
}

+ (NSString *)PROPERTY_BACKGROUND_PAINTER {
  return RAREiComponentPainter_PROPERTY_BACKGROUND_PAINTER_;
}

+ (NSString *)PROPERTY_BORDER {
  return RAREiComponentPainter_PROPERTY_BORDER_;
}

+ (NSString *)PROPERTY_DISPOSED {
  return RAREiComponentPainter_PROPERTY_DISPOSED_;
}

+ (NSString *)PROPERTY_OVERLAY_PAINTER {
  return RAREiComponentPainter_PROPERTY_OVERLAY_PAINTER_;
}

+ (NSString *)PROPERTY_PAINTER_HOLDER {
  return RAREiComponentPainter_PROPERTY_PAINTER_HOLDER_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addPropertyChangeListenerWithJavaBeansPropertyChangeListener:", NULL, "V", 0x401, NULL },
    { "clear", NULL, "V", 0x401, NULL },
    { "copyFromWithRAREiPlatformComponentPainter:", NULL, "V", 0x401, NULL },
    { "paintWithRAREiPlatformGraphics:withFloat:withFloat:withFloat:withFloat:withInt:withBoolean:", NULL, "V", 0x401, NULL },
    { "removePropertyChangeListenerWithJavaBeansPropertyChangeListener:", NULL, "V", 0x401, NULL },
    { "setBackgroundOverlayPainterWithRAREiPlatformPainter:", NULL, "V", 0x401, NULL },
    { "setBackgroundColorWithRAREUIColor:", NULL, "V", 0x401, NULL },
    { "setBackgroundColorWithRAREUIColor:withBoolean:", NULL, "V", 0x401, NULL },
    { "setBackgroundPainterWithRAREiBackgroundPainter:withBoolean:", NULL, "V", 0x401, NULL },
    { "setBorderWithRAREiPlatformBorder:", NULL, "V", 0x401, NULL },
    { "setForegroundColorWithRAREUIColor:", NULL, "V", 0x401, NULL },
    { "setDisabledForegroundColorWithRAREUIColor:", NULL, "V", 0x401, NULL },
    { "setOverlayPainterWithRAREiPlatformPainter:", NULL, "V", 0x401, NULL },
    { "setPainterHolderWithRAREPainterHolder:", NULL, "V", 0x401, NULL },
    { "setSharedBorderWithRARESharedLineBorder:", NULL, "V", 0x401, NULL },
    { "getBackgroundOverlayPainter", NULL, "LRAREiPlatformPainter", 0x401, NULL },
    { "getBackgroundPainter", NULL, "LRAREiBackgroundPainter", 0x401, NULL },
    { "getBorder", NULL, "LRAREiPlatformBorder", 0x401, NULL },
    { "getBorderAlways", NULL, "LRAREiPlatformBorder", 0x401, NULL },
    { "getForegroundColor", NULL, "LRAREUIColor", 0x401, NULL },
    { "getOverlayPainter", NULL, "LRAREiPlatformPainter", 0x401, NULL },
    { "getPainterHolder", NULL, "LRAREPainterHolder", 0x401, NULL },
    { "getSharedBorder", NULL, "LRARESharedLineBorder", 0x401, NULL },
    { "isBackgroundPaintEnabled", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "PROPERTY_BACKGROUND_COLOR_", NULL, 0x19, "LNSString" },
    { "PROPERTY_BACKGROUND_OVERLAY_PAINTER_", NULL, 0x19, "LNSString" },
    { "PROPERTY_BACKGROUND_PAINTER_", NULL, 0x19, "LNSString" },
    { "PROPERTY_BORDER_", NULL, 0x19, "LNSString" },
    { "PROPERTY_DISPOSED_", NULL, 0x19, "LNSString" },
    { "PROPERTY_OVERLAY_PAINTER_", NULL, 0x19, "LNSString" },
    { "PROPERTY_PAINTER_HOLDER_", NULL, 0x19, "LNSString" },
  };
  static J2ObjcClassInfo _RAREiComponentPainter = { "iComponentPainter", "com.appnativa.rare.ui.painter", NULL, 0x201, 24, methods, 7, fields, 0, NULL};
  return &_RAREiComponentPainter;
}

@end
