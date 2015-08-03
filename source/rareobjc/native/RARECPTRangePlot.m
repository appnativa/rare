//
//  RARECPTRangePlot.m
//  RareTOUCH
//
//  Created by Don DeCoteau on 1/5/15.
//  Copyright (c) 2015 SparseWare. All rights reserved.
//

#import "RARECPTRangePlot.h"

#import <com/appnativa/rare/ui/RenderableDataItem.h>
#import <com/appnativa/rare/ui/chart/ChartDataItem.h>
#import <com/appnativa/rare/ui/chart/coreplot/ChartPlot.h>
#import <com/appnativa/rare/ui/chart/ChartAxis.h>
#import <com/appnativa/rare/ui/painter/iPlatformPainter.h>
#import <com/appnativa/util/NumberRange.h>
#import <java/lang/Double.h>
#import "RARECPTHostView.h"
#import "RARECPTRangePlot.h"

@implementation RARECPTRangePlot {
@public
id<RAREiPainter> painter;
}

-(RAREUTNumberRange*)rangeAtRecordIndex:(NSUInteger)idx {
  RAREChartDataItem *item=[((RARECPTDataSource*)self.dataSource) itemForPlot:self recordIndex:idx];
  return (RAREUTNumberRange*)[ item getValue];
}

-(BOOL)barAtRecordIndex:(NSUInteger)idx basePoint:(CGPoint *)basePoint tipPoint:(CGPoint *)tipPoint
{
  BOOL horizontalBars            = self.barsAreHorizontal;
  CPTCoordinate independentCoord = (horizontalBars ? CPTCoordinateY : CPTCoordinateX);
  CPTCoordinate dependentCoord   = (horizontalBars ? CPTCoordinateX : CPTCoordinateY);
  
  CPTPlotSpace *thePlotSpace = self.plotSpace;
  RAREUTNumberRange *range=(RAREUTNumberRange *)[self rangeAtRecordIndex:idx];
  
  if ( self.doublePrecisionCache ) {
    double plotPoint[2];
    plotPoint[independentCoord] = [self cachedDoubleForField:CPTBarPlotFieldBarLocation recordIndex:idx];
    if ( isnan(plotPoint[independentCoord]) ) {
      return NO;
    }
    
    // Tip point
    //    plotPoint[dependentCoord] = [self cachedDoubleForField:CPTBarPlotFieldBarTip recordIndex:idx];
    plotPoint[dependentCoord]=range.getHighValue.doubleValue;
    if ( isnan(plotPoint[dependentCoord]) ) {
      return NO;
    }
    *tipPoint = [thePlotSpace plotAreaViewPointForDoublePrecisionPlotPoint:plotPoint numberOfCoordinates:2];
    
    plotPoint[dependentCoord]=range.getLowValue.doubleValue;
    if ( isnan(plotPoint[dependentCoord]) ) {
      return NO;
    }
    *basePoint = [thePlotSpace plotAreaViewPointForDoublePrecisionPlotPoint:plotPoint numberOfCoordinates:2];
  }
  else {
    NSDecimal plotPoint[2];
    plotPoint[independentCoord] = [self cachedDecimalForField:CPTBarPlotFieldBarLocation recordIndex:idx];
    if ( NSDecimalIsNotANumber(&plotPoint[independentCoord]) ) {
      return NO;
    }
    
    // Tip point
    //    plotPoint[dependentCoord] = [self cachedDecimalForField:CPTBarPlotFieldBarTip recordIndex:idx];
    plotPoint[dependentCoord]= CPTDecimalFromDouble(range.getHighValue.doubleValue);
    if ( NSDecimalIsNotANumber(&plotPoint[dependentCoord]) ) {
      return NO;
    }
    *tipPoint = [thePlotSpace plotAreaViewPointForPlotPoint:plotPoint numberOfCoordinates:2];
    
    // Base point
    if ( !self.barBasesVary ) {
      plotPoint[dependentCoord] = self.baseValue;
    }
    else {
      plotPoint[dependentCoord] = [self cachedDecimalForField:CPTBarPlotFieldBarBase recordIndex:idx];
    }
    plotPoint[dependentCoord]= CPTDecimalFromDouble(range.getLowValue.doubleValue);
    if ( NSDecimalIsNotANumber(&plotPoint[dependentCoord]) ) {
      return NO;
    }
    *basePoint = [thePlotSpace plotAreaViewPointForPlotPoint:plotPoint numberOfCoordinates:2];
  }
  
  // Determine bar width and offset.
  CGFloat barOffsetLength = [self lengthInView:self.barOffset] * self.barOffsetScale;
  
  // Offset
  if ( horizontalBars ) {
    basePoint->y += barOffsetLength;
    tipPoint->y  += barOffsetLength;
  }
  else {
    basePoint->x += barOffsetLength;
    tipPoint->x  += barOffsetLength;
  }
  
  return YES;
  //  BOOL ok=[super barAtRecordIndex:idx basePoint:basePoint tipPoint:tipPoint];
  //  if(!ok) {
  //    return NO;
  //  }
  //  RAREChartDataItem* item=[((RARECPTDataSource*)self.dataSource) itemForPlot:self recordIndex:idx];
  //  RAREUTNumberRange *range = (RAREUTNumberRange *) item.getValue;
  //
  //  double hi= [range.highValue doubleValue];
  //  double ratio=tipPoint->y/hi;
  //  double lo= [range.lowValue doubleValue];
  //  lo*=ratio;
  //  basePoint->y=lo;
  //  return YES;
}
@end


