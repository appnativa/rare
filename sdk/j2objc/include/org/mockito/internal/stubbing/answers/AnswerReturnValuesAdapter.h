//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/stubbing/answers/AnswerReturnValuesAdapter.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalStubbingAnswersAnswerReturnValuesAdapter_H_
#define _OrgMockitoInternalStubbingAnswersAnswerReturnValuesAdapter_H_

@protocol OrgMockitoInvocationInvocationOnMock;
@protocol OrgMockitoReturnValues;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "org/mockito/stubbing/Answer.h"

#define OrgMockitoInternalStubbingAnswersAnswerReturnValuesAdapter_serialVersionUID 1418158596876713469

@interface OrgMockitoInternalStubbingAnswersAnswerReturnValuesAdapter : NSObject < OrgMockitoStubbingAnswer, JavaIoSerializable > {
 @public
  id<OrgMockitoReturnValues> returnValues_;
}

- (id)initWithOrgMockitoReturnValues:(id<OrgMockitoReturnValues>)returnValues;
- (id)answerWithOrgMockitoInvocationInvocationOnMock:(id<OrgMockitoInvocationInvocationOnMock>)invocation;
- (void)copyAllFieldsTo:(OrgMockitoInternalStubbingAnswersAnswerReturnValuesAdapter *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalStubbingAnswersAnswerReturnValuesAdapter, returnValues_, id<OrgMockitoReturnValues>)

#endif // _OrgMockitoInternalStubbingAnswersAnswerReturnValuesAdapter_H_