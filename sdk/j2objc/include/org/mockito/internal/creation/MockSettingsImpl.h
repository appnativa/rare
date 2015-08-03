//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: src/main/java/org/mockito/internal/creation/MockSettingsImpl.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalCreationMockSettingsImpl_H_
#define _OrgMockitoInternalCreationMockSettingsImpl_H_

@class IOSClass;
@class IOSObjectArray;
@protocol JavaUtilList;
@protocol JavaUtilSet;
@protocol OrgMockitoMockMockName;
@protocol OrgMockitoStubbingAnswer;

#import "JreEmulation.h"
#include "org/mockito/MockSettings.h"
#include "org/mockito/internal/creation/settings/CreationSettings.h"
#include "org/mockito/mock/MockCreationSettings.h"

#define OrgMockitoInternalCreationMockSettingsImpl_serialVersionUID 4475297236197939568

@interface OrgMockitoInternalCreationMockSettingsImpl : OrgMockitoInternalCreationSettingsCreationSettings < OrgMockitoMockSettings, OrgMockitoMockMockCreationSettings > {
}

- (id<OrgMockitoMockSettings>)serializable;
- (id<OrgMockitoMockSettings>)extraInterfacesWithIOSClassArray:(IOSObjectArray *)extraInterfaces;
- (id<OrgMockitoMockMockName>)getMockName;
- (id<JavaUtilSet>)getExtraInterfaces;
- (id)getSpiedInstance;
- (id<OrgMockitoMockSettings>)nameWithNSString:(NSString *)name;
- (id<OrgMockitoMockSettings>)spiedInstanceWithId:(id)spiedInstance;
- (id<OrgMockitoMockSettings>)defaultAnswerWithOrgMockitoStubbingAnswer:(id<OrgMockitoStubbingAnswer>)defaultAnswer;
- (id<OrgMockitoStubbingAnswer>)getDefaultAnswer;
- (BOOL)isSerializable;
- (id<OrgMockitoMockSettings>)verboseLogging;
- (id<OrgMockitoMockSettings>)invocationListenersWithOrgMockitoListenersInvocationListenerArray:(IOSObjectArray *)listeners;
- (BOOL)invocationListenersContainsTypeWithIOSClass:(IOSClass *)clazz;
- (id<JavaUtilList>)getInvocationListeners;
- (BOOL)hasInvocationListeners;
- (IOSClass *)getTypeToMock;
- (id<OrgMockitoMockMockCreationSettings>)confirmWithIOSClass:(IOSClass *)typeToMock;
+ (OrgMockitoInternalCreationSettingsCreationSettings *)validatedSettingsWithIOSClass:(IOSClass *)typeToMock
                               withOrgMockitoInternalCreationSettingsCreationSettings:(OrgMockitoInternalCreationSettingsCreationSettings *)source;
+ (id<JavaUtilSet>)prepareExtraInterfacesWithOrgMockitoInternalCreationSettingsCreationSettings:(OrgMockitoInternalCreationSettingsCreationSettings *)settings;
- (id)init;
@end

#endif // _OrgMockitoInternalCreationMockSettingsImpl_H_
