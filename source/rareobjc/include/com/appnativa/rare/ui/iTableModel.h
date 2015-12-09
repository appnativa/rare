//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iTableModel.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREiTableModel_H_
#define _RAREiTableModel_H_

@class IOSObjectArray;
@class RAREColumn;
@class RARERenderableDataItem;
@protocol JavaLangCharSequence;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol JavaUtilList;
@protocol RAREUTiFilter;
@protocol RAREUTiFilterableList;
@protocol RAREUTiStringConverter;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iPlatformListDataModel.h"

@protocol RAREiTableModel < RAREiPlatformListDataModel, NSObject, JavaObject >
- (void)filterWithInt:(int)index;
- (void)resetModified;
- (int)getColumnCount;
- (BOOL)isModified;
- (void)addIndexToFilteredListWithInt:(int)index;
- (void)addRowWithRARERenderableDataItem:(RARERenderableDataItem *)row;
- (void)addRowWithInt:(int)index
withRARERenderableDataItem:(RARERenderableDataItem *)row;
- (void)addRowExWithRARERenderableDataItem:(RARERenderableDataItem *)row;
- (void)addRowExWithInt:(int)index
withRARERenderableDataItem:(RARERenderableDataItem *)row;
- (void)addRowsWithJavaUtilCollection:(id<JavaUtilCollection>)rows;
- (void)addRowsWithInt:(int)index
withJavaUtilCollection:(id<JavaUtilCollection>)rows;
- (void)addRowsExWithJavaUtilCollection:(id<JavaUtilCollection>)rows;
- (void)addRowsExWithInt:(int)index
  withJavaUtilCollection:(id<JavaUtilCollection>)rows;
- (void)addToFilteredListWithId:(RARERenderableDataItem *)row;
- (void)addToFilteredListWithInt:(int)index
                          withId:(RARERenderableDataItem *)row;
- (void)clearTable;
- (void)clearTableData;
- (void)clearTableDataEx;
- (id<JavaUtilList>)concatWithJavaUtilListArray:(IOSObjectArray *)e;
- (id<RAREiTableModel>)createEmptyCopy;
- (void)dispose;
- (BOOL)filterWithRAREUTiFilter:(id<RAREUTiFilter>)filter;
- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains;
- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains
               withBoolean:(BOOL)nullPasses
               withBoolean:(BOOL)emptyPasses;
- (int)findWithRAREUTiFilter:(id<RAREUTiFilter>)filter
                     withInt:(int)start;
- (int)findWithNSString:(NSString *)filter
                withInt:(int)start
            withBoolean:(BOOL)contains;
- (int)indexForRowWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (NSString *)joinWithNSString:(NSString *)sep;
- (void)moveRowWithInt:(int)from
               withInt:(int)to;
- (BOOL)refilter;
- (void)removeRowWithInt:(int)pos;
- (void)resetModelWithJavaUtilList:(id<JavaUtilList>)columns
         withRAREUTiFilterableList:(id<RAREUTiFilterableList>)rows;
- (void)resetRowsWithRAREUTiFilterableList:(id<RAREUTiFilterableList>)rows;
- (id<JavaUtilList>)reverse;
- (id<JavaUtilList>)sliceWithInt:(int)start;
- (id<JavaUtilList>)sliceWithInt:(int)start
                         withInt:(int)end;
- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (void)sortWithInt:(int)col
        withBoolean:(BOOL)descending
        withBoolean:(BOOL)useLinkedData;
- (id<JavaUtilList>)sortExWithInt:(int)col
                      withBoolean:(BOOL)desc
                      withBoolean:(BOOL)force
                      withBoolean:(BOOL)useLinkedData;
- (id<JavaUtilList>)spliceWithInt:(int)index
                          withInt:(int)howMany;
- (id<JavaUtilList>)spliceWithInt:(int)index
                          withInt:(int)howMany
                withNSObjectArray:(IOSObjectArray *)e;
- (id<JavaUtilList>)spliceListWithInt:(int)index
                              withInt:(int)howMany
                     withJavaUtilList:(id<JavaUtilList>)e;
- (void)tableChanged;
- (void)tableDataChanged;
- (void)tableItemsModified;
- (BOOL)unfilter;
- (void)updateForInsertionOrDeletion;
- (void)setActiveColumnWithInt:(int)col;
- (void)setAllowSortingWithBoolean:(BOOL)sortingAllowed;
- (void)setConverterWithRAREUTiStringConverter:(id<RAREUTiStringConverter>)converter;
- (void)setExpandableColumnWithInt:(int)col;
- (void)setRowWithInt:(int)pos
withRARERenderableDataItem:(RARERenderableDataItem *)row;
- (void)setValueAtWithId:(id)value
                 withInt:(int)row
                 withInt:(int)col;
- (RAREColumn *)getColumnWithInt:(int)col;
- (id<JavaUtilList>)getColumns;
- (id<JavaUtilComparator>)getComparator;
- (id<RAREUTiStringConverter>)getConverter;
- (int)getExpandableColumn;
- (id<RAREUTiFilterableList>)getFilterableList;
- (RARERenderableDataItem *)getItemAtWithInt:(int)row
                                     withInt:(int)col;
- (RAREColumn *)getItemDescriptionWithInt:(int)row
                                  withInt:(int)col;
- (int)getOperatingColumn;
- (RARERenderableDataItem *)getRowWithInt:(int)row;
- (int)getRowCount;
- (id<RAREUTiFilterableList>)getRowsEx;
- (int)getSortColumn;
- (id<JavaLangCharSequence>)getTooltipWithInt:(int)row
                                      withInt:(int)col;
- (id<JavaUtilList>)getUnfilteredList;
- (id)getValueAtWithInt:(int)row
                withInt:(int)col;
- (id<RAREiWidget>)getWidget;
- (BOOL)isAllowSorting;
- (BOOL)isFiltered;
- (BOOL)isSortDescending;
@end

#define ComAppnativaRareUiITableModel RAREiTableModel

#endif // _RAREiTableModel_H_
