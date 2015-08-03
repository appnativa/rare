//
//  RARECPTLegentView.m
//  RareTOUCH
//
//  Created by Don DeCoteau on 1/5/15.
//  Copyright (c) 2015 appNativa. All rights reserved.
//

#import "RARECPTLegendView.h"
#import "RARECPTHostView.h"
#import <com/appnativa/rare/ui/chart/ChartDefinition.h>
#import <com/appnativa/rare/ui/chart/coreplot/ChartHandler.h>
#import "APView+Component.h"

@implementation RARECPTLegendView
-(instancetype) initWithHostView: (RARECPTHostView*) host {
  
  if(self=[super initWithFrame:CGRectMake(0, 0, 50, 50)]) {
    CPTGraph *graph = host.hostedGraph;
    CPTLegend *l = [CPTLegend legendWithGraph:graph];
    RAREChartHandler *ch = host->chartHandler_;
    RAREChartDefinition* cd=host->chartDefinition_;
    CPTMutableTextStyle *style = [CPTMutableTextStyle textStyle];
    [RARECPTHostView setTextStyle:style withFont:[ch getTextFontWithRAREChartDefinition:cd]
                         andColor:[ch getTextColorWithRAREChartDefinition:cd]];
    l.textStyle=style;
    legend=l;
    self.layer.transform = CATransform3DMakeScale(1.0f, -1.0f, 1.0f);
#if TARGET_OS_IPHONE
    [self.layer  addSublayer:l];
#else
    self.layer = l;
    [self setWantsLayer:YES];
#endif
  }
  return self;
}
-(void)sparDispose {
  CPTLegend* l=legend;
  if(l) {
    NSArray* a=[l allPlots];
    for (CPTPlot* plot in a) {
      [l removePlot:plot];
    }
  }
  [super sparDispose];
}

- (void)updateColumns:(int)columns andRows: (int) rows {
  CPTLegend *l=legend;
  if(l) {
    if (columns > 0) {
      l.numberOfColumns = (NSUInteger) columns;
    }
    if (rows > 0) {
      l.numberOfRows = (NSUInteger) rows;
    }
    [l setLayoutChanged];
    [self setNeedsDisplay];
  }
  
  
}
#if TARGET_OS_IPHONE
-(CGSize)getPreferredSize:(CGFloat)maxWidth {
  CPTLegend* l=legend;
  if(l) {
    [l rowHeightsThatFit];
    CGSize size=l.bounds.size;
    size.width+=16;
    return size;
  }
  return CGSizeZero;
  
}
-(void)layoutSublayersOfLayer:(CALayer *)layer {
  [super layoutSublayersOfLayer:layer];
  CPTLegend* l=legend;
  if(l) {
    CGRect rect=self.frame;
    rect.origin.x=0;
    rect.origin.y=0;
    l.frame=rect;
    l.anchorPoint=CGPointMake(0.5,0.5);
  }
}
#else
- (NSSize)intrinsicContentSize {
  CPTLegend* l=legend;
  if(l) {
    [l rowHeightsThatFit];
    CGSize size=l.bounds.size;
    size.width+=16;
    return size;
  }
  return [self intrinsicContentSize];
}


- (BOOL)isFlipped {
  return NO
}
#endif
@end
