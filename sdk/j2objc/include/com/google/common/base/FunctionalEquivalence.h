//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/base/FunctionalEquivalence.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonBaseFunctionalEquivalence_RESTRICT
#define ComGoogleCommonBaseFunctionalEquivalence_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonBaseFunctionalEquivalence_RESTRICT

#if !defined (_ComGoogleCommonBaseFunctionalEquivalence_) && (ComGoogleCommonBaseFunctionalEquivalence_INCLUDE_ALL || ComGoogleCommonBaseFunctionalEquivalence_INCLUDE)
#define _ComGoogleCommonBaseFunctionalEquivalence_

@protocol ComGoogleCommonBaseFunction;

#define ComGoogleCommonBaseEquivalence_RESTRICT 1
#define ComGoogleCommonBaseEquivalence_INCLUDE 1
#include "com/google/common/base/Equivalence.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonBaseFunctionalEquivalence_serialVersionUID 0

@interface ComGoogleCommonBaseFunctionalEquivalence : ComGoogleCommonBaseEquivalence < JavaIoSerializable > {
 @public
  id<ComGoogleCommonBaseFunction> function_;
  ComGoogleCommonBaseEquivalence *resultEquivalence_;
}

- (id)initWithComGoogleCommonBaseFunction:(id<ComGoogleCommonBaseFunction>)function
       withComGoogleCommonBaseEquivalence:(ComGoogleCommonBaseEquivalence *)resultEquivalence;
- (BOOL)doEquivalentWithId:(id)a
                    withId:(id)b;
- (int)doHashWithId:(id)a;
- (BOOL)isEqual:(id)obj;
- (NSUInteger)hash;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonBaseFunctionalEquivalence *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonBaseFunctionalEquivalence, function_, id<ComGoogleCommonBaseFunction>)
J2OBJC_FIELD_SETTER(ComGoogleCommonBaseFunctionalEquivalence, resultEquivalence_, ComGoogleCommonBaseEquivalence *)
#endif
