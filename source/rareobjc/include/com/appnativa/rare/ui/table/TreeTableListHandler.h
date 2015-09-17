//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/ui/table/TreeTableListHandler.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RARETreeTableListHandler_H_
#define _RARETreeTableListHandler_H_

@class RAREDataItemTreeModel;
@class RARERenderableDataItem;
@class RARETableComponent;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol RAREUTiFilter;
@protocol RAREiPlatformListDataModel;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/table/TableListHandler.h"

@interface RARETreeTableListHandler : RARETableListHandler {
 @public
  RAREDataItemTreeModel *treeModel_;
}

- (id)initWithRARETableComponent:(RARETableComponent *)table
  withRAREiPlatformListDataModel:(id<RAREiPlatformListDataModel>)model
       withRAREDataItemTreeModel:(RAREDataItemTreeModel *)tm;
- (void)dispose;
- (BOOL)setAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (void)refreshItems;
- (void)addWithInt:(int)index
            withId:(RARERenderableDataItem *)element;
- (BOOL)addWithId:(RARERenderableDataItem *)child;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)addAllWithInt:(int)index
withJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)clear;
- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (BOOL)filterWithRAREUTiFilter:(id<RAREUTiFilter>)filter;
- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains;
- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains
               withBoolean:(BOOL)nullPasses
               withBoolean:(BOOL)emptyPasses;
- (void)copyAllFieldsTo:(RARETreeTableListHandler *)other;
@end

J2OBJC_FIELD_SETTER(RARETreeTableListHandler, treeModel_, RAREDataItemTreeModel *)

typedef RARETreeTableListHandler ComAppnativaRareUiTableTreeTableListHandler;

#endif // _RARETreeTableListHandler_H_
