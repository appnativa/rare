//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/junit/build_result/java/org/hamcrest/core/IsSame.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgHamcrestCoreIsSame_H_
#define _OrgHamcrestCoreIsSame_H_

@protocol OrgHamcrestDescription;
@protocol OrgHamcrestMatcher;

#import "JreEmulation.h"
#include "org/hamcrest/BaseMatcher.h"

@interface OrgHamcrestCoreIsSame : OrgHamcrestBaseMatcher {
 @public
  id object_;
}

- (id)initWithId:(id)object;
- (BOOL)matchesWithId:(id)arg;
- (void)describeToWithOrgHamcrestDescription:(id<OrgHamcrestDescription>)description_;
+ (id<OrgHamcrestMatcher>)sameInstanceWithId:(id)object;
- (void)copyAllFieldsTo:(OrgHamcrestCoreIsSame *)other;
@end

J2OBJC_FIELD_SETTER(OrgHamcrestCoreIsSame, object_, id)

#endif // _OrgHamcrestCoreIsSame_H_
