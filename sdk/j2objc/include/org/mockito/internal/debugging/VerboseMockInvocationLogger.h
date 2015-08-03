//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/debugging/VerboseMockInvocationLogger.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalDebuggingVerboseMockInvocationLogger_H_
#define _OrgMockitoInternalDebuggingVerboseMockInvocationLogger_H_

@class JavaIoPrintStream;
@protocol OrgMockitoInvocationDescribedInvocation;
@protocol OrgMockitoListenersMethodInvocationReport;

#import "JreEmulation.h"
#include "org/mockito/listeners/InvocationListener.h"

@interface OrgMockitoInternalDebuggingVerboseMockInvocationLogger : NSObject < OrgMockitoListenersInvocationListener > {
 @public
  JavaIoPrintStream *printStream_;
  int mockInvocationsCounter_;
}

- (id)init;
- (id)initWithJavaIoPrintStream:(JavaIoPrintStream *)printStream;
- (void)reportInvocationWithOrgMockitoListenersMethodInvocationReport:(id<OrgMockitoListenersMethodInvocationReport>)methodInvocationReport;
- (void)printReturnedValueOrThrowableWithOrgMockitoListenersMethodInvocationReport:(id<OrgMockitoListenersMethodInvocationReport>)methodInvocationReport;
- (void)printStubInfoWithOrgMockitoListenersMethodInvocationReport:(id<OrgMockitoListenersMethodInvocationReport>)methodInvocationReport;
- (void)printHeader;
- (void)printInvocationWithOrgMockitoInvocationDescribedInvocation:(id<OrgMockitoInvocationDescribedInvocation>)invocation;
- (void)printFooter;
- (void)printlnIndentedWithNSString:(NSString *)message;
- (void)copyAllFieldsTo:(OrgMockitoInternalDebuggingVerboseMockInvocationLogger *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalDebuggingVerboseMockInvocationLogger, printStream_, JavaIoPrintStream *)

#endif // _OrgMockitoInternalDebuggingVerboseMockInvocationLogger_H_