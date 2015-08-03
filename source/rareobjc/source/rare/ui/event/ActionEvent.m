//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/ActionEvent.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/ui/aUIAction.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/widget/iWidget.h"

@implementation RAREActionEvent

+ (int)ACTION_PERFORMED {
  return RAREActionEvent_ACTION_PERFORMED;
}

- (id)initWithId:(id)source {
  return [super initWithId:source withInt:RAREActionEvent_ACTION_PERFORMED];
}

- (id)initWithId:(id)source
          withId:(id)sourceEvent {
  if (self = [super initWithId:source withInt:RAREActionEvent_ACTION_PERFORMED]) {
    self->sourceEvent_ = sourceEvent;
  }
  return self;
}

- (id)initWithId:(id)source
    withNSString:(NSString *)actionCommand {
  if (self = [super initWithId:source]) {
    self->actionCommand_ = actionCommand;
  }
  return self;
}

- (void)setActionCommandWithNSString:(NSString *)actionCommand {
  self->actionCommand_ = actionCommand;
}

- (NSString *)getActionCommand {
  return actionCommand_;
}

- (id<RAREiPlatformComponent>)getComponent {
  if ([source_ isKindOfClass:[RAREaUIAction class]]) {
    id<RAREiWidget> w = [((RAREaUIAction *) check_class_cast(source_, [RAREaUIAction class])) getContext];
    return (w == nil) ? [RAREPlatform getPlatformComponentWithId:sourceEvent_] : [w getContainerComponent];
  }
  else {
    return [super getComponent];
  }
}

- (id)getSourceEvent {
  return sourceEvent_;
}

- (void)copyAllFieldsTo:(RAREActionEvent *)other {
  [super copyAllFieldsTo:other];
  other->actionCommand_ = actionCommand_;
  other->sourceEvent_ = sourceEvent_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getActionCommand", NULL, "LNSString", 0x1, NULL },
    { "getComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getSourceEvent", NULL, "LNSObject", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "ACTION_PERFORMED_", NULL, 0x19, "I" },
  };
  static J2ObjcClassInfo _RAREActionEvent = { "ActionEvent", "com.appnativa.rare.ui.event", NULL, 0x1, 3, methods, 1, fields, 0, NULL};
  return &_RAREActionEvent;
}

@end