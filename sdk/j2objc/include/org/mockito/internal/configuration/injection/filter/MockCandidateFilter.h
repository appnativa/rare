//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/configuration/injection/filter/MockCandidateFilter.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalConfigurationInjectionFilterMockCandidateFilter_H_
#define _OrgMockitoInternalConfigurationInjectionFilterMockCandidateFilter_H_

@class JavaLangReflectField;
@protocol JavaUtilCollection;
@protocol OrgMockitoInternalConfigurationInjectionFilterOngoingInjecter;

#import "JreEmulation.h"

@protocol OrgMockitoInternalConfigurationInjectionFilterMockCandidateFilter < NSObject, JavaObject >
- (id<OrgMockitoInternalConfigurationInjectionFilterOngoingInjecter>)filterCandidateWithJavaUtilCollection:(id<JavaUtilCollection>)mocks
                                                                                  withJavaLangReflectField:(JavaLangReflectField *)fieldToBeInjected
                                                                                                    withId:(id)fieldInstance;
@end

#endif // _OrgMockitoInternalConfigurationInjectionFilterMockCandidateFilter_H_
