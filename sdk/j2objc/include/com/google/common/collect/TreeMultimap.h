//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/TreeMultimap.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectTreeMultimap_RESTRICT
#define ComGoogleCommonCollectTreeMultimap_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectTreeMultimap_RESTRICT

#if !defined (_ComGoogleCommonCollectTreeMultimap_) && (ComGoogleCommonCollectTreeMultimap_INCLUDE_ALL || ComGoogleCommonCollectTreeMultimap_INCLUDE)
#define _ComGoogleCommonCollectTreeMultimap_

@protocol ComGoogleCommonCollectMultimap;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol JavaUtilNavigableMap;
@protocol JavaUtilNavigableSet;
@protocol JavaUtilSortedSet;

#define ComGoogleCommonCollectAbstractSortedKeySortedSetMultimap_RESTRICT 1
#define ComGoogleCommonCollectAbstractSortedKeySortedSetMultimap_INCLUDE 1
#include "com/google/common/collect/AbstractSortedKeySortedSetMultimap.h"

#define ComGoogleCommonCollectTreeMultimap_serialVersionUID 0

@interface ComGoogleCommonCollectTreeMultimap : ComGoogleCommonCollectAbstractSortedKeySortedSetMultimap {
 @public
  id<JavaUtilComparator> keyComparator__;
  id<JavaUtilComparator> valueComparator__;
}

+ (ComGoogleCommonCollectTreeMultimap *)create;
+ (ComGoogleCommonCollectTreeMultimap *)createWithJavaUtilComparator:(id<JavaUtilComparator>)keyComparator
                                              withJavaUtilComparator:(id<JavaUtilComparator>)valueComparator;
+ (ComGoogleCommonCollectTreeMultimap *)createWithComGoogleCommonCollectMultimap:(id<ComGoogleCommonCollectMultimap>)multimap;
- (id)initWithJavaUtilComparator:(id<JavaUtilComparator>)keyComparator
          withJavaUtilComparator:(id<JavaUtilComparator>)valueComparator;
- (id)initWithJavaUtilComparator:(id<JavaUtilComparator>)keyComparator
          withJavaUtilComparator:(id<JavaUtilComparator>)valueComparator
withComGoogleCommonCollectMultimap:(id<ComGoogleCommonCollectMultimap>)multimap;
- (id<JavaUtilSortedSet>)createCollection;
- (id<JavaUtilCollection>)createCollectionWithId:(id)key;
- (id<JavaUtilComparator>)keyComparator;
- (id<JavaUtilComparator>)valueComparator;
- (id<JavaUtilNavigableMap>)backingMap;
- (id<JavaUtilNavigableSet>)getWithId:(id)key;
- (id<JavaUtilCollection>)unmodifiableCollectionSubclassWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (id<JavaUtilCollection>)wrapCollectionWithId:(id)key
                        withJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (id<JavaUtilNavigableSet>)keySet;
- (id<JavaUtilNavigableSet>)createKeySet;
- (id<JavaUtilNavigableMap>)asMap;
- (id<JavaUtilNavigableMap>)createAsMap;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectTreeMultimap *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeMultimap, keyComparator__, id<JavaUtilComparator>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeMultimap, valueComparator__, id<JavaUtilComparator>)
#endif
