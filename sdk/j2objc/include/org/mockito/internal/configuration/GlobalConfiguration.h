//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/configuration/GlobalConfiguration.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalConfigurationGlobalConfiguration_H_
#define _OrgMockitoInternalConfigurationGlobalConfiguration_H_

@class JavaLangThreadLocal;
@protocol OrgMockitoConfigurationAnnotationEngine;
@protocol OrgMockitoReturnValues;
@protocol OrgMockitoStubbingAnswer;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "org/mockito/configuration/IMockitoConfiguration.h"

#define OrgMockitoInternalConfigurationGlobalConfiguration_serialVersionUID -2860353062105505938

@interface OrgMockitoInternalConfigurationGlobalConfiguration : NSObject < OrgMockitoConfigurationIMockitoConfiguration, JavaIoSerializable > {
}

+ (long long int)serialVersionUID;
+ (JavaLangThreadLocal *)globalConfiguration;
+ (void)setGlobalConfiguration:(JavaLangThreadLocal *)globalConfiguration;
- (id<OrgMockitoConfigurationIMockitoConfiguration>)getIt;
- (id)init;
- (id<OrgMockitoConfigurationIMockitoConfiguration>)createConfig;
+ (void)validate;
- (id<OrgMockitoReturnValues>)getReturnValues;
- (id<OrgMockitoConfigurationAnnotationEngine>)getAnnotationEngine;
- (BOOL)cleansStackTrace;
- (BOOL)enableClassCache;
- (id<OrgMockitoStubbingAnswer>)getDefaultAnswer;
@end

#endif // _OrgMockitoInternalConfigurationGlobalConfiguration_H_
