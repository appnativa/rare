//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/iWidgetCustomizer.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiWidgetCustomizer_H_
#define _RAREiWidgetCustomizer_H_

@class RARESPOTWidget;
@protocol RAREiParentComponent;
@protocol RAREiWidget;

#import "JreEmulation.h"

@protocol RAREiWidgetCustomizer < NSObject, JavaObject >
- (id<RAREiParentComponent>)widgetConfiguredWithRAREiWidget:(id<RAREiWidget>)widget
                                         withRARESPOTWidget:(RARESPOTWidget *)cfg;
- (void)widgetDisposedWithRAREiWidget:(id<RAREiWidget>)widget;
@end

#define ComAppnativaRareIWidgetCustomizer RAREiWidgetCustomizer

#endif // _RAREiWidgetCustomizer_H_
