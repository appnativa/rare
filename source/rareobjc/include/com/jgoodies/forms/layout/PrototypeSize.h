//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/jgoodies/forms/layout/PrototypeSize.java
//
//  Created by decoteaud on 5/11/15.
//

#ifndef _RAREJGPrototypeSize_H_
#define _RAREJGPrototypeSize_H_

@protocol JavaUtilList;
@protocol RAREJGFormLayout_Measure;
@protocol RAREiParentComponent;

#import "JreEmulation.h"
#include "com/jgoodies/forms/layout/Size.h"

@interface RAREJGPrototypeSize : NSObject < RAREJGSize > {
 @public
  NSString *prototype_;
}

- (id)initWithNSString:(NSString *)prototype;
- (NSString *)getPrototype;
- (int)maximumSizeWithRAREiParentComponent:(id<RAREiParentComponent>)container
                          withJavaUtilList:(id<JavaUtilList>)components
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)minMeasure
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)prefMeasure
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)defaultMeasure;
- (BOOL)compressible;
- (NSString *)encodeEx;
- (NSString *)encode;
- (BOOL)isEqual:(id)o;
- (NSUInteger)hash;
- (NSString *)description;
- (void)copyAllFieldsTo:(RAREJGPrototypeSize *)other;
@end

J2OBJC_FIELD_SETTER(RAREJGPrototypeSize, prototype_, NSString *)

typedef RAREJGPrototypeSize ComJgoodiesFormsLayoutPrototypeSize;

#endif // _RAREJGPrototypeSize_H_