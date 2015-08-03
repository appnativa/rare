//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/verification/AtMost.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalVerificationAtMost_H_
#define _OrgMockitoInternalVerificationAtMost_H_

@class OrgMockitoInternalInvocationInvocationMarker;
@protocol OrgMockitoInternalVerificationApiVerificationData;

#import "JreEmulation.h"
#include "org/mockito/verification/VerificationMode.h"

@interface OrgMockitoInternalVerificationAtMost : NSObject < OrgMockitoVerificationVerificationMode > {
 @public
  int maxNumberOfInvocations_;
  OrgMockitoInternalInvocationInvocationMarker *invocationMarker_;
}

- (id)initWithInt:(int)maxNumberOfInvocations;
- (void)verifyWithOrgMockitoInternalVerificationApiVerificationData:(id<OrgMockitoInternalVerificationApiVerificationData>)data;
- (void)copyAllFieldsTo:(OrgMockitoInternalVerificationAtMost *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalVerificationAtMost, invocationMarker_, OrgMockitoInternalInvocationInvocationMarker *)

#endif // _OrgMockitoInternalVerificationAtMost_H_