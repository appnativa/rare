//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iRenderingComponent.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/iRenderingComponent.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "java/util/Map.h"


@interface RAREiRenderingComponent : NSObject
@end

@implementation RAREiRenderingComponent

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setBackgroundWithRAREUIColor:", NULL, "V", 0x401, NULL },
    { "setBorderWithRAREiPlatformBorder:", NULL, "V", 0x401, NULL },
    { "setComponentPainterWithRAREiPlatformComponentPainter:", NULL, "V", 0x401, NULL },
    { "setEnabledWithBoolean:", NULL, "V", 0x401, NULL },
    { "setFontWithRAREUIFont:", NULL, "V", 0x401, NULL },
    { "setForegroundWithRAREUIColor:", NULL, "V", 0x401, NULL },
    { "setIconWithRAREiPlatformIcon:", NULL, "V", 0x401, NULL },
    { "setIconPositionWithRARERenderableDataItem_IconPositionEnum:", NULL, "V", 0x401, NULL },
    { "setOptionsWithJavaUtilMap:", NULL, "V", 0x401, NULL },
    { "dispose", NULL, "V", 0x401, NULL },
    { "setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:withRARERenderableDataItem_VerticalAlignEnum:", NULL, "V", 0x401, NULL },
    { "setMarginWithRAREUIInsets:", NULL, "V", 0x401, NULL },
    { "setOrientationWithRARERenderableDataItem_OrientationEnum:", NULL, "V", 0x401, NULL },
    { "setWordWrapWithBoolean:", NULL, "V", 0x401, NULL },
    { "getComponent", NULL, "LRAREiPlatformComponent", 0x401, NULL },
    { "clearRenderer", NULL, "V", 0x401, NULL },
    { "setScaleIconWithBoolean:withFloat:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiRenderingComponent = { "iRenderingComponent", "com.appnativa.rare.ui", NULL, 0x201, 17, methods, 0, NULL, 0, NULL};
  return &_RAREiRenderingComponent;
}

@end
