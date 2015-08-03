//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/aCheckBoxListViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/scripting/WidgetContext.h"
#include "com/appnativa/rare/scripting/iScriptHandler.h"
#include "com/appnativa/rare/spot/CheckBoxList.h"
#include "com/appnativa/rare/spot/ListBox.h"
#include "com/appnativa/rare/spot/Tree.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/event/ItemChangeEvent.h"
#include "com/appnativa/rare/ui/iListHandler.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/iPlatformListHandler.h"
#include "com/appnativa/rare/util/DataItemCollection.h"
#include "com/appnativa/rare/viewer/aCheckBoxListViewer.h"
#include "com/appnativa/rare/viewer/aListViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/viewer/iFormViewer.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "java/util/ArrayList.h"
#include "java/util/List.h"

@implementation RAREaCheckBoxListViewer

- (id)init {
  return [self initRAREaCheckBoxListViewerWithRAREiContainer:nil];
}

- (id)initRAREaCheckBoxListViewerWithRAREiContainer:(id<RAREiContainer>)parent {
  if (self = [super initWithRAREiContainer:parent]) {
    widgetType_ = [RAREiWidget_WidgetTypeEnum CheckBoxList];
  }
  return self;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [self initRAREaCheckBoxListViewerWithRAREiContainer:parent];
}

- (void)clearCheckMarks {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)dispose {
  [super dispose];
  checkBoxChangeHandler_ = nil;
}

- (void)removeCheckBoxChangedHandler {
  if (checkBoxChangeHandler_ != nil) {
    checkBoxChangeHandler_ = nil;
  }
}

- (void)swapWithInt:(int)index1
            withInt:(int)index2 {
  [super swapWithInt:index1 withInt:index2];
  [self setRowCheckedWithInt:index2 withBoolean:[self isRowCheckedWithInt:index1]];
  [self setRowCheckedWithInt:index1 withBoolean:[self isRowCheckedWithInt:index2]];
}

- (void)setCheckBoxChangedHandlerWithNSString:(NSString *)handler {
  if (handler == nil) {
    [self removeCheckBoxChangedHandler];
  }
  else {
    if (checkBoxChangeHandler_ == nil) {
      checkBoxChangeHandler_ = [[RAREaCheckBoxListViewer_CheckBoxChangeHandler alloc] initWithRAREaCheckBoxListViewer:self];
    }
    [((RAREaCheckBoxListViewer_CheckBoxChangeHandler *) nil_chk(checkBoxChangeHandler_)) setHandlerWithNSString:handler];
  }
}

- (void)setCheckboxTrailingWithBoolean:(BOOL)value {
  [self setListSelectionTypeWithRAREiListHandler_SelectionTypeEnum:value ? [RAREiListHandler_SelectionTypeEnum CHECKED_RIGHT] : [RAREiListHandler_SelectionTypeEnum CHECKED_LEFT]];
}

- (void)setCheckedItemWithRARERenderableDataItem:(RARERenderableDataItem *)item
                                     withBoolean:(BOOL)checked {
  [self setRowCheckedWithInt:[self indexOfWithId:item] withBoolean:checked];
}

- (void)setCheckedRowsWithIntArray:(IOSIntArray *)indices {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)setFromHTTPFormValueWithId:(id)value {
  @try {
    settingFromHTTPFormValue_ = YES;
    [super setFromHTTPFormValueWithId:value];
  }
  @finally {
    settingFromHTTPFormValue_ = NO;
  }
}

- (void)setLinkSelectionWithBoolean:(BOOL)linked {
  linkedSelection_ = linked;
}

- (void)setRowCheckedWithInt:(int)row
                 withBoolean:(BOOL)checked {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)setSelectedIndexWithInt:(int)index {
  if (settingFromHTTPFormValue_ && (index > -1)) {
    switch (submitValueType_) {
      case RARESPOTTree_CSubmitValue_checked_index:
      case RARESPOTTree_CSubmitValue_checked_linked_data:
      [self setRowCheckedWithInt:index withBoolean:YES];
      return;
    }
  }
  [super setSelectedIndexWithInt:index];
}

- (void)setSelectedIndexesWithIntArray:(IOSIntArray *)indices {
  if (settingFromHTTPFormValue_ && (indices != nil)) {
    [self clearSelection];
    switch (submitValueType_) {
      case RARESPOTTree_CSubmitValue_checked_index:
      case RARESPOTTree_CSubmitValue_checked_linked_data:
      [self setCheckedRowsWithIntArray:indices];
      return;
    }
  }
  [super setSelectedIndexesWithIntArray:indices];
}

- (IOSObjectArray *)getCheckedData {
  return [RAREDataItemCollection getValuesWithJavaUtilList:listComponent_ withIntArray:[self getCheckedRows] withInt:-1 withBoolean:YES];
}

- (IOSObjectArray *)getCheckedDataAsStrings {
  return [self getSelectionsDataAsStrings];
}

- (id)getCheckedItem {
  return [self getCheckedRowItem];
}

- (NSString *)getCheckedItemAsString {
  RARERenderableDataItem *di = [self getCheckedRowItem];
  return (di == nil) ? nil : [di description];
}

- (IOSObjectArray *)getCheckedItems {
  return [self getSelections];
}

- (IOSObjectArray *)getCheckedItemsAsStrings {
  return [self getSelectionsAsStrings];
}

- (RARERenderableDataItem *)getCheckedRowItem {
  return [self getSelectedItem];
}

- (IOSIntArray *)getCheckedRows {
  return [self getSelectedIndexes];
}

- (id)getHTTPFormValue {
  if (![self hasSelection] && ![self hasCheckedRows]) {
    return nil;
  }
  switch (submitValueType_) {
    case RARESPOTListBox_CSubmitValue_checked_index:
    return [self getCheckedRows];
    case RARESPOTListBox_CSubmitValue_checked_linked_data:
    return [self getCheckedData];
    default:
    return [super getHTTPFormValue];
  }
}

- (BOOL)getLinkSelection {
  return linkedSelection_;
}

- (BOOL)hasCheckedRows {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (BOOL)isCheckboxTrailing {
  return [RAREiListHandler_SelectionTypeEnum CHECKED_RIGHT] == [self getListSelectionType];
}

- (BOOL)isRowCheckedWithInt:(int)row {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (BOOL)isValidForSubmissionWithBoolean:(BOOL)showerror {
  switch (submitValueType_) {
    case RARESPOTListBox_CSubmitValue_checked_value_text:
    case RARESPOTListBox_CSubmitValue_checked_linked_data:
    case RARESPOTListBox_CSubmitValue_checked_index:
    return [self hasCheckedRows];
    default:
    return [((id<RAREiPlatformListHandler>) nil_chk(listComponent_)) hasSelection];
  }
}

- (void)configureExWithRARESPOTListBox:(RARESPOTListBox *)cfg {
  [super configureExWithRARESPOTListBox:cfg];
  RARESPOTCheckBoxList *cbl = (RARESPOTCheckBoxList *) check_class_cast(cfg, [RARESPOTCheckBoxList class]);
  if ([((SPOTBoolean *) nil_chk(((RARESPOTCheckBoxList *) nil_chk(cbl))->checkboxTrailing_)) spot_valueWasSet] && [RAREPlatform isTouchDevice]) {
    [self setCheckboxTrailingWithBoolean:[cbl->checkboxTrailing_ booleanValue]];
  }
  else {
    [self setCheckboxTrailingWithBoolean:[RAREPlatform isTouchDevice] ? YES : NO];
  }
  [self setLinkSelectionWithBoolean:[((SPOTBoolean *) nil_chk(cbl->linkSelection_)) booleanValue]];
  RAREUIColor *fg = [self getForeground];
  if (fg == nil) {
    fg = [RAREColorUtils getForeground];
  }
  id<RAREiPlatformIcon> checked;
  id<RAREiPlatformIcon> unchecked;
  id<RAREiPlatformIcon> indeterminate;
  if ([((RAREUIColor *) nil_chk(fg)) isDarkColor]) {
    checked = [RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.checkboxchecked"];
    unchecked = [RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.checkbox"];
    indeterminate = [RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.checkboxindeterminate"];
  }
  else {
    checked = [RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.checkboxchecked.light"];
    unchecked = [RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.checkbox.light"];
    indeterminate = [RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.checkboxindeterminate.light"];
  }
  [self setIconsWithRAREiPlatformIcon:checked withRAREiPlatformIcon:unchecked withRAREiPlatformIcon:indeterminate];
}

- (void)finishedLoadingEx {
  if ([self isDisposed]) {
    return;
  }
  [super finishedLoadingEx];
  if (itemAttributes_ != nil) {
    if (itemAttributes_->check_ != nil) {
      [self setCheckedItemWithRARERenderableDataItem:itemAttributes_->check_ withBoolean:YES];
    }
    id<JavaUtilList> list = itemAttributes_->checked_;
    int len = (list == nil) ? 0 : [list size];
    for (int i = 0; i < len; i++) {
      int n = [self indexOfWithId:[((id<JavaUtilList>) nil_chk(list)) getWithInt:i]];
      if (n != -1) {
        [self setRowCheckedWithInt:n withBoolean:YES];
      }
    }
    if (([self getFormViewer] == nil) || ![((id<RAREiFormViewer>) nil_chk([self getFormViewer])) isRetainInitialWidgetValues]) {
      itemAttributes_ = nil;
    }
  }
}

- (void)setIconsWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)checked
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)unchecked
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)indeterminate {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)copyAllFieldsTo:(RAREaCheckBoxListViewer *)other {
  [super copyAllFieldsTo:other];
  other->checkBoxChangeHandler_ = checkBoxChangeHandler_;
  other->linkedSelection_ = linkedSelection_;
  other->settingFromHTTPFormValue_ = settingFromHTTPFormValue_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "clearCheckMarks", NULL, "V", 0x401, NULL },
    { "setCheckedRowsWithIntArray:", NULL, "V", 0x401, NULL },
    { "setRowCheckedWithInt:withBoolean:", NULL, "V", 0x401, NULL },
    { "getCheckedData", NULL, "LIOSObjectArray", 0x1, NULL },
    { "getCheckedDataAsStrings", NULL, "LIOSObjectArray", 0x1, NULL },
    { "getCheckedItem", NULL, "LNSObject", 0x1, NULL },
    { "getCheckedItemAsString", NULL, "LNSString", 0x1, NULL },
    { "getCheckedItems", NULL, "LIOSObjectArray", 0x1, NULL },
    { "getCheckedItemsAsStrings", NULL, "LIOSObjectArray", 0x1, NULL },
    { "getCheckedRowItem", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "getCheckedRows", NULL, "LIOSIntArray", 0x1, NULL },
    { "getHTTPFormValue", NULL, "LNSObject", 0x1, NULL },
    { "getLinkSelection", NULL, "Z", 0x1, NULL },
    { "hasCheckedRows", NULL, "Z", 0x401, NULL },
    { "isCheckboxTrailing", NULL, "Z", 0x1, NULL },
    { "isRowCheckedWithInt:", NULL, "Z", 0x401, NULL },
    { "isValidForSubmissionWithBoolean:", NULL, "Z", 0x1, NULL },
    { "configureExWithRARESPOTListBox:", NULL, "V", 0x4, NULL },
    { "finishedLoadingEx", NULL, "V", 0x4, NULL },
    { "setIconsWithRAREiPlatformIcon:withRAREiPlatformIcon:withRAREiPlatformIcon:", NULL, "V", 0x404, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "checkBoxChangeHandler_", NULL, 0x4, "LRAREaCheckBoxListViewer_CheckBoxChangeHandler" },
    { "linkedSelection_", NULL, 0x4, "Z" },
    { "settingFromHTTPFormValue_", NULL, 0x4, "Z" },
  };
  static J2ObjcClassInfo _RAREaCheckBoxListViewer = { "aCheckBoxListViewer", "com.appnativa.rare.viewer", NULL, 0x401, 20, methods, 3, fields, 0, NULL};
  return &_RAREaCheckBoxListViewer;
}

@end
@implementation RAREaCheckBoxListViewer_CheckBoxChangeHandler

- (void)itemChangedWithRAREItemChangeEvent:(RAREItemChangeEvent *)e {
  id<RAREiScriptHandler> sh = [this$0_ getScriptHandler];
  if (scriptCode_ == nil) {
    scriptCode_ = [((id<RAREiScriptHandler>) nil_chk(sh)) compileWithRAREWidgetContext:[this$0_ getScriptingContext] withNSString:[NSString stringWithFormat:@"%@.CheckBoxChangeHandler", [this$0_ getName]] withNSString:handler_];
    handler_ = nil;
  }
  [RAREaWidgetListener executeWithRAREiWidget:this$0_ withRAREiScriptHandler:sh withId:scriptCode_ withNSString:[RAREiConstants EVENT_CHANGE] withJavaUtilEventObject:e];
}

- (void)setHandlerWithNSString:(NSString *)handler {
  self->handler_ = handler;
  scriptCode_ = nil;
}

- (id)initWithRAREaCheckBoxListViewer:(RAREaCheckBoxListViewer *)outer$ {
  this$0_ = outer$;
  return [super init];
}

- (void)copyAllFieldsTo:(RAREaCheckBoxListViewer_CheckBoxChangeHandler *)other {
  [super copyAllFieldsTo:other];
  other->handler_ = handler_;
  other->scriptCode_ = scriptCode_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREaCheckBoxListViewer" },
    { "handler_", NULL, 0x0, "LNSString" },
    { "scriptCode_", NULL, 0x0, "LNSObject" },
  };
  static J2ObjcClassInfo _RAREaCheckBoxListViewer_CheckBoxChangeHandler = { "CheckBoxChangeHandler", "com.appnativa.rare.viewer", "aCheckBoxListViewer", 0x2, 0, NULL, 3, fields, 0, NULL};
  return &_RAREaCheckBoxListViewer_CheckBoxChangeHandler;
}

@end
