//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/UnmodifiableIterator.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectUnmodifiableIterator_RESTRICT
#define ComGoogleCommonCollectUnmodifiableIterator_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectUnmodifiableIterator_RESTRICT

#if !defined (_ComGoogleCommonCollectUnmodifiableIterator_) && (ComGoogleCommonCollectUnmodifiableIterator_INCLUDE_ALL || ComGoogleCommonCollectUnmodifiableIterator_INCLUDE)
#define _ComGoogleCommonCollectUnmodifiableIterator_

#define JavaUtilIterator_RESTRICT 1
#define JavaUtilIterator_INCLUDE 1
#include "java/util/Iterator.h"

@interface ComGoogleCommonCollectUnmodifiableIterator : NSObject < JavaUtilIterator > {
}

- (id)init;
- (void)remove;
- (BOOL)hasNext;
- (id)next;
@end
#endif
