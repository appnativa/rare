//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/util/concurrent/UncheckedTimeoutException.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonUtilConcurrentUncheckedTimeoutException_RESTRICT
#define ComGoogleCommonUtilConcurrentUncheckedTimeoutException_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonUtilConcurrentUncheckedTimeoutException_RESTRICT

#if !defined (_ComGoogleCommonUtilConcurrentUncheckedTimeoutException_) && (ComGoogleCommonUtilConcurrentUncheckedTimeoutException_INCLUDE_ALL || ComGoogleCommonUtilConcurrentUncheckedTimeoutException_INCLUDE)
#define _ComGoogleCommonUtilConcurrentUncheckedTimeoutException_

@class JavaLangThrowable;

#define JavaLangRuntimeException_RESTRICT 1
#define JavaLangRuntimeException_INCLUDE 1
#include "java/lang/RuntimeException.h"

#define ComGoogleCommonUtilConcurrentUncheckedTimeoutException_serialVersionUID 0

@interface ComGoogleCommonUtilConcurrentUncheckedTimeoutException : JavaLangRuntimeException {
}

- (id)init;
- (id)initWithNSString:(NSString *)message;
- (id)initWithJavaLangThrowable:(JavaLangThrowable *)cause;
- (id)initWithNSString:(NSString *)message
 withJavaLangThrowable:(JavaLangThrowable *)cause;
@end
#endif
