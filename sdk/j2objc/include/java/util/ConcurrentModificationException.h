//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: apache_harmony/classlib/modules/luni/src/main/java/java/util/ConcurrentModificationException.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentModificationException_H_
#define _JavaUtilConcurrentModificationException_H_

@class JavaLangThrowable;

#import "JreEmulation.h"
#include "java/lang/RuntimeException.h"

#define JavaUtilConcurrentModificationException_serialVersionUID -3666751008965953603

@interface JavaUtilConcurrentModificationException : JavaLangRuntimeException {
}

- (id)init;
- (id)initWithNSString:(NSString *)detailMessage;
- (id)initWithNSString:(NSString *)detailMessage
 withJavaLangThrowable:(JavaLangThrowable *)cause;
- (id)initWithJavaLangThrowable:(JavaLangThrowable *)cause;
@end

#endif // _JavaUtilConcurrentModificationException_H_