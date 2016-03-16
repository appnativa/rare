//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/MenuUtils.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/net/ActionLink.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/spot/Link.h"
#include "com/appnativa/rare/spot/MenuItem.h"
#include "com/appnativa/rare/ui/MenuUtils.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIAction.h"
#include "com/appnativa/rare/ui/UIImageIcon.h"
#include "com/appnativa/rare/ui/UIMenu.h"
#include "com/appnativa/rare/ui/UIMenuItem.h"
#include "com/appnativa/rare/ui/UIPopupMenu.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/aFocusedAction.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSet.h"

@implementation RAREMenuUtils

static IOSObjectArray * RAREMenuUtils_EMPTY_MENU_ARRAY_;

+ (IOSObjectArray *)EMPTY_MENU_ARRAY {
  return RAREMenuUtils_EMPTY_MENU_ARRAY_;
}

- (id)init {
  return [super init];
}

+ (void)addTextActionsWithRAREUIMenu:(RAREUIMenu *)menu
                         withBoolean:(BOOL)find
                         withBoolean:(BOOL)replace {
  RAREUIMenuItem *item = [[RAREUIMenuItem alloc] initWithRAREUIAction:[RAREaPlatformHelper getUndoAction]];
  (void) [((RAREUIMenu *) nil_chk(menu)) registerItemWithNSString:[RAREiConstants UNDO_ACTION_NAME] withId:item];
  [menu addWithId:item];
  item = [[RAREUIMenuItem alloc] initWithRAREUIAction:[RAREaPlatformHelper getRedoAction]];
  (void) [menu registerItemWithNSString:[RAREiConstants UNDO_ACTION_NAME] withId:item];
  [menu addWithId:item];
  [menu addSeparator];
  item = [[RAREUIMenuItem alloc] initWithRAREUIAction:[RAREaPlatformHelper getCutAction]];
  (void) [menu registerItemWithNSString:[RAREiConstants CUT_ACTION_NAME] withId:item];
  [menu addWithId:item];
  item = [[RAREUIMenuItem alloc] initWithRAREUIAction:[RAREaPlatformHelper getCopyAction]];
  (void) [menu registerItemWithNSString:[RAREiConstants COPY_ACTION_NAME] withId:item];
  [menu addWithId:item];
  item = [[RAREUIMenuItem alloc] initWithRAREUIAction:[RAREaPlatformHelper getPasteAction]];
  (void) [menu registerItemWithNSString:[RAREiConstants PASTE_ACTION_NAME] withId:item];
  [menu addWithId:item];
  item = [[RAREUIMenuItem alloc] initWithRAREUIAction:[RAREaPlatformHelper getDeleteAction]];
  (void) [menu registerItemWithNSString:[RAREiConstants DELETE_ACTION_NAME] withId:item];
  [menu addWithId:item];
  [menu addSeparator];
  item = [[RAREUIMenuItem alloc] initWithRAREUIAction:[RAREaPlatformHelper getSelectAllAction]];
  (void) [menu registerItemWithNSString:[RAREiConstants SELECTALL_ACTION_NAME] withId:item];
  [menu addWithId:item];
}

+ (RAREUIMenu *)createMenuWithNSString:(NSString *)text
                 withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                withId:(id)data {
  RAREUIMenu *m = [[RAREUIMenu alloc] initWithNSString:@"" withRAREiPlatformIcon:icon withId:data];
  (void) [RAREUtils setMnemonicAndTextWithRAREaUIMenuItem:m withNSString:text];
  return m;
}

+ (RAREUIMenuItem *)createMenuItemWithRAREiWidget:(id<RAREiWidget>)context
                             withRARESPOTMenuItem:(RARESPOTMenuItem *)item {
  return [RAREMenuUtils createMenuItemWithRAREiWidget:context withRARESPOTMenuItem:item withBoolean:NO];
}

+ (IOSObjectArray *)createMenuItemsWithRAREiWidget:(id<RAREiWidget>)context
                                       withSPOTSet:(SPOTSet *)set {
  if (set == nil) {
    return RAREMenuUtils_EMPTY_MENU_ARRAY_;
  }
  int len = [((SPOTSet *) nil_chk(set)) getCount];
  IOSObjectArray *a = [IOSObjectArray arrayWithLength:len type:[IOSClass classWithClass:[RAREUIMenuItem class]]];
  for (int i = 0; i < len; i++) {
    (void) IOSObjectArray_Set(a, i, [RAREMenuUtils createMenuItemWithRAREiWidget:context withRARESPOTMenuItem:(RARESPOTMenuItem *) check_class_cast([set getWithInt:i], [RARESPOTMenuItem class])]);
    [((RAREUIMenuItem *) IOSObjectArray_Get(a, i)) setContextWidgetWithRAREiWidget:context];
  }
  return a;
}

+ (IOSObjectArray *)createMenusWithRAREiWidget:(id<RAREiWidget>)context
                                   withSPOTSet:(SPOTSet *)set {
  if (set == nil) {
    return RAREMenuUtils_EMPTY_MENU_ARRAY_;
  }
  int len = [((SPOTSet *) nil_chk(set)) getCount];
  IOSObjectArray *a = [IOSObjectArray arrayWithLength:len type:[IOSClass classWithClass:[RAREUIMenuItem class]]];
  for (int i = 0; i < len; i++) {
    (void) IOSObjectArray_Set(a, i, [RAREMenuUtils createMenuItemWithRAREiWidget:context withRARESPOTMenuItem:(RARESPOTMenuItem *) check_class_cast([set getWithInt:i], [RARESPOTMenuItem class]) withBoolean:YES]);
    [((RAREUIMenuItem *) IOSObjectArray_Get(a, i)) setContextWidgetWithRAREiWidget:context];
  }
  return a;
}

+ (RAREUIMenuItem *)createNamedMenuWithRAREiWidget:(id<RAREiWidget>)context
                                      withNSString:(NSString *)name
                                       withBoolean:(BOOL)hasSubs
                                       withBoolean:(BOOL)topLevelMenu {
  RAREUIMenuItem *item;
  RAREUIMenu *m;
  RAREUIAction *a = [((id<RAREiPlatformAppContext>) nil_chk([((id<RAREiWidget>) nil_chk(context)) getAppContext])) getActionWithNSString:name];
  if (a != nil) {
    item = [RAREaPlatformHelper createMenuItemWithRAREUIAction:a withBoolean:topLevelMenu];
    [((RAREUIMenuItem *) nil_chk(item)) setNameWithNSString:name];
    return item;
  }
  if ((name == nil) || ([name sequenceLength] == 0)) {
    return nil;
  }
  if ([((NSString *) nil_chk(name)) isEqual:[RAREiConstants UNDO_ACTION_NAME]]) {
    item = [[RAREUIMenuItem alloc] initWithRAREUIAction:[RAREaPlatformHelper getUndoAction]];
    [item setNameWithNSString:name];
    return item;
  }
  if ([name isEqual:[RAREiConstants REDO_ACTION_NAME]]) {
    item = [[RAREUIMenuItem alloc] initWithRAREUIAction:[RAREaPlatformHelper getRedoAction]];
    [item setNameWithNSString:name];
    return item;
  }
  if ([name isEqual:[RAREiConstants CUT_ACTION_NAME]]) {
    item = [[RAREUIMenuItem alloc] initWithRAREUIAction:[RAREaPlatformHelper getCutAction]];
    [item setNameWithNSString:name];
    return item;
  }
  if ([name isEqual:[RAREiConstants COPY_ACTION_NAME]]) {
    item = [[RAREUIMenuItem alloc] initWithRAREUIAction:[RAREaPlatformHelper getCopyAction]];
    [item setNameWithNSString:name];
    return item;
  }
  if ([name isEqual:[RAREiConstants PASTE_ACTION_NAME]]) {
    item = [[RAREUIMenuItem alloc] initWithRAREUIAction:[RAREaPlatformHelper getPasteAction]];
    [item setNameWithNSString:name];
    return item;
  }
  if ([name isEqual:[RAREiConstants SELECTALL_ACTION_NAME]]) {
    item = [[RAREUIMenuItem alloc] initWithRAREUIAction:[RAREaPlatformHelper getSelectAllAction]];
    [item setNameWithNSString:name];
    return item;
  }
  if ([name isEqual:[RAREiConstants MENU_SEPARATOR_NAME]]) {
    return [RAREaPlatformHelper getSeparatorMenuItem];
  }
  if ([name isEqual:[RAREiConstants EXIT_ACTION_NAME]]) {
    item = [RAREaUIMenuItem createMenuItemWithJavaLangCharSequence:@"E_xit"];
    [((RAREUIMenuItem *) nil_chk(item)) setNameWithNSString:name];
    id<RAREiPlatformAppContext> app = [context getAppContext];
    [item setActionListenerWithRAREiActionListener:[[RAREMenuUtils_$1 alloc] initWithRAREiPlatformAppContext:app]];
    [item setIconWithRAREiPlatformIcon:[((id<RAREiPlatformAppContext>) nil_chk([context getAppContext])) getResourceAsIconWithNSString:@"Rare.icon.empty"]];
    return item;
  }
  if ([name isEqual:[RAREiConstants EDIT_MENU_NAME]]) {
    m = [RAREMenuUtils createMenuWithNSString:@"_Edit" withRAREiPlatformIcon:nil withId:nil];
    [((RAREUIMenu *) nil_chk(m)) setNameWithNSString:name];
    if (!hasSubs) {
      [RAREMenuUtils addTextActionsWithRAREUIMenu:m withBoolean:NO withBoolean:NO];
    }
    return m;
  }
  if ([name isEqual:[RAREiConstants FILE_MENU_NAME]]) {
    m = [RAREMenuUtils createMenuWithNSString:@"_File" withRAREiPlatformIcon:nil withId:nil];
    [((RAREUIMenu *) nil_chk(m)) setNameWithNSString:name];
    if (!hasSubs) {
      [RAREMenuUtils addFileActionsWithRAREiWidget:context withRAREUIMenu:m];
    }
    return m;
  }
  if ([name isEqual:[RAREiConstants TOOLS_MENU_NAME]]) {
    m = [RAREMenuUtils createMenuWithNSString:@"_Tools" withRAREiPlatformIcon:nil withId:nil];
    [((RAREUIMenu *) nil_chk(m)) setNameWithNSString:name];
    return m;
  }
  if ([name isEqual:[RAREiConstants HELP_MENU_NAME]]) {
    m = [RAREMenuUtils createMenuWithNSString:@"_Help" withRAREiPlatformIcon:nil withId:nil];
    [((RAREUIMenu *) nil_chk(m)) setNameWithNSString:name];
    return m;
  }
  return nil;
}

+ (RAREUIPopupMenu *)createPopupMenuWithRAREUIPopupMenu:(RAREUIPopupMenu *)menu
                                        withRAREiWidget:(id<RAREiWidget>)context
                                            withSPOTSet:(SPOTSet *)menus
                                            withBoolean:(BOOL)addDefaults {
  if (menu == nil) {
    menu = [[RAREUIPopupMenu alloc] init];
    [menu setContextWidgetWithRAREiWidget:context];
  }
  if (menus != nil) {
    NSString *s = [menus spot_getAttributeWithNSString:[RAREiConstants ATTRIBUTE_ON_ACTION]];
    if ((s != nil) && ([s sequenceLength] > 0)) {
      [((RAREUIPopupMenu *) nil_chk(menu)) setActionScriptWithId:[RAREaWidgetListener processEventStringWithNSString:s]];
    }
  }
  IOSObjectArray *menuItems = [RAREMenuUtils createMenuItemsWithRAREiWidget:context withSPOTSet:menus];
  RAREUIMenuItem *mi = nil;
  NSString *name;
  for (int i = 0; i < (int) [((IOSObjectArray *) nil_chk(menuItems)) count]; i++) {
    mi = IOSObjectArray_Get(menuItems, i);
    if ((mi == nil) || ([mi isSeparator])) {
      [((RAREUIPopupMenu *) nil_chk(menu)) addSeparator];
    }
    else {
      [((RAREUIPopupMenu *) nil_chk(menu)) addWithId:mi];
      name = [mi getName];
      if (name != nil) {
        (void) [menu registerItemWithNSString:name withId:mi];
      }
    }
  }
  if (addDefaults) {
    if (mi != nil) {
      [((RAREUIPopupMenu *) nil_chk(menu)) addSeparator];
    }
    [((RAREUIPopupMenu *) nil_chk(menu)) addWithId:[[RAREUIMenuItem alloc] initWithRAREUIAction:[RAREaPlatformHelper getCutAction]]];
    [menu addWithId:[[RAREUIMenuItem alloc] initWithRAREUIAction:[RAREaPlatformHelper getCopyAction]]];
    [menu addWithId:[[RAREUIMenuItem alloc] initWithRAREUIAction:[RAREaPlatformHelper getPasteAction]]];
    if ([((id<RAREiWidget>) nil_chk(context)) canSelectAll]) {
      [menu addSeparator];
      [menu addWithId:[[RAREUIMenuItem alloc] initWithRAREUIAction:[RAREaPlatformHelper getSelectAllAction]]];
    }
  }
  return menu;
}

+ (RAREUIMenuItem *)getSeparatorItem {
  return [RAREaPlatformHelper getSeparatorMenuItem];
}

+ (void)addFileActionsWithRAREiWidget:(id<RAREiWidget>)context
                       withRAREUIMenu:(RAREUIMenu *)menu {
  RAREUIMenuItem *item = [RAREMenuUtils createNamedMenuWithRAREiWidget:context withNSString:[RAREiConstants EXIT_ACTION_NAME] withBoolean:NO withBoolean:NO];
  [((RAREUIMenu *) nil_chk(menu)) addSeparator];
  [menu addWithId:item];
}

+ (RAREUIMenuItem *)createMenuItemWithRAREiWidget:(id<RAREiWidget>)context
                             withRARESPOTMenuItem:(RARESPOTMenuItem *)item
                                      withBoolean:(BOOL)forceTopLevel {
  NSString *text = [((id<RAREiWidget>) nil_chk(context)) expandStringWithNSString:[((SPOTPrintableString *) nil_chk(((RARESPOTMenuItem *) nil_chk(item))->value_)) getValue] withBoolean:NO];
  NSString *name = [((SPOTPrintableString *) nil_chk(item->name_)) getValue];
  id<RAREiPlatformIcon> icon = [context getIconWithSPOTPrintableString:item->icon_];
  id<RAREiPlatformIcon> dicon = [context getIconWithSPOTPrintableString:item->disabledIcon_];
  id data = [((SPOTPrintableString *) nil_chk(item->linkedData_)) getValue];
  RAREUIMenuItem *mi = nil;
  SPOTSet *subs = [item getSubMenu];
  NSString *s;
  if (name != nil) {
    if ([name isEqual:[RAREiConstants MENU_SEPARATOR_NAME]]) {
      mi = [RAREaPlatformHelper getSeparatorMenuItem];
    }
    else {
      mi = [RAREMenuUtils createNamedMenuWithRAREiWidget:context withNSString:name withBoolean:(subs != nil) && ([subs size] > 0) withBoolean:forceTopLevel];
      if (mi != nil) {
        if (data != nil) {
          [mi setLinkedDataWithId:data];
        }
        if ((text != nil) && ([text sequenceLength] > 0)) {
          (void) [RAREUtils setMnemonicAndTextWithRAREaUIMenuItem:mi withNSString:text];
        }
      }
    }
  }
  if ((mi == nil) && (name == nil) && ((text == nil) || ([text sequenceLength] == 0))) {
    mi = [RAREaPlatformHelper getSeparatorMenuItem];
  }
  do {
    if (subs != nil) {
      if (mi == nil) {
        mi = [RAREMenuUtils createMenuWithNSString:text withRAREiPlatformIcon:icon withId:data];
      }
      s = [item spot_getAttributeWithNSString:[RAREiConstants ATTRIBUTE_ON_ACTION]];
      if ((s != nil) && ([s sequenceLength] > 0)) {
        [((RAREUIMenuItem *) nil_chk(mi)) setActionScriptWithId:s];
      }
      IOSObjectArray *a = [RAREMenuUtils createMenuItemsWithRAREiWidget:context withSPOTSet:subs];
      [((RAREUIMenuItem *) nil_chk(mi)) setItemsWithRARERenderableDataItemArray:a withInt:(int) [((IOSObjectArray *) nil_chk(a)) count]];
      if ([((SPOTBoolean *) nil_chk(item->enabledOnSelectionOnly_)) spot_valueWasSet]) {
        [mi setEnabledOnSelectionWithBoolean:[item->enabledOnSelectionOnly_ booleanValue]];
      }
      break;
    }
    if (!([mi isKindOfClass:[RAREUIMenu class]])) {
      if (mi == nil) {
        if ([((SPOTBoolean *) nil_chk(item->checkbox_)) booleanValue]) {
          mi = [RAREaUIMenuItem createCheckboxMenuItemWithJavaLangCharSequence:text withRAREiPlatformIcon:icon withId:data];
          [((RAREUIMenuItem *) nil_chk(mi)) setSelectedWithBoolean:[((SPOTBoolean *) nil_chk(item->selected_)) booleanValue]];
        }
        else if (forceTopLevel) {
          mi = [RAREMenuUtils createMenuWithNSString:text withRAREiPlatformIcon:icon withId:data];
        }
        else {
          mi = [RAREaUIMenuItem createMenuItemWithJavaLangCharSequence:text withRAREiPlatformIcon:icon withId:data];
        }
      }
      RARESPOTLink *link = [item getActionLink];
      if (link != nil) {
        [((RAREUIMenuItem *) nil_chk(mi)) setActionListenerWithRAREiActionListener:[[RAREActionLink alloc] initWithRAREUTiURLResolver:context withRARESPOTLink:link]];
      }
      else {
        s = [item spot_getAttributeWithNSString:[RAREiConstants ATTRIBUTE_ON_ACTION]];
        if ((s != nil) && ([s sequenceLength] > 0)) {
          [((RAREUIMenuItem *) nil_chk(mi)) setActionScriptWithId:[RAREaWidgetListener processEventStringWithNSString:s]];
        }
      }
      if ([((SPOTBoolean *) nil_chk(item->enabledOnSelectionOnly_)) spot_valueWasSet]) {
        [((RAREUIMenuItem *) nil_chk(mi)) setEnabledOnSelectionWithBoolean:[item->enabledOnSelectionOnly_ booleanValue]];
      }
    }
  }
  while (NO);
  if (dicon != nil) {
    [((RAREUIMenuItem *) nil_chk(mi)) setDisabledIconWithRAREiPlatformIcon:dicon];
  }
  if ([((SPOTBoolean *) nil_chk(item->enabled_)) spot_valueWasSet]) {
    [((RAREUIMenuItem *) nil_chk(mi)) setEnabledWithBoolean:[item->enabled_ booleanValue]];
  }
  s = [((SPOTPrintableString *) nil_chk(item->shortcutKeystroke_)) getValue];
  if ((s != nil) && ([s sequenceLength] > 0)) {
    [RAREPlatformHelper setShortcutWithRAREUIMenuItem:mi withNSString:s];
  }
  if ((name != nil) && ([name sequenceLength] > 0) && ([((RAREUIMenuItem *) nil_chk(mi)) getName] == nil)) {
    [mi setNameWithNSString:name];
  }
  if ([context isDesignMode]) {
    [((RAREUIMenuItem *) nil_chk(mi)) setLinkedDataWithId:item];
  }
  return mi;
}

+ (RARERenderableDataItem *)createDataItemWithRAREiWidget:(id<RAREiWidget>)context
                                     withRARESPOTMenuItem:(RARESPOTMenuItem *)item {
  NSString *text = [((id<RAREiWidget>) nil_chk(context)) expandStringWithNSString:[((SPOTPrintableString *) nil_chk(((RARESPOTMenuItem *) nil_chk(item))->value_)) getValue] withBoolean:NO];
  id<RAREiPlatformIcon> icon = [context getIconWithSPOTPrintableString:item->icon_];
  id<RAREiPlatformIcon> dicon = [context getIconWithSPOTPrintableString:item->disabledIcon_];
  id data = [((SPOTPrintableString *) nil_chk(item->linkedData_)) getValue];
  RARERenderableDataItem *mi = [[RARERenderableDataItem alloc] initWithId:text withInt:RARERenderableDataItem_TYPE_STRING withId:data withRAREiPlatformIcon:icon];
  NSString *name = [((SPOTPrintableString *) nil_chk(item->name_)) getValue];
  [mi setLinkedDataWithId:name];
  if (dicon != nil) {
    [mi setDisabledIconWithRAREiPlatformIcon:dicon];
  }
  if ([((SPOTBoolean *) nil_chk(item->enabled_)) spot_valueWasSet]) {
    [mi setEnabledWithBoolean:[item->enabled_ booleanValue]];
  }
  NSString *s = [item spot_getAttributeWithNSString:[RAREiConstants ATTRIBUTE_ON_ACTION]];
  if ((s != nil) && ([s sequenceLength] > 0)) {
    [mi setActionCodeWithRAREiWidget:context withNSString:s];
  }
  else {
    if (name != nil) {
      RAREUIAction *a = [((id<RAREiPlatformAppContext>) nil_chk([context getAppContext])) getActionWithNSString:name];
      if (a != nil) {
        [mi setActionListenerWithRAREiActionListener:a];
      }
    }
  }
  return mi;
}

+ (void)initialize {
  if (self == [RAREMenuUtils class]) {
    RAREMenuUtils_EMPTY_MENU_ARRAY_ = [IOSObjectArray arrayWithLength:0 type:[IOSClass classWithClass:[RAREUIMenuItem class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x2, NULL },
    { "createMenuWithNSString:withRAREiPlatformIcon:withId:", NULL, "LRAREUIMenu", 0x9, NULL },
    { "createMenuItemWithRAREiWidget:withRARESPOTMenuItem:", NULL, "LRAREUIMenuItem", 0x9, NULL },
    { "createMenuItemsWithRAREiWidget:withSPOTSet:", NULL, "LIOSObjectArray", 0x9, NULL },
    { "createMenusWithRAREiWidget:withSPOTSet:", NULL, "LIOSObjectArray", 0x9, NULL },
    { "createNamedMenuWithRAREiWidget:withNSString:withBoolean:withBoolean:", NULL, "LRAREUIMenuItem", 0x9, NULL },
    { "createPopupMenuWithRAREUIPopupMenu:withRAREiWidget:withSPOTSet:withBoolean:", NULL, "LRAREUIPopupMenu", 0x9, NULL },
    { "getSeparatorItem", NULL, "LRAREUIMenuItem", 0x9, NULL },
    { "addFileActionsWithRAREiWidget:withRAREUIMenu:", NULL, "V", 0xa, NULL },
    { "createMenuItemWithRAREiWidget:withRARESPOTMenuItem:withBoolean:", NULL, "LRAREUIMenuItem", 0xa, NULL },
    { "createDataItemWithRAREiWidget:withRARESPOTMenuItem:", NULL, "LRARERenderableDataItem", 0x9, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "EMPTY_MENU_ARRAY_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RAREMenuUtils = { "MenuUtils", "com.appnativa.rare.ui", NULL, 0x1, 11, methods, 1, fields, 0, NULL};
  return &_RAREMenuUtils;
}

@end
@implementation RAREMenuUtils_$1

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e {
  if (val$app_ != nil) {
    [val$app_ exit];
  }
}

- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)capture$0 {
  val$app_ = capture$0;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "val$app_", NULL, 0x1012, "LRAREiPlatformAppContext" },
  };
  static J2ObjcClassInfo _RAREMenuUtils_$1 = { "$1", "com.appnativa.rare.ui", "MenuUtils", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREMenuUtils_$1;
}

@end
