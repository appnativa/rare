//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/iFilterableList.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREUTiFilterableList_H_
#define _RAREUTiFilterableList_H_

@class IOSIntArray;
@class IOSObjectArray;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol RAREUTiFilter;
@protocol RAREUTiStringConverter;

#import "JreEmulation.h"
#include "java/util/List.h"

@protocol RAREUTiFilterableList < JavaUtilList, NSObject, JavaObject >
- (void)addToFilteredListWithInt:(int)index
                          withId:(id)e;
- (void)addIndexToFilteredListWithInt:(int)index;
- (void)addToFilteredListWithId:(id)e;
- (id<JavaUtilList>)concatWithJavaUtilListArray:(IOSObjectArray *)e;
- (void)ensureCapacityWithInt:(int)capacity;
- (BOOL)filterWithRAREUTiFilter:(id<RAREUTiFilter>)filter;
- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains;
- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains
               withBoolean:(BOOL)nullPasses
               withBoolean:(BOOL)emptyPasses;
- (void)setFilteredListWithJavaUtilList:(id<JavaUtilList>)list
                      withRAREUTiFilter:(id<RAREUTiFilter>)lastFilter;
- (int)findWithRAREUTiFilter:(id<RAREUTiFilter>)filter
                     withInt:(int)start;
- (int)findWithNSString:(NSString *)filter
                withInt:(int)start
            withBoolean:(BOOL)contains;
- (NSString *)joinWithNSString:(NSString *)sep;
- (void)moveWithInt:(int)from
            withInt:(int)to;
- (id)pop;
- (void)pushWithNSObjectArray:(IOSObjectArray *)e;
- (BOOL)refilter;
- (void)removeRowsWithIntArray:(IOSIntArray *)indexes;
- (id<JavaUtilList>)reverse;
- (id)shift;
- (id<JavaUtilList>)sliceWithInt:(int)start;
- (id<JavaUtilList>)sliceWithInt:(int)start
                         withInt:(int)end;
- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (id<JavaUtilList>)spliceWithInt:(int)index
                          withInt:(int)howMany;
- (id<JavaUtilList>)spliceWithInt:(int)index
                          withInt:(int)howMany
                withNSObjectArray:(IOSObjectArray *)e;
- (id<JavaUtilList>)spliceListWithInt:(int)index
                              withInt:(int)howMany
                     withJavaUtilList:(id<JavaUtilList>)e;
- (void)swapWithInt:(int)index1
            withInt:(int)index2;
- (BOOL)unfilter;
- (void)unshiftWithId:(id)value;
- (BOOL)setAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (void)setConverterWithRAREUTiStringConverter:(id<RAREUTiStringConverter>)converter;
- (id<RAREUTiStringConverter>)getConverter;
- (id<JavaUtilList>)getFilteredList;
- (id<RAREUTiFilter>)getLastFilter;
- (id<JavaUtilList>)getUnfilteredList;
- (BOOL)isFiltered;
- (void)trimToSize;
@end

#define ComAppnativaUtilIFilterableList RAREUTiFilterableList

#endif // _RAREUTiFilterableList_H_
