//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/verification/Timeout.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoVerificationTimeout_H_
#define _OrgMockitoVerificationTimeout_H_

@class OrgMockitoInternalVerificationVerificationWithTimeoutImpl;
@protocol OrgMockitoInternalVerificationApiVerificationData;
@protocol OrgMockitoVerificationVerificationMode;

#import "JreEmulation.h"
#include "org/mockito/verification/VerificationWithTimeout.h"

@interface OrgMockitoVerificationTimeout : NSObject < OrgMockitoVerificationVerificationWithTimeout > {
 @public
  OrgMockitoInternalVerificationVerificationWithTimeoutImpl *impl_;
}

- (id)initWithInt:(int)millis
withOrgMockitoVerificationVerificationMode:(id<OrgMockitoVerificationVerificationMode>)delegate;
- (id)initWithInt:(int)treshhold
          withInt:(int)millis
withOrgMockitoVerificationVerificationMode:(id<OrgMockitoVerificationVerificationMode>)delegate;
- (void)verifyWithOrgMockitoInternalVerificationApiVerificationData:(id<OrgMockitoInternalVerificationApiVerificationData>)data;
- (id<OrgMockitoVerificationVerificationMode>)atLeastWithInt:(int)minNumberOfInvocations;
- (id<OrgMockitoVerificationVerificationMode>)atLeastOnce;
- (id<OrgMockitoVerificationVerificationMode>)atMostWithInt:(int)maxNumberOfInvocations;
- (id<OrgMockitoVerificationVerificationMode>)never;
- (id<OrgMockitoVerificationVerificationMode>)only;
- (id<OrgMockitoVerificationVerificationMode>)timesWithInt:(int)wantedNumberOfInvocations;
- (void)copyAllFieldsTo:(OrgMockitoVerificationTimeout *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoVerificationTimeout, impl_, OrgMockitoInternalVerificationVerificationWithTimeoutImpl *)

#endif // _OrgMockitoVerificationTimeout_H_
