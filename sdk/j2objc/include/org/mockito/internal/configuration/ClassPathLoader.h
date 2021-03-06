//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: src/main/java/org/mockito/internal/configuration/ClassPathLoader.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalConfigurationClassPathLoader_H_
#define _OrgMockitoInternalConfigurationClassPathLoader_H_

@class IOSClass;
@class JavaIoInputStream;
@class JavaIoReader;
@protocol JavaUtilList;
@protocol OrgMockitoConfigurationIMockitoConfiguration;
@protocol OrgMockitoPluginsMockMaker;
@protocol OrgMockitoPluginsStackTraceCleanerProvider;

#import "JreEmulation.h"

@interface OrgMockitoInternalConfigurationClassPathLoader : NSObject {
}

+ (id<OrgMockitoPluginsMockMaker>)mockMaker;
+ (id<OrgMockitoPluginsStackTraceCleanerProvider>)stackTraceCleanerProvider;
+ (NSString *)MOCKITO_CONFIGURATION_CLASS_NAME;
- (id<OrgMockitoConfigurationIMockitoConfiguration>)loadConfiguration;
+ (id<OrgMockitoPluginsMockMaker>)getMockMaker;
+ (id<OrgMockitoPluginsStackTraceCleanerProvider>)getStackTraceCleanerProvider;
+ (id<OrgMockitoPluginsMockMaker>)findPlatformMockMaker;
+ (id)findPluginImplementationWithIOSClass:(IOSClass *)pluginType
                                    withId:(id)defaultPlugin;
+ (id<JavaUtilList>)loadImplementationsWithIOSClass:(IOSClass *)service;
+ (id<JavaUtilList>)readerToLinesWithJavaIoReader:(JavaIoReader *)reader;
+ (NSString *)stripCommentAndWhitespaceWithNSString:(NSString *)line;
+ (void)closeQuietlyWithJavaIoInputStream:(JavaIoInputStream *)inArg;
- (id)init;
@end

#endif // _OrgMockitoInternalConfigurationClassPathLoader_H_
