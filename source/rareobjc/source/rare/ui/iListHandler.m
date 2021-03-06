//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iListHandler.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIStroke.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/event/iItemChangeListener.h"
#include "com/appnativa/rare/ui/iListHandler.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformItemRenderer.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/util/List.h"


@interface RAREiListHandler : NSObject
@end

@implementation RAREiListHandler

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addAllWithInt:withJavaUtilList:withBoolean:", NULL, "V", 0x401, NULL },
    { "copySelectedItemsWithInt:withBoolean:withBoolean:", NULL, "V", 0x401, NULL },
    { "deleteSelectedDataWithBoolean:", NULL, "LNSObject", 0x401, NULL },
    { "removeRowsWithIntArray:", NULL, "V", 0x401, NULL },
    { "setListSelectionTypeWithRAREiListHandler_SelectionTypeEnum:", NULL, "V", 0x401, NULL },
    { "getItemCount", NULL, "I", 0x401, NULL },
    { "getListSelectionType", NULL, "LRAREiListHandler_SelectionTypeEnum", 0x401, NULL },
    { "getRowBoundsWithInt:withInt:", NULL, "LRAREUIRectangle", 0x401, NULL },
    { "getRowIndexAtWithFloat:withFloat:", NULL, "I", 0x401, NULL },
    { "getSelectedIndexCount", NULL, "I", 0x401, NULL },
    { "addActionListenerWithRAREiActionListener:", NULL, "V", 0x401, NULL },
    { "addSelectionChangeListenerWithRAREiItemChangeListener:", NULL, "V", 0x401, NULL },
    { "addSelectionIndexWithInt:", NULL, "V", 0x401, NULL },
    { "clearContextMenuIndex", NULL, "V", 0x401, NULL },
    { "clearSelection", NULL, "V", 0x401, NULL },
    { "dispose", NULL, "V", 0x401, NULL },
    { "fireActionForSelected", NULL, "V", 0x401, NULL },
    { "refreshItems", NULL, "V", 0x401, NULL },
    { "removeActionListenerWithRAREiActionListener:", NULL, "V", 0x401, NULL },
    { "removeSelectionChangeListenerWithRAREiItemChangeListener:", NULL, "V", 0x401, NULL },
    { "removeSelectionWithInt:", NULL, "V", 0x401, NULL },
    { "scrollRowToVisibleWithInt:", NULL, "V", 0x401, NULL },
    { "makeSelectionVisible", NULL, "V", 0x401, NULL },
    { "isKeepSelectionVisible", NULL, "Z", 0x401, NULL },
    { "setKeepSelectionVisibleWithBoolean:", NULL, "V", 0x401, NULL },
    { "selectAll", NULL, "V", 0x401, NULL },
    { "sizeRowsToFit", NULL, "V", 0x401, NULL },
    { "setAlternatingRowColorWithRAREUIColor:", NULL, "V", 0x401, NULL },
    { "setAutoHilightWithBoolean:", NULL, "V", 0x401, NULL },
    { "setChangeEventsEnabledWithBoolean:", NULL, "V", 0x401, NULL },
    { "setDataEventsEnabledWithBoolean:", NULL, "V", 0x401, NULL },
    { "setDeselectEventsDisabledWithBoolean:", NULL, "V", 0x401, NULL },
    { "setChangeSelColorOnLostFocusWithBoolean:", NULL, "V", 0x401, NULL },
    { "setHandleFirstFocusSelectionWithBoolean:", NULL, "V", 0x401, NULL },
    { "setListSelectableWithBoolean:", NULL, "V", 0x401, NULL },
    { "setMinRowHeightWithInt:", NULL, "V", 0x401, NULL },
    { "setRowHeightWithInt:", NULL, "V", 0x401, NULL },
    { "setSelectedIndexWithInt:", NULL, "V", 0x401, NULL },
    { "setSelectedIndexesWithIntArray:", NULL, "V", 0x401, NULL },
    { "setSelectedItemWithRARERenderableDataItem:", NULL, "V", 0x401, NULL },
    { "setShowDividerWithBoolean:", NULL, "V", 0x401, NULL },
    { "setSingleClickActionWithBoolean:", NULL, "V", 0x401, NULL },
    { "setMinimumVisibleRowCountWithInt:", NULL, "V", 0x401, NULL },
    { "getMinimumVisibleRowCount", NULL, "I", 0x401, NULL },
    { "setVisibleRowCountWithInt:", NULL, "V", 0x401, NULL },
    { "getAlternatingRowColor", NULL, "LRAREUIColor", 0x401, NULL },
    { "getAnchorSelectionIndex", NULL, "I", 0x401, NULL },
    { "getContainerComponent", NULL, "LRAREiPlatformComponent", 0x401, NULL },
    { "getItemRenderer", NULL, "LRAREiPlatformItemRenderer", 0x401, NULL },
    { "getListComponent", NULL, "LRAREiPlatformComponent", 0x401, NULL },
    { "getMaxSelectionIndex", NULL, "I", 0x401, NULL },
    { "getMinSelectionIndex", NULL, "I", 0x401, NULL },
    { "getContextMenuIndex", NULL, "I", 0x401, NULL },
    { "getContextMenuItem", NULL, "LRARERenderableDataItem", 0x401, NULL },
    { "getPreferredHeightWithInt:", NULL, "I", 0x401, NULL },
    { "getRowHeight", NULL, "I", 0x401, NULL },
    { "getRows", NULL, "LJavaUtilList", 0x401, NULL },
    { "getSelectedIndex", NULL, "I", 0x401, NULL },
    { "getSelectedIndexes", NULL, "LIOSIntArray", 0x401, NULL },
    { "getCheckedIndexes", NULL, "LIOSIntArray", 0x401, NULL },
    { "getSelectedItem", NULL, "LRARERenderableDataItem", 0x401, NULL },
    { "getSelection", NULL, "LNSObject", 0x401, NULL },
    { "getSelectionAsString", NULL, "LNSString", 0x401, NULL },
    { "getSelections", NULL, "LIOSObjectArray", 0x401, NULL },
    { "getSelectionsAsStrings", NULL, "LIOSObjectArray", 0x401, NULL },
    { "getVisibleRowCount", NULL, "I", 0x401, NULL },
    { "hasSelection", NULL, "Z", 0x401, NULL },
    { "isChangeEventsEnabled", NULL, "Z", 0x401, NULL },
    { "isChangeSelColorOnLostFocus", NULL, "Z", 0x401, NULL },
    { "isDataEventsEnabled", NULL, "Z", 0x401, NULL },
    { "isHandleFirstFocusSelection", NULL, "Z", 0x401, NULL },
    { "isListSelectable", NULL, "Z", 0x401, NULL },
    { "isRowSelectedWithInt:", NULL, "Z", 0x401, NULL },
    { "isRowSelectedWithRARERenderableDataItem:", NULL, "Z", 0x401, NULL },
    { "isTabular", NULL, "Z", 0x401, NULL },
    { "setDividerLineWithRAREUIColor:withRAREUIStroke:", NULL, "V", 0x401, NULL },
    { "getLastVisibleIndex", NULL, "I", 0x401, NULL },
    { "getFirstVisibleIndex", NULL, "I", 0x401, NULL },
    { "scrollRowToTopWithInt:", NULL, "V", 0x401, NULL },
    { "scrollRowToBottomWithInt:", NULL, "V", 0x401, NULL },
    { "setSelectionModeWithRAREiListHandler_SelectionModeEnum:", NULL, "V", 0x401, NULL },
    { "repaintRowWithInt:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiListHandler = { "iListHandler", "com.appnativa.rare.ui", NULL, 0x201, 82, methods, 0, NULL, 0, NULL};
  return &_RAREiListHandler;
}

@end

static RAREiListHandler_SelectionModeEnum *RAREiListHandler_SelectionModeEnum_SINGLE;
static RAREiListHandler_SelectionModeEnum *RAREiListHandler_SelectionModeEnum_MULTIPLE;
static RAREiListHandler_SelectionModeEnum *RAREiListHandler_SelectionModeEnum_BLOCK;
static RAREiListHandler_SelectionModeEnum *RAREiListHandler_SelectionModeEnum_NONE;
static RAREiListHandler_SelectionModeEnum *RAREiListHandler_SelectionModeEnum_INVISIBLE;
IOSObjectArray *RAREiListHandler_SelectionModeEnum_values;

@implementation RAREiListHandler_SelectionModeEnum

+ (RAREiListHandler_SelectionModeEnum *)SINGLE {
  return RAREiListHandler_SelectionModeEnum_SINGLE;
}
+ (RAREiListHandler_SelectionModeEnum *)MULTIPLE {
  return RAREiListHandler_SelectionModeEnum_MULTIPLE;
}
+ (RAREiListHandler_SelectionModeEnum *)BLOCK {
  return RAREiListHandler_SelectionModeEnum_BLOCK;
}
+ (RAREiListHandler_SelectionModeEnum *)NONE {
  return RAREiListHandler_SelectionModeEnum_NONE;
}
+ (RAREiListHandler_SelectionModeEnum *)INVISIBLE {
  return RAREiListHandler_SelectionModeEnum_INVISIBLE;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREiListHandler_SelectionModeEnum class]) {
    RAREiListHandler_SelectionModeEnum_SINGLE = [[RAREiListHandler_SelectionModeEnum alloc] initWithNSString:@"SINGLE" withInt:0];
    RAREiListHandler_SelectionModeEnum_MULTIPLE = [[RAREiListHandler_SelectionModeEnum alloc] initWithNSString:@"MULTIPLE" withInt:1];
    RAREiListHandler_SelectionModeEnum_BLOCK = [[RAREiListHandler_SelectionModeEnum alloc] initWithNSString:@"BLOCK" withInt:2];
    RAREiListHandler_SelectionModeEnum_NONE = [[RAREiListHandler_SelectionModeEnum alloc] initWithNSString:@"NONE" withInt:3];
    RAREiListHandler_SelectionModeEnum_INVISIBLE = [[RAREiListHandler_SelectionModeEnum alloc] initWithNSString:@"INVISIBLE" withInt:4];
    RAREiListHandler_SelectionModeEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREiListHandler_SelectionModeEnum_SINGLE, RAREiListHandler_SelectionModeEnum_MULTIPLE, RAREiListHandler_SelectionModeEnum_BLOCK, RAREiListHandler_SelectionModeEnum_NONE, RAREiListHandler_SelectionModeEnum_INVISIBLE, nil } count:5 type:[IOSClass classWithClass:[RAREiListHandler_SelectionModeEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREiListHandler_SelectionModeEnum_values];
}

+ (RAREiListHandler_SelectionModeEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREiListHandler_SelectionModeEnum_values count]; i++) {
    RAREiListHandler_SelectionModeEnum *e = RAREiListHandler_SelectionModeEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREiListHandler_SelectionModeEnum"};
  static J2ObjcClassInfo _RAREiListHandler_SelectionModeEnum = { "SelectionMode", "com.appnativa.rare.ui", "iListHandler", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREiListHandler_SelectionModeEnum;
}

@end

static RAREiListHandler_SelectionTypeEnum *RAREiListHandler_SelectionTypeEnum_CHECKED_LEFT;
static RAREiListHandler_SelectionTypeEnum *RAREiListHandler_SelectionTypeEnum_CHECKED_RIGHT;
static RAREiListHandler_SelectionTypeEnum *RAREiListHandler_SelectionTypeEnum_ROW_ON_TOP;
static RAREiListHandler_SelectionTypeEnum *RAREiListHandler_SelectionTypeEnum_ROW_ON_BOTTOM;
IOSObjectArray *RAREiListHandler_SelectionTypeEnum_values;

@implementation RAREiListHandler_SelectionTypeEnum

+ (RAREiListHandler_SelectionTypeEnum *)CHECKED_LEFT {
  return RAREiListHandler_SelectionTypeEnum_CHECKED_LEFT;
}
+ (RAREiListHandler_SelectionTypeEnum *)CHECKED_RIGHT {
  return RAREiListHandler_SelectionTypeEnum_CHECKED_RIGHT;
}
+ (RAREiListHandler_SelectionTypeEnum *)ROW_ON_TOP {
  return RAREiListHandler_SelectionTypeEnum_ROW_ON_TOP;
}
+ (RAREiListHandler_SelectionTypeEnum *)ROW_ON_BOTTOM {
  return RAREiListHandler_SelectionTypeEnum_ROW_ON_BOTTOM;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREiListHandler_SelectionTypeEnum class]) {
    RAREiListHandler_SelectionTypeEnum_CHECKED_LEFT = [[RAREiListHandler_SelectionTypeEnum alloc] initWithNSString:@"CHECKED_LEFT" withInt:0];
    RAREiListHandler_SelectionTypeEnum_CHECKED_RIGHT = [[RAREiListHandler_SelectionTypeEnum alloc] initWithNSString:@"CHECKED_RIGHT" withInt:1];
    RAREiListHandler_SelectionTypeEnum_ROW_ON_TOP = [[RAREiListHandler_SelectionTypeEnum alloc] initWithNSString:@"ROW_ON_TOP" withInt:2];
    RAREiListHandler_SelectionTypeEnum_ROW_ON_BOTTOM = [[RAREiListHandler_SelectionTypeEnum alloc] initWithNSString:@"ROW_ON_BOTTOM" withInt:3];
    RAREiListHandler_SelectionTypeEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREiListHandler_SelectionTypeEnum_CHECKED_LEFT, RAREiListHandler_SelectionTypeEnum_CHECKED_RIGHT, RAREiListHandler_SelectionTypeEnum_ROW_ON_TOP, RAREiListHandler_SelectionTypeEnum_ROW_ON_BOTTOM, nil } count:4 type:[IOSClass classWithClass:[RAREiListHandler_SelectionTypeEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREiListHandler_SelectionTypeEnum_values];
}

+ (RAREiListHandler_SelectionTypeEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREiListHandler_SelectionTypeEnum_values count]; i++) {
    RAREiListHandler_SelectionTypeEnum *e = RAREiListHandler_SelectionTypeEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREiListHandler_SelectionTypeEnum"};
  static J2ObjcClassInfo _RAREiListHandler_SelectionTypeEnum = { "SelectionType", "com.appnativa.rare.ui", "iListHandler", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREiListHandler_SelectionTypeEnum;
}

@end
