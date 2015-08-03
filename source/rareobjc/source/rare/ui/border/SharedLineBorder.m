//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/border/SharedLineBorder.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/border/SharedLineBorder.h"

@implementation RARESharedLineBorder

- (id)initWithRAREUIColor:(RAREUIColor *)color {
  return [super initWithRAREUIColor:color];
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness {
  return [super initWithRAREUIColor:color withFloat:thickness];
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
              withBoolean:(BOOL)roundedCorners {
  return [super initWithRAREUIColor:color withFloat:thickness withBoolean:roundedCorners];
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                  withInt:(int)arc {
  return [super initWithRAREUIColor:color withFloat:thickness withInt:arc];
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                  withInt:(int)arcWidth
                  withInt:(int)arcHeight {
  return [super initWithRAREUIColor:color withFloat:thickness withInt:arcWidth withInt:arcHeight];
}

- (BOOL)canUseMainLayer {
  return NO;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "canUseMainLayer", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RARESharedLineBorder = { "SharedLineBorder", "com.appnativa.rare.ui.border", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RARESharedLineBorder;
}

@end