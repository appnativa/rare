//
//  RARECPTHostView.m
//  RareOSX
//
//  Created by Don DeCoteau on 8/4/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <com/appnativa/rare/ui/chart/ChartDefinition.h>
#import <com/appnativa/rare/ui/RenderableDataItem.h>
#import <com/appnativa/rare/ui/chart/ChartDataItem.h>
#import <com/appnativa/rare/ui/chart/PlotInformation.h>
#import <com/appnativa/rare/ui/chart/coreplot/ChartHandler.h>
#import <com/appnativa/rare/ui/UIFont.h>
#import <com/appnativa/rare/ui/UIColor.h>
#import <com/appnativa/rare/ui/UIPoint.h>
#import <com/appnativa/rare/ui/UIDimension.h>
#import <com/appnativa/rare/ui/UIInsets.h>
#import <com/appnativa/rare/ui/chart/coreplot/ChartPlot.h>
#import <com/appnativa/rare/ui/chart/ChartAxis.h>
#import <com/appnativa/rare/ui/painter/iPlatformPainter.h>
#import <com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h>
#import <com/appnativa/rare/ui/UIStroke.h>
#import <com/appnativa/rare/converters/aConverter.h>
#import <com/appnativa/rare/converters/ConverterContext.h>
#import <com/appnativa/rare/ui/border/UIMatteBorder.h>
#import <com/appnativa/util/NumberRange.h>
#import <com/appnativa/rare/ui/painter/UIBackgroundPainter.h>
#import <com/appnativa/rare/ui/painter/iPlatformComponentPainter.h>
#import <com/appnativa/rare/ui/UIColorShade.h>
#import <com/appnativa/rare/ui/Location.h>
#import <com/appnativa/rare/ui/FontUtils.h>
#import <com/appnativa/rare/ui/painter/PaintBucket.h>
#import <com/appnativa/rare/ui/event/MouseEventEx.h>
#import <com/appnativa/rare/platform/apple/ui/view/View.h>
#import <IOSFloatArray.h>
#import <java/lang/Double.h>
#include "java/text/SimpleDateFormat.h"
#import "RAREAPView.h"
#import "RARECPTHostView.h"
#import "RAREAPView.h"
#import "AppleHelper.h"
#import "APView+Component.h"
#import "RAREConverterFormatter.h"
#import "APView+Component.h"
#import "RAREEmptyFormatter.h"
#import "RARECPTRangePlot.h"

@implementation CPTColor (ColorWithArgb)

+ (CPTColor *)colorWithARGB:(int)argb {
  CGFloat a = ((int) (((unsigned int) argb) >> 24) & 0xFF) / 255.0;
  CGFloat r = ((argb >> 16) & 0xFF) / 255.0;
  CGFloat g = ((argb >> 8) & 0xFF) / 255.0;
  CGFloat b = (argb & 0xFF) / 255.0;
  return [CPTColor colorWithComponentRed:r green:g blue:b alpha:a];

}
@end

@interface RAREPainterFill : CPTFill <NSCopying, NSCoding> {
  
}
@end

@implementation RAREPainterFill  {
@public
  id<RAREiPainter> painter;
  __weak RAREView* view;
}
-(BOOL)isOpaque {
  return NO;
}
-(void)fillRect:(CGRect)rect inContext:(CGContextRef)context {
  CGContextSaveGState(context);
  CGContextTranslateCTM(context, 0, rect.size.height);
  CGContextScaleCTM(context, 1, -1);
  
  RAREAppleGraphics* g=[[RAREAppleGraphics alloc]initWithId:(__bridge id)(context) withRAREView:view];
  [self->painter paintWithRAREiPlatformGraphics:g withFloat:rect.origin.x withFloat:rect.origin.y withFloat:rect.size.width withFloat:rect.size.height withInt:0];
  CGContextRestoreGState(context);
  [g dispose];
}

-(void)fillPathInContext:(CGContextRef)context {
  CGContextSaveGState(context);
  CGRect rect=CGContextGetClipBoundingBox(context);
  CGContextTranslateCTM(context, 0, rect.size.height);
  CGContextScaleCTM(context, 1, -1);
  rect = CGContextGetPathBoundingBox(context);
  CGContextClip(context);
  RAREAppleGraphics* g=[[RAREAppleGraphics alloc]initWithId:(__bridge id)(context) withRAREView:view];
  [self->painter paintWithRAREiPlatformGraphics:g withFloat:rect.origin.x withFloat:rect.origin.y withFloat:rect.size.width withFloat:rect.size.height withInt:0];
  [g dispose];
  CGContextRestoreGState(context);
}

-(id)copyWithZone:(NSZone *)zone
{
  RAREPainterFill *copy = [[[self class] allocWithZone:zone] init];
  
  copy->painter = self->painter;
  copy->view=self->view;
  
  return copy;
}

@end


@implementation RARECPTHostView {
  RAREChartHandler_LabelsManager*  domainLabels_;
  RAREChartHandler_LabelsManager*  rangeLabels_;
  CGSize oldSize_;
}

- (id)initWithChartHandler:(RAREChartHandler *)ch withGraph:(CPTGraph *)graph {
  self = [super init];
  if (self) {
    self.hostedGraph = graph;
    chartHandler_ = ch;
    oldSize_=CGSizeZero;
    self.allowPinchScaling=NO;
    self.collapsesLayers=YES;
  }

  return self;
}

- (id)initWithChartHandler:(RAREChartHandler *)ch {
  return [self initWithChartHandler:ch withGraph:[[CPTXYGraph alloc] initWithFrame:CGRectZero]];
}

- (CPTFill *)createFillWithColor: (RAREUIColor*) c{
  if([ c isKindOfClass:[RAREUIColorShade class]]) {
    id <RAREiBackgroundPainter> p = ((RAREUIColorShade*)c).getBackgroundPainter;
    if(p) {
      return [self createFill:p];
    }
  }
  return [CPTFill fillWithColor:[CPTColor colorWithARGB:c.getARGB]];
}
- (CPTFill *)createFill:(id <RAREiPainter>)p{
  RAREPainterFill* fill=[RAREPainterFill new];
  fill->painter=p;
  fill->view=self.sparView;
  return fill;
  
}

- (CPTFill *)getItemFill:(RARERenderableDataItem *)item withDefault:(RAREUIColor *)c {
  RAREUIColor *bg = item.getBackground;
  id <RAREiPlatformComponentPainter> cp = (id <RAREiPlatformComponentPainter> )item.getComponentPainter;
  if (cp ) {
    return [self createFill:cp];
  }
  else if (bg) {
    return [self createFillWithColor:bg];
  }
  else {
    return (c ? [self createFillWithColor:c] : nil);
  }

}
-(void) addMarker: (RAREChartDataItem*) item defaultBackground: (RAREUIColor*) bg defaultForeground: (RAREUIColor*) fg range: (BOOL) rangeMarker {
  CPTFill *fill=[self getItemFill:item withDefault:bg];
  NSNumber *lower=(NSNumber*)[item getValue];
  NSNumber *upper=(NSNumber*)[item getDomainValue];
  double len=upper.doubleValue-lower.doubleValue;
  CPTPlotRange* range=[CPTPlotRange plotRangeWithLocation:lower.decimalValue length:[NSNumber numberWithDouble:len].decimalValue];
  CPTLimitBand* marker=[CPTLimitBand limitBandWithRange:range fill:fill];
  CPTGraph *graph = self.hostedGraph;
  CPTXYAxisSet *axisSet = (CPTXYAxisSet *) graph.axisSet;
  if(rangeMarker) {
    [axisSet.yAxis addBackgroundLimitBand:marker];
  }
  else {
    [axisSet.xAxis addBackgroundLimitBand:marker];
  }

}
-(void) removeMarkers: (BOOL) range {
  CPTGraph *graph = self.hostedGraph;
  CPTXYAxisSet *axisSet = (CPTXYAxisSet *) graph.axisSet;
  NSArray* a=nil;
  CPTXYAxis* x;
  if(range) {
    x=axisSet.yAxis;
  }
  else {
    x=axisSet.xAxis;
  }
  a=[x.backgroundLimitBands copy];
  for (CPTLimitBand* m in a) {
    [x removeBackgroundLimitBand:m];
  }
  
}
-(CGPoint) rowAndColumnAtX: (float) x andY: (float)y {
  int i=0;
  CGPoint p=CGPointMake(x, y);
  CGPoint rc=CGPointMake(-1, -1);
  for (id object in self.hostedGraph.allPlots) {
    CPTPlot *plot = (CPTPlot *) object;
    int n=(int)[plot dataIndexFromInteractionPoint:p];
    if(n!=-1) {
      rc.x=n;
      rc.y=i;
    }
    i++;
  }
  return rc;
}


- (void)setChartDefinition:(RAREChartDefinition *)cd {
  chartDefinition_ = cd;
}

-(void) setAxisLabelsLabels:(RAREChartHandler_LabelsManager*) lm forAxis: (CPTXYAxis*) axis domain: (BOOL) domain{
  CGFloat padding=[RAREaChartHandler LABELS_PADDING];
  CGFloat size= domain ? self.frame.size.width : self.frame.size.height;
  if(size<10 || axis.hidden) {
    return;
  }
  IOSObjectArray * a=[lm getLabelsWithRAREChartDefinition:chartDefinition_ withInt: size withInt:padding];
  NSUInteger len=a ? a.count : 0;
  NSMutableArray *customLabels = len==0 ? [NSMutableArray array] : [NSMutableArray arrayWithCapacity:len];
  NSMutableArray *customTicks = len==0 ? [NSMutableArray array] : [NSMutableArray arrayWithCapacity:len];
  NSMutableArray *customMinorTicks = len==0 ? [NSMutableArray array] : [NSMutableArray arrayWithCapacity:len];
  CPTTextStyle *labelTextStyle=axis.labelTextStyle;
  CGFloat labelOffset=axis.labelOffset+axis.minorTickLength;
  CGFloat rotation=axis.labelRotation;
  RAREUIPoint* p=[lm getAxisSizeWithRAREChartDefinition:chartDefinition_];
  CGFloat axisSize=p->y_;
  size-=axisSize;
  if(rotation==0) {
    size-=axisSize*2;
  }
  else {
    padding=MAX(padding-8,0);
  }
  int mod=[RAREaChartHandler getLabelsModWithRAREaChartHandler_LabelDataArray:a withFloat:size withFloat:padding];
  for (NSUInteger i=0;i<len;i++) {
    RAREaChartHandler_LabelData* label=[a objectAtIndex:i];
    NSString* text=(mod==0 || i%mod==0) ? label->label_ : nil;
    if(!text) {
      [customMinorTicks addObject:[NSDecimalNumber numberWithDouble:label->position_]];
      continue;
    }
    CPTAxisLabel *newLabel = [[CPTAxisLabel alloc] initWithText: text textStyle:labelTextStyle];
    [customTicks addObject:[NSDecimalNumber numberWithDouble:label->position_]];
    newLabel.tickLocation = CPTDecimalFromDouble(label->position_);
    newLabel.offset = labelOffset;
    newLabel.rotation = rotation;
    [customLabels addObject:newLabel];
  }
  axis.axisLabels =  [NSSet setWithArray:customLabels];
  axis.majorTickLocations=[NSSet setWithArray:customTicks];
  axis.minorTickLocations=[NSSet setWithArray:customMinorTicks];
  if(domain) {
    axis.graph.plotAreaFrame.paddingBottom=axisSize;
    axis.titleOffset=p->x_+4;
  }
  else {
    axis.graph.plotAreaFrame.paddingLeft=axisSize+15;
    axis.titleOffset=p->x_+4;
  }
}
- (void)configureGraphLabels:(RAREChartHandler_LabelsManager *)domainManager rangeLabels: (RAREChartHandler_LabelsManager*) rangeManager{
  RAREChartDefinition *cd = chartDefinition_;
  RAREChartHandler *ch = chartHandler_;
  RAREPlotInformation *pi = cd.getPlotInformation;
  RAREUIColor *c;
  RAREUIColor *gridColor = [ch getGridColorWithRAREPlotInformation:pi];
  RAREUIStroke *gridStroke = [ch getGridStrokeWithRAREPlotInformation:pi];
  CPTGraph *graph = self.hostedGraph;
  domainLabels_=domainManager;
  rangeLabels_=rangeManager;
  graph.paddingLeft = 15.0f;
  graph.paddingTop = 15.0f;
  graph.paddingRight = 15.0f;
  graph.paddingBottom = 15.0f;
  if ([cd.getChartType ordinal] == RAREChartDefinition_ChartType_PIE) {
    graph.axisSet = nil;
    return;
  }
  
  graph.plotAreaFrame.paddingRight = 15;
  graph.plotAreaFrame.paddingTop = 25;
  graph.plotAreaFrame.paddingBottom = 0;
  graph.plotAreaFrame.paddingLeft = 0;
  
  CPTXYAxisSet *axisSet = (CPTXYAxisSet *) graph.axisSet;
  CPTXYAxis *x = axisSet.xAxis;
  CPTXYAxis *y = axisSet.yAxis;
  x.labelingPolicy = CPTAxisLabelingPolicyNone;
  y.labelingPolicy = CPTAxisLabelingPolicyNone;
  x.hidden=![[cd getDomainAxis] isVisible];
  y.hidden=![[cd getRangeAxis] isVisible];
  if(y.hidden) {
    graph.plotAreaFrame.paddingLeft=15;;
  }
  if(x.hidden) {
    graph.plotAreaFrame.paddingBottom=15;
  }
  
  CPTFill *plotFill = nil;
  if (pi) {
    c = pi.getBackgroundColor;
    if (c) {
      plotFill = [self createFillWithColor:c];
    }
    if (!pi.isShowGridLines) {
      gridColor = nil;
    }
  }
  if(!plotFill) {
    c=ch->plotBackground_;
    if(c) {
      plotFill= [self createFillWithColor:c];
    }
  }
  graph.plotAreaFrame.plotArea.fill=plotFill;
  UIColor* borderColor=nil;
  if(pi && pi->borderColor_) {
    borderColor=(UIColor*)[pi->borderColor_ getAPColor];
  }
  if (gridColor) {
    if(!borderColor) {
      borderColor=(UIColor*)[[gridColor alphaWithInt:255] getAPColor];
    }
    CPTMutableLineStyle *majorGridLineStyle = [CPTMutableLineStyle lineStyle];
    CPTColor *gc = [CPTColor colorWithARGB:gridColor.getARGB];
    majorGridLineStyle.lineColor = gc;
    if (gridStroke) {
      majorGridLineStyle.lineWidth = gridStroke->width_;
          IOSFloatArray *a = gridStroke->dashIntervals_;
          NSInteger len = a.count;
          NSMutableArray *dash = [NSMutableArray arrayWithCapacity:len];
          for (NSInteger i = 0; i < len; i++) {
            [dash addObject:[NSNumber numberWithFloat:[a floatAtIndex:i]]];
          }
          majorGridLineStyle.dashPattern = dash;
        majorGridLineStyle.patternPhase = gridStroke->dashPhase_;
      
    }
    else {
      majorGridLineStyle.lineWidth = 0.5;
    }
    if ([cd.getRangeAxis isShowGridLine]) {
      y.majorGridLineStyle = majorGridLineStyle;
    }
    else {
      y.majorGridLineStyle = nil;
    }
    if ([cd.getDomainAxis isShowGridLine]) {
      x.majorGridLineStyle = majorGridLineStyle;
    }
    else {
      x.majorGridLineStyle = nil;
    }
  }
  
  [self customizeNumberAxis:y withDefinition:[cd getRangeAxis]];
  switch ([RAREChartHandler getDataTypeWithRAREChartAxis:[cd getDomainAxis]]) {
    case RAREaChartHandler_TYPE_DATE:
      [self customizeDateAxis:x withDefinition:[cd getDomainAxis]];
      break;
    case RAREaChartHandler_TYPE_NUMBER:
      [self customizeNumberAxis:x withDefinition:[cd getDomainAxis]];
      break;
    default:
      [self customizeAxis:x withDefinition:[cd getDomainAxis]];
      break;
  }
  
  CPTXYPlotSpace *plotSpace = (CPTXYPlotSpace *) graph.defaultPlotSpace;
  
  // Adjust visible ranges so bar charts are not smushed together
  CPTMutablePlotRange *xRange = [plotSpace.xRange mutableCopy];
  CPTMutablePlotRange *yRange = [plotSpace.yRange mutableCopy];
  CGFloat count=0;
  for (id object in self.hostedGraph.allPlots) {
    CPTPlot *p = (CPTPlot *) object;
    if(borderColor) {
      p.plotArea.borderColor=borderColor.CGColor;
      p.plotArea.borderWidth=1;
    }
    if ([p isKindOfClass:[CPTBarPlot class]]) {
      CPTBarPlot *plot = (CPTBarPlot *) p;
      if (!plot.barBasesVary) {
        count++;
      }
    }
  }
  CGFloat const CPDBarWidth = CPTDecimalDoubleValue(x.majorIntervalLength)/(count+.3);
  CGFloat barX = 0;
  CGFloat xRangeExpansion = 1;
  NSDecimal cptBarWidth = CPTDecimalFromDouble(CPDBarWidth);
  for (id object in self.hostedGraph.allPlots) {
    CPTPlot *p = (CPTPlot *) object;
    if ([p isKindOfClass:[CPTBarPlot class]]) {
      CPTBarPlot *plot = (CPTBarPlot *) p;
      if (!plot.barBasesVary) {
        plot.barWidth = cptBarWidth;
        plot.barOffset = CPTDecimalFromDouble(barX);
        barX += CPDBarWidth;
      }
    }
  }
  [xRange expandRangeByFactor:CPTDecimalFromDouble(xRangeExpansion)];
  
  x.orthogonalCoordinateDecimal = yRange.location;
  y.orthogonalCoordinateDecimal = xRange.location;
  
  x.visibleRange = xRange;
  y.visibleRange = yRange;
  
  x.gridLinesRange = yRange;
  y.gridLinesRange = xRange;
  
  plotSpace.xRange = xRange;
  plotSpace.yRange = yRange;
 
  [self setAxisLabelsLabels:domainManager forAxis:x domain:YES];
  [self setAxisLabelsLabels:rangeManager forAxis:y domain:NO];
}
- (void)updateOnSizeChange:(float) width height: (float) height{
  if(width<10 || height<10) {
    return;
  }
  BOOL hasOther=NO;
  for (id object in self.hostedGraph.allPlots) {
    CPTPlot *p = (CPTPlot *) object;
    if ([p isKindOfClass:[CPTPieChart class]]) {
      ((CPTPieChart *) p).pieRadius = MIN(0.6 * (height / 2.0), 0.6 * (width / 2.0));
    }
    else {
      hasOther=YES;
    }
  }
  if(!hasOther) return;
  CPTGraph *graph = self.hostedGraph;
  CPTXYAxisSet *axisSet = (CPTXYAxisSet *) graph.axisSet;
  CPTXYAxis *x = axisSet.xAxis;
  CPTXYAxis *y = axisSet.yAxis;
  if(!y.hidden) {
    [self setAxisLabelsLabels:rangeLabels_ forAxis:y domain:NO];
  }
  else {
    graph.paddingLeft = 15.0f;
    
  }
  if(!x.hidden) {
    [self setAxisLabelsLabels:domainLabels_ forAxis:x domain:YES];
  }
  else {
    graph.paddingBottom = 15.0f;
  }
}
#if TARGET_OS_IPHONE
- (void)setFrame:(CGRect)frameRect {
  [super setFrame:frameRect];
  CGSize size=frameRect.size;
  if(size.width!=oldSize_.width || size.height!=oldSize_.height) {
    oldSize_=size;
    [self updateOnSizeChange:size.width height:size.height];
  }
}
#else
- (void)setFrame:(NSRect)frameRect {
  [super setFrame:frameRect];
  
  [self updateOnSizeChange:frameRect.size.width height:frameRect.size.height];
}
#endif

+ (void)setTextStyle:(CPTMutableTextStyle *)style withFont:(RAREUIFont *)font andColor:(RAREUIColor *)color {
  if (font) {
    style.fontName = font->name_;
    style.fontSize = font.getSize;
  }
  if (color) {
    style.color = [CPTColor colorWithARGB:color.getARGB];
  }
}

- (void)pieChart:(CPTPieChart *)plot sliceWasSelectedAtRecordIndex:(NSUInteger)index withEvent:(CPTNativeEvent *)event {
  RARECPTDataSource *ds =(RARECPTDataSource *)plot.dataSource;
  RAREChartDataItem* di=[ds itemForPlot:plot recordIndex:index];
  if(di) {
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:self.sparView withId:event];
    [chartDefinition_ mouseClickedWithRAREMouseEvent:me withRAREChartDataItem:di];
  }
}

- (void)plot:(CPTPlot *)plot dataLabelWasSelectedAtRecordIndex:(NSUInteger)index withEvent:(CPTNativeEvent *)event {
  RARECPTDataSource *ds =(RARECPTDataSource *)plot.dataSource;
  RAREChartDataItem* di=[ds itemForPlot:plot recordIndex:index];
  if(di) {
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:self.sparView withId:event];
    [chartDefinition_ mouseClickedWithRAREMouseEvent:me withRAREChartDataItem:di];
  }

}
-(void)barPlot:(CPTBarPlot *)plot barWasSelectedAtRecordIndex:(NSUInteger)index withEvent:(CPTNativeEvent *)event {
  RARECPTDataSource *ds =(RARECPTDataSource *)plot.dataSource;
  RAREChartDataItem* di=[ds itemForPlot:plot recordIndex:index];
  if(di) {
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:self.sparView withId:event];
    [chartDefinition_ mouseClickedWithRAREMouseEvent:me withRAREChartDataItem:di];
  }
}
- (RAREChartPlot *)createRangeChartWithData:(RAREaChartHandler_SeriesData*) seriesData isCategory: (BOOL) category isArea:(BOOL)area is3D:(BOOL)is3D {
  // Create bar plot
  CPTBarPlot *barPlot = [[RARECPTRangePlot alloc] init];
  CPTMutableLineStyle *barLineStyle = [[CPTMutableLineStyle alloc] init];
  RAREChartDataItem *item = [chartDefinition_ getSeriesWithInt:seriesData->seriesIndex_];
  int index=seriesData->dataIndex_;
  barLineStyle.lineWidth = 0;
  barPlot.title = [item description];
  barPlot.lineStyle = barLineStyle;
  barPlot.barWidth = CPTDecimalFromFloat(0.75f); // bar is 75% of the available space
  barPlot.barsAreHorizontal = NO;
  barPlot.barBasesVary =  NO;
  CPTFill *fill=[self getItemFill:item withDefault:nil];
  if(!fill) {
    fill=[self getItemFill:chartDefinition_.getRangeAxis withDefault:[RAREaChartHandler getDefaultColorWithInt:index]];
  }
  barPlot.fill = fill;
  barPlot.delegate=self;
  RARECPTDataSource *dataSource = [[RARECPTDataSource alloc] initWithHostView:self andData:seriesData andChartType:seriesData->chartType_.ordinal isCategory: category];
  barPlot.dataSource=dataSource;
  RAREChartPlot* plot=[[RAREChartPlot alloc] initWithId:barPlot];
  plot->dataSource_=dataSource;
  return plot;
}

- (RAREChartPlot *)createBarChartWithData:(RAREaChartHandler_SeriesData*) seriesData isCategory: (BOOL) category isHorizontal:(BOOL)horizontal isStacked:(BOOL)stacked is3D:(BOOL)is3D {

  // Create bar plot
  CPTBarPlot *barPlot = [[CPTBarPlot alloc] init];
  CPTMutableLineStyle *barLineStyle = [[CPTMutableLineStyle alloc] init];
  RAREChartDataItem *item = [chartDefinition_ getSeriesWithInt:seriesData->seriesIndex_];
  int index=seriesData->dataIndex_;
  barLineStyle.lineWidth = 1.0;
  barPlot.title = [item description];
  barPlot.lineStyle = nil;//barLineStyle;
  barPlot.barWidth = CPTDecimalFromFloat(0.75f); // bar is 75% of the available space
  barPlot.barsAreHorizontal = horizontal;
  barPlot.barBasesVary = stacked ? YES : NO;
  CPTFill *fill=[self getItemFill:item withDefault:nil];
  if(!fill) {
    fill=[self getItemFill:chartDefinition_.getRangeAxis withDefault:[RAREaChartHandler getDefaultColorWithInt:index]];
  }
  barPlot.fill = fill;
  barPlot.delegate=self;
  RARECPTDataSource *dataSource = [[RARECPTDataSource alloc] initWithHostView:self andData:seriesData andChartType:seriesData->chartType_.ordinal isCategory: category];
  barPlot.dataSource=dataSource;
  RAREChartPlot* plot=[[RAREChartPlot alloc] initWithId:barPlot];
  plot->dataSource_=dataSource;
  return plot;
}

- (RAREChartPlot *)createPieChartWithData: (RAREaChartHandler_SeriesData*) seriesData is3D:(BOOL)is3D {
  CPTPieChart *piePlot = [[CPTPieChart alloc] initWithFrame:CGRectZero];
  piePlot.delegate = self;
  piePlot.sliceDirection = CPTPieDirectionClockwise;
  piePlot.pieRadius = MIN(0.6 * (self.frame.size.height / 2.0), 0.6 * (self.frame.size.width / 2.0));
// 3 - Create gradient
  CPTGradient *overlayGradient = [[CPTGradient alloc] init];
  overlayGradient.gradientType = CPTGradientTypeRadial;
  overlayGradient = [overlayGradient addColorStop:[[CPTColor blackColor] colorWithAlphaComponent:0.0] atPosition:0.9];
  overlayGradient = [overlayGradient addColorStop:[[CPTColor blackColor] colorWithAlphaComponent:0.4] atPosition:1.0];
  piePlot.overlayFill = [CPTFill fillWithGradient:overlayGradient];
  piePlot.delegate=self;
  RARECPTDataSource *dataSource = [[RARECPTDataSource alloc] initWithHostView:self andData:seriesData andChartType:seriesData->chartType_.ordinal isCategory: YES];
  piePlot.dataSource=dataSource;
  RAREChartPlot* plot=[[RAREChartPlot alloc] initWithId:piePlot];
  plot->dataSource_=dataSource;

  return plot;
}

- (RAREChartPlot *)createLineChartWithData:(RAREaChartHandler_SeriesData*) seriesData isCategory: (BOOL) category  isSpline:(BOOL)isSpline isArea:(BOOL)area {
  RAREChartDefinition *cd = chartDefinition_;
  RAREPlotInformation *pi = [cd getPlotInformation];
  RAREChartDataItem *item = [cd getSeriesWithInt:seriesData->seriesIndex_];
  int index=seriesData->dataIndex_;
  CPTScatterPlot *linePlot = [[CPTScatterPlot alloc] init];
  RAREUIColor *bg=[RAREPaintBucket getBackgroundColorWithRARERenderableDataItem:item];
  if(!bg) {
    bg=[RAREPaintBucket getBackgroundColorWithRARERenderableDataItem:cd.getRangeAxis];
  }
  CPTColor *c;
  if (bg) {
    c = [CPTColor colorWithARGB:[bg getARGB]];
  }
  else {
    c = [CPTColor colorWithARGB:[RAREaChartHandler getDefaultColorWithInt:index].getARGB];
  }
  linePlot.delegate = self;
  linePlot.title = [item description];
  CPTMutableLineStyle *ls = [linePlot.dataLineStyle mutableCopy];
  ls.lineWidth = [RAREChartHandler getSeriesLineThicknessWithRAREChartDataItem:item withFloat: pi ? pi->lineThickness_ : 1.0];
  ls.lineColor = c;
  if (isSpline) {
    linePlot.interpolation = CPTScatterPlotInterpolationCurved;
  }
  RAREPlotInformation_ShapeStyleEnum *ss = [RAREChartHandler getSeriesShapeStyleWithRAREPlotInformation:pi withRAREChartDataItem:item];
  if (ss.ordinal != RAREPlotInformation_ShapeStyle_NONE && !area) {
    CPTPlotSymbol *ps = [CPTPlotSymbol plotSymbol];
    int i = index % 4;
    switch (i) {
      case 1:
        ps.symbolType = CPTPlotSymbolTypeRectangle;
        break;
      case 2:
        ps.symbolType = CPTPlotSymbolTypeDiamond;
        break;
      case 3:
        ps.symbolType = CPTPlotSymbolTypeTriangle;
        break;
      default:
        ps.symbolType = CPTPlotSymbolTypeEllipse;
        break;
    }
    RAREUIColor *uifc = seriesData->fillColor_;
    CPTColor *fc = uifc ? [CPTColor colorWithARGB:[uifc getARGB]] : nil;
    RAREUIColor *uioc = seriesData->outlineColor_;
    CPTColor *oc = uioc ? [CPTColor colorWithARGB:[uifc getARGB]] : nil;
    switch ([ss ordinal]) {
      case RAREPlotInformation_ShapeStyle_FILLED:
        if (!fc) {
          fc = c;
        }
        ps.lineStyle = ls;
        break;
      case RAREPlotInformation_ShapeStyle_OUTLINED:
        if(!oc) {
          ps.lineStyle = ls;
        }
        else {
          CPTMutableLineStyle *ols=[ls mutableCopy];
          ols.lineColor=oc;
          ps.lineStyle = ols;
        }
        fc = nil;
        break;
      case RAREPlotInformation_ShapeStyle_FILLED_AND_OUTLINED:
        if (!fc) {
          fc = [CPTColor whiteColor];
        }
        if(!oc) {
          ps.lineStyle = ls;
        }
        else {
          CPTMutableLineStyle *ols=[ls mutableCopy];
          ols.lineColor=oc;
          ps.lineStyle = ols;
        }
        break;
      default:
        break;
    }
    
    if (fc) {
      ps.fill = [CPTFill fillWithColor:fc];
    }
    linePlot.plotSymbol = ps;
  }
  linePlot.dataLineStyle = ls;
  if (area) {
    CPTFill *fill = [self getItemFill:item withDefault:nil];
    if (fill) {
      linePlot.areaFill = fill;
    }
    else {
      linePlot.areaFill = [CPTFill fillWithColor:c];
    }
    linePlot.areaBaseValue = [[NSDecimalNumber numberWithFloat:0.75] decimalValue];
  }
  linePlot.delegate=self;
  RARECPTDataSource *dataSource = [[RARECPTDataSource alloc] initWithHostView:self andData:seriesData andChartType:seriesData->chartType_.ordinal isCategory: category];
  linePlot.dataSource=dataSource;
  RAREChartPlot* plot=[[RAREChartPlot alloc] initWithId:linePlot];
  plot->dataSource_=dataSource;
  return plot;

}

- (void)addPlot:(CPTPlot *)plot {
  CPTGraph *graph = self.hostedGraph;
  [graph addPlot:plot];
}

- (void)reset:(RAREChartDefinition *)cd andCreateNewGraph:(BOOL)create {
  if (create) {
    self.hostedGraph = [[CPTXYGraph alloc] initWithFrame:CGRectZero];
  }
  self->rangeLabels_=nil;
  self->domainLabels_=nil;
  [self setChartDefinition:cd];
}

- (void)reloadSeries:(int)index {
  NSArray *a = [self.hostedGraph allPlots];
  if (index < a.count) {
    [((CPTPlot *) [a objectAtIndex:index]) reloadData];
  }
}

- (void)reloadAllPlots {
  for (id object in self.hostedGraph.allPlots) {
    CPTPlot *p = (CPTPlot *) object;
    [p reloadData];
  }
}
-(void) setShowPlotValues: (BOOL) show {
  [self reloadAllPlots];
}

-(RAREUIDimension*) getPlotAreaSize {
  CGRect frame=self.hostedGraph.plotAreaFrame.plotArea.frame;
  return [[RAREUIDimension alloc] initWithFloat:frame.size.width withFloat:frame.size.height];
}

- (void)updateAxis:(BOOL)rangeAxis label:(BOOL)label angle:(BOOL)angle {
  if(!chartDefinition_) return;
  RAREChartAxis *ai = rangeAxis ? [chartDefinition_ getRangeAxis] : [chartDefinition_ getDomainAxis];
  CPTGraph *graph = self.hostedGraph;
  if ([chartDefinition_.getChartType ordinal] == RAREChartDefinition_ChartType_PIE) {
    return;
  }
  CPTXYAxisSet *aset = (CPTXYAxisSet *) graph.axisSet;
  CPTXYAxis *axis = ai.isRangeAxis ? aset.yAxis : aset.xAxis;
  if (angle) {
    axis.labelRotation = ai.getAngle * M_PI / 180*-1;
  }
  if (label) {
    axis.title = ai.getLabel;
  }
  if(!ai.isVisible) {
    axis.hidden=YES;
  }
  CGSize size=self.frame.size;
  [self updateOnSizeChange:size.width height:size.height];
}

- (void)customizeAxis:(CPTXYAxis *)axis withDefinition:(RAREChartAxis *)ai {
  RAREChartHandler *ch = chartHandler_;
  axis.title = ai.getLabel;
  RAREUIFont *f = [ch getAxisTitleFontWithRAREChartAxis:ai];
  RAREUIColor *c = [ch getAxisTitleColorWithRAREChartAxis:ai];
  CPTColor *cc = [CPTColor colorWithARGB:c.getARGB];
  
  CPTMutableTextStyle *style = [CPTMutableTextStyle textStyle];
  [RARECPTHostView setTextStyle:style withFont:f andColor:nil];
  style.color = cc;
  axis.titleTextStyle = style;

  CPTMutableLineStyle *ls = [axis.axisLineStyle mutableCopy];
  RAREUIColor *lc = [ai getForeground];
  if(!lc) lc=[ch getGridColorWithRAREPlotInformation:nil];
  ls.lineColor = [CPTColor colorWithARGB:lc.getARGB];
  axis.axisLineStyle = ls;
  axis.majorTickLineStyle=ls;
  if([ai isShowMinorTicks]) {
    axis.minorTickLineStyle=ls;
  }
  else {
    axis.minorTickLineStyle=nil;
  }
  
  f = [ch getAxisLabelFontWithRAREChartAxis:ai];
  c = [ch getAxisLabelColorWithRAREChartAxis:ai];
  style = [CPTMutableTextStyle textStyle];
  [RARECPTHostView setTextStyle:style withFont:f andColor:c];
  axis.labelTextStyle = style;
  axis.labelRotation = ai.getAngle * M_PI / 180*-1;
  if (!ai.isVisible) {
    axis.hidden = YES;
  }
  NSObject *ctx;
  RAREaConverter *cvt;
  if (ai.isRangeAxis) {
    cvt = [ai getDataConverter];
    ctx = ai.getValueContext;

  }
  else if (!axis.labelFormatter) {
    cvt = [ai getDomainDataConverter];
    ctx = ai.getDomainContext;
  }
  if(![ctx isKindOfClass:[  RAREConverterContext  class]]) {
    ctx=nil;
  }
  if (cvt) {
    axis.labelFormatter = [RAREConverterFormatter formatterWithConverter:cvt context:(  RAREConverterContext *)ctx];
  }
  NSFormatter* formatter=axis.labelFormatter;
  if(!ai.isLabelsVisible) {
    axis.labelingPolicy=CPTAxisLabelingPolicyNone;
    if(![formatter isKindOfClass:[RAREEmptyFormatter class]]) {
      axis.labelFormatter=[[RAREEmptyFormatter alloc]initWithFormatter:formatter];
    }
  }
  else {
    if([formatter isKindOfClass:[RAREEmptyFormatter class]]) {
      axis.labelFormatter=((RAREEmptyFormatter*)formatter)->oldFormatter;
      ((RAREEmptyFormatter*)formatter)->oldFormatter=nil;
    }
  }
}

- (void)customizeNumberAxis:(CPTXYAxis *)axis withDefinition:(RAREChartAxis *)ai {
  [self customizeAxis:axis withDefinition:ai];
}

- (void)customizeDateAxis:(CPTXYAxis *)axis withDefinition:(RAREChartAxis *)ai {
  [self customizeAxis:axis withDefinition:ai];
}

- (void)setAxis:(RAREChartAxis *)ai increment:(double)increment {
  CPTXYAxisSet *axisSet = (CPTXYAxisSet *) self.hostedGraph.axisSet;
  CPTXYAxis *axis = ai.isRangeAxis ? axisSet.yAxis : axisSet.xAxis;
   axis.majorIntervalLength = CPTDecimalFromDouble(increment);
}

- (void) updateAxisXincrement: (double)xInc yIncrement: (double)yInc {
  CPTXYAxisSet *axisSet = (CPTXYAxisSet *) self.hostedGraph.axisSet;
  if(xInc!=0) {
    axisSet.xAxis.majorIntervalLength = CPTDecimalFromDouble(xInc);
  }
  if(yInc!=0) {
    axisSet.yAxis.majorIntervalLength = CPTDecimalFromDouble(yInc);
  }
  
}

- (void)setAxisRange:(RAREChartAxis *)ai loValue:(double)loValue  hiValue:(double)hiValue increment:(double)increment expand:(BOOL)expand{
  CPTXYAxisSet *axisSet = (CPTXYAxisSet *) self.hostedGraph.axisSet;
  CPTXYAxis *axis = ai.isRangeAxis ? axisSet.yAxis : axisSet.xAxis;
  CPTXYPlotSpace *plotSpace = (CPTXYPlotSpace *) self.hostedGraph.defaultPlotSpace;
  CPTMutablePlotRange *range = [CPTMutablePlotRange plotRangeWithLocation:CPTDecimalFromDouble(loValue) length:CPTDecimalFromDouble(hiValue - loValue)];
  if(increment!=0) {
    axis.majorIntervalLength = CPTDecimalFromDouble(increment);
  }
  
  
  if (expand) {
    if (ai.isRangeAxis) {
      [range expandRangeByFactor:CPTDecimalFromDouble(1.05)];
      plotSpace.yRange = range;
    }
    else {
      CGFloat xRangeExpansion = 1.0;
      for (id object in self.hostedGraph.allPlots) {
        CPTPlot *p = (CPTPlot *) object;
        if ([p isKindOfClass:[CPTBarPlot class]]) {
          CPTBarPlot *plot = (CPTBarPlot *) p;
          if (!plot.barBasesVary) {
            xRangeExpansion += .05;
          }
        }
      }
      [range expandRangeByFactor:CPTDecimalFromDouble(xRangeExpansion)];
    }
  }
  if (ai.isRangeAxis) {
    plotSpace.yRange = range;
  }
  else {
    plotSpace.xRange = range;
  }
}
#if TARGET_OS_IPHONE
-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
  if(self.tag & MASK_MOUSE_HANDLER) {
    RAREView* view=self.sparView;
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseListener_ mousePressedWithRAREMouseEvent:me];
    if(me.isConsumed) {
      return;
    }
  }

  [super touchesBegan:touches withEvent:event];

}

-(void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
  if(self.tag & MASK_MOUSE_HANDLER) {
    RAREView* view=self.sparView;
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseListener_ mouseReleasedWithRAREMouseEvent:me];
    if(me.isConsumed) {
      return;
    }
  }
  [super touchesEnded:touches withEvent:event];


}
-(void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event {
  if(self.tag & MASK_MOUSE_MOTION_HANDLER) {
    RAREView* view=self.sparView;
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseMotionListener_ mouseDraggedWithRAREMouseEvent:me];
    if(me.isConsumed) {
      return;
    }
  }
  [super touchesMoved:touches withEvent:event];
}
#else
#endif
@end

@implementation RARECPTDataSource {
  CPTMutableTextStyle *labelText_;
  NSString *labelFormat_;
  __weak RAREaChartHandler_SeriesData* seriesData_;
  NSUInteger seriesSize_;
  int chartType_;
  BOOL categoryDomain_;

}
- (id)initWithHostView:(RARECPTHostView *)hv andData:(RAREaChartHandler_SeriesData*)data  andChartType: (int) type isCategory: (BOOL) category{
  self = [super init];
  if (self) {
    seriesData_=data;
    seriesSize_=data->domainValues_ ? data->domainValues_.size : 0;
    hostView_ = hv;
    chartType_=type;
    categoryDomain_=category;
  }
  return self;
}

- (RAREChartDataItem *)itemForPlot:(CPTPlot *)plot recordIndex:(NSUInteger)index {
    if(!seriesData_) return nil;
    return  (RAREChartDataItem *) [seriesData_->dataItems_ getWithInt:(int) index];
}

- (CGFloat)radialOffsetForPieChart:(CPTPieChart *)piePlot recordIndex:(NSUInteger)index {
  if(!seriesData_) return 0;
  RAREChartDataItem *item = (RAREChartDataItem *) [seriesData_->dataItems_ getWithInt:(int) index];
  if ([item.getVerticalAlignment ordinal] == RARERenderableDataItem_VerticalAlign_TOP) {
    return 10;
  }
  return 0;
}

- (CPTFill *)sliceFillForPieChart:(CPTPieChart *)piePlot recordIndex:(NSUInteger)index {
   RAREChartDataItem *item = (RAREChartDataItem *) [seriesData_->dataItems_ getWithInt:(int) index];
  RAREUIColor* bg=[RAREaChartHandler getDefaultColorWithInt:(int)index];
  return [hostView_ getItemFill:item withDefault:bg];
}

- (NSString *)legendTitleForPieChart:(CPTPieChart *)piePlot recordIndex:(NSUInteger)index {
  if(!seriesData_) return @"";
  RAREChartDataItem *item = (RAREChartDataItem *) [seriesData_->dataItems_ getWithInt:(int) index];
  return [item getDomainString];
}

- (NSArray *)symbolsForScatterPlot:(CPTScatterPlot *)plot recordIndexRange:(NSRange)indexRange {
  return nil;
}

- (CPTPlotSymbol *)symbolForScatterPlot:(CPTScatterPlot *)plot recordIndex:(NSUInteger)index {
  return nil;
}


- (CPTLineStyle *)barLineStyleForBarPlot:(CPTBarPlot *)barPlot recordIndex:(NSUInteger)index {
  if(!seriesData_) return nil;
  RAREChartDataItem *item = (RAREChartDataItem *) [seriesData_->dataItems_ getWithInt:(int) index];
  if (item.getBorder) {
    return (CPTLineStyle *)[NSNull null];
  }
  return nil;
}

- (NSUInteger)numberOfRecordsForPlot:(CPTPlot *)plot {
  return seriesSize_;
}
- (NSNumber *)numberForPlot:(CPTPlot *)plot field:(NSUInteger)fieldEnum recordIndex:(NSUInteger)index {
  if(!seriesData_) return [NSDecimalNumber zero];
  RAREChartDataItem *item = (RAREChartDataItem *) [seriesData_->dataItems_ getWithInt:(int) index];
  switch (chartType_) {
    case RAREChartDefinition_ChartType_PIE:
      if (CPTPieChartFieldSliceWidth == fieldEnum) {
        return (NSNumber *) item.getValue;
      }
      break;
    case RAREChartDefinition_ChartType_RANGE_AREA:
    case RAREChartDefinition_ChartType_RANGE_BAR:
      switch (fieldEnum) {
        case CPTRangePlotFieldX: {
          if(categoryDomain_) {
            return (NSNumber *) [seriesData_->domainValues_ getWithInt:(int)index];
          }
          else {
            return [JavaLangDouble valueOfWithDouble:item.getDomainDouble];
          }
        }
        case CPTRangePlotFieldLeft:
          return [NSDecimalNumber zero];
        case CPTRangePlotFieldLow: {
          RAREUTNumberRange *range = (RAREUTNumberRange *) item.getValue;
          return range.getLowValue;
        }
        case CPTRangePlotFieldRight:
          return [NSDecimalNumber zero];
        case CPTRangePlotFieldHigh: {
          RAREUTNumberRange *range = (RAREUTNumberRange *) item.getValue;
          return range.getHighValue;
        }
        default: {
          RAREUTNumberRange *range = (RAREUTNumberRange *) item.getValue;
          return range.getHighValue;
        }

      }
      break;
    default:
      switch (fieldEnum) {
        case CPTScatterPlotFieldX: {
          if(categoryDomain_) {
            return (NSNumber *) [seriesData_->domainValues_ getWithInt:(int)index];
          }
          else {
            return [JavaLangDouble valueOfWithDouble:item.getDomainDouble];
          }
        }
        case CPTScatterPlotFieldY:{
          return (NSNumber*)item.getValue;
        }
        default:
          break;

      }
      break;

  }
  return [NSDecimalNumber zero];
}

- (CPTLayer *)dataLabelForPlot:(CPTPlot *)plot recordIndex:(NSUInteger)index {
  if(!seriesData_ || !seriesData_->showPointLabels_) return (CPTLayer *)[NSNull null];
  if (!labelText_) {
    RAREChartDefinition *cd = hostView_->chartDefinition_;
    RAREChartHandler *ch = hostView_->chartHandler_;
    labelText_ = [CPTMutableTextStyle textStyle];
    [RARECPTHostView setTextStyle:labelText_
                         withFont:[ch getTextFontWithRAREChartDefinition:cd]
                         andColor:[ch getTextColorWithRAREChartDefinition:cd]];
    if ([cd getPlotInformation]) {
      labelFormat_ = [[cd getPlotInformation] getLabelsFormat];
    }
    if([plot isKindOfClass:[CPTPieChart class]]) {
      if(!labelFormat_ || labelFormat_.length==0) {
        labelFormat_=@"{2}";
      }
    }
  }
  NSString* label=nil;
  if([plot isKindOfClass:[CPTPieChart class]]) {
    label=[seriesData_ getPieChartLabelWithInt:(int)index withNSString:labelFormat_];
  }
  else {
    label=[seriesData_ getPointLabelWithInt:(int)index withNSString:labelFormat_];
  }
  if (!label || label.length==0) {
    return (CPTLayer *)[NSNull null];
  }
  RAREChartDataItem *item = (RAREChartDataItem *) [seriesData_->dataItems_ getWithInt:(int) index];
  CPTMutableTextStyle *style = labelText_;
  RAREUIColor *c = item.getForeground;
  RAREUIFont *f = item.getFont;
  if (f || c) {
    if (f && c) {
      style = [CPTMutableTextStyle textStyle];
    }
    else {
      style = [style mutableCopy];
    }
    [RARECPTHostView setTextStyle:style withFont:f andColor:c];
  }
  return [[CPTTextLayer alloc] initWithText:label style:style];
}

@end


