//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/listener/WidgetScriptChangeListener.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/scripting/WidgetContext.h"
#include "com/appnativa/rare/scripting/iScriptHandler.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/listener/WidgetScriptChangeListener.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/util/EventObject.h"

@implementation RAREWidgetScriptChangeListener

- (id)initWithNSString:(NSString *)code {
  if (self = [super init]) {
    direction_ = 1;
    eventName_ = @"_OnScrollBarChange";
    self->code_ = code;
  }
  return self;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)widget
                   withId:(id)code {
  return [self initRAREWidgetScriptChangeListenerWithRAREiWidget:widget withId:code withNSString:nil];
}

- (id)initRAREWidgetScriptChangeListenerWithRAREiWidget:(id<RAREiWidget>)widget
                                                 withId:(id)code
                                           withNSString:(NSString *)eventName {
  if (self = [super init]) {
    direction_ = 1;
    eventName_ = @"_OnScrollBarChange";
    self->widget_ = widget;
    self->code_ = code;
    [self setEventNameWithNSString:eventName];
  }
  return self;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)widget
                   withId:(id)code
             withNSString:(NSString *)eventName {
  return [self initRAREWidgetScriptChangeListenerWithRAREiWidget:widget withId:code withNSString:eventName];
}

- (void)setEventNameWithNSString:(NSString *)name {
  if (name != nil) {
    eventName_ = name;
  }
}

- (void)setCodeWithId:(id)code {
  self->code_ = code;
}

- (void)setWidgetWithRAREiWidget:(id<RAREiWidget>)widget {
  self->widget_ = widget;
}

- (id)getCode {
  return code_;
}

- (id<RAREiWidget>)getWidget {
  return widget_;
}

- (void)stateChangedWithJavaUtilEventObject:(JavaUtilEventObject *)e {
  id<RAREiWidget> w = [self getWidget];
  id<RAREiScriptHandler> sh = [((id<RAREiWidget>) nil_chk(w)) getScriptHandler];
  if ([code_ isKindOfClass:[NSString class]]) {
    code_ = [((id<RAREiScriptHandler>) nil_chk(sh)) compileWithRAREWidgetContext:[w getScriptingContext] withNSString:[NSString stringWithFormat:@"%@%@", [w getName], eventName_] withNSString:(NSString *) check_class_cast(code_, [NSString class])];
  }
  (void) [RAREaWidgetListener evaluateWithRAREiWidget:w withRAREiScriptHandler:sh withId:code_ withNSString:[RAREiConstants EVENT_CHANGE] withJavaUtilEventObject:e];
}

- (void)copyAllFieldsTo:(RAREWidgetScriptChangeListener *)other {
  [super copyAllFieldsTo:other];
  other->code_ = code_;
  other->direction_ = direction_;
  other->eventName_ = eventName_;
  other->widget_ = widget_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getCode", NULL, "LNSObject", 0x1, NULL },
    { "getWidget", NULL, "LRAREiWidget", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "direction_", NULL, 0x0, "I" },
  };
  static J2ObjcClassInfo _RAREWidgetScriptChangeListener = { "WidgetScriptChangeListener", "com.appnativa.rare.ui.listener", NULL, 0x1, 2, methods, 1, fields, 0, NULL};
  return &_RAREWidgetScriptChangeListener;
}

@end
