//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-coreplot/com/appnativa/rare/ui/chart/coreplot/ChartPlot.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREChartPlot_H_
#define _RAREChartPlot_H_

#import "JreEmulation.h"

@interface RAREChartPlot : NSObject {
 @public
  id proxy_;
  id dataSource_;
}

- (id)initWithId:(id)proxy;
- (void)dispose;
- (void)copyAllFieldsTo:(RAREChartPlot *)other;
@end

J2OBJC_FIELD_SETTER(RAREChartPlot, proxy_, id)
J2OBJC_FIELD_SETTER(RAREChartPlot, dataSource_, id)

typedef RAREChartPlot ComAppnativaRareUiChartCoreplotChartPlot;

#endif // _RAREChartPlot_H_
