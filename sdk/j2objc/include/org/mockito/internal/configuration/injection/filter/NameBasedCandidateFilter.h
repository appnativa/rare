//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/configuration/injection/filter/NameBasedCandidateFilter.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalConfigurationInjectionFilterNameBasedCandidateFilter_H_
#define _OrgMockitoInternalConfigurationInjectionFilterNameBasedCandidateFilter_H_

@class JavaLangReflectField;
@class OrgMockitoInternalUtilMockUtil;
@protocol JavaUtilCollection;
@protocol OrgMockitoInternalConfigurationInjectionFilterOngoingInjecter;

#import "JreEmulation.h"
#include "org/mockito/internal/configuration/injection/filter/MockCandidateFilter.h"

@interface OrgMockitoInternalConfigurationInjectionFilterNameBasedCandidateFilter : NSObject < OrgMockitoInternalConfigurationInjectionFilterMockCandidateFilter > {
 @public
  id<OrgMockitoInternalConfigurationInjectionFilterMockCandidateFilter> next_;
  OrgMockitoInternalUtilMockUtil *mockUtil_;
}

- (id)initWithOrgMockitoInternalConfigurationInjectionFilterMockCandidateFilter:(id<OrgMockitoInternalConfigurationInjectionFilterMockCandidateFilter>)next;
- (id<OrgMockitoInternalConfigurationInjectionFilterOngoingInjecter>)filterCandidateWithJavaUtilCollection:(id<JavaUtilCollection>)mocks
                                                                                  withJavaLangReflectField:(JavaLangReflectField *)field
                                                                                                    withId:(id)fieldInstance;
- (void)copyAllFieldsTo:(OrgMockitoInternalConfigurationInjectionFilterNameBasedCandidateFilter *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalConfigurationInjectionFilterNameBasedCandidateFilter, next_, id<OrgMockitoInternalConfigurationInjectionFilterMockCandidateFilter>)
J2OBJC_FIELD_SETTER(OrgMockitoInternalConfigurationInjectionFilterNameBasedCandidateFilter, mockUtil_, OrgMockitoInternalUtilMockUtil *)

#endif // _OrgMockitoInternalConfigurationInjectionFilterNameBasedCandidateFilter_H_
