//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iCustomLayoutManager.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/spot/GroupBox.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/iCustomLayoutManager.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/widget/iWidget.h"


@interface RAREiCustomLayoutManager : NSObject
@end

@implementation RAREiCustomLayoutManager

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createComponentWithRAREiWidget:withRARESPOTGroupBox:", NULL, "LRAREiPlatformComponent", 0x401, NULL },
    { "getConstraintsConverter", NULL, "LRAREiCustomLayoutManager_iConstraintsConverter", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiCustomLayoutManager = { "iCustomLayoutManager", "com.appnativa.rare.ui", NULL, 0x201, 2, methods, 0, NULL, 0, NULL};
  return &_RAREiCustomLayoutManager;
}

@end

@interface RAREiCustomLayoutManager_iConstraintsConverter : NSObject
@end

@implementation RAREiCustomLayoutManager_iConstraintsConverter

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getConstraintsWithRARESPOTWidget:", NULL, "LNSObject", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiCustomLayoutManager_iConstraintsConverter = { "iConstraintsConverter", "com.appnativa.rare.ui", "iCustomLayoutManager", 0x209, 1, methods, 0, NULL, 0, NULL};
  return &_RAREiCustomLayoutManager_iConstraintsConverter;
}

@end
