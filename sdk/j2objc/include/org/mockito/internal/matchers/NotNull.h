//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/matchers/NotNull.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalMatchersNotNull_H_
#define _OrgMockitoInternalMatchersNotNull_H_

@protocol OrgHamcrestDescription;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "org/mockito/ArgumentMatcher.h"

#define OrgMockitoInternalMatchersNotNull_serialVersionUID 7278261081285153228

@interface OrgMockitoInternalMatchersNotNull : OrgMockitoArgumentMatcher < JavaIoSerializable > {
}

+ (OrgMockitoInternalMatchersNotNull *)NOT_NULL;
- (id)init;
- (BOOL)matchesWithId:(id)actual;
- (void)describeToWithOrgHamcrestDescription:(id<OrgHamcrestDescription>)description_;
@end

#endif // _OrgMockitoInternalMatchersNotNull_H_