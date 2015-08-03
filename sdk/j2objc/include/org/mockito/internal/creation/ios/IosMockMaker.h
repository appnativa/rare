//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: src/main/java/org/mockito/internal/creation/ios/IosMockMaker.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalCreationIosIosMockMaker_H_
#define _OrgMockitoInternalCreationIosIosMockMaker_H_

@class IOSClass;
@class IOSObjectArray;
@class OrgMockitoInternalCreationIosInvocationHandlerAdapter;
@protocol JavaLangReflectInvocationHandler;
@protocol JavaUtilMap;
@protocol OrgMockitoInvocationMockHandler;
@protocol OrgMockitoMockMockCreationSettings;

#import "JreEmulation.h"
#include "org/mockito/plugins/MockMaker.h"

@interface OrgMockitoInternalCreationIosIosMockMaker : NSObject < OrgMockitoPluginsMockMaker > {
}

+ (id<JavaUtilMap>)classCache;
+ (void)setClassCache:(id<JavaUtilMap>)classCache;
+ (id<JavaUtilMap>)proxyCache;
+ (int)nextClassNameIndex;
+ (int *)nextClassNameIndexRef;
- (id)createMockWithOrgMockitoMockMockCreationSettings:(id<OrgMockitoMockMockCreationSettings>)settings
                   withOrgMockitoInvocationMockHandler:(id<OrgMockitoInvocationMockHandler>)handler;
- (id<OrgMockitoInvocationMockHandler>)getHandlerWithId:(id)mock;
- (void)resetMockWithId:(id)mock
withOrgMockitoInvocationMockHandler:(id<OrgMockitoInvocationMockHandler>)newHandler
withOrgMockitoMockMockCreationSettings:(id<OrgMockitoMockMockCreationSettings>)settings;
- (OrgMockitoInternalCreationIosInvocationHandlerAdapter *)getInvocationHandlerAdapterWithId:(id)mock;
- (IOSClass *)getProxyClassWithIOSClass:(IOSClass *)typeToMock
                      withIOSClassArray:(IOSObjectArray *)interfaces;
+ (IOSClass *)generateClassProxyWithNSString:(NSString *)name
                                withIOSClass:(IOSClass *)classToMock
                           withIOSClassArray:(IOSObjectArray *)interfaces;
- (id)init;
@end

@interface OrgMockitoInternalCreationIosIosMockMaker_ClassProxy : NSObject {
 @public
  id<JavaLangReflectInvocationHandler> $__handler_;
}

- (id<JavaLangReflectInvocationHandler>)getHandler;
- (void)setHandlerWithJavaLangReflectInvocationHandler:(id<JavaLangReflectInvocationHandler>)handler;
- (id)init;
- (void)copyAllFieldsTo:(OrgMockitoInternalCreationIosIosMockMaker_ClassProxy *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalCreationIosIosMockMaker_ClassProxy, $__handler_, id<JavaLangReflectInvocationHandler>)

#endif // _OrgMockitoInternalCreationIosIosMockMaker_H_