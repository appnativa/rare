//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/converters/ColorConverter.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREColorConverter_H_
#define _RAREColorConverter_H_

@class IOSClass;
@class RAREConverterContext;
@protocol JavaLangCharSequence;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/converters/aConverter.h"

@interface RAREColorConverter : RAREaConverter {
}

+ (RAREConverterContext *)HEX_CONTEXT;
+ (void)setHEX_CONTEXT:(RAREConverterContext *)HEX_CONTEXT;
+ (RAREConverterContext *)RGB_CONTEXT;
+ (void)setRGB_CONTEXT:(RAREConverterContext *)RGB_CONTEXT;
+ (RAREConverterContext *)ICON_RGB_CONTEXT;
+ (void)setICON_RGB_CONTEXT:(RAREConverterContext *)ICON_RGB_CONTEXT;
+ (RAREConverterContext *)ICON_HEX_CONTEXT;
+ (void)setICON_HEX_CONTEXT:(RAREConverterContext *)ICON_HEX_CONTEXT;
- (id)init;
- (id)createContextWithRAREiWidget:(id<RAREiWidget>)widget
                      withNSString:(NSString *)value;
- (id)objectFromStringWithRAREiWidget:(id<RAREiWidget>)widget
                         withNSString:(NSString *)value
                               withId:(id)context;
- (id<JavaLangCharSequence>)objectToStringWithRAREiWidget:(id<RAREiWidget>)widget
                                                   withId:(id)o
                                                   withId:(id)context;
- (BOOL)objectsAreImmutableWithId:(id)contex;
- (IOSClass *)getObjectClassWithId:(id)context;
@end

typedef RAREColorConverter ComAppnativaRareConvertersColorConverter;

#endif // _RAREColorConverter_H_