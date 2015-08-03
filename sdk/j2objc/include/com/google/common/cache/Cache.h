//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/cache/Cache.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCacheCache_RESTRICT
#define ComGoogleCommonCacheCache_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCacheCache_RESTRICT

#if !defined (_ComGoogleCommonCacheCache_) && (ComGoogleCommonCacheCache_INCLUDE_ALL || ComGoogleCommonCacheCache_INCLUDE)
#define _ComGoogleCommonCacheCache_

@class ComGoogleCommonCacheCacheStats;
@class ComGoogleCommonCollectImmutableMap;
@protocol JavaLangIterable;
@protocol JavaUtilConcurrentCallable;
@protocol JavaUtilConcurrentConcurrentMap;
@protocol JavaUtilMap;

@protocol ComGoogleCommonCacheCache < NSObject, JavaObject >
- (id)getIfPresentWithId:(id)key;
- (id)getWithId:(id)key
withJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)valueLoader;
- (ComGoogleCommonCollectImmutableMap *)getAllPresentWithJavaLangIterable:(id<JavaLangIterable>)keys;
- (void)putWithId:(id)key
           withId:(id)value;
- (void)putAllWithJavaUtilMap:(id<JavaUtilMap>)m;
- (void)invalidateWithId:(id)key;
- (void)invalidateAllWithJavaLangIterable:(id<JavaLangIterable>)keys;
- (void)invalidateAll;
- (long long int)size;
- (ComGoogleCommonCacheCacheStats *)stats;
- (id<JavaUtilConcurrentConcurrentMap>)asMap;
- (void)cleanUp;
@end
#endif