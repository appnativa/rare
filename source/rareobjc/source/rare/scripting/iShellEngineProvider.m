//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/scripting/iShellEngineProvider.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/scripting/iScriptingEngine.h"
#include "com/appnativa/rare/scripting/iScriptingIO.h"
#include "com/appnativa/rare/scripting/iShellEngineProvider.h"
#include "java/util/List.h"


@interface RAREiShellEngineProvider : NSObject
@end

@implementation RAREiShellEngineProvider

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getDefaultEngineWithRAREiScriptingIO:", NULL, "LRAREiScriptingEngine", 0x401, NULL },
    { "getEngineWithNSString:withRAREiScriptingIO:", NULL, "LRAREiScriptingEngine", 0x401, NULL },
    { "getLanguages", NULL, "LJavaUtilList", 0x401, NULL },
    { "getTargets", NULL, "LJavaUtilList", 0x401, NULL },
    { "getViewers", NULL, "LJavaUtilList", 0x401, NULL },
    { "getWidgetNamesWithNSString:", NULL, "LJavaUtilList", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiShellEngineProvider = { "iShellEngineProvider", "com.appnativa.rare.scripting", NULL, 0x201, 6, methods, 0, NULL, 0, NULL};
  return &_RAREiShellEngineProvider;
}

@end
