//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/verification/VerificationDataImpl.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalVerificationVerificationDataImpl_H_
#define _OrgMockitoInternalVerificationVerificationDataImpl_H_

@class OrgMockitoInternalInvocationInvocationMatcher;
@protocol JavaUtilList;
@protocol OrgMockitoInternalStubbingInvocationContainer;

#import "JreEmulation.h"
#include "org/mockito/internal/verification/api/VerificationData.h"

@interface OrgMockitoInternalVerificationVerificationDataImpl : NSObject < OrgMockitoInternalVerificationApiVerificationData > {
 @public
  OrgMockitoInternalInvocationInvocationMatcher *wanted_;
  id<OrgMockitoInternalStubbingInvocationContainer> invocations_;
}

- (id)initWithOrgMockitoInternalStubbingInvocationContainer:(id<OrgMockitoInternalStubbingInvocationContainer>)invocations
          withOrgMockitoInternalInvocationInvocationMatcher:(OrgMockitoInternalInvocationInvocationMatcher *)wanted;
- (id<JavaUtilList>)getAllInvocations;
- (OrgMockitoInternalInvocationInvocationMatcher *)getWanted;
- (void)assertWantedIsVerifiable;
- (void)copyAllFieldsTo:(OrgMockitoInternalVerificationVerificationDataImpl *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalVerificationVerificationDataImpl, wanted_, OrgMockitoInternalInvocationInvocationMatcher *)
J2OBJC_FIELD_SETTER(OrgMockitoInternalVerificationVerificationDataImpl, invocations_, id<OrgMockitoInternalStubbingInvocationContainer>)

#endif // _OrgMockitoInternalVerificationVerificationDataImpl_H_
