//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/configuration/injection/SpyOnInjectedFieldsHandler.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalConfigurationInjectionSpyOnInjectedFieldsHandler_H_
#define _OrgMockitoInternalConfigurationInjectionSpyOnInjectedFieldsHandler_H_

@class JavaLangReflectField;
@protocol JavaUtilSet;

#import "JreEmulation.h"
#include "org/mockito/internal/configuration/injection/MockInjectionStrategy.h"

@interface OrgMockitoInternalConfigurationInjectionSpyOnInjectedFieldsHandler : OrgMockitoInternalConfigurationInjectionMockInjectionStrategy {
}

- (BOOL)processInjectionWithJavaLangReflectField:(JavaLangReflectField *)field
                                          withId:(id)fieldOwner
                                 withJavaUtilSet:(id<JavaUtilSet>)mockCandidates;
- (id)init;
@end

#endif // _OrgMockitoInternalConfigurationInjectionSpyOnInjectedFieldsHandler_H_
