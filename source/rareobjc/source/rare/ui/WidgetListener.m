//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/WidgetListener.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/scripting/iScriptHandler.h"
#include "com/appnativa/rare/ui/WidgetListener.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/util/Map.h"

@implementation RAREWidgetListener

- (id)initWithRAREiWidget:(id<RAREiWidget>)widget
          withJavaUtilMap:(id<JavaUtilMap>)map
   withRAREiScriptHandler:(id<RAREiScriptHandler>)sh {
  return [super initWithRAREiWidget:widget withJavaUtilMap:map withRAREiScriptHandler:sh];
}

- (id<RAREiPlatformComponent>)getSourceWithId:(id)view {
  RAREView *v = (RAREView *) check_class_cast(view, [RAREView class]);
  id<RAREiPlatformComponent> c = (v == nil) ? nil : [v getComponent];
  if (c == nil) {
    c = [((id<RAREiWidget>) nil_chk(theWidget_)) getDataComponent];
  }
  return c;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getSourceWithId:", NULL, "LRAREiPlatformComponent", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREWidgetListener = { "WidgetListener", "com.appnativa.rare.ui", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RAREWidgetListener;
}

@end
