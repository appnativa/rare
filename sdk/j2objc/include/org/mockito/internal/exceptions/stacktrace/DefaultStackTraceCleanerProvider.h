//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/exceptions/stacktrace/DefaultStackTraceCleanerProvider.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalExceptionsStacktraceDefaultStackTraceCleanerProvider_H_
#define _OrgMockitoInternalExceptionsStacktraceDefaultStackTraceCleanerProvider_H_

@protocol OrgMockitoExceptionsStacktraceStackTraceCleaner;

#import "JreEmulation.h"
#include "org/mockito/plugins/StackTraceCleanerProvider.h"

@interface OrgMockitoInternalExceptionsStacktraceDefaultStackTraceCleanerProvider : NSObject < OrgMockitoPluginsStackTraceCleanerProvider > {
}

- (id<OrgMockitoExceptionsStacktraceStackTraceCleaner>)getStackTraceCleanerWithOrgMockitoExceptionsStacktraceStackTraceCleaner:(id<OrgMockitoExceptionsStacktraceStackTraceCleaner>)defaultCleaner;
- (id)init;
@end

#endif // _OrgMockitoInternalExceptionsStacktraceDefaultStackTraceCleanerProvider_H_
