//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/TransformedIterator.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectTransformedIterator_RESTRICT
#define ComGoogleCommonCollectTransformedIterator_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectTransformedIterator_RESTRICT

#if !defined (_ComGoogleCommonCollectTransformedIterator_) && (ComGoogleCommonCollectTransformedIterator_INCLUDE_ALL || ComGoogleCommonCollectTransformedIterator_INCLUDE)
#define _ComGoogleCommonCollectTransformedIterator_

#define JavaUtilIterator_RESTRICT 1
#define JavaUtilIterator_INCLUDE 1
#include "java/util/Iterator.h"

@interface ComGoogleCommonCollectTransformedIterator : NSObject < JavaUtilIterator > {
 @public
  id<JavaUtilIterator> backingIterator_;
}

- (id)initWithJavaUtilIterator:(id<JavaUtilIterator>)backingIterator;
- (id)transformWithId:(id)from;
- (BOOL)hasNext;
- (id)next;
- (void)remove;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectTransformedIterator *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTransformedIterator, backingIterator_, id<JavaUtilIterator>)
#endif
