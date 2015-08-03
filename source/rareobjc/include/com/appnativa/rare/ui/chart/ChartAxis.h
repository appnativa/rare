//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-charts/com/appnativa/rare/ui/chart/ChartAxis.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREChartAxis_H_
#define _RAREChartAxis_H_

@class JavaLangDouble;
@class RAREChartAxis_TimeUnitEnum;
@class RARERenderableDataItem;
@class RAREUIColor;
@class RAREUIFont;
@protocol RAREiPlatformIcon;
@protocol RAREiWidget;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/chart/ChartDataItem.h"
#include "java/lang/Enum.h"

@interface RAREChartAxis : RAREChartDataItem {
 @public
  double increment_;
  RARERenderableDataItem *lowerBounds_;
  RAREChartAxis_TimeUnitEnum *timeUnit_;
  RARERenderableDataItem *upperBounds_;
  BOOL rangeAxis_;
  int angle_;
  RAREUIFont *labelFont_;
  RAREUIColor *labelColor_;
  BOOL labelsVisible_;
  BOOL showGridLine_;
  BOOL showMinorTicks_;
  int ticksPerDataPoint_;
}

- (BOOL)isRangeAxis;
- (void)setRangeAxisWithBoolean:(BOOL)rangeAxis;
- (int)getAngle;
- (void)setAngleWithInt:(int)angle;
- (RAREUIFont *)getLabelFont;
- (void)setLabelFontWithRAREUIFont:(RAREUIFont *)labelFont;
- (RAREUIColor *)getLabelColor;
- (void)setLabelColorWithRAREUIColor:(RAREUIColor *)labelColor;
- (BOOL)isLabelsVisible;
- (void)setLabelsVisibleWithBoolean:(BOOL)labelsVisible;
- (id)init;
- (id)initWithNSString:(NSString *)label
               withInt:(int)type
                withId:(id)data
 withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (int)getAxisType;
- (double)getIncrement;
- (NSString *)getLabel;
- (NSString *)description;
- (RARERenderableDataItem *)getLowerBounds;
- (RAREChartAxis_TimeUnitEnum *)getTimeUnit;
- (RARERenderableDataItem *)getUpperBounds;
- (void)handleAttributesWithRAREiWidget:(id<RAREiWidget>)context
                       withISPOTElement:(id<iSPOTElement>)elem;
- (void)setBoundsWithId:(id)lower
                 withId:(id)upper
     withJavaLangDouble:(JavaLangDouble *)increment;
- (void)setIncrementWithDouble:(double)increment;
- (void)setLabelWithNSString:(NSString *)label;
- (void)setTimeUnitWithNSString:(NSString *)unit;
- (void)setTimeUnitWithRAREChartAxis_TimeUnitEnum:(RAREChartAxis_TimeUnitEnum *)unit;
- (BOOL)isShowGridLine;
- (void)setShowGridLineWithBoolean:(BOOL)showGridLine;
- (BOOL)isShowMinorTicks;
- (void)setShowMinorTicksWithBoolean:(BOOL)showMinorTicks;
- (int)getTicksPerDataPoint;
- (void)setTicksPerDataPointWithInt:(int)ticksPerDataPoint;
- (void)copyAllFieldsTo:(RAREChartAxis *)other;
@end

J2OBJC_FIELD_SETTER(RAREChartAxis, lowerBounds_, RARERenderableDataItem *)
J2OBJC_FIELD_SETTER(RAREChartAxis, timeUnit_, RAREChartAxis_TimeUnitEnum *)
J2OBJC_FIELD_SETTER(RAREChartAxis, upperBounds_, RARERenderableDataItem *)
J2OBJC_FIELD_SETTER(RAREChartAxis, labelFont_, RAREUIFont *)
J2OBJC_FIELD_SETTER(RAREChartAxis, labelColor_, RAREUIColor *)

typedef RAREChartAxis ComAppnativaRareUiChartChartAxis;

typedef enum {
  RAREChartAxis_TimeUnit_NONE = 0,
  RAREChartAxis_TimeUnit_MILLISECOND = 1,
  RAREChartAxis_TimeUnit_SECOND = 2,
  RAREChartAxis_TimeUnit_MINUTE = 3,
  RAREChartAxis_TimeUnit_HOUR = 4,
  RAREChartAxis_TimeUnit_DAY = 5,
  RAREChartAxis_TimeUnit_WEEK = 6,
  RAREChartAxis_TimeUnit_MONTH = 7,
  RAREChartAxis_TimeUnit_YEAR = 8,
} RAREChartAxis_TimeUnit;

@interface RAREChartAxis_TimeUnitEnum : JavaLangEnum < NSCopying > {
}
+ (RAREChartAxis_TimeUnitEnum *)NONE;
+ (RAREChartAxis_TimeUnitEnum *)MILLISECOND;
+ (RAREChartAxis_TimeUnitEnum *)SECOND;
+ (RAREChartAxis_TimeUnitEnum *)MINUTE;
+ (RAREChartAxis_TimeUnitEnum *)HOUR;
+ (RAREChartAxis_TimeUnitEnum *)DAY;
+ (RAREChartAxis_TimeUnitEnum *)WEEK;
+ (RAREChartAxis_TimeUnitEnum *)MONTH;
+ (RAREChartAxis_TimeUnitEnum *)YEAR;
+ (IOSObjectArray *)values;
+ (RAREChartAxis_TimeUnitEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _RAREChartAxis_H_
