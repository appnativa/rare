//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/stubbing/ConsecutiveStubbing.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalStubbingConsecutiveStubbing_H_
#define _OrgMockitoInternalStubbingConsecutiveStubbing_H_

@class OrgMockitoInternalStubbingInvocationContainerImpl;
@protocol OrgMockitoStubbingAnswer;
@protocol OrgMockitoStubbingDeprecatedOngoingStubbing;
@protocol OrgMockitoStubbingOngoingStubbing;

#import "JreEmulation.h"
#include "org/mockito/internal/stubbing/BaseStubbing.h"

@interface OrgMockitoInternalStubbingConsecutiveStubbing : OrgMockitoInternalStubbingBaseStubbing {
 @public
  OrgMockitoInternalStubbingInvocationContainerImpl *invocationContainerImpl_;
}

- (id)initWithOrgMockitoInternalStubbingInvocationContainerImpl:(OrgMockitoInternalStubbingInvocationContainerImpl *)invocationContainerImpl;
- (id<OrgMockitoStubbingOngoingStubbing>)thenAnswerWithOrgMockitoStubbingAnswer:(id<OrgMockitoStubbingAnswer>)answer;
- (id<OrgMockitoStubbingOngoingStubbing>)thenWithOrgMockitoStubbingAnswer:(id<OrgMockitoStubbingAnswer>)answer;
- (id<OrgMockitoStubbingDeprecatedOngoingStubbing>)toAnswerWithOrgMockitoStubbingAnswer:(id<OrgMockitoStubbingAnswer>)answer;
- (id)getMock;
- (void)copyAllFieldsTo:(OrgMockitoInternalStubbingConsecutiveStubbing *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalStubbingConsecutiveStubbing, invocationContainerImpl_, OrgMockitoInternalStubbingInvocationContainerImpl *)

#endif // _OrgMockitoInternalStubbingConsecutiveStubbing_H_
