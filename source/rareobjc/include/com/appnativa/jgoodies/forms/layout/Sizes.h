//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/jgoodies/forms/layout/Sizes.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESizes_H_
#define _RARESizes_H_

@class IOSObjectArray;
@class RAREConstantSize;
@class RAREConstantSize_Unit;
@class RARESizes_ComponentSize;
@protocol JavaUtilList;
@protocol RAREFormLayout_Measure;
@protocol RAREUnitConverter;
@protocol RAREiParentComponent;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/jgoodies/forms/layout/Size.h"

@interface RARESizes : NSObject {
}

+ (RAREConstantSize *)ZERO;
+ (RAREConstantSize *)DLUX1;
+ (RAREConstantSize *)DLUX2;
+ (RAREConstantSize *)DLUX3;
+ (RAREConstantSize *)DLUX4;
+ (RAREConstantSize *)DLUX5;
+ (RAREConstantSize *)DLUX6;
+ (RAREConstantSize *)DLUX7;
+ (RAREConstantSize *)DLUX8;
+ (RAREConstantSize *)DLUX9;
+ (RAREConstantSize *)DLUX11;
+ (RAREConstantSize *)DLUX14;
+ (RAREConstantSize *)DLUX21;
+ (RAREConstantSize *)DLUY1;
+ (RAREConstantSize *)DLUY2;
+ (RAREConstantSize *)DLUY3;
+ (RAREConstantSize *)DLUY4;
+ (RAREConstantSize *)DLUY5;
+ (RAREConstantSize *)DLUY6;
+ (RAREConstantSize *)DLUY7;
+ (RAREConstantSize *)DLUY8;
+ (RAREConstantSize *)DLUY9;
+ (RAREConstantSize *)DLUY11;
+ (RAREConstantSize *)DLUY14;
+ (RAREConstantSize *)DLUY21;
+ (RARESizes_ComponentSize *)MINIMUM;
+ (RARESizes_ComponentSize *)PREFERRED;
+ (RARESizes_ComponentSize *)DEFAULT;
+ (IOSObjectArray *)VALUES;
+ (id<RAREUnitConverter>)unitConverter;
+ (void)setUnitConverter:(id<RAREUnitConverter>)unitConverter;
+ (RAREConstantSize_Unit *)defaultUnit;
+ (void)setDefaultUnit:(RAREConstantSize_Unit *)defaultUnit;
- (id)init;
+ (RAREConstantSize *)constantWithNSString:(NSString *)encodedValueAndUnit
                               withBoolean:(BOOL)horizontal;
+ (RAREConstantSize *)dluXWithInt:(int)value;
+ (RAREConstantSize *)dluYWithInt:(int)value;
+ (RAREConstantSize *)pixelWithInt:(int)value;
+ (id<RARESize>)boundedWithRARESize:(id<RARESize>)basis
                       withRARESize:(id<RARESize>)lowerBound
                       withRARESize:(id<RARESize>)upperBound;
+ (int)inchAsPixelWithDouble:(double)inArg
  withRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
+ (int)millimeterAsPixelWithDouble:(double)mm
        withRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
+ (int)centimeterAsPixelWithDouble:(double)cm
        withRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
+ (int)pointAsPixelWithInt:(int)pt
withRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
+ (int)dialogUnitXAsPixelWithInt:(int)dluX
      withRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
+ (int)dialogUnitYAsPixelWithInt:(int)dluY
      withRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
+ (id<RAREUnitConverter>)getUnitConverter;
+ (void)setUnitConverterWithRAREUnitConverter:(id<RAREUnitConverter>)newUnitConverter;
+ (RAREConstantSize_Unit *)getDefaultUnit;
+ (void)setDefaultUnitWithRAREConstantSize_Unit:(RAREConstantSize_Unit *)unit;
@end

typedef RARESizes ComAppnativaJgoodiesFormsLayoutSizes;

@interface RARESizes_ComponentSize : NSObject < RARESize > {
 @public
  NSString *name_;
  int ordinal_;
}

+ (int)nextOrdinal;
+ (int *)nextOrdinalRef;
- (id)initWithNSString:(NSString *)name;
- (NSString *)encodeEx;
+ (RARESizes_ComponentSize *)valueOfWithNSString:(NSString *)str;
- (int)maximumSizeWithRAREiParentComponent:(id<RAREiParentComponent>)container
                          withJavaUtilList:(id<JavaUtilList>)components
                withRAREFormLayout_Measure:(id<RAREFormLayout_Measure>)minMeasure
                withRAREFormLayout_Measure:(id<RAREFormLayout_Measure>)prefMeasure
                withRAREFormLayout_Measure:(id<RAREFormLayout_Measure>)defaultMeasure;
- (BOOL)compressible;
- (NSString *)description;
- (NSString *)encode;
- (void)copyAllFieldsTo:(RARESizes_ComponentSize *)other;
@end

J2OBJC_FIELD_SETTER(RARESizes_ComponentSize, name_, NSString *)

#endif // _RARESizes_H_
