//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-coreplot/com/appnativa/rare/ui/chart/coreplot/ChartView.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREChartView_H_
#define _RAREChartView_H_

@class IOSDoubleArray;
@class RAREChartAxis;
@class RAREChartDataItem;
@class RAREChartDefinition;
@class RAREChartHandler;
@class RAREChartHandler_LabelsManager;
@class RAREChartPlot;
@class RAREUIColor;
@class RAREUIDimension;
@class RAREUIPoint;
@class RAREaChartHandler_SeriesData;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"

@interface RAREChartView : RAREView {
 @public
  IOSDoubleArray *xAxisValues_;
  IOSDoubleArray *yAxisValues_;
}

- (id)initWithRAREChartHandler:(RAREChartHandler *)ch;
- (void)addPlotWithRAREChartPlot:(RAREChartPlot *)plot;
- (void)rowAndColumnAtWithFloat:(float)x
                      withFloat:(float)y
                withRAREUIPoint:(RAREUIPoint *)outPoint;
- (RAREChartPlot *)createPlotWithRAREaChartHandler_SeriesData:(RAREaChartHandler_SeriesData *)data
                                                  withBoolean:(BOOL)category;
- (void)addMarkerWithRAREChartDataItem:(RAREChartDataItem *)item
                       withRAREUIColor:(RAREUIColor *)bg
                       withRAREUIColor:(RAREUIColor *)fg
                           withBoolean:(BOOL)rangeMarker;
- (void)reloadAllPlots;
- (void)reloadSeriesWithInt:(int)index;
- (void)resetWithRAREChartDefinition:(RAREChartDefinition *)cd
                         withBoolean:(BOOL)createNewGraph;
- (void)updateAxisWithBoolean:(BOOL)rangeAxis
                  withBoolean:(BOOL)label
                  withBoolean:(BOOL)angle;
- (void)setAxisRangeWithRAREChartAxis:(RAREChartAxis *)ai
                           withDouble:(double)min
                           withDouble:(double)max
                           withDouble:(double)inc;
- (void)setAxisRangesWithDouble:(double)xMin
                     withDouble:(double)xMax
                     withDouble:(double)xInc
                     withDouble:(double)yMin
                     withDouble:(double)yMax
                     withDouble:(double)yInc
withRAREChartHandler_LabelsManager:(RAREChartHandler_LabelsManager *)domainManager
withRAREChartHandler_LabelsManager:(RAREChartHandler_LabelsManager *)rangeManager;
- (void)updateAxisIncrementsWithDouble:(double)xInc
                            withDouble:(double)yInc;
- (RAREView *)createLegendView;
- (id)createLegendViewProxy;
+ (id)createProxyWithRAREChartHandler:(RAREChartHandler *)ch;
- (void)setAxisTickWithRAREChartAxis:(RAREChartAxis *)rangeAxis
                          withDouble:(double)increment;
- (void)setShowPlotValuesWithBoolean:(BOOL)show;
- (RAREUIDimension *)getPlotAreaSize;
- (void)removeMarkersWithBoolean:(BOOL)range;
- (void)copyAllFieldsTo:(RAREChartView *)other;
@end

J2OBJC_FIELD_SETTER(RAREChartView, xAxisValues_, IOSDoubleArray *)
J2OBJC_FIELD_SETTER(RAREChartView, yAxisValues_, IOSDoubleArray *)

typedef RAREChartView ComAppnativaRareUiChartCoreplotChartView;

#endif // _RAREChartView_H_
