//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/util/ObjectMethodsGuru.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalUtilObjectMethodsGuru_H_
#define _OrgMockitoInternalUtilObjectMethodsGuru_H_

@class JavaLangReflectMethod;
@protocol OrgMockitoInternalInvocationMockitoMethod;

#import "JreEmulation.h"
#include "java/io/Serializable.h"

#define OrgMockitoInternalUtilObjectMethodsGuru_serialVersionUID -1286718569065470494

@interface OrgMockitoInternalUtilObjectMethodsGuru : NSObject < JavaIoSerializable > {
}

- (BOOL)isToStringWithJavaLangReflectMethod:(JavaLangReflectMethod *)method;
- (BOOL)isToStringWithOrgMockitoInternalInvocationMockitoMethod:(id<OrgMockitoInternalInvocationMockitoMethod>)method;
- (BOOL)isEqualsMethodWithJavaLangReflectMethod:(JavaLangReflectMethod *)method;
- (BOOL)isHashCodeMethodWithJavaLangReflectMethod:(JavaLangReflectMethod *)method;
- (BOOL)isCompareToMethodWithJavaLangReflectMethod:(JavaLangReflectMethod *)method;
- (id)init;
@end

#endif // _OrgMockitoInternalUtilObjectMethodsGuru_H_
