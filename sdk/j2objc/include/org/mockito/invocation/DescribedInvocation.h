//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/invocation/DescribedInvocation.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInvocationDescribedInvocation_H_
#define _OrgMockitoInvocationDescribedInvocation_H_

@protocol OrgMockitoInvocationLocation;

#import "JreEmulation.h"
#include "org/mockito/exceptions/PrintableInvocation.h"

@protocol OrgMockitoInvocationDescribedInvocation < OrgMockitoExceptionsPrintableInvocation, NSObject, JavaObject >
- (NSString *)description;
- (id<OrgMockitoInvocationLocation>)getLocation;
@end

#endif // _OrgMockitoInvocationDescribedInvocation_H_
