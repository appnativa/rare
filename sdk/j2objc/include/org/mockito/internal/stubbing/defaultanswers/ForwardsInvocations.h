//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/stubbing/defaultanswers/ForwardsInvocations.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalStubbingDefaultanswersForwardsInvocations_H_
#define _OrgMockitoInternalStubbingDefaultanswersForwardsInvocations_H_

@protocol OrgMockitoInvocationInvocationOnMock;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "org/mockito/stubbing/Answer.h"

#define OrgMockitoInternalStubbingDefaultanswersForwardsInvocations_serialVersionUID -8343690268123254910

@interface OrgMockitoInternalStubbingDefaultanswersForwardsInvocations : NSObject < OrgMockitoStubbingAnswer, JavaIoSerializable > {
 @public
  id delegatedObject_;
}

- (id)initWithId:(id)delegatedObject;
- (id)answerWithOrgMockitoInvocationInvocationOnMock:(id<OrgMockitoInvocationInvocationOnMock>)invocation;
- (void)copyAllFieldsTo:(OrgMockitoInternalStubbingDefaultanswersForwardsInvocations *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalStubbingDefaultanswersForwardsInvocations, delegatedObject_, id)

#endif // _OrgMockitoInternalStubbingDefaultanswersForwardsInvocations_H_
