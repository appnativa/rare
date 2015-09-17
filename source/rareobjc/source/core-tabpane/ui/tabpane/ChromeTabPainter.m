//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-tabpane/com/appnativa/rare/ui/tabpane/ChromeTabPainter.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/iPath.h"
#include "com/appnativa/rare/ui/iPlatformPath.h"
#include "com/appnativa/rare/ui/tabpane/ChromeTabPainter.h"
#include "com/appnativa/rare/ui/tabpane/aTabPainter.h"

@implementation RAREChromeTabPainter

- (id)init {
  if (self = [super initWithInt:[RAREScreenUtils PLATFORM_PIXELS_6]]) {
    overlapOffset_ = [RAREScreenUtils platformPixelsWithFloat:30];
    padding_ = [RAREScreenUtils PLATFORM_PIXELS_4];
    [self setStartOffsetWithInt:[RAREScreenUtils PLATFORM_PIXELS_4]];
    cornerSize_ = [RAREScreenUtils PLATFORM_PIXELS_6];
    ((RAREUIInsets *) nil_chk(textInsets_))->left_ = 0;
    textInsets_->right_ = [RAREScreenUtils platformPixelsWithFloat:16];
  }
  return self;
}

- (void)updateShapeWithRAREiPlatformPath:(id<RAREiPlatformPath>)path
                               withFloat:(float)width
                               withFloat:(float)height
                               withFloat:(float)size {
  if (path == nil) {
    return;
  }
  [((id<RAREiPlatformPath>) nil_chk(path)) reset];
  (void) [path moveToWithFloat:0 withFloat:height];
  (void) [path lineToWithFloat:1 withFloat:height];
  (void) [path cubicToWithFloat:size * 2 withFloat:height withFloat:size * 2 withFloat:size * 2 withFloat:size * 4 withFloat:0];
  (void) [path lineToWithFloat:width - size * 4 withFloat:0];
  (void) [path cubicToWithFloat:width - size * 2 withFloat:size * 2 withFloat:width - size * 2 withFloat:height - size withFloat:width - size withFloat:height];
  (void) [path lineToWithFloat:width withFloat:height];
  (void) [path moveToWithFloat:width withFloat:height];
  [path close];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "updateShapeWithRAREiPlatformPath:withFloat:withFloat:withFloat:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREChromeTabPainter = { "ChromeTabPainter", "com.appnativa.rare.ui.tabpane", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RAREChromeTabPainter;
}

@end
