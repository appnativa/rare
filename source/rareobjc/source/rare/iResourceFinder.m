//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/iResourceFinder.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/TemplateHandler.h"
#include "com/appnativa/rare/iResourceFinder.h"
#include "com/appnativa/rare/ui/UIAction.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIImageIcon.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/net/URL.h"


@interface RAREiResourceFinder : NSObject
@end

@implementation RAREiResourceFinder

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getActionWithNSString:", NULL, "LRAREUIAction", 0x401, NULL },
    { "getResourceWithNSString:", NULL, "LJavaNetURL", 0x401, NULL },
    { "getResourceAsIconWithNSString:", NULL, "LRAREUIImageIcon", 0x401, NULL },
    { "getResourceAsImageWithNSString:", NULL, "LRAREUIImage", 0x401, NULL },
    { "getResourceAsStringWithNSString:", NULL, "LNSString", 0x401, NULL },
    { "getTemplateHandlerWithRAREiWidget:", NULL, "LRARETemplateHandler", 0x401, NULL },
    { "setTemplateHandlerWithRAREiWidget:withRARETemplateHandler:", NULL, "V", 0x401, NULL },
    { "getUIDefaults", NULL, "LRAREUIProperties", 0x401, NULL },
    { "getApplicationRoot", NULL, "LNSString", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiResourceFinder = { "iResourceFinder", "com.appnativa.rare", NULL, 0x201, 9, methods, 0, NULL, 0, NULL};
  return &_RAREiResourceFinder;
}

@end