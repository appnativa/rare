//
//  RARECPTLegentView.h
//  RareTOUCH
//
//  Created by Don DeCoteau on 1/5/15.
//  Copyright (c) 2015 SparseWare. All rights reserved.
//

#import "RAREAPView.h"
#if TARGET_OS_IPHONE
#import "CorePlot-CocoaTouch.h"
#else
#import <CorePlot/CorePlot.h>
#endif
@class RARECPTHostView;
@interface RARECPTLegendView : RAREAPView {
__weak CPTLegend* legend;

}
-(instancetype) initWithHostView: (RARECPTHostView*) host;
- (void)updateColumns:(int)columns andRows: (int) rows;
@end
