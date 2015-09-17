//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-table_and_tree/com/appnativa/rare/ui/tree/aDataItemTreeModel.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/ui/Column.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/event/EventListenerList.h"
#include "com/appnativa/rare/ui/event/iItemChangeListener.h"
#include "com/appnativa/rare/ui/tree/aDataItemTreeModel.h"
#include "com/appnativa/rare/ui/tree/iTreeItem.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/util/FilterableList.h"
#include "com/appnativa/util/iFilter.h"
#include "com/appnativa/util/iStringConverter.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/lang/System.h"
#include "java/lang/Throwable.h"
#include "java/lang/UnsupportedOperationException.h"
#include "java/util/ArrayList.h"
#include "java/util/Arrays.h"
#include "java/util/Collection.h"
#include "java/util/Comparator.h"
#include "java/util/List.h"

@implementation RAREaDataItemTreeModel

- (id)initRAREaDataItemTreeModel {
  if (self = [super init]) {
    expandableColumn_ = -1;
    rootNode_ = [[RARERenderableDataItem alloc] initWithId:@"Root Node"];
    listenerList_ = [[RAREEventListenerList alloc] init];
    eventsEnabled_ = YES;
    asksAllowsChildren__ = YES;
    hideBarenBranchesWhenFiltered_ = YES;
    [rootNode_ setConverterWithRAREUTiStringConverter:self];
  }
  return self;
}

- (id)init {
  return [self initRAREaDataItemTreeModel];
}

- (id)initWithRARERenderableDataItem:(RARERenderableDataItem *)root {
  if (self = [self initRAREaDataItemTreeModel]) {
    if (root != nil) {
      [((RARERenderableDataItem *) nil_chk(rootNode_)) copy__WithRARERenderableDataItem:root];
      [rootNode_ setConverterWithRAREUTiStringConverter:self];
    }
  }
  return self;
}

- (id)initWithRARERenderableDataItem:(RARERenderableDataItem *)root
                         withBoolean:(BOOL)asksAllowsChildren {
  if (self = [self initRAREaDataItemTreeModel]) {
    if (root != nil) {
      RARERenderableDataItem *list = [self getExpandableItemWithRARERenderableDataItem:rootNode_];
      [((RARERenderableDataItem *) nil_chk(list)) copy__WithRARERenderableDataItem:root];
      [list setConverterWithRAREUTiStringConverter:self];
    }
    self->asksAllowsChildren__ = asksAllowsChildren;
  }
  return self;
}

- (BOOL)addWithId:(RARERenderableDataItem *)child {
  [self addNodeWithRARERenderableDataItem:child withRARERenderableDataItem:rootNode_];
  return YES;
}

- (void)addWithInt:(int)index
            withId:(RARERenderableDataItem *)child {
  [self insertNodeIntoWithInt:index withRARERenderableDataItem:child withRARERenderableDataItem:rootNode_];
}

- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)c {
  int len1 = [self size];
  @synchronized (rootNode_) {
    [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) addAllWithJavaUtilCollection:c];
  }
  int len2 = [self size];
  if (len2 > len1) {
    [self nodesWereInsertedWithRARERenderableDataItem:rootNode_ withIntArray:[self indicesWithInt:len1 withInt:len2]];
  }
  return NO;
}

- (BOOL)addAllWithInt:(int)index
withJavaUtilCollection:(id<JavaUtilCollection>)c {
  int len1 = [self size];
  @synchronized (rootNode_) {
    [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) addAllWithInt:index withJavaUtilCollection:c];
  }
  int len2 = [self size];
  if (len1 != len2) {
    [self nodesWereInsertedWithRARERenderableDataItem:rootNode_ withIntArray:[self indicesWithInt:len1 withInt:len2]];
    return YES;
  }
  return NO;
}

- (void)addExWithRARERenderableDataItem:(RARERenderableDataItem *)child {
  [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) addWithId:child];
}

- (void)addIndexToFilteredListWithInt:(int)index {
  int len = [self size];
  if (index == -1) {
    @synchronized (rootNode_) {
      [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) addIndexToFilteredListWithInt:index];
    }
    if (len > 0) {
      [self nodeStructureChangedWithRARERenderableDataItem:rootNode_];
    }
    return;
  }
  if ((index < 0) || (index >= len)) {
    return;
  }
  [self addToFilteredListWithId:[self getWithInt:index]];
}

- (void)addItemChangeListenerWithRAREiItemChangeListener:(id<RAREiItemChangeListener>)l {
  [((RAREEventListenerList *) nil_chk(listenerList_)) addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiItemChangeListener)] withId:l];
}

- (void)addNodeWithRARERenderableDataItem:(RARERenderableDataItem *)child
               withRARERenderableDataItem:(RARERenderableDataItem *)parent {
  RARERenderableDataItem *list = [self getExpandableItemWithRARERenderableDataItem:parent];
  int len = [((RARERenderableDataItem *) nil_chk(list)) getItemCount];
  @synchronized (parent) {
    [list addWithId:child];
  }
  IOSIntArray *changed = [IOSIntArray arrayWithInts:(int[]){ len } count:1];
  [self nodesWereInsertedWithRARERenderableDataItem:parent withIntArray:changed];
}

- (void)addNodesWithJavaUtilList:(id<JavaUtilList>)children
      withRARERenderableDataItem:(RARERenderableDataItem *)parent {
  if (children == nil) {
    return;
  }
  int count = [((id<JavaUtilList>) nil_chk(children)) size];
  IOSIntArray *changed = [IOSIntArray arrayWithLength:count];
  int len = [((RARERenderableDataItem *) nil_chk(parent)) getItemCount];
  @synchronized (parent) {
    RARERenderableDataItem *list = [self getExpandableItemWithRARERenderableDataItem:parent];
    for (int i = 0; i < count; i++) {
      (*IOSIntArray_GetRef(changed, i)) = len + i;
      [((RARERenderableDataItem *) nil_chk(list)) addWithId:[children getWithInt:i]];
    }
  }
  [self nodesWereInsertedWithRARERenderableDataItem:parent withIntArray:changed];
}

- (void)addNodesWithInt:(int)index
       withJavaUtilList:(id<JavaUtilList>)children
withRARERenderableDataItem:(RARERenderableDataItem *)parent {
  if (children == nil) {
    return;
  }
  int count = [((id<JavaUtilList>) nil_chk(children)) size];
  IOSIntArray *changed = [IOSIntArray arrayWithLength:count];
  @synchronized (parent) {
    RARERenderableDataItem *list = [self getExpandableItemWithRARERenderableDataItem:parent];
    for (int i = 0; i < count; i++) {
      (*IOSIntArray_GetRef(changed, i)) = index + i;
      [((RARERenderableDataItem *) nil_chk(list)) addWithInt:index + i withId:[children getWithInt:i]];
    }
  }
  [self nodesWereInsertedWithRARERenderableDataItem:parent withIntArray:changed];
}

- (void)addNodesWithRARERenderableDataItemArray:(IOSObjectArray *)child
                                        withInt:(int)count
                     withRARERenderableDataItem:(RARERenderableDataItem *)parent {
  IOSIntArray *changed = [IOSIntArray arrayWithLength:count];
  @synchronized (parent) {
    RARERenderableDataItem *list = [self getExpandableItemWithRARERenderableDataItem:parent];
    int len = [((RARERenderableDataItem *) nil_chk(list)) getItemCount];
    for (int i = 0; i < count; i++) {
      (*IOSIntArray_GetRef(changed, i)) = len + i;
      [list addWithId:IOSObjectArray_Get(nil_chk(child), i)];
    }
  }
  [self nodesWereInsertedWithRARERenderableDataItem:parent withIntArray:changed];
}

- (void)addToFilteredListWithId:(RARERenderableDataItem *)child {
  [self addFilteredListNodeWithRARERenderableDataItem:child withRARERenderableDataItem:rootNode_ withInt:-1];
}

- (void)addToFilteredListWithInt:(int)index
                          withId:(RARERenderableDataItem *)child {
  [self addFilteredListNodeWithRARERenderableDataItem:child withRARERenderableDataItem:rootNode_ withInt:index];
}

- (BOOL)asksAllowsChildren {
  return asksAllowsChildren__;
}

- (void)clear {
  [self clearEx];
  [self structureChanged];
}

- (void)clearEx {
  if (rootNode_ != nil) {
    RARERenderableDataItem *list = [self getExpandableItemExWithRARERenderableDataItem:rootNode_];
    if (list != nil) {
      [list clearSubItemParents];
      if (list != rootNode_) {
        [list clear];
      }
      else {
        [list clearSubItems];
      }
    }
  }
}

- (void)collapseAll {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (id<JavaUtilList>)concatWithJavaUtilListArray:(IOSObjectArray *)e {
  return [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) concatWithJavaUtilListArray:e];
}

- (void)dispose {
  @try {
    if (listenerList_ != nil) {
      [listenerList_ clear];
      eventsEnabled_ = NO;
    }
    [self clearEx];
    if (rootItem_ != nil) {
      [rootItem_ dispose];
    }
  }
  @catch (JavaLangThrowable *e) {
    [RAREPlatform ignoreExceptionWithNSString:@"dispose exception" withJavaLangThrowable:e];
  }
  lastFilter_ = nil;
  comparator_ = nil;
  columnDescription_ = nil;
  listenerList_ = nil;
  theWidget_ = nil;
  rootNode_ = nil;
  converter_ = nil;
  rootItem_ = nil;
}

- (void)ensureCapacityWithInt:(int)capacity {
  [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) ensureCapacityWithInt:capacity];
}

- (void)expandAll {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (BOOL)filterWithRAREUTiFilter:(id<RAREUTiFilter>)filter {
  lastFilter_ = filter;
  [self needsFiltering];
  [self structureChanged];
  return YES;
}

- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains {
  RAREUTFilterableList *l = [[RAREUTFilterableList alloc] initWithJavaUtilList:[[JavaUtilArrayList alloc] initWithInt:1]];
  [l filterWithNSString:filter withBoolean:contains];
  return [self filterWithRAREUTiFilter:[l getLastFilter]];
}

- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains
               withBoolean:(BOOL)nullPasses
               withBoolean:(BOOL)emptyPasses {
  RAREUTFilterableList *l = [[RAREUTFilterableList alloc] initWithJavaUtilList:[[JavaUtilArrayList alloc] initWithInt:1]];
  [l filterWithNSString:filter withBoolean:contains withBoolean:nullPasses withBoolean:emptyPasses];
  return [self filterWithRAREUTiFilter:[l getLastFilter]];
}

- (int)findWithRAREUTiFilter:(id<RAREUTiFilter>)filter
                     withInt:(int)start {
  return [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) findWithRAREUTiFilter:filter withInt:start];
}

- (void)setFilteredListWithJavaUtilList:(id<JavaUtilList>)list
                      withRAREUTiFilter:(id<RAREUTiFilter>)lastFilter {
  @throw [[JavaLangUnsupportedOperationException alloc] initWithNSString:@"not supported on tree models"];
}

- (int)findWithNSString:(NSString *)filter
                withInt:(int)start
            withBoolean:(BOOL)contains {
  return [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) findWithNSString:filter withInt:start withBoolean:contains];
}

- (void)insertNodeIntoWithInt:(int)index
   withRARERenderableDataItem:(RARERenderableDataItem *)child
   withRARERenderableDataItem:(RARERenderableDataItem *)parent {
  @synchronized (parent) {
    RARERenderableDataItem *list = [self getExpandableItemWithRARERenderableDataItem:parent];
    int count = [((RARERenderableDataItem *) nil_chk(list)) getItemCount];
    if (count == 0) {
      [list addWithId:child];
    }
    else {
      [list addWithInt:index withId:child];
    }
  }
  IOSIntArray *changed = [IOSIntArray arrayWithInts:(int[]){ index } count:1];
  [self nodesWereInsertedWithRARERenderableDataItem:parent withIntArray:changed];
}

- (NSString *)joinWithNSString:(NSString *)sep {
  return [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) joinWithNSString:sep];
}

- (void)moveWithInt:(int)from
            withInt:(int)to {
  [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) moveWithInt:from withInt:to];
  [self nodeStructureChangedWithRARERenderableDataItem:rootNode_];
}

- (void)nodeChangedWithRARERenderableDataItem:(RARERenderableDataItem *)node {
  if (eventsEnabled_ && (listenerList_ != nil) && (node != nil)) {
    RARERenderableDataItem *parent = [node getParentItem];
    if (parent != nil) {
      RARERenderableDataItem *list = [self getExpandableItemWithRARERenderableDataItem:parent];
      int anIndex = [((RARERenderableDataItem *) nil_chk(list)) indexOfWithId:node];
      if (anIndex != -1) {
        IOSIntArray *cIndexs = [IOSIntArray arrayWithLength:1];
        (*IOSIntArray_GetRef(cIndexs, 0)) = anIndex;
        [self nodesChangedWithRARERenderableDataItem:parent withIntArray:cIndexs];
      }
    }
    else if (node == [self getRoot]) {
      [self nodesChangedWithRARERenderableDataItem:node withIntArray:nil];
    }
  }
}

- (void)nodeStructureChangedWithRARERenderableDataItem:(RARERenderableDataItem *)node {
  if (eventsEnabled_ && (node != nil)) {
    [self fireTreeStructureChangedWithRARERenderableDataItem:node];
  }
}

- (void)nodesChangedWithRARERenderableDataItem:(RARERenderableDataItem *)node
                                  withIntArray:(IOSIntArray *)childIndices {
  if (eventsEnabled_ && (node != nil)) {
    [self fireTreeNodesChangedWithRARERenderableDataItem:node withIntArray:childIndices];
  }
}

- (void)nodesWereInsertedWithRARERenderableDataItem:(RARERenderableDataItem *)node
                                       withIntArray:(IOSIntArray *)childIndices {
  if (eventsEnabled_ && (listenerList_ != nil) && (node != nil) && (childIndices != nil) && ((int) [childIndices count] > 0)) {
    int cCount = (int) [childIndices count];
    if (cCount == [node size]) {
      [self nodeStructureChangedWithRARERenderableDataItem:node];
    }
    else {
      [self fireTreeNodesInsertedWithRARERenderableDataItem:node withIntArray:childIndices];
    }
  }
}

- (void)nodesWereRemovedWithRARERenderableDataItem:(RARERenderableDataItem *)node
                                      withIntArray:(IOSIntArray *)childIndices
                                 withNSObjectArray:(IOSObjectArray *)removedChildren {
  if (eventsEnabled_ && (node != nil) && (childIndices != nil)) {
    if ([node size] == 0) {
      [self nodeStructureChangedWithRARERenderableDataItem:node];
    }
    else {
      [self fireTreeNodesRemovedWithRARERenderableDataItem:node withIntArray:childIndices withNSObjectArray:removedChildren];
    }
  }
}

- (RARERenderableDataItem *)pop {
  RARERenderableDataItem *list = [self getExpandableItemWithRARERenderableDataItem:rootNode_];
  return ([((RARERenderableDataItem *) nil_chk(list)) isEmpty]) ? nil : [list removeWithInt:[list size] - 1];
}

- (void)populateWithRARERenderableDataItemArray:(IOSObjectArray *)child
                                        withInt:(int)count
                     withRARERenderableDataItem:(RARERenderableDataItem *)parent {
  @synchronized (parent) {
    RARERenderableDataItem *list = [self getExpandableItemWithRARERenderableDataItem:parent];
    [((RARERenderableDataItem *) nil_chk(list)) setItemsWithRARERenderableDataItemArray:child withInt:count];
  }
  [self nodeStructureChangedWithRARERenderableDataItem:parent];
}

- (void)populateRootWithRARERenderableDataItemArray:(IOSObjectArray *)child
                                            withInt:(int)count {
  [self populateWithRARERenderableDataItemArray:child withInt:count withRARERenderableDataItem:rootNode_];
}

- (void)pushWithNSObjectArray:(IOSObjectArray *)value {
  if (value != nil) {
    [self addNodesWithRARERenderableDataItemArray:value withInt:(int) [value count] withRARERenderableDataItem:rootNode_];
  }
}

- (BOOL)refilter {
  return [self filterWithRAREUTiFilter:lastFilter_];
}

- (void)refreshItems {
  [self structureChanged];
}

- (void)reloadWithRARERenderableDataItem:(RARERenderableDataItem *)node {
  if (eventsEnabled_ && (node != nil)) {
    [self fireTreeStructureChangedWithRARERenderableDataItem:node];
  }
}

- (RARERenderableDataItem *)removeWithInt:(int)index {
  RARERenderableDataItem *di = [self getWithInt:index];
  [self removeNodeWithInt:index withRARERenderableDataItem:rootNode_];
  return di;
}

- (void)removeItemChangeListenerWithRAREiItemChangeListener:(id<RAREiItemChangeListener>)l {
  [((RAREEventListenerList *) nil_chk(listenerList_)) removeWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiItemChangeListener)] withId:l];
}

- (void)removeNodeWithInt:(int)index
withRARERenderableDataItem:(RARERenderableDataItem *)parent {
  IOSObjectArray *removed = nil;
  @synchronized (parent) {
    RARERenderableDataItem *list = [self getExpandableItemWithRARERenderableDataItem:parent];
    int len = [((RARERenderableDataItem *) nil_chk(list)) getItemCount];
    if ((index < 0) || (index >= len)) {
      return;
    }
    removed = [IOSObjectArray arrayWithLength:1 type:[IOSClass classWithClass:[RARERenderableDataItem class]]];
    (void) IOSObjectArray_Set(removed, 0, [list removeWithInt:index]);
  }
  IOSIntArray *changed = [IOSIntArray arrayWithInts:(int[]){ index } count:1];
  [self nodesWereRemovedWithRARERenderableDataItem:parent withIntArray:changed withNSObjectArray:removed];
}

- (void)removeNodeFromParentWithRARERenderableDataItem:(RARERenderableDataItem *)node {
  RARERenderableDataItem *parent = [((RARERenderableDataItem *) nil_chk(node)) getParentItem];
  if (parent == nil) {
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"node does not have a parent."];
  }
  RARERenderableDataItem *list = [self getExpandableItemWithRARERenderableDataItem:parent];
  int index = [((RARERenderableDataItem *) nil_chk(list)) indexOfWithId:node];
  if (index > -1) {
    IOSIntArray *childIndex = [IOSIntArray arrayWithInts:(int[]){ index } count:1];
    IOSObjectArray *removed = [IOSObjectArray arrayWithObjects:(id[]){ node } count:1 type:[IOSClass classWithClass:[NSObject class]]];
    @synchronized (parent) {
      (void) [list removeWithInt:index];
    }
    [self nodesWereRemovedWithRARERenderableDataItem:parent withIntArray:childIndex withNSObjectArray:removed];
  }
}

- (void)removeRowsWithIntArray:(IOSIntArray *)indexes {
  int len = (indexes == nil) ? 0 : (int) [indexes count];
  if (len == 0) {
    return;
  }
  IOSIntArray *copy_ = [IOSIntArray arrayWithLength:len];
  [JavaLangSystem arraycopyWithId:indexes withInt:0 withId:copy_ withInt:0 withInt:len];
  indexes = copy_;
  [JavaUtilArrays sortWithIntArray:indexes];
  IOSObjectArray *removed = [IOSObjectArray arrayWithLength:len type:[IOSClass classWithClass:[RARERenderableDataItem class]]];
  @synchronized (rootNode_) {
    RARERenderableDataItem *list = [self getExpandableItemWithRARERenderableDataItem:rootNode_];
    for (int i = 0; i < len; i++) {
      (void) IOSObjectArray_Set(removed, i, [((RARERenderableDataItem *) nil_chk(list)) getWithInt:IOSIntArray_Get(indexes, i)]);
    }
    [((RARERenderableDataItem *) nil_chk(list)) removeRowsWithIntArray:indexes];
  }
  [self nodesWereRemovedWithRARERenderableDataItem:rootNode_ withIntArray:indexes withNSObjectArray:removed];
}

- (id<JavaUtilList>)reverse {
  RARERenderableDataItem *list = [self getExpandableItemWithRARERenderableDataItem:rootNode_];
  (void) [((RARERenderableDataItem *) nil_chk(list)) reverse];
  if ([list size] > 0) {
    [self nodeStructureChangedWithRARERenderableDataItem:rootNode_];
  }
  return self;
}

- (RARERenderableDataItem *)shift {
  RARERenderableDataItem *list = [self getExpandableItemWithRARERenderableDataItem:rootNode_];
  return ([((RARERenderableDataItem *) nil_chk(list)) isEmpty]) ? nil : [list removeWithInt:0];
}

- (int)size {
  RARERenderableDataItem *item = [self getExpandableItemExWithRARERenderableDataItem:rootNode_];
  return (item == nil) ? 0 : [item size];
}

- (id<JavaUtilList>)sliceWithInt:(int)start {
  return [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) sliceWithInt:start withInt:[self size]];
}

- (id<JavaUtilList>)sliceWithInt:(int)start
                         withInt:(int)end {
  return [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) sliceWithInt:start withInt:end];
}

- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)comparator {
  self->comparator_ = comparator;
  sorting_ = YES;
  @try {
    [self needsSorting];
    [self structureChanged];
  }
  @finally {
    sorting_ = NO;
  }
}

- (id<JavaUtilList>)sortExWithJavaUtilComparator:(id<JavaUtilComparator>)comparator {
  self->comparator_ = comparator;
  sorting_ = YES;
  @try {
    [self needsSorting];
  }
  @finally {
    sorting_ = NO;
  }
  return self;
}

- (id<JavaUtilList>)spliceWithInt:(int)index
                          withInt:(int)howMany {
  return [self spliceWithInt:index withInt:howMany withNSObjectArray:(IOSObjectArray *) check_class_cast(nil, [IOSObjectArray class])];
}

- (id<JavaUtilList>)spliceWithInt:(int)index
                          withInt:(int)howMany
                withNSObjectArray:(IOSObjectArray *)e {
  return [self spliceListWithInt:index withInt:howMany withJavaUtilList:(e == nil) ? nil : [JavaUtilArrays asListWithNSObjectArray:e]];
}

- (id<JavaUtilList>)spliceListWithInt:(int)index
                              withInt:(int)howMany
                     withJavaUtilList:(id<JavaUtilList>)e {
  (void) [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) spliceListWithInt:index withInt:howMany withJavaUtilList:e];
  [self nodeStructureChangedWithRARERenderableDataItem:rootNode_];
  return self;
}

- (void)structureChanged {
  if (eventsEnabled_) {
    [self fireRootChanged];
  }
}

- (void)swapWithInt:(int)index1
            withInt:(int)index2 {
  [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) swapWithInt:index1 withInt:index2];
  [self nodesChangedWithRARERenderableDataItem:rootNode_ withIntArray:[IOSIntArray arrayWithInts:(int[]){ index1, index2 } count:2]];
}

- (void)trimToSize {
}

- (NSString *)toStringWithId:(id)item {
  RARERenderableDataItem *di = (RARERenderableDataItem *) check_class_cast(item, [RARERenderableDataItem class]);
  if ((columnDescription_ != nil) && ![((RARERenderableDataItem *) nil_chk(di)) isConverted] && (theWidget_ != nil)) {
    [self convertWithRAREiWidget:theWidget_ withRAREColumn:columnDescription_ withRARERenderableDataItem:di];
  }
  return [((RARERenderableDataItem *) nil_chk(di)) description];
}

- (void)unshiftWithId:(RARERenderableDataItem *)value {
  [self addWithInt:0 withId:value];
}

- (RARERenderableDataItem *)setWithInt:(int)index
                                withId:(RARERenderableDataItem *)element {
  RARERenderableDataItem *item = [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) setWithInt:index withId:element];
  [self nodesChangedWithRARERenderableDataItem:rootNode_ withIntArray:[IOSIntArray arrayWithInts:(int[]){ index } count:1]];
  return item;
}

- (BOOL)setAllWithJavaUtilCollection:(id<JavaUtilCollection>)c {
  int len1 = [self size];
  [self clearEx];
  @synchronized (rootNode_) {
    [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) addAllWithJavaUtilCollection:c];
  }
  int len2 = [self size];
  if (len2 != len1) {
    [self structureChanged];
  }
  return NO;
}

- (void)setAsksAllowsChildrenWithBoolean:(BOOL)ask {
  asksAllowsChildren__ = ask;
}

- (void)setConverterWithRAREUTiStringConverter:(id<RAREUTiStringConverter>)converter {
  self->converter_ = converter;
  if (rootNode_ != nil) {
    [rootNode_ setConverterWithRAREUTiStringConverter:(converter == nil) ? self : ((id) converter)];
  }
}

- (void)setEventsEnabledWithBoolean:(BOOL)enabled {
  self->eventsEnabled_ = enabled;
}

- (void)setExpandAllWithBoolean:(BOOL)expandAll {
  self->expandAll__ = expandAll;
}

- (void)setExpandableColumnWithInt:(int)expandableColumn {
  self->expandableColumn_ = expandableColumn;
}

- (void)setHideBarenBranchesWhenFilteredWithBoolean:(BOOL)hideBarenBranchesWhenFiltered {
  self->hideBarenBranchesWhenFiltered_ = hideBarenBranchesWhenFiltered;
}

- (void)setItemDescriptionWithRAREColumn:(RAREColumn *)column {
  self->columnDescription_ = column;
}

- (void)setRootWithRARERenderableDataItem:(RARERenderableDataItem *)root {
  [self clearEx];
  [((RARERenderableDataItem *) nil_chk(self->rootNode_)) clear];
  RARERenderableDataItem *list = [self getExpandableItemWithRARERenderableDataItem:rootNode_];
  if (root != nil) {
    [((RARERenderableDataItem *) nil_chk(list)) copy__WithRARERenderableDataItem:root];
    [list setConverterWithRAREUTiStringConverter:(converter_ == nil) ? self : ((id) converter_)];
    [rootNode_ copyExWithRARERenderableDataItem:root];
  }
  BOOL expanded = YES;
  if (rootItem_ != nil) {
    expanded = [rootItem_ isExpanded];
    [rootItem_ dispose];
  }
  rootItem_ = [self createTreeItemWithRARERenderableDataItem:rootNode_ withRAREiTreeItem:nil];
  [((id<RAREiTreeItem>) nil_chk(rootItem_)) setExpandedWithBoolean:expanded];
  [self structureChanged];
}

- (void)setWidgetWithRAREiWidget:(id<RAREiWidget>)widget {
  theWidget_ = widget;
}

- (RARERenderableDataItem *)getWithInt:(int)index {
  RARERenderableDataItem *di = [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) getWithInt:index];
  if ((columnDescription_ != nil) && ![((RARERenderableDataItem *) nil_chk(di)) isConverted] && (theWidget_ != nil)) {
    [self convertWithRAREiWidget:theWidget_ withRAREColumn:columnDescription_ withRARERenderableDataItem:di];
  }
  return di;
}

- (id)getChildWithRARERenderableDataItem:(RARERenderableDataItem *)parent
                                 withInt:(int)index {
  if (parent == rootNode_) {
    return [self getWithInt:index];
  }
  return [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:parent])) getItemWithInt:index];
}

- (int)getChildCountWithRARERenderableDataItem:(RARERenderableDataItem *)parent {
  return [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:parent])) getItemCount];
}

- (id<RAREUTiStringConverter>)getConverter {
  return converter_;
}

- (int)getExpandableColumn {
  return expandableColumn_;
}

- (id<JavaUtilList>)getFilteredList {
  return [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) getFilteredList];
}

- (id<RAREUTiStringConverter>)getFilteringConverter {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (int)getIndexOfChildWithRARERenderableDataItem:(RARERenderableDataItem *)parent
                      withRARERenderableDataItem:(RARERenderableDataItem *)child {
  return [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:parent])) indexOfWithId:child];
}

- (RAREColumn *)getItemDescription {
  return columnDescription_;
}

- (id<JavaUtilComparator>)getLastComparator {
  return comparator_;
}

- (id<RAREUTiFilter>)getLastFilter {
  return lastFilter_;
}

- (IOSObjectArray *)getListenersWithIOSClass:(IOSClass *)listenerType {
  return [((RAREEventListenerList *) nil_chk(listenerList_)) getListenersWithIOSClass:listenerType];
}

- (id<JavaUtilList>)getRawRows {
  return [self getExpandableItemWithRARERenderableDataItem:rootNode_];
}

- (RARERenderableDataItem *)getRoot {
  return rootNode_;
}

- (id<RAREiTreeItem>)getTreeItemWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id<RAREiTreeItem>)getTreeItemWithRARERenderableDataItem:(RARERenderableDataItem *)item
                                         withRAREiTreeItem:(id<RAREiTreeItem>)parent
                                               withBoolean:(BOOL)create {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id<JavaUtilList>)getUnfilteredList {
  return [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) getUnfilteredList];
}

- (BOOL)isEventsEnabled {
  return eventsEnabled_;
}

- (BOOL)isFiltered {
  return [((RARERenderableDataItem *) nil_chk([self getExpandableItemWithRARERenderableDataItem:rootNode_])) isFiltered];
}

- (BOOL)isHideBarenBranchesWhenFiltered {
  return hideBarenBranchesWhenFiltered_;
}

- (BOOL)isLeafWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  int col = [self getExpandableColumn];
  if (col == -1) {
    return [((RARERenderableDataItem *) nil_chk(item)) isEmpty];
  }
  if ([((RARERenderableDataItem *) nil_chk(item)) size] <= col) {
    return YES;
  }
  item = [item getItemExWithInt:col];
  return (item != nil) && ![item isEmpty];
}

- (void)addFilteredListNodeWithRARERenderableDataItem:(RARERenderableDataItem *)child
                           withRARERenderableDataItem:(RARERenderableDataItem *)parent
                                              withInt:(int)index {
  int len = [((RARERenderableDataItem *) nil_chk(parent)) getItemCount];
  @synchronized (parent) {
    RARERenderableDataItem *list = [self getExpandableItemWithRARERenderableDataItem:parent];
    if (index > -1) {
      [((RARERenderableDataItem *) nil_chk(list)) addToFilteredListWithInt:index withId:child];
      len = (index >= len) ? [list getItemCount] - 1 : index;
    }
    else {
      [((RARERenderableDataItem *) nil_chk(list)) addToFilteredListWithId:child];
    }
  }
  IOSIntArray *changed = [IOSIntArray arrayWithInts:(int[]){ len } count:1];
  [self nodesWereInsertedWithRARERenderableDataItem:parent withIntArray:changed];
}

- (id<RAREiTreeItem>)createTreeItemWithRARERenderableDataItem:(RARERenderableDataItem *)item
                                            withRAREiTreeItem:(id<RAREiTreeItem>)parent {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)fireRootChanged {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)fireTreeNodesChangedWithRARERenderableDataItem:(RARERenderableDataItem *)parent
                                          withIntArray:(IOSIntArray *)childIndices {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)fireTreeNodesInsertedWithRARERenderableDataItem:(RARERenderableDataItem *)parent
                                           withIntArray:(IOSIntArray *)childIndices {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)fireTreeNodesRemovedWithRARERenderableDataItem:(RARERenderableDataItem *)parent
                                          withIntArray:(IOSIntArray *)childIndices
                                     withNSObjectArray:(IOSObjectArray *)children {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)fireTreeStructureChangedWithRARERenderableDataItem:(RARERenderableDataItem *)parent {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)needsFiltering {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)needsSorting {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)setupRootItem {
  rootItem_ = [self createTreeItemWithRARERenderableDataItem:rootNode_ withRAREiTreeItem:nil];
  [((id<RAREiTreeItem>) nil_chk(rootItem_)) setExpandedWithBoolean:YES];
}

- (RARERenderableDataItem *)getExpandableItemWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  if (expandableColumn_ == -1) {
    return item;
  }
  RARERenderableDataItem *di = [((RARERenderableDataItem *) nil_chk(item)) getItemExWithInt:expandableColumn_];
  if (di == nil) {
    di = [[RARERenderableDataItem alloc] init];
    if (expandableColumn_ <= [item size]) {
      [item setItemCountWithInt:expandableColumn_ + 1];
    }
    (void) [item setWithInt:expandableColumn_ withId:di];
  }
  return di;
}

- (RARERenderableDataItem *)getExpandableItemExWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  if (expandableColumn_ == -1) {
    return item;
  }
  return [((RARERenderableDataItem *) nil_chk(item)) getItemExWithInt:expandableColumn_];
}

- (void)convertWithRAREiWidget:(id<RAREiWidget>)w
                withRAREColumn:(RAREColumn *)col
    withRARERenderableDataItem:(RARERenderableDataItem *)di {
  [((RAREColumn *) nil_chk(col)) convertWithRAREiWidget:w withRARERenderableDataItem:di];
  if ([((RARERenderableDataItem *) nil_chk(di)) size] > 0) {
    int len = [di size];
    for (int i = 0; i < len; i++) {
      [col convertWithRAREiWidget:w withRARERenderableDataItem:[di getWithInt:i]];
    }
  }
}

- (IOSIntArray *)indicesWithInt:(int)len1
                        withInt:(int)len2 {
  int len = len2 - len1;
  if (len < 0) {
    len = 0;
  }
  IOSIntArray *n = [IOSIntArray arrayWithLength:len];
  for (int i = 0; i < len; i++) {
    (*IOSIntArray_GetRef(n, i)) = len1++;
  }
  return n;
}

- (BOOL)unfilter {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)copyAllFieldsTo:(RAREaDataItemTreeModel *)other {
  [super copyAllFieldsTo:other];
  other->asksAllowsChildren__ = asksAllowsChildren__;
  other->columnDescription_ = columnDescription_;
  other->comparator_ = comparator_;
  other->converter_ = converter_;
  other->eventsEnabled_ = eventsEnabled_;
  other->expandAll__ = expandAll__;
  other->expandableColumn_ = expandableColumn_;
  other->hideBarenBranchesWhenFiltered_ = hideBarenBranchesWhenFiltered_;
  other->lastFilter_ = lastFilter_;
  other->listenerList_ = listenerList_;
  other->rootItem_ = rootItem_;
  other->rootNode_ = rootNode_;
  other->sorting_ = sorting_;
  other->theWidget_ = theWidget_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addWithRARERenderableDataItem:", NULL, "Z", 0x1, NULL },
    { "addAllWithJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "addAllWithInt:withJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "asksAllowsChildren", NULL, "Z", 0x1, NULL },
    { "collapseAll", NULL, "V", 0x401, NULL },
    { "concatWithJavaUtilListArray:", NULL, "LJavaUtilList", 0x81, NULL },
    { "expandAll", NULL, "V", 0x401, NULL },
    { "filterWithRAREUTiFilter:", NULL, "Z", 0x1, NULL },
    { "filterWithNSString:withBoolean:", NULL, "Z", 0x1, NULL },
    { "filterWithNSString:withBoolean:withBoolean:withBoolean:", NULL, "Z", 0x1, NULL },
    { "joinWithNSString:", NULL, "LNSString", 0x1, NULL },
    { "pop", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "pushWithRARERenderableDataItemArray:", NULL, "V", 0x81, NULL },
    { "refilter", NULL, "Z", 0x1, NULL },
    { "removeWithInt:", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "reverse", NULL, "LJavaUtilList", 0x1, NULL },
    { "shift", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "sliceWithInt:", NULL, "LJavaUtilList", 0x1, NULL },
    { "sliceWithInt:withInt:", NULL, "LJavaUtilList", 0x1, NULL },
    { "sortExWithJavaUtilComparator:", NULL, "LJavaUtilList", 0x1, NULL },
    { "spliceWithInt:withInt:", NULL, "LJavaUtilList", 0x1, NULL },
    { "spliceWithInt:withInt:withRARERenderableDataItemArray:", NULL, "LJavaUtilList", 0x81, NULL },
    { "spliceListWithInt:withInt:withJavaUtilList:", NULL, "LJavaUtilList", 0x1, NULL },
    { "toStringWithId:", NULL, "LNSString", 0x1, NULL },
    { "setWithInt:withRARERenderableDataItem:", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "setAllWithJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "getWithInt:", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "getChildWithRARERenderableDataItem:withInt:", NULL, "LNSObject", 0x1, NULL },
    { "getConverter", NULL, "LRAREUTiStringConverter", 0x1, NULL },
    { "getFilteredList", NULL, "LJavaUtilList", 0x1, NULL },
    { "getFilteringConverter", NULL, "LRAREUTiStringConverter", 0x401, NULL },
    { "getItemDescription", NULL, "LRAREColumn", 0x1, NULL },
    { "getLastComparator", NULL, "LJavaUtilComparator", 0x1, NULL },
    { "getLastFilter", NULL, "LRAREUTiFilter", 0x1, NULL },
    { "getListenersWithIOSClass:", NULL, "LIOSObjectArray", 0x1, NULL },
    { "getRawRows", NULL, "LJavaUtilList", 0x1, NULL },
    { "getRoot", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "getTreeItemWithRARERenderableDataItem:", NULL, "LRAREiTreeItem", 0x401, NULL },
    { "getTreeItemWithRARERenderableDataItem:withRAREiTreeItem:withBoolean:", NULL, "LRAREiTreeItem", 0x401, NULL },
    { "getUnfilteredList", NULL, "LJavaUtilList", 0x1, NULL },
    { "isEventsEnabled", NULL, "Z", 0x1, NULL },
    { "isFiltered", NULL, "Z", 0x1, NULL },
    { "isHideBarenBranchesWhenFiltered", NULL, "Z", 0x1, NULL },
    { "isLeafWithRARERenderableDataItem:", NULL, "Z", 0x1, NULL },
    { "addFilteredListNodeWithRARERenderableDataItem:withRARERenderableDataItem:withInt:", NULL, "V", 0x0, NULL },
    { "createTreeItemWithRARERenderableDataItem:withRAREiTreeItem:", NULL, "LRAREiTreeItem", 0x404, NULL },
    { "fireRootChanged", NULL, "V", 0x404, NULL },
    { "fireTreeNodesChangedWithRARERenderableDataItem:withIntArray:", NULL, "V", 0x404, NULL },
    { "fireTreeNodesInsertedWithRARERenderableDataItem:withIntArray:", NULL, "V", 0x404, NULL },
    { "fireTreeNodesRemovedWithRARERenderableDataItem:withIntArray:withNSObjectArray:", NULL, "V", 0x404, NULL },
    { "fireTreeStructureChangedWithRARERenderableDataItem:", NULL, "V", 0x404, NULL },
    { "needsFiltering", NULL, "V", 0x404, NULL },
    { "needsSorting", NULL, "V", 0x404, NULL },
    { "setupRootItem", NULL, "V", 0x4, NULL },
    { "getExpandableItemWithRARERenderableDataItem:", NULL, "LRARERenderableDataItem", 0x4, NULL },
    { "getExpandableItemExWithRARERenderableDataItem:", NULL, "LRARERenderableDataItem", 0x4, NULL },
    { "convertWithRAREiWidget:withRAREColumn:withRARERenderableDataItem:", NULL, "V", 0x2, NULL },
    { "indicesWithInt:withInt:", NULL, "LIOSIntArray", 0x2, NULL },
    { "unfilter", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "expandableColumn_", NULL, 0x4, "I" },
    { "rootNode_", NULL, 0x4, "LRARERenderableDataItem" },
    { "listenerList_", NULL, 0x4, "LRAREEventListenerList" },
    { "eventsEnabled_", NULL, 0x4, "Z" },
    { "asksAllowsChildren__", "asksAllowsChildren", 0x4, "Z" },
    { "columnDescription_", NULL, 0x4, "LRAREColumn" },
    { "comparator_", NULL, 0x4, "LJavaUtilComparator" },
    { "converter_", NULL, 0x4, "LRAREUTiStringConverter" },
    { "expandAll__", "expandAll", 0x4, "Z" },
    { "lastFilter_", NULL, 0x4, "LRAREUTiFilter" },
    { "rootItem_", NULL, 0x4, "LRAREiTreeItem" },
    { "sorting_", NULL, 0x4, "Z" },
    { "theWidget_", NULL, 0x4, "LRAREiWidget" },
  };
  static const char *superclass_type_args[] = {"LRARERenderableDataItem"};
  static J2ObjcClassInfo _RAREaDataItemTreeModel = { "aDataItemTreeModel", "com.appnativa.rare.ui.tree", NULL, 0x401, 59, methods, 13, fields, 1, superclass_type_args};
  return &_RAREaDataItemTreeModel;
}

@end
