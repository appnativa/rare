//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/painter/UICompoundPainter.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/painter/UICompoundPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformPainter.h"

@implementation RAREUICompoundPainter

- (id)initWithRAREiPlatformPainter:(id<RAREiPlatformPainter>)firstPainter
          withRAREiPlatformPainter:(id<RAREiPlatformPainter>)secondPainter {
  return [super initWithRAREiPlatformPainter:firstPainter withRAREiPlatformPainter:secondPainter];
}

- (void)disposeEx {
  [super disposeEx];
}

- (BOOL)canUseMainLayer {
  return NO;
}

- (BOOL)canUseLayer {
  if ((firstPainter_ != nil) && ![firstPainter_ canUseLayer]) {
    return NO;
  }
  if ((secondPainter_ != nil) && ![secondPainter_ canUseLayer]) {
    return NO;
  }
  return YES;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "disposeEx", NULL, "V", 0x4, NULL },
    { "canUseMainLayer", NULL, "Z", 0x1, NULL },
    { "canUseLayer", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREUICompoundPainter = { "UICompoundPainter", "com.appnativa.rare.ui.painter", NULL, 0x1, 3, methods, 0, NULL, 0, NULL};
  return &_RAREUICompoundPainter;
}

@end
