//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/matchers/Contains.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalMatchersContains_H_
#define _OrgMockitoInternalMatchersContains_H_

@protocol OrgHamcrestDescription;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "org/mockito/ArgumentMatcher.h"

#define OrgMockitoInternalMatchersContains_serialVersionUID -1909837398271763801

@interface OrgMockitoInternalMatchersContains : OrgMockitoArgumentMatcher < JavaIoSerializable > {
 @public
  NSString *substring_;
}

- (id)initWithNSString:(NSString *)substring;
- (BOOL)matchesWithId:(id)actual;
- (void)describeToWithOrgHamcrestDescription:(id<OrgHamcrestDescription>)description_;
- (void)copyAllFieldsTo:(OrgMockitoInternalMatchersContains *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalMatchersContains, substring_, NSString *)

#endif // _OrgMockitoInternalMatchersContains_H_
