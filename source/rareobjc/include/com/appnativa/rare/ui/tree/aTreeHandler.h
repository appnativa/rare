//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-table_and_tree/com/appnativa/rare/ui/tree/aTreeHandler.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaTreeHandler_H_
#define _RAREaTreeHandler_H_

@class RAREDataItemTreeModel;
@class RAREEventListenerList;
@class RAREExpansionEvent;
@class RARERenderableDataItem;
@class RAREiTreeHandler_EditingModeEnum;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol JavaUtilList;
@protocol RAREUTiFilter;
@protocol RAREUTiStringConverter;
@protocol RAREiExpandedListener;
@protocol RAREiExpansionListener;
@protocol RAREiTree;
@protocol RAREiTreeItem;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iTreeHandler.h"
#include "com/appnativa/rare/ui/tree/iExpansionHandler.h"

@interface RAREaTreeHandler : NSObject < RAREiTreeHandler, RAREiExpansionHandler > {
 @public
  BOOL hasExpandedListener_;
  BOOL hasExpansionListener_;
  id<RAREiTree> theTree_;
  RAREDataItemTreeModel *treeModel_;
}

- (id)initWithRAREiTree:(id<RAREiTree>)list
withRAREDataItemTreeModel:(RAREDataItemTreeModel *)lm;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)addChildWithRARERenderableDataItem:(RARERenderableDataItem *)child;
- (void)addChildWithInt:(int)row
withRARERenderableDataItem:(RARERenderableDataItem *)child;
- (BOOL)isExpandableStateLocked;
- (void)setExpandableStateLockedWithBoolean:(BOOL)locked;
- (void)addChildWithRARERenderableDataItem:(RARERenderableDataItem *)item
                          withJavaUtilList:(id<JavaUtilList>)children;
- (void)addChildWithRARERenderableDataItem:(RARERenderableDataItem *)item
                withRARERenderableDataItem:(RARERenderableDataItem *)child;
- (void)addChildExWithRARERenderableDataItem:(RARERenderableDataItem *)child;
- (void)addChildrenWithInt:(int)row
          withJavaUtilList:(id<JavaUtilList>)children;
- (void)addChildrenWithRARERenderableDataItem:(RARERenderableDataItem *)row
                             withJavaUtilList:(id<JavaUtilList>)children;
- (void)addExpandedListenerWithRAREiExpandedListener:(id<RAREiExpandedListener>)l;
- (void)addExpansionListenerWithRAREiExpansionListener:(id<RAREiExpansionListener>)l;
- (void)clearRootNode;
- (void)collapseAll;
- (void)collapseRowWithInt:(int)row;
- (void)collapseRowWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)dispose;
- (void)expandAll;
- (void)expandRowWithInt:(int)row;
- (void)expandRowWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (BOOL)filterWithRAREUTiFilter:(id<RAREUTiFilter>)filter;
- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains;
- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains
               withBoolean:(BOOL)nullPasses
               withBoolean:(BOOL)emptyPasses;
- (void)refreshItems;
- (void)removeExpandedListenerWithRAREiExpandedListener:(id<RAREiExpandedListener>)l;
- (void)removeExpansionListenerWithRAREiExpansionListener:(id<RAREiExpansionListener>)l;
- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (void)sortExWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (int)toggleExpansionWithRAREiTree:(id<RAREiTree>)view
                  withRAREiTreeItem:(id<RAREiTreeItem>)ti
                            withInt:(int)listModelIndex;
- (void)toggleRowWithInt:(int)row;
- (BOOL)unfilter;
- (BOOL)setAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (void)setConverterWithRAREUTiStringConverter:(id<RAREUTiStringConverter>)converter;
- (void)setEditingModeWithRAREiTreeHandler_EditingModeEnum:(RAREiTreeHandler_EditingModeEnum *)valueOf;
- (void)setExpandableColumnWithInt:(int)expandableColumn;
- (void)setIndentByWithInt:(int)indent;
- (void)setRootNodeCollapsibleWithBoolean:(BOOL)collapsible;
- (void)setShowRootHandlesWithBoolean:(BOOL)show;
- (void)setShowRootNodeWithBoolean:(BOOL)show;
- (void)setSingleClickToggleWithBoolean:(BOOL)singleClickToggle;
- (void)setToggleOnTwistyOnlyWithBoolean:(BOOL)twistyOnly;
- (void)setTwistyMarginOfErrorWithInt:(int)twistyMarginOfError;
- (id<RAREUTiStringConverter>)getConverter;
- (int)getExpandableColumn;
- (RARERenderableDataItem *)getParentWithInt:(int)index;
- (RARERenderableDataItem *)getRootItem;
- (int)getTwistyMarginOfError;
- (BOOL)isFiltered;
- (BOOL)isItemEditableWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (BOOL)isLeafItemWithInt:(int)index;
- (BOOL)isRootNodeCollapsible;
- (BOOL)isRowExpandedWithInt:(int)row;
- (BOOL)isRowExpandedWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (BOOL)isSingleClickToggle;
- (BOOL)isTabular;
- (BOOL)isToggleOnTwistyOnly;
- (RAREEventListenerList *)getEventListenerList;
- (BOOL)hasListeners;
- (id<JavaUtilList>)getRawRows;
- (void)fireExpandedWithRAREExpansionEvent:(RAREExpansionEvent *)e
                               withBoolean:(BOOL)expand;
- (void)fireExpansionWithRAREExpansionEvent:(RAREExpansionEvent *)e
                                withBoolean:(BOOL)expand;
- (int)indexOfInListWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (RARERenderableDataItem *)getWithInt:(int)row;
- (BOOL)isEventsEnabled;
- (void)setTreeEventsEnabledWithBoolean:(BOOL)enabled;
- (BOOL)isAutoScrollOnExpansion;
- (void)setAutoScrollOnExpansionWithBoolean:(BOOL)param0;
- (void)copyAllFieldsTo:(RAREaTreeHandler *)other;
@end

J2OBJC_FIELD_SETTER(RAREaTreeHandler, theTree_, id<RAREiTree>)
J2OBJC_FIELD_SETTER(RAREaTreeHandler, treeModel_, RAREDataItemTreeModel *)

typedef RAREaTreeHandler ComAppnativaRareUiTreeATreeHandler;

#endif // _RAREaTreeHandler_H_
