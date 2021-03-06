//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/AbstractListMultimap.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectAbstractListMultimap_RESTRICT
#define ComGoogleCommonCollectAbstractListMultimap_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectAbstractListMultimap_RESTRICT

#if !defined (_ComGoogleCommonCollectAbstractListMultimap_) && (ComGoogleCommonCollectAbstractListMultimap_INCLUDE_ALL || ComGoogleCommonCollectAbstractListMultimap_INCLUDE)
#define _ComGoogleCommonCollectAbstractListMultimap_

@protocol JavaLangIterable;
@protocol JavaUtilList;
@protocol JavaUtilMap;

#define ComGoogleCommonCollectAbstractMapBasedMultimap_RESTRICT 1
#define ComGoogleCommonCollectAbstractMapBasedMultimap_INCLUDE 1
#include "com/google/common/collect/AbstractMapBasedMultimap.h"

#define ComGoogleCommonCollectListMultimap_RESTRICT 1
#define ComGoogleCommonCollectListMultimap_INCLUDE 1
#include "com/google/common/collect/ListMultimap.h"

#define ComGoogleCommonCollectAbstractListMultimap_serialVersionUID 6588350623831699109

@interface ComGoogleCommonCollectAbstractListMultimap : ComGoogleCommonCollectAbstractMapBasedMultimap < ComGoogleCommonCollectListMultimap > {
}

- (id)initWithJavaUtilMap:(id<JavaUtilMap>)map;
- (id<JavaUtilList>)createCollection;
- (id<JavaUtilList>)createUnmodifiableEmptyCollection;
- (id<JavaUtilList>)getWithId:(id)key;
- (id<JavaUtilList>)removeAllWithId:(id)key;
- (id<JavaUtilList>)replaceValuesWithId:(id)key
                   withJavaLangIterable:(id<JavaLangIterable>)values;
- (BOOL)putWithId:(id)key
           withId:(id)value;
- (id<JavaUtilMap>)asMap;
- (BOOL)isEqual:(id)object;
@end
#endif
