//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/verification/checkers/AtLeastXNumberOfInvocationsInOrderChecker.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalVerificationCheckersAtLeastXNumberOfInvocationsInOrderChecker_H_
#define _OrgMockitoInternalVerificationCheckersAtLeastXNumberOfInvocationsInOrderChecker_H_

@class OrgMockitoExceptionsReporter;
@class OrgMockitoInternalInvocationInvocationMarker;
@class OrgMockitoInternalInvocationInvocationMatcher;
@class OrgMockitoInternalInvocationInvocationsFinder;
@protocol JavaUtilList;
@protocol OrgMockitoInternalVerificationApiInOrderContext;

#import "JreEmulation.h"

@interface OrgMockitoInternalVerificationCheckersAtLeastXNumberOfInvocationsInOrderChecker : NSObject {
 @public
  OrgMockitoExceptionsReporter *reporter_;
  OrgMockitoInternalInvocationInvocationsFinder *finder_;
  OrgMockitoInternalInvocationInvocationMarker *invocationMarker_;
  id<OrgMockitoInternalVerificationApiInOrderContext> orderingContext_;
}

- (id)initWithOrgMockitoInternalVerificationApiInOrderContext:(id<OrgMockitoInternalVerificationApiInOrderContext>)orderingContext;
- (void)checkWithJavaUtilList:(id<JavaUtilList>)invocations
withOrgMockitoInternalInvocationInvocationMatcher:(OrgMockitoInternalInvocationInvocationMatcher *)wanted
                      withInt:(int)wantedCount;
- (void)copyAllFieldsTo:(OrgMockitoInternalVerificationCheckersAtLeastXNumberOfInvocationsInOrderChecker *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalVerificationCheckersAtLeastXNumberOfInvocationsInOrderChecker, reporter_, OrgMockitoExceptionsReporter *)
J2OBJC_FIELD_SETTER(OrgMockitoInternalVerificationCheckersAtLeastXNumberOfInvocationsInOrderChecker, finder_, OrgMockitoInternalInvocationInvocationsFinder *)
J2OBJC_FIELD_SETTER(OrgMockitoInternalVerificationCheckersAtLeastXNumberOfInvocationsInOrderChecker, invocationMarker_, OrgMockitoInternalInvocationInvocationMarker *)
J2OBJC_FIELD_SETTER(OrgMockitoInternalVerificationCheckersAtLeastXNumberOfInvocationsInOrderChecker, orderingContext_, id<OrgMockitoInternalVerificationApiInOrderContext>)

#endif // _OrgMockitoInternalVerificationCheckersAtLeastXNumberOfInvocationsInOrderChecker_H_