//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/lang/reflect/Member.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaLangReflectMember_H_
#define _JavaLangReflectMember_H_

@class IOSClass;

#import "JreEmulation.h"

#define JavaLangReflectMember_DECLARED 1
#define JavaLangReflectMember_PUBLIC 0

@protocol JavaLangReflectMember < NSObject, JavaObject >
- (IOSClass *)getDeclaringClass;
- (int)getModifiers;
- (NSString *)getName;
- (BOOL)isSynthetic;
@end

@interface JavaLangReflectMember : NSObject {
}
+ (int)PUBLIC;
+ (int)DECLARED;
@end

#endif // _JavaLangReflectMember_H_
