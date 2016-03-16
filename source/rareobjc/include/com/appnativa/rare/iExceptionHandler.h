//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/iExceptionHandler.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiExceptionHandler_H_
#define _RAREiExceptionHandler_H_

@class JavaIoWriter;
@class JavaLangThrowable;

#import "JreEmulation.h"

@protocol RAREiExceptionHandler < NSObject, JavaObject >
- (JavaIoWriter *)getErrorWriter;
- (void)handleExceptionWithJavaLangThrowable:(JavaLangThrowable *)e;
- (void)handleScriptExceptionWithJavaLangThrowable:(JavaLangThrowable *)e;
- (void)ignoreExceptionWithNSString:(NSString *)msg
              withJavaLangThrowable:(JavaLangThrowable *)e;
@end

#define ComAppnativaRareIExceptionHandler RAREiExceptionHandler

#endif // _RAREiExceptionHandler_H_
