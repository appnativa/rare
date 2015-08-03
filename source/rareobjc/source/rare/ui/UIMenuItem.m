//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/UIMenuItem.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/scripting/WidgetContext.h"
#include "com/appnativa/rare/scripting/iScriptHandler.h"
#include "com/appnativa/rare/ui/MenuItem.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIAction.h"
#include "com/appnativa/rare/ui/UIMenuItem.h"
#include "com/appnativa/rare/ui/aUIMenuItem.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/lang/CharSequence.h"

@implementation RAREUIMenuItem

- (id)init {
  return [super init];
}

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text {
  return [self initRAREUIMenuItemWithJavaLangCharSequence:text withRAREiPlatformIcon:nil withId:nil withBoolean:NO];
}

- (id)initWithRAREMenuItem:(RAREMenuItem *)item {
  if (self = [super init]) {
    menuItem_ = item;
    [self setupItem];
    originalValue_ = [((RAREMenuItem *) nil_chk(item)) getText];
    theType_ = RARERenderableDataItem_TYPE_STRING;
  }
  return self;
}

- (id)initWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  if (self = [super initWithRARERenderableDataItem:item]) {
    menuItem_ = [[RAREMenuItem alloc] initWithBoolean:NO];
    [self setupItem];
  }
  return self;
}

- (id)initWithRAREUIAction:(RAREUIAction *)a {
  if (self = [super initWithRAREUIAction:a]) {
    menuItem_ = [[RAREMenuItem alloc] initWithBoolean:NO];
    [self setupItem];
  }
  return self;
}

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
             withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  return [self initRAREUIMenuItemWithJavaLangCharSequence:text withRAREiPlatformIcon:icon withId:nil withBoolean:NO];
}

- (id)initWithRAREUIAction:(RAREUIAction *)a
               withBoolean:(BOOL)checkbox {
  return [self initRAREUIMenuItemWithRAREUIAction:a withRAREMenuItem:[[RAREMenuItem alloc] initWithBoolean:checkbox]];
}

- (id)initRAREUIMenuItemWithRAREUIAction:(RAREUIAction *)a
                        withRAREMenuItem:(RAREMenuItem *)item {
  if (self = [super init]) {
    menuItem_ = item;
    [self setupItem];
    [self setActionWithRAREUIAction:a];
  }
  return self;
}

- (id)initWithRAREUIAction:(RAREUIAction *)a
          withRAREMenuItem:(RAREMenuItem *)item {
  return [self initRAREUIMenuItemWithRAREUIAction:a withRAREMenuItem:item];
}

- (id)initRAREUIMenuItemWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
                           withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                          withId:(id)data
                                     withBoolean:(BOOL)checkbox {
  if (self = [super initWithJavaLangCharSequence:nil withRAREiPlatformIcon:icon withId:data]) {
    menuItem_ = [[RAREMenuItem alloc] initWithBoolean:checkbox];
    [self setupItem];
    [self setValueWithId:text];
  }
  return self;
}

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
             withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                            withId:(id)data
                       withBoolean:(BOOL)checkbox {
  return [self initRAREUIMenuItemWithJavaLangCharSequence:text withRAREiPlatformIcon:icon withId:data withBoolean:checkbox];
}

- (void)dispose {
  [super dispose];
  if (menuItem_ != nil) {
    [menuItem_ dispose];
  }
  self->menuItem_ = nil;
}

- (void)handleWithRAREActionEvent:(RAREActionEvent *)e {
  if (menuItem_ == nil) {
    return;
  }
  id<RAREiWidget> w = [RAREPlatform findWidgetForComponentWithId:menuItem_];
  if (w == nil) {
    w = [self getContextWidget];
  }
  if (w == nil) {
    w = [RAREPlatform getContextRootViewer];
  }
  if (w == nil) {
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
    RAREActionEvent *ae = [[RAREActionEvent alloc] initWithId:self withId:e];
    [RAREaWidgetListener executeWithRAREiWidget:w withRAREiScriptHandler:scriptHandler withId:o withNSString:[RAREiConstants EVENT_ACTION] withJavaUtilEventObject:ae];
  }
}

- (void)updateText {
  if (hasVars_ && (menuItem_ != nil) && (originalValue_ != nil)) {
    id<RAREiWidget> w = (contextWidget_ == nil) ? ((id) [RAREPlatform getContextRootViewer]) : ((id) contextWidget_);
    [menuItem_ setTextWithJavaLangCharSequence:[w expandStringWithNSString:(NSString *) check_class_cast(originalValue_, [NSString class]) withBoolean:NO]];
  }
}

- (void)setCheckableWithBoolean:(BOOL)checkable {
  [super setCheckableWithBoolean:checkable];
  if (menuItem_ != nil) {
    [menuItem_ setCheckableWithBoolean:checkable];
  }
}

- (void)setMnemonicWithChar:(unichar)mn {
}

- (void)setSelectedWithBoolean:(BOOL)selected {
  [super setSelectedWithBoolean:selected];
  self->selected_ = selected;
  [((RAREMenuItem *) nil_chk(menuItem_)) setSelectedWithBoolean:checkable_];
}

- (void)setTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text {
  [super setTextWithJavaLangCharSequence:text];
  if (menuItem_ != nil) {
    [menuItem_ setTextWithJavaLangCharSequence:text];
  }
}

- (void)setVisibleWithBoolean:(BOOL)b {
  [super setVisibleWithBoolean:b];
  if (menuItem_ != nil) {
    [menuItem_ setVisibleWithBoolean:b];
  }
}

- (RAREMenuItem *)getMenuItem {
  return menuItem_;
}

- (id)getProxy {
  return (menuItem_ == nil) ? nil : [menuItem_ getProxy];
}

- (BOOL)isSeparator {
  return [((RAREMenuItem *) nil_chk(menuItem_)) isSeparator];
}

- (void)setMenuItemWithRAREMenuItem:(RAREMenuItem *)item {
  menuItem_ = item;
}

- (void)setupItem {
  [((RAREMenuItem *) nil_chk(menuItem_)) setUserDataWithId:self];
}

- (void)setNativeItemIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  if (menuItem_ != nil) {
    [menuItem_ setIconWithRAREiPlatformIcon:icon];
  }
}

- (void)copyAllFieldsTo:(RAREUIMenuItem *)other {
  [super copyAllFieldsTo:other];
  other->menuItem_ = menuItem_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getMenuItem", NULL, "LRAREMenuItem", 0x1, NULL },
    { "getProxy", NULL, "LNSObject", 0x1, NULL },
    { "isSeparator", NULL, "Z", 0x1, NULL },
    { "setMenuItemWithRAREMenuItem:", NULL, "V", 0x0, NULL },
    { "setupItem", NULL, "V", 0x4, NULL },
    { "setNativeItemIconWithRAREiPlatformIcon:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "menuItem_", NULL, 0x4, "LRAREMenuItem" },
  };
  static J2ObjcClassInfo _RAREUIMenuItem = { "UIMenuItem", "com.appnativa.rare.ui", NULL, 0x1, 6, methods, 1, fields, 0, NULL};
  return &_RAREUIMenuItem;
}

@end