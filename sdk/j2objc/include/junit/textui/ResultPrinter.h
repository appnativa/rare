//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/junit/build_result/java/junit/textui/ResultPrinter.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JunitTextuiResultPrinter_H_
#define _JunitTextuiResultPrinter_H_

@class JavaIoPrintStream;
@class JavaLangThrowable;
@class JunitFrameworkAssertionFailedError;
@class JunitFrameworkTestFailure;
@class JunitFrameworkTestResult;
@protocol JavaUtilEnumeration;
@protocol JunitFrameworkTest;

#import "JreEmulation.h"
#include "junit/framework/TestListener.h"

@interface JunitTextuiResultPrinter : NSObject < JunitFrameworkTestListener > {
 @public
  JavaIoPrintStream *fWriter_;
  int fColumn_;
}

- (id)initWithJavaIoPrintStream:(JavaIoPrintStream *)writer;
- (void)printWithJunitFrameworkTestResult:(JunitFrameworkTestResult *)result
                                 withLong:(long long int)runTime;
- (void)printWaitPrompt;
- (void)printHeaderWithLong:(long long int)runTime;
- (void)printErrorsWithJunitFrameworkTestResult:(JunitFrameworkTestResult *)result;
- (void)printFailuresWithJunitFrameworkTestResult:(JunitFrameworkTestResult *)result;
- (void)printDefectsWithJavaUtilEnumeration:(id<JavaUtilEnumeration>)booBoos
                                    withInt:(int)count
                               withNSString:(NSString *)type;
- (void)printDefectWithJunitFrameworkTestFailure:(JunitFrameworkTestFailure *)booBoo
                                         withInt:(int)count;
- (void)printDefectHeaderWithJunitFrameworkTestFailure:(JunitFrameworkTestFailure *)booBoo
                                               withInt:(int)count;
- (void)printDefectTraceWithJunitFrameworkTestFailure:(JunitFrameworkTestFailure *)booBoo;
- (void)printFooterWithJunitFrameworkTestResult:(JunitFrameworkTestResult *)result;
- (NSString *)elapsedTimeAsStringWithLong:(long long int)runTime;
- (JavaIoPrintStream *)getWriter;
- (void)addErrorWithJunitFrameworkTest:(id<JunitFrameworkTest>)test
                 withJavaLangThrowable:(JavaLangThrowable *)t;
- (void)addFailureWithJunitFrameworkTest:(id<JunitFrameworkTest>)test
  withJunitFrameworkAssertionFailedError:(JunitFrameworkAssertionFailedError *)t;
- (void)endTestWithJunitFrameworkTest:(id<JunitFrameworkTest>)test;
- (void)startTestWithJunitFrameworkTest:(id<JunitFrameworkTest>)test;
- (void)copyAllFieldsTo:(JunitTextuiResultPrinter *)other;
@end

J2OBJC_FIELD_SETTER(JunitTextuiResultPrinter, fWriter_, JavaIoPrintStream *)

#endif // _JunitTextuiResultPrinter_H_
