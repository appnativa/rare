//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/util/reflection/BeanPropertySetter.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalUtilReflectionBeanPropertySetter_H_
#define _OrgMockitoInternalUtilReflectionBeanPropertySetter_H_

@class JavaLangReflectField;

#import "JreEmulation.h"

@interface OrgMockitoInternalUtilReflectionBeanPropertySetter : NSObject {
 @public
  id target_;
  BOOL reportNoSetterFound__;
  JavaLangReflectField *field_;
}

+ (NSString *)SET_PREFIX;
- (id)initWithId:(id)target
withJavaLangReflectField:(JavaLangReflectField *)propertyField
     withBoolean:(BOOL)reportNoSetterFound;
- (id)initWithId:(id)target
withJavaLangReflectField:(JavaLangReflectField *)propertyField;
- (BOOL)setWithId:(id)value;
- (NSString *)setterNameWithNSString:(NSString *)fieldName;
- (void)reportNoSetterFound;
- (void)copyAllFieldsTo:(OrgMockitoInternalUtilReflectionBeanPropertySetter *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalUtilReflectionBeanPropertySetter, target_, id)
J2OBJC_FIELD_SETTER(OrgMockitoInternalUtilReflectionBeanPropertySetter, field_, JavaLangReflectField *)

#endif // _OrgMockitoInternalUtilReflectionBeanPropertySetter_H_
