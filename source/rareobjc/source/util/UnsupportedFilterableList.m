//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src/com/appnativa/util/UnsupportedFilterableList.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/util/UnsupportedFilterableList.h"
#include "com/appnativa/util/iFilter.h"
#include "com/appnativa/util/iStringConverter.h"
#include "java/lang/UnsupportedOperationException.h"
#include "java/util/Collection.h"
#include "java/util/Comparator.h"
#include "java/util/Iterator.h"
#include "java/util/List.h"
#include "java/util/ListIterator.h"

@implementation RAREUTUnsupportedFilterableList

static IOSObjectArray * RAREUTUnsupportedFilterableList_EMPTY_ARRAY_;

+ (IOSObjectArray *)EMPTY_ARRAY {
  return RAREUTUnsupportedFilterableList_EMPTY_ARRAY_;
}

- (id)init {
  return [super init];
}

- (BOOL)addWithId:(id)e {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (void)addWithInt:(int)index
            withId:(id)element {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)c {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (BOOL)addAllWithInt:(int)index
withJavaUtilCollection:(id<JavaUtilCollection>)c {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (void)addIndexToFilteredListWithInt:(int)index {
}

- (void)addToFilteredListWithId:(id)element {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (void)addToFilteredListWithInt:(int)index
                          withId:(id)element {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (void)clear {
}

- (id<JavaUtilList>)concatWithJavaUtilListArray:(IOSObjectArray *)e {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (BOOL)containsWithId:(id)o {
  return NO;
}

- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)c {
  return NO;
}

- (void)ensureCapacityWithInt:(int)capacity {
}

- (BOOL)filterWithRAREUTiFilter:(id<RAREUTiFilter>)filter {
  return NO;
}

- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains {
  return NO;
}

- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains
               withBoolean:(BOOL)nullPasses
               withBoolean:(BOOL)emptyPasses {
  return NO;
}

- (int)findWithRAREUTiFilter:(id<RAREUTiFilter>)filter
                     withInt:(int)start {
  return -1;
}

- (int)findWithNSString:(NSString *)filter
                withInt:(int)start
            withBoolean:(BOOL)contains {
  return -1;
}

- (int)indexOfWithId:(id)o {
  return -1;
}

- (id<JavaUtilIterator>)iterator {
  return [[RAREUTUnsupportedFilterableList_$1 alloc] init];
}

- (NSString *)joinWithNSString:(NSString *)sep {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (int)lastIndexOfWithId:(id)o {
  return -1;
}

- (id<JavaUtilListIterator>)listIterator {
  return [[RAREUTUnsupportedFilterableList_$2 alloc] init];
}

- (id<JavaUtilListIterator>)listIteratorWithInt:(int)index {
  return [self listIterator];
}

- (void)moveWithInt:(int)from
            withInt:(int)to {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (id)pop {
  return nil;
}

- (void)pushWithNSObjectArray:(IOSObjectArray *)e {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (BOOL)refilter {
  return NO;
}

- (id)removeWithInt:(int)index {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (BOOL)removeWithId:(id)o {
  return NO;
}

- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)c {
  return NO;
}

- (void)removeRowsWithIntArray:(IOSIntArray *)indexes {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)c {
  return NO;
}

- (id<JavaUtilList>)reverse {
  return self;
}

- (id)shift {
  return nil;
}

- (int)size {
  return 0;
}

- (id<JavaUtilList>)sliceWithInt:(int)start {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (id<JavaUtilList>)sliceWithInt:(int)start
                         withInt:(int)end {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)comparator {
}

- (id<JavaUtilList>)spliceWithInt:(int)index
                          withInt:(int)howMany {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (id<JavaUtilList>)spliceWithInt:(int)index
                          withInt:(int)howMany
                withNSObjectArray:(IOSObjectArray *)e {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (id<JavaUtilList>)spliceListWithInt:(int)index
                              withInt:(int)howMany
                     withJavaUtilList:(id<JavaUtilList>)e {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (id<JavaUtilList>)subListWithInt:(int)fromIndex
                           withInt:(int)toIndex {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (void)swapWithInt:(int)index1
            withInt:(int)index2 {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (IOSObjectArray *)toArray {
  return RAREUTUnsupportedFilterableList_EMPTY_ARRAY_;
}

- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)a {
  return a;
}

- (BOOL)unfilter {
  return NO;
}

- (void)unshiftWithId:(id)value {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (id)setWithInt:(int)index
          withId:(id)element {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (BOOL)setAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (void)setConverterWithRAREUTiStringConverter:(id<RAREUTiStringConverter>)converter {
}

- (id)getWithInt:(int)index {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (id<RAREUTiStringConverter>)getConverter {
  return nil;
}

- (id<JavaUtilList>)getFilteredList {
  return self;
}

- (id<RAREUTiFilter>)getLastFilter {
  return nil;
}

- (id<JavaUtilList>)getUnfilteredList {
  return self;
}

- (BOOL)isEmpty {
  return YES;
}

- (BOOL)isFiltered {
  return NO;
}

- (void)setFilteredListWithJavaUtilList:(id<JavaUtilList>)list
                      withRAREUTiFilter:(id<RAREUTiFilter>)lastFilter {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (void)trimToSize {
}

+ (void)initialize {
  if (self == [RAREUTUnsupportedFilterableList class]) {
    RAREUTUnsupportedFilterableList_EMPTY_ARRAY_ = [IOSObjectArray arrayWithLength:0 type:[IOSClass classWithClass:[NSObject class]]];
  }
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addWithId:", NULL, "Z", 0x1, NULL },
    { "addAllWithJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "addAllWithInt:withJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "concatWithJavaUtilListArray:", NULL, "LJavaUtilList", 0x81, NULL },
    { "containsWithId:", NULL, "Z", 0x1, NULL },
    { "containsAllWithJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "filterWithRAREUTiFilter:", NULL, "Z", 0x1, NULL },
    { "filterWithNSString:withBoolean:", NULL, "Z", 0x1, NULL },
    { "filterWithNSString:withBoolean:withBoolean:withBoolean:", NULL, "Z", 0x1, NULL },
    { "iterator", NULL, "LJavaUtilIterator", 0x1, NULL },
    { "joinWithNSString:", NULL, "LNSString", 0x1, NULL },
    { "listIterator", NULL, "LJavaUtilListIterator", 0x1, NULL },
    { "listIteratorWithInt:", NULL, "LJavaUtilListIterator", 0x1, NULL },
    { "pop", NULL, "TE", 0x1, NULL },
    { "pushWithNSObjectArray:", NULL, "V", 0x81, NULL },
    { "refilter", NULL, "Z", 0x1, NULL },
    { "removeWithInt:", NULL, "TE", 0x1, NULL },
    { "removeWithId:", NULL, "Z", 0x1, NULL },
    { "removeAllWithJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "retainAllWithJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "reverse", NULL, "LJavaUtilList", 0x1, NULL },
    { "shift", NULL, "TE", 0x1, NULL },
    { "sliceWithInt:", NULL, "LJavaUtilList", 0x1, NULL },
    { "sliceWithInt:withInt:", NULL, "LJavaUtilList", 0x1, NULL },
    { "spliceWithInt:withInt:", NULL, "LJavaUtilList", 0x1, NULL },
    { "spliceWithInt:withInt:withNSObjectArray:", NULL, "LJavaUtilList", 0x81, NULL },
    { "spliceListWithInt:withInt:withJavaUtilList:", NULL, "LJavaUtilList", 0x1, NULL },
    { "subListWithInt:withInt:", NULL, "LJavaUtilList", 0x1, NULL },
    { "toArray", NULL, "LIOSObjectArray", 0x1, NULL },
    { "toArrayWithNSObjectArray:", NULL, "LIOSObjectArray", 0x1, NULL },
    { "unfilter", NULL, "Z", 0x1, NULL },
    { "setWithInt:withId:", NULL, "TE", 0x1, NULL },
    { "setAllWithJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "getWithInt:", NULL, "TE", 0x1, NULL },
    { "getConverter", NULL, "LRAREUTiStringConverter", 0x1, NULL },
    { "getFilteredList", NULL, "LJavaUtilList", 0x1, NULL },
    { "getLastFilter", NULL, "LRAREUTiFilter", 0x1, NULL },
    { "getUnfilteredList", NULL, "LJavaUtilList", 0x1, NULL },
    { "isEmpty", NULL, "Z", 0x1, NULL },
    { "isFiltered", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "EMPTY_ARRAY_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RAREUTUnsupportedFilterableList = { "UnsupportedFilterableList", "com.appnativa.util", NULL, 0x1, 40, methods, 1, fields, 0, NULL};
  return &_RAREUTUnsupportedFilterableList;
}

@end
@implementation RAREUTUnsupportedFilterableList_$1

- (BOOL)hasNext {
  return NO;
}

- (id)next {
  return nil;
}

- (void)remove {
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "hasNext", NULL, "Z", 0x1, NULL },
    { "next", NULL, "TE", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREUTUnsupportedFilterableList_$1 = { "$1", "com.appnativa.util", "UnsupportedFilterableList", 0x8000, 2, methods, 0, NULL, 0, NULL};
  return &_RAREUTUnsupportedFilterableList_$1;
}

@end
@implementation RAREUTUnsupportedFilterableList_$2

- (void)addWithId:(id)arg0 {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (BOOL)hasNext {
  return NO;
}

- (BOOL)hasPrevious {
  return NO;
}

- (id)next {
  return nil;
}

- (int)nextIndex {
  return 0;
}

- (id)previous {
  return nil;
}

- (int)previousIndex {
  return -1;
}

- (void)remove {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (void)setWithId:(id)arg0 {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "hasNext", NULL, "Z", 0x1, NULL },
    { "hasPrevious", NULL, "Z", 0x1, NULL },
    { "next", NULL, "TE", 0x1, NULL },
    { "previous", NULL, "TE", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREUTUnsupportedFilterableList_$2 = { "$2", "com.appnativa.util", "UnsupportedFilterableList", 0x8000, 4, methods, 0, NULL, 0, NULL};
  return &_RAREUTUnsupportedFilterableList_$2;
}

@end
