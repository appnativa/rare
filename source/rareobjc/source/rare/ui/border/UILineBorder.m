//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/border/UILineBorder.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/border/UILineBorder.h"
#include "com/appnativa/rare/ui/iPlatformPath.h"
#include "com/appnativa/util/SNumber.h"

@implementation RAREUILineBorder

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
                withFloat:(float)arc {
  return [super initWithRAREUIColor:color withFloat:thickness withFloat:arc];
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                withFloat:(float)arcWidth
                withFloat:(float)arcHeight {
  return [super initWithRAREUIColor:color withFloat:thickness withFloat:arcWidth withFloat:arcHeight];
}

- (float)getClipingOffset {
  return 0;
}

- (BOOL)usesPath {
  return !has2MissingSides_;
}

- (id<RAREiPlatformPath>)getPathWithRAREiPlatformPath:(id<RAREiPlatformPath>)p
                                            withFloat:(float)x
                                            withFloat:(float)y
                                            withFloat:(float)width
                                            withFloat:(float)height
                                          withBoolean:(BOOL)forClip {
  if (forClip && !has2MissingSides_) {
    BOOL nb = noBottom_;
    BOOL nt = noTop_;
    BOOL nr = noRight_;
    BOOL nl = noLeft_;
    noBottom_ = NO;
    noTop_ = NO;
    noLeft_ = NO;
    noRight_ = NO;
    id<RAREiPlatformPath> path = [super getPathWithRAREiPlatformPath:p withFloat:x withFloat:y withFloat:width withFloat:height withBoolean:NO];
    noBottom_ = nb;
    noTop_ = nt;
    noLeft_ = nl;
    noRight_ = nr;
    return path;
  }
  return [super getPathWithRAREiPlatformPath:p withFloat:x withFloat:y withFloat:width withFloat:height withBoolean:forClip];
}

- (BOOL)canUseMainLayer {
  if (roundedCorners_ && (flatBottom_ || flatTop_ || flatRight_ || flatLeft_)) {
    return NO;
  }
  if (![RAREUTSNumber isEqualWithFloat:arcHeight_ withFloat:arcWidth_]) {
    return NO;
  }
  if (![@"solid" equalsIgnoreCase:lineStyle_]) {
    return NO;
  }
  return !noBottom_ && !noTop_ && !noLeft_ && !noRight_;
}

- (float)getPathWidth {
  return thickness_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getClipingOffset", NULL, "F", 0x4, NULL },
    { "usesPath", NULL, "Z", 0x1, NULL },
    { "getPathWithRAREiPlatformPath:withFloat:withFloat:withFloat:withFloat:withBoolean:", NULL, "LRAREiPlatformPath", 0x1, NULL },
    { "canUseMainLayer", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREUILineBorder = { "UILineBorder", "com.appnativa.rare.ui.border", NULL, 0x1, 4, methods, 0, NULL, 0, NULL};
  return &_RAREUILineBorder;
}

@end