//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/handler/NullResultGuardian.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalHandlerNullResultGuardian_H_
#define _OrgMockitoInternalHandlerNullResultGuardian_H_

@protocol JavaUtilList;
@protocol OrgMockitoInternalStubbingInvocationContainer;
@protocol OrgMockitoInvocationInvocation;
@protocol OrgMockitoMockMockCreationSettings;
@protocol OrgMockitoStubbingVoidMethodStubbable;

#import "JreEmulation.h"
#include "org/mockito/internal/InternalMockHandler.h"

@interface OrgMockitoInternalHandlerNullResultGuardian : NSObject < OrgMockitoInternalInternalMockHandler > {
 @public
  id<OrgMockitoInternalInternalMockHandler> delegate_;
}

- (id)initWithOrgMockitoInternalInternalMockHandler:(id<OrgMockitoInternalInternalMockHandler>)delegate;
- (id)handleWithOrgMockitoInvocationInvocation:(id<OrgMockitoInvocationInvocation>)invocation;
- (id<OrgMockitoMockMockCreationSettings>)getMockSettings;
- (id<OrgMockitoStubbingVoidMethodStubbable>)voidMethodStubbableWithId:(id)mock;
- (void)setAnswersForStubbingWithJavaUtilList:(id<JavaUtilList>)answers;
- (id<OrgMockitoInternalStubbingInvocationContainer>)getInvocationContainer;
- (void)copyAllFieldsTo:(OrgMockitoInternalHandlerNullResultGuardian *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalHandlerNullResultGuardian, delegate_, id<OrgMockitoInternalInternalMockHandler>)

#endif // _OrgMockitoInternalHandlerNullResultGuardian_H_
