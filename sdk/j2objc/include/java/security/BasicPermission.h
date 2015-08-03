//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/security/BasicPermission.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaSecurityBasicPermission_H_
#define _JavaSecurityBasicPermission_H_

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "java/security/Permission.h"

@interface JavaSecurityBasicPermission : JavaSecurityPermission < JavaIoSerializable > {
}

- (id)initWithNSString:(NSString *)name;
- (id)initWithNSString:(NSString *)name
          withNSString:(NSString *)action;
- (NSString *)getActions;
- (BOOL)impliesWithJavaSecurityPermission:(JavaSecurityPermission *)permission;
@end

#endif // _JavaSecurityBasicPermission_H_
