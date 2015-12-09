//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/border/UITitledBorder.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/border/UITitledBorder.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"

@implementation RAREUITitledBorder

- (id)initWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border {
  return [super initWithRAREiPlatformBorder:border];
}

- (id)initWithNSString:(NSString *)title {
  return [super initWithNSString:title];
}

- (id)initWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border
                     withNSString:(NSString *)title {
  return [super initWithRAREiPlatformBorder:border withNSString:title];
}

- (id)initWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border
                     withNSString:(NSString *)title
                          withInt:(int)titleJustification
                          withInt:(int)titlePosition {
  return [super initWithRAREiPlatformBorder:border withNSString:title withInt:titleJustification withInt:titlePosition];
}

- (id)initWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border
                     withNSString:(NSString *)title
                          withInt:(int)titleJustification
                          withInt:(int)titlePosition
                   withRAREUIFont:(RAREUIFont *)titleFont {
  return [super initWithRAREiPlatformBorder:border withNSString:title withInt:titleJustification withInt:titlePosition withRAREUIFont:titleFont];
}

- (id)initWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border
                     withNSString:(NSString *)title
                          withInt:(int)titleJustification
                          withInt:(int)titlePosition
                   withRAREUIFont:(RAREUIFont *)titleFont
                  withRAREUIColor:(RAREUIColor *)titleColor {
  return [super initWithRAREiPlatformBorder:border withNSString:title withInt:titleJustification withInt:titlePosition withRAREUIFont:titleFont withRAREUIColor:titleColor];
}

- (void)paintIinnerBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b
                       withRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                       withFloat:(float)x
                                       withFloat:(float)y
                                       withFloat:(float)width
                                       withFloat:(float)height
                                     withBoolean:(BOOL)last {
  [((id<RAREiPlatformBorder>) nil_chk(b)) paintWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height withBoolean:last];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "paintIinnerBorderWithRAREiPlatformBorder:withRAREiPlatformGraphics:withFloat:withFloat:withFloat:withFloat:withBoolean:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREUITitledBorder = { "UITitledBorder", "com.appnativa.rare.ui.border", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RAREUITitledBorder;
}

@end
