//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/invocation/MockitoMethod.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalInvocationMockitoMethod_H_
#define _OrgMockitoInternalInvocationMockitoMethod_H_

@class IOSClass;
@class IOSObjectArray;
@class JavaLangReflectMethod;

#import "JreEmulation.h"

@protocol OrgMockitoInternalInvocationMockitoMethod < NSObject, JavaObject >
- (NSString *)getName;
- (IOSClass *)getReturnType;
- (IOSObjectArray *)getParameterTypes;
- (IOSObjectArray *)getExceptionTypes;
- (BOOL)isVarArgs;
- (JavaLangReflectMethod *)getJavaMethod;
@end

#endif // _OrgMockitoInternalInvocationMockitoMethod_H_
