//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/configuration/FieldAnnotationProcessor.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalConfigurationFieldAnnotationProcessor_H_
#define _OrgMockitoInternalConfigurationFieldAnnotationProcessor_H_

@class JavaLangReflectField;
@protocol JavaLangAnnotationAnnotation;

#import "JreEmulation.h"

@protocol OrgMockitoInternalConfigurationFieldAnnotationProcessor < NSObject, JavaObject >
- (id)processWithId:(id<JavaLangAnnotationAnnotation>)annotation
withJavaLangReflectField:(JavaLangReflectField *)field;
@end

#endif // _OrgMockitoInternalConfigurationFieldAnnotationProcessor_H_