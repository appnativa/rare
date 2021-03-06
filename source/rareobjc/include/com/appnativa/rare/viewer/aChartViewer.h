//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-charts/com/appnativa/rare/viewer/aChartViewer.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaChartViewer_H_
#define _RAREaChartViewer_H_

@class JavaLangDouble;
@class RAREChartAxis;
@class RAREChartDataItem;
@class RAREChartDefinition;
@class RAREMouseEvent;
@class RAREPlotInformation;
@class RARERenderableDataItem;
@class RARESPOTChart;
@class RARESPOTDataItem;
@class RARESPOTPlot;
@class RARESPOTViewer;
@class RAREUIDimension;
@class RAREaChartHandler;
@class SPOTSet;
@protocol RAREUTiFilterableList;
@protocol RAREiContainer;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/chart/iMouseHandler.h"
#include "com/appnativa/rare/viewer/aPlatformViewer.h"
#include "java/lang/Runnable.h"
#include "java/util/Comparator.h"

@interface RAREaChartViewer : RAREaPlatformViewer {
 @public
  id<RAREiPlatformComponent> chartComponent_;
  RAREChartDefinition *chartDefinition_;
  RAREaChartHandler *chartHandler_;
  BOOL aggregate_;
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (BOOL)addWithId:(RARERenderableDataItem *)e;
- (RAREChartDataItem *)addAnnotationWithNSString:(NSString *)annotation
                                        withChar:(unichar)separator;
- (RAREChartDataItem *)addDomainMarkerWithNSString:(NSString *)marker
                                          withChar:(unichar)separator;
- (RAREChartDataItem *)addDomainMarkerWithId:(id)lower
                                      withId:(id)upper
                                withNSString:(NSString *)title;
- (RARERenderableDataItem *)addDomainValueWithId:(id)value;
- (RARERenderableDataItem *)addDomainValueWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)addParsedRowWithRARERenderableDataItem:(RARERenderableDataItem *)row;
- (RAREChartDataItem *)addRangeMarkerWithNSString:(NSString *)marker
                                     withNSString:(NSString *)separator;
- (RAREChartDataItem *)addRangeMarkerWithNSString:(NSString *)marker
                                         withChar:(unichar)separator;
- (RAREChartDataItem *)addRangeMarkerWithDouble:(double)lower
                                     withDouble:(double)upper
                                   withNSString:(NSString *)title;
- (RAREChartDataItem *)addSeriesWithRAREChartDataItem:(RAREChartDataItem *)series;
- (RAREChartDataItem *)addSubTitleWithRARERenderableDataItem:(RARERenderableDataItem *)title;
- (RAREChartDataItem *)addSubTitleWithNSString:(NSString *)title;
- (void)clearChart;
- (void)clearChartData;
- (void)clearContents;
- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (RAREChartDefinition *)createChartDefinitionWithRARESPOTChart:(RARESPOTChart *)cfg;
- (RARERenderableDataItem *)createItemWithId:(id)value;
- (RARERenderableDataItem *)createItemWithId:(id)value
                                     withInt:(int)type
                                      withId:(id)data
                                      withId:(id)icon
                                      withId:(id)color;
- (RARERenderableDataItem *)createItemExWithRARESPOTDataItem:(RARESPOTDataItem *)item;
- (RARERenderableDataItem *)createRowWithInt:(int)capacity
                                 withBoolean:(BOOL)populate;
+ (RAREChartDataItem *)createSeriesWithNSString:(NSString *)name;
+ (RAREChartDataItem *)createSeriesValueWithId:(id)domain
                                        withId:(id)range;
- (void)dispose;
- (void)itemChangedWithRAREChartDataItem:(RAREChartDataItem *)item;
- (RAREChartDataItem *)itemFromLocationWithInt:(int)x
                                       withInt:(int)y;
- (void)rebuildChart;
- (void)refreshItems;
- (void)refreshItemsWithBoolean:(BOOL)rebuild;
- (void)sortSeriesDatapointsWithBoolean:(BOOL)descending;
- (RAREChartDataItem *)toChartSeriesWithRARERenderableDataItem:(RARERenderableDataItem *)item
                                                   withBoolean:(BOOL)firstIsSeriesName;
- (void)updatesCompleted;
- (void)updatesPending;
- (void)setChartDefinitionWithRAREChartDefinition:(RAREChartDefinition *)cd;
- (void)setDomainLabelWithNSString:(NSString *)label;
- (void)setDomainLabelsAngleWithInt:(int)angle;
- (void)setDomainLabelsVisibleWithBoolean:(BOOL)visible;
- (void)setHorizontalScrollingEnabledWithBoolean:(BOOL)enable;
- (void)setOrientationWithBoolean:(BOOL)vertical;
- (void)setPlotValuesVisibleWithBoolean:(BOOL)visible;
- (void)setRangeBoundsWithId:(id)lower
                      withId:(id)upper
          withJavaLangDouble:(JavaLangDouble *)increment;
- (void)setRangeLabelWithNSString:(NSString *)label;
- (void)setRangeLabelsVisibleWithBoolean:(BOOL)visible;
- (void)setVerticalScrollingEnabledWithBoolean:(BOOL)enable;
- (RAREChartDefinition *)getChartDefinition;
- (RAREChartAxis *)getDomainAxis;
- (RARERenderableDataItem *)getDomainValueWithInt:(int)col;
- (RAREChartAxis *)getRangeAxis;
- (id)getSelection;
- (int)getSelectionColumn;
- (int)getSelectionRow;
- (BOOL)getShowPlotValues;
- (RAREUIDimension *)getPlotAreaSize;
- (void)configureExWithRARESPOTChart:(RARESPOTChart *)cfg;
- (RAREChartAxis *)createAxisInfoWithBoolean:(BOOL)raxis
                        withRARESPOTDataItem:(RARESPOTDataItem *)di;
- (RAREaChartHandler *)createChartHandler;
- (RAREChartDataItem *)createColumnWithRARESPOTDataItem:(RARESPOTDataItem *)di
                                                withInt:(int)type;
- (RAREPlotInformation *)createPlotInfoWithRARESPOTPlot:(RARESPOTPlot *)plot;
- (void)finishedLoadingEx;
- (NSString *)getWidgetAttributeWithNSString:(NSString *)name;
- (id<RAREUTiFilterableList>)createListWithSPOTSet:(SPOTSet *)set;
- (void)fixSeriesItemsWithRARERenderableDataItem:(RARERenderableDataItem *)series;
- (void)copyAllFieldsTo:(RAREaChartViewer *)other;
@end

J2OBJC_FIELD_SETTER(RAREaChartViewer, chartComponent_, id<RAREiPlatformComponent>)
J2OBJC_FIELD_SETTER(RAREaChartViewer, chartDefinition_, RAREChartDefinition *)
J2OBJC_FIELD_SETTER(RAREaChartViewer, chartHandler_, RAREaChartHandler *)

typedef RAREaChartViewer ComAppnativaRareViewerAChartViewer;

@interface RAREaChartViewer_$1 : NSObject < RAREiMouseHandler > {
 @public
  RAREaChartViewer *this$0_;
}

- (BOOL)wantsMouseMoveEvents;
- (void)mouseMovedWithRAREMouseEvent:(RAREMouseEvent *)event
               withRAREChartDataItem:(RAREChartDataItem *)item;
- (void)mouseClickedWithRAREMouseEvent:(RAREMouseEvent *)event
                 withRAREChartDataItem:(RAREChartDataItem *)item;
- (id)initWithRAREaChartViewer:(RAREaChartViewer *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREaChartViewer_$1, this$0_, RAREaChartViewer *)

@interface RAREaChartViewer_$2 : NSObject < JavaLangRunnable > {
 @public
  RAREaChartViewer *this$0_;
}

- (void)run;
- (id)initWithRAREaChartViewer:(RAREaChartViewer *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREaChartViewer_$2, this$0_, RAREaChartViewer *)

@interface RAREaChartViewer_$3 : NSObject < JavaLangRunnable > {
 @public
  RAREaChartViewer *this$0_;
}

- (void)run;
- (id)initWithRAREaChartViewer:(RAREaChartViewer *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREaChartViewer_$3, this$0_, RAREaChartViewer *)

@interface RAREaChartViewer_$4 : NSObject < JavaUtilComparator > {
 @public
  BOOL val$descending_;
}

- (int)compareWithId:(RARERenderableDataItem *)o1
              withId:(RARERenderableDataItem *)o2;
- (id)initWithBoolean:(BOOL)capture$0;
@end

#endif // _RAREaChartViewer_H_
