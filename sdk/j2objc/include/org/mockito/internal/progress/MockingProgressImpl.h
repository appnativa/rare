//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/progress/MockingProgressImpl.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalProgressMockingProgressImpl_H_
#define _OrgMockitoInternalProgressMockingProgressImpl_H_

@class IOSClass;
@class OrgMockitoExceptionsReporter;
@class OrgMockitoInternalDebuggingLocalized;
@protocol OrgMockitoInternalListenersMockingProgressListener;
@protocol OrgMockitoInternalProgressArgumentMatcherStorage;
@protocol OrgMockitoInternalProgressIOngoingStubbing;
@protocol OrgMockitoInvocationInvocation;
@protocol OrgMockitoInvocationLocation;
@protocol OrgMockitoVerificationVerificationMode;

#import "JreEmulation.h"
#include "org/mockito/internal/progress/MockingProgress.h"

@interface OrgMockitoInternalProgressMockingProgressImpl : NSObject < OrgMockitoInternalProgressMockingProgress > {
 @public
  OrgMockitoExceptionsReporter *reporter_;
  id<OrgMockitoInternalProgressArgumentMatcherStorage> argumentMatcherStorage_;
  id<OrgMockitoInternalProgressIOngoingStubbing> iOngoingStubbing_;
  OrgMockitoInternalDebuggingLocalized *verificationMode_;
  id<OrgMockitoInvocationLocation> stubbingInProgress_;
  id<OrgMockitoInternalListenersMockingProgressListener> listener_;
}

- (void)reportOngoingStubbingWithOrgMockitoInternalProgressIOngoingStubbing:(id<OrgMockitoInternalProgressIOngoingStubbing>)iOngoingStubbing;
- (id<OrgMockitoInternalProgressIOngoingStubbing>)pullOngoingStubbing;
- (void)verificationStartedWithOrgMockitoVerificationVerificationMode:(id<OrgMockitoVerificationVerificationMode>)verify;
- (void)resetOngoingStubbing;
- (id<OrgMockitoVerificationVerificationMode>)pullVerificationMode;
- (void)stubbingStarted;
- (void)validateState;
- (void)validateMostStuff;
- (void)stubbingCompletedWithOrgMockitoInvocationInvocation:(id<OrgMockitoInvocationInvocation>)invocation;
- (NSString *)description;
- (void)reset;
- (id<OrgMockitoInternalProgressArgumentMatcherStorage>)getArgumentMatcherStorage;
- (void)mockingStartedWithId:(id)mock
                withIOSClass:(IOSClass *)classToMock;
- (void)setListenerWithOrgMockitoInternalListenersMockingProgressListener:(id<OrgMockitoInternalListenersMockingProgressListener>)listener;
- (id)init;
- (void)copyAllFieldsTo:(OrgMockitoInternalProgressMockingProgressImpl *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalProgressMockingProgressImpl, reporter_, OrgMockitoExceptionsReporter *)
J2OBJC_FIELD_SETTER(OrgMockitoInternalProgressMockingProgressImpl, argumentMatcherStorage_, id<OrgMockitoInternalProgressArgumentMatcherStorage>)
J2OBJC_FIELD_SETTER(OrgMockitoInternalProgressMockingProgressImpl, iOngoingStubbing_, id<OrgMockitoInternalProgressIOngoingStubbing>)
J2OBJC_FIELD_SETTER(OrgMockitoInternalProgressMockingProgressImpl, verificationMode_, OrgMockitoInternalDebuggingLocalized *)
J2OBJC_FIELD_SETTER(OrgMockitoInternalProgressMockingProgressImpl, stubbingInProgress_, id<OrgMockitoInvocationLocation>)
J2OBJC_FIELD_SETTER(OrgMockitoInternalProgressMockingProgressImpl, listener_, id<OrgMockitoInternalListenersMockingProgressListener>)

#endif // _OrgMockitoInternalProgressMockingProgressImpl_H_