//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/ScriptActionListener.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/scripting/WidgetContext.h"
#include "com/appnativa/rare/scripting/iScriptHandler.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/event/ScriptActionListener.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/iWidget.h"

@implementation RAREScriptActionListener

- (id)initWithRAREiWidget:(id<RAREiWidget>)context
             withNSString:(NSString *)code {
  if (self = [super init]) {
    self->context_ = context;
    self->code_ = code;
  }
  return self;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)context
                   withId:(id)source
             withNSString:(NSString *)code {
  if (self = [super init]) {
    self->context_ = context;
    self->code_ = code;
    self->source_ = source;
  }
  return self;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e {
  id<RAREiWidget> w = self->context_;
  if (w == nil) {
    w = [RAREPlatform getContextRootViewer];
  }
  id<RAREiScriptHandler> sh = [((id<RAREiWidget>) nil_chk(w)) getScriptHandler];
  if ([code_ isKindOfClass:[NSString class]]) {
    code_ = [((id<RAREiScriptHandler>) nil_chk(sh)) compileWithRAREWidgetContext:[w getScriptingContext] withNSString:@"scriptActionListener" withNSString:(NSString *) check_class_cast(code_, [NSString class])];
  }
  id s = (self->source_ == nil) ? ((id) w) : self->source_;
  [RAREaWidgetListener executeWithRAREiWidget:context_ withRAREiScriptHandler:sh withId:code_ withNSString:[RAREiConstants EVENT_ACTION] withJavaUtilEventObject:[[RAREActionEvent alloc] initWithId:s withNSString:@"onAction"]];
}

- (void)copyAllFieldsTo:(RAREScriptActionListener *)other {
  [super copyAllFieldsTo:other];
  other->code_ = code_;
  other->context_ = context_;
  other->source_ = source_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "code_", NULL, 0x0, "LNSObject" },
    { "context_", NULL, 0x0, "LRAREiWidget" },
    { "source_", NULL, 0x0, "LNSObject" },
  };
  static J2ObjcClassInfo _RAREScriptActionListener = { "ScriptActionListener", "com.appnativa.rare.ui.event", NULL, 0x1, 0, NULL, 3, fields, 0, NULL};
  return &_RAREScriptActionListener;
}

@end
