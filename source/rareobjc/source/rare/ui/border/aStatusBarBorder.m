//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/border/aStatusBarBorder.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/border/aStatusBarBorder.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"

@implementation RAREaStatusBarBorder

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)end {
  if (end) {
    [((id<RAREiPlatformGraphics>) nil_chk(g)) setColorWithRAREUIColor:[RAREColorUtils getColorWithNSString:@"Rare.backgroundDkShadow"]];
    [g drawLineWithFloat:x withFloat:y + 2 withFloat:x + width withFloat:y + 2];
    [g setColorWithRAREUIColor:[RAREColorUtils getColorWithNSString:@"Rare.backgroundShadow"]];
    [g drawLineWithFloat:x withFloat:y + 3 withFloat:x + width withFloat:y + 3];
    [g drawLineWithFloat:x withFloat:y + height - 1 withFloat:x + width withFloat:y + height - 1];
    [g setColorWithRAREUIColor:[RAREColorUtils getColorWithNSString:@"Rare.backgroundLtShadow"]];
    [g drawLineWithFloat:x withFloat:y + 4 withFloat:x + width withFloat:y + 4];
    [g drawLineWithFloat:x withFloat:y + height - 2 withFloat:x + width withFloat:y + height - 2];
    [g setColorWithRAREUIColor:[RAREColorUtils getColorWithNSString:@"Rare.backgroundHighlight"]];
    [g drawLineWithFloat:x withFloat:y + 5 withFloat:x + width withFloat:y + 5];
    [g drawLineWithFloat:x withFloat:y + height - 3 withFloat:x + width withFloat:y + height - 3];
  }
}

- (RAREUIInsets *)getBorderInsetsWithRAREUIInsets:(RAREUIInsets *)insets {
  if (insets != nil) {
    (void) [insets setWithInt:6 withInt:0 withInt:2 withInt:0];
    return insets;
  }
  else {
    return [[RAREUIInsets alloc] initWithInt:6 withInt:0 withInt:2 withInt:0];
  }
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getBorderInsetsWithRAREUIInsets:", NULL, "LRAREUIInsets", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREaStatusBarBorder = { "aStatusBarBorder", "com.appnativa.rare.ui.border", NULL, 0x401, 1, methods, 0, NULL, 0, NULL};
  return &_RAREaStatusBarBorder;
}

@end
