//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/matchers/EqualsWithDelta.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalMatchersEqualsWithDelta_H_
#define _OrgMockitoInternalMatchersEqualsWithDelta_H_

@protocol OrgHamcrestDescription;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "org/mockito/ArgumentMatcher.h"

#define OrgMockitoInternalMatchersEqualsWithDelta_serialVersionUID 5066980489920383664

@interface OrgMockitoInternalMatchersEqualsWithDelta : OrgMockitoArgumentMatcher < JavaIoSerializable > {
 @public
  NSNumber *wanted_;
  NSNumber *delta_;
}

- (id)initWithNSNumber:(NSNumber *)value
          withNSNumber:(NSNumber *)delta;
- (BOOL)matchesWithId:(id)actual;
- (void)describeToWithOrgHamcrestDescription:(id<OrgHamcrestDescription>)description_;
- (void)copyAllFieldsTo:(OrgMockitoInternalMatchersEqualsWithDelta *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalMatchersEqualsWithDelta, wanted_, NSNumber *)
J2OBJC_FIELD_SETTER(OrgMockitoInternalMatchersEqualsWithDelta, delta_, NSNumber *)

#endif // _OrgMockitoInternalMatchersEqualsWithDelta_H_