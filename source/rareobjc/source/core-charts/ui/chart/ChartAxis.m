//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-charts/com/appnativa/rare/ui/chart/ChartAxis.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/converters/iDataConverter.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIColorHelper.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIFontHelper.h"
#include "com/appnativa/rare/ui/chart/ChartAxis.h"
#include "com/appnativa/rare/ui/chart/ChartDataItem.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "com/appnativa/util/SNumber.h"
#include "java/lang/Double.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/text/ParseException.h"
#include "java/util/Locale.h"

@implementation RAREChartAxis

- (BOOL)isRangeAxis {
  return rangeAxis_;
}

- (void)setRangeAxisWithBoolean:(BOOL)rangeAxis {
  self->rangeAxis_ = rangeAxis;
}

- (int)getAngle {
  return angle_;
}

- (void)setAngleWithInt:(int)angle {
  self->angle_ = angle;
}

- (RAREUIFont *)getLabelFont {
  return labelFont_;
}

- (void)setLabelFontWithRAREUIFont:(RAREUIFont *)labelFont {
  self->labelFont_ = labelFont;
}

- (RAREUIColor *)getLabelColor {
  return labelColor_;
}

- (void)setLabelColorWithRAREUIColor:(RAREUIColor *)labelColor {
  self->labelColor_ = labelColor;
}

- (BOOL)isLabelsVisible {
  return labelsVisible_;
}

- (void)setLabelsVisibleWithBoolean:(BOOL)labelsVisible {
  self->labelsVisible_ = labelsVisible;
}

- (id)init {
  if (self = [super init]) {
    showGridLine_ = YES;
    showMinorTicks_ = YES;
  }
  return self;
}

- (id)initWithNSString:(NSString *)label
               withInt:(int)type
                withId:(id)data
 withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  if (self = [super initWithId:label withInt:type withId:data withRAREiPlatformIcon:icon]) {
    showGridLine_ = YES;
    showMinorTicks_ = YES;
  }
  return self;
}

- (int)getAxisType {
  return [self getDomainType];
}

- (double)getIncrement {
  return increment_;
}

- (NSString *)getLabel {
  return (theValue_ == nil) ? @"" : [theValue_ description];
}

- (NSString *)description {
  return (theValue_ == nil) ? @"" : [theValue_ description];
}

- (RARERenderableDataItem *)getLowerBounds {
  return lowerBounds_;
}

- (RAREChartAxis_TimeUnitEnum *)getTimeUnit {
  return timeUnit_;
}

- (RARERenderableDataItem *)getUpperBounds {
  return upperBounds_;
}

- (void)handleAttributesWithRAREiWidget:(id<RAREiWidget>)context
                       withISPOTElement:(id<iSPOTElement>)elem {
  NSString *s = [((id<iSPOTElement>) nil_chk(elem)) spot_getAttributeWithNSString:@"timeUnit"];
  if ((s != nil) && ([s sequenceLength] == 0)) {
    s = nil;
  }
  if (s != nil) {
    s = [s uppercaseStringWithJRELocale:[JavaUtilLocale ENGLISH]];
    if ([((NSString *) nil_chk(s)) isEqual:@"SEC"] || [s isEqual:@"S"]) {
      s = @"SECOND";
    }
    else if ([s isEqual:@"MS"]) {
      s = @"MILLISECOND";
    }
    timeUnit_ = [RAREChartAxis_TimeUnitEnum valueOfWithNSString:s];
  }
  else {
    timeUnit_ = nil;
  }
  angle_ = [RAREUTSNumber intValueWithNSString:[elem spot_getAttributeWithNSString:@"labelAngle"]];
  NSString *l = [elem spot_getAttributeWithNSString:@"lowerBound"];
  NSString *u = [elem spot_getAttributeWithNSString:@"upperBound"];
  s = [elem spot_getAttributeWithNSString:@"tickIncrement"];
  double inc = 0;
  if (s != nil) {
    inc = [RAREUTSNumber doubleValueWithNSString:s];
  }
  [self setBoundsWithId:l withId:u withJavaLangDouble:[JavaLangDouble valueOfWithDouble:inc]];
  s = [elem spot_getAttributeWithNSString:@"labelFont"];
  RAREUIFont *f = [RAREUIFontHelper parseFontWithRAREiWidget:context withNSString:s];
  if (f != nil) {
    labelFont_ = f;
  }
  s = [elem spot_getAttributeWithNSString:@"labelColor"];
  if (s != nil) {
    labelColor_ = [RAREUIColorHelper getColorWithNSString:s];
  }
  s = [elem spot_getAttributeWithNSString:@"ticksPerDataPoint"];
  if (s != nil) {
    ticksPerDataPoint_ = [RAREUTSNumber intValueWithNSString:s];
  }
  s = [elem spot_getAttributeWithNSString:@"labelsVisible"];
  [self setLabelsVisibleWithBoolean:![@"false" equalsIgnoreCase:s]];
  s = [elem spot_getAttributeWithNSString:@"showGridLine"];
  [self setShowGridLineWithBoolean:![@"false" equalsIgnoreCase:s]];
  s = [elem spot_getAttributeWithNSString:@"showMinorTicks"];
  [self setShowMinorTicksWithBoolean:![@"false" equalsIgnoreCase:s]];
}

- (void)setBoundsWithId:(id)lower
                 withId:(id)upper
     withJavaLangDouble:(JavaLangDouble *)increment {
  id l;
  id u;
  if ([lower isKindOfClass:[RARERenderableDataItem class]]) {
    l = [((RARERenderableDataItem *) check_class_cast(lower, [RARERenderableDataItem class])) getValue];
  }
  else {
    l = lower;
  }
  if ([upper isKindOfClass:[RARERenderableDataItem class]]) {
    u = [((RARERenderableDataItem *) check_class_cast(upper, [RARERenderableDataItem class])) getValue];
  }
  else {
    u = upper;
  }
  if (l != nil) {
    lowerBounds_ = [[RARERenderableDataItem alloc] initWithId:l];
    [lowerBounds_ setTypeWithInt:[self getDomainType]];
    [lowerBounds_ setDataConverterWithRAREiDataConverter:[self getDomainDataConverter]];
    [lowerBounds_ setValueContextWithId:[self getDomainContext]];
    [lowerBounds_ setConverterClassWithIOSClass:[self getDomainConverterClass]];
  }
  if (u != nil) {
    upperBounds_ = [[RARERenderableDataItem alloc] initWithId:u];
    [upperBounds_ setTypeWithInt:[self getDomainType]];
    [upperBounds_ setDataConverterWithRAREiDataConverter:[self getDomainDataConverter]];
    [upperBounds_ setValueContextWithId:[self getDomainContext]];
    [upperBounds_ setConverterClassWithIOSClass:[self getDomainConverterClass]];
  }
  if (increment != nil) {
    self->increment_ = [increment doubleValue];
  }
}

- (void)setIncrementWithDouble:(double)increment {
  self->increment_ = increment;
}

- (void)setLabelWithNSString:(NSString *)label {
  theValue_ = label;
  [self setConvertedWithBoolean:YES];
}

- (void)setTimeUnitWithNSString:(NSString *)unit {
  if ((unit != nil) && ([unit sequenceLength] == 0)) {
    unit = nil;
  }
  if (unit != nil) {
    unit = [unit uppercaseStringWithJRELocale:[JavaUtilLocale ENGLISH]];
    if ([((NSString *) nil_chk(unit)) isEqual:@"SEC"] || [unit isEqual:@"S"]) {
      unit = @"SECOND";
    }
    else if ([unit isEqual:@"MS"]) {
      unit = @"MILLISECOND";
    }
    timeUnit_ = [RAREChartAxis_TimeUnitEnum valueOfWithNSString:unit];
  }
  else {
    timeUnit_ = [RAREChartAxis_TimeUnitEnum DAY];
  }
}

- (void)setTimeUnitWithRAREChartAxis_TimeUnitEnum:(RAREChartAxis_TimeUnitEnum *)unit {
  self->timeUnit_ = unit;
}

- (BOOL)isShowGridLine {
  return showGridLine_;
}

- (void)setShowGridLineWithBoolean:(BOOL)showGridLine {
  self->showGridLine_ = showGridLine;
}

- (BOOL)isShowMinorTicks {
  return showMinorTicks_;
}

- (void)setShowMinorTicksWithBoolean:(BOOL)showMinorTicks {
  self->showMinorTicks_ = showMinorTicks;
}

- (int)getTicksPerDataPoint {
  return ticksPerDataPoint_;
}

- (void)setTicksPerDataPointWithInt:(int)ticksPerDataPoint {
  self->ticksPerDataPoint_ = ticksPerDataPoint;
}

- (void)copyAllFieldsTo:(RAREChartAxis *)other {
  [super copyAllFieldsTo:other];
  other->angle_ = angle_;
  other->increment_ = increment_;
  other->labelColor_ = labelColor_;
  other->labelFont_ = labelFont_;
  other->labelsVisible_ = labelsVisible_;
  other->lowerBounds_ = lowerBounds_;
  other->rangeAxis_ = rangeAxis_;
  other->showGridLine_ = showGridLine_;
  other->showMinorTicks_ = showMinorTicks_;
  other->ticksPerDataPoint_ = ticksPerDataPoint_;
  other->timeUnit_ = timeUnit_;
  other->upperBounds_ = upperBounds_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isRangeAxis", NULL, "Z", 0x1, NULL },
    { "getLabelFont", NULL, "LRAREUIFont", 0x1, NULL },
    { "getLabelColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "isLabelsVisible", NULL, "Z", 0x1, NULL },
    { "getLabel", NULL, "LNSString", 0x1, NULL },
    { "getLowerBounds", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "getTimeUnit", NULL, "LRAREChartAxis_TimeUnitEnum", 0x1, NULL },
    { "getUpperBounds", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "handleAttributesWithRAREiWidget:withISPOTElement:", NULL, "V", 0x1, "JavaTextParseException" },
    { "isShowGridLine", NULL, "Z", 0x1, NULL },
    { "isShowMinorTicks", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREChartAxis = { "ChartAxis", "com.appnativa.rare.ui.chart", NULL, 0x1, 11, methods, 0, NULL, 0, NULL};
  return &_RAREChartAxis;
}

@end

static RAREChartAxis_TimeUnitEnum *RAREChartAxis_TimeUnitEnum_NONE;
static RAREChartAxis_TimeUnitEnum *RAREChartAxis_TimeUnitEnum_MILLISECOND;
static RAREChartAxis_TimeUnitEnum *RAREChartAxis_TimeUnitEnum_SECOND;
static RAREChartAxis_TimeUnitEnum *RAREChartAxis_TimeUnitEnum_MINUTE;
static RAREChartAxis_TimeUnitEnum *RAREChartAxis_TimeUnitEnum_HOUR;
static RAREChartAxis_TimeUnitEnum *RAREChartAxis_TimeUnitEnum_DAY;
static RAREChartAxis_TimeUnitEnum *RAREChartAxis_TimeUnitEnum_WEEK;
static RAREChartAxis_TimeUnitEnum *RAREChartAxis_TimeUnitEnum_MONTH;
static RAREChartAxis_TimeUnitEnum *RAREChartAxis_TimeUnitEnum_YEAR;
IOSObjectArray *RAREChartAxis_TimeUnitEnum_values;

@implementation RAREChartAxis_TimeUnitEnum

+ (RAREChartAxis_TimeUnitEnum *)NONE {
  return RAREChartAxis_TimeUnitEnum_NONE;
}
+ (RAREChartAxis_TimeUnitEnum *)MILLISECOND {
  return RAREChartAxis_TimeUnitEnum_MILLISECOND;
}
+ (RAREChartAxis_TimeUnitEnum *)SECOND {
  return RAREChartAxis_TimeUnitEnum_SECOND;
}
+ (RAREChartAxis_TimeUnitEnum *)MINUTE {
  return RAREChartAxis_TimeUnitEnum_MINUTE;
}
+ (RAREChartAxis_TimeUnitEnum *)HOUR {
  return RAREChartAxis_TimeUnitEnum_HOUR;
}
+ (RAREChartAxis_TimeUnitEnum *)DAY {
  return RAREChartAxis_TimeUnitEnum_DAY;
}
+ (RAREChartAxis_TimeUnitEnum *)WEEK {
  return RAREChartAxis_TimeUnitEnum_WEEK;
}
+ (RAREChartAxis_TimeUnitEnum *)MONTH {
  return RAREChartAxis_TimeUnitEnum_MONTH;
}
+ (RAREChartAxis_TimeUnitEnum *)YEAR {
  return RAREChartAxis_TimeUnitEnum_YEAR;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREChartAxis_TimeUnitEnum class]) {
    RAREChartAxis_TimeUnitEnum_NONE = [[RAREChartAxis_TimeUnitEnum alloc] initWithNSString:@"NONE" withInt:0];
    RAREChartAxis_TimeUnitEnum_MILLISECOND = [[RAREChartAxis_TimeUnitEnum alloc] initWithNSString:@"MILLISECOND" withInt:1];
    RAREChartAxis_TimeUnitEnum_SECOND = [[RAREChartAxis_TimeUnitEnum alloc] initWithNSString:@"SECOND" withInt:2];
    RAREChartAxis_TimeUnitEnum_MINUTE = [[RAREChartAxis_TimeUnitEnum alloc] initWithNSString:@"MINUTE" withInt:3];
    RAREChartAxis_TimeUnitEnum_HOUR = [[RAREChartAxis_TimeUnitEnum alloc] initWithNSString:@"HOUR" withInt:4];
    RAREChartAxis_TimeUnitEnum_DAY = [[RAREChartAxis_TimeUnitEnum alloc] initWithNSString:@"DAY" withInt:5];
    RAREChartAxis_TimeUnitEnum_WEEK = [[RAREChartAxis_TimeUnitEnum alloc] initWithNSString:@"WEEK" withInt:6];
    RAREChartAxis_TimeUnitEnum_MONTH = [[RAREChartAxis_TimeUnitEnum alloc] initWithNSString:@"MONTH" withInt:7];
    RAREChartAxis_TimeUnitEnum_YEAR = [[RAREChartAxis_TimeUnitEnum alloc] initWithNSString:@"YEAR" withInt:8];
    RAREChartAxis_TimeUnitEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREChartAxis_TimeUnitEnum_NONE, RAREChartAxis_TimeUnitEnum_MILLISECOND, RAREChartAxis_TimeUnitEnum_SECOND, RAREChartAxis_TimeUnitEnum_MINUTE, RAREChartAxis_TimeUnitEnum_HOUR, RAREChartAxis_TimeUnitEnum_DAY, RAREChartAxis_TimeUnitEnum_WEEK, RAREChartAxis_TimeUnitEnum_MONTH, RAREChartAxis_TimeUnitEnum_YEAR, nil } count:9 type:[IOSClass classWithClass:[RAREChartAxis_TimeUnitEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREChartAxis_TimeUnitEnum_values];
}

+ (RAREChartAxis_TimeUnitEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREChartAxis_TimeUnitEnum_values count]; i++) {
    RAREChartAxis_TimeUnitEnum *e = RAREChartAxis_TimeUnitEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREChartAxis_TimeUnitEnum"};
  static J2ObjcClassInfo _RAREChartAxis_TimeUnitEnum = { "TimeUnit", "com.appnativa.rare.ui.chart", "ChartAxis", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREChartAxis_TimeUnitEnum;
}

@end
