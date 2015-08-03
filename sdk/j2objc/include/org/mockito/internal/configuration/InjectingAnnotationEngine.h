//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/configuration/InjectingAnnotationEngine.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalConfigurationInjectingAnnotationEngine_H_
#define _OrgMockitoInternalConfigurationInjectingAnnotationEngine_H_

@class IOSClass;
@class JavaLangReflectField;
@protocol JavaLangAnnotationAnnotation;

#import "JreEmulation.h"
#include "org/mockito/configuration/AnnotationEngine.h"

@interface OrgMockitoInternalConfigurationInjectingAnnotationEngine : NSObject < OrgMockitoConfigurationAnnotationEngine > {
 @public
  id<OrgMockitoConfigurationAnnotationEngine> delegate_;
  id<OrgMockitoConfigurationAnnotationEngine> spyAnnotationEngine_;
}

- (id)createMockForWithJavaLangAnnotationAnnotation:(id<JavaLangAnnotationAnnotation>)annotation
                           withJavaLangReflectField:(JavaLangReflectField *)field;
- (void)processWithIOSClass:(IOSClass *)clazz
                     withId:(id)testInstance;
- (void)processInjectMocksWithIOSClass:(IOSClass *)clazz
                                withId:(id)testInstance;
- (void)processIndependentAnnotationsWithIOSClass:(IOSClass *)clazz
                                           withId:(id)testInstance;
- (void)injectMocksWithId:(id)testClassInstance;
- (id)init;
- (void)copyAllFieldsTo:(OrgMockitoInternalConfigurationInjectingAnnotationEngine *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalConfigurationInjectingAnnotationEngine, delegate_, id<OrgMockitoConfigurationAnnotationEngine>)
J2OBJC_FIELD_SETTER(OrgMockitoInternalConfigurationInjectingAnnotationEngine, spyAnnotationEngine_, id<OrgMockitoConfigurationAnnotationEngine>)

#endif // _OrgMockitoInternalConfigurationInjectingAnnotationEngine_H_
