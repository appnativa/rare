//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/scripting/DynamicBindings.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/scripting/DynamicBindings.h"
#include "com/appnativa/rare/scripting/ScriptingEvent.h"
#include "com/appnativa/rare/scripting/aScriptManager.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "com/appnativa/rare/viewer/aWindowViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/viewer/iFormViewer.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/lang/Throwable.h"
#include "java/util/Collection.h"
#include "java/util/HashMap.h"
#include "java/util/Map.h"
#include "java/util/Set.h"
#include "javax/script/Bindings.h"
#include "javax/script/ScriptEngine.h"

@implementation RAREDynamicBindings

static NSString * RAREDynamicBindings_FORM_ = @"form";
static NSString * RAREDynamicBindings_TOP_ = @"_top";
static NSString * RAREDynamicBindings_WIDGET_ = @"widget";
static NSString * RAREDynamicBindings_WINDOW_ = @"window";
static JavaUtilHashMap * RAREDynamicBindings_generatedValues_;

+ (NSString *)FORM {
  return RAREDynamicBindings_FORM_;
}

+ (NSString *)TOP {
  return RAREDynamicBindings_TOP_;
}

+ (NSString *)WIDGET {
  return RAREDynamicBindings_WIDGET_;
}

+ (NSString *)WINDOW {
  return RAREDynamicBindings_WINDOW_;
}

+ (JavaUtilHashMap *)generatedValues {
  return RAREDynamicBindings_generatedValues_;
}

- (id)initWithJavaxScriptBindings:(id<JavaxScriptBindings>)bindings
           withRAREaScriptManager:(RAREaScriptManager *)sm {
  if (self = [super init]) {
    self->bindings_ = bindings;
    self->scriptNamager_ = sm;
  }
  return self;
}

- (void)clear {
  [((id<JavaxScriptBindings>) nil_chk(bindings_)) clear];
}

- (BOOL)containsKeyWithId:(id)key {
  return [((id<JavaxScriptBindings>) nil_chk(bindings_)) containsKeyWithId:key] ? YES : [self getOtherWithId:key] != nil;
}

- (BOOL)containsValueWithId:(id)value {
  return [((id<JavaxScriptBindings>) nil_chk(bindings_)) containsValueWithId:value];
}

- (id<JavaUtilSet>)entrySet {
  return [((id<JavaxScriptBindings>) nil_chk(bindings_)) entrySet];
}

- (id<JavaUtilSet>)keySet {
  return [((id<JavaxScriptBindings>) nil_chk(bindings_)) keySet];
}

- (id)putWithId:(NSString *)name
         withId:(id)value {
  return [((id<JavaxScriptBindings>) nil_chk(bindings_)) putWithId:name withId:value];
}

- (void)putAllWithJavaUtilMap:(id<JavaUtilMap>)toMerge {
  [((id<JavaxScriptBindings>) nil_chk(bindings_)) putAllWithJavaUtilMap:toMerge];
}

- (id)removeWithId:(id)key {
  return [((id<JavaxScriptBindings>) nil_chk(bindings_)) removeWithId:key];
}

- (int)size {
  return [((id<JavaxScriptBindings>) nil_chk(bindings_)) size];
}

- (id<JavaUtilCollection>)values {
  return [((id<JavaxScriptBindings>) nil_chk(bindings_)) values];
}

- (id)getWithId:(id)key {
  id o = [((id<JavaxScriptBindings>) nil_chk(bindings_)) getWithId:key];
  return (o == nil) ? [self getOtherWithId:key] : o;
}

- (BOOL)isEmpty {
  return [((id<JavaxScriptBindings>) nil_chk(bindings_)) isEmpty];
}

- (id)getOtherWithId:(id)key {
  if ((key == [JavaxScriptScriptEngine FILENAME]) || ([nil_chk(key) isEqual:@"Function"]) || ([key isEqual:@"Object"]) || ([key isEqual:RAREDynamicBindings_WIDGET_]) || ([key isEqual:RAREDynamicBindings_FORM_]) || ([key isEqual:RAREDynamicBindings_WINDOW_])) {
    return nil;
  }
  RAREWindowViewer *win = nil;
  id o = nil;
  @try {
    NSString *s = (NSString *) check_class_cast(key, [NSString class]);
    id w = [RAREPlatform isUIThread] ? [((RAREaScriptManager *) nil_chk(scriptNamager_)) getWidget] : nil;
    if ([w conformsToProtocol: @protocol(RAREiContainer)]) {
      o = [((id<RAREiContainer>) check_protocol_cast(w, @protocol(RAREiContainer))) getWidgetWithNSString:s];
    }
    if (o == nil) {
      if ([w isKindOfClass:[RAREWindowViewer class]]) {
        win = (RAREWindowViewer *) check_class_cast(w, [RAREWindowViewer class]);
      }
      else if (w != nil) {
        id f = [RAREPlatform isUIThread] ? [((RAREaScriptManager *) nil_chk(scriptNamager_)) getFormViewer] : nil;
        if ((f != w) && ([f conformsToProtocol: @protocol(RAREiFormViewer)])) {
          o = [((id<RAREiFormViewer>) check_protocol_cast(f, @protocol(RAREiFormViewer))) getWidgetWithNSString:s];
        }
      }
      if (o == nil) {
        if (win == nil) {
          win = [((RAREaScriptManager *) nil_chk(scriptNamager_)) getWindow];
        }
        if (win != nil) {
          if (win != w) {
            o = [win getViewerWithNSString:s];
          }
          if (o == nil) {
            key = [((JavaUtilHashMap *) nil_chk(RAREDynamicBindings_generatedValues_)) getWithId:s];
            if (key != nil) {
              if ([key isEqual:@"event"]) {
                o = [win getEvent];
              }
              else if ([key isEqual:@"document"]) {
                o = win;
              }
              else if ([key isEqual:@"navigator"]) {
                o = [win getNavigator];
              }
            }
          }
        }
      }
    }
  }
  @catch (JavaLangThrowable *ignore) {
    [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:ignore];
  }
  return o;
}

+ (void)initialize {
  if (self == [RAREDynamicBindings class]) {
    RAREDynamicBindings_generatedValues_ = [[JavaUtilHashMap alloc] initWithInt:10];
    {
      (void) [RAREDynamicBindings_generatedValues_ putWithId:@"document" withId:@"document"];
      (void) [RAREDynamicBindings_generatedValues_ putWithId:@"navigator" withId:@"navigator"];
      (void) [RAREDynamicBindings_generatedValues_ putWithId:@"event" withId:@"event"];
    }
  }
}

- (void)copyAllFieldsTo:(RAREDynamicBindings *)other {
  [super copyAllFieldsTo:other];
  other->bindings_ = bindings_;
  other->scriptNamager_ = scriptNamager_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "containsKeyWithId:", NULL, "Z", 0x1, NULL },
    { "containsValueWithId:", NULL, "Z", 0x1, NULL },
    { "entrySet", NULL, "LJavaUtilSet", 0x1, NULL },
    { "keySet", NULL, "LJavaUtilSet", 0x1, NULL },
    { "putWithNSString:withId:", NULL, "LNSObject", 0x1, NULL },
    { "removeWithId:", NULL, "LNSObject", 0x1, NULL },
    { "values", NULL, "LJavaUtilCollection", 0x1, NULL },
    { "getWithId:", NULL, "LNSObject", 0x1, NULL },
    { "isEmpty", NULL, "Z", 0x1, NULL },
    { "getOtherWithId:", NULL, "LNSObject", 0x14, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "FORM_", NULL, 0x19, "LNSString" },
    { "TOP_", NULL, 0x19, "LNSString" },
    { "WIDGET_", NULL, 0x19, "LNSString" },
    { "WINDOW_", NULL, 0x19, "LNSString" },
    { "generatedValues_", NULL, 0x18, "LJavaUtilHashMap" },
    { "bindings_", NULL, 0x10, "LJavaxScriptBindings" },
    { "scriptNamager_", NULL, 0x10, "LRAREaScriptManager" },
  };
  static J2ObjcClassInfo _RAREDynamicBindings = { "DynamicBindings", "com.appnativa.rare.scripting", NULL, 0x1, 10, methods, 7, fields, 0, NULL};
  return &_RAREDynamicBindings;
}

@end
