//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iScrollerSupport.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/iScrollerSupport.h"
#include "com/appnativa/rare/ui/painter/UIScrollingEdgePainter.h"


@interface RAREiScrollerSupport : NSObject
@end

@implementation RAREiScrollerSupport

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isScrolling", NULL, "Z", 0x401, NULL },
    { "isAtLeftEdge", NULL, "Z", 0x401, NULL },
    { "isAtRightEdge", NULL, "Z", 0x401, NULL },
    { "isAtTopEdge", NULL, "Z", 0x401, NULL },
    { "isAtBottomEdge", NULL, "Z", 0x401, NULL },
    { "getContentOffset", NULL, "LRAREUIPoint", 0x401, NULL },
    { "setScrollingEdgePainterWithRAREUIScrollingEdgePainter:", NULL, "V", 0x401, NULL },
    { "getScrollingEdgePainter", NULL, "LRAREUIScrollingEdgePainter", 0x401, NULL },
    { "scrollToBottomEdge", NULL, "V", 0x401, NULL },
    { "scrollToLeftEdge", NULL, "V", 0x401, NULL },
    { "scrollToRightEdge", NULL, "V", 0x401, NULL },
    { "scrollToTopEdge", NULL, "V", 0x401, NULL },
    { "moveUpDownWithBoolean:withBoolean:", NULL, "V", 0x401, NULL },
    { "moveLeftRightWithBoolean:withBoolean:", NULL, "V", 0x401, NULL },
    { "setContentOffsetWithFloat:withFloat:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiScrollerSupport = { "iScrollerSupport", "com.appnativa.rare.ui", NULL, 0x201, 15, methods, 0, NULL, 0, NULL};
  return &_RAREiScrollerSupport;
}

@end
