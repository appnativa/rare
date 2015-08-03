//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/util/MockCreationValidator.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalUtilMockCreationValidator_H_
#define _OrgMockitoInternalUtilMockCreationValidator_H_

@class IOSClass;
@protocol JavaUtilCollection;

#import "JreEmulation.h"

@interface OrgMockitoInternalUtilMockCreationValidator : NSObject {
}

- (BOOL)isTypeMockableWithIOSClass:(IOSClass *)clz;
- (void)validateTypeWithIOSClass:(IOSClass *)classToMock;
- (void)validateExtraInterfacesWithIOSClass:(IOSClass *)classToMock
                     withJavaUtilCollection:(id<JavaUtilCollection>)extraInterfaces;
- (void)validateMockedTypeWithIOSClass:(IOSClass *)classToMock
                                withId:(id)spiedInstance;
- (void)validateDelegatedInstanceWithIOSClass:(IOSClass *)classToMock
                                       withId:(id)delegatedInstance;
- (id)init;
@end

#endif // _OrgMockitoInternalUtilMockCreationValidator_H_
