//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/stubbing/defaultanswers/ReturnsMocks.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalStubbingDefaultanswersReturnsMocks_H_
#define _OrgMockitoInternalStubbingDefaultanswersReturnsMocks_H_

@class IOSClass;
@class OrgMockitoInternalMockitoCore;
@protocol OrgMockitoInvocationInvocationOnMock;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "org/mockito/stubbing/Answer.h"

#define OrgMockitoInternalStubbingDefaultanswersReturnsMocks_serialVersionUID -6755257986994634579

@interface OrgMockitoInternalStubbingDefaultanswersReturnsMocks : NSObject < OrgMockitoStubbingAnswer, JavaIoSerializable > {
 @public
  OrgMockitoInternalMockitoCore *mockitoCore_;
  id<OrgMockitoStubbingAnswer> delegate_;
}

- (id)answerWithOrgMockitoInvocationInvocationOnMock:(id<OrgMockitoInvocationInvocationOnMock>)invocation;
- (id)returnValueForWithIOSClass:(IOSClass *)clazz;
- (id)init;
- (void)copyAllFieldsTo:(OrgMockitoInternalStubbingDefaultanswersReturnsMocks *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalStubbingDefaultanswersReturnsMocks, mockitoCore_, OrgMockitoInternalMockitoCore *)
J2OBJC_FIELD_SETTER(OrgMockitoInternalStubbingDefaultanswersReturnsMocks, delegate_, id<OrgMockitoStubbingAnswer>)

#endif // _OrgMockitoInternalStubbingDefaultanswersReturnsMocks_H_
