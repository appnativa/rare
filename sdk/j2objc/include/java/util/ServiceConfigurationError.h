//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/ServiceConfigurationError.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilServiceConfigurationError_H_
#define _JavaUtilServiceConfigurationError_H_

@class JavaLangThrowable;

#import "JreEmulation.h"
#include "java/lang/Error.h"

#define JavaUtilServiceConfigurationError_serialVersionUID 74132770414881

@interface JavaUtilServiceConfigurationError : JavaLangError {
}

- (id)initWithNSString:(NSString *)message;
- (id)initWithNSString:(NSString *)message
 withJavaLangThrowable:(JavaLangThrowable *)cause;
@end

#endif // _JavaUtilServiceConfigurationError_H_
