//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/ForwardingConcurrentMap.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectForwardingConcurrentMap_RESTRICT
#define ComGoogleCommonCollectForwardingConcurrentMap_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectForwardingConcurrentMap_RESTRICT

#if !defined (_ComGoogleCommonCollectForwardingConcurrentMap_) && (ComGoogleCommonCollectForwardingConcurrentMap_INCLUDE_ALL || ComGoogleCommonCollectForwardingConcurrentMap_INCLUDE)
#define _ComGoogleCommonCollectForwardingConcurrentMap_

#define ComGoogleCommonCollectForwardingMap_RESTRICT 1
#define ComGoogleCommonCollectForwardingMap_INCLUDE 1
#include "com/google/common/collect/ForwardingMap.h"

#define JavaUtilConcurrentConcurrentMap_RESTRICT 1
#define JavaUtilConcurrentConcurrentMap_INCLUDE 1
#include "java/util/concurrent/ConcurrentMap.h"

@interface ComGoogleCommonCollectForwardingConcurrentMap : ComGoogleCommonCollectForwardingMap < JavaUtilConcurrentConcurrentMap > {
}

- (id)init;
- (id<JavaUtilConcurrentConcurrentMap>)delegate;
- (id)putIfAbsentWithId:(id)key
                 withId:(id)value;
- (BOOL)removeWithId:(id)key
              withId:(id)value;
- (id)replaceWithId:(id)key
             withId:(id)value;
- (BOOL)replaceWithId:(id)key
               withId:(id)oldValue
               withId:(id)newValue;
@end
#endif
