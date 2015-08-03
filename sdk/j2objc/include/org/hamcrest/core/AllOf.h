//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/junit/build_result/java/org/hamcrest/core/AllOf.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgHamcrestCoreAllOf_H_
#define _OrgHamcrestCoreAllOf_H_

@class IOSObjectArray;
@protocol JavaLangIterable;
@protocol OrgHamcrestDescription;
@protocol OrgHamcrestMatcher;

#import "JreEmulation.h"
#include "org/hamcrest/BaseMatcher.h"

@interface OrgHamcrestCoreAllOf : OrgHamcrestBaseMatcher {
 @public
  id<JavaLangIterable> matchers_;
}

- (id)initWithJavaLangIterable:(id<JavaLangIterable>)matchers;
- (BOOL)matchesWithId:(id)o;
- (void)describeToWithOrgHamcrestDescription:(id<OrgHamcrestDescription>)description_;
+ (id<OrgHamcrestMatcher>)allOfWithOrgHamcrestMatcherArray:(IOSObjectArray *)matchers;
+ (id<OrgHamcrestMatcher>)allOfWithJavaLangIterable:(id<JavaLangIterable>)matchers;
- (void)copyAllFieldsTo:(OrgHamcrestCoreAllOf *)other;
@end

J2OBJC_FIELD_SETTER(OrgHamcrestCoreAllOf, matchers_, id<JavaLangIterable>)

#endif // _OrgHamcrestCoreAllOf_H_
