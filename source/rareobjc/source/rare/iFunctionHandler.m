//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/iFunctionHandler.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/iFunctionHandler.h"
#include "com/appnativa/rare/scripting/Functions.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/text/ParseException.h"


@interface RAREiFunctionHandler : NSObject
@end

@implementation RAREiFunctionHandler

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "executeWithRAREiWidget:withNSString:", NULL, "LNSString", 0x401, "JavaTextParseException" },
    { "executeFunctionWithRAREiWidget:withNSString:withNSStringArray:", NULL, "LNSString", 0x401, NULL },
    { "getFunctions", NULL, "LRAREFunctions", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiFunctionHandler = { "iFunctionHandler", "com.appnativa.rare", NULL, 0x201, 3, methods, 0, NULL, 0, NULL};
  return &_RAREiFunctionHandler;
}

@end