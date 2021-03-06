//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/converters/iDataConverter.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiDataConverter_H_
#define _RAREiDataConverter_H_

@class IOSClass;
@protocol JavaLangCharSequence;
@protocol JavaLangComparable;
@protocol RAREiPlatformRenderingComponent;
@protocol RAREiWidget;

#import "JreEmulation.h"

@protocol RAREiDataConverter < NSObject, JavaObject >
- (id<JavaLangCharSequence>)objectToStringWithRAREiWidget:(id<RAREiWidget>)widget
                                                   withId:(id)object;
- (id<JavaLangCharSequence>)objectToStringWithRAREiWidget:(id<RAREiWidget>)widget
                                                   withId:(id)object
                                                   withId:(id)context;
- (id)createContextWithRAREiWidget:(id<RAREiWidget>)widget
                      withNSString:(NSString *)value;
- (id)objectFromStringWithRAREiWidget:(id<RAREiWidget>)widget
                         withNSString:(NSString *)value;
- (id)objectFromStringWithRAREiWidget:(id<RAREiWidget>)widget
                         withNSString:(NSString *)value
                               withId:(id)context;
- (BOOL)objectsAreImmutableWithId:(id)context;
- (void)setMaxValueWithJavaLangComparable:(id<JavaLangComparable>)max;
- (void)setMinValueWithJavaLangComparable:(id<JavaLangComparable>)min;
- (void)setObjectClassWithIOSClass:(IOSClass *)cls;
- (id<JavaLangComparable>)getMaxValue;
- (id<JavaLangComparable>)getMinValue;
- (IOSClass *)getObjectClassWithId:(id)context;
- (id<RAREiPlatformRenderingComponent>)getRendererWithRAREiWidget:(id<RAREiWidget>)widget
                                                           withId:(id)context;
@end

#define ComAppnativaRareConvertersIDataConverter RAREiDataConverter

#endif // _RAREiDataConverter_H_
