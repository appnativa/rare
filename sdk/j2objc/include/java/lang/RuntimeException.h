//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: apache_harmony/classlib/modules/luni/src/main/java/java/lang/RuntimeException.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaLangRuntimeException_H_
#define _JavaLangRuntimeException_H_

@class JavaLangThrowable;

#import "JreEmulation.h"
#include "java/lang/Exception.h"

#define JavaLangRuntimeException_serialVersionUID -7034897190745766939

@interface JavaLangRuntimeException : JavaLangException {
}

- (id)init;
- (id)initWithNSString:(NSString *)detailMessage;
- (id)initWithNSString:(NSString *)detailMessage
 withJavaLangThrowable:(JavaLangThrowable *)throwable;
- (id)initWithJavaLangThrowable:(JavaLangThrowable *)throwable;
@end

#endif // _JavaLangRuntimeException_H_
