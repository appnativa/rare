//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-charts/com/appnativa/rare/ui/chart/iMouseHandler.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiMouseHandler_H_
#define _RAREiMouseHandler_H_

@class RAREChartDataItem;
@class RAREMouseEvent;

#import "JreEmulation.h"

@protocol RAREiMouseHandler < NSObject, JavaObject >
- (void)mouseClickedWithRAREMouseEvent:(RAREMouseEvent *)event
                 withRAREChartDataItem:(RAREChartDataItem *)item;
- (void)mouseMovedWithRAREMouseEvent:(RAREMouseEvent *)event
               withRAREChartDataItem:(RAREChartDataItem *)item;
- (BOOL)wantsMouseMoveEvents;
@end

#define ComAppnativaRareUiChartIMouseHandler RAREiMouseHandler

#endif // _RAREiMouseHandler_H_
