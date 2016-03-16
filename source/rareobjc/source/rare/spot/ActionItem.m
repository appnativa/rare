//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/ActionItem.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/spot/ActionItem.h"
#include "com/appnativa/rare/spot/Link.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/Boolean.h"
#include "java/lang/ClassCastException.h"

@implementation RARESPOTActionItem

- (id)init {
  return [self initRARESPOTActionItemWithBoolean:YES];
}

- (id)initRARESPOTActionItemWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    name_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    value_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    tooltip_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    icon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    disabledIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    selectedIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    enabled_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    focusedAction_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    enabledOnSelectionOnly_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    enabledIfHasValueOnly_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    groupName_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    shortcutKeystroke_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    linkedData_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    actionLink_ = nil;
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTActionItemWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    name_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    value_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    tooltip_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    icon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    disabledIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    selectedIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    enabled_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    focusedAction_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    enabledOnSelectionOnly_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    enabledIfHasValueOnly_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    groupName_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    shortcutKeystroke_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    linkedData_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    actionLink_ = nil;
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 14;
  self->attributeSizeHint_ += 1;
  [super spot_setElements];
  [self spot_defineAttributeWithNSString:@"onAction" withNSString:nil];
  [self spot_addElementWithNSString:@"name" withISPOTElement:name_];
  [self spot_addElementWithNSString:@"value" withISPOTElement:value_];
  [self spot_addElementWithNSString:@"tooltip" withISPOTElement:tooltip_];
  [self spot_addElementWithNSString:@"icon" withISPOTElement:icon_];
  [((SPOTPrintableString *) nil_chk(icon_)) spot_defineAttributeWithNSString:@"alt" withNSString:nil];
  [icon_ spot_defineAttributeWithNSString:@"slice" withNSString:nil];
  [icon_ spot_defineAttributeWithNSString:@"size" withNSString:nil];
  [icon_ spot_defineAttributeWithNSString:@"scaling" withNSString:nil];
  [icon_ spot_defineAttributeWithNSString:@"density" withNSString:nil];
  [self spot_addElementWithNSString:@"disabledIcon" withISPOTElement:disabledIcon_];
  [((SPOTPrintableString *) nil_chk(disabledIcon_)) spot_defineAttributeWithNSString:@"alt" withNSString:nil];
  [disabledIcon_ spot_defineAttributeWithNSString:@"slice" withNSString:nil];
  [disabledIcon_ spot_defineAttributeWithNSString:@"size" withNSString:nil];
  [disabledIcon_ spot_defineAttributeWithNSString:@"scaling" withNSString:nil];
  [disabledIcon_ spot_defineAttributeWithNSString:@"density" withNSString:nil];
  [self spot_addElementWithNSString:@"selectedIcon" withISPOTElement:selectedIcon_];
  [((SPOTPrintableString *) nil_chk(selectedIcon_)) spot_defineAttributeWithNSString:@"alt" withNSString:nil];
  [selectedIcon_ spot_defineAttributeWithNSString:@"slice" withNSString:nil];
  [selectedIcon_ spot_defineAttributeWithNSString:@"size" withNSString:nil];
  [selectedIcon_ spot_defineAttributeWithNSString:@"scaling" withNSString:nil];
  [selectedIcon_ spot_defineAttributeWithNSString:@"density" withNSString:nil];
  [self spot_addElementWithNSString:@"enabled" withISPOTElement:enabled_];
  [self spot_addElementWithNSString:@"focusedAction" withISPOTElement:focusedAction_];
  [self spot_addElementWithNSString:@"enabledOnSelectionOnly" withISPOTElement:enabledOnSelectionOnly_];
  [self spot_addElementWithNSString:@"enabledIfHasValueOnly" withISPOTElement:enabledIfHasValueOnly_];
  [self spot_addElementWithNSString:@"groupName" withISPOTElement:groupName_];
  [self spot_addElementWithNSString:@"shortcutKeystroke" withISPOTElement:shortcutKeystroke_];
  [self spot_addElementWithNSString:@"linkedData" withISPOTElement:linkedData_];
  [self spot_addElementWithNSString:@"actionLink" withISPOTElement:actionLink_];
}

- (RARESPOTLink *)getActionLink {
  return actionLink_;
}

- (RARESPOTLink *)getActionLinkReference {
  if (actionLink_ == nil) {
    actionLink_ = [[RARESPOTLink alloc] initWithBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"actionLink" withISPOTElement:actionLink_];
  }
  return actionLink_;
}

- (void)setActionLinkWithISPOTElement:(id<iSPOTElement>)reference {
  actionLink_ = (RARESPOTLink *) check_class_cast(reference, [RARESPOTLink class]);
  (void) [self spot_setReferenceWithNSString:@"actionLink" withISPOTElement:reference];
}

- (void)copyAllFieldsTo:(RARESPOTActionItem *)other {
  [super copyAllFieldsTo:other];
  other->actionLink_ = actionLink_;
  other->disabledIcon_ = disabledIcon_;
  other->enabled_ = enabled_;
  other->enabledIfHasValueOnly_ = enabledIfHasValueOnly_;
  other->enabledOnSelectionOnly_ = enabledOnSelectionOnly_;
  other->focusedAction_ = focusedAction_;
  other->groupName_ = groupName_;
  other->icon_ = icon_;
  other->linkedData_ = linkedData_;
  other->name_ = name_;
  other->selectedIcon_ = selectedIcon_;
  other->shortcutKeystroke_ = shortcutKeystroke_;
  other->tooltip_ = tooltip_;
  other->value_ = value_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
    { "getActionLink", NULL, "LRARESPOTLink", 0x1, NULL },
    { "getActionLinkReference", NULL, "LRARESPOTLink", 0x1, NULL },
    { "setActionLinkWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "name_", NULL, 0x1, "LSPOTPrintableString" },
    { "value_", NULL, 0x1, "LSPOTPrintableString" },
    { "tooltip_", NULL, 0x1, "LSPOTPrintableString" },
    { "icon_", NULL, 0x1, "LSPOTPrintableString" },
    { "disabledIcon_", NULL, 0x1, "LSPOTPrintableString" },
    { "selectedIcon_", NULL, 0x1, "LSPOTPrintableString" },
    { "enabled_", NULL, 0x1, "LSPOTBoolean" },
    { "focusedAction_", NULL, 0x1, "LSPOTBoolean" },
    { "enabledOnSelectionOnly_", NULL, 0x1, "LSPOTBoolean" },
    { "enabledIfHasValueOnly_", NULL, 0x1, "LSPOTBoolean" },
    { "groupName_", NULL, 0x1, "LSPOTPrintableString" },
    { "shortcutKeystroke_", NULL, 0x1, "LSPOTPrintableString" },
    { "linkedData_", NULL, 0x1, "LSPOTPrintableString" },
    { "actionLink_", NULL, 0x4, "LRARESPOTLink" },
  };
  static J2ObjcClassInfo _RARESPOTActionItem = { "ActionItem", "com.appnativa.rare.spot", NULL, 0x1, 5, methods, 14, fields, 0, NULL};
  return &_RARESPOTActionItem;
}

@end
