//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/exceptions/stacktrace/ConditionalStackTraceFilter.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalExceptionsStacktraceConditionalStackTraceFilter_H_
#define _OrgMockitoInternalExceptionsStacktraceConditionalStackTraceFilter_H_

@class JavaLangThrowable;
@class OrgMockitoInternalExceptionsStacktraceStackTraceFilter;
@protocol OrgMockitoConfigurationIMockitoConfiguration;

#import "JreEmulation.h"
#include "java/io/Serializable.h"

#define OrgMockitoInternalExceptionsStacktraceConditionalStackTraceFilter_serialVersionUID -8085849703510292641

@interface OrgMockitoInternalExceptionsStacktraceConditionalStackTraceFilter : NSObject < JavaIoSerializable > {
 @public
  id<OrgMockitoConfigurationIMockitoConfiguration> config_;
  OrgMockitoInternalExceptionsStacktraceStackTraceFilter *filter__;
}

+ (long long int)serialVersionUID;
- (void)filterWithJavaLangThrowable:(JavaLangThrowable *)throwable;
- (id)init;
- (void)copyAllFieldsTo:(OrgMockitoInternalExceptionsStacktraceConditionalStackTraceFilter *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalExceptionsStacktraceConditionalStackTraceFilter, config_, id<OrgMockitoConfigurationIMockitoConfiguration>)
J2OBJC_FIELD_SETTER(OrgMockitoInternalExceptionsStacktraceConditionalStackTraceFilter, filter__, OrgMockitoInternalExceptionsStacktraceStackTraceFilter *)

#endif // _OrgMockitoInternalExceptionsStacktraceConditionalStackTraceFilter_H_
