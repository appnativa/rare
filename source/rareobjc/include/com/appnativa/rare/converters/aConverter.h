//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/converters/aConverter.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaConverter_H_
#define _RAREaConverter_H_

@class IOSClass;
@class JavaIoReader;
@class RAREConverterContext;
@class RAREUTFormatException;
@protocol JavaLangCharSequence;
@protocol JavaLangComparable;
@protocol RAREiPlatformRenderingComponent;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/converters/iDataConverter.h"

@interface RAREaConverter : NSObject < RAREiDataConverter > {
 @public
  IOSClass *objectClass_;
  id<JavaLangComparable> maxValue_;
  id<JavaLangComparable> minValue_;
  id theContext_;
  id<RAREiWidget> widget_;
}

- (id)init;
- (id)createContextWithRAREiWidget:(id<RAREiWidget>)widget
                      withNSString:(NSString *)value;
+ (id<RAREiDataConverter>)createConverterWithRAREiWidget:(id<RAREiWidget>)widget
                                            withNSString:(NSString *)name
                                                 withInt:(int)type;
+ (RAREUTFormatException *)formatExceptionWithRAREiWidget:(id<RAREiWidget>)widget
                                             withNSString:(NSString *)value
                                             withIOSClass:(IOSClass *)cls;
+ (RAREUTFormatException *)formatExceptionWithRAREiWidget:(id<RAREiWidget>)widget
                                             withNSString:(NSString *)format
                                             withNSString:(NSString *)value;
- (id)fromStringWithNSString:(NSString *)string
    withRAREConverterContext:(RAREConverterContext *)ctx;
- (id)objectFromStringWithRAREiWidget:(id<RAREiWidget>)widget
                         withNSString:(NSString *)value;
- (id)objectFromStringWithRAREiWidget:(id<RAREiWidget>)widget
                         withNSString:(NSString *)value
                               withId:(id)context;
- (id<JavaLangCharSequence>)objectToStringWithRAREiWidget:(id<RAREiWidget>)widget
                                                   withId:(id)object;
- (id<JavaLangCharSequence>)objectToStringWithRAREiWidget:(id<RAREiWidget>)widget
                                                   withId:(id)object
                                                   withId:(id)context;
- (BOOL)objectsAreImmutableWithId:(id)context;
- (id<JavaLangCharSequence>)readerToStringWithJavaIoReader:(JavaIoReader *)r;
+ (void)showRangeErrorWithJavaLangComparable:(id<JavaLangComparable>)minValue
                      withJavaLangComparable:(id<JavaLangComparable>)maxValue;
- (BOOL)supportFromStringWithNSString:(NSString *)string
             withRAREConverterContext:(RAREConverterContext *)ctx;
- (BOOL)supportToStringWithId:(id)object
     withRAREConverterContext:(RAREConverterContext *)ctx;
- (id<JavaLangCharSequence>)toStringWithId:(id)object
                  withRAREConverterContext:(RAREConverterContext *)ctx;
- (void)setMaxValueWithJavaLangComparable:(id<JavaLangComparable>)maxValue;
- (void)setMinValueWithJavaLangComparable:(id<JavaLangComparable>)minValue;
- (void)setObjectClassWithIOSClass:(IOSClass *)cls;
- (void)setWidgetWithRAREiWidget:(id<RAREiWidget>)widget;
- (id<JavaLangComparable>)getMaxValue;
- (id<JavaLangComparable>)getMinValue;
- (IOSClass *)getObjectClassWithId:(id)context;
+ (NSString *)getRangeErrorWithJavaLangComparable:(id<JavaLangComparable>)minValue
                           withJavaLangComparable:(id<JavaLangComparable>)maxValue;
- (id<RAREiPlatformRenderingComponent>)getRendererWithRAREiWidget:(id<RAREiWidget>)widget
                                                           withId:(id)context;
- (id<RAREiWidget>)getWidget;
- (id<RAREiWidget>)getWidgetEx;
- (void)copyAllFieldsTo:(RAREaConverter *)other;
@end

J2OBJC_FIELD_SETTER(RAREaConverter, objectClass_, IOSClass *)
J2OBJC_FIELD_SETTER(RAREaConverter, maxValue_, id<JavaLangComparable>)
J2OBJC_FIELD_SETTER(RAREaConverter, minValue_, id<JavaLangComparable>)
J2OBJC_FIELD_SETTER(RAREaConverter, theContext_, id)
J2OBJC_FIELD_SETTER(RAREaConverter, widget_, id<RAREiWidget>)

typedef RAREaConverter ComAppnativaRareConvertersAConverter;

#endif // _RAREaConverter_H_
