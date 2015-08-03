//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/FocusedAction.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREFocusedAction_H_
#define _RAREFocusedAction_H_

@class RAREActionEvent;
@class RARESPOTActionItem;
@class RAREUIAction;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/aFocusedAction.h"

@interface RAREFocusedAction : RAREaFocusedAction {
 @public
  id<RAREiPlatformComponent> _focusedComponent_;
}

- (id)initWithRAREUIAction:(RAREUIAction *)a;
- (id)initWithNSString:(NSString *)name;
- (id)initWithRAREiWidget:(id<RAREiWidget>)context
   withRARESPOTActionItem:(RARESPOTActionItem *)item;
- (id)initWithNSString:(NSString *)name
          withNSString:(NSString *)text
 withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)dispose;
- (void)update;
- (void)updateWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)permanentFocusOwner;
- (void)refresh;
- (void)updateEnabledFromTarget;
- (void)setFocusedComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
- (void)setFocusedComponentExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
- (id<RAREiPlatformComponent>)getFocusedComponent;
- (id<RAREiPlatformComponent>)getActionComponentWithRAREActionEvent:(RAREActionEvent *)e;
- (BOOL)isActionSupportedWithRAREiWidget:(id<RAREiWidget>)w
              withRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
- (void)copyAllFieldsTo:(RAREFocusedAction *)other;
@end

J2OBJC_FIELD_SETTER(RAREFocusedAction, _focusedComponent_, id<RAREiPlatformComponent>)

typedef RAREFocusedAction ComAppnativaRareUiFocusedAction;

#endif // _RAREFocusedAction_H_