//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/converters/BooleanConverter.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREBooleanConverter_H_
#define _RAREBooleanConverter_H_

@class IOSClass;
@protocol JavaLangCharSequence;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/converters/aConverter.h"

@interface RAREBooleanConverter : RAREaConverter {
}

- (id)init;
- (IOSClass *)getObjectClassWithId:(id)context;
- (id)objectFromStringWithRAREiWidget:(id<RAREiWidget>)widget
                         withNSString:(NSString *)value
                               withId:(id)context;
- (id<JavaLangCharSequence>)objectToStringWithRAREiWidget:(id<RAREiWidget>)widget
                                                   withId:(id)object
                                                   withId:(id)context;
@end

typedef RAREBooleanConverter ComAppnativaRareConvertersBooleanConverter;

#endif // _RAREBooleanConverter_H_
