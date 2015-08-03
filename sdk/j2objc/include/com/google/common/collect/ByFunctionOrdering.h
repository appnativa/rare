//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/ByFunctionOrdering.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectByFunctionOrdering_RESTRICT
#define ComGoogleCommonCollectByFunctionOrdering_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectByFunctionOrdering_RESTRICT

#if !defined (_ComGoogleCommonCollectByFunctionOrdering_) && (ComGoogleCommonCollectByFunctionOrdering_INCLUDE_ALL || ComGoogleCommonCollectByFunctionOrdering_INCLUDE)
#define _ComGoogleCommonCollectByFunctionOrdering_

@protocol ComGoogleCommonBaseFunction;

#define ComGoogleCommonCollectOrdering_RESTRICT 1
#define ComGoogleCommonCollectOrdering_INCLUDE 1
#include "com/google/common/collect/Ordering.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonCollectByFunctionOrdering_serialVersionUID 0

@interface ComGoogleCommonCollectByFunctionOrdering : ComGoogleCommonCollectOrdering < JavaIoSerializable > {
 @public
  id<ComGoogleCommonBaseFunction> function_;
  ComGoogleCommonCollectOrdering *ordering_;
}

- (id)initWithComGoogleCommonBaseFunction:(id<ComGoogleCommonBaseFunction>)function
       withComGoogleCommonCollectOrdering:(ComGoogleCommonCollectOrdering *)ordering;
- (int)compareWithId:(id)left
              withId:(id)right;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectByFunctionOrdering *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectByFunctionOrdering, function_, id<ComGoogleCommonBaseFunction>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectByFunctionOrdering, ordering_, ComGoogleCommonCollectOrdering *)
#endif