//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/exceptions/base/MockitoAssertionError.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoExceptionsBaseMockitoAssertionError_H_
#define _OrgMockitoExceptionsBaseMockitoAssertionError_H_

@class IOSObjectArray;

#import "JreEmulation.h"
#include "java/lang/AssertionError.h"

#define OrgMockitoExceptionsBaseMockitoAssertionError_serialVersionUID 1

@interface OrgMockitoExceptionsBaseMockitoAssertionError : JavaLangAssertionError {
 @public
  IOSObjectArray *unfilteredStackTrace_;
}

- (id)initWithNSString:(NSString *)message;
- (IOSObjectArray *)getUnfilteredStackTrace;
- (void)copyAllFieldsTo:(OrgMockitoExceptionsBaseMockitoAssertionError *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoExceptionsBaseMockitoAssertionError, unfilteredStackTrace_, IOSObjectArray *)

#endif // _OrgMockitoExceptionsBaseMockitoAssertionError_H_
