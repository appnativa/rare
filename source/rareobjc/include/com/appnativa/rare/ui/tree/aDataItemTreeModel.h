//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-table_and_tree/com/appnativa/rare/ui/tree/aDataItemTreeModel.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaDataItemTreeModel_H_
#define _RAREaDataItemTreeModel_H_

@class IOSClass;
@class IOSIntArray;
@class IOSObjectArray;
@class RAREColumn;
@class RAREEventListenerList;
@class RARERenderableDataItem;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol JavaUtilList;
@protocol RAREUTiFilter;
@protocol RAREiItemChangeListener;
@protocol RAREiTreeItem;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/util/iFilterableList.h"
#include "com/appnativa/util/iStringConverter.h"
#include "java/util/AbstractList.h"

@interface RAREaDataItemTreeModel : JavaUtilAbstractList < RAREUTiFilterableList, RAREUTiStringConverter > {
 @public
  int expandableColumn_;
  RARERenderableDataItem *rootNode_;
  RAREEventListenerList *listenerList_;
  BOOL eventsEnabled_;
  BOOL asksAllowsChildren__;
  RAREColumn *columnDescription_;
  id<JavaUtilComparator> comparator_;
  id<RAREUTiStringConverter> converter_;
  BOOL expandAll__;
  id<RAREUTiFilter> lastFilter_;
  id<RAREiTreeItem> rootItem_;
  BOOL sorting_;
  id<RAREiWidget> theWidget_;
  BOOL hideBarenBranchesWhenFiltered_;
}

- (id)init;
- (id)initWithRARERenderableDataItem:(RARERenderableDataItem *)root;
- (id)initWithRARERenderableDataItem:(RARERenderableDataItem *)root
                         withBoolean:(BOOL)asksAllowsChildren;
- (BOOL)addWithId:(RARERenderableDataItem *)child;
- (void)addWithInt:(int)index
            withId:(RARERenderableDataItem *)child;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)addAllWithInt:(int)index
withJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)addExWithRARERenderableDataItem:(RARERenderableDataItem *)child;
- (void)addIndexToFilteredListWithInt:(int)index;
- (void)addItemChangeListenerWithRAREiItemChangeListener:(id<RAREiItemChangeListener>)l;
- (void)addNodeWithRARERenderableDataItem:(RARERenderableDataItem *)child
               withRARERenderableDataItem:(RARERenderableDataItem *)parent;
- (void)addNodesWithJavaUtilList:(id<JavaUtilList>)children
      withRARERenderableDataItem:(RARERenderableDataItem *)parent;
- (void)addNodesWithInt:(int)index
       withJavaUtilList:(id<JavaUtilList>)children
withRARERenderableDataItem:(RARERenderableDataItem *)parent;
- (void)addNodesWithRARERenderableDataItemArray:(IOSObjectArray *)child
                                        withInt:(int)count
                     withRARERenderableDataItem:(RARERenderableDataItem *)parent;
- (void)addToFilteredListWithId:(RARERenderableDataItem *)child;
- (void)addToFilteredListWithInt:(int)index
                          withId:(RARERenderableDataItem *)child;
- (BOOL)asksAllowsChildren;
- (void)clear;
- (void)clearEx;
- (void)collapseAll;
- (id<JavaUtilList>)concatWithJavaUtilListArray:(IOSObjectArray *)e;
- (void)dispose;
- (void)ensureCapacityWithInt:(int)capacity;
- (void)expandAll;
- (BOOL)filterWithRAREUTiFilter:(id<RAREUTiFilter>)filter;
- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains;
- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains
               withBoolean:(BOOL)nullPasses
               withBoolean:(BOOL)emptyPasses;
- (int)findWithRAREUTiFilter:(id<RAREUTiFilter>)filter
                     withInt:(int)start;
- (void)setFilteredListWithJavaUtilList:(id<JavaUtilList>)list
                      withRAREUTiFilter:(id<RAREUTiFilter>)lastFilter;
- (int)findWithNSString:(NSString *)filter
                withInt:(int)start
            withBoolean:(BOOL)contains;
- (void)insertNodeIntoWithInt:(int)index
   withRARERenderableDataItem:(RARERenderableDataItem *)child
   withRARERenderableDataItem:(RARERenderableDataItem *)parent;
- (NSString *)joinWithNSString:(NSString *)sep;
- (void)moveWithInt:(int)from
            withInt:(int)to;
- (void)nodeChangedWithRARERenderableDataItem:(RARERenderableDataItem *)node;
- (void)nodeStructureChangedWithRARERenderableDataItem:(RARERenderableDataItem *)node;
- (void)nodesChangedWithRARERenderableDataItem:(RARERenderableDataItem *)node
                                  withIntArray:(IOSIntArray *)childIndices;
- (void)nodesWereInsertedWithRARERenderableDataItem:(RARERenderableDataItem *)node
                                       withIntArray:(IOSIntArray *)childIndices;
- (void)nodesWereRemovedWithRARERenderableDataItem:(RARERenderableDataItem *)node
                                      withIntArray:(IOSIntArray *)childIndices
                                 withNSObjectArray:(IOSObjectArray *)removedChildren;
- (RARERenderableDataItem *)pop;
- (void)populateWithRARERenderableDataItemArray:(IOSObjectArray *)child
                                        withInt:(int)count
                     withRARERenderableDataItem:(RARERenderableDataItem *)parent;
- (void)populateRootWithRARERenderableDataItemArray:(IOSObjectArray *)child
                                            withInt:(int)count;
- (void)pushWithNSObjectArray:(IOSObjectArray *)value;
- (BOOL)refilter;
- (void)refreshItems;
- (void)reloadWithRARERenderableDataItem:(RARERenderableDataItem *)node;
- (RARERenderableDataItem *)removeWithInt:(int)index;
- (void)removeItemChangeListenerWithRAREiItemChangeListener:(id<RAREiItemChangeListener>)l;
- (void)removeNodeWithInt:(int)index
withRARERenderableDataItem:(RARERenderableDataItem *)parent;
- (void)removeNodeFromParentWithRARERenderableDataItem:(RARERenderableDataItem *)node;
- (void)removeRowsWithIntArray:(IOSIntArray *)indexes;
- (id<JavaUtilList>)reverse;
- (RARERenderableDataItem *)shift;
- (int)size;
- (id<JavaUtilList>)sliceWithInt:(int)start;
- (id<JavaUtilList>)sliceWithInt:(int)start
                         withInt:(int)end;
- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (id<JavaUtilList>)sortExWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (id<JavaUtilList>)spliceWithInt:(int)index
                          withInt:(int)howMany;
- (id<JavaUtilList>)spliceWithInt:(int)index
                          withInt:(int)howMany
                withNSObjectArray:(IOSObjectArray *)e;
- (id<JavaUtilList>)spliceListWithInt:(int)index
                              withInt:(int)howMany
                     withJavaUtilList:(id<JavaUtilList>)e;
- (void)structureChanged;
- (void)swapWithInt:(int)index1
            withInt:(int)index2;
- (void)trimToSize;
- (NSString *)toStringWithId:(id)item;
- (void)unshiftWithId:(RARERenderableDataItem *)value;
- (RARERenderableDataItem *)setWithInt:(int)index
                                withId:(RARERenderableDataItem *)element;
- (BOOL)setAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)setAsksAllowsChildrenWithBoolean:(BOOL)ask;
- (void)setConverterWithRAREUTiStringConverter:(id<RAREUTiStringConverter>)converter;
- (void)setEventsEnabledWithBoolean:(BOOL)enabled;
- (void)setExpandAllWithBoolean:(BOOL)expandAll;
- (void)setExpandableColumnWithInt:(int)expandableColumn;
- (void)setHideBarenBranchesWhenFilteredWithBoolean:(BOOL)hideBarenBranchesWhenFiltered;
- (void)setItemDescriptionWithRAREColumn:(RAREColumn *)column;
- (void)setRootWithRARERenderableDataItem:(RARERenderableDataItem *)root;
- (void)setWidgetWithRAREiWidget:(id<RAREiWidget>)widget;
- (RARERenderableDataItem *)getWithInt:(int)index;
- (id)getChildWithRARERenderableDataItem:(RARERenderableDataItem *)parent
                                 withInt:(int)index;
- (int)getChildCountWithRARERenderableDataItem:(RARERenderableDataItem *)parent;
- (id<RAREUTiStringConverter>)getConverter;
- (int)getExpandableColumn;
- (id<JavaUtilList>)getFilteredList;
- (id<RAREUTiStringConverter>)getFilteringConverter;
- (int)getIndexOfChildWithRARERenderableDataItem:(RARERenderableDataItem *)parent
                      withRARERenderableDataItem:(RARERenderableDataItem *)child;
- (RAREColumn *)getItemDescription;
- (id<JavaUtilComparator>)getLastComparator;
- (id<RAREUTiFilter>)getLastFilter;
- (IOSObjectArray *)getListenersWithIOSClass:(IOSClass *)listenerType;
- (id<JavaUtilList>)getRawRows;
- (RARERenderableDataItem *)getRoot;
- (id<RAREiTreeItem>)getTreeItemWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (id<RAREiTreeItem>)getTreeItemWithRARERenderableDataItem:(RARERenderableDataItem *)item
                                         withRAREiTreeItem:(id<RAREiTreeItem>)parent
                                               withBoolean:(BOOL)create;
- (id<JavaUtilList>)getUnfilteredList;
- (BOOL)isEventsEnabled;
- (BOOL)isFiltered;
- (BOOL)isHideBarenBranchesWhenFiltered;
- (BOOL)isLeafWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)addFilteredListNodeWithRARERenderableDataItem:(RARERenderableDataItem *)child
                           withRARERenderableDataItem:(RARERenderableDataItem *)parent
                                              withInt:(int)index;
- (id<RAREiTreeItem>)createTreeItemWithRARERenderableDataItem:(RARERenderableDataItem *)item
                                            withRAREiTreeItem:(id<RAREiTreeItem>)parent;
- (void)fireRootChanged;
- (void)fireTreeNodesChangedWithRARERenderableDataItem:(RARERenderableDataItem *)parent
                                          withIntArray:(IOSIntArray *)childIndices;
- (void)fireTreeNodesInsertedWithRARERenderableDataItem:(RARERenderableDataItem *)parent
                                           withIntArray:(IOSIntArray *)childIndices;
- (void)fireTreeNodesRemovedWithRARERenderableDataItem:(RARERenderableDataItem *)parent
                                          withIntArray:(IOSIntArray *)childIndices
                                     withNSObjectArray:(IOSObjectArray *)children;
- (void)fireTreeStructureChangedWithRARERenderableDataItem:(RARERenderableDataItem *)parent;
- (void)needsFiltering;
- (void)needsSorting;
- (void)setupRootItem;
- (RARERenderableDataItem *)getExpandableItemWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (RARERenderableDataItem *)getExpandableItemExWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)convertWithRAREiWidget:(id<RAREiWidget>)w
                withRAREColumn:(RAREColumn *)col
    withRARERenderableDataItem:(RARERenderableDataItem *)di;
- (IOSIntArray *)indicesWithInt:(int)len1
                        withInt:(int)len2;
- (BOOL)unfilter;
- (void)copyAllFieldsTo:(RAREaDataItemTreeModel *)other;
@end

J2OBJC_FIELD_SETTER(RAREaDataItemTreeModel, rootNode_, RARERenderableDataItem *)
J2OBJC_FIELD_SETTER(RAREaDataItemTreeModel, listenerList_, RAREEventListenerList *)
J2OBJC_FIELD_SETTER(RAREaDataItemTreeModel, columnDescription_, RAREColumn *)
J2OBJC_FIELD_SETTER(RAREaDataItemTreeModel, comparator_, id<JavaUtilComparator>)
J2OBJC_FIELD_SETTER(RAREaDataItemTreeModel, converter_, id<RAREUTiStringConverter>)
J2OBJC_FIELD_SETTER(RAREaDataItemTreeModel, lastFilter_, id<RAREUTiFilter>)
J2OBJC_FIELD_SETTER(RAREaDataItemTreeModel, rootItem_, id<RAREiTreeItem>)
J2OBJC_FIELD_SETTER(RAREaDataItemTreeModel, theWidget_, id<RAREiWidget>)

typedef RAREaDataItemTreeModel ComAppnativaRareUiTreeADataItemTreeModel;

#endif // _RAREaDataItemTreeModel_H_
