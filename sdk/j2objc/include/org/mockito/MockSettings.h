//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/MockSettings.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoMockSettings_H_
#define _OrgMockitoMockSettings_H_

@class IOSObjectArray;
@protocol OrgMockitoStubbingAnswer;

#import "JreEmulation.h"
#include "java/io/Serializable.h"

@protocol OrgMockitoMockSettings < JavaIoSerializable, NSObject, JavaObject >
- (id<OrgMockitoMockSettings>)extraInterfacesWithIOSClassArray:(IOSObjectArray *)interfaces;
- (id<OrgMockitoMockSettings>)nameWithNSString:(NSString *)name;
- (id<OrgMockitoMockSettings>)spiedInstanceWithId:(id)instance;
- (id<OrgMockitoMockSettings>)defaultAnswerWithOrgMockitoStubbingAnswer:(id<OrgMockitoStubbingAnswer>)defaultAnswer;
- (id<OrgMockitoMockSettings>)serializable;
- (id<OrgMockitoMockSettings>)verboseLogging;
- (id<OrgMockitoMockSettings>)invocationListenersWithOrgMockitoListenersInvocationListenerArray:(IOSObjectArray *)listeners;
@end

#endif // _OrgMockitoMockSettings_H_
