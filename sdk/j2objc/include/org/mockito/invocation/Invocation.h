//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/invocation/Invocation.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInvocationInvocation_H_
#define _OrgMockitoInvocationInvocation_H_

@class IOSObjectArray;
@protocol OrgMockitoInvocationLocation;
@protocol OrgMockitoInvocationStubInfo;

#import "JreEmulation.h"
#include "org/mockito/invocation/DescribedInvocation.h"
#include "org/mockito/invocation/InvocationOnMock.h"

@protocol OrgMockitoInvocationInvocation < OrgMockitoInvocationInvocationOnMock, OrgMockitoInvocationDescribedInvocation, NSObject, JavaObject >
- (BOOL)isVerified;
- (int)getSequenceNumber;
- (id<OrgMockitoInvocationLocation>)getLocation;
- (IOSObjectArray *)getRawArguments;
- (void)markVerified;
- (id<OrgMockitoInvocationStubInfo>)stubInfo;
- (void)markStubbedWithOrgMockitoInvocationStubInfo:(id<OrgMockitoInvocationStubInfo>)stubInfo;
- (BOOL)isIgnoredForVerification;
- (void)ignoreForVerification;
@end

#endif // _OrgMockitoInvocationInvocation_H_
