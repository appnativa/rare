//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/ComparatorOrdering.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectComparatorOrdering_RESTRICT
#define ComGoogleCommonCollectComparatorOrdering_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectComparatorOrdering_RESTRICT

#if !defined (_ComGoogleCommonCollectComparatorOrdering_) && (ComGoogleCommonCollectComparatorOrdering_INCLUDE_ALL || ComGoogleCommonCollectComparatorOrdering_INCLUDE)
#define _ComGoogleCommonCollectComparatorOrdering_

@class ComGoogleCommonCollectImmutableList;
@protocol JavaLangIterable;
@protocol JavaUtilComparator;
@protocol JavaUtilList;

#define ComGoogleCommonCollectOrdering_RESTRICT 1
#define ComGoogleCommonCollectOrdering_INCLUDE 1
#include "com/google/common/collect/Ordering.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonCollectComparatorOrdering_serialVersionUID 0

@interface ComGoogleCommonCollectComparatorOrdering : ComGoogleCommonCollectOrdering < JavaIoSerializable > {
 @public
  id<JavaUtilComparator> comparator_;
}

- (id)initWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (int)compareWithId:(id)a
              withId:(id)b;
- (int)binarySearchWithJavaUtilList:(id<JavaUtilList>)sortedList
                             withId:(id)key;
- (id<JavaUtilList>)sortedCopyWithJavaLangIterable:(id<JavaLangIterable>)iterable;
- (ComGoogleCommonCollectImmutableList *)immutableSortedCopyWithJavaLangIterable:(id<JavaLangIterable>)iterable;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectComparatorOrdering *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectComparatorOrdering, comparator_, id<JavaUtilComparator>)
#endif
