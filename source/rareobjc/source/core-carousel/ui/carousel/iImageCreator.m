//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-carousel/com/appnativa/rare/ui/carousel/iImageCreator.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/carousel/aCarouselPanel.h"
#include "com/appnativa/rare/ui/carousel/iImageCreator.h"


@interface RAREiImageCreator : NSObject
@end

@implementation RAREiImageCreator

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createImageWithRAREaCarouselPanel:withId:", NULL, "LRAREUIImage", 0x401, NULL },
    { "cancelLoadingWithRAREaCarouselPanel:", NULL, "V", 0x401, NULL },
    { "disposeWithRAREaCarouselPanel:", NULL, "V", 0x401, NULL },
    { "reset", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiImageCreator = { "iImageCreator", "com.appnativa.rare.ui.carousel", NULL, 0x201, 4, methods, 0, NULL, 0, NULL};
  return &_RAREiImageCreator;
}

@end
