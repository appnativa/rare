//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/stubbing/defaultanswers/ReturnsDeepStubs.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalStubbingDefaultanswersReturnsDeepStubs_H_
#define _OrgMockitoInternalStubbingDefaultanswersReturnsDeepStubs_H_

@class OrgMockitoInternalStubbingInvocationContainerImpl;
@protocol OrgMockitoInvocationInvocationOnMock;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "org/mockito/stubbing/Answer.h"

#define OrgMockitoInternalStubbingDefaultanswersReturnsDeepStubs_serialVersionUID -6926328908792880098

@interface OrgMockitoInternalStubbingDefaultanswersReturnsDeepStubs : NSObject < OrgMockitoStubbingAnswer, JavaIoSerializable > {
 @public
  id<OrgMockitoStubbingAnswer> delegate_;
}

- (id)answerWithOrgMockitoInvocationInvocationOnMock:(id<OrgMockitoInvocationInvocationOnMock>)invocation;
- (id)getMockWithOrgMockitoInvocationInvocationOnMock:(id<OrgMockitoInvocationInvocationOnMock>)invocation;
- (id)recordDeepStubMockWithOrgMockitoInvocationInvocationOnMock:(id<OrgMockitoInvocationInvocationOnMock>)invocation
           withOrgMockitoInternalStubbingInvocationContainerImpl:(OrgMockitoInternalStubbingInvocationContainerImpl *)container;
- (id)init;
- (void)copyAllFieldsTo:(OrgMockitoInternalStubbingDefaultanswersReturnsDeepStubs *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalStubbingDefaultanswersReturnsDeepStubs, delegate_, id<OrgMockitoStubbingAnswer>)

@interface OrgMockitoInternalStubbingDefaultanswersReturnsDeepStubs_$1 : NSObject < OrgMockitoStubbingAnswer > {
 @public
  id val$mock_;
}

- (id)answerWithOrgMockitoInvocationInvocationOnMock:(id<OrgMockitoInvocationInvocationOnMock>)invocation;
- (id)initWithId:(id)capture$0;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalStubbingDefaultanswersReturnsDeepStubs_$1, val$mock_, id)

#endif // _OrgMockitoInternalStubbingDefaultanswersReturnsDeepStubs_H_