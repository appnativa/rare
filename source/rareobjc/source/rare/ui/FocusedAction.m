//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/FocusedAction.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/ActionHelper.h"
#include "com/appnativa/rare/spot/ActionItem.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/FocusedAction.h"
#include "com/appnativa/rare/ui/UIAction.h"
#include "com/appnativa/rare/ui/aUIAction.h"
#include "com/appnativa/rare/ui/dnd/iTransferSupport.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"

@implementation RAREFocusedAction

- (id)initWithRAREUIAction:(RAREUIAction *)a {
  if (self = [super initWithRAREUIAction:a]) {
    [self update];
  }
  return self;
}

- (id)initWithNSString:(NSString *)name {
  if (self = [super initWithNSString:name]) {
    [self update];
  }
  return self;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)context
   withRARESPOTActionItem:(RARESPOTActionItem *)item {
  if (self = [super initWithRAREiWidget:context withRARESPOTActionItem:item]) {
    [self update];
  }
  return self;
}

- (id)initWithNSString:(NSString *)name
          withNSString:(NSString *)text
 withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  if (self = [super initWithNSString:name withNSString:text withRAREiPlatformIcon:icon]) {
    [self update];
  }
  return self;
}

- (void)dispose {
  [super dispose];
  id<RAREiPlatformComponent> c = _focusedComponent_;
  _focusedComponent_ = nil;
  if (c != nil) {
  }
}

- (void)update {
  [self updateWithRAREiPlatformComponent:[((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getPermanentFocusOwner]];
}

- (void)updateWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)permanentFocusOwner {
  RAREComponent *comp = ([(id) permanentFocusOwner isKindOfClass:[RAREComponent class]]) ? (RAREComponent *) check_class_cast(permanentFocusOwner, [RAREComponent class]) : nil;
  if ((comp != nil) && ![comp isDisplayable]) {
    comp = nil;
  }
  [self setFocusedComponentWithRAREiPlatformComponent:comp];
}

- (void)refresh {
  id<RAREiPlatformComponent> c = [self getFocusedComponent];
  if (c != nil) {
    id<RAREiWidget> w = [RAREaWidget getWidgetForComponentWithRAREiPlatformComponent:c];
    if ((w == nil) || ![self isActionSupportedWithRAREiWidget:w withRAREiPlatformComponent:c]) {
      [self setEnabledWithBoolean:NO];
    }
    else {
      [self updateEnabledFromTarget];
    }
  }
  else {
    [self setEnabledWithBoolean:NO];
  }
}

- (void)updateEnabledFromTarget {
  id<RAREiPlatformComponent> c = [self getFocusedComponent];
  BOOL on = YES;
  if ([self isEnabledOnSelectionOnly]) {
    if ([(id) c conformsToProtocol: @protocol(RAREiTransferSupport)]) {
      on = [((id<RAREiTransferSupport>) check_protocol_cast(c, @protocol(RAREiTransferSupport))) hasSelection];
    }
    else {
      id<RAREiWidget> w = [RAREaWidget getWidgetForComponentWithRAREiPlatformComponent:c];
      if ((w != nil) && ![w hasSelection]) {
        on = NO;
      }
    }
  }
  else if ([self isEnabledIfHasValueOnly]) {
    id<RAREiWidget> w = [RAREaWidget getWidgetForComponentWithRAREiPlatformComponent:c];
    if ((w != nil) && ![w hasValue]) {
      on = NO;
    }
  }
  [self setEnabledWithBoolean:on];
}

- (void)setFocusedComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  BOOL validTarget = NO;
  if (component != nil) {
    NSString *name = [self getActionPropertyName];
    if ((name != nil) && [RAREActionHelper isEnabledWithRAREiPlatformComponent:component withNSString:name]) {
      id<RAREiWidget> w = [RAREaWidget getWidgetForComponentWithRAREiPlatformComponent:component];
      validTarget = (w == nil) || [self isActionSupportedWithRAREiWidget:w withRAREiPlatformComponent:component];
    }
  }
  if (!validTarget) {
    [self setFocusedComponentExWithRAREiPlatformComponent:nil];
  }
  else {
    [self setFocusedComponentExWithRAREiPlatformComponent:component];
    [self updateEnabledFromTarget];
  }
}

- (void)setFocusedComponentExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  if (_focusedComponent_ != nil) {
  }
  if ((component != nil) && ![component isDisplayable]) {
    component = nil;
  }
  _focusedComponent_ = component;
  if (_focusedComponent_ != nil) {
    if ([self isEnabledOnSelectionOnly]) {
    }
  }
  else {
    [self setEnabledWithBoolean:NO];
  }
}

- (id<RAREiPlatformComponent>)getFocusedComponent {
  return _focusedComponent_;
}

- (id<RAREiPlatformComponent>)getActionComponentWithRAREActionEvent:(RAREActionEvent *)e {
  id<RAREiPlatformComponent> c = [super getActionComponentWithRAREActionEvent:e];
  return (c == nil) ? _focusedComponent_ : c;
}

- (BOOL)isActionSupportedWithRAREiWidget:(id<RAREiWidget>)w
              withRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  return YES;
}

- (void)copyAllFieldsTo:(RAREFocusedAction *)other {
  [super copyAllFieldsTo:other];
  other->_focusedComponent_ = _focusedComponent_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "updateEnabledFromTarget", NULL, "V", 0x4, NULL },
    { "setFocusedComponentWithRAREiPlatformComponent:", NULL, "V", 0x4, NULL },
    { "setFocusedComponentExWithRAREiPlatformComponent:", NULL, "V", 0x4, NULL },
    { "getFocusedComponent", NULL, "LRAREiPlatformComponent", 0x4, NULL },
    { "getActionComponentWithRAREActionEvent:", NULL, "LRAREiPlatformComponent", 0x4, NULL },
    { "isActionSupportedWithRAREiWidget:withRAREiPlatformComponent:", NULL, "Z", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREFocusedAction = { "FocusedAction", "com.appnativa.rare.ui", NULL, 0x1, 6, methods, 0, NULL, 0, NULL};
  return &_RAREFocusedAction;
}

@end
