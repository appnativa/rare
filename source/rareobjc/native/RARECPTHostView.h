//
//  RARECPTHostView.h
//  RareOSX
//
//  Created by Don DeCoteau on 8/4/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <Foundation/Foundation.h>
#if TARGET_OS_IPHONE
#import "CorePlot-CocoaTouch.h"
#else
#import <CorePlot/CorePlot.h>
#endif
@class RAREChartDefinition;
@class RAREChartHandler;
@class RAREChartPlot;
@class RAREAPView;
@class RAREChartDataItem;
@class RAREChartAxis;
@class RAREChartHandler_LabelsManager;
@class RAREaChartHandler_SeriesData;
@class RAREChartPlot;
@class RAREChartDefinition_ChartTypeEnum;
@class RAREUIDimension;
@protocol JavaUtilMap;

@interface RARECPTHostView : CPTGraphHostingView <CPTPieChartDelegate,CPTBarPlotDelegate,CPTScatterPlotDelegate,CPTRangePlotDelegate>{
@public

  __weak RAREChartHandler* chartHandler_;
  __weak RAREChartDefinition* chartDefinition_;
}
- (id)initWithChartHandler:(RAREChartHandler *)ch withGraph:(CPTGraph *)graph ;
- (id)initWithChartHandler:(RAREChartHandler *)ch;
-(void) reset: (RAREChartDefinition *) cd andCreateNewGraph: (BOOL) create;
-(void) addPlot: (CPTPlot*)plot;
-(void) setChartDefinition: (RAREChartDefinition *) cd ;
-(void) reloadSeries: (int) index;
-(void) reloadAllPlots;
-(RAREUIDimension*) getPlotAreaSize;
- (void)updateAxis:(BOOL)rangeAxis label:(BOOL)label angle:(BOOL)angle;
-(RAREChartPlot *) createPieChartWithData: (RAREaChartHandler_SeriesData*) seriesData  is3D: (BOOL) is3D;
-(RAREChartPlot *)createBarChartWithData:(RAREaChartHandler_SeriesData*) seriesData isCategory: (BOOL) category  isHorizontal:(BOOL)horizontal isStacked: (BOOL) stacked is3D:(BOOL)is3D;
-(RAREChartPlot *)createRangeChartWithData:(RAREaChartHandler_SeriesData*) seriesData isCategory: (BOOL) category  isArea:(BOOL)area is3D:(BOOL)is3D;
-(RAREChartPlot *) createLineChartWithData: (RAREaChartHandler_SeriesData*) seriesData isCategory: (BOOL) category isSpline: (BOOL) isSpline isArea: (BOOL) area;
-(void)setAxisRange:(RAREChartAxis *)ai loValue:(double)loValue  hiValue:(double)hiValue increment: (double) increment expand: (BOOL) expand;
- (void) updateAxisXincrement: (double)xInc yIncrement: (double)yInc;
- (void)configureGraphLabels:(RAREChartHandler_LabelsManager *)domainManager rangeLabels: (RAREChartHandler_LabelsManager*) rangeManager;
-(void) setAxisLabelsLabels:(RAREChartHandler_LabelsManager*) lm forAxis: (CPTXYAxis*) axis domain: (BOOL) domain;
-(void) setShowPlotValues: (BOOL) show;
-(void) addMarker: (RAREChartDataItem*) item defaultBackground: (RAREUIColor*) bg defaultForeground: (RAREUIColor*) fg range: (BOOL) rangeMarker;
-(void) removeMarkers: (BOOL) range;
-(CGPoint) rowAndColumnAtX: (float) x andY: (float)y;
+(void) setTextStyle: (CPTMutableTextStyle*) style withFont:  (RAREUIFont*) font andColor: (RAREUIColor*) color;
@end

@interface RARECPTDataSource : NSObject <CPTPlotDataSource,CPTPieChartDataSource,CPTScatterPlotDataSource, CPTBarPlotDataSource,CPTRangePlotDataSource> {
  __weak RARECPTHostView* hostView_;
}
-(id) initWithHostView: (RARECPTHostView*) hv andData: (RAREaChartHandler_SeriesData*) data andChartType: (int) type isCategory: (BOOL)category;
- (RAREChartDataItem *)itemForPlot:(CPTPlot *)plot recordIndex:(NSUInteger)index;


@end
