//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/InternalMockHandler.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalInternalMockHandler_H_
#define _OrgMockitoInternalInternalMockHandler_H_

@protocol JavaUtilList;
@protocol OrgMockitoInternalStubbingInvocationContainer;
@protocol OrgMockitoMockMockCreationSettings;
@protocol OrgMockitoStubbingVoidMethodStubbable;

#import "JreEmulation.h"
#include "org/mockito/invocation/MockHandler.h"

@protocol OrgMockitoInternalInternalMockHandler < OrgMockitoInvocationMockHandler, NSObject, JavaObject >
- (id<OrgMockitoMockMockCreationSettings>)getMockSettings;
- (id<OrgMockitoStubbingVoidMethodStubbable>)voidMethodStubbableWithId:(id)mock;
- (void)setAnswersForStubbingWithJavaUtilList:(id<JavaUtilList>)answers;
- (id<OrgMockitoInternalStubbingInvocationContainer>)getInvocationContainer;
@end

#endif // _OrgMockitoInternalInternalMockHandler_H_
