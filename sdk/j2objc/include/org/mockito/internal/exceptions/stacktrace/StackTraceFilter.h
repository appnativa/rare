//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/exceptions/stacktrace/StackTraceFilter.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalExceptionsStacktraceStackTraceFilter_H_
#define _OrgMockitoInternalExceptionsStacktraceStackTraceFilter_H_

@class IOSObjectArray;
@protocol OrgMockitoExceptionsStacktraceStackTraceCleaner;

#import "JreEmulation.h"
#include "java/io/Serializable.h"

#define OrgMockitoInternalExceptionsStacktraceStackTraceFilter_serialVersionUID -5499819791513105700

@interface OrgMockitoInternalExceptionsStacktraceStackTraceFilter : NSObject < JavaIoSerializable > {
}

+ (long long int)serialVersionUID;
+ (id<OrgMockitoExceptionsStacktraceStackTraceCleaner>)cleaner;
+ (void)setCleaner:(id<OrgMockitoExceptionsStacktraceStackTraceCleaner>)cleaner;
- (IOSObjectArray *)filterWithJavaLangStackTraceElementArray:(IOSObjectArray *)target
                                                 withBoolean:(BOOL)keepTop;
- (id)init;
@end

#endif // _OrgMockitoInternalExceptionsStacktraceStackTraceFilter_H_
