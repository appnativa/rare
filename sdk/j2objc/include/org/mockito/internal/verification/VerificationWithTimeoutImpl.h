//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/verification/VerificationWithTimeoutImpl.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalVerificationVerificationWithTimeoutImpl_H_
#define _OrgMockitoInternalVerificationVerificationWithTimeoutImpl_H_

@protocol OrgMockitoInternalVerificationApiVerificationData;
@protocol OrgMockitoVerificationVerificationMode;

#import "JreEmulation.h"

@interface OrgMockitoInternalVerificationVerificationWithTimeoutImpl : NSObject {
 @public
  id<OrgMockitoVerificationVerificationMode> delegate_;
  int timeout_;
  int treshhold_;
}

- (id)initWithInt:(int)treshhold
          withInt:(int)millis
withOrgMockitoVerificationVerificationMode:(id<OrgMockitoVerificationVerificationMode>)delegate;
- (void)verifyWithOrgMockitoInternalVerificationApiVerificationData:(id<OrgMockitoInternalVerificationApiVerificationData>)data;
- (void)sleepWithInt:(int)sleep;
- (id<OrgMockitoVerificationVerificationMode>)getDelegate;
- (int)getTimeout;
- (int)getTreshhold;
- (void)copyAllFieldsTo:(OrgMockitoInternalVerificationVerificationWithTimeoutImpl *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalVerificationVerificationWithTimeoutImpl, delegate_, id<OrgMockitoVerificationVerificationMode>)

#endif // _OrgMockitoInternalVerificationVerificationWithTimeoutImpl_H_
