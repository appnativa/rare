//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-canvas/com/appnativa/rare/ui/canvas/CanvasColor.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/canvas/CanvasColor.h"
#include "com/appnativa/rare/ui/canvas/iContext.h"
#include "com/appnativa/rare/ui/iPlatformPaint.h"
#include "java/lang/CloneNotSupportedException.h"
#include "java/lang/RuntimeException.h"

@implementation RARECanvasColor

- (id)initWithRAREUIColor:(RAREUIColor *)color {
  if (self = [super init]) {
    self->color_ = color;
  }
  return self;
}

- (id)clone {
  @try {
    return [super clone];
  }
  @catch (JavaLangCloneNotSupportedException *ex) {
    @throw [[JavaLangRuntimeException alloc] initWithJavaLangThrowable:ex];
  }
}

- (id<RAREiContext_iCanvasPaint>)cloneCopy {
  return self;
}

- (id<RAREiPlatformPaint>)getPaint {
  return color_;
}

- (RAREUIColor *)getUIColor {
  return color_;
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RARECanvasColor *)other {
  [super copyAllFieldsTo:other];
  other->color_ = color_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "cloneCopy", NULL, "LRAREiContext_iCanvasPaint", 0x1, NULL },
    { "getPaint", NULL, "LRAREiPlatformPaint", 0x1, NULL },
    { "getUIColor", NULL, "LRAREUIColor", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "color_", NULL, 0x0, "LRAREUIColor" },
  };
  static J2ObjcClassInfo _RARECanvasColor = { "CanvasColor", "com.appnativa.rare.ui.canvas", NULL, 0x1, 4, methods, 1, fields, 0, NULL};
  return &_RARECanvasColor;
}

@end
