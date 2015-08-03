//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/handler/MockHandlerImpl.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalHandlerMockHandlerImpl_H_
#define _OrgMockitoInternalHandlerMockHandlerImpl_H_

@class OrgMockitoInternalInvocationMatchersBinder;
@class OrgMockitoInternalStubbingInvocationContainerImpl;
@protocol JavaUtilList;
@protocol OrgMockitoInternalProgressMockingProgress;
@protocol OrgMockitoInternalStubbingInvocationContainer;
@protocol OrgMockitoInvocationInvocation;
@protocol OrgMockitoMockMockCreationSettings;
@protocol OrgMockitoStubbingVoidMethodStubbable;

#import "JreEmulation.h"
#include "org/mockito/internal/InternalMockHandler.h"

#define OrgMockitoInternalHandlerMockHandlerImpl_serialVersionUID -2917871070982574165

@interface OrgMockitoInternalHandlerMockHandlerImpl : NSObject < OrgMockitoInternalInternalMockHandler > {
 @public
  OrgMockitoInternalStubbingInvocationContainerImpl *invocationContainerImpl_;
  OrgMockitoInternalInvocationMatchersBinder *matchersBinder_;
  id<OrgMockitoInternalProgressMockingProgress> mockingProgress_;
  id<OrgMockitoMockMockCreationSettings> mockSettings_;
}

- (id)initWithOrgMockitoMockMockCreationSettings:(id<OrgMockitoMockMockCreationSettings>)mockSettings;
- (id)handleWithOrgMockitoInvocationInvocation:(id<OrgMockitoInvocationInvocation>)invocation;
- (id<OrgMockitoStubbingVoidMethodStubbable>)voidMethodStubbableWithId:(id)mock;
- (id<OrgMockitoMockMockCreationSettings>)getMockSettings;
- (void)setAnswersForStubbingWithJavaUtilList:(id<JavaUtilList>)answers;
- (id<OrgMockitoInternalStubbingInvocationContainer>)getInvocationContainer;
- (void)copyAllFieldsTo:(OrgMockitoInternalHandlerMockHandlerImpl *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalHandlerMockHandlerImpl, invocationContainerImpl_, OrgMockitoInternalStubbingInvocationContainerImpl *)
J2OBJC_FIELD_SETTER(OrgMockitoInternalHandlerMockHandlerImpl, matchersBinder_, OrgMockitoInternalInvocationMatchersBinder *)
J2OBJC_FIELD_SETTER(OrgMockitoInternalHandlerMockHandlerImpl, mockingProgress_, id<OrgMockitoInternalProgressMockingProgress>)
J2OBJC_FIELD_SETTER(OrgMockitoInternalHandlerMockHandlerImpl, mockSettings_, id<OrgMockitoMockMockCreationSettings>)

#endif // _OrgMockitoInternalHandlerMockHandlerImpl_H_
