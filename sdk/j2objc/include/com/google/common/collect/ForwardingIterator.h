//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/ForwardingIterator.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectForwardingIterator_RESTRICT
#define ComGoogleCommonCollectForwardingIterator_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectForwardingIterator_RESTRICT

#if !defined (_ComGoogleCommonCollectForwardingIterator_) && (ComGoogleCommonCollectForwardingIterator_INCLUDE_ALL || ComGoogleCommonCollectForwardingIterator_INCLUDE)
#define _ComGoogleCommonCollectForwardingIterator_

#define ComGoogleCommonCollectForwardingObject_RESTRICT 1
#define ComGoogleCommonCollectForwardingObject_INCLUDE 1
#include "com/google/common/collect/ForwardingObject.h"

#define JavaUtilIterator_RESTRICT 1
#define JavaUtilIterator_INCLUDE 1
#include "java/util/Iterator.h"

@interface ComGoogleCommonCollectForwardingIterator : ComGoogleCommonCollectForwardingObject < JavaUtilIterator > {
}

- (id)init;
- (id<JavaUtilIterator>)delegate;
- (BOOL)hasNext;
- (id)next;
- (void)remove;
@end
#endif
