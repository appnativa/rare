//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-charts/com/appnativa/rare/ui/chart/aChartHandler.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREaChartHandler_H_
#define _RAREaChartHandler_H_

@class IOSDoubleArray;
@class IOSObjectArray;
@class JavaTextNumberFormat;
@class JavaUtilHashSet;
@class RAREChartAxis;
@class RAREChartAxis_TimeUnitEnum;
@class RAREChartDataItem;
@class RAREChartDefinition;
@class RAREChartDefinition_ChartTypeEnum;
@class RAREPlotInformation;
@class RAREPlotInformation_LabelTypeEnum;
@class RAREPlotInformation_ShapeStyleEnum;
@class RARERenderableDataItem;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIFont;
@class RAREUIFontMetrics;
@class RAREUIImage;
@class RAREUIStroke;
@class RAREaChartHandler_SeriesData;
@class RAREaChartViewer;
@protocol JavaUtilList;
@protocol RAREiActionComponent;
@protocol RAREiDataConverter;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "java/lang/Comparable.h"
#include "java/util/Comparator.h"

@interface RAREaChartHandler_LabelData : NSObject < JavaLangComparable > {
 @public
  NSString *label_;
  float width_;
  float height_;
  double position_;
}

- (id)initWithNSString:(NSString *)label
             withFloat:(float)width
             withFloat:(float)height;
- (int)compareToWithId:(id)o;
- (NSString *)description;
- (void)copyAllFieldsTo:(RAREaChartHandler_LabelData *)other;
@end

J2OBJC_FIELD_SETTER(RAREaChartHandler_LabelData, label_, NSString *)

#define RAREaChartHandler_TYPE_DATE 1
#define RAREaChartHandler_TYPE_NUMBER 0
#define RAREaChartHandler_TYPE_STRING 2

@interface RAREaChartHandler : NSObject {
 @public
  RAREUIFont *chartFont_;
  RAREUIColor *chartForeground_;
  RAREUIColor *chartBackground_;
  RAREUIColor *gridColor_;
  RAREUIColor *legendLabelColor_;
  RAREUIFont *legendLabelFont_;
  RAREUIColor *plotBackground_;
  RAREUIColor *plotLabelColor_;
  RAREUIFont *plotLabelFont_;
}

+ (int)TYPE_DATE;
+ (int)TYPE_NUMBER;
+ (int)TYPE_STRING;
+ (IOSObjectArray *)defaultColors;
+ (void)setDefaultColors:(IOSObjectArray *)defaultColors;
+ (int)LABELS_PADDING;
+ (int *)LABELS_PADDINGRef;
- (id)init;
- (void)configureTitleWithRAREiActionComponent:(id<RAREiActionComponent>)c
                    withRARERenderableDataItem:(RARERenderableDataItem *)title;
- (id<RAREiPlatformComponent>)createChartWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                                            withRAREChartDefinition:(RAREChartDefinition *)cd;
- (IOSObjectArray *)createLabelDataWithRAREChartDefinition:(RAREChartDefinition *)cd
                                               withBoolean:(BOOL)domain
                                                withDouble:(double)availableWidth
                                                withDouble:(double)startValue
                                                withDouble:(double)endValue
                                                withDouble:(double)increment;
- (IOSObjectArray *)createLabelsDataWithJavaUtilList:(id<JavaUtilList>)list
                                withRAREaChartViewer:(RAREaChartViewer *)cv
                              withRAREiDataConverter:(id<RAREiDataConverter>)cvt
                                              withId:(id)context
                                         withBoolean:(BOOL)domain
                               withRAREUIFontMetrics:(RAREUIFontMetrics *)fm
                                             withInt:(int)textAngle;
- (IOSObjectArray *)createNumericLabelsDataWithRAREChartDefinition:(RAREChartDefinition *)cd
                                                        withDouble:(double)width
                                                        withDouble:(double)startValue
                                                        withDouble:(double)endValue
                                                        withDouble:(double)increment
                                                       withBoolean:(BOOL)domain
                                                        withDouble:(double)widthDivisor;
- (RAREaChartHandler_SeriesData *)createSeriesDataWithInt:(int)dataType
                                  withRAREChartDefinition:(RAREChartDefinition *)cd
                                                  withInt:(int)row;
- (id<RAREiPlatformComponent>)dataChangedWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                                            withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)disposeWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                  withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)disposeChartWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel;
- (void)ensurePlotFullyVisibleWithDoubleArray:(IOSDoubleArray *)values
                                   withDouble:(double)lowest
                                   withDouble:(double)highest;
- (RAREUIColor *)getAxisLabelColorWithRAREChartAxis:(RAREChartAxis *)ai;
- (RAREUIFont *)getAxisLabelFontWithRAREChartAxis:(RAREChartAxis *)ai;
- (RAREUIColor *)getAxisTitleColorWithRAREChartAxis:(RAREChartAxis *)ai;
- (RAREUIFont *)getAxisTitleFontWithRAREChartAxis:(RAREChartAxis *)ai;
- (RAREUIFont *)getChartFont;
- (RAREUIColor *)getChartForeground;
- (RAREUIImage *)getChartImageWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                                 withRAREChartDefinition:(RAREChartDefinition *)cd;
- (id<RAREiPlatformComponent>)getLegendComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                                                   withRAREChartDefinition:(RAREChartDefinition *)cd;
- (RAREUIDimension *)getPlotAreaSizeWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                                       withRAREChartDefinition:(RAREChartDefinition *)cd;
- (RAREUIColor *)getTextColorWithRAREChartDefinition:(RAREChartDefinition *)cd;
- (RAREUIFont *)getTextFontWithRAREChartDefinition:(RAREChartDefinition *)cd;
- (double)getTimeIntervalWithRAREChartAxis_TimeUnitEnum:(RAREChartAxis_TimeUnitEnum *)tm;
- (double)getTimeIntervalWithDouble:(double)value
     withRAREChartAxis_TimeUnitEnum:(RAREChartAxis_TimeUnitEnum *)tm;
- (RAREUIColor *)getTitleColorWithRARERenderableDataItem:(RARERenderableDataItem *)title;
- (RAREUIFont *)getTitleFontWithRARERenderableDataItem:(RARERenderableDataItem *)title;
- (BOOL)isLegendSeperate;
- (void)itemChangedWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                      withRAREChartDefinition:(RAREChartDefinition *)cd
                        withRAREChartDataItem:(RAREChartDataItem *)item;
- (RAREChartDataItem *)itemFromLocationWithRAREChartDefinition:(RAREChartDefinition *)cd
                                                       withInt:(int)x
                                                       withInt:(int)y;
- (void)recomputeAxisRangeAndTickWithDoubleArray:(IOSDoubleArray *)values;
- (void)setChartFontWithRAREUIFont:(RAREUIFont *)chartFont;
- (void)setChartForegroundWithRAREUIColor:(RAREUIColor *)chartForeground;
- (void)setDomainLabelWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                         withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)setDomainLabelAngelWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                              withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)setHorizontalScrollingEnabledWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartComponent
                                                    withBoolean:(BOOL)enable;
- (void)setRangeLabelWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                        withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)setRangeLabelAngelWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                             withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)setShowDomainLabelsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                              withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)setShowPlotValuesWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                            withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)setShowRangeLabelsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                             withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)setVerticalScrollingEnabledWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartComponent
                                                  withBoolean:(BOOL)enable;
- (void)unzoomWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel;
- (void)updateRangeAxisWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                          withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)updatesCompletedWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                           withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)updatesPendingWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                         withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)calculateSeriesesRangeWithRAREChartDefinition:(RAREChartDefinition *)cd
                                              withInt:(int)domainType
                                      withDoubleArray:(IOSDoubleArray *)xrange
                                      withDoubleArray:(IOSDoubleArray *)yrange;
- (RAREUIColor *)getGridColorWithRAREPlotInformation:(RAREPlotInformation *)plot;
- (RAREUIStroke *)getGridStrokeWithRAREPlotInformation:(RAREPlotInformation *)plot;
- (void)setupDefaultsWithBoolean:(BOOL)allowNull;
+ (double)calculateIncrementWithDouble:(double)availableWidth
                            withDouble:(double)tickWidth
                            withDouble:(double)startValue
                            withDouble:(double)endValue
                            withDouble:(double)increment;
+ (void)calculateRangeBoundsWithRAREChartDataItem:(RAREChartDataItem *)series
                                  withDoubleArray:(IOSDoubleArray *)xrange
                                  withDoubleArray:(IOSDoubleArray *)yrange
                                          withInt:(int)start
                                          withInt:(int)end
                                      withBoolean:(BOOL)numberRange;
+ (void)calculateTextSizeWithFloat:(float)textWidth
                         withFloat:(float)lineHeight
                         withFloat:(float)textAngle
               withRAREUIDimension:(RAREUIDimension *)size;
+ (void)calculateTextSizeWithNSString:(NSString *)text
                withRAREUIFontMetrics:(RAREUIFontMetrics *)fm
                              withInt:(int)textAngle
                  withRAREUIDimension:(RAREUIDimension *)size;
+ (IOSObjectArray *)createLabelsDataWithJavaUtilList:(id<JavaUtilList>)list
                                withRAREaChartViewer:(RAREaChartViewer *)cv
                              withRAREiDataConverter:(id<RAREiDataConverter>)cvt
                                              withId:(id)context
                                         withBoolean:(BOOL)domain
                               withRAREUIFontMetrics:(RAREUIFontMetrics *)fm
                                             withInt:(int)textAngle
                                          withDouble:(double)startPosition
                                          withDouble:(double)increment
                                         withBoolean:(BOOL)padEnds;
+ (RAREaChartHandler_SeriesData *)createSeriesDataWithInt:(int)dataType
                                    withRAREChartDataItem:(RAREChartDataItem *)series
                                         withJavaUtilList:(id<JavaUtilList>)domainValues
                                                  withInt:(int)start
                                                  withInt:(int)end
                    withRAREPlotInformation_LabelTypeEnum:(RAREPlotInformation_LabelTypeEnum *)lt;
+ (int)getDataTypeWithRAREChartAxis:(RAREChartAxis *)axis;
+ (RAREUIColor *)getDefaultColorWithInt:(int)index;
+ (int)getLabelsModWithRAREaChartHandler_LabelDataArray:(IOSObjectArray *)list
                                              withFloat:(float)width
                                              withFloat:(float)pad;
+ (RAREChartDefinition_ChartTypeEnum *)getSeriesChartTypeWithRAREChartDefinition:(RAREChartDefinition *)cd
                                                           withRAREChartDataItem:(RAREChartDataItem *)series;
+ (RAREUIColor *)getSeriesFillColorWithRAREChartDataItem:(RAREChartDataItem *)series;
+ (RAREPlotInformation_LabelTypeEnum *)getSeriesLabelTypeWithRAREPlotInformation:(RAREPlotInformation *)pi
                                                           withRAREChartDataItem:(RAREChartDataItem *)series;
+ (float)getSeriesLineThicknessWithRAREChartDataItem:(RAREChartDataItem *)series
                                           withFloat:(float)def;
+ (RAREUIColor *)getSeriesOutlineColorWithRAREChartDataItem:(RAREChartDataItem *)series;
+ (float)getSeriesOutlineLineThicknessWithRAREChartDataItem:(RAREChartDataItem *)series
                                                  withFloat:(float)def;
+ (RAREPlotInformation_ShapeStyleEnum *)getSeriesShapeStyleWithRAREPlotInformation:(RAREPlotInformation *)pi
                                                             withRAREChartDataItem:(RAREChartDataItem *)series;
+ (void)remeasureLabelsWithRAREaChartHandler_LabelDataArray:(IOSObjectArray *)labels
                                      withRAREUIFontMetrics:(RAREUIFontMetrics *)fm
                                                    withInt:(int)textAngle;
+ (IOSObjectArray *)createDefaultColors;
+ (int)getCalendarFieldWithRAREChartAxis_TimeUnitEnum:(RAREChartAxis_TimeUnitEnum *)tu;
+ (int)getLabelsWidthWithRAREaChartHandler_LabelDataArray:(IOSObjectArray *)list
                                                  withInt:(int)mod
                                                withFloat:(float)pad;
+ (double)niceNumberWithDouble:(double)Value
                   withBoolean:(BOOL)Round;
- (void)copyAllFieldsTo:(RAREaChartHandler *)other;
@end

J2OBJC_FIELD_SETTER(RAREaChartHandler, chartFont_, RAREUIFont *)
J2OBJC_FIELD_SETTER(RAREaChartHandler, chartForeground_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaChartHandler, chartBackground_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaChartHandler, gridColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaChartHandler, legendLabelColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaChartHandler, legendLabelFont_, RAREUIFont *)
J2OBJC_FIELD_SETTER(RAREaChartHandler, plotBackground_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaChartHandler, plotLabelColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaChartHandler, plotLabelFont_, RAREUIFont *)

typedef RAREaChartHandler ComAppnativaRareUiChartAChartHandler;

@interface RAREaChartHandler_NoChartHandler : RAREaChartHandler {
}

- (id)init;
- (id<RAREiPlatformComponent>)createChartWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                                            withRAREChartDefinition:(RAREChartDefinition *)cd;
- (id<RAREiPlatformComponent>)dataChangedWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                                            withRAREChartDefinition:(RAREChartDefinition *)cd;
- (RAREUIImage *)getChartImageWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                                 withRAREChartDefinition:(RAREChartDefinition *)cd;
- (RAREUIDimension *)getPlotAreaSizeWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                                       withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)itemChangedWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                      withRAREChartDefinition:(RAREChartDefinition *)cd
                        withRAREChartDataItem:(RAREChartDataItem *)item;
- (RAREChartDataItem *)itemFromLocationWithRAREChartDefinition:(RAREChartDefinition *)cd
                                                       withInt:(int)x
                                                       withInt:(int)y;
- (void)setChartForegroundWithRAREUIColor:(RAREUIColor *)chartForeground;
- (void)setDomainLabelWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                         withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)setDomainLabelAngelWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartComponent
                              withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)setRangeLabelWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                        withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)setRangeLabelAngelWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                             withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)setShowDomainLabelsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                              withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)setShowPlotValuesWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                            withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)setShowRangeLabelsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                             withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)unzoomWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel;
- (void)updateRangeAxisWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                          withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)updatesCompletedWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                           withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)updatesPendingWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel
                         withRAREChartDefinition:(RAREChartDefinition *)cd;
- (id<RAREiPlatformComponent>)getErrorPanelWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)chartPanel;
@end

@interface RAREaChartHandler_SeriesData : NSObject {
 @public
  id<JavaUtilList> rangeValues_;
  id<JavaUtilList> domainValues_;
  BOOL showPointLabels_;
  id<JavaUtilList> dataItems_;
  JavaUtilHashSet *domainMap_;
  NSString *legend_;
  id linkedData_;
  BOOL isNumberRange_;
  RAREChartDefinition_ChartTypeEnum *chartType_;
  int seriesIndex_;
  int dataIndex_;
  RAREPlotInformation_LabelTypeEnum *labelType_;
  RAREUIColor *outlineColor_;
  RAREUIColor *fillColor_;
  double pieTotal_;
  JavaTextNumberFormat *percentFormat_;
  JavaTextNumberFormat *totalFormat_;
}

- (id)initWithNSString:(NSString *)legend
               withInt:(int)size
withRAREPlotInformation_LabelTypeEnum:(RAREPlotInformation_LabelTypeEnum *)lt;
- (id)initWithNSString:(NSString *)legend
               withInt:(int)size
withRAREPlotInformation_LabelTypeEnum:(RAREPlotInformation_LabelTypeEnum *)lt
      withJavaUtilList:(id<JavaUtilList>)domainValues
   withJavaUtilHashSet:(JavaUtilHashSet *)domains;
- (void)addDomainValuesWithJavaUtilList:(id<JavaUtilList>)values;
- (int)addValueWithRAREChartDataItem:(RAREChartDataItem *)item
              withJavaLangComparable:(id<JavaLangComparable>)domain
                        withNSNumber:(NSNumber *)range
                        withNSString:(NSString *)label;
- (void)clearValuesWithBoolean:(BOOL)rangeOnly;
- (IOSObjectArray *)createLabelsWithRAREaChartViewer:(RAREaChartViewer *)cv
                              withRAREiDataConverter:(id<RAREiDataConverter>)cvt
                                              withId:(id)context
                                         withBoolean:(BOOL)domain;
- (void)dispose;
- (RAREChartDataItem *)getDataItemWithInt:(int)index;
- (NSString *)getPieChartLabelWithInt:(int)index
                         withNSString:(NSString *)format;
- (NSString *)getPointLabelWithInt:(int)index
                      withNSString:(NSString *)format;
- (NSString *)description;
- (void)copyAllFieldsTo:(RAREaChartHandler_SeriesData *)other;
@end

J2OBJC_FIELD_SETTER(RAREaChartHandler_SeriesData, rangeValues_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RAREaChartHandler_SeriesData, domainValues_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RAREaChartHandler_SeriesData, dataItems_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RAREaChartHandler_SeriesData, domainMap_, JavaUtilHashSet *)
J2OBJC_FIELD_SETTER(RAREaChartHandler_SeriesData, legend_, NSString *)
J2OBJC_FIELD_SETTER(RAREaChartHandler_SeriesData, linkedData_, id)
J2OBJC_FIELD_SETTER(RAREaChartHandler_SeriesData, chartType_, RAREChartDefinition_ChartTypeEnum *)
J2OBJC_FIELD_SETTER(RAREaChartHandler_SeriesData, labelType_, RAREPlotInformation_LabelTypeEnum *)
J2OBJC_FIELD_SETTER(RAREaChartHandler_SeriesData, outlineColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaChartHandler_SeriesData, fillColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaChartHandler_SeriesData, percentFormat_, JavaTextNumberFormat *)
J2OBJC_FIELD_SETTER(RAREaChartHandler_SeriesData, totalFormat_, JavaTextNumberFormat *)

@interface RAREaChartHandler_aChartInfo : NSObject {
 @public
  int domainType_;
  IOSDoubleArray *xAxisValues_;
  IOSDoubleArray *yAxisValues_;
  BOOL xIncrementFixed_;
  BOOL yIncrementFixed_;
  id<JavaUtilList> domainValues_;
  JavaUtilHashSet *domainMap_;
  id<JavaUtilList> seriesData_;
  IOSObjectArray *labelData_;
  BOOL categoryDomain_;
}

- (void)addDomainValuesWithJavaUtilList:(id<JavaUtilList>)list;
- (IOSObjectArray *)createLabelDataWithRAREChartDefinition:(RAREChartDefinition *)cd
                                     withRAREUIFontMetrics:(RAREUIFontMetrics *)fm
                                               withBoolean:(BOOL)convert;
- (void)dispose;
- (void)popularSeriesDataAndCaluclateRangesWithRAREaChartHandler:(RAREaChartHandler *)ch
                                         withRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)reset;
- (void)setShowPointLabelsWithBoolean:(BOOL)show;
- (RAREaChartHandler_SeriesData *)updateSeriesWithRAREChartDefinition:(RAREChartDefinition *)cd
                                                withRAREChartDataItem:(RAREChartDataItem *)series;
- (void)updateCategorDomainValues;
- (void)updateRangeBoundsWithRAREaChartHandler:(RAREaChartHandler *)ch
                       withRAREChartDefinition:(RAREChartDefinition *)cd;
- (id)init;
- (void)copyAllFieldsTo:(RAREaChartHandler_aChartInfo *)other;
@end

J2OBJC_FIELD_SETTER(RAREaChartHandler_aChartInfo, xAxisValues_, IOSDoubleArray *)
J2OBJC_FIELD_SETTER(RAREaChartHandler_aChartInfo, yAxisValues_, IOSDoubleArray *)
J2OBJC_FIELD_SETTER(RAREaChartHandler_aChartInfo, domainValues_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RAREaChartHandler_aChartInfo, domainMap_, JavaUtilHashSet *)
J2OBJC_FIELD_SETTER(RAREaChartHandler_aChartInfo, seriesData_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RAREaChartHandler_aChartInfo, labelData_, IOSObjectArray *)

@interface RAREaChartHandler_aChartInfo_$1 : NSObject < JavaUtilComparator > {
}

- (int)compareWithId:(id)t
              withId:(id)t1;
- (id)init;
@end

@interface RAREaChartHandler_aChartInfo_$2 : NSObject < JavaUtilComparator > {
}

- (int)compareWithId:(id)t
              withId:(id)t1;
- (id)init;
@end

#endif // _RAREaChartHandler_H_
