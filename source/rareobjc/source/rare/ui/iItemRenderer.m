//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iItemRenderer.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/Column.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/iItemRenderer.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/util/iStringConverter.h"


@interface RAREiItemRenderer : NSObject
@end

@implementation RAREiItemRenderer

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setConverterWithRAREUTiStringConverter:", NULL, "V", 0x401, NULL },
    { "setInsetsWithRAREUIInsets:", NULL, "V", 0x401, NULL },
    { "setItemDescriptionWithRAREColumn:", NULL, "V", 0x401, NULL },
    { "setLostFocusSelectionPaintWithRAREPaintBucket:", NULL, "V", 0x401, NULL },
    { "setSelectionPaintWithRAREPaintBucket:", NULL, "V", 0x401, NULL },
    { "setSelectionPaintedWithBoolean:", NULL, "V", 0x401, NULL },
    { "getConverter", NULL, "LRAREUTiStringConverter", 0x401, NULL },
    { "getInsets", NULL, "LRAREUIInsets", 0x401, NULL },
    { "getItemDescription", NULL, "LRAREColumn", 0x401, NULL },
    { "getLostFocusSelectionPaint", NULL, "LRAREPaintBucket", 0x401, NULL },
    { "getSelectionPaint", NULL, "LRAREPaintBucket", 0x401, NULL },
    { "getSelectionPaintForExternalPainterWithBoolean:", NULL, "LRAREPaintBucket", 0x401, NULL },
    { "getAutoHilightPaint", NULL, "LRAREPaintBucket", 0x401, NULL },
    { "setAutoHilightPaintWithRAREPaintBucket:", NULL, "V", 0x401, NULL },
    { "getPressedPaint", NULL, "LRAREPaintBucket", 0x401, NULL },
    { "setPressedPaintWithRAREPaintBucket:", NULL, "V", 0x401, NULL },
    { "isSelectionPainted", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiItemRenderer = { "iItemRenderer", "com.appnativa.rare.ui", NULL, 0x201, 17, methods, 0, NULL, 0, NULL};
  return &_RAREiItemRenderer;
}

@end
