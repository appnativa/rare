//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iPath.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/iPath.h"


@interface RAREiPath : NSObject
@end

@implementation RAREiPath

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "arcWithFloat:withFloat:withFloat:withFloat:withFloat:withBoolean:", NULL, "LRAREiPath", 0x401, NULL },
    { "arcToWithFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "LRAREiPath", 0x401, NULL },
    { "close", NULL, "V", 0x401, NULL },
    { "cubicToWithFloat:withFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "LRAREiPath", 0x401, NULL },
    { "drawLineWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "drawRectWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "drawRoundedRectWithFloat:withFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "lineToWithFloat:withFloat:", NULL, "LRAREiPath", 0x401, NULL },
    { "moveToWithFloat:withFloat:", NULL, "LRAREiPath", 0x401, NULL },
    { "quadToWithFloat:withFloat:withFloat:withFloat:", NULL, "LRAREiPath", 0x401, NULL },
    { "reset", NULL, "V", 0x401, NULL },
    { "rewind", NULL, "V", 0x401, NULL },
    { "startLineDrawingWithFloat:withFloat:withBoolean:", NULL, "LRAREiPath", 0x401, NULL },
    { "translateWithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "isEmpty", NULL, "Z", 0x401, NULL },
    { "isPointInPathWithFloat:withFloat:", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiPath = { "iPath", "com.appnativa.rare.ui", NULL, 0x201, 16, methods, 0, NULL, 0, NULL};
  return &_RAREiPath;
}

@end
