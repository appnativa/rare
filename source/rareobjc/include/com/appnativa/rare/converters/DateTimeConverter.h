//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/converters/DateTimeConverter.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREDateTimeConverter_H_
#define _RAREDateTimeConverter_H_

@class IOSClass;
@class IOSObjectArray;
@class JavaTextDateFormat;
@class RAREDateContext;
@protocol JavaLangCharSequence;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/converters/aConverter.h"
#include "java/util/Date.h"

@interface RAREDateTimeConverter : RAREaConverter {
}

- (id)init;
- (id)createContextWithRAREiWidget:(id<RAREiWidget>)widget
                      withNSString:(NSString *)value;
- (id)objectFromStringWithRAREiWidget:(id<RAREiWidget>)widget
                         withNSString:(NSString *)value
                               withId:(id)context;
- (id)objectFromStringWithRAREiWidget:(id<RAREiWidget>)widget
                         withNSString:(NSString *)value
                               withId:(id)context
                          withBoolean:(BOOL)ignoreExceptions;
- (id<JavaLangCharSequence>)objectToStringWithRAREiWidget:(id<RAREiWidget>)widget
                                                   withId:(id)object
                                                   withId:(id)context;
- (BOOL)objectsAreImmutableWithId:(id)context;
- (IOSClass *)getObjectClassWithId:(id)context;
- (RAREDateContext *)getDateContextWithRAREiWidget:(id<RAREiWidget>)widget
                                            withId:(id)context;
- (JavaTextDateFormat *)getDateFormatWithRAREiWidget:(id<RAREiWidget>)widget
                                              withId:(id)context
                                         withBoolean:(BOOL)display;
- (IOSObjectArray *)getItemFormatsWithRAREiWidget:(id<RAREiWidget>)widget
                              withRAREDateContext:(RAREDateContext *)context;
@end

typedef RAREDateTimeConverter ComAppnativaRareConvertersDateTimeConverter;

@interface RAREDateTimeConverter_BadValueDate : JavaUtilDate {
 @public
  NSString *stringValue_;
}

- (id)initWithNSString:(NSString *)value;
- (NSString *)description;
- (void)copyAllFieldsTo:(RAREDateTimeConverter_BadValueDate *)other;
@end

J2OBJC_FIELD_SETTER(RAREDateTimeConverter_BadValueDate, stringValue_, NSString *)

#endif // _RAREDateTimeConverter_H_
