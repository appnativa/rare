//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-tabpane/com/appnativa/rare/ui/tabpane/aPlatformTabPainter.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/UIAction.h"
#include "com/appnativa/rare/ui/tabpane/TabLabel.h"
#include "com/appnativa/rare/ui/tabpane/aPlatformTabPainter.h"
#include "com/appnativa/rare/ui/tabpane/iTabLabel.h"

@implementation RAREaPlatformTabPainter

- (id<RAREiTabLabel>)createNewRendererWithRAREUIAction:(RAREUIAction *)a {
  return [[RARETabLabel alloc] initWithRAREUIAction:a];
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createNewRendererWithRAREUIAction:", NULL, "LRAREiTabLabel", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREaPlatformTabPainter = { "aPlatformTabPainter", "com.appnativa.rare.ui.tabpane", NULL, 0x401, 1, methods, 0, NULL, 0, NULL};
  return &_RAREaPlatformTabPainter;
}

@end
