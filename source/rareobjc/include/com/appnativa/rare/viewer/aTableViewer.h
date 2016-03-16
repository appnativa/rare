//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-table_and_tree/com/appnativa/rare/viewer/aTableViewer.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaTableViewer_H_
#define _RAREaTableViewer_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaUtilLinkedList;
@class RAREColumn;
@class RARERenderableDataItem;
@class RARESPOTTable;
@class RARESPOTViewer;
@class RAREUIPoint;
@class RAREUIRectangle;
@class RAREaListItemRenderer;
@class RAREaWidgetListener;
@class RAREiTableComponent_GridViewTypeEnum;
@class RAREiTableComponent_TableTypeEnum;
@class RAREiTreeHandler_EditingModeEnum;
@protocol JavaLangCharSequence;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol JavaUtilList;
@protocol RAREUTiFilter;
@protocol RAREUTiStringConverter;
@protocol RAREiContainer;
@protocol RAREiExpandedListener;
@protocol RAREiExpansionListener;
@protocol RAREiPlatformIcon;
@protocol RAREiTableComponent;
@protocol RAREiTableModel;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iTreeHandler.h"
#include "com/appnativa/rare/viewer/aListViewer.h"
#include "java/lang/Runnable.h"

@interface RAREaTableViewer : RAREaListViewer < RAREiTreeHandler > {
 @public
  BOOL columnSelectionAllowed_;
  RARERenderableDataItem *currentRow_;
  RAREiTableComponent_GridViewTypeEnum *gridViewType_;
  JavaUtilLinkedList *levelStack_;
  id<RAREiTableComponent> tableHandler_;
  id<RAREiTableModel> tableModel_;
  id<RAREiTreeHandler> treeHandler_;
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)addChildWithRARERenderableDataItem:(RARERenderableDataItem *)child;
- (id)getSelection;
- (void)addChildWithInt:(int)row
withRARERenderableDataItem:(RARERenderableDataItem *)child;
- (void)addChildWithRARERenderableDataItem:(RARERenderableDataItem *)item
                withRARERenderableDataItem:(RARERenderableDataItem *)child;
- (void)addChildrenWithInt:(int)row
          withJavaUtilList:(id<JavaUtilList>)children;
- (void)addChildrenWithRARERenderableDataItem:(RARERenderableDataItem *)row
                             withJavaUtilList:(id<JavaUtilList>)children;
- (void)addExpandedListenerWithRAREiExpandedListener:(id<RAREiExpandedListener>)l;
- (void)addExpansionListenerWithRAREiExpansionListener:(id<RAREiExpansionListener>)l;
- (void)addParsedRowWithRARERenderableDataItem:(RARERenderableDataItem *)row;
- (void)clearCellsWithIntArray:(IOSIntArray *)rows
                  withIntArray:(IOSIntArray *)cols
                   withBoolean:(BOOL)valueOnly;
- (void)clearRootNode;
- (void)clearSelectedCellsWithBoolean:(BOOL)valueOnly;
- (void)collapseAll;
- (void)collapseRowWithInt:(int)row;
- (void)collapseRowWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (RAREColumn *)createColumnWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (RAREColumn *)createColumnWithNSString:(NSString *)title;
- (RAREColumn *)createColumnWithNSString:(NSString *)title
                                 withInt:(int)type
                   withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (int)convertColumnModelIndexToViewWithInt:(int)index;
- (int)convertColumnViewIndexToModelWithInt:(int)index;
- (RAREColumn *)createColumnWithNSString:(NSString *)title
                                 withInt:(int)type
                            withNSString:(NSString *)icon;
- (RAREColumn *)createColumnWithNSString:(NSString *)title
                                  withId:(id)value
                                 withInt:(int)type
                                  withId:(id)data
                   withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (id<JavaUtilList>)createColumnsWithNSStringArray:(IOSObjectArray *)data;
- (id<JavaUtilList>)createColumnsWithNSStringArray:(IOSObjectArray *)data
                                           withInt:(int)pos
                                           withInt:(int)len;
- (RARERenderableDataItem *)createRowWithNSStringArray:(IOSObjectArray *)data;
- (RARERenderableDataItem *)createRowWithNSObjectArray:(IOSObjectArray *)data
                                               withInt:(int)pos
                                               withInt:(int)len;
- (RARERenderableDataItem *)createRowWithNSStringArray:(IOSObjectArray *)data
                                               withInt:(int)pos
                                               withInt:(int)len;
- (void)dispose;
- (void)editCellWithInt:(int)row
                withInt:(int)column;
- (void)expandAll;
- (void)expandRowWithInt:(int)row;
- (void)expandRowWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (BOOL)filterWithRAREUTiFilter:(id<RAREUTiFilter>)filter;
- (BOOL)filterWithInt:(int)col
    withRAREUTiFilter:(id<RAREUTiFilter>)filter;
- (BOOL)filterWithInt:(int)col
         withNSString:(NSString *)filter
          withBoolean:(BOOL)contains;
- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains
               withBoolean:(BOOL)nullPasses
               withBoolean:(BOOL)emptyPasses;
- (int)findWithInt:(int)col
 withRAREUTiFilter:(id<RAREUTiFilter>)filter
           withInt:(int)start;
- (int)findWithInt:(int)col
      withNSString:(NSString *)filter
       withBoolean:(BOOL)contains;
- (int)findWithInt:(int)col
      withNSString:(NSString *)filter
           withInt:(int)start
       withBoolean:(BOOL)contains;
- (void)finishedParsing;
- (void)handleGroupedCollectionWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (void)handleGroupedCollectionWithJavaUtilCollection:(id<JavaUtilCollection>)collection
                                          withBoolean:(BOOL)appendCounts;
- (void)moveColumnWithInt:(int)column
                  withInt:(int)targetColumn;
- (void)refreshItems;
- (void)removeExpandedListenerWithRAREiExpandedListener:(id<RAREiExpandedListener>)l;
- (void)removeExpansionListenerWithRAREiExpansionListener:(id<RAREiExpansionListener>)l;
- (void)resetDataWithJavaUtilList:(id<JavaUtilList>)rows;
- (void)resetTableWithJavaUtilList:(id<JavaUtilList>)columns;
- (void)resetTableWithJavaUtilList:(id<JavaUtilList>)columns
                  withJavaUtilList:(id<JavaUtilList>)rows;
- (void)rowChangedWithInt:(int)index;
- (void)gridViewItemChangedWithInt:(int)index;
- (void)gridViewItemChangedWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)rowChangedWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)rowsChangedWithIntArray:(IOSIntArray *)rows;
- (void)rowsChangedWithInt:(int)firstRow
                   withInt:(int)lastRow;
- (void)sizeColumnToFitWithInt:(int)col;
- (void)sizeColumnsToFit;
- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (void)sortWithInt:(int)col
        withBoolean:(BOOL)descending;
- (void)sortWithInt:(int)col
        withBoolean:(BOOL)descending
        withBoolean:(BOOL)useLinkedData;
- (void)sortExWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (void)sortExWithInt:(int)col
          withBoolean:(BOOL)descending;
- (void)sortExWithInt:(int)col
          withBoolean:(BOOL)descending
          withBoolean:(BOOL)useLinkedData;
- (void)startedParsing;
- (void)toggleRowWithInt:(int)row;
- (BOOL)unfilter;
- (void)setActiveColumnWithInt:(int)col;
- (void)setAutoScrollOnExpansionWithBoolean:(BOOL)autoScrollOnExpansion;
- (void)setColumnIconWithInt:(int)col
       withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setColumnNameWithInt:(int)col
                withNSString:(NSString *)name;
- (void)setColumnTitleWithInt:(int)col
                 withNSString:(NSString *)title;
- (void)setColumnVisibleWithInt:(int)col
                    withBoolean:(BOOL)visible;
- (void)setConverterWithRAREUTiStringConverter:(id<RAREUTiStringConverter>)converter;
- (void)setEditingModeWithRAREiTreeHandler_EditingModeEnum:(RAREiTreeHandler_EditingModeEnum *)mode;
- (void)setExpandableStateLockedWithBoolean:(BOOL)locked;
- (void)setFromHTTPFormValueWithId:(id)value;
- (void)setGridViewTypeWithId:(id)type;
- (void)setHideFilteredEmptyBranchesWithBoolean:(BOOL)hide;
- (void)setIndentByWithInt:(int)indent;
- (void)setRawRowsWithJavaUtilList:(id<JavaUtilList>)rows;
- (void)setRootNodeCollapsibleWithBoolean:(BOOL)collapsible;
- (void)setRowHeightWithInt:(int)row
                    withInt:(int)height;
- (void)setSelectedColumnIndexWithInt:(int)index;
- (void)setSelectedIndexWithInt:(int)index;
- (int)getGridViewSelectedPoistion;
- (void)setGridViewSelectedPositionWithInt:(int)index;
- (void)setSelectedItemWithRARERenderableDataItem:(RARERenderableDataItem *)value;
- (void)setSelectedColumnIndicesWithIntArray:(IOSIntArray *)indices;
- (void)setShowRootHandlesWithBoolean:(BOOL)show;
- (void)setShowRootNodeWithBoolean:(BOOL)show;
- (void)setSingleClickToggleWithBoolean:(BOOL)singleClickToggle;
- (void)setSortColumnWithInt:(int)col
                 withBoolean:(BOOL)descending;
- (void)setToggleOnTwistyOnlyWithBoolean:(BOOL)twistyOnly;
- (void)setParentItemsSelectableWithBoolean:(BOOL)parentItemsSelectable;
- (BOOL)isParentItemsSelectable;
- (void)setExpandAllWithBoolean:(BOOL)expandAll;
- (void)setTreeEventsEnabledWithBoolean:(BOOL)enabled;
- (void)setTwistyMarginOfErrorWithInt:(int)twistyMarginOfError;
- (void)setValueAtWithInt:(int)row
                  withInt:(int)col
                   withId:(id)value;
- (id<JavaUtilList>)getChildRowsWithJavaUtilList:(id<JavaUtilList>)list;
- (int)getColumnIndexAtWithRAREUIPoint:(RAREUIPoint *)p;
- (RAREColumn *)getColumnWithInt:(int)col;
- (int)getColumnCount;
- (int)getColumnIndexAtWithFloat:(float)x
                       withFloat:(float)y;
- (NSString *)getColumnNameWithInt:(int)col;
- (id<JavaLangCharSequence>)getColumnTitleWithInt:(int)col;
- (id<JavaUtilList>)getColumns;
- (id<RAREUTiStringConverter>)getConverter;
- (id<JavaUtilList>)getDisplayColumns;
- (int)getEditingColumn;
- (int)getExpandableColumn;
- (int)getFirstVisibleColumnIndex;
- (int)getLastVisibleColumnIndex;
- (RAREiTableComponent_GridViewTypeEnum *)getGridViewType;
- (id)getHTTPFormValue;
- (RARERenderableDataItem *)getItemWithInt:(int)row
                                   withInt:(int)col;
- (RARERenderableDataItem *)getItemAtWithInt:(int)row
                                     withInt:(int)col;
- (RAREUIRectangle *)getItemBoundsWithInt:(int)row
                                  withInt:(int)col;
- (RARERenderableDataItem *)getParentWithInt:(int)index;
- (int)getParentIndexWithInt:(int)index;
- (id<JavaUtilList>)getRawRows;
- (RARERenderableDataItem *)getRootItem;
- (int)getSelectedColumn;
- (RARERenderableDataItem *)getSelectedItem;
- (id)getSelectionWithInt:(int)col;
- (NSString *)getSelectionAsStringWithInt:(int)col;
- (id)getSelectionDataWithInt:(int)col;
- (NSString *)getSelectionDataAsStringWithInt:(int)col;
- (IOSObjectArray *)getSelections;
- (IOSObjectArray *)getSelectionsAsStrings;
- (IOSObjectArray *)getSelectionsDataAsStrings;
- (int)getSortColumn;
- (id<RAREiTableComponent>)getTableComponent;
- (id<RAREiTreeHandler>)getTreeHandler;
- (int)getTwistyMarginOfError;
- (id)getValueAtWithInt:(int)row
                withInt:(int)col;
- (void)convertWithInt:(int)col
withRARERenderableDataItem:(RARERenderableDataItem *)item;
- (BOOL)isAutoScrollOnExpansion;
- (BOOL)isExpandableStateLocked;
- (BOOL)isFiltered;
- (BOOL)isHideFilteredEmptyBranches;
- (BOOL)isItemEditableWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (BOOL)isLeafItemWithInt:(int)index;
- (BOOL)isRootNodeCollapsible;
- (BOOL)isRowExpandedWithInt:(int)row;
- (BOOL)isRowExpandedWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (BOOL)isSingleClickToggle;
- (BOOL)isSortDescending;
- (BOOL)isTabular;
- (BOOL)isToggleOnTwistyOnly;
- (BOOL)isTreeTable;
- (void)addTreeRowWithRARERenderableDataItem:(RARERenderableDataItem *)row;
- (void)configureExWithRARESPOTTable:(RARESPOTTable *)cfg;
- (void)convertRowsWithIntArray:(IOSIntArray *)sels;
- (void)fixColumnsWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)l OBJC_METHOD_FAMILY_NONE;
- (RARERenderableDataItem *)getConvertedRowWithInt:(int)row;
- (IOSObjectArray *)getSelectionsAsStringsWithBoolean:(BOOL)data;
- (NSString *)getWidgetAttributeWithNSString:(NSString *)name;
- (void)adjustMultiTableRendererWithRAREaListItemRenderer:(RAREaListItemRenderer *)lr
                    withRAREiTableComponent_TableTypeEnum:(RAREiTableComponent_TableTypeEnum *)type;
- (int)getMiltiTableConfigurationTypeWithRARESPOTTable:(RARESPOTTable *)cfg;
- (IOSObjectArray *)toStringWithId:(id)o;
- (id<JavaUtilComparator>)getComparatorWithInt:(int)col
                                   withBoolean:(BOOL)descending;
- (void)copyAllFieldsTo:(RAREaTableViewer *)other;
@end

J2OBJC_FIELD_SETTER(RAREaTableViewer, currentRow_, RARERenderableDataItem *)
J2OBJC_FIELD_SETTER(RAREaTableViewer, gridViewType_, RAREiTableComponent_GridViewTypeEnum *)
J2OBJC_FIELD_SETTER(RAREaTableViewer, levelStack_, JavaUtilLinkedList *)
J2OBJC_FIELD_SETTER(RAREaTableViewer, tableHandler_, id<RAREiTableComponent>)
J2OBJC_FIELD_SETTER(RAREaTableViewer, tableModel_, id<RAREiTableModel>)
J2OBJC_FIELD_SETTER(RAREaTableViewer, treeHandler_, id<RAREiTreeHandler>)

typedef RAREaTableViewer ComAppnativaRareViewerATableViewer;

@interface RAREaTableViewer_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREaTableViewer *this$0_;
  id<JavaUtilList> val$rows_;
}

- (void)run;
- (id)initWithRAREaTableViewer:(RAREaTableViewer *)outer$
              withJavaUtilList:(id<JavaUtilList>)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREaTableViewer_$1, this$0_, RAREaTableViewer *)
J2OBJC_FIELD_SETTER(RAREaTableViewer_$1, val$rows_, id<JavaUtilList>)

#endif // _RAREaTableViewer_H_
