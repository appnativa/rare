//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iTransform.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/iTransform.h"


@interface RAREiTransform : NSObject
@end

@implementation RAREiTransform

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getPlatformTransform", NULL, "LNSObject", 0x401, NULL },
    { "concatenateWithRAREiTransform:", NULL, "V", 0x401, NULL },
    { "rotateWithFloat:", NULL, "V", 0x401, NULL },
    { "scale__WithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "shearWithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "translateWithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "setTransformWithFloat:withFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "concatenateWithFloat:withFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "cloneCopy", NULL, "LRAREiTransform", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiTransform = { "iTransform", "com.appnativa.rare.ui", NULL, 0x201, 9, methods, 0, NULL, 0, NULL};
  return &_RAREiTransform;
}

@end