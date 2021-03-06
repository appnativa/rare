//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/base/Present.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonBasePresent_RESTRICT
#define ComGoogleCommonBasePresent_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonBasePresent_RESTRICT

#if !defined (_ComGoogleCommonBasePresent_) && (ComGoogleCommonBasePresent_INCLUDE_ALL || ComGoogleCommonBasePresent_INCLUDE)
#define _ComGoogleCommonBasePresent_

@protocol ComGoogleCommonBaseFunction;
@protocol ComGoogleCommonBaseSupplier;
@protocol JavaUtilSet;

#define ComGoogleCommonBaseOptional_RESTRICT 1
#define ComGoogleCommonBaseOptional_INCLUDE 1
#include "com/google/common/base/Optional.h"

#define ComGoogleCommonBasePresent_serialVersionUID 0

@interface ComGoogleCommonBasePresent : ComGoogleCommonBaseOptional {
 @public
  id reference_;
}

- (id)initWithId:(id)reference;
- (BOOL)isPresent;
- (id)get;
- (id)or__WithId:(id)defaultValue;
- (ComGoogleCommonBaseOptional *)or__WithComGoogleCommonBaseOptional:(ComGoogleCommonBaseOptional *)secondChoice;
- (id)or__WithComGoogleCommonBaseSupplier:(id<ComGoogleCommonBaseSupplier>)supplier;
- (id)orNull;
- (id<JavaUtilSet>)asSet;
- (ComGoogleCommonBaseOptional *)transformWithComGoogleCommonBaseFunction:(id<ComGoogleCommonBaseFunction>)function;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonBasePresent *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonBasePresent, reference_, id)
#endif
