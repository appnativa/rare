//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/jgoodies/forms/layout/PrototypeSize.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREPrototypeSize_H_
#define _RAREPrototypeSize_H_

@protocol JavaUtilList;
@protocol RAREFormLayout_Measure;
@protocol RAREiParentComponent;

#import "JreEmulation.h"
#include "com/appnativa/jgoodies/forms/layout/Size.h"

@interface RAREPrototypeSize : NSObject < RARESize > {
 @public
  NSString *prototype_;
}

- (id)initWithNSString:(NSString *)prototype;
- (NSString *)getPrototype;
- (int)maximumSizeWithRAREiParentComponent:(id<RAREiParentComponent>)container
                          withJavaUtilList:(id<JavaUtilList>)components
                withRAREFormLayout_Measure:(id<RAREFormLayout_Measure>)minMeasure
                withRAREFormLayout_Measure:(id<RAREFormLayout_Measure>)prefMeasure
                withRAREFormLayout_Measure:(id<RAREFormLayout_Measure>)defaultMeasure;
- (BOOL)compressible;
- (NSString *)encodeEx;
- (NSString *)encode;
- (BOOL)isEqual:(id)o;
- (NSUInteger)hash;
- (NSString *)description;
- (void)copyAllFieldsTo:(RAREPrototypeSize *)other;
@end

J2OBJC_FIELD_SETTER(RAREPrototypeSize, prototype_, NSString *)

typedef RAREPrototypeSize ComAppnativaJgoodiesFormsLayoutPrototypeSize;

#endif // _RAREPrototypeSize_H_
