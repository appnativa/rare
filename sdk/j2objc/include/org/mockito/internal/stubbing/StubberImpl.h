//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/stubbing/StubberImpl.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalStubbingStubberImpl_H_
#define _OrgMockitoInternalStubbingStubberImpl_H_

@class IOSClass;
@class JavaLangThrowable;
@class OrgMockitoExceptionsReporter;
@protocol JavaUtilList;
@protocol OrgMockitoStubbingAnswer;

#import "JreEmulation.h"
#include "org/mockito/stubbing/Stubber.h"

@interface OrgMockitoInternalStubbingStubberImpl : NSObject < OrgMockitoStubbingStubber > {
 @public
  id<JavaUtilList> answers_;
  OrgMockitoExceptionsReporter *reporter_;
}

- (id)whenWithId:(id)mock;
- (id<OrgMockitoStubbingStubber>)doReturnWithId:(id)toBeReturned;
- (id<OrgMockitoStubbingStubber>)doThrowWithJavaLangThrowable:(JavaLangThrowable *)toBeThrown;
- (id<OrgMockitoStubbingStubber>)doThrowWithIOSClass:(IOSClass *)toBeThrown;
- (id<OrgMockitoStubbingStubber>)doNothing;
- (id<OrgMockitoStubbingStubber>)doAnswerWithOrgMockitoStubbingAnswer:(id<OrgMockitoStubbingAnswer>)answer;
- (id<OrgMockitoStubbingStubber>)doCallRealMethod;
- (id)init;
- (void)copyAllFieldsTo:(OrgMockitoInternalStubbingStubberImpl *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalStubbingStubberImpl, answers_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(OrgMockitoInternalStubbingStubberImpl, reporter_, OrgMockitoExceptionsReporter *)

#endif // _OrgMockitoInternalStubbingStubberImpl_H_
