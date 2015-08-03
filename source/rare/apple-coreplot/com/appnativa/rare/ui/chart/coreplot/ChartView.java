/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.ui.chart.coreplot;

import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.chart.ChartAxis;
import com.appnativa.rare.ui.chart.ChartDataItem;
import com.appnativa.rare.ui.chart.ChartDefinition;
import com.appnativa.rare.ui.chart.aChartHandler.SeriesData;
import com.appnativa.rare.ui.chart.coreplot.ChartHandler.LabelsManager;
/*-[
#import "RARECPTHostView.h"
#import "AppleHelper.h"
 #import "RARECPTLegendView.h"
]-*/

public class ChartView extends View {
  double[] xAxisValues;
  double[] yAxisValues;

  public ChartView(ChartHandler ch) {
    super(createProxy(ch));
  }

  public native void addPlot(ChartPlot plot)
  /*-[
    RARECPTHostView* hv=(RARECPTHostView*)proxy_;
    [hv addPlot: (CPTPlot*)plot->proxy_];
  ]-*/
  ;

  public native void rowAndColumnAt(float x, float y, UIPoint outPoint)
  /*-[
    RARECPTHostView* hv=(RARECPTHostView*)proxy_;
    CGPoint p=[hv rowAndColumnAtX: x andY: y];
    outPoint->x_=p.x;
    outPoint->y_=p.y;
  ]-*/
  ;

  public native ChartPlot createPlot(SeriesData data, boolean category)
  /*-[
    RARECPTHostView* hv=(RARECPTHostView*)proxy_;
    RAREChartDefinition* cd=hv->chartDefinition_;
    switch([data->chartType_ ordinal]) {
      case RAREChartDefinition_ChartType_LINE:
      return [hv createLineChartWithData: data isCategory: category isSpline: NO isArea: NO];
      case RAREChartDefinition_ChartType_SPLINE:
      return [hv createLineChartWithData: data isCategory: category isSpline: YES isArea: NO];
      case RAREChartDefinition_ChartType_AREA:
      return [hv createLineChartWithData: data isCategory: category isSpline: NO isArea: YES];
      case RAREChartDefinition_ChartType_SPLINE_AREA:
      return [hv createLineChartWithData: data isCategory: category isSpline: YES isArea: YES];
      case RAREChartDefinition_ChartType_STACKED_BAR:
      return [hv createBarChartWithData: data isCategory: category isHorizontal:![cd isVertical] isStacked: YES is3D:[cd isDraw3D]];
      case RAREChartDefinition_ChartType_BAR:
      return [hv createBarChartWithData: data isCategory: category isHorizontal:![cd isVertical] isStacked: NO is3D:[cd isDraw3D]];
      case RAREChartDefinition_ChartType_RANGE_BAR:
      return[ hv createRangeChartWithData: data isCategory: category isArea:NO is3D:[cd isDraw3D]];
      case RAREChartDefinition_ChartType_RANGE_AREA:
      return[ hv createRangeChartWithData: data isCategory: category isArea:YES is3D:[cd isDraw3D]];
      case RAREChartDefinition_ChartType_PIE:
      return [hv createPieChartWithData: data is3D: [cd isDraw3D]];
      default:
      return nil;
    }
  ]-*/
  ;

  public native void addMarker(ChartDataItem item, UIColor bg, UIColor fg, boolean rangeMarker)
  /*-[
    RARECPTHostView* hv=(RARECPTHostView*)proxy_;
    [hv addMarker: item defaultBackground:  bg defaultForeground: fg range: rangeMarker];
  ]-*/
  ;

  public native void reloadAllPlots()
  /*-[
    [((RARECPTHostView*)proxy_) reloadAllPlots];
  ]-*/
  ;

  public native void reloadSeries(int index)
  /*-[
    [((RARECPTHostView*)proxy_) reloadSeries: index];
  ]-*/
  ;

  public native void reset(ChartDefinition cd, boolean createNewGraph)
  /*-[
   RARECPTHostView* hv=(RARECPTHostView*)proxy_;
   [hv  reset: cd andCreateNewGraph: createNewGraph];
  ]-*/
  ;

  public native void updateAxis(boolean rangeAxis, boolean label, boolean angle)
  /*-[
    [((RARECPTHostView*)proxy_) updateAxis: rangeAxis label: label angle: angle];
  ]-*/
  ;

  public native void setAxisRange(ChartAxis ai, double min, double max, double inc)
  /*-[
    RARECPTHostView* hv=(RARECPTHostView*)proxy_;
    [hv setAxisRange:ai loValue:min hiValue:max increment: inc expand: YES];
  ]-*/
  ;

  public native void setAxisRanges(double xMin, double xMax, double xInc, double yMin, double yMax, double yInc,
                                   LabelsManager domainManager, LabelsManager rangeManager)
  /*-[
    RARECPTHostView* hv=(RARECPTHostView*)proxy_;
    [hv setAxisRange:[hv->chartDefinition_ getDomainAxis] loValue:xMin hiValue:xMax increment: xInc expand: NO];
    [hv setAxisRange:[hv->chartDefinition_ getRangeAxis] loValue:yMin hiValue:yMax increment: yInc expand: NO];
    [hv configureGraphLabels:domainManager rangeLabels:rangeManager];
  ]-*/
  ;

  public native void updateAxisIncrements(double xInc, double yInc)
  /*-[
    RARECPTHostView* hv=(RARECPTHostView*)proxy_;
    [hv updateAxisXincrement: xInc yIncrement: yInc];
  ]-*/
  ;

  public View createLegendView() {
    return new View(createLegendViewProxy());
  }

  native Object createLegendViewProxy()
  /*-[
    RARECPTHostView* hv=(RARECPTHostView*)proxy_;
    return [[RARECPTLegendView alloc] initWithHostView: hv];
  ]-*/
  ;

  private static native Object createProxy(ChartHandler ch)
  /*-[
    return [[RARECPTHostView alloc] initWithChartHandler: ch];
  ]-*/
  ;

  public void setAxisTick(ChartAxis rangeAxis, double increment) {}

  public native void setShowPlotValues(boolean show)
  /*-[
    RARECPTHostView* hv=(RARECPTHostView*)proxy_;
    [hv setShowPlotValues: show];
  ]-*/
  ;

  public native UIDimension getPlotAreaSize()
  /*-[
    return [((RARECPTHostView*)proxy_) getPlotAreaSize];
  ]-*/
  ;
}
