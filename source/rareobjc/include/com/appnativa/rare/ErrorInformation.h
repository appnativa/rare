//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ErrorInformation.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREErrorInformation_H_
#define _RAREErrorInformation_H_

@class JavaLangException;
@class JavaLangThrowable;

#import "JreEmulation.h"

@interface RAREErrorInformation : NSObject < NSCopying > {
 @public
  id error_;
  NSString *javaStackTrace_;
  NSString *message_;
  NSString *scriptStackTrace_;
  NSString *title_;
  JavaLangException *javaException_;
}

- (id)initWithJavaLangThrowable:(JavaLangThrowable *)error;
- (id)initWithNSString:(NSString *)title
          withNSString:(NSString *)message;
- (id)initWithNSString:(NSString *)title
 withJavaLangThrowable:(JavaLangThrowable *)error;
- (id)initWithId:(id)error
    withNSString:(NSString *)title
    withNSString:(NSString *)message;
- (id)initWithId:(id)error
    withNSString:(NSString *)title
    withNSString:(NSString *)message
    withNSString:(NSString *)stackTrace;
- (id)initWithId:(id)error
    withNSString:(NSString *)title
    withNSString:(NSString *)message
    withNSString:(NSString *)stackTrace
    withNSString:(NSString *)scriptStackTrace;
- (id)clone;
- (NSString *)description;
- (NSString *)toAlertPanelString;
- (void)setErrorWithId:(id)error;
- (void)setMessageWithNSString:(NSString *)message;
- (void)setScriptStackTraceWithNSString:(NSString *)scriptStackTrace;
- (void)setStackTraceWithNSString:(NSString *)stackTrace;
- (void)setTitleWithNSString:(NSString *)title;
- (id)getError;
- (NSString *)getStackTrace;
- (NSString *)getMessage;
- (NSString *)getScriptStackTrace;
- (NSString *)getTitle;
- (BOOL)okWithNSString:(NSString *)s;
- (JavaLangException *)getJavaException;
- (void)setJavaExceptionWithJavaLangException:(JavaLangException *)e;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(RAREErrorInformation *)other;
@end

J2OBJC_FIELD_SETTER(RAREErrorInformation, error_, id)
J2OBJC_FIELD_SETTER(RAREErrorInformation, javaStackTrace_, NSString *)
J2OBJC_FIELD_SETTER(RAREErrorInformation, message_, NSString *)
J2OBJC_FIELD_SETTER(RAREErrorInformation, scriptStackTrace_, NSString *)
J2OBJC_FIELD_SETTER(RAREErrorInformation, title_, NSString *)
J2OBJC_FIELD_SETTER(RAREErrorInformation, javaException_, JavaLangException *)

typedef RAREErrorInformation ComAppnativaRareErrorInformation;

#endif // _RAREErrorInformation_H_
