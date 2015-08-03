//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/security/Policy.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaSecurityPolicy_H_
#define _JavaSecurityPolicy_H_

@class JavaSecurityCodeSource;
@class JavaSecurityPermission;
@class JavaSecurityPermissionCollection;
@class JavaSecurityProtectionDomain;
@class JavaSecurityProvider;
@protocol JavaSecurityPolicy_Parameters;

#import "JreEmulation.h"

@interface JavaSecurityPolicy : NSObject {
}

+ (JavaSecurityPermissionCollection *)UNSUPPORTED_EMPTY_COLLECTION;
- (id)init;
+ (JavaSecurityPolicy *)getInstanceWithNSString:(NSString *)type
              withJavaSecurityPolicy_Parameters:(id<JavaSecurityPolicy_Parameters>)params;
+ (JavaSecurityPolicy *)getInstanceWithNSString:(NSString *)type
              withJavaSecurityPolicy_Parameters:(id<JavaSecurityPolicy_Parameters>)params
                                   withNSString:(NSString *)provider;
+ (JavaSecurityPolicy *)getInstanceWithNSString:(NSString *)type
              withJavaSecurityPolicy_Parameters:(id<JavaSecurityPolicy_Parameters>)params
                       withJavaSecurityProvider:(JavaSecurityProvider *)provider;
- (id<JavaSecurityPolicy_Parameters>)getParameters;
- (JavaSecurityProvider *)getProvider;
- (NSString *)getType;
- (JavaSecurityPermissionCollection *)getPermissionsWithJavaSecurityCodeSource:(JavaSecurityCodeSource *)cs;
- (void)refresh;
- (JavaSecurityPermissionCollection *)getPermissionsWithJavaSecurityProtectionDomain:(JavaSecurityProtectionDomain *)domain;
- (BOOL)impliesWithJavaSecurityProtectionDomain:(JavaSecurityProtectionDomain *)domain
                     withJavaSecurityPermission:(JavaSecurityPermission *)permission;
+ (JavaSecurityPolicy *)getPolicy;
+ (void)setPolicyWithJavaSecurityPolicy:(JavaSecurityPolicy *)policy;
@end

@protocol JavaSecurityPolicy_Parameters < NSObject, JavaObject >
@end

#endif // _JavaSecurityPolicy_H_