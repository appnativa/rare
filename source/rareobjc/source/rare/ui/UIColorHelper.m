//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/UIColorHelper.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSFloatArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/spot/GridCell.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIColorHelper.h"
#include "com/appnativa/rare/ui/UIColorShade.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/painter/UISimpleBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/util/DataParser.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/io/StringReader.h"
#include "java/lang/Exception.h"

@implementation RAREUIColorHelper

+ (int)HSVToColorWithInt:(int)alpha
          withFloatArray:(IOSFloatArray *)hsv {
  return [RAREColorUtils HSVToColorWithInt:alpha withFloatArray:hsv];
}

+ (RAREUIColorShade *)getColorStateListWithRAREUIColor:(RAREUIColor *)fg
                                       withRAREUIColor:(RAREUIColor *)disabled {
  return [RAREPlatformHelper getColorStateListWithRAREUIColor:fg withRAREUIColor:disabled];
}

+ (RAREUIColorShade *)getColorStateListWithRAREUIColor:(RAREUIColor *)fg
                                       withRAREUIColor:(RAREUIColor *)disabled
                                       withRAREUIColor:(RAREUIColor *)pressed {
  return [RAREPlatformHelper getColorStateListWithRAREUIColor:fg withRAREUIColor:disabled withRAREUIColor:pressed];
}

+ (void)updateColorWithNSString:(NSString *)name
                         withId:(id)value {
  [RAREColorUtils updateColorWithNSString:name withId:value];
}

+ (int)setAlphaWithInt:(int)argb
               withInt:(int)alpha {
  return [RAREColorUtils setAlphaWithInt:argb withInt:alpha];
}

+ (RAREUIColor *)getBackground {
  return [RAREColorUtils getBackground];
}

+ (RAREUIColor *)getBackgroundColorWithSPOTPrintableString:(SPOTPrintableString *)color {
  return [RAREColorUtils getBackgroundColorWithSPOTPrintableString:color];
}

+ (RAREUIColor *)getBackgroundColorWithNSString:(NSString *)color {
  return [RAREColorUtils getBackgroundColorWithNSString:color];
}

+ (id<RAREiBackgroundPainter>)getBackgroundPainterWithNSString:(NSString *)color {
  return [RAREColorUtils getBackgroundPainterWithNSString:color];
}

+ (id<RAREiBackgroundPainter>)getBackgroundPainterWithRAREUIColor:(RAREUIColor *)bg {
  return [[RAREUISimpleBackgroundPainter alloc] initWithRAREUIColor:bg];
}

+ (RAREUIColor *)getColorWithNSString:(NSString *)color {
  return [RAREColorUtils getColorWithNSString:color];
}

+ (RAREUIColor *)getForeground {
  return [RAREColorUtils getForeground];
}

+ (RAREPaintBucket *)getPaintBucketWithSPOTPrintableString:(SPOTPrintableString *)bgColor {
  return [RAREUIColorHelper getPaintBucketWithSPOTPrintableString:bgColor withRAREPaintBucket:nil];
}

+ (RAREPaintBucket *)getPaintBucketWithSPOTPrintableString:(SPOTPrintableString *)bgColor
                                       withRAREPaintBucket:(RAREPaintBucket *)pb {
  if ((bgColor == nil) || ([bgColor getValue] == nil)) {
    return nil;
  }
  if (pb == nil) {
    pb = [[RAREPaintBucket alloc] init];
  }
  [RAREColorUtils configureBackgroundPainterWithRAREPaintBucket:pb withSPOTPrintableString:bgColor];
  return pb;
}

+ (RAREPaintBucket *)getPaintBucketWithNSString:(NSString *)color {
  return [RAREColorUtils getPaintBucketWithNSString:color];
}

+ (RAREPaintBucket *)getPaintBucketWithRAREiWidget:(id<RAREiWidget>)context
                              withRARESPOTGridCell:(RARESPOTGridCell *)gc {
  return [RAREUIColorHelper getPaintBucketWithRAREiWidget:context withRARESPOTGridCell:gc withRAREPaintBucket:nil];
}

+ (RAREPaintBucket *)getPaintBucketWithRAREiWidget:(id<RAREiWidget>)context
                              withRARESPOTGridCell:(RARESPOTGridCell *)gc
                               withRAREPaintBucket:(RAREPaintBucket *)pb {
  if (pb == nil) {
    pb = (gc == nil) ? nil : [[RAREPaintBucket alloc] init];
  }
  if (gc != nil) {
    (void) [RAREColorUtils configureWithRAREiWidget:context withRARESPOTGridCell:gc withRAREPaintBucket:pb];
  }
  return pb;
}

+ (RAREPaintBucket *)getPaintBucketForCellStringWithRAREiWidget:(id<RAREiWidget>)context
                                                   withNSString:(NSString *)gridCell
                                            withRAREPaintBucket:(RAREPaintBucket *)pb {
  if (pb == nil) {
    pb = (gridCell == nil) ? nil : [[RAREPaintBucket alloc] init];
  }
  if (gridCell != nil) {
    RARESPOTGridCell *gc = [[RARESPOTGridCell alloc] init];
    @try {
      (void) [RAREDataParser loadSPOTObjectSDFWithRAREiWidget:context withJavaIoReader:[[JavaIoStringReader alloc] initWithNSString:[gridCell trim]] withISPOTElement:gc withNSString:nil withJavaNetURL:nil];
    }
    @catch (JavaLangException *ex) {
      [RAREPlatform ignoreExceptionWithNSString:@"Invalid GridCell definition" withJavaLangThrowable:ex];
    }
    (void) [RAREColorUtils configureWithRAREiWidget:context withRARESPOTGridCell:gc withRAREPaintBucket:pb];
  }
  return pb;
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getColorStateListWithRAREUIColor:withRAREUIColor:", NULL, "LRAREUIColorShade", 0x9, NULL },
    { "getColorStateListWithRAREUIColor:withRAREUIColor:withRAREUIColor:", NULL, "LRAREUIColorShade", 0x9, NULL },
    { "getBackground", NULL, "LRAREUIColor", 0x9, NULL },
    { "getBackgroundColorWithSPOTPrintableString:", NULL, "LRAREUIColor", 0x9, NULL },
    { "getBackgroundColorWithNSString:", NULL, "LRAREUIColor", 0x9, NULL },
    { "getBackgroundPainterWithNSString:", NULL, "LRAREiBackgroundPainter", 0x9, NULL },
    { "getBackgroundPainterWithRAREUIColor:", NULL, "LRAREiBackgroundPainter", 0x9, NULL },
    { "getColorWithNSString:", NULL, "LRAREUIColor", 0x9, NULL },
    { "getForeground", NULL, "LRAREUIColor", 0x9, NULL },
    { "getPaintBucketWithSPOTPrintableString:", NULL, "LRAREPaintBucket", 0x9, NULL },
    { "getPaintBucketWithSPOTPrintableString:withRAREPaintBucket:", NULL, "LRAREPaintBucket", 0x9, NULL },
    { "getPaintBucketWithNSString:", NULL, "LRAREPaintBucket", 0x9, NULL },
    { "getPaintBucketWithRAREiWidget:withRARESPOTGridCell:", NULL, "LRAREPaintBucket", 0x9, NULL },
    { "getPaintBucketWithRAREiWidget:withRARESPOTGridCell:withRAREPaintBucket:", NULL, "LRAREPaintBucket", 0x9, NULL },
    { "getPaintBucketForCellStringWithRAREiWidget:withNSString:withRAREPaintBucket:", NULL, "LRAREPaintBucket", 0x9, NULL },
  };
  static J2ObjcClassInfo _RAREUIColorHelper = { "UIColorHelper", "com.appnativa.rare.ui", NULL, 0x1, 15, methods, 0, NULL, 0, NULL};
  return &_RAREUIColorHelper;
}

@end
