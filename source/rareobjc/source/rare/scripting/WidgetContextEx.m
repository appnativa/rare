//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/scripting/WidgetContextEx.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/scripting/JavaScriptEngine.h"
#include "com/appnativa/rare/scripting/WidgetContextEx.h"
#include "com/appnativa/rare/widget/iWidget.h"

@implementation RAREWidgetContextEx

- (void)dispose {
  if (scriptEngine_ != nil) {
    RAREJavaScriptEngine *js = (RAREJavaScriptEngine *) check_class_cast(scriptEngine_, [RAREJavaScriptEngine class]);
    [js disposeJSObjectForScriptingContextWithRAREWidgetContext:self];
    if ([scriptObject_ conformsToProtocol: @protocol(RAREiWidget)]) {
      if ([js getWithNSString:@"form"] == scriptObject_) {
        [js putWithNSString:@"form" withId:nil];
      }
      if ([js getWithNSString:@"widget"] == scriptObject_) {
        [js putWithNSString:@"widget" withId:nil];
      }
      if ([js getWithNSString:@"window"] == scriptObject_) {
        [js putWithNSString:@"window" withId:nil];
      }
    }
  }
  [super dispose];
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREWidgetContextEx = { "WidgetContextEx", "com.appnativa.rare.scripting", NULL, 0x1, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREWidgetContextEx;
}

@end
