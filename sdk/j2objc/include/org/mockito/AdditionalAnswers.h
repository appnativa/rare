//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/AdditionalAnswers.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoAdditionalAnswers_H_
#define _OrgMockitoAdditionalAnswers_H_

@class OrgMockitoInternalStubbingAnswersReturnsArgumentAt;
@protocol JavaUtilCollection;
@protocol OrgMockitoStubbingAnswer;

#import "JreEmulation.h"

@interface OrgMockitoAdditionalAnswers : NSObject {
}

+ (OrgMockitoInternalStubbingAnswersReturnsArgumentAt *)RETURNS_FIRST_ARGUMENT;
+ (OrgMockitoInternalStubbingAnswersReturnsArgumentAt *)RETURNS_SECOND_ARGUMENT;
+ (OrgMockitoInternalStubbingAnswersReturnsArgumentAt *)RETURNS_LAST_ARGUMENT;
+ (id<OrgMockitoStubbingAnswer>)returnsFirstArg;
+ (id<OrgMockitoStubbingAnswer>)returnsSecondArg;
+ (id<OrgMockitoStubbingAnswer>)returnsLastArg;
+ (id<OrgMockitoStubbingAnswer>)returnsArgAtWithInt:(int)position;
+ (id<OrgMockitoStubbingAnswer>)delegatesToWithId:(id)delegate;
+ (id<OrgMockitoStubbingAnswer>)returnsElementsOfWithJavaUtilCollection:(id<JavaUtilCollection>)elements;
- (id)init;
@end

#endif // _OrgMockitoAdditionalAnswers_H_
