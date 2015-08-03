//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/jgoodies/forms/layout/ConstantSize.java
//
//  Created by decoteaud on 5/11/15.
//

#ifndef _RAREJGConstantSize_H_
#define _RAREJGConstantSize_H_

@class IOSObjectArray;
@class RAREJGConstantSize_Unit;
@protocol JavaUtilList;
@protocol RAREJGFormLayout_Measure;
@protocol RAREiParentComponent;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/jgoodies/forms/layout/Size.h"
#include "java/io/Serializable.h"

@interface RAREJGConstantSize : NSObject < RAREJGSize, JavaIoSerializable > {
 @public
  double value_;
  RAREJGConstantSize_Unit *unit_;
}

+ (RAREJGConstantSize_Unit *)PIXEL;
+ (RAREJGConstantSize_Unit *)POINT;
+ (RAREJGConstantSize_Unit *)DIALOG_UNITS_X;
+ (RAREJGConstantSize_Unit *)DIALOG_UNITS_Y;
+ (RAREJGConstantSize_Unit *)MILLIMETER;
+ (RAREJGConstantSize_Unit *)CENTIMETER;
+ (RAREJGConstantSize_Unit *)INCH;
+ (RAREJGConstantSize_Unit *)PX;
+ (RAREJGConstantSize_Unit *)PT;
+ (RAREJGConstantSize_Unit *)DLUX;
+ (RAREJGConstantSize_Unit *)DLUY;
+ (RAREJGConstantSize_Unit *)MM;
+ (RAREJGConstantSize_Unit *)CM;
+ (RAREJGConstantSize_Unit *)IN;
+ (float)pixelMultiplier;
+ (float *)pixelMultiplierRef;
+ (IOSObjectArray *)VALUES;
- (id)initWithInt:(int)value
withRAREJGConstantSize_Unit:(RAREJGConstantSize_Unit *)unit;
- (id)initWithDouble:(double)value
withRAREJGConstantSize_Unit:(RAREJGConstantSize_Unit *)unit;
+ (RAREJGConstantSize *)valueOfWithNSString:(NSString *)encodedValueAndUnit
                                withBoolean:(BOOL)horizontal;
+ (RAREJGConstantSize *)dluXWithInt:(int)value;
+ (RAREJGConstantSize *)dluYWithInt:(int)value;
- (double)getValue;
- (RAREJGConstantSize_Unit *)getUnit;
- (int)getPixelSizeWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
- (int)maximumSizeWithRAREiParentComponent:(id<RAREiParentComponent>)container
                          withJavaUtilList:(id<JavaUtilList>)components
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)minMeasure
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)prefMeasure
              withRAREJGFormLayout_Measure:(id<RAREJGFormLayout_Measure>)defaultMeasure;
- (BOOL)compressible;
- (BOOL)isEqual:(id)o;
- (NSUInteger)hash;
- (NSString *)description;
- (NSString *)encode;
- (NSString *)encodeEx;
- (int)intValue;
+ (IOSObjectArray *)splitValueAndUnitWithNSString:(NSString *)encodedValueAndUnit;
+ (float)getPixelMultiplier;
+ (void)setPixelMultiplierWithFloat:(float)pixelMultiplier;
- (void)copyAllFieldsTo:(RAREJGConstantSize *)other;
@end

J2OBJC_FIELD_SETTER(RAREJGConstantSize, unit_, RAREJGConstantSize_Unit *)

typedef RAREJGConstantSize ComJgoodiesFormsLayoutConstantSize;

@interface RAREJGConstantSize_Unit : NSObject < JavaIoSerializable > {
 @public
  NSString *name_;
  NSString *abbreviation__;
  NSString *parseAbbreviation_;
  BOOL requiresIntegers_;
  int ordinal_;
}

+ (int)nextOrdinal;
+ (int *)nextOrdinalRef;
- (id)initWithNSString:(NSString *)name
          withNSString:(NSString *)abbreviation
          withNSString:(NSString *)parseAbbreviation
           withBoolean:(BOOL)requiresIntegers;
+ (RAREJGConstantSize_Unit *)valueOfWithNSString:(NSString *)name
                                     withBoolean:(BOOL)horizontal;
- (NSString *)description;
- (NSString *)encode;
- (NSString *)encodeEx;
- (NSString *)abbreviation;
- (void)copyAllFieldsTo:(RAREJGConstantSize_Unit *)other;
@end

J2OBJC_FIELD_SETTER(RAREJGConstantSize_Unit, name_, NSString *)
J2OBJC_FIELD_SETTER(RAREJGConstantSize_Unit, abbreviation__, NSString *)
J2OBJC_FIELD_SETTER(RAREJGConstantSize_Unit, parseAbbreviation_, NSString *)

#endif // _RAREJGConstantSize_H_
