//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/configuration/DefaultMockitoConfiguration.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoConfigurationDefaultMockitoConfiguration_H_
#define _OrgMockitoConfigurationDefaultMockitoConfiguration_H_

@protocol OrgMockitoConfigurationAnnotationEngine;
@protocol OrgMockitoReturnValues;
@protocol OrgMockitoStubbingAnswer;

#import "JreEmulation.h"
#include "org/mockito/configuration/IMockitoConfiguration.h"

@interface OrgMockitoConfigurationDefaultMockitoConfiguration : NSObject < OrgMockitoConfigurationIMockitoConfiguration > {
}

- (id<OrgMockitoReturnValues>)getReturnValues;
- (id<OrgMockitoStubbingAnswer>)getDefaultAnswer;
- (id<OrgMockitoConfigurationAnnotationEngine>)getAnnotationEngine;
- (BOOL)cleansStackTrace;
- (BOOL)enableClassCache;
- (id)init;
@end

#endif // _OrgMockitoConfigurationDefaultMockitoConfiguration_H_
