//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/apple/AppContext.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/platform/aAppContext.h"
#include "com/appnativa/rare/platform/aRare.h"
#include "com/appnativa/rare/platform/apple/AppContext.h"
#include "com/appnativa/rare/platform/apple/ui/view/Window.h"
#include "java/lang/Runnable.h"

@implementation RAREAppContext

- (id)initWithRAREaRare:(RAREaRare *)instance {
  return [super initWithRAREaRare:instance];
}

+ (RAREAppContext *)getContext {
  return (RAREAppContext *) check_class_cast([RAREaAppContext _instance], [RAREAppContext class]);
}

- (RAREWindow *)getPlatformMainWindow {
  return mainWindow_;
}

- (void)didReceiveMemoryWarning {
  if (lowMemoryWarningHandler_ != nil) {
    [lowMemoryWarningHandler_ run];
  }
}

- (void)copyAllFieldsTo:(RAREAppContext *)other {
  [super copyAllFieldsTo:other];
  other->mainWindow_ = mainWindow_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getContext", NULL, "LRAREAppContext", 0x9, NULL },
    { "getPlatformMainWindow", NULL, "LRAREWindow", 0x1, NULL },
    { "didReceiveMemoryWarning", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "mainWindow_", NULL, 0x0, "LRAREWindow" },
  };
  static J2ObjcClassInfo _RAREAppContext = { "AppContext", "com.appnativa.rare.platform.apple", NULL, 0x1, 3, methods, 1, fields, 0, NULL};
  return &_RAREAppContext;
}

@end