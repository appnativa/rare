//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/painter/aUIPlatformPainter.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/painter/aUIPlatformPainter.h"

@implementation RAREaUIPlatformPainter

- (id)init {
  return [super init];
}

- (BOOL)canUseLayer {
  return NO;
}

- (BOOL)canUseMainLayer {
  return NO;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "canUseLayer", NULL, "Z", 0x1, NULL },
    { "canUseMainLayer", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREaUIPlatformPainter = { "aUIPlatformPainter", "com.appnativa.rare.ui.painter", NULL, 0x401, 2, methods, 0, NULL, 0, NULL};
  return &_RAREaUIPlatformPainter;
}

@end