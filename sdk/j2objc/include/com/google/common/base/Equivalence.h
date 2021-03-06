//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/base/Equivalence.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonBaseEquivalence_RESTRICT
#define ComGoogleCommonBaseEquivalence_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonBaseEquivalence_RESTRICT
#if ComGoogleCommonBaseEquivalence_Identity_INCLUDE
#define ComGoogleCommonBaseEquivalence_INCLUDE 1
#endif
#if ComGoogleCommonBaseEquivalence_Equals_INCLUDE
#define ComGoogleCommonBaseEquivalence_INCLUDE 1
#endif

#if !defined (_ComGoogleCommonBaseEquivalence_Wrapper_) && (ComGoogleCommonBaseEquivalence_INCLUDE_ALL || ComGoogleCommonBaseEquivalence_Wrapper_INCLUDE)
#define _ComGoogleCommonBaseEquivalence_Wrapper_

@class ComGoogleCommonBaseEquivalence;

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonBaseEquivalence_Wrapper_serialVersionUID 0

@interface ComGoogleCommonBaseEquivalence_Wrapper : NSObject < JavaIoSerializable > {
 @public
  ComGoogleCommonBaseEquivalence *equivalence_;
  id reference_;
}

- (id)initWithComGoogleCommonBaseEquivalence:(ComGoogleCommonBaseEquivalence *)equivalence
                                      withId:(id)reference;
- (id)get;
- (BOOL)isEqual:(id)obj;
- (NSUInteger)hash;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonBaseEquivalence_Wrapper *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonBaseEquivalence_Wrapper, equivalence_, ComGoogleCommonBaseEquivalence *)
J2OBJC_FIELD_SETTER(ComGoogleCommonBaseEquivalence_Wrapper, reference_, id)
#endif

#if !defined (_ComGoogleCommonBaseEquivalence_EquivalentToPredicate_) && (ComGoogleCommonBaseEquivalence_INCLUDE_ALL || ComGoogleCommonBaseEquivalence_EquivalentToPredicate_INCLUDE)
#define _ComGoogleCommonBaseEquivalence_EquivalentToPredicate_

@class ComGoogleCommonBaseEquivalence;

#define ComGoogleCommonBasePredicate_RESTRICT 1
#define ComGoogleCommonBasePredicate_INCLUDE 1
#include "com/google/common/base/Predicate.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonBaseEquivalence_EquivalentToPredicate_serialVersionUID 0

@interface ComGoogleCommonBaseEquivalence_EquivalentToPredicate : NSObject < ComGoogleCommonBasePredicate, JavaIoSerializable > {
 @public
  ComGoogleCommonBaseEquivalence *equivalence_;
  id target_;
}

- (id)initWithComGoogleCommonBaseEquivalence:(ComGoogleCommonBaseEquivalence *)equivalence
                                      withId:(id)target;
- (BOOL)applyWithId:(id)input;
- (BOOL)isEqual:(id)obj;
- (NSUInteger)hash;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonBaseEquivalence_EquivalentToPredicate *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonBaseEquivalence_EquivalentToPredicate, equivalence_, ComGoogleCommonBaseEquivalence *)
J2OBJC_FIELD_SETTER(ComGoogleCommonBaseEquivalence_EquivalentToPredicate, target_, id)
#endif

#if !defined (_ComGoogleCommonBaseEquivalence_) && (ComGoogleCommonBaseEquivalence_INCLUDE_ALL || ComGoogleCommonBaseEquivalence_INCLUDE)
#define _ComGoogleCommonBaseEquivalence_

@class ComGoogleCommonBaseEquivalence_Wrapper;
@protocol ComGoogleCommonBaseFunction;
@protocol ComGoogleCommonBasePredicate;

@interface ComGoogleCommonBaseEquivalence : NSObject {
}

- (id)init;
- (BOOL)equivalentWithId:(id)a
                  withId:(id)b;
- (BOOL)doEquivalentWithId:(id)a
                    withId:(id)b;
- (int)hash__WithId:(id)t;
- (int)doHashWithId:(id)t;
- (ComGoogleCommonBaseEquivalence *)onResultOfWithComGoogleCommonBaseFunction:(id<ComGoogleCommonBaseFunction>)function;
- (ComGoogleCommonBaseEquivalence_Wrapper *)wrapWithId:(id)reference;
- (ComGoogleCommonBaseEquivalence *)pairwise;
- (id<ComGoogleCommonBasePredicate>)equivalentToWithId:(id)target;
+ (ComGoogleCommonBaseEquivalence *)equals;
+ (ComGoogleCommonBaseEquivalence *)identity;
@end
#endif

#if !defined (_ComGoogleCommonBaseEquivalence_Equals_) && (ComGoogleCommonBaseEquivalence_INCLUDE_ALL || ComGoogleCommonBaseEquivalence_Equals_INCLUDE)
#define _ComGoogleCommonBaseEquivalence_Equals_

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonBaseEquivalence_Equals_serialVersionUID 1

@interface ComGoogleCommonBaseEquivalence_Equals : ComGoogleCommonBaseEquivalence < JavaIoSerializable > {
}

+ (ComGoogleCommonBaseEquivalence_Equals *)INSTANCE;
- (BOOL)doEquivalentWithId:(id)a
                    withId:(id)b;
- (int)doHashWithId:(id)o;
- (id)init;
@end
#endif

#if !defined (_ComGoogleCommonBaseEquivalence_Identity_) && (ComGoogleCommonBaseEquivalence_INCLUDE_ALL || ComGoogleCommonBaseEquivalence_Identity_INCLUDE)
#define _ComGoogleCommonBaseEquivalence_Identity_

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonBaseEquivalence_Identity_serialVersionUID 1

@interface ComGoogleCommonBaseEquivalence_Identity : ComGoogleCommonBaseEquivalence < JavaIoSerializable > {
}

+ (ComGoogleCommonBaseEquivalence_Identity *)INSTANCE;
- (BOOL)doEquivalentWithId:(id)a
                    withId:(id)b;
- (int)doHashWithId:(id)o;
- (id)init;
@end
#endif
