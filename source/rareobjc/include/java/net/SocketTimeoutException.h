//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/Projects/porting/src/java/net/SocketTimeoutException.java
//
//  Created by decoteaud on 4/16/14.
//

#ifndef _JavaNetSocketTimeoutException_H_
#define _JavaNetSocketTimeoutException_H_

@class JavaLangThrowable;

#import "JreEmulation.h"
#include "java/io/IOException.h"

@interface JavaNetSocketTimeoutException : JavaIoIOException {
}

- (id)initWithNSString:(NSString *)message
 withJavaLangThrowable:(JavaLangThrowable *)cause;
- (id)initWithNSString:(NSString *)message;
@end

#endif // _JavaNetSocketTimeoutException_H_
