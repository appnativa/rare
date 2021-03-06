//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/platform/apple/ui/view/TreeView.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARETreeView_H_
#define _RARETreeView_H_

@class RAREAppleGraphics;
@class RARECheckListManager;
@class RAREDataItemTreeModel;
@class RARERenderableDataItem;
@class RAREUIDimension;
@class RAREUIImage;
@class RAREUIRectangle;
@class RAREaDataItemTreeModel;
@class RAREaTableBasedView_RowView;
@class RAREaTreeItemRenderer;
@class RAREiListHandler_SelectionTypeEnum;
@protocol RAREiExpansionHandler;
@protocol RAREiPlatformIcon;
@protocol RAREiTreeItem;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/table/TableView.h"
#include "com/appnativa/rare/ui/tree/iTree.h"

@interface RARETreeView : RARETableView < RAREiTree > {
 @public
  int lastUpX_;
  int lastUpY_;
  long long int lastUpTime_;
  BOOL twistyMarginOfErrorSet_;
  int indentBy_;
  BOOL showRootHandles_;
  BOOL showRoot_;
  BOOL manageCheckboxSelection_;
  BOOL autoScrollOnExpansion_;
  id<RAREiPlatformIcon> collapsedIcon_;
  BOOL expandableStateLocked_;
  id<RAREiPlatformIcon> expandedIcon_;
  id<RAREiExpansionHandler> expansionHandler_;
  BOOL indentBySet_;
  BOOL rootNodeCollapsible_;
  BOOL singleClickToggle_;
  BOOL toggleOnTwistyOnly_;
  RAREaDataItemTreeModel *treeModel_;
  int twistyMarginOfError_;
  BOOL parentItemsSelectable_;
}

- (id)init;
- (id)initWithId:(id)proxy;
- (void)paintRowWithRAREaTableBasedView_RowView:(RAREaTableBasedView_RowView *)view
                          withRAREAppleGraphics:(RAREAppleGraphics *)g
                     withRARERenderableDataItem:(RARERenderableDataItem *)item
                            withRAREUIRectangle:(RAREUIRectangle *)rect
                              withRAREiTreeItem:(id<RAREiTreeItem>)ti;
- (void)renderItemWithInt:(int)row
withRARERenderableDataItem:(RARERenderableDataItem *)item
withRAREaTableBasedView_RowView:(RAREaTableBasedView_RowView *)view
              withBoolean:(BOOL)isSelected
              withBoolean:(BOOL)hasFocus
        withRAREiTreeItem:(id<RAREiTreeItem>)ti;
- (void)setAutoScrollOnExpansionWithBoolean:(BOOL)autoScrollOnExpansion;
- (void)setExpandableStateLockedWithBoolean:(BOOL)locked;
- (void)setExpansionHandlerWithRAREiExpansionHandler:(id<RAREiExpansionHandler>)expansionHandler;
- (void)setIconsWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)checked
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)unchecked
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)indeterminate;
- (void)setIndentByWithInt:(int)indent;
- (void)setItemRendererWithRAREaTreeItemRenderer:(RAREaTreeItemRenderer *)lr;
- (void)setManageCheckboxSelectionWithBoolean:(BOOL)manageCheckboxSelection;
- (void)setRootNodeCollapsibleWithBoolean:(BOOL)collapsible;
- (void)setSelectionTypeWithRAREiListHandler_SelectionTypeEnum:(RAREiListHandler_SelectionTypeEnum *)type;
- (void)setShowRootWithBoolean:(BOOL)show;
- (void)setShowRootHandlesWithBoolean:(BOOL)show;
- (void)setSingleClickToggleWithBoolean:(BOOL)singleClickToggle;
- (void)setToggleOnTwistyOnlyWithBoolean:(BOOL)twistyOnly;
- (void)setParentItemsSelectableWithBoolean:(BOOL)parentItemsSelectable;
- (BOOL)isParentItemsSelectable;
- (void)setTreeIconsWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)expaneded
                    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)collapsed;
- (void)setTreeModelWithRAREDataItemTreeModel:(RAREDataItemTreeModel *)treeModel;
- (void)setTwistyMarginOfErrorWithInt:(int)twistyMarginOfError;
- (id<RAREiPlatformIcon>)getCollapsedIcon;
- (int)getCount;
- (id<RAREiPlatformIcon>)getExpandedIcon;
- (id<RAREiExpansionHandler>)getExpansionHandler;
- (int)getIndentWithInt:(int)row;
- (int)getIndentBy;
- (int)getIndicatorHeight;
- (int)getIndicatorWidth;
- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth;
- (id<RAREiTreeItem>)getTreeItemWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (RAREaDataItemTreeModel *)getTreeModel;
- (int)getTwistyMarginOfError;
- (BOOL)isAutoScrollOnExpansion;
- (BOOL)isExpandableStateLocked;
- (BOOL)isManageCheckboxSelection;
- (BOOL)isRootNodeCollapsible;
- (BOOL)isShowRoot;
- (BOOL)isShowRootHandles;
- (BOOL)isSingleClickToggle;
- (BOOL)isToggleOnTwistyOnly;
- (BOOL)checkForCellHotspotWithInt:(int)row
                         withFloat:(float)x
                         withFloat:(float)y
                         withFloat:(float)width
                         withFloat:(float)height;
- (RARECheckListManager *)createCheckListManager;
- (void)createHeader;
- (void)disposeEx;
- (BOOL)handleExpansionWithRAREiTreeItem:(id<RAREiTreeItem>)ti
                                 withInt:(int)position
                             withBoolean:(BOOL)userDriven;
- (void)loadIcons;
- (void)toggleCheckedStateWithInt:(int)row;
- (void)updateRenderInsetsForCheckBoxWithFloat:(float)left
                                     withFloat:(float)right;
- (BOOL)isOnCheckBoxWithFloat:(float)x
                    withFloat:(float)width
                      withInt:(int)indent;
- (BOOL)isSelectableWithInt:(int)row
                    withInt:(int)col
 withRARERenderableDataItem:(RARERenderableDataItem *)item;
- (BOOL)isSelectableWithInt:(int)row
 withRARERenderableDataItem:(RARERenderableDataItem *)item
          withRAREiTreeItem:(id<RAREiTreeItem>)ti;
- (BOOL)isTable;
- (BOOL)isTree;
+ (id)createTreeProxy;
- (int)getIndicatorWidthWithRAREUIImage:(RAREUIImage *)image;
- (void)copyAllFieldsTo:(RARETreeView *)other;
@end

J2OBJC_FIELD_SETTER(RARETreeView, collapsedIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RARETreeView, expandedIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RARETreeView, expansionHandler_, id<RAREiExpansionHandler>)
J2OBJC_FIELD_SETTER(RARETreeView, treeModel_, RAREaDataItemTreeModel *)

typedef RARETreeView ComAppnativaRarePlatformAppleUiViewTreeView;

#endif // _RARETreeView_H_
