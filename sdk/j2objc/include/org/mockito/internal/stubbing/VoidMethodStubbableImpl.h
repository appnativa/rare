//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/stubbing/VoidMethodStubbableImpl.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalStubbingVoidMethodStubbableImpl_H_
#define _OrgMockitoInternalStubbingVoidMethodStubbableImpl_H_

@class JavaLangThrowable;
@class OrgMockitoInternalStubbingInvocationContainerImpl;
@protocol OrgMockitoStubbingAnswer;

#import "JreEmulation.h"
#include "org/mockito/stubbing/VoidMethodStubbable.h"

@interface OrgMockitoInternalStubbingVoidMethodStubbableImpl : NSObject < OrgMockitoStubbingVoidMethodStubbable > {
 @public
  id mock_;
  OrgMockitoInternalStubbingInvocationContainerImpl *invocationContainerImpl_;
}

- (id)initWithId:(id)mock
withOrgMockitoInternalStubbingInvocationContainerImpl:(OrgMockitoInternalStubbingInvocationContainerImpl *)invocationContainerImpl;
- (id<OrgMockitoStubbingVoidMethodStubbable>)toThrowWithJavaLangThrowable:(JavaLangThrowable *)throwable;
- (id<OrgMockitoStubbingVoidMethodStubbable>)toReturn;
- (id<OrgMockitoStubbingVoidMethodStubbable>)toAnswerWithOrgMockitoStubbingAnswer:(id<OrgMockitoStubbingAnswer>)answer;
- (id)on;
- (void)copyAllFieldsTo:(OrgMockitoInternalStubbingVoidMethodStubbableImpl *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalStubbingVoidMethodStubbableImpl, mock_, id)
J2OBJC_FIELD_SETTER(OrgMockitoInternalStubbingVoidMethodStubbableImpl, invocationContainerImpl_, OrgMockitoInternalStubbingInvocationContainerImpl *)

#endif // _OrgMockitoInternalStubbingVoidMethodStubbableImpl_H_
