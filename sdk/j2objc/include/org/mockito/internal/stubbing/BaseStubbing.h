//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/stubbing/BaseStubbing.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalStubbingBaseStubbing_H_
#define _OrgMockitoInternalStubbingBaseStubbing_H_

@class IOSClass;
@class IOSObjectArray;
@class JavaLangThrowable;
@protocol OrgMockitoStubbingAnswer;

#import "JreEmulation.h"
#include "org/mockito/stubbing/DeprecatedOngoingStubbing.h"
#include "org/mockito/stubbing/OngoingStubbing.h"

@interface OrgMockitoInternalStubbingBaseStubbing : NSObject < OrgMockitoStubbingOngoingStubbing, OrgMockitoStubbingDeprecatedOngoingStubbing > {
}

- (id<OrgMockitoStubbingOngoingStubbing>)thenReturnWithId:(id)value;
- (id<OrgMockitoStubbingOngoingStubbing>)thenReturnWithId:(id)value
                                        withNSObjectArray:(IOSObjectArray *)values;
- (id<OrgMockitoStubbingOngoingStubbing>)thenThrowWithJavaLangThrowable:(JavaLangThrowable *)throwable;
- (id<OrgMockitoStubbingOngoingStubbing>)thenThrowWithJavaLangThrowableArray:(IOSObjectArray *)throwables;
- (id<OrgMockitoStubbingOngoingStubbing>)thenThrowWithIOSClass:(IOSClass *)throwableClass;
- (id<OrgMockitoStubbingOngoingStubbing>)thenThrowWithIOSClassArray:(IOSObjectArray *)throwableClasses;
- (id<OrgMockitoStubbingOngoingStubbing>)thenCallRealMethod;
- (id<OrgMockitoStubbingDeprecatedOngoingStubbing>)toReturnWithId:(id)value;
- (id<OrgMockitoStubbingDeprecatedOngoingStubbing>)toThrowWithJavaLangThrowable:(JavaLangThrowable *)throwable;
- (id)getMock;
- (id<OrgMockitoStubbingOngoingStubbing>)thenWithOrgMockitoStubbingAnswer:(id<OrgMockitoStubbingAnswer>)param0;
- (id<OrgMockitoStubbingOngoingStubbing>)thenAnswerWithOrgMockitoStubbingAnswer:(id<OrgMockitoStubbingAnswer>)param0;
- (id<OrgMockitoStubbingDeprecatedOngoingStubbing>)toAnswerWithOrgMockitoStubbingAnswer:(id<OrgMockitoStubbingAnswer>)param0;
- (id)init;
@end

#endif // _OrgMockitoInternalStubbingBaseStubbing_H_
