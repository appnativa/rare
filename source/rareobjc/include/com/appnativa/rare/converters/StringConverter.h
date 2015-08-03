//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/converters/StringConverter.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREStringConverter_H_
#define _RAREStringConverter_H_

@class IOSClass;
@class RAREConverterContext;
@class RAREUTCharArray;
@protocol JavaLangCharSequence;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/converters/aConverter.h"
#include "java/lang/ThreadLocal.h"

@interface RAREStringConverter : RAREaConverter {
}

+ (RAREConverterContext *)EXPANDER_CONTEXT;
+ (void)setEXPANDER_CONTEXT:(RAREConverterContext *)EXPANDER_CONTEXT;
+ (RAREConverterContext *)PASSWORD_CONTEXT;
+ (void)setPASSWORD_CONTEXT:(RAREConverterContext *)PASSWORD_CONTEXT;
+ (RAREConverterContext *)TITLECASE_CONTEXT;
+ (void)setTITLECASE_CONTEXT:(RAREConverterContext *)TITLECASE_CONTEXT;
+ (RAREConverterContext *)TITLECASE_CLEAN_CONTEXT;
+ (void)setTITLECASE_CLEAN_CONTEXT:(RAREConverterContext *)TITLECASE_CLEAN_CONTEXT;
+ (RAREConverterContext *)RESOURCE_CONTEXT;
+ (void)setRESOURCE_CONTEXT:(RAREConverterContext *)RESOURCE_CONTEXT;
+ (RAREConverterContext *)HTML_CONTEXT;
+ (void)setHTML_CONTEXT:(RAREConverterContext *)HTML_CONTEXT;
+ (RAREConverterContext *)CAPITALIZE_CONTEXT;
+ (void)setCAPITALIZE_CONTEXT:(RAREConverterContext *)CAPITALIZE_CONTEXT;
+ (RAREConverterContext *)CAPITALIZE_CLEAN_CONTEXT;
+ (void)setCAPITALIZE_CLEAN_CONTEXT:(RAREConverterContext *)CAPITALIZE_CLEAN_CONTEXT;
+ (JavaLangThreadLocal *)perThreadCharArray;
+ (void)setPerThreadCharArray:(JavaLangThreadLocal *)perThreadCharArray;
- (id)init;
- (id)createContextWithRAREiWidget:(id<RAREiWidget>)widget
                      withNSString:(NSString *)value;
- (id)objectFromStringWithRAREiWidget:(id<RAREiWidget>)widget
                         withNSString:(NSString *)value
                               withId:(id)context;
- (id<JavaLangCharSequence>)objectToStringWithRAREiWidget:(id<RAREiWidget>)widget
                                                   withId:(id)object
                                                   withId:(id)context;
- (BOOL)objectsAreImmutable;
- (IOSClass *)getObjectClassWithId:(id)context;
@end

typedef RAREStringConverter ComAppnativaRareConvertersStringConverter;

@interface RAREStringConverter_$1 : JavaLangThreadLocal {
}

- (RAREUTCharArray *)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

#endif // _RAREStringConverter_H_
