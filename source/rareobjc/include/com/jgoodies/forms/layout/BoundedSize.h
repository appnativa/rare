//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/jgoodies/forms/layout/BoundedSize.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREJGBoundedSize_H_
#define _RAREJGBoundedSize_H_

@protocol JavaUtilList;
@protocol RAREJGFormLayout_Measure;
@protocol RAREiParentComponent;

#import "JreEmulation.h"
#include "com/jgoodies/forms/layout/Size.h"

@interface RAREJGBoundedSize : NSObject < RAREJGSize > {
 @public
  id<RAREJGSize> basis_;
  id<RAREJGSize> lowerBound_;
  id<RAREJGSize> upperBound_;
}

- (id)initWithRAREJGSize:(id<RAREJGSize>)basis
          withRAREJGSize:(id<RAREJGSize>)lowerBound
          withRAREJGSize:(id<RAREJGSize>)upperBound;
- (id<RAREJGSize>)getBasis;
- (id<RAREJGSize>)getLowerBound;
- (id<RAREJGSize>)getUpperBound;
- (int)maximumSizeWithRAREiParentComponent:(id<RAREiParentComponent>)container
                          withJavaUtilList:(id<JavaUtilList>)components
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)minMeasure
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)prefMeasure
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)defaultMeasure;
- (BOOL)compressible;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (NSString *)description;
- (NSString *)encodeEx;
- (NSString *)encode;
- (void)copyAllFieldsTo:(RAREJGBoundedSize *)other;
@end

J2OBJC_FIELD_SETTER(RAREJGBoundedSize, basis_, id<RAREJGSize>)
J2OBJC_FIELD_SETTER(RAREJGBoundedSize, lowerBound_, id<RAREJGSize>)
J2OBJC_FIELD_SETTER(RAREJGBoundedSize, upperBound_, id<RAREJGSize>)

typedef RAREJGBoundedSize ComJgoodiesFormsLayoutBoundedSize;

#endif // _RAREJGBoundedSize_H_
