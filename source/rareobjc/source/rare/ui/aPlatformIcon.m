//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/aPlatformIcon.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/ui/aPlatformIcon.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"

@implementation RAREaPlatformIcon

- (id)init {
  return [super init];
}

- (id<RAREiPlatformIcon>)getDisabledVersion {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (int)getIconHeight {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (int)getIconWidth {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)param0
                             withFloat:(float)param1
                             withFloat:(float)param2
                             withFloat:(float)param3
                             withFloat:(float)param4 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getDisabledVersion", NULL, "LRAREiPlatformIcon", 0x401, NULL },
    { "getIconHeight", NULL, "I", 0x401, NULL },
    { "getIconWidth", NULL, "I", 0x401, NULL },
    { "paintWithRAREiPlatformGraphics:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREaPlatformIcon = { "aPlatformIcon", "com.appnativa.rare.ui", NULL, 0x401, 4, methods, 0, NULL, 0, NULL};
  return &_RAREaPlatformIcon;
}

@end
