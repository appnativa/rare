//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aUIMenuItem.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/scripting/WidgetContext.h"
#include "com/appnativa/rare/scripting/iScriptHandler.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIAction.h"
#include "com/appnativa/rare/ui/UIMenuItem.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/aUIMenuItem.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/util/iFilterableList.h"
#include "java/beans/PropertyChangeEvent.h"
#include "java/lang/CharSequence.h"
#include "java/lang/StringBuilder.h"
#include "java/util/List.h"

@implementation RAREaUIMenuItem

- (id)init {
  return [super init];
}

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text {
  return [self initRAREaUIMenuItemWithJavaLangCharSequence:text withRAREiPlatformIcon:nil withId:nil];
}

- (id)initWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  if (self = [self initRAREaUIMenuItemWithJavaLangCharSequence:[((RARERenderableDataItem *) nil_chk(item)) description] withRAREiPlatformIcon:[item getIcon] withId:[item getLinkedData]]) {
    [self setActionListenerWithRAREiActionListener:[item getActionListener]];
    [self setDisabledIconWithRAREiPlatformIcon:[item getDisabledIcon]];
    [self setEnabledWithBoolean:[item isEnabled]];
    [self setLinkedDataWithId:[item getLinkedData]];
  }
  return self;
}

- (id)initWithRAREUIAction:(RAREUIAction *)a {
  return [self initRAREaUIMenuItemWithRAREUIAction:a withBoolean:NO];
}

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
                           withInt:(int)mn {
  if (self = [self initRAREaUIMenuItemWithJavaLangCharSequence:text withRAREiPlatformIcon:nil withId:nil]) {
    mnemonic_ = mn;
  }
  return self;
}

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
             withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  return [self initRAREaUIMenuItemWithJavaLangCharSequence:text withRAREiPlatformIcon:icon withId:nil];
}

- (id)initRAREaUIMenuItemWithRAREUIAction:(RAREUIAction *)a
                              withBoolean:(BOOL)checkbox {
  if (self = [super init]) {
    checkable_ = checkbox;
    theType_ = RARERenderableDataItem_TYPE_STRING;
    [self setActionWithRAREUIAction:a];
  }
  return self;
}

- (id)initWithRAREUIAction:(RAREUIAction *)a
               withBoolean:(BOOL)checkbox {
  return [self initRAREaUIMenuItemWithRAREUIAction:a withBoolean:checkbox];
}

- (id)initRAREaUIMenuItemWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
                            withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                           withId:(id)data {
  if (self = [super init]) {
    [self setValueWithId:text];
    [self setIconWithRAREiPlatformIcon:icon];
    linkedData_ = data;
  }
  return self;
}

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
             withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                            withId:(id)data {
  return [self initRAREaUIMenuItemWithJavaLangCharSequence:text withRAREiPlatformIcon:icon withId:data];
}

+ (RAREUIMenuItem *)createCheckboxMenuItemWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  NSString *text = [((RARERenderableDataItem *) nil_chk(item)) description];
  id<RAREiPlatformIcon> icon = [item getIcon];
  id data = [item getLinkedData];
  RAREUIMenuItem *mi = [[RAREUIMenuItem alloc] initWithJavaLangCharSequence:text withRAREiPlatformIcon:icon withId:data withBoolean:YES];
  [mi setActionListenerWithRAREiActionListener:[item getActionListener]];
  [mi setDisabledIconWithRAREiPlatformIcon:[item getDisabledIcon]];
  [mi setEnabledWithBoolean:[item isEnabled]];
  return mi;
}

+ (RAREUIMenuItem *)createCheckboxMenuItemWithNSString:(NSString *)text {
  return [RAREaUIMenuItem createCheckboxMenuItemWithJavaLangCharSequence:text withRAREiPlatformIcon:nil withId:nil];
}

+ (RAREUIMenuItem *)createCheckboxMenuItemWithNSString:(NSString *)text
                                 withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  return [RAREaUIMenuItem createCheckboxMenuItemWithJavaLangCharSequence:text withRAREiPlatformIcon:icon withId:nil];
}

+ (RAREUIMenuItem *)createCheckboxMenuItemWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
                                             withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                                            withId:(id)data {
  RAREUIMenuItem *menu = [[RAREUIMenuItem alloc] initWithJavaLangCharSequence:text withRAREiPlatformIcon:icon withId:data withBoolean:YES];
  return menu;
}

+ (RAREUIMenuItem *)createMenuItemWithJavaLangCharSequence:(id<JavaLangCharSequence>)text {
  return [RAREaUIMenuItem createMenuItemWithJavaLangCharSequence:text withRAREiPlatformIcon:nil withId:nil];
}

+ (RAREUIMenuItem *)createMenuItemWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
                                     withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  return [RAREaUIMenuItem createMenuItemWithJavaLangCharSequence:text withRAREiPlatformIcon:icon withId:nil];
}

+ (RAREUIMenuItem *)createMenuItemWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
                                     withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                                    withId:(id)data {
  return [RAREaUIMenuItem createMenuItemWithJavaLangCharSequence:text withRAREiPlatformIcon:icon withId:data withId:nil];
}

+ (RAREUIMenuItem *)createMenuItemWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
                                     withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                                    withId:(id)data
                                                    withId:(id)action {
  RAREUIMenuItem *mi = [[RAREUIMenuItem alloc] initWithJavaLangCharSequence:text withRAREiPlatformIcon:icon withId:data withBoolean:NO];
  if (action != nil) {
    [mi setActionScriptWithId:action];
  }
  return mi;
}

- (void)dispose {
  valueContext_ = nil;
  if (action_ != nil) {
    [action_ removePropertyChangeListenerWithJavaBeansPropertyChangeListener:self];
    action_ = nil;
  }
  actionListener_ = nil;
  actionScript_ = nil;
  contextWidget_ = nil;
  parentMenu_ = nil;
  parentItem_ = nil;
  id<JavaUtilList> items = [self getItems];
  int len = (items == nil) ? 0 : [items size];
  int i = 0;
  while (i < len) {
    [((RAREUIMenuItem *) check_class_cast([((id<JavaUtilList>) nil_chk(items)) getWithInt:i++], [RAREUIMenuItem class])) dispose];
  }
  [super clear];
}

- (void)propertyChangeWithJavaBeansPropertyChangeEvent:(JavaBeansPropertyChangeEvent *)pce {
  if (!([[((JavaBeansPropertyChangeEvent *) nil_chk(pce)) getSource] isKindOfClass:[RAREUIAction class]])) {
    return;
  }
  RAREUIAction *a = (RAREUIAction *) check_class_cast([pce getSource], [RAREUIAction class]);
  NSString *property = [pce getPropertyName];
  if ([@"enabled" isEqual:property]) {
    if ([((RAREUIAction *) nil_chk(a)) isEnabled]) {
      [self setEnabledWithBoolean:YES];
      if ([a getIcon] != nil) {
        [self setIconWithRAREiPlatformIcon:[a getIcon]];
      }
    }
    else {
      [self setEnabledWithBoolean:NO];
      if ([a getDisabledIcon] != nil) {
        [self setIconWithRAREiPlatformIcon:[a getDisabledIcon]];
      }
    }
  }
  else if ([@"ActionText" isEqual:property]) {
    [self setValueWithId:[pce getNewValue]];
    [self setMnemonicWithChar:(unichar) [((RAREUIAction *) nil_chk(a)) getMnemonic]];
  }
  else if ([@"SmallIcon" isEqual:property]) {
    if ([((RAREUIAction *) nil_chk(a)) isEnabled]) {
      [self setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>) check_protocol_cast([pce getNewValue], @protocol(RAREiPlatformIcon))];
    }
    else {
      if ([a getDisabledIcon] == nil) {
        [self setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>) check_protocol_cast([pce getNewValue], @protocol(RAREiPlatformIcon))];
      }
    }
  }
  else if ([@"DisabledIcon" isEqual:property]) {
    if (![((RAREUIAction *) nil_chk(a)) isEnabled]) {
      [self setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>) check_protocol_cast([pce getNewValue], @protocol(RAREiPlatformIcon))];
    }
  }
}

- (void)fireWithRAREiWidget:(id<RAREiWidget>)context {
  id<RAREiWidget> w = context;
  if (w == nil) {
    w = [self getContextWidget];
  }
  if (w == nil) {
    w = [RAREPlatform getContextRootViewer];
  }
  if (w == nil) {
    return;
  }
  if (actionListener_ != nil) {
    [actionListener_ actionPerformedWithRAREActionEvent:[[RAREActionEvent alloc] initWithId:self]];
    return;
  }
  id<RAREiScriptHandler> scriptHandler = [((id<RAREiWidget>) nil_chk(w)) getScriptHandler];
  id o = [self getActionScript];
  if (o != nil) {
    if ([o isKindOfClass:[NSString class]]) {
      NSString *name = [self getName];
      if (name == nil) {
        name = [self getText];
      }
      name = [NSString stringWithFormat:@"onAction.%@", name];
      o = [((id<RAREiScriptHandler>) nil_chk(scriptHandler)) compileWithRAREWidgetContext:[w getScriptingContext] withNSString:name withNSString:(NSString *) check_class_cast(o, [NSString class])];
      [self setActionScriptWithId:o];
    }
    RAREActionEvent *ae = [[RAREActionEvent alloc] initWithId:self];
    [RAREaWidgetListener executeWithRAREiWidget:w withRAREiScriptHandler:scriptHandler withId:o withNSString:[RAREiConstants EVENT_ACTION] withJavaUtilEventObject:ae];
  }
}

- (NSString *)description {
  return [self getText];
}

- (void)toStringWithJavaLangStringBuilder:(JavaLangStringBuilder *)sb
                             withNSString:(NSString *)tab {
  (void) [((JavaLangStringBuilder *) nil_chk([((JavaLangStringBuilder *) nil_chk(sb)) appendWithNSString:tab])) appendWithNSString:[self description]];
}

- (void)setActionWithRAREUIAction:(RAREUIAction *)a {
  [self setValueWithId:[((RAREUIAction *) nil_chk(a)) getActionText]];
  enabledOnSelection_ = [a isEnabledOnSelectionOnly];
  theName_ = [a getActionName];
  if ([a isEnabled]) {
    [self setIconWithRAREiPlatformIcon:[a getIcon]];
    [self setEnabledWithBoolean:YES];
  }
  else {
    [self setIconWithRAREiPlatformIcon:[a getDisabledIcon]];
    [self setEnabledWithBoolean:NO];
  }
  [self setLinkedDataWithId:[a getLinkedData]];
  action_ = a;
  [action_ addPropertyChangeListenerWithJavaBeansPropertyChangeListener:self];
  [self setActionListenerWithRAREiActionListener:action_];
}

- (void)setActionScriptWithId:(id)action {
  actionScript_ = action;
}

- (void)setCheckableWithBoolean:(BOOL)checkable {
  self->checkable_ = checkable;
}

- (void)setContextWidgetWithRAREiWidget:(id<RAREiWidget>)context {
  contextWidget_ = context;
}

- (void)setEnabledWithBoolean:(BOOL)enabled {
  [super setEnabledWithBoolean:enabled];
  [self updateNativeItemIconForStateWithBoolean:enabled];
}

- (void)setEnabledOnSelectionWithBoolean:(BOOL)booleanValue {
}

- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  [super setIconWithRAREiPlatformIcon:icon];
  [self updateNativeItemIconForStateWithBoolean:[self isEnabled]];
}

- (void)setMnemonicWithChar:(unichar)c {
  mnemonic_ = c;
}

- (void)setNameWithNSString:(NSString *)name {
  self->theName_ = name;
}

- (void)setParentMenuWithRARERenderableDataItem:(RARERenderableDataItem *)parent {
  self->parentMenu_ = parent;
}

- (void)setSelectedWithBoolean:(BOOL)selected {
  self->selected_ = selected;
}

- (void)setTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text {
  theValue_ = text;
}

- (void)setValueWithId:(id)value {
  if ([value isKindOfClass:[NSString class]]) {
    originalValue_ = (id<JavaLangCharSequence>) check_protocol_cast(value, @protocol(JavaLangCharSequence));
    (void) [RAREUtils setMnemonicAndTextWithRAREaUIMenuItem:self withNSString:(NSString *) check_class_cast(value, [NSString class])];
    hasVars_ = [((NSString *) check_class_cast(value, [NSString class])) indexOf:'{'] != -1;
  }
  else if ([value conformsToProtocol: @protocol(JavaLangCharSequence)]) {
    originalValue_ = (id<JavaLangCharSequence>) check_protocol_cast(value, @protocol(JavaLangCharSequence));
    [self setTextWithJavaLangCharSequence:(id<JavaLangCharSequence>) check_protocol_cast(value, @protocol(JavaLangCharSequence))];
  }
}

- (RAREUIAction *)getAction {
  return action_;
}

- (id)getActionScript {
  return actionScript_;
}

- (id<RAREiWidget>)getContextWidget {
  return contextWidget_;
}

- (int)getMnemonic {
  return mnemonic_;
}

- (NSString *)getName {
  return theName_;
}

- (id<JavaLangCharSequence>)getOriginalValue {
  return originalValue_;
}

- (RARERenderableDataItem *)getParentMenu {
  return parentMenu_;
}

- (NSString *)getText {
  return (theValue_ == nil) ? @"" : [theValue_ description];
}

- (int)getType {
  return (checkable_) ? RARERenderableDataItem_TYPE_BOOLEAN : RARERenderableDataItem_TYPE_STRING;
}

- (id)getValue {
  return originalValue_;
}

- (BOOL)hasVariables {
  return hasVars_;
}

- (BOOL)isCheckable {
  return checkable_;
}

- (BOOL)isEnabledOnSelection {
  return enabledOnSelection_;
}

- (BOOL)isSelected {
  return selected_;
}

- (BOOL)isSeparator {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)updateNativeItemIconForStateWithBoolean:(BOOL)enabled {
  id<RAREiPlatformIcon> icon = [self isEnabled] ? [self getIcon] : [self getDisabledIcon];
  if (icon == nil) {
    icon = [self getIcon];
  }
  [self setNativeItemIconWithRAREiPlatformIcon:icon];
}

- (void)setNativeItemIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)copyAllFieldsTo:(RAREaUIMenuItem *)other {
  [super copyAllFieldsTo:other];
  other->action_ = action_;
  other->actionScript_ = actionScript_;
  other->checkable_ = checkable_;
  other->contextWidget_ = contextWidget_;
  other->enabledOnSelection_ = enabledOnSelection_;
  other->hasVars_ = hasVars_;
  other->mnemonic_ = mnemonic_;
  other->originalValue_ = originalValue_;
  other->parentMenu_ = parentMenu_;
  other->selected_ = selected_;
  other->theName_ = theName_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createCheckboxMenuItemWithRARERenderableDataItem:", NULL, "LRAREUIMenuItem", 0x9, NULL },
    { "createCheckboxMenuItemWithNSString:", NULL, "LRAREUIMenuItem", 0x9, NULL },
    { "createCheckboxMenuItemWithNSString:withRAREiPlatformIcon:", NULL, "LRAREUIMenuItem", 0x9, NULL },
    { "createCheckboxMenuItemWithJavaLangCharSequence:withRAREiPlatformIcon:withId:", NULL, "LRAREUIMenuItem", 0x9, NULL },
    { "createMenuItemWithJavaLangCharSequence:", NULL, "LRAREUIMenuItem", 0x9, NULL },
    { "createMenuItemWithJavaLangCharSequence:withRAREiPlatformIcon:", NULL, "LRAREUIMenuItem", 0x9, NULL },
    { "createMenuItemWithJavaLangCharSequence:withRAREiPlatformIcon:withId:", NULL, "LRAREUIMenuItem", 0x9, NULL },
    { "createMenuItemWithJavaLangCharSequence:withRAREiPlatformIcon:withId:withId:", NULL, "LRAREUIMenuItem", 0x9, NULL },
    { "getAction", NULL, "LRAREUIAction", 0x1, NULL },
    { "getActionScript", NULL, "LNSObject", 0x1, NULL },
    { "getContextWidget", NULL, "LRAREiWidget", 0x1, NULL },
    { "getName", NULL, "LNSString", 0x1, NULL },
    { "getOriginalValue", NULL, "LJavaLangCharSequence", 0x1, NULL },
    { "getParentMenu", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "getText", NULL, "LNSString", 0x1, NULL },
    { "getValue", NULL, "LNSObject", 0x1, NULL },
    { "hasVariables", NULL, "Z", 0x1, NULL },
    { "isCheckable", NULL, "Z", 0x1, NULL },
    { "isEnabledOnSelection", NULL, "Z", 0x1, NULL },
    { "isSelected", NULL, "Z", 0x1, NULL },
    { "isSeparator", NULL, "Z", 0x401, NULL },
    { "updateNativeItemIconForStateWithBoolean:", NULL, "V", 0x4, NULL },
    { "setNativeItemIconWithRAREiPlatformIcon:", NULL, "V", 0x404, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "action_", NULL, 0x4, "LRAREUIAction" },
    { "actionScript_", NULL, 0x4, "LNSObject" },
    { "checkable_", NULL, 0x4, "Z" },
    { "contextWidget_", NULL, 0x4, "LRAREiWidget" },
    { "enabledOnSelection_", NULL, 0x4, "Z" },
    { "hasVars_", NULL, 0x4, "Z" },
    { "mnemonic_", NULL, 0x4, "I" },
    { "originalValue_", NULL, 0x4, "LJavaLangCharSequence" },
    { "parentMenu_", NULL, 0x4, "LRARERenderableDataItem" },
    { "selected_", NULL, 0x4, "Z" },
    { "theName_", NULL, 0x4, "LNSString" },
  };
  static J2ObjcClassInfo _RAREaUIMenuItem = { "aUIMenuItem", "com.appnativa.rare.ui", NULL, 0x401, 23, methods, 11, fields, 0, NULL};
  return &_RAREaUIMenuItem;
}

@end
