//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-tabpane/com/appnativa/rare/ui/tabpane/aPlatformTabPainter.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/UIAction.h"
#include "com/appnativa/rare/ui/painter/UIComponentPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/ui/tabpane/TabLabel.h"
#include "com/appnativa/rare/ui/tabpane/aPlatformTabPainter.h"
#include "com/appnativa/rare/ui/tabpane/iTabLabel.h"

@implementation RAREaPlatformTabPainter

- (id<RAREiTabLabel>)createNewRendererWithRAREUIAction:(RAREUIAction *)a {
  return [[RARETabLabel alloc] initWithRAREUIAction:a];
}

- (void)updatePaintersModCount {
  if (normalComponentPainter_ != nil) {
    [normalComponentPainter_ updateModCount];
  }
  else if (tabPainter_ != nil) {
    [RAREUIComponentPainter updateBucketModCountWithRAREPaintBucket:tabPainter_];
  }
  if (selectedComponentPainter_ != nil) {
    [selectedComponentPainter_ updateModCount];
  }
  else if (selectedPainter_ != nil) {
    [RAREUIComponentPainter updateBucketModCountWithRAREPaintBucket:selectedPainter_];
  }
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
