//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iComponentFactory.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/spot/CollapsibleInfo.h"
#include "com/appnativa/rare/ui/iCollapsible.h"
#include "com/appnativa/rare/ui/iComponentFactory.h"
#include "com/appnativa/rare/widget/iWidget.h"


@interface RAREiComponentFactory : NSObject
@end

@implementation RAREiComponentFactory

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setAppContextWithRAREiPlatformAppContext:", NULL, "V", 0x401, NULL },
    { "getCollapsibleWithRAREiWidget:withRARESPOTCollapsibleInfo:", NULL, "LRAREiCollapsible", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiComponentFactory = { "iComponentFactory", "com.appnativa.rare.ui", NULL, 0x201, 2, methods, 0, NULL, 0, NULL};
  return &_RAREiComponentFactory;
}

@end
