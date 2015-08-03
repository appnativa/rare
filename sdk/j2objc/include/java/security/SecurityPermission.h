//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/security/SecurityPermission.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaSecuritySecurityPermission_H_
#define _JavaSecuritySecurityPermission_H_

@class JavaSecurityPermission;

#import "JreEmulation.h"
#include "java/security/BasicPermission.h"

@interface JavaSecuritySecurityPermission : JavaSecurityBasicPermission {
}

- (id)initWithNSString:(NSString *)name;
- (id)initWithNSString:(NSString *)name
          withNSString:(NSString *)action;
- (NSString *)getActions;
- (BOOL)impliesWithJavaSecurityPermission:(JavaSecurityPermission *)permission;
@end

#endif // _JavaSecuritySecurityPermission_H_
