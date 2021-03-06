//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-tree/com/appnativa/rare/viewer/aTreeViewer.java
//
//  Created by decoteaud on 9/15/14.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/spot/DataItem.h"
#include "com/appnativa/rare/spot/ItemDescription.h"
#include "com/appnativa/rare/spot/ListBox.h"
#include "com/appnativa/rare/spot/Tree.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/Column.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIColorHelper.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIFontHelper.h"
#include "com/appnativa/rare/ui/UIImageHelper.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/ui/UISelectionModelGroup.h"
#include "com/appnativa/rare/ui/UIStroke.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/event/iExpandedListener.h"
#include "com/appnativa/rare/ui/event/iExpansionListener.h"
#include "com/appnativa/rare/ui/iListHandler.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/iPlatformItemRenderer.h"
#include "com/appnativa/rare/ui/iPlatformListDataModel.h"
#include "com/appnativa/rare/ui/iTreeHandler.h"
#include "com/appnativa/rare/ui/table/TableHelper.h"
#include "com/appnativa/rare/ui/tree/aDataItemTreeModel.h"
#include "com/appnativa/rare/util/ListHelper.h"
#include "com/appnativa/rare/viewer/TreeViewer.h"
#include "com/appnativa/rare/viewer/aListViewer.h"
#include "com/appnativa/rare/viewer/aTreeViewer.h"
#include "com/appnativa/rare/viewer/aViewer.h"
#include "com/appnativa/rare/viewer/iFormViewer.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/util/MutableInteger.h"
#include "com/appnativa/util/SNumber.h"
#include "java/lang/Character.h"
#include "java/lang/Exception.h"
#include "java/lang/Integer.h"
#include "java/lang/Math.h"
#include "java/util/ArrayList.h"
#include "java/util/Comparator.h"
#include "java/util/List.h"
#include "java/util/Locale.h"

@implementation RAREaTreeViewer

- (id)init {
  return [self initRAREaTreeViewerWithRAREiFormViewer:nil];
}

- (id)initRAREaTreeViewerWithRAREiFormViewer:(id<RAREiFormViewer>)fv {
  if (self = [super initWithRAREiFormViewer:fv]) {
    currentLevel_ = 1;
    rootSetInCfg_ = NO;
    widgetType_ = [RAREiWidget_WidgetTypeEnum Tree];
    initiallySelectedIndex_ = -1;
  }
  return self;
}

- (id)initWithRAREiFormViewer:(id<RAREiFormViewer>)fv {
  return [self initRAREaTreeViewerWithRAREiFormViewer:fv];
}

- (void)addChildWithRARERenderableDataItem:(RARERenderableDataItem *)child {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) addChildWithRARERenderableDataItem:child];
}

- (void)addChildWithInt:(int)row
withRARERenderableDataItem:(RARERenderableDataItem *)child {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) addChildWithInt:row withRARERenderableDataItem:child];
}

- (void)addChildWithRARERenderableDataItem:(RARERenderableDataItem *)row
                withRARERenderableDataItem:(RARERenderableDataItem *)child {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) addChildWithRARERenderableDataItem:row withRARERenderableDataItem:child];
}

- (void)addChildrenWithInt:(int)row
          withJavaUtilList:(id<JavaUtilList>)children {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) addChildrenWithInt:row withJavaUtilList:children];
}

- (void)addChildrenWithRARERenderableDataItem:(RARERenderableDataItem *)row
                             withJavaUtilList:(id<JavaUtilList>)children {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) addChildrenWithRARERenderableDataItem:row withJavaUtilList:children];
}

- (void)addExpandedListenerWithRAREiExpandedListener:(id<RAREiExpandedListener>)l {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) addExpandedListenerWithRAREiExpandedListener:l];
}

- (void)addExpansionListenerWithRAREiExpansionListener:(id<RAREiExpansionListener>)l {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) addExpansionListenerWithRAREiExpansionListener:l];
}

- (void)addParsedRowWithRARERenderableDataItem:(RARERenderableDataItem *)row {
  @synchronized (widgetType_) {
    if ([self isDisposed]) {
      return;
    }
    id o = [((RARERenderableDataItem *) nil_chk(row)) getModelData];
    int l = 1;
    if ([o isKindOfClass:[RAREUTMutableInteger class]]) {
      RAREUTMutableInteger *level = (RAREUTMutableInteger *) check_class_cast(o, [RAREUTMutableInteger class]);
      [row setModelDataWithId:nil];
      l = [((RAREUTMutableInteger *) nil_chk(level)) intValue];
      if ((l == 0) && ([self getItemCount] == 0)) {
        [self setRootItemWithRARERenderableDataItem:row];
        return;
      }
      if (l < 1) {
        l = 1;
      }
    }
    if ((l < 2) || (currentItem_ == nil)) {
      [self addExWithRARERenderableDataItem:row];
      currentLevel_ = 1;
    }
    else {
      if (currentLevel_ == l) {
        [((RARERenderableDataItem *) check_class_cast([currentItem_ getParentItem], [RARERenderableDataItem class])) addWithId:row];
      }
      else if (currentLevel_ < l) {
        [currentItem_ addWithId:row];
        currentLevel_++;
      }
      else {
        while (currentLevel_ > l) {
          currentItem_ = (RARERenderableDataItem *) check_class_cast([currentItem_ getParentItem], [RARERenderableDataItem class]);
          currentLevel_--;
        }
        [((RARERenderableDataItem *) check_class_cast([((RARERenderableDataItem *) nil_chk(currentItem_)) getParentItem], [RARERenderableDataItem class])) addWithId:row];
      }
    }
    currentItem_ = row;
  }
}

- (void)cancelEditing {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)clear {
  if (![RAREPlatform isUIThread]) {
    [RAREListHelper runLaterWithRAREiListHandler:self withRAREListHelper_RunTypeEnum:[RAREListHelper_RunTypeEnum CLEAR]];
    return;
  }
  [((id<RAREiListHandler>) nil_chk(listComponent_)) clear];
  rootSetInCfg_ = NO;
}

- (void)clearContents {
  [super clearContents];
  if (rootSetInCfg_) {
    [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) clearRootNode];
  }
  else {
    [self clear];
  }
}

- (void)clearRootNode {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) clearRootNode];
}

- (void)collapseAll {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) collapseAll];
}

- (void)collapseRowWithInt:(int)row {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) collapseRowWithInt:row];
}

- (void)collapseRowWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) collapseRowWithRARERenderableDataItem:item];
}

- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg {
  [self configureExWithRARESPOTTree:(RARESPOTTree *) check_class_cast(vcfg, [RARESPOTTree class])];
  [self handleDataURLWithRARESPOTWidget:vcfg];
  [self fireConfigureEventWithRARESPOTWidget:vcfg withNSString:[RAREiConstants EVENT_CONFIGURE]];
}

+ (RARETreeViewer *)createWithRAREiFormViewer:(id<RAREiFormViewer>)fv
                             withRARESPOTTree:(RARESPOTTree *)cfg {
  RARETreeViewer *widget = [[RARETreeViewer alloc] initWithRAREiFormViewer:fv];
  [widget configureWithRARESPOTViewer:cfg];
  return widget;
}

- (void)dispose {
  if (![self isDisposable]) {
    return;
  }
  if ((selectionModelGroup_ != nil) && (selectionModel_ != nil)) {
    [selectionModelGroup_ removeWithId:selectionModel_];
  }
  [super dispose];
  if (listModel_ != nil) {
    [listModel_ dispose];
  }
  if (treeModel_ != nil) {
    [treeModel_ dispose];
  }
  treeModel_ = nil;
  selectionModelGroup_ = nil;
  listModel_ = nil;
  treeHandler_ = nil;
  closedIcon_ = nil;
  currentItem_ = nil;
  disabledClosedIcon_ = nil;
  disabledLeafIcon_ = nil;
  disabledOpenIcon_ = nil;
  leafIcon_ = nil;
  openIcon_ = nil;
}

- (void)expandAll {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) expandAll];
}

- (void)expandRowWithInt:(int)row {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) expandRowWithInt:row];
}

- (void)expandRowWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) expandRowWithRARERenderableDataItem:item];
}

- (void)refreshItems {
  if (![RAREPlatform isUIThread]) {
    [RAREListHelper runLaterWithRAREiListHandler:self withRAREListHelper_RunTypeEnum:[RAREListHelper_RunTypeEnum REFRESH]];
    return;
  }
  if (tempList_ != nil) {
    [((RAREaDataItemTreeModel *) nil_chk(treeModel_)) setAllWithJavaUtilCollection:tempList_];
    [tempList_ clear];
    tempList_ = nil;
    if (sorter_ != nil) {
      [treeModel_ sortWithJavaUtilComparator:sorter_];
    }
  }
  else {
    [((RAREaDataItemTreeModel *) nil_chk(treeModel_)) refreshItems];
  }
}

- (void)removeExpandedListenerWithRAREiExpandedListener:(id<RAREiExpandedListener>)l {
  if (treeHandler_ != nil) {
    [treeHandler_ removeExpandedListenerWithRAREiExpandedListener:l];
  }
}

- (void)removeExpansionListenerWithRAREiExpansionListener:(id<RAREiExpansionListener>)l {
  if (treeHandler_ != nil) {
    [treeHandler_ removeExpansionListenerWithRAREiExpansionListener:l];
  }
}

- (void)repaintRowWithInt:(int)row {
  [self rowChangedWithInt:row];
}

- (void)rowChangedWithInt:(int)index {
  [((RAREaDataItemTreeModel *) nil_chk(treeModel_)) nodeChangedWithRARERenderableDataItem:[self getWithInt:index]];
}

- (void)rowChangedWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  [((RAREaDataItemTreeModel *) nil_chk(treeModel_)) nodeChangedWithRARERenderableDataItem:item];
}

- (void)rowStructureChangedWithInt:(int)index {
  [((RAREaDataItemTreeModel *) nil_chk(treeModel_)) nodeStructureChangedWithRARERenderableDataItem:[self getWithInt:index]];
}

- (void)rowStructureChangedWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  [((RAREaDataItemTreeModel *) nil_chk(treeModel_)) nodeStructureChangedWithRARERenderableDataItem:item];
}

- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)comparator {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) sortWithJavaUtilComparator:comparator];
}

- (void)sortExWithJavaUtilComparator:(id<JavaUtilComparator>)comparator {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) sortExWithJavaUtilComparator:comparator];
}

- (void)startedParsing {
  parsing_ = YES;
  [super startedParsing];
}

- (void)toggleRowWithInt:(int)row {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) toggleRowWithInt:row];
}

- (void)setAutoScrollOnExpansionWithBoolean:(BOOL)autoScrollOnExpansion {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) setAutoScrollOnExpansionWithBoolean:autoScrollOnExpansion];
}

- (void)setAutoSizeRowsToFitWithBoolean:(BOOL)autoSize {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)setDisabledFolderClosedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  self->disabledClosedIcon_ = icon;
}

- (void)setDisabledFolderOpenIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  self->disabledOpenIcon_ = icon;
}

- (void)setDisabledLeafIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  self->disabledLeafIcon_ = icon;
}

- (void)setEditingModeWithRAREiTreeHandler_EditingModeEnum:(RAREiTreeHandler_EditingModeEnum *)mode {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) setEditingModeWithRAREiTreeHandler_EditingModeEnum:mode];
}

- (void)setExpandableStateLockedWithBoolean:(BOOL)locked {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) setExpandableStateLockedWithBoolean:locked];
}

- (void)setFolderClosedIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  self->closedIcon_ = icon;
}

- (void)setFolderOpenIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  self->openIcon_ = icon;
}

- (void)setFromHTTPFormValueWithId:(id)value {
  {
    int n;
    switch (submitValueType_) {
      case RARESPOTTree_CSubmitValue_selected_index:
      case RARESPOTTree_CSubmitValue_checked_index:
      if (value == nil) {
        [self setSelectedIndexWithInt:-1];
        return;
      }
      if ([value isKindOfClass:[IOSIntArray class]]) {
        [self setSelectedIndexesWithIntArray:(IOSIntArray *) check_class_cast(value, [IOSIntArray class])];
        break;
      }
      n = -1;
      if ([value isKindOfClass:[NSNumber class]]) {
        n = [((NSNumber *) check_class_cast(value, [NSNumber class])) intValue];
      }
      else {
        NSString *s = [nil_chk(value) description];
        if (([((NSString *) nil_chk(s)) sequenceLength] > 0) && [JavaLangCharacter isDigitWithChar:[s charAtWithInt:0]]) {
          n = [RAREUTSNumber intValueWithNSString:s];
        }
      }
      if ((n < -1) || (n >= [self size])) {
        n = -1;
      }
      [self setSelectedIndexWithInt:n];
      break;
      case RARESPOTTree_CSubmitValue_selected_linked_data:
      case RARESPOTTree_CSubmitValue_checked_linked_data:
      if (value != nil) {
        [self setSelectedIndexWithInt:[self indexOfLinkedDataWithId:value]];
      }
      else {
        [self setSelectedIndexWithInt:-1];
      }
      break;
      case RARESPOTTree_CSubmitValue_selected_value_text:
      if (value != nil) {
        [self setSelectedIndexWithInt:[self indexOfValueWithId:value]];
      }
      else {
        [self setSelectedIndexWithInt:-1];
      }
      break;
      default:
      [self setValueWithId:value];
      break;
    }
  }
}

- (void)setIndentByWithInt:(int)indent {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) setIndentByWithInt:indent];
}

- (void)setLeafIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  leafIcon_ = icon;
}

- (void)setRootItemWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  if (parsing_ && rootSetInCfg_) {
    return;
  }
  if ((treeModel_ != nil) && (item != nil)) {
    BOOL ed = [treeModel_ isEventsEnabled];
    @try {
      [treeModel_ setEventsEnabledWithBoolean:NO];
      [treeModel_ clearEx];
      [((RARERenderableDataItem *) nil_chk([treeModel_ getRoot])) copy__WithRARERenderableDataItem:item];
    }
    @finally {
      [treeModel_ setEventsEnabledWithBoolean:ed];
      if (ed && !parsing_) {
        [treeModel_ structureChanged];
        if (![self isRootNodeCollapsible]) {
          [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) expandRowWithInt:0];
        }
      }
    }
  }
}

- (void)setRootNodeCollapsibleWithBoolean:(BOOL)collapsible {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) setRootNodeCollapsibleWithBoolean:collapsible];
}

- (void)setShowRootHandlesWithBoolean:(BOOL)show {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) setShowRootHandlesWithBoolean:show];
}

- (void)setShowRootNodeWithBoolean:(BOOL)show {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) setShowRootNodeWithBoolean:show];
}

- (void)setSingleClickToggleWithBoolean:(BOOL)singleClickToggle {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) setSingleClickToggleWithBoolean:singleClickToggle];
}

- (void)setToggleOnTwistyOnlyWithBoolean:(BOOL)twistyOnly {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) setToggleOnTwistyOnlyWithBoolean:twistyOnly];
}

- (void)setTreeEventsEnabledWithBoolean:(BOOL)enabled {
  if (treeHandler_ != nil) {
    [treeHandler_ setTreeEventsEnabledWithBoolean:enabled];
  }
}

- (void)setTreeIconsWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)expanded
                    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)collapsed {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)setTwistyMarginOfErrorWithInt:(int)twistyMarginOfError {
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) setTwistyMarginOfErrorWithInt:twistyMarginOfError];
}

- (id<RAREiPlatformIcon>)getDisabledFolderClosedIcon {
  if ((disabledClosedIcon_ == nil) && (closedIcon_ != nil)) {
    disabledClosedIcon_ = [RAREUIImageHelper createDisabledIconWithRAREiPlatformIcon:closedIcon_];
  }
  return disabledClosedIcon_;
}

- (id<RAREiPlatformIcon>)getDisabledFolderOpenIcon {
  if ((disabledOpenIcon_ == nil) && (openIcon_ != nil)) {
    disabledOpenIcon_ = [RAREUIImageHelper createDisabledIconWithRAREiPlatformIcon:openIcon_];
  }
  return disabledOpenIcon_;
}

- (id<RAREiPlatformIcon>)getDisabledLeafIcon {
  if ((disabledLeafIcon_ == nil) && (leafIcon_ != nil)) {
    disabledLeafIcon_ = [RAREUIImageHelper createDisabledIconWithRAREiPlatformIcon:leafIcon_];
  }
  return disabledLeafIcon_;
}

- (int)getExpandableColumn {
  return 0;
}

- (id<RAREiPlatformIcon>)getFolderClosedIcon {
  return closedIcon_;
}

- (id<RAREiPlatformIcon>)getFolderOpenIcon {
  return openIcon_;
}

- (id)getHTTPFormValue {
  if (![self hasSelection]) {
    return nil;
  }
  switch (submitValueType_) {
    case RARESPOTTree_CSubmitValue_selected_index:
    case RARESPOTTree_CSubmitValue_checked_index:
    if (!selectAllAllowed_) {
      return [JavaLangInteger valueOfWithInt:[self getSelectedIndex]];
    }
    return [self getSelectedIndexes];
    case RARESPOTTree_CSubmitValue_selected_linked_data:
    case RARESPOTTree_CSubmitValue_checked_linked_data:
    return [self getSelectionData];
    case RARESPOTTree_CSubmitValue_selected_value_text:
    if (!selectAllAllowed_) {
      return [self getSelectionAsString];
    }
    return [self getSelectionAsString];
    default:
    if (!selectAllAllowed_) {
      return [self getSelection];
    }
    return [self getSelections];
  }
}

- (id<RAREiPlatformIcon>)getIcon {
  if (displayIcon_ != nil) {
    return displayIcon_;
  }
  RARERenderableDataItem *item = [self getSelectedItem];
  if ((item != nil) && ([item getIcon] != nil)) {
    return [item getIcon];
  }
  if (self->itemDescription_ != nil) {
    return [itemDescription_ getIcon];
  }
  return nil;
}

- (id<RAREiPlatformIcon>)getLeafIcon {
  return leafIcon_;
}

- (RARERenderableDataItem *)getParentWithInt:(int)index {
  return [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) getParentWithInt:index];
}

- (int)getParentIndexWithInt:(int)index {
  RARERenderableDataItem *item = [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) getParentWithInt:index];
  if (item == nil) {
    return -1;
  }
  while (index > 0) {
    index--;
    if ([self getWithInt:index] == item) {
      return index;
    }
  }
  return -1;
}

- (id<JavaUtilList>)getRawRows {
  return [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) getRawRows];
}

- (RARERenderableDataItem *)getRootItem {
  return [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) getRootItem];
}

- (int)getTwistyMarginOfError {
  return [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) getTwistyMarginOfError];
}

- (BOOL)isAutoScrollOnExpansion {
  return [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) isAutoScrollOnExpansion];
}

- (BOOL)isExpandableStateLocked {
  return [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) isExpandableStateLocked];
}

- (BOOL)isItemEditableWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  return [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) isItemEditableWithRARERenderableDataItem:item];
}

- (BOOL)isLeafItemWithInt:(int)index {
  return [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) isLeafItemWithInt:index];
}

- (BOOL)isRootNodeCollapsible {
  return [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) isRootNodeCollapsible];
}

- (BOOL)isRowExpandedWithInt:(int)row {
  return [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) isRowExpandedWithInt:row];
}

- (BOOL)isRowExpandedWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  return [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) isRowExpandedWithRARERenderableDataItem:item];
}

- (BOOL)isSingleClickToggle {
  return [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) isSingleClickToggle];
}

- (BOOL)isToggleOnTwistyOnly {
  return [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) isToggleOnTwistyOnly];
}

- (void)configureExWithRARESPOTTree:(RARESPOTTree *)cfg {
  [self createModelAndComponentsWithRARESPOTViewer:cfg];
  [self configureExWithRARESPOTViewer:cfg withBoolean:YES withBoolean:YES withBoolean:YES];
  [self setSubItemsWithRAREUTiFilterableList:listModel_];
  id<RAREiPlatformIcon> icon = [self getIconWithSPOTPrintableString:((RARESPOTTree *) nil_chk(cfg))->leafIcon_];
  id<RAREiPlatformIcon> dicon;
  if (icon != nil) {
    [self setLeafIconWithRAREiPlatformIcon:icon];
  }
  else {
    icon = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getIconWithNSString:@"Tree.leafIcon"];
    if (icon == nil) {
      [self setLeafIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.page"]];
      [self setDisabledLeafIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.pageDisabled"]];
    }
    else {
      [self setLeafIconWithRAREiPlatformIcon:icon];
      dicon = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getIconWithNSString:@"Tree.disabledLeafIcon"];
      if (dicon == nil) {
        dicon = [RAREUIImageHelper createDisabledIconWithRAREiPlatformIcon:icon];
      }
      [self setDisabledLeafIconWithRAREiPlatformIcon:dicon];
    }
  }
  icon = [self getIconWithSPOTPrintableString:cfg->folderOpenIcon_];
  if (icon != nil) {
    [self setFolderOpenIconWithRAREiPlatformIcon:icon];
  }
  else {
    icon = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getIconWithNSString:@"Tree.openIcon"];
    if (icon == nil) {
      [self setFolderOpenIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.folderOpen"]];
      [self setDisabledFolderOpenIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.folderOpenDisabled"]];
    }
    else {
      [self setFolderOpenIconWithRAREiPlatformIcon:icon];
      dicon = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getIconWithNSString:@"Tree.disabledOpenIcon"];
      if (dicon == nil) {
        dicon = [RAREUIImageHelper createDisabledIconWithRAREiPlatformIcon:icon];
      }
      [self setDisabledFolderOpenIconWithRAREiPlatformIcon:dicon];
    }
  }
  icon = [self getIconWithSPOTPrintableString:cfg->folderClosedIcon_];
  if (icon != nil) {
    [self setFolderClosedIconWithRAREiPlatformIcon:icon];
  }
  else {
    icon = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getIconWithNSString:@"Tree.closedIcon"];
    if (icon == nil) {
      [self setFolderClosedIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.folderClosed"]];
      [self setDisabledFolderClosedIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.folderClosedDisabled"]];
    }
    else {
      [self setFolderClosedIconWithRAREiPlatformIcon:icon];
      dicon = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getIconWithNSString:@"Tree.disabledClosedIcon"];
      if (dicon == nil) {
        dicon = [RAREUIImageHelper createDisabledIconWithRAREiPlatformIcon:icon];
      }
      [self setDisabledFolderClosedIconWithRAREiPlatformIcon:dicon];
    }
  }
  [((id<RAREiListHandler>) nil_chk(listComponent_)) setDeselectEventsDisabledWithBoolean:![((SPOTBoolean *) nil_chk(cfg->deselectEventsEnabled_)) booleanValue]];
  if ([((SPOTInteger *) nil_chk(cfg->visibleRowCount_)) spot_valueWasSet]) {
    int n = [cfg->visibleRowCount_ intValue];
    [listComponent_ setVisibleRowCountWithInt:n];
  }
  else {
    [listComponent_ setVisibleRowCountWithInt:[RARETableHelper getDefaultPreferredRows]];
  }
  if ([cfg getItemDescription] != nil) {
    itemDescription_ = [self createColumnWithRARESPOTItemDescription:[cfg getItemDescription]];
    [((id<RAREiPlatformItemRenderer>) nil_chk([self getItemRenderer])) setItemDescriptionWithRAREColumn:itemDescription_];
    if ([((RAREColumn *) nil_chk(itemDescription_)) getFont] != nil) {
      [((id<RAREiPlatformComponent>) nil_chk(dataComponent_)) setFontWithRAREUIFont:[itemDescription_ getFont]];
      [((id<RAREiPlatformComponent>) nil_chk(formComponent_)) setFontWithRAREUIFont:[itemDescription_ getFont]];
    }
  }
  [((id<RAREiPlatformListDataModel>) nil_chk(listModel_)) setWidgetWithRAREiWidget:self];
  [listModel_ setColumnDescriptionWithRAREColumn:itemDescription_];
  expandAll__ = [((SPOTBoolean *) nil_chk(cfg->expandAll_)) booleanValue];
  RARESPOTDataItem *root = [cfg getRootNode];
  if (root != nil) {
    RARERenderableDataItem *rootItem = [RAREaWidget populateItemWithRAREiWidget:self withRARESPOTDataItem:root withRARERenderableDataItem:nil];
    [((RAREaDataItemTreeModel *) nil_chk(treeModel_)) setRootWithRARERenderableDataItem:rootItem];
    rootSetInCfg_ = YES;
  }
  if ([((SPOTPrintableString *) nil_chk(cfg->alternatingHighlightColor_)) spot_valueWasSet]) {
    RAREUIColor *c = [RAREUIColorHelper getColorWithNSString:[cfg->alternatingHighlightColor_ getValue]];
    if ((c != nil) && (c != [RAREColorUtils TRANSPARENT_COLOR])) {
      [listComponent_ setAlternatingRowColorWithRAREUIColor:c];
    }
  }
  BOOL showDivider = [RAREPlatform isTouchableDevice];
  if ([((SPOTBoolean *) nil_chk(cfg->showDividerLine_)) spot_valueWasSet]) {
    showDivider = [cfg->showDividerLine_ booleanValue];
  }
  if (showDivider) {
    [listComponent_ setShowDividerWithBoolean:YES];
    RAREUIColor *c = [RAREUIColorHelper getColorWithNSString:[((SPOTPrintableString *) nil_chk(cfg->dividerLineColor_)) getValue]];
    if (c == nil) {
      c = [RAREColorUtils getListDividerColor];
    }
    [self setDividerLineWithRAREUIColor:c withRAREUIStroke:[RAREUIStroke getStrokeWithNSString:[((RARESPOTTree_CDividerLineStyle *) nil_chk(cfg->dividerLineStyle_)) stringValue]]];
    if ([@"false" isEqual:[cfg->showDividerLine_ spot_getAttributeWithNSString:@"showLastLine"]]) {
      [self setShowLastDividerWithBoolean:NO];
    }
  }
  int min = (int) [RAREUIFontHelper getDefaultLineHeight];
  min = [JavaLangMath maxWithInt:min withInt:[((id<RAREiPlatformIcon>) nil_chk(openIcon_)) getIconHeight]];
  min = [JavaLangMath maxWithInt:min withInt:[((id<RAREiPlatformIcon>) nil_chk(closedIcon_)) getIconHeight]];
  min = [JavaLangMath maxWithInt:min withInt:[((id<RAREiPlatformIcon>) nil_chk(leafIcon_)) getIconHeight]];
  [listComponent_ setMinRowHeightWithInt:min];
  [self configureSelectionModelGroupWithSPOTPrintableString:cfg->selectionGroupName_ withId:[[NSObject alloc] init]];
  [listComponent_ setSingleClickActionWithBoolean:[((SPOTBoolean *) nil_chk(cfg->singleClickActionEnabled_)) booleanValue]];
  NSString *s = nil;
  if ([((SPOTPrintableString *) nil_chk(cfg->rowHeight_)) spot_valueWasSet]) {
    s = [cfg->rowHeight_ getValue];
  }
  if (s == nil) {
    s = [RAREPlatformHelper getDefaultRowHeight];
  }
  [self setRowHeightWithInt:[RAREScreenUtils toPlatformPixelHeightWithNSString:s withRAREiPlatformComponent:dataComponent_ withInt:400 withBoolean:YES]];
  switch ([((RARESPOTTree_CSelectionMode *) nil_chk(cfg->selectionMode_)) intValue]) {
    case RARESPOTTree_CSelectionMode_multiple:
    [self setSelectionModeWithRAREiListHandler_SelectionModeEnum:[RAREiListHandler_SelectionModeEnum MULTIPLE]];
    break;
    case RARESPOTTree_CSelectionMode_block:
    [self setSelectionModeWithRAREiListHandler_SelectionModeEnum:[RAREiListHandler_SelectionModeEnum BLOCK]];
    break;
    case RARESPOTTree_CSelectionMode_invisible:
    [self setSelectionModeWithRAREiListHandler_SelectionModeEnum:[RAREiListHandler_SelectionModeEnum INVISIBLE]];
    break;
    case RARESPOTTree_CSelectionMode_none:
    [self setSelectionModeWithRAREiListHandler_SelectionModeEnum:[RAREiListHandler_SelectionModeEnum NONE]];
    break;
    case RARESPOTListBox_CSelectionMode_single_auto:
    [self setSelectionModeWithRAREiListHandler_SelectionModeEnum:[RAREiListHandler_SelectionModeEnum SINGLE]];
    [listComponent_ setAutoHilightWithBoolean:YES];
    break;
    default:
    [self setSelectionModeWithRAREiListHandler_SelectionModeEnum:[RAREiListHandler_SelectionModeEnum SINGLE]];
    break;
  }
  [self setTreeIconsWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.Tree.expandedIcon"] withRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.Tree.collapsedIcon"]];
  [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) setShowRootNodeWithBoolean:[((SPOTBoolean *) nil_chk(cfg->showRootNode_)) booleanValue]];
  [treeHandler_ setShowRootHandlesWithBoolean:[((SPOTBoolean *) nil_chk(cfg->showRootHandles_)) booleanValue]];
  @try {
    if ([((RARESPOTTree_CEditingMode *) nil_chk(cfg->editingMode_)) spot_valueWasSet]) {
      [treeHandler_ setEditingModeWithRAREiTreeHandler_EditingModeEnum:[RAREiTreeHandler_EditingModeEnum valueOfWithNSString:[((NSString *) nil_chk([cfg->editingMode_ stringValue])) uppercaseStringWithJRELocale:[JavaUtilLocale US]]]];
    }
  }
  @catch (JavaLangException *ex) {
    [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:ex];
  }
  if ([((SPOTBoolean *) nil_chk(cfg->autoSizeRowsToFit_)) booleanValue]) {
    [self setAutoSizeRowsToFitWithBoolean:YES];
  }
  [treeHandler_ setRootNodeCollapsibleWithBoolean:[((SPOTBoolean *) nil_chk(cfg->rootNodeCollapsible_)) booleanValue]];
  [listComponent_ setHandleFirstFocusSelectionWithBoolean:[((SPOTBoolean *) nil_chk(cfg->handleFirstFocusSelection_)) booleanValue]];
  initiallySelectedIndex_ = [((SPOTInteger *) nil_chk(cfg->selectedIndex_)) intValue];
  submitValueType_ = [((RARESPOTTree_CSubmitValue *) nil_chk(cfg->submitValue_)) intValue];
}

- (void)handleInitialStuff {
  [self refreshItems];
  if (expandAll__) {
    [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) expandAll];
  }
  if (itemAttributes_ != nil) {
    if (itemAttributes_->expand_ != nil) {
      [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) expandRowWithRARERenderableDataItem:itemAttributes_->expand_];
    }
    id<JavaUtilList> list = itemAttributes_->expanded_;
    int len = (list == nil) ? 0 : [list size];
    for (int i = 0; i < len; i++) {
      [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) expandRowWithRARERenderableDataItem:[((id<JavaUtilList>) nil_chk(list)) getWithInt:i]];
    }
    [RAREaWidget handleSelectionsWithRAREiListHandler:listComponent_ withRAREaWidget_ItemAttributes:itemAttributes_];
    if ((itemAttributes_->check_ == nil) && ([self getFormViewer] != nil) && ![((id<RAREiFormViewer>) nil_chk([self getFormViewer])) isRetainInitialWidgetValues]) {
      itemAttributes_ = nil;
    }
  }
  if (([self getSelectedIndex] == -1) && (initiallySelectedIndex_ > -1) && (initiallySelectedIndex_ < [self getItemCount])) {
    [self setSelectedIndexWithInt:initiallySelectedIndex_];
  }
  if (![self isRootNodeCollapsible]) {
    [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) expandRowWithInt:0];
  }
}

- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l {
  [super initializeListenersWithRAREaWidgetListener:l];
  if (l != nil) {
    if ([l isExpandedEventsEnabled]) {
      [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) addExpandedListenerWithRAREiExpandedListener:l];
    }
    if ([l isExpansionEventsEnabled]) {
      [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) addExpansionListenerWithRAREiExpansionListener:l];
    }
  }
}

- (void)uninitializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l {
  [super uninitializeListenersWithRAREaWidgetListener:l];
  if (l != nil) {
    [((id<RAREiTreeHandler>) nil_chk(treeHandler_)) removeExpandedListenerWithRAREiExpandedListener:l];
    [treeHandler_ removeExpansionListenerWithRAREiExpansionListener:l];
  }
}

- (void)setFlingThresholdWithInt:(int)threshold {
}

- (void)setSelectFlingedWithBoolean:(BOOL)select {
}

- (void)setWholeViewFlingWithBoolean:(BOOL)wholeViewFling {
}

- (void)copyAllFieldsTo:(RAREaTreeViewer *)other {
  [super copyAllFieldsTo:other];
  other->closedIcon_ = closedIcon_;
  other->currentItem_ = currentItem_;
  other->currentLevel_ = currentLevel_;
  other->disabledClosedIcon_ = disabledClosedIcon_;
  other->disabledLeafIcon_ = disabledLeafIcon_;
  other->disabledOpenIcon_ = disabledOpenIcon_;
  other->expandAll__ = expandAll__;
  other->leafIcon_ = leafIcon_;
  other->openIcon_ = openIcon_;
  other->parsing_ = parsing_;
  other->rootSetInCfg_ = rootSetInCfg_;
  other->treeHandler_ = treeHandler_;
  other->treeModel_ = treeModel_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "cancelEditing", NULL, "V", 0x401, NULL },
    { "createWithRAREiFormViewer:withRARESPOTTree:", NULL, "LRARETreeViewer", 0x9, NULL },
    { "setAutoSizeRowsToFitWithBoolean:", NULL, "V", 0x401, NULL },
    { "setTreeIconsWithRAREiPlatformIcon:withRAREiPlatformIcon:", NULL, "V", 0x401, NULL },
    { "getDisabledFolderClosedIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getDisabledFolderOpenIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getDisabledLeafIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getFolderClosedIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getFolderOpenIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getHTTPFormValue", NULL, "LNSObject", 0x1, NULL },
    { "getIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getLeafIcon", NULL, "LRAREiPlatformIcon", 0x1, NULL },
    { "getParentWithInt:", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "getRawRows", NULL, "LJavaUtilList", 0x1, NULL },
    { "getRootItem", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "isAutoScrollOnExpansion", NULL, "Z", 0x1, NULL },
    { "isExpandableStateLocked", NULL, "Z", 0x1, NULL },
    { "isItemEditableWithRARERenderableDataItem:", NULL, "Z", 0x1, NULL },
    { "isLeafItemWithInt:", NULL, "Z", 0x1, NULL },
    { "isRootNodeCollapsible", NULL, "Z", 0x1, NULL },
    { "isRowExpandedWithInt:", NULL, "Z", 0x1, NULL },
    { "isRowExpandedWithRARERenderableDataItem:", NULL, "Z", 0x1, NULL },
    { "isSingleClickToggle", NULL, "Z", 0x1, NULL },
    { "isToggleOnTwistyOnly", NULL, "Z", 0x1, NULL },
    { "configureExWithRARESPOTTree:", NULL, "V", 0x4, NULL },
    { "handleInitialStuff", NULL, "V", 0x4, NULL },
    { "initializeListenersWithRAREaWidgetListener:", NULL, "V", 0x4, NULL },
    { "uninitializeListenersWithRAREaWidgetListener:", NULL, "V", 0x4, NULL },
    { "setFlingThresholdWithInt:", NULL, "V", 0x4, NULL },
    { "setSelectFlingedWithBoolean:", NULL, "V", 0x4, NULL },
    { "setWholeViewFlingWithBoolean:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "currentLevel_", NULL, 0x4, "I" },
    { "rootSetInCfg_", NULL, 0x4, "Z" },
    { "closedIcon_", NULL, 0x4, "LRAREiPlatformIcon" },
    { "currentItem_", NULL, 0x4, "LRARERenderableDataItem" },
    { "disabledClosedIcon_", NULL, 0x84, "LRAREiPlatformIcon" },
    { "disabledLeafIcon_", NULL, 0x84, "LRAREiPlatformIcon" },
    { "disabledOpenIcon_", NULL, 0x84, "LRAREiPlatformIcon" },
    { "expandAll__", "expandAll", 0x4, "Z" },
    { "leafIcon_", NULL, 0x84, "LRAREiPlatformIcon" },
    { "openIcon_", NULL, 0x84, "LRAREiPlatformIcon" },
    { "parsing_", NULL, 0x4, "Z" },
    { "treeHandler_", NULL, 0x4, "LRAREiTreeHandler" },
    { "treeModel_", NULL, 0x4, "LRAREaDataItemTreeModel" },
  };
  static J2ObjcClassInfo _RAREaTreeViewer = { "aTreeViewer", "com.appnativa.rare.viewer", NULL, 0x401, 31, methods, 13, fields, 0, NULL};
  return &_RAREaTreeViewer;
}

@end
